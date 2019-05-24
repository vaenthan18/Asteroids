/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameitems;

import asteroids.AsteroidsGUI;
import asteroids.components.GameComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author vaenthan
 */
public class Player extends GameComponent {

	private double angle = 0;
	private int angularVelocity = 5;
	protected int velocity;
	protected int xSpeed;
	protected int ySpeed;
	private ArrayList<Integer> inputs = new ArrayList<>();
	protected Polygon shipBody = new Polygon();

	public Player(int x, int y, Color color, int velocity) {
		super(x, y, color);
		this.velocity = velocity;
	}

	public void updateMovementInputs(ArrayList<Integer> newInputs) {
		inputs = newInputs;
	}

	public void updatePoints() {
		shipBody.reset();
		shipBody.addPoint(x, y);
		shipBody.addPoint(x + 10, y + 30);
		shipBody.addPoint(x + 0, y + 20);
		shipBody.addPoint(x - 10, y + 30);
	}

	public void update(AsteroidsGUI gui) {
		xSpeed = 0;
		ySpeed = 0;
		if (inputs.contains(KeyEvent.VK_UP)) { //UP
			x += velocity * Math.cos(Math.toRadians(-angle - 90));
			y += velocity * Math.sin(Math.toRadians(-angle - 90));
		}
		if (inputs.contains(KeyEvent.VK_LEFT)) { //LEFT
			angle += angularVelocity;
		}
		if (inputs.contains(KeyEvent.VK_RIGHT)) { //RIGHT
			angle -= angularVelocity;
		}
		x += xSpeed;
		y += ySpeed;

		x = (x < 0 || x > 1000) ? Math.abs(x - 1000) : x;
		y = (y < 0 || y > 600) ? Math.abs(y - 600) : y;
	}

	public void paintComponent(Graphics2D g2d) {
		//System.out.println(angle);

		updatePoints();

		//angle = -135;
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(-angle), x, y);
		//Rectangle shape = new Rectangle(x + 10, y + 10, 100, 20);
		Shape newShape = tx.createTransformedShape(shipBody);
		g2d.setColor(Color.RED);
		g2d.fill(newShape);
	}
}