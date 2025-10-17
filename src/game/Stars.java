package game;

import java.awt.Graphics;

public class Stars extends Polygon{
	private static double paralaxConst = 0.03;
	
	private static Point[] starShape = { 
			/*
			 * Star shape too resource demanding
			 * 
			 * 
			new Point(-1, 0), // A
			new Point(-0.4, 0), // B
			new Point(0, 0.75), // D
			new Point(0.4, 0), // E
			new Point(1, 0), // A'
			new Point(0.47, -0.3), // H
			new Point(0.75, -1), // C'
			new Point(0, -0.6), // G
			new Point(-0.74, -1), // C
			new Point(-0.45, -0.3) // F
			*/
			
			
			//star shape same effect, less resource usage
			new Point(-1.5,0),
			new Point(0,3),
			new Point(1.5,0),

	};
	//individual randomized shape for each star
	private Point[] indShape = new Point[starShape.length];

	{ // randomly generated size per star
		for (int i = 0; i < starShape.length; i++) {
			indShape[i] = new Point(starShape[i].getX() * Math.random() * 3, 
					starShape[i].getY() * Math.random() * 3);

		}
	}	
	
	public Stars(int x, int y) {
		super(starShape, new Point(x, y), 0);
	}
	
	public void paint(Graphics g, double xVel, double yVel) {
		
		/* offsetCalc starOffset =  */
		//(position.getX(), xVel) -> position.setX(position.getX() - (xVel * 0.1));
		
		//position.setX(position.getX() - (xVel * 0.1));
		//position.setY(position.getY() - (yVel * 0.1));

		offsetCalc offsetX = vel -> position.setX(position.getX() - (vel * paralaxConst));
		offsetCalc offsetY = vel -> position.setY(position.getY() - (vel * paralaxConst));

		offsetX.starOffset(xVel);
		offsetY.starOffset(yVel);

		
		
		int[] x = new int[indShape.length];
		int[] y = new int[indShape.length];

		for (int i = 0; i < this.getPoints().length; i++) {
			x[i] = (int) this.getPoints()[i].getX();
			y[i] = (int) this.getPoints()[i].getY();
		}
		g.fillPolygon(x, y, indShape.length);

	}

}
