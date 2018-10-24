import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/** A implementation for the Ship class
@author Jeremy Hilliker @ Langara
@author Tomas Gonzalez Ortega
@version 2017-07-05 23h10
@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736">a 07: Gradius</a>
*/
public class ShipImpl implements Ship {

	private final static Color FILL = Color.GREEN;
	private final static Color BORDER = Color.BLACK;

	private final static int HIGHEST_I = 0; // the array position of the top
	private final static int LOWEST_I = 1;  // the array position of the bottom
	private final static int FRONT_I = 2;
	private final static int HEIGHT = 20;
	private final static int WIDTH = HEIGHT;
	private final Polygon shape;
	private Direction d;
	private Rectangle2D movementBounds;
	public int xVelocity = 0;
	public int yVelocity = 0;

	/**
	* This method constructs the Ship implementation
	* @param x the integer value for the Ship to be translated
	* @param y the integer value for the Ship to be translated
	*/
	public ShipImpl(int x, int y) {
		shape = new Polygon(
			new int[] {0,0,WIDTH}, //top left, bottom left, front middle
			new int[] {0,HEIGHT,HEIGHT/2}, 3);
		shape.translate(x, y);
		d = Direction.NONE;
	}

	/**
	* This method sets the direction of the Ship
	* @param d the Direction d to pass to the instance variable Direction d
	*/
	public void setDirection(Direction d) {
		this.d = d;
	}

	/**
	* This method set the boundaries in where the ship will move
	* @param movementBounds the boundaries of type Rectangle2D
	*/
	public void setMovementBounds(Rectangle2D movementBounds) {
		this.movementBounds = movementBounds;
	}

	/**
	* This method controls the behaviour which will prevent the ship to go throw the limits of the viewport
	*/
	public void move() {
		if (d == Direction.UP && shape.ypoints[0] > movementBounds.getMinY()) {
			shape.translate(xVelocity, yVelocity);
		}
		if (d == Direction.DOWN && shape.ypoints[1] < movementBounds.getMaxY()) {
			shape.translate(xVelocity, yVelocity);
		}
		if (d == Direction.RIGHT && shape.xpoints[2] < movementBounds.getMaxX()) {
			shape.translate(xVelocity, yVelocity);
		}
		if (d == Direction.LEFT && shape.xpoints[0] > movementBounds.getMinX()) {
				shape.translate(xVelocity, yVelocity);
		}
	}

	/**
	* This method draws the ship
	* @param g the graphics context
	*/
	public void draw(Graphics2D g) {
		g.setColor(BORDER);
		g.draw(shape);
		g.setColor(FILL);
		g.fill(shape);
	}

	/**
	* Retrieves the shape of the ship of type Shape
	* @return shape the actual shape of the ship
	*/
	public Shape getShape() {
		return shape;
	}

	/**
	* intersects verifies if the ship is colliding with any asteroid or any other object
	* if it actually collides with something that isn't of type Asteroid it throws an exception
	* @param other of type Sprite
	*/
	public boolean intersects(Sprite other) {
		if(other instanceof Asteroid) {
			return ((Asteroid)other).intersects(this);
		} else {
			throw new IllegalArgumentException("Not an asteroid");
		}
	}
}
