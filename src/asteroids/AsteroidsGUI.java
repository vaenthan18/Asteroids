package asteroids;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import asteroids.components.AsteroidsPanel;
import asteroids.components.GameComponent;
import asteroids.listeners.MoveListener;
import asteroids.components.gameitems.Player;

public final class AsteroidsGUI extends JPanel implements Runnable {

	private JFrame frame = new JFrame("Asteroids");
	private JPanel mainPanel = new JPanel(); //Layout
	private ArrayList<GameComponent> components = new ArrayList<>();
	public Player player = new Player(500, 300, Color.WHITE, 1.5);
	private boolean running = false;

	public void start() {
		Thread thread = new Thread(this);
		running = true;
		thread.start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			// Check for collision, draw objects and sleep
			for (GameComponent i : components) {
				i.update(this); //Updates state of game objects.
			}
			repaint(); //Draws objects
			try {
				Thread.sleep(17);
			} catch (Exception e) {
			}
		}
	}

	public AsteroidsGUI() {
		// Add the plane and control panels to the main panel
		//JPanel mainPanel = new JPanel();
		//mainPanel.add(space);
		components.add(player);
		// Configure the frame
		frame.addKeyListener(new MoveListener(this));
		frame.setVisible(true);
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setFocusable(true);
		start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (GameComponent i : components) {
			i.paintComponent(g2);
		}
	}

	public static void main(String[] args) {
		AsteroidsGUI gui = new AsteroidsGUI(); //MAIN HERE
                System.out.println("Git test 123");
	}
}
