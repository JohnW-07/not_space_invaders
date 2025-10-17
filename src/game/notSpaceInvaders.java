package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;

public class notSpaceInvaders extends Game {
	private static int counter = 20;
	private static Ship p1 = new Ship(500, 500);
	private static Stars[] starList = new Stars[150];
	private static boolean starsDrawn = true;
	private static Asteroid[] asteroids = new Asteroid[20];
	private static int cooldown = 50;
	// Polygon.fillPolygon(p1);
	// .fillPolygon(p1);

	public notSpaceInvaders() {
		super("notSpaceInvaders!", 1450, 850);
		this.setFocusable(true);
		this.requestFocus();
		for (int i = 0; i < starList.length - 25; i++) {
			starList[i] = new Stars((int) (1450 * Math.random()), (int) (850 * Math.random()));
		}

		for (int i = 0; i < asteroids.length; i++) {
			asteroids[i] = new Asteroid((int) (1250 * Math.random()) + 100, (int) (650 * Math.random()) + 100);
		}

	}
	/**
	 * This method takes care of drawing the ship and the asteroid
	 * Alongside with that logic, this method also implements the game over logic 
	 * keeping track of how many asteroids are left in the game. It also uses the checkCollision method to check for collisions
	 * @param Takes In A Graphics instance
	 */
	public void paint(Graphics brush) {
		if (counter == 0 && cooldown > 0) { cooldown --; }
		if (cooldown != 0) {
			brush.setColor(Color.black);
			brush.fillRect(0, 0, width, height);

			// sample code for printing message for debugging
			// counter is incremented and this message printed
			// each time the canvas is repainted
			// counter++;
			brush.setColor(Color.white);
			brush.drawString("ASTEROIDS LEFT: " + counter, 30, 30);
			p1.paint(brush);
			p1.move();

			// if (!starsDrawn) {
			for (int i = 0; i < starList.length - 25; i++) {
				brush.setColor(Color.white);
				starList[i].paint(brush, p1.getXVel(), p1.getYVel());
				if (starList[i].position.getX() > 1450) {
					starList[i].position.setX(0);
				}
				if (starList[i].position.getX() < 0) {
					starList[i].position.setX(1450);
				}
				if (starList[i].position.getY() > 850) {
					starList[i].position.setY(0);
				}
				if (starList[i].position.getY() < 0) {
					starList[i].position.setY(850);
				}
			}
			// starsDrawn = false;
			// }

			for (int i = 0; i < asteroids.length; i++) {
				brush.setColor(asteroids[i].getColor());
				asteroids[i].paint(brush);
			}

			// Map wrap-around logic
			if (p1.position.getX() > 1450) {
				p1.position.setX(0);
			}
			if (p1.position.getX() < 0) {
				p1.position.setX(1450);
			}
			if (p1.position.getY() > 850) {
				p1.position.setY(0);
			}
			if (p1.position.getY() < 0) {
				p1.position.setY(850);
			}

			// check collision for all asteroids and all lasers
			checkCollision();
		}
	}
/**
 * This methods checks for the initial collision between the line and the asteroid 
 */
	public void checkCollision() {
		for (int i = 0; i < asteroids.length; i++) {
			if (asteroids[i].getColor() != Color.BLACK
					&& p1.checkCollision(asteroids[i].getX(), asteroids[i].getY(), 30)) {
				asteroids[i].delete();
				counter--;
			}
		}
	}

	public static void main(String[] args) {
		notSpaceInvaders a = new notSpaceInvaders();
		a.addKeyListener(p1);
		a.repaint();
	}

}