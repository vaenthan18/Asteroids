/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameitems;
import asteroids.AsteroidsGUI;
import asteroids.components.GameComponent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author vaenthan
 */
public class Bullet extends GameComponent {

    protected double angle;
    protected double velocity = 2;
    protected double length = 15;
    protected double width = 15;
    protected Ellipse2D.Double bulletBody;
    
    public Bullet(double x, double y, Color color, double angle) {
        super(x, y, color);
        this.angle = angle;
    }
    
    public void update(AsteroidsGUI gui) {
        //Currently no acceleration in bullets.
        x += velocity * Math.cos(Math.toRadians(-angle - 90));
        y += velocity * Math.sin(Math.toRadians(-angle - 90));
        
        if ((x < 0 || x > 1000) | (y < 0 || y > 600)) {
            //Checks when a bullet goes off screen. 
            //Removes it from bulletlist when that happens.
            //This makes sure there aren't a billion useless bullets in bulletList
            gui.removePlayerBullet(this);
        }
        bulletBody = new Ellipse2D.Double(x - length / 2, y - width / 2, length, width);
    }
    
    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(bulletBody);
    }
}
