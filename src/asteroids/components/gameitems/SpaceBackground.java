/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameitems;

import asteroids.AsteroidsGUI;
import asteroids.components.GameComponent;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SpaceBackground extends GameComponent implements Runnable {

    private int interval = 10; //Higher number = less stars
    private int rows;
    private int columns;
    private boolean running = false;
    private AsteroidsGUI gui;
    private boolean openStar = false;
    private Ellipse2D[][] stars;

    public void start() {
        Thread thread = new Thread(this);
        running = true;
        thread.start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            update(gui);
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }
        }
    }

    public SpaceBackground(AsteroidsGUI gui) {
        super(0, 0, Color.WHITE);

        this.gui = gui;
        int length = gui.getFrameLength();
        int height = gui.getFrameHeight();
        rows = length / interval;
        columns = height / interval;

        makeStars();
        start();
    }

    public void makeStars() {
        stars = new Ellipse2D[rows][columns];
        for (int i = 0; i < stars.length; i++) {
            for (int j = 0; j < stars[i].length; j++) {
                double diameter = Math.random() * 10 - 9;
                stars[i][j] = new Ellipse2D.Double(i * interval, j * interval, diameter, diameter);
            }
        }
    }

    public void flicker() {
        for (int i = 0; i < stars.length; i++) {
            for (int j = 0; j < stars[i].length; j++) {
                int random = (int) (Math.random() * 100);
                if (random < 3) {
                    double diameter = Math.random() * 10 - 6;
                    stars[i][j].setFrame(i * interval, j * interval, diameter, diameter);
                } else if (random < 20) {
                    stars[i][j].setFrame(i * interval, j * interval, 0, 0);
                }
            }
        }
    }

    public void update(AsteroidsGUI gui) {
        flicker();
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        for (int i = 0; i < stars.length; i++) {
            for (int j = 0; j < stars[i].length; j++) {
                g2.fill(stars[i][j]);
            }
        }
    }
}
