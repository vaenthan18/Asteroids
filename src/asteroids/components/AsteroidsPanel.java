package asteroids.components;

import asteroids.AsteroidsGUI;
import asteroids.components.gameitems.Player;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;

public class AsteroidsPanel extends JPanel {
/*
	public final AsteroidsGUI gui;
	public Player player;

	public AsteroidsPanel(AsteroidsGUI gui) {
		this.gui = gui;
		this.components = new LinkedList<>();

		addPlayer();
	}

	public void addPlayer() {
		player = new Player(gui);
		components.add(player);
	}

	@Override
	public void paintComponent(Graphics graphics) {
		// Complete me
		super.paintComponent(graphics);
		addPlayer();
		try {
			graphics.drawImage(ImageIO.read(new File("Images/background.jpg")), 0, 0, null);
			for (GameComponent a : components) {
				a.draw((Graphics2D) graphics);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
