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

/**
 *
 * @author vaenthan
 */
public class Score extends JPanel{
    public static Color bgc = Color.decode("#240046");

    public Score(JFrame frame, AsteroidsGUI gui) {
        //Menu creation
        this.setLayout(null);
        this.setBackground(bgc);

        JLabel title = new JLabel("SCOREBOARD", SwingConstants.CENTER);
        title.setBounds(300, 25, 400, 50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier", Font.PLAIN, 40));

        MouseClickListener buttonPressed = new MouseClickListener(gui);

        JButton scoreButton = new JButton("RETURN");
        scoreButton.setBounds(450, 500, 100, 50);
        scoreButton.setBackground(Color.BLACK);
        scoreButton.setForeground(Color.WHITE);
        scoreButton.setOpaque(true);
        scoreButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scoreButton.setFont(new Font("Courier", Font.PLAIN, 20));
        scoreButton.addActionListener(buttonPressed);
        scoreButton.setActionCommand("return");

        this.add(scoreButton);
        this.add(title);
    }
}
