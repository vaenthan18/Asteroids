/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameitems;

import asteroids.AsteroidsGUI;
import asteroids.components.GameComponent;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

/**
 * @author vaenthan
 */
public class Alien extends GameComponent {

	private static AsteroidsGUI gui;
	private static Path2D.Double alienBody = new Path2D.Double();
	protected double velocity = 3;
	private double shootDelay = 50;

	public Alien(double x, double y, Color color, AsteroidsGUI gui) {
		super(x, y, color);
		this.gui = gui;
	}

	public void updatePoints() {
		alienBody.reset();
		alienBody.moveTo(x - 20, y - 20);
		alienBody.lineTo(x + 20, y - 20);
		alienBody.lineTo(x + 30, y);
		alienBody.lineTo(x + 20, y + 20);
		alienBody.lineTo(x - 20, y + 20);
		alienBody.lineTo(x - 30, y);
	}

	public void move() { //Don't forget to put this in the update method
		x += velocity;
	}

	public void update(AsteroidsGUI at) {
		move();
		updatePoints();
		if (shootDelay >= 0) {
			shootDelay--;
		} else {
			double angle = ((gui.player.getX() - x) == 0) ? 0 : ((gui.player.getY() - y) == 0 ? (gui.player.getX() - x > 0 ? -Math.PI / 2 : Math.PI / 2) : Math.atan((gui.player.getX() - x) / (gui.player.getY() - y)) + ((gui.player.getY() - y) > 0 ? Math.PI : 0));
			Bullet bullet = new Bullet(x, y, Color.RED, Math.toDegrees(angle));
			gui.addAlienBullet(bullet);
			shootDelay = 50;
		}
	}


	public void paintComponent(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.draw(alienBody);
		g2.draw(new Area((Shape) alienBody));
	}
}
