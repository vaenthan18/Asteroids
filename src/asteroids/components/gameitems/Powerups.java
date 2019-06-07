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
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Powerups extends GameComponent {

    protected double x;
    protected double y;
    protected Color color;
    protected double radius;
    protected Ellipse2D.Double powerupBody = new Ellipse2D.Double(x, y, radius, radius);
    protected PowerupType type;
    protected Boolean collided = false;
    protected BufferedImage image = null;

    public Powerups(double x, double y, Color color, double radius, PowerupType type) {
        super(x, y, color);
        this.x = x;
        this.y = y;
        this.color = color;
        this.radius = radius;
        this.type = type;
        try {
            if (type == PowerupType.BOMB) {
                image = ImageIO.read(new File("Images/bomb.png"));
            } else if (type == PowerupType.SHIELD) {
                image = ImageIO.read(new File("Images/shield.png"));
            } else {
                image = ImageIO.read(new File("Images/health.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(AsteroidsGUI gui) {
        powerupBody = new Ellipse2D.Double(x, y, radius, radius);
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.draw(powerupBody);
        g2.draw(new Area(powerupBody));

        AffineTransform tx = new AffineTransform();
        tx.scale(radius / image.getWidth(), radius / image.getHeight());
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2.drawImage(image, op, (int) x, (int) y);
    }

    public Ellipse2D getBody() {
        return powerupBody;
    }

    public PowerupType getType() {
        return type;
    }
}
