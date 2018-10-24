import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/** A component that develops a Gradius type video game
@author Jeremy Hilliker @ Langara
@author Tomas Gonzalez Ortega
@version 2017-07-05 23h10
@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736">a 07: Gradius</a>
*/
public class GameComponent extends JComponent {

	private ShipImpl ship;
	private Timer timer;
	private Timer timer2;
	HashSet<Asteroid> asteroids;
	AsteroidFactory asteroidGenerator;
	private boolean isGameOver;
	ShipKeyListener keyListener;

	/**
	* Constructs a new GameComponent with a ship, a couple of timers (60 and 15 fps)
	* an Asteroid generator
	* It also attachs a key listener to the Component and a collection of asteroids
	*/
	public GameComponent() {
		ship = new ShipImpl(10, getHeight() / 3);
		timer = new Timer(1000 / 60, (al) -> {this.update();});
		timer2 = new Timer(1000 / 4, (al) -> {makeAsteroid();});
		asteroidGenerator = asteroidGenerator.getInstance();
		keyListener = new ShipKeyListener();
		addKeyListener(keyListener);
		asteroids = new HashSet<Asteroid>();
	}

	/**
	* Parsing the g component into Graphics2D
	* Setting the Rendering hints
	* @param g the graphics context
	*/
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
		RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintComponent(g2);
	}

	/**
	* This method draws the ship and the asteroids
	* When the game is over takes the screen to black and displays the Game Over message
	* @param g the graphics context of type Graphics2D
	*/
	private void paintComponent(Graphics2D g) {
		ship.draw(g);
		for (Asteroid index : asteroids)
		{
			index.draw(g);
		}
		if(isGameOver) {
			int fontSize = 100;
			g.setColor(Color.BLACK);
    		g.fillRect(0, 0, 900, 700);
			g.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
			g.setColor(Color.GREEN);
			g.drawString("GAME OVER", getWidth() / 4, getHeight() / 2);
		}
	}

	/**
	* This method starts the instance, the bounds of the view port displayed
	* The bounds in where the movements of the objects will be contained
	* and the start of the timers
	*/
	public void start() {
		AsteroidFactory whatever = AsteroidFactory.getInstance();
		whatever.setStartBounds(900,0,700); // 0 is used twice
		ship.setMovementBounds(new Rectangle(0, 0, 900, 675));
		timer.start();
		timer2.start();
		//makeAsteroid();
	}

	/**
	* This inner class provides methods for the ship to move
	* it triggers a move when the keys Up, down, right, left, w, s, d and a are pressed
	* Basically it will move the ship into that direction with a fixed velocity
	*/
	public class ShipKeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_KP_UP:
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
				ship.yVelocity = -2;
				ship.setDirection(Ship.Direction.UP);
				break;

				case KeyEvent.VK_KP_DOWN:
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
				ship.yVelocity = 2;
				ship.setDirection(Ship.Direction.DOWN);
				break;

				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
				ship.xVelocity = 2;
				ship.setDirection(Ship.Direction.RIGHT);
				break;

				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
				ship.xVelocity = -2;
				ship.setDirection(Ship.Direction.LEFT);
				//SoundEffect.BACKWARD.play();
				break;
				default:
			}
		}

		/**
		* This method provides methods to stop moving the ship whenever the keys Up, down, right, left, w, s, d and a keys are released
		* Whenever this happens the ship speed is set to 0 and it stop moving
		*/
		public void keyReleased(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_KP_UP:
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
				ship.yVelocity = 0;
				ship.setDirection(Ship.Direction.NONE);
				break;

				case KeyEvent.VK_KP_DOWN:
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
				ship.yVelocity = 0;
				ship.setDirection(Ship.Direction.NONE);
				break;

				case KeyEvent.VK_KP_RIGHT:
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
				ship.yVelocity = 0;
				ship.setDirection(Ship.Direction.NONE);
				break;

				case KeyEvent.VK_KP_LEFT:
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
				ship.yVelocity = 0;
				ship.setDirection(Ship.Direction.NONE);
				break;
				default:
			}
		}
	}

	/**
	* This method calls another methods to implement the Window(to get the key events), the movement of the ship and the asteroids
	* If a collision is detected promps the Game over method else keeps repainting just like in an animation
	*/
	private void update() {
		requestFocusInWindow();
		//play();
		ship.move();
		moveAsteroids();
		if(checkCollisions()) {
			gameOver();
		}
		repaint();
	}

	/**
	* Creates a single asteroid and add it to a set of asteroids
	*/
	public void makeAsteroid() {
		asteroids.add(asteroidGenerator.makeAsteroid());
	}

	/**
	* Iterates through the Asteroid collection and move them
	* Whenever one of them is not visible anymore removes it
	*/
	public void moveAsteroids() {
		/*for (Asteroid index : asteroids)
		{
			index.move();
		}*/
		Iterator<Asteroid> it = asteroids.iterator();
		while (it.hasNext()) {
			Asteroid unit = it.next();
			unit.move();
			if(!unit.isVisible()) {
				it.remove();
			}
		}
	}

	/**
	* Checks if any of the asteroids collide the ship
	* @return false if there isn't any collision
	* @return true if any collision is present
	*/
	public boolean checkCollisions() {
		Iterator<Asteroid> it = asteroids.iterator();
		while (it.hasNext()) {
			Asteroid unit = it.next();
			if(unit.intersects(ship)) {
				return true;
			}
		}
		return false;
	}

	/**
	* Game over method stops the timers (so it wont repaint anymore)
	* sets Game Over to true (triggering the Game Over message)
	* and it removes the key listeners so the keys are no longer active
	*/
	public void gameOver() {
		timer.stop();
		timer2.stop();
		isGameOver = true;
		removeKeyListener(keyListener);
		//volume = volume.MUTE;
	}
/* TRIED TO MAKE THE JAVA SOUND API WORK... didn't work for me.
	/**
	*@see https://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
	* This enum encapsulates all the sound effects of a game, so as to separate the sound playing
	* codes from the game codes.
	* 1. Define all your sound effect names and the associated wave file.
	* 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
	* 3. You might optionally invoke the static method SoundEffect.init() to pre-load all the
	*    sound files, so that the play is not paused while loading the file for the first time.
	* 4. You can use the static variable SoundEffect.volume to mute the sound.
	* /
	public enum SoundEffect {
		BACKWARD("backward.wav"),   // going backwards
		COLLISION("collision.wav"), // collision sound
		FORWARD("forward.wav"),    // going forward
		START("start.wav");         // initial sound

		// Nested class for specifying volume
		public static enum Volume {
			MUTE, LOW, MEDIUM, HIGH
		}

		public static Volume volume = Volume.LOW;

		// Each sound effect has its own clip, loaded with its own sound file.
		private Clip clip;

		// Constructor to construct each element of the enum with its own sound file.
		SoundEffect(String soundFileName) {
			try {
				// Use URL (instead of File) to read from disk and JAR.
				URL url = this.getClass().getClassLoader().getResource(soundFileName);
				// Set up an audio input stream piped from the sound file.
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
				// Get a clip resource.
				clip = AudioSystem.getClip();
				// Open audio clip and load samples from the audio input stream.
				clip.open(audioInputStream);
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}

		// Play or Re-play the sound effect from the beginning, by rewinding.
		public void play() {
			if (volume != Volume.MUTE) {
				if (clip.isRunning())
				clip.stop();   // Stop the player if it is still running
				clip.setFramePosition(0); // rewind to the beginning
				clip.start();     // Start playing
			}
		}

		// Optional static method to pre-load all the sound files.
		static void init() {
			values(); // calls the constructor for all the elements
		}
	} */
}
