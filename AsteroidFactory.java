import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.Random;

/** a class that deploys asteroids through the viewport of the game
@author Jeremy Hilliker @ Langara
@author Tomas Gonzalez Ortega
@version 2017-07-05 23h10
@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736">a 07: Gradius</a>
*/
public class AsteroidFactory {

	private final static AsteroidFactory instance = new AsteroidFactory();
	private static Rectangle startBounds;

	/**
	* constructs the factory
	*/
	private AsteroidFactory() {}

	/**
	* Retrieves the instance of the factory
	* @return instance of type AsteroidFactory
	*/
	public static AsteroidFactory getInstance() {
		return instance;
	}

	/**
	* This method adjusts the bounds in which the factory will be implemented
	* @param x int x is the x coordinate in where the asteroid will be deployed
	* @param minY the minimum y coordinate in which the asteroid will be deployed
	* @param maxY the maximum y coordinate in which the asteroid will be deployed
	*/
	public void setStartBounds(int x, int minY, int maxY) {
		startBounds = new Rectangle(x, minY, x, maxY);
	}

	/**
	* constructs the asteroid starting at points: max x coordinate, a random value within the y coordinate
	* random width between 10 and 40, random height between 10 and 40 and a random velocity from 1 to 4
	* @return AsteroidImpl the actual new asteroid created
	*/
	public Asteroid makeAsteroid() {
		return new AsteroidImpl(899, random(0, 700), random(10, 40), random(10, 40), random(1, 4));
	}

	/**
	* Method to create a random value our of 2 integers
	* @param min the minimum integer value to evaluate
	* @param max the maximum integer value to evaluate
	* @return the random value operation
	*/
	private static int random(int min, int max) {
		Random rand = java.util.concurrent.ThreadLocalRandom.current();
		return min + (int) (rand.nextDouble()*(max-min));
	}

	/**
	* A class to implement the methods of the interface Asteroid
	*/
	private static class AsteroidImpl implements Asteroid {

		private final static Color COLOR = Color.DARK_GRAY;
		private final int velocity;
		private final Ellipse2D.Double shape;

		/**
		* The Asteroid constructor
		* @param x the integer value for the x coordinate
		* @param y the integer value for the y coordinate
		* @param width the integer value for the width
		* @param height the integer value for the height
		* @param aVelocity the integer value to set up the velocity
		*/
		private AsteroidImpl(int x, int y, int width, int height, int aVelocity) {
			velocity = aVelocity;
			shape = new Ellipse2D.Double(x, y, width, height);
		}

		/**
		* Controls the direction in which the asteroids are moving (from right to left)
		*/
		public void move() {
			shape.x -= velocity;
		}

		/**
		* verifies if a shape is visible
		* @return false if not
		* @return true if it's still visible
		*/
		public boolean isVisible() {
			if(shape.x < -50) {
				return false;
			}
			return true;
		}

		/**
		* Draws the asteroids
		* @param g the graphics context
		*/
		public void draw(Graphics2D g) {
			g.setColor(COLOR);
			g.fill(shape);
		}

		/**
		* This method retrieves the shape of the asteroid
		* @return shape the actual asteroid of type Shape
		*/
		public Shape getShape() {
			return shape;
		}

		/**
		* Checks if there is any collision amongst objects
		* @param other the object of type Sprite to compare areas and declare if there is a collision or not
		* @return false if intersected
		* @return true if there isn't any collision
		*/
		public boolean intersects(Sprite other) {
			if(shape.intersects(other.getShape().getBounds2D())) {
				Area a = new Area(shape);
				Area b = new Area(other.getShape());
				a.intersect(b);
				if(!a.isEmpty()) {
					return true;
				}
			}
			return false;
		}
	}
}
