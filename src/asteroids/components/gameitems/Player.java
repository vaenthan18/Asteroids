/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameitems;

import asteroids.AsteroidsGUI;
import asteroids.components.GameComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author vaenthan
 */
public class Player extends GameComponent {

    private double angle = 0;
    private int angularVelocity = 5;
    protected double velocity;
    protected double xSpeed = 0;
    protected double ySpeed = 0;
    private ArrayList<Integer> inputs = new ArrayList<>();
    protected Path2D.Double shipBody = new Path2D.Double();
    protected double maxShootDelay = 10; //The lower the number, the higher the fire rate
    protected double shootDelay = 0; //Current shoot delay counter
    protected int lives = 3;
    protected int bombs = 2;
    private static BufferedImage img;
    private static File shipImg = new File("Images/ship.png");
	private static File shipImgMoving = new File("Images/shipfire.png");

    public Player(int x, int y, Color color, double velocity) {
        super(x, y, color);
        this.velocity = velocity;
        try {
            img = ImageIO.read(shipImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMovementInputs(ArrayList<Integer> newInputs) {
        inputs = newInputs;
    }

    public void updatePoints() {
        shipBody.reset();
        shipBody.moveTo(x, y);
        shipBody.lineTo(x + 8, y + 12);
        shipBody.lineTo(x + 16, y + 15);
        shipBody.lineTo(x + 16, y + 22);
        shipBody.lineTo(x + 10, y + 20);
        shipBody.lineTo(x + 6, y + 22);
        shipBody.lineTo(x + 2, y + 20);
        shipBody.lineTo(x, y + 21);
        shipBody.lineTo(x - 2, y + 20);
        shipBody.lineTo(x - 6, y + 22);
        shipBody.lineTo(x - 10, y + 20);
        shipBody.lineTo(x - 16, y + 22);
        shipBody.lineTo(x - 16, y + 15);
        shipBody.lineTo(x - 8, y + 12);
    }

    public void update(AsteroidsGUI gui) {
        collisionCheck(gui);
        if (inputs.contains(KeyEvent.VK_UP)) { //UP
            xSpeed += velocity * Math.cos(Math.toRadians(angle + 90));
            ySpeed += velocity * Math.sin(Math.toRadians(-angle - 90));
            double speed = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2));
            int speedLimit = 5;
            if (speed > speedLimit) {
                if (Math.abs(xSpeed) > Math.abs(speedLimit * Math.cos(Math.toRadians(angle + 90)))) {
                    xSpeed = speedLimit * Math.cos(Math.toRadians(angle + 90));
                }
                if (Math.abs(ySpeed) > Math.abs(speedLimit * Math.sin(Math.toRadians(-angle - 90)))) {
                    ySpeed = speedLimit * Math.sin(Math.toRadians(-angle - 90));
                }
            }
            try {
            	img = ImageIO.read(shipImgMoving);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				img = ImageIO.read(shipImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
            double a = (xSpeed == 0) ? 0 : (ySpeed == 0 ? Math.PI / 2 : Math.atan(xSpeed / ySpeed) + (ySpeed > 0 ? Math.PI : 0));
            double speed = Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2)) - velocity / 6;
            speed = (speed < velocity / 6) ? 0 : speed;
            xSpeed = speed * Math.cos(a + Math.PI / 2);
            ySpeed = speed * Math.sin(-a - Math.PI / 2);
        }
        if (inputs.contains(KeyEvent.VK_LEFT)) { //LEFT
            angle += angularVelocity;
        }
        if (inputs.contains(KeyEvent.VK_RIGHT)) { //RIGHT
            angle -= angularVelocity;
        }
        if (inputs.contains(KeyEvent.VK_SPACE)) { //New Shot fired
            if (shootDelay <= 0) {
                Bullet bullet = new Bullet(x, y, Color.WHITE, angle);
                gui.addPlayerBullet(bullet);
                shootDelay = maxShootDelay;
            }
        }
        if (shootDelay > 0) {
            setShootDelay(getShootDelay() - 1);
        }

        x += xSpeed;
        y += ySpeed;

        x = (x < 0 || x > 1000) ? Math.abs(x - 1000) : x;
        y = (y < 0 || y > 600) ? Math.abs(y - 600) : y;
    }

    public void collisionCheck(AsteroidsGUI gui) {
        Area shipArea;
        Area asteroidArea;
        Area powerupArea;
        Asteroid asteroid;
        Powerups powerup;
        ArrayList<Asteroid> asteroidList = gui.getAsteroidList();
        ArrayList<Powerups> powerupsList = gui.getPowerupsList();
        boolean hasCollided = false;

        for (int i = 0; i < asteroidList.size(); i++) {
            asteroid = asteroidList.get(i);
            asteroidArea = new Area((Shape) asteroid.getBody());
            shipArea = new Area((Shape) shipBody);

            shipArea.intersect(asteroidArea);
            hasCollided = !shipArea.isEmpty();
            if (hasCollided) {
                asteroid.shatter(gui);
                asteroidList.remove(asteroid);
                lives--;
                System.out.println("Lives: " + lives);
                break;
            }
        }
        for (int i = 0; i < powerupsList.size(); i++) {
            powerup = powerupsList.get(i);
            powerupArea = new Area((Shape) powerup.getBody());
            shipArea = new Area((Shape) shipBody);
            shipArea.intersect(powerupArea);
            hasCollided = !shipArea.isEmpty();
            if (hasCollided) {
                powerupsList.remove(powerup);
                if (powerup.getType().equals("Health")) {
                    lives++;
                } else if (powerup.getType().equals("Bomb")) {
                    bombs++;
                }
            }
        }
    }

    public void setShootDelay(double newDelay) {
        shootDelay = newDelay;
    }

    public double getShootDelay() {
        return shootDelay;
    }

    public void paintComponent(Graphics2D g2d) {
        updatePoints();

		AffineTransform tx = new AffineTransform();
		AffineTransform a = new AffineTransform();
		//tx.rotate(Math.toRadians(-angle), x, y);
		tx.translate(-img.getWidth() / 2.0, 0);
		tx.rotate(Math.toRadians(-angle), img.getWidth() / 2.0, 0);
		a.rotate(Math.toRadians(-angle), x, y);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        Shape newShape = a.createTransformedShape(shipBody);
        shipBody = (Path2D.Double) newShape;
        g2d.setColor(Color.BLACK);
		g2d.drawImage(img, op, (int)x, (int)y);
    }
}
