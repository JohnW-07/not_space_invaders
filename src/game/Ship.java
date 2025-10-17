package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener {
	private int health = 100;

	private boolean forward = false;
	private boolean right = false;
	private boolean left = false;
	private double xVel = 0.0;
	private double yVel = 0.0;
	private static double maxSpd = 25.0;
	private static double accel = 1;
	private static double decel = 0.2;
	private static int rotSpd = 5;
	private static Point[] shipShape = { new Point(0, 0), new Point(-12.5, 15), new Point(25, 0),
			new Point(-12.5, -15) };
	private ArrayList<Laser> lasers = new ArrayList<>();

	public Ship(int x, int y, String color) {
		super(shipShape, new Point(x, y), 0);
	}

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
				
				//foundLaser.cooldown = foundLaser.cooldown == 10
				
				if(foundLaser.cooldown == 0) {
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
			//System.out.println(foundLaser);
		}
		//System.out.println();
	}

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

	public double getXVel() {
		return xVel;
	}
	
	public double getYVel() {
		return yVel;
	}
	
	
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

	public void keyTyped(KeyEvent e) {
	} // initialized for implementation, no usage

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
		for (Laser foundLaser : lasers) {
			if (foundLaser != null) {
				if (foundLaser.opacity == 0) {
					foundLaser = null;
				} else {
					foundLaser.opacity--;
				}
			}
		} 
		*/

	}
	
	
	//get laser direction and start pos for collision check -> might need to be 
	//replaced with col
	public Double[] getLaserDir() {
		Double[] ret = new Double [3];
		ret[0] = this.position.getX();
		ret[1] = this.position.getY();
		ret[2] = this.rotation;
		return ret;
	}
	
	public int damage(int dmg) {
		health -= dmg;
		return health;
	}
	
	private class Laser {
		private int dmg = 0;
		private Color color = Color.WHITE;
		private int cooldown = 3;
		private int x1, x2, y1, y2;

		public Laser(Ship ship) {
			x1 = (int) (ship.position.getX() + 12.5);
			y1 = (int) (ship.position.getY() + 12.5);
			x2 = (int) (ship.position.getX() + (2000 * Math.cos(ship.rotation * (Math.PI / 180))));
			y2 = (int) (ship.position.getY() + (1000 * Math.sin(ship.rotation * (Math.PI / 180))));

		}

	}

}
