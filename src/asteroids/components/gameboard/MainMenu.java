package asteroids.components.gameboard;


import asteroids.AsteroidsGUI;
import asteroids.listeners.MouseClickListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    public static Color bgc = Color.decode("#240046");

    public MainMenu(JFrame frame, AsteroidsGUI gui) {
        //Menu creation
        this.setLayout(null);
        this.setBackground(Color.getColor("#"));

		BufferedImage before = null;
		try {
			before = ImageIO.read(new File("Images/title.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel title = new JLabel(new ImageIcon(before));
		title.setBounds(200, 100, 600, 200);
		this.add(title);
/*
        JLabel title = new JLabel("ASTEROIDS", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier", Font.PLAIN, 55));
*/
        MouseClickListener buttonPressed = new MouseClickListener(gui);

        JButton startButton = new JButton("START");
        startButton.setBounds(450, 350, 100, 50);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        startButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        startButton.addActionListener(buttonPressed);
        startButton.setActionCommand("start");

        JButton scoreButton = new JButton("SCORES");
        scoreButton.setBounds(450, 425, 100, 50);
        scoreButton.setBackground(Color.BLACK);
        scoreButton.setForeground(Color.WHITE);
        scoreButton.setOpaque(true);
        scoreButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scoreButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        scoreButton.addActionListener(buttonPressed);
        scoreButton.setActionCommand("score");

        JLabel shopButton = new JLabel("Shop");

        this.add(startButton);
        this.add(scoreButton);
    }
}
