package game;

public class Ship extends Polygon {
	private int health;
	private static double maxSpd = 100;
	private static double accel = 100;
	private static Point[] shipShape = {new Point(0,0), new Point(60, -50), new Point(0,100), new Point(-60,-50)};
	
	public Ship(int x, int y) {
		super(shipShape, new Point(x, y), 0);
		health = 100;
	}
}
