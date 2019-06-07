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
import java.util.ArrayList;

public class Asteroid extends GameComponent {

    protected double maxHealth;
    protected double health;
    protected double radius;
    protected Ellipse2D.Double asteroidBody = new Ellipse2D.Double(x, y, radius, radius);
    protected double angle;
    protected double rotation = 0;
    protected double velocity;
    private BufferedImage asteroidImg;

    public Asteroid(double x, double y, Color color, double angle, double maxHealth, double radius, double velocity) {
        super(x, y, color);
        this.angle = angle;
        this.radius = radius;
        this.maxHealth = maxHealth;
        this.velocity = velocity;
        health = maxHealth;
        try {
            asteroidImg = ImageIO.read(new File("Images/asteroid.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(AsteroidsGUI gui) {
        collisionCheck(gui);
        move();
        if (isDead()) {
            shatter(gui);
            gui.removeAsteroid(this);
            if (maxHealth == 5) {
                asteroids.AsteroidsGUI.getPlayerData()[2] += 500;
            } else if (maxHealth == 3) {
                asteroids.AsteroidsGUI.getPlayerData()[2] += 300;
            } else if (maxHealth == 1) {
                asteroids.AsteroidsGUI.getPlayerData()[2] += 100;
            }
            int randomInt = (int) (Math.random() * 10 + 1);
            if (randomInt == 1 && gui.getPowerupsList().size() < 2) {
                gui.addPowerup(new Powerups(x, y, AsteroidsGUI.bgc, 35, PowerupType.HEALTH));
            } else if (randomInt == 2 && gui.getPowerupsList().size() < 2) {
                gui.addPowerup(new Powerups(x, y, AsteroidsGUI.bgc, 35, PowerupType.BOMB));
            } else if (randomInt == 3 && gui.getPowerupsList().size() < 2) {
                gui.addPowerup(new Powerups(x, y, AsteroidsGUI.bgc, 35, PowerupType.SHIELD));
            }
        }

        x = (x < 0 || x > 1000) ? Math.abs(x - 1000) : x;
        y = (y < 0 || y > 600) ? Math.abs(y - 600) : y;
        asteroidBody = new Ellipse2D.Double(x, y, radius, radius);
    }

    public void shatter(AsteroidsGUI gui) {
        if (maxHealth > 1) {
            gui.addAsteroid(new Asteroid(x, y - 10, AsteroidsGUI.bgc, angle + 10, maxHealth - 2, radius / 2, velocity + 0.5));
            gui.addAsteroid(new Asteroid(x, y + 10, AsteroidsGUI.bgc, angle - 10, maxHealth - 2, radius / 2, velocity + 0.5));
        }
    }

    public Ellipse2D getBody() {
        return asteroidBody;
    }

    public void collisionCheck(AsteroidsGUI gui) {
        Area asteroidArea;
        Area bulletArea;
        Bullet bullet;
        ArrayList<Bullet> bulletList = gui.getBulletList();
        Bomb bomb;
        Area bombArea;
        ArrayList<Bomb> bombList = gui.getBombList();
        boolean hasCollided = false;

        for (int i = 0; i < bulletList.size(); i++) {
            bullet = bulletList.get(i);
            bulletArea = new Area(bullet.getBody());
            asteroidArea = new Area(asteroidBody);

            asteroidArea.intersect(bulletArea);
            hasCollided = !asteroidArea.isEmpty();
            if (hasCollided) {
                bulletList.remove(bullet);
                health--;
                break;
            }
        }
        for (int i = 0; i < bombList.size(); i++) {
            bomb = bombList.get(i);
            bombArea = new Area(bomb.getBody());
            asteroidArea = new Area(asteroidBody);

            asteroidArea.intersect(bombArea);
            hasCollided = !asteroidArea.isEmpty();
            if (hasCollided) {
                health = 0;
                break;
            }
        }
    }

    public boolean isDead() {
        return health == 0;
    }

    public int healthValue() {
        return (int) maxHealth;
    }

    public void move() { //Don't forget to put this in the update method
        x += velocity * Math.cos(Math.toRadians(-angle - 90));
        y += velocity * Math.sin(Math.toRadians(-angle - 90));
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(color);
        g2.draw(asteroidBody);
        g2.draw(new Area(asteroidBody));
        AffineTransform tx = new AffineTransform();
        tx.scale(radius / asteroidImg.getWidth(), radius / asteroidImg.getHeight());
        tx.rotate(Math.toRadians(rotation), asteroidImg.getWidth() / 2.0, asteroidImg.getHeight() / 2.0);
        rotation += Math.toRadians(45);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2.drawImage(asteroidImg, op, (int) x, (int) y);
    }
}
