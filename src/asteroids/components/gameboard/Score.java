/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameboard;

import asteroids.AsteroidsGUI;
import asteroids.listeners.MouseClickListener;

import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {

    public static Color bgc = Color.decode("#000c28");
    public static String[][] scoreArray = new String[10][2];
    public static JLabel[][] jlabelArray = new JLabel[10][2];

    public Score(AsteroidsGUI gui) {
        //Menu creation
        this.setLayout(null);
        this.setBackground(bgc);

        //title
        JLabel title = new JLabel("SCOREBOARD", SwingConstants.CENTER);
        title.setBounds(300, 25, 400, 50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Helvetica", Font.BOLD, 40));

        //buttons
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

        //actual scoreboard
        for (int i = 0; i < 10; i++) {
            scoreArray[i][0] = asteroids.AsteroidsGUI.getScoreData().get(i).getName();
            scoreArray[i][1] = Integer.toString(asteroids.AsteroidsGUI.getScoreData().get(i).getScore());
            for (int j = 0; j < 2; j++) {
                jlabelArray[i][j] = new JLabel(scoreArray[i][j], SwingConstants.CENTER);
                jlabelArray[i][j].setBounds(250 + j * 250, 85 + i * 40, 250, 40);
                jlabelArray[i][j].setBackground(bgc);
                jlabelArray[i][j].setForeground(Color.WHITE);
                jlabelArray[i][j].setOpaque(true);
                jlabelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                jlabelArray[i][j].setFont(new Font("Helvetica", Font.PLAIN, 16));
                this.add(jlabelArray[i][j]);
            }
        }

    }
}
