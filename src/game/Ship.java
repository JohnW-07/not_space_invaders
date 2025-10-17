package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener {
	/* health variable, not currently used */
	private int health = 100;
	
	/*direction vars for keeping track of pressed buttons*/
	private boolean forward = false;
	private boolean right = false;
	private boolean left = false;

	/*keeps track of current ship velocities, independent of currently pressed buttons*/
	private double xVel = 0.0;
	private double yVel = 0.0;

	/*ship maximum velocity, acceleration, rotation, and deceleration limits
	 * static in order to keep fairness, should be same for all players 
	 * (currently only one player, left not hard coded in case more are to be added)
	 * */
	private static double maxSpd = 25.0; // default 25
	private static double accel = 1;
	private static double decel = 0.2;
	private static int rotSpd = 5;
	
	/*ship shape points, kept low to reduce computation load*/
	private static Point[] shipShape = { new Point(0, 0), new Point(-12.5, 15), new Point(25, 0),
			new Point(-12.5, -15) };
	
	/* lasers are tracked on a per-ship basis, contains every laser fired by the ship
	 * lasers are cleared when they fade away
	 * */
	private ArrayList<Laser> lasers = new ArrayList<>();

	/**
	* Base constructor, uses Polygon for shape 
	*
	* @param  x x starting coordinate
	* @param  y y starting coordinate
	*/
	public Ship(int x, int y) {
		super(shipShape, new Point(x, y), 0);
	}

	/**
	* paints current ship 
	* <p>
	* paints every laser: darkens laser, removes from list when laser disappears
	* 
	* @param g graphics brush to draw elements with 
	*/
	
	public void paint(Graphics g) {
		int[] x = new int[shipShape.length];
		int[] y = new int[shipShape.length];

		for (int i = 0; i < this.getPoints().length; i++) {
			x[i] = (int) this.getPoints()[i].getX();
			y[i] = (int) this.getPoints()[i].getY();
		}
		g.fillPolygon(x, y, shipShape.length);

		for (Laser foundLaser : lasers) {
			if (foundLaser != null) {

				g.setColor(foundLaser.color.darker());

				// foundLaser.cooldown = foundLaser.cooldown == 10

				if (foundLaser.cooldown == 0) {
					foundLaser.color = foundLaser.color.darker();
					foundLaser.cooldown = 1;
				} else {
					foundLaser.cooldown--;
				}
				g.drawLine(foundLaser.x1, foundLaser.y1, foundLaser.x2, foundLaser.y2);
				if (foundLaser.color.getRGB() == -16777216) {
					foundLaser = null;

				}

			}
			// System.out.println(foundLaser);
		}
		// System.out.println();
	}

	/**
	* Checks for keyPressed, primarily for WASD movement
	*
	* @param  e input keyEvent
	*/
	public void keyPressed(KeyEvent e) {
		// System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 87) {
			forward = true;
		}
		if (e.getKeyCode() == 65) {
			left = true;
		}
		if (e.getKeyCode() == 68) {
			right = true;
		}
		if (e.getKeyCode() == 32) {
			lasers.add(new Laser(this));
		}

	}

	/**
	* Returns get ship x velocity (stored in xVel). 
	*
	* @return      current ship x velocity
	*/
	public double getXVel() {
		return xVel;
	}

	/**
	 * Returns get ship y velocity (stored in yVel).
	 * 
	 * @return		current ship y velocity
	 */
	public double getYVel() {
		return yVel;
	}

	/**
	* Managing WASD movement
	*
	* @param  e input key released
	*/
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 87) {
			forward = false;
		}
		if (e.getKeyCode() == 65) {
			left = false;
		}
		if (e.getKeyCode() == 68) {
			right = false;
		}
	}

	/**
	* Checks each laser for collision, only if laser is not null and is white(only checks
	* new lasers to reduce compute requirement).
	* <p>
	* Effectively gives each asteroid a circular hitbox:
	* calculates distance orthogonal from line to center of asteroid to determine collision
	* more efficient than calculating point by point: this method is O(1) per asteroid
	* <p>
	* Uses current rotation of ship to separate game field into 4 quadrants, then
	* uses x and y coordinates to determine if the asteroid being checked is
	* in front of ship or behind (only returns true if asteroid is hit in front of ship).
	*
	* @param  x    current asteroid x position
	* @param y     current asteroid y position
	* @param rad   current asteroid radius (NOT EXACT RADIUS OF ASTEROID, extra
	* 			    radius given to make game easier)
	* @return      if collision was detected with current asteroid (given x, y coords)
	*/
	public boolean checkCollision(double x, double y, int rad) {
		for (Laser foundLaser : lasers) {
			if (foundLaser != null && foundLaser.color == Color.WHITE) {

				double a = -foundLaser.y1 + foundLaser.y2;
				double b = foundLaser.x1 - foundLaser.x2;
				double c = (foundLaser.x1 * -foundLaser.y2) - (foundLaser.x2 * -foundLaser.y1);

				double dist = (Math.abs(a * x + b * y + c)) / Math.sqrt(a * a + b * b);
				
				/*
				 * uses current heading, coordinates of ship and asteroids coords 
				 * to determine if asteroid is infront of ship

				 */
				
				if (rad == dist || rad > dist) {
					if((rotation >= 0 && rotation < 90) || (rotation <= -270 && rotation > -360)) {
						if(x > foundLaser.x1 && y > foundLaser.y1) {
							return true;

						}
					}
					if((rotation >= 90 && rotation < 180) || (rotation <= -180 && rotation > -270)) {
						if(x < foundLaser.x1 && y > foundLaser.y1) {
							return true;

						}
					}
					if((rotation >= 180 && rotation < 270) || (rotation <= -90 && rotation > -180)) {
						if(x < foundLaser.x1 && y < foundLaser.y1) {
							return true;

						}
					}
					if((rotation >= 270 && rotation < 360) || (rotation <= 0 && rotation > -90)) {
						if(x > foundLaser.x1 && y < foundLaser.y1) {
							return true;

						}
					}
					
					
				}
			}
		}
		return false;
	}

	/**
	* initialized for implementation, no usage
	*/
	public void keyTyped(KeyEvent e) {
	} 
	
	/**
	* called by paint every tick, moves ship in direction of current heading, 
	* decreases x and y velocities by deceleration value. \
	* <p>
	* uses left and right booleans to rotate ship by rotSpd
	* <p>
	*/
	public void move() {

		if (forward && Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2)) < maxSpd) {
			this.xVel += accel * Math.cos(this.rotation * (Math.PI / 180));
			this.yVel += accel * Math.sin(this.rotation * (Math.PI / 180));
		}

		xVel = xVel > 0 ? xVel - decel : xVel + decel; // x move decay
		yVel = yVel > 0 ? yVel - decel : yVel + decel; // y move decay

		if (left) {
			this.rotate(-rotSpd);
		}
		if (right) {
			this.rotate(rotSpd);
		}

		this.position.setX(this.position.getX() + xVel);
		this.position.setY(this.position.getY() + yVel);

		// laser logic
		/*
		 * for (Laser foundLaser : lasers) { if (foundLaser != null) { if
		 * (foundLaser.opacity == 0) { foundLaser = null; } else { foundLaser.opacity--;
		 * } } }
		 */

	}

	// get laser direction and start pos for collision check -> might need to be
	// replaced with col
	
	/**
	* Helper method to facilitate collision checks, not currently used
	*
	* @return      Double array containing laser direction and start position
	*/
	public Double[] getLaserDir() {
		Double[] ret = new Double[3];
		ret[0] = this.position.getX();
		ret[1] = this.position.getY();
		ret[2] = this.rotation;
		return ret;
	}

	/**
	* reduce ship health and return remaining hp, not currently used
	*
	* @param  dmg  damage to take from ship health
	* @return      current ship health after damage
	*/
	public int damage(int dmg) {
		health -= dmg;
		return health;
	}

	/**
	* Laser inner class
	*/
	private class Laser {
		private int dmg = 0;
		private Color color = Color.WHITE;
		private int cooldown = 3;
		private int x1, x2, y1, y2;

		
		/**
		* Laser base contructor, draws line from current ship position to offscreen
		* in the direction that the ship is pointing
		*
		* @param  ship  current ship used for position info (where to begin laser line)
		*/
		public Laser(Ship ship) {
			x1 = (int) (ship.position.getX() + 12.5);
			y1 = (int) (ship.position.getY() + 12.5);
			x2 = (int) (ship.position.getX() + (2000 * Math.cos(ship.rotation * (Math.PI / 180))));
			y2 = (int) (ship.position.getY() + (1000 * Math.sin(ship.rotation * (Math.PI / 180))));

		}

	}

}
