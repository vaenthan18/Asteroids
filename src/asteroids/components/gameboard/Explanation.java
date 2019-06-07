/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameboard;

import asteroids.AsteroidsGUI;
import asteroids.listeners.MouseClickListener;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author vaenthan
 */
public class Explanation extends JPanel{

    public static Color bgc = Color.decode("#000c28");

    public Explanation(AsteroidsGUI gui) {
        //Menu creation
        this.setLayout(null);
        this.setBackground(bgc);

        //title
        JLabel title = new JLabel("Explanation", SwingConstants.CENTER);
        title.setBounds(300, 25, 400, 50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Helvetica", Font.BOLD, 40));

        //buttons - Return BUtton
        MouseClickListener buttonPressed = new MouseClickListener(gui);
        JButton scoreButton = new JButton("RETURN");
        scoreButton.setBounds(450, 500, 100, 50);
        scoreButton.setBackground(Color.BLACK);
        scoreButton.setForeground(Color.WHITE);
        scoreButton.setOpaque(true);
        scoreButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scoreButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
        scoreButton.addActionListener(buttonPressed);
        scoreButton.setActionCommand("return");

        //adding to board
        this.add(scoreButton);
        this.add(title);

        //Explanation JLabel
        JTextArea explain = new JTextArea("Welcome to Asteroids\n" +
                "This is a game where you shoot asteroids for points!\n" +
                "Press spacebar to shoot regular bullets!\n" +
                "Each asteroid has a different point value and different sizes\n" +
                "When you break a large one, it might split into smaller ones!\n" +
                "Press the arrow keys to move, except down arrow\n" +
                "Press the down arrow to shoot a bomb, which pierces enemies and deals massive damage!\n" +
                "Watch out for aliens - they can shoot you!\n" +
                "Have fun!");
        explain.setBounds(200, 100, 600, 300);
        explain.setBackground(bgc);
        explain.setForeground(Color.WHITE);
        explain.setOpaque(false);
        explain.setFont(new Font("Helvetica", Font.PLAIN, 20));

        this.add(explain);

    }
}