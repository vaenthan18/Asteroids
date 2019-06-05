/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.listeners;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import asteroids.AsteroidsGUI;

/**
 *
 * @author vaenthan
 */
public class MouseClickListener implements ActionListener {

    private final AsteroidsGUI gui;

    public MouseClickListener (AsteroidsGUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if ("start".equals(e.getActionCommand())) {
            gui.start();
            System.out.println("starting!");
        } else if ("score".equals(e.getActionCommand())) {
            gui.makeScoreboard();
            System.out.println("haha not implemented yet");
        } else if ("return".equals(e.getActionCommand())) {
            gui.makeMenu();
        }
    }
}
