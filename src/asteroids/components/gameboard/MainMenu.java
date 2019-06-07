package asteroids.components.gameboard;

import asteroids.AsteroidsGUI;
import asteroids.components.gameitems.Asteroid;
import asteroids.components.gameitems.SpaceBackground;
import asteroids.listeners.MouseClickListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends JPanel {

    public static Color bgc = Color.decode("#240046");
    private static Timer timer = new Timer(17, new Animate());
    private static AsteroidsGUI gui;
    private static int counter = 0;
    private static SpaceBackground sbg;

    private static class Animate implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!gui.isRunning()) {
                if (counter == 0) {
                    counter = 100;
                    if (gui.getAsteroidList().size() < 10) {
                        gui.createAsteroid();
                    }
                } else {
                    counter--;
                }
                ArrayList<Asteroid> asteroidList = gui.getAsteroidList();
                for (int i = 0; i < gui.getAsteroidList().size(); i++) { //Don't change this to a for each
                    asteroidList.get(i).update(gui); //Updates state of game objects.
                }
                gui.getFrame().repaint();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        sbg.paintComponent(g2);
        ArrayList<Asteroid> asteroidList = gui.getAsteroidList();
        for (int i = 0; i < gui.getAsteroidList().size(); i++) { //Don't change this to a for each
            asteroidList.get(i).paintComponent(g2); //Updates state of game objects.
        }
    }

    public MainMenu(AsteroidsGUI gui) {
        //Menu creation
        this.setLayout(null);
        this.setBackground(AsteroidsGUI.bgc);
        sbg = new SpaceBackground(gui);
        MainMenu.gui = gui;

        BufferedImage before = null;
        try {
            before = ImageIO.read(new File("Images/title.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel title = new JLabel(new ImageIcon(before));
        title.setBounds(200, 100, 600, 200);
        this.add(title);
        
        MouseClickListener buttonPressed = new MouseClickListener(gui);

        JButton startButton = new JButton("S T A R T");
        startButton.setBounds(450, 350, 150, 50);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        startButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        startButton.addActionListener(buttonPressed);
        startButton.setActionCommand("start");

        JButton scoreButton = new JButton("S C O R E S");
        scoreButton.setBounds(450, 425, 150, 50);
        scoreButton.setBackground(Color.BLACK);
        scoreButton.setForeground(Color.WHITE);
        scoreButton.setOpaque(true);
        scoreButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scoreButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        scoreButton.addActionListener(buttonPressed);
        scoreButton.setActionCommand("score");

        JButton instructionsButton = new JButton("I N S T R U C T I O N S");
        instructionsButton.setBounds(400, 500, 250, 50);
        instructionsButton.setBackground(Color.BLACK);
        instructionsButton.setForeground(Color.WHITE);
        instructionsButton.setOpaque(true);
        instructionsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        instructionsButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        instructionsButton.addActionListener(buttonPressed);
        instructionsButton.setActionCommand("instructions");

        this.add(startButton);
        this.add(scoreButton);
        this.add(instructionsButton);
        timer.start();
    }
}
