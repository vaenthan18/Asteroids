package asteroids.listeners;

import asteroids.AsteroidsGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import asteroids.ScoreData;
import asteroids.components.gameboard.ScoreMenu;

public class EnterListener implements ActionListener {

    private final AsteroidsGUI gui;

    public EnterListener (AsteroidsGUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        //upon clicking enter, we will get the text from the name and get the current score, then add it to scoreboard
        gui.getScoreData().add(new ScoreData(asteroids.components.gameboard.ScoreMenu.getText(), gui.getPlayerData()[2]));
        Collections.sort(asteroids.AsteroidsGUI.getScoreData());
        gui.printScores();
        gui.makeScoreboard();
    }
}
