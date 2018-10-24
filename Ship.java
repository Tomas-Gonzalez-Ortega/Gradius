import java.awt.geom.Rectangle2D;

/** interface that implements a Direction method to control the direction of the ship inheriting everything from Sprite
@author Jeremy Hilliker @ Langara
@author Tomas Gonzalez Ortega
@version 2017-07-05 23h10
@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736">a 07: Gradius</a>
*/
public interface Ship extends Sprite {

	/**
	* 4 Directions plus a non direction distributed as a cartesian coordinates
	*/
	public enum Direction {
		NONE(0, 0), UP(0, -1), DOWN(0, 1), RIGHT(1, 0), LEFT(-1, 0);
		public final int dx;
		public final int dy;
		/**
		* anonymous method to pass a x and y values to the direction
		* @param int dx the x value of the direction
		* @param int dy the y value of the direction
		*/
		Direction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}
	};

	/**
	* Method to set up a direcion of type Direction of the ship
	* @param d the direction of the ship
	*/
	public void setDirection(Direction d);

	/**
	* Method to set up the movement restrictions of the ship type Rectangle2D
	* @param bounds the boundaries in where the ship can fly
	*/
	public void setMovementBounds(Rectangle2D bounds);
}
