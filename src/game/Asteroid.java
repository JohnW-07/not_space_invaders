package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Asteroid extends Polygon implements RandomGeneration {
	private static Point[] asteroidShape = { 
			new Point(-15, 0), 
			new Point(0, 15), 
			new Point(15, 0), 
			new Point(0, -15) 
			};
	
	private Color color = Color.white;

	public Asteroid(int x, int y) {
		super(asteroidShape, new Point(x, y), 0);
	}
	
	public double getX() {
		return super.position.x;
	}
	
	public double getY() {
		return super.position.y;
	}
	
	public void delete() {
		color = Color.BLACK;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color newCol) {
		color = newCol;
	}
	
	public void paint(Graphics brush) {
		int[] x = new int[asteroidShape.length];
		int[] y = new int[asteroidShape.length];

		for (int i = 0; i < this.getPoints().length; i++) {
			x[i] = (int) this.getPoints()[i].getX();
			y[i] = (int) this.getPoints()[i].getY();
		}
		brush.fillPolygon(x, y, asteroidShape.length);
	}

	public Point generation() { // RandomGeneration implementation
		Random rand = new Random();
		double randomNumber1 = rand.nextDouble(1000);
		double randomNumber2 = rand.nextDouble(1000);
		return new Point(randomNumber1, randomNumber2);
	}

	public Point[] asteroidRandomGeneration() {
		Point[] asteroidShapeRandomGeneration = { generation(), generation(), generation(), generation() };
		return asteroidShapeRandomGeneration;
	}

	RandomGeneration randomExample = new RandomGeneration() {
		public Point generation() {
			Random rand = new Random();
			double randomNumber1 = rand.nextDouble(1000);
			double randomNumber2 = rand.nextDouble(1000);
			return new Point(randomNumber1, randomNumber2);
		}

		public Point[] asteroidRandomGeneration() {
			Point[] asteroidShapeRandomGeneration = { generation(), generation(), generation(), generation() };
			return asteroidShapeRandomGeneration;
		}
	};

	private class Counter {
		int count;
		int scoreKeeper;
		int highScoreKeeper;
		int streak;

		public Counter(int count, int scoreKeeper, int highScoreKeeper, int streak) {
			this.count = count;
			this.scoreKeeper = scoreKeeper;
			this.highScoreKeeper = highScoreKeeper;
			this.streak = streak;
		}
	}
}