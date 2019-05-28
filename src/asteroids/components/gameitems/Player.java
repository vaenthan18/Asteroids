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
import java.awt.geom.Path2D;
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
    protected double maxShootDelay = 15; //The lower the number, the higher the fire rate
    protected double shootDelay = 0; //Current shoot delay counter
    
    public Player(int x, int y, Color color, double velocity) {
        super(x, y, color);
        this.velocity = velocity;
    }

    public void updateMovementInputs(ArrayList<Integer> newInputs) {
        inputs = newInputs;
    }

    public void updatePoints() {
        shipBody.reset();
        shipBody.moveTo(x, y);
        shipBody.lineTo(x + 10, y + 30);
        shipBody.lineTo(x + 0, y + 20);
        shipBody.lineTo(x - 10, y + 30);
    }

    public void update(AsteroidsGUI gui) {
        if (inputs.contains(KeyEvent.VK_UP)) { //UP
            xSpeed += velocity * Math.cos(Math.toRadians(angle + 90));
            ySpeed += velocity * Math.sin(Math.toRadians(-angle - 90));
        } else {
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
                gui.addPlayerBullet(new Bullet(x, y, Color.BLACK, angle));
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
    
    public void setShootDelay(double newDelay) {
        shootDelay = newDelay;
    }
    
    public double getShootDelay() {
        return shootDelay;
    }

    public void paintComponent(Graphics2D g2d) {
        updatePoints();

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(-angle), x, y);
        Shape newShape = tx.createTransformedShape(shipBody);
        g2d.setColor(Color.RED);
        g2d.fill(newShape);
    }
}
