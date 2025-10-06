package game;

import java.awt.Graphics;
import java.awt.event.*;

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
	}
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		if(e.getKeyCode() == 87) { forward = true; }
		if(e.getKeyCode() == 65) { left = true; }
		if(e.getKeyCode() == 68) { right = true; }


		}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 87) { forward = false; }
		if(e.getKeyCode() == 65) { left = false; }
		if(e.getKeyCode() == 68) { right = false; }
	}

	public void keyTyped(KeyEvent e) {

	}

	public void move() {
		
		if(forward && Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2)) < maxSpd) { 
			this.xVel += accel * Math.cos(this.rotation * (Math.PI/180));
			System.out.println(xVel);
			this.yVel += accel * Math.sin(this.rotation * (Math.PI/180));
		}
		
		xVel = xVel > 0 ? xVel - decel : xVel + decel; // x move decay
		yVel = yVel > 0 ? yVel - decel : yVel + decel; // y move decay

		if (left) { this.rotate(-rotSpd); }
		if (right) { this.rotate(rotSpd); }

		this.position.setX(this.position.getX() + xVel);
		this.position.setY(this.position.getY() + yVel);
	}

	// make sure to add map looparound (when goes offscreen, teleport to opposite
	// side)

}
