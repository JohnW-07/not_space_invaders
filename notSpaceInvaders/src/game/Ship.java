package game;

import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener {
	private int health = 100;
	private String color;

	private boolean forward = false;
	private boolean right = false;
	private boolean left = false;
	private double xVel = 0.0;
	private double yVel = 0.0;
	private static double maxSpd = 10.0;
	private static double accel = 1;
	private static double decel = 0.2;
	private static int rotSpd = 5;
	private static Point[] shipShape = { new Point(0, 0), new Point(-12.5, 15), new Point(25, 0),
			new Point(-12.5, -15) };
	private ArrayList<Laser> lasers = new ArrayList<>();

	public Ship(int x, int y, String color) {
		super(shipShape, new Point(x, y), 0);
		this.color = color;
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
				g.setColor(g.getColor().darker());
				g.drawLine(foundLaser.x1, foundLaser.y1, foundLaser.x2, foundLaser.y2);
			}

		}
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
		for (Laser foundLaser : lasers) {
			if (foundLaser != null) {
				if (foundLaser.opacity == 0) {
					foundLaser = null;
				} else {
					foundLaser.opacity--;
				}
			}
		}

	}

	private class Laser {
		private int dmg = 0;
		private int opacity;
		private int cooldown = 10;
		private int x1, x2, y1, y2;

		private static Point[] laserShape = { new Point(-1500, 4), new Point(1500, 4), new Point(1500, -4),
				new Point(-1500, -4) };

		public Laser(Ship ship) {
			x1 = (int) ship.position.getX();
			y1 = (int) ship.position.getY();
			x2 = (int) (ship.position.getX() + (1000 * Math.cos(ship.rotation * (Math.PI / 180))));
			y2 = (int) (ship.position.getY() + (1000 * Math.sin(ship.rotation * (Math.PI / 180))));

			opacity = 100;
		}

	}

}
