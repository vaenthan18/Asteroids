package asteroids.components.gameboard;


import asteroids.AsteroidsGUI;
import asteroids.listeners.MouseClickListener;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    public static Color bgc = Color.decode("#240046");

    public MainMenu(JFrame frame, AsteroidsGUI gui) {
        //Menu creation
        this.setLayout(null);
        this.setBackground(bgc);

        //title creation
        JLabel title = new JLabel("ASTEROIDS", SwingConstants.CENTER);
        title.setBounds(350, 100, 300, 100);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier", Font.PLAIN, 55));

        //button functionality added
        MouseClickListener buttonPressed = new MouseClickListener(gui);

        JButton startButton = new JButton("START");
        startButton.setBounds(450, 350, 100, 50);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        startButton.setFont(new Font("Courier", Font.PLAIN, 20));
        startButton.addActionListener(buttonPressed);
        startButton.setActionCommand("start");

        JButton scoreButton = new JButton("SCORES");
        scoreButton.setBounds(450, 425, 100, 50);
        scoreButton.setBackground(Color.BLACK);
        scoreButton.setForeground(Color.WHITE);
        scoreButton.setOpaque(true);
        scoreButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scoreButton.setFont(new Font("Courier", Font.PLAIN, 20));
        scoreButton.addActionListener(buttonPressed);
        scoreButton.setActionCommand("score");

        JLabel shopButton = new JLabel("Shop");

        //adding components to the JPanel
        this.add(startButton);
        this.add(scoreButton);
        this.add(title);
    }
}
