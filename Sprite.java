import java.awt.*;

/**
* interface Sprite to draw, move, get the shape and check if it intersects
*/
public interface Sprite {
	public void draw(Graphics2D g);
	public void move();
	public Shape getShape(); // used by intersects
	public boolean intersects(Sprite other);
}
