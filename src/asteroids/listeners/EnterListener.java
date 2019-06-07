package asteroids.listeners;

import asteroids.AsteroidsGUI;
import asteroids.ScoreData;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class EnterListener implements ActionListener {

    private final AsteroidsGUI gui;

    public EnterListener(AsteroidsGUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        //upon clicking enter, we will get the text from the name and get the current score, then add it to scoreboard
		System.out.println("hi");
        AsteroidsGUI.getScoreData().add(new ScoreData(asteroids.components.gameboard.ScoreMenu.getText(), AsteroidsGUI.getPlayerData()[2]));
        Collections.sort(asteroids.AsteroidsGUI.getScoreData());
        try {
            gui.printScores();
            gui.makeScoreboard();
        } catch(Exception ex) {
        }
    }
}
