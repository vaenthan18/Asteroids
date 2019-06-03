package asteroids.listeners;

import asteroids.AsteroidsGUI;
import asteroids.components.gameitems.Player;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MoveListener extends KeyAdapter {

    private final AsteroidsGUI gui;
    private ArrayList<Integer> keysPressed = new ArrayList<>();

    public MoveListener(AsteroidsGUI gui) {
        this.gui = gui;
        addKeyListener(this);
    }

    public void keyPressed(KeyEvent k) {
        if (!keysPressed.contains(k.getKeyCode())) {
            keysPressed.add(k.getKeyCode());
        }

        gui.player.updateMovementInputs(keysPressed);
    }

    public void keyReleased(KeyEvent k) {
        if (keysPressed.contains(k.getKeyCode())) {
            keysPressed.remove(keysPressed.indexOf(k.getKeyCode()));
        }
        gui.player.updateMovementInputs(keysPressed);
    }
}
