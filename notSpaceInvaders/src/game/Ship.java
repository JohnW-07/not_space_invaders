package game;

import java.awt.Graphics;

public class Ship extends Polygon {
	private int health;
	private String color;
	private static double maxSpd = 100;
	private static double accel = 100;
	private static Point[] shipShape = {new Point(0,0), new Point(60, -50), new Point(0,100), new Point(-60,-50)};
	
	public Ship(int x, int y, String color) {
		super(shipShape, new Point(x, y), 0);
		health = 100;
		this.color = color;
	}
		
	public void paint(Graphics g) {
		int[] x = new int[shipShape.length];
		int[] y = new int[shipShape.length];
		
		for(int i = 0; i < this.getPoints().length; i++) {
			x[i] = (int)this.getPoints()[i].getX();
			y[i] = (int)this.getPoints()[i].getY();
		}
		g.fillPolygon(x, y, shipShape.length);
	}
}
