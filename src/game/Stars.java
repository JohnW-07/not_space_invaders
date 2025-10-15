package game;

import java.awt.Graphics;

public class Stars extends Polygon {
	private static Point[] starShape = { new Point(-1, 0), // A
			new Point(-0.4, 0), // B
			new Point(0, 0.75), // D
			new Point(0.4, 0), // E
			new Point(1, 0), // A'
			new Point(0.47, -0.3), // H
			new Point(0.75, -1), // C'
			new Point(0, -0.6), // G
			new Point(-0.74, -1), // C
			new Point(-0.45, -0.3) // F

	};
	private Point[] indShape = new Point[starShape.length];
	private String color;

	{ // randomly generated size per star
		for (int i = 0; i < starShape.length; i++) {
			indShape[i] = new Point(starShape[i].getX() * Math.random(), 
					starShape[i].getY() * Math.random());

		}
	}

	public Stars(int x, int y, String color) {
		super(starShape, new Point(x, y), 0);
		this.color = color;
	}

	public void paint(Graphics g) {
		int[] x = new int[indShape.length];
		int[] y = new int[indShape.length];

		for (int i = 0; i < this.getPoints().length; i++) {
			x[i] = (int) this.getPoints()[i].getX();
			y[i] = (int) this.getPoints()[i].getY();
		}
		g.fillPolygon(x, y, indShape.length);

	}

}
