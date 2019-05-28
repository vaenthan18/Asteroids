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
    protected double velocity = 5;
    protected double length = 10;
    protected double width = 10;
    
    public Bullet(double x, double y, Color color, double angle) {
        super(x, y, color);
        this.angle = angle;
    }
    
    
    public void update(AsteroidsGUI gui) {
        x += velocity * Math.cos(Math.toRadians(-angle - 90));
        y += velocity * Math.sin(Math.toRadians(-angle - 90));
        
        if ((x < 0 || x > 1000) | (y < 0 || y > 600)) {
            gui.removePlayerBullet(this);
        }
    }
    
    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.draw(new Ellipse2D.Double(x - length / 2, y - width / 2, length, width));
    }
}
