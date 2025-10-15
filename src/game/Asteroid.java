package game;
import java.awt.Graphics;
import java.util.Random;

public class Asteroid extends Polygon implements RandomGeneration {
	private static Point [] asteroidShape = { new Point (-5,0), new Point (0,5), new Point (5,0), new Point(0,-5)};
	public Asteroid (int x, int y) {
		super(asteroidShape, new Point(x,y), 0 );
		}
	public void paint (Graphics brush) {
		int[] x = new int[asteroidShape.length];
		int[] y = new int[asteroidShape.length];
		
		for (int i = 0; i < this.getPoints().length; i++) {
			x[i] = (int) this.getPoints()[i].getX();
			y[i] = (int) this.getPoints()[i].getY();
		}
		brush.fillPolygon(x, y, asteroidShape.length);
	}
	public Point generation () {
		Random rand = new Random ();
		double randomNumber1 = rand.nextDouble(1000);
		double randomNumber2 = rand.nextDouble(1000);
		return new Point (randomNumber1, randomNumber2);
	}
}
