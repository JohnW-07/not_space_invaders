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
/**
 * @param x the x coordinate of the asteroid
 * @param y the y coordinate of the asteroid
 */
	public Asteroid(int x, int y) {
		super(asteroidShape, new Point(x, y), 0);
	}
	/**
	 * 
	 * @return the x-cordinate of the asteroid
	 */
	public double getX() {
		return super.position.x;
	}
	/**
	 * 
	 * @return the y-coordinate of the asteroid
	 */
	public double getY() {
		return super.position.y;
	}
	/** 
	 * Making The Color Black After Deleting The Asteroid
	 */
	public void delete() {
		color = Color.BLACK;
	}
	/**
	 * 
	 * @return the color of the asteroid
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * 
	 * @param newCol to change the color of the asteroid
	 */
	public void setColor(Color newCol) {
		color = newCol;
	}
	/**
	 * Draws a singular asteroid
	 * @param brush pass in the appropriate Graphics instancc
	 */
	public void paint(Graphics brush) {
		int[] x = new int[asteroidShape.length];
		int[] y = new int[asteroidShape.length];

		for (int i = 0; i < this.getPoints().length; i++) {
			x[i] = (int) this.getPoints()[i].getX();
			y[i] = (int) this.getPoints()[i].getY();
		}
		brush.fillPolygon(x, y, asteroidShape.length);
	}
	/**
	 * @return A singular Point Randomly Generated
	 */
	public Point generation() { // RandomGeneration implementation
		Random rand = new Random();
		double randomNumber1 = rand.nextDouble(1000);
		double randomNumber2 = rand.nextDouble(1000);
		return new Point(randomNumber1, randomNumber2);
	}
	/**
	 * @return An array of Points randomly generated using another distinct method
	 */
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
/**
 * 
 * This class keeps track of the amount of asteroids that have been eliminated
 *
 */
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