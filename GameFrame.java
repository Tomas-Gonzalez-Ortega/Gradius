import javax.swing.JFrame;
import java.awt.*;

/** A class to create the fram that contains the game
@author Jeremy Hilliker @ Langara
@author Tomas Gonzalez Ortega
@version 2017-07-05 23h10
@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736">a 07: Gradius</a>
*/
public class GameFrame extends JFrame {

	private final static int WIDTH = 900;
	public final static int HEIGHT = 700;

	private final GameComponent comp;

	/**
	* Constructs and add the component to a non resizable frame
	*/
	public GameFrame() {
		setResizable(false);
		comp = new GameComponent();
		add(comp);
	}

	/**
	* Creates a frame window, that works as a canvas to display the game
	* @param args line arguments -- ignored in this assignment
	*/
	public static void main(String[] args) {
		GameFrame frame = new GameFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.comp.start();
	}
}
