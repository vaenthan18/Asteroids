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
import java.awt.geom.Path2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Alien extends GameComponent {

    private static AsteroidsGUI gui;
    private static Path2D.Double alienBody = new Path2D.Double();
    protected double velocity;
    private double shootDelay = 50;
    private int health = 5;
    private static BufferedImage img = null;

    public Alien(double x, double y, Color color, AsteroidsGUI gui, int velocity) {
        super(x, y, color);
        Alien.gui = gui;
        this.velocity = velocity;
        try {
            img = ImageIO.read(new File("Images/alien.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path2D.Double getAlienBody() {
        return alienBody;
    }

    public void updatePoints() {
        alienBody.reset();
        alienBody.moveTo(x, y);
        alienBody.lineTo(x - 12, y + 1);
        alienBody.lineTo(x - 28, y + 19);
        alienBody.lineTo(x - 30, y + 15);
        alienBody.lineTo(x - 32, y + 25);
        alienBody.lineTo(x - 29, y + 35);
        alienBody.lineTo(x - 25, y + 22);
        alienBody.lineTo(x - 18, y + 20);
        alienBody.lineTo(x, y + 27);
        alienBody.lineTo(x + 18, y + 20);
        alienBody.lineTo(x + 25, y + 22);
        alienBody.lineTo(x + 25, y + 22);
        alienBody.lineTo(x + 29, y + 35);
        alienBody.lineTo(x + 32, y + 25);
        alienBody.lineTo(x + 30, y + 15);
        alienBody.lineTo(x + 28, y + 19);
        alienBody.lineTo(x + 12, y + 1);
    }

    public void move() { //Don't forget to put this in the update method
        x += velocity;
    }

    public void update(AsteroidsGUI gui) {
        collisionCheck(gui);
        move();
        if (x < 0 || x > gui.getFrameLength()) {
            gui.removeAlien(this);
        }
        if (isDead()) {
            gui.removeAlien(this);
            asteroids.AsteroidsGUI.getPlayerData()[2] += 500;
            int randomInt = (int) (Math.random() * 5 + 1);
            if (randomInt == 1 && gui.getPowerupsList().size() < 2) {
                gui.addPowerup(new Powerups(x, y, AsteroidsGUI.bgc, 35, PowerupType.HEALTH));
            } else if (randomInt == 2 && gui.getPowerupsList().size() < 2) {
                gui.addPowerup(new Powerups(x, y, AsteroidsGUI.bgc, 35, PowerupType.BOMB));
            } else if (randomInt == 3 && gui.getPowerupsList().size() < 2) {
                gui.addPowerup(new Powerups(x, y, AsteroidsGUI.bgc, 35, PowerupType.SHIELD));
            }
        }
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

    public boolean isDead() {
        return health == 0;
    }

    public void collisionCheck(AsteroidsGUI gui) {
        Area alienArea;
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
            alienArea = new Area(alienBody);

            alienArea.intersect(bulletArea);
            hasCollided = !alienArea.isEmpty();
            if (hasCollided) {
                bulletList.remove(bullet);
                health--;
                break;
            }
        }
        for (int i = 0; i < bombList.size(); i++) {
            bomb = bombList.get(i);
            bombArea = new Area(bomb.getBody());
            alienArea = new Area(alienBody);

            alienArea.intersect(bombArea);
            hasCollided = !alienArea.isEmpty();
            if (hasCollided) {
                health = 0;
                break;
            }
        }
    }

    public void paintComponent(Graphics2D g2) {
        g2.setColor(AsteroidsGUI.bgc);
        g2.draw(alienBody);
        g2.draw(new Area(alienBody));
        AffineTransform tx = new AffineTransform();
        tx.scale(70.0 / img.getWidth(), 70.0 / img.getWidth());
        tx.translate(-img.getWidth() / 2.0, 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2.drawImage(img, op, (int) x, (int) y);
    }
}
