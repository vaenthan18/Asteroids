/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids.components.gameitems;

import asteroids.components.GameComponent;
import asteroids.AsteroidsGUI;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
/**
 *
 * @author vaenthan
 */
public class Powerups extends GameComponent {

    protected double x;
    protected double y;
    protected Color color;
    protected double radius;
    protected Ellipse2D.Double powerupBody = new Ellipse2D.Double(x, y, radius, radius);
    protected String type;
    protected Boolean collided = false;

    public Powerups(double x, double y, Color color, double radius, String type) {
        super(x, y, color);
        this.x = x;
        this.y = y;
        this.color = color;
        this.radius = radius;
        this.type = type;
    }

    public void update(AsteroidsGUI gui) {

    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.draw(powerupBody);
        g2.setColor(Color.GREEN);
        g2.draw(new Area((Shape) powerupBody));
        //System.out.println("paintin!");
    }

    public Ellipse2D getBody() {
        return powerupBody;
    }

    public String getType() {
        return type;
    }
}
