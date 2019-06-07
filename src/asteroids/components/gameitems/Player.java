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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends GameComponent {

    private static BufferedImage img;
    private static File shipImg = new File("Images/ship.png");
    private static File shipImgMoving = new File("Images/shipfire.png");
    private Path2D.Double shipBody = new Path2D.Double();
    private ArrayList<Integer> inputs = new ArrayList<>();
    private double velocity;
    private double xSpeed = 0;
    private double ySpeed = 0;
    private double angle = 0;
    private double immortalCounter = 0;
    private int angularVelocity = 5;
    private double maxShootDelay = 10; //The lower the number, the higher the fire rate
    private double shootDelay = 0; //Current shoot delay counter
    private int[] playerData;
    public boolean shieldActive = false;
    private static int shieldCounter = 0;
    protected Ellipse2D.Double shield = new Ellipse2D.Double((int) (x - 20 + 5 * Math.cos(Math.toRadians(angle + 90)) - 20 * Math.cos(Math.toRadians(angle + 90))), (int) (y - 20 - 5 * Math.sin(Math.toRadians(angle + 90)) - 20 * Math.sin(Math.toRadians(-angle - 90))), 40, 40);

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
        if (shieldCounter <= 0) {
            shieldActive = false;
        } else {
            shieldCounter--;
        }
        if (shieldActive) {
            shield = new Ellipse2D.Double((int) (x - 20 + 5 * Math.cos(Math.toRadians(angle + 90)) - 20 * Math.cos(Math.toRadians(angle + 90))), (int) (y - 20 - 5 * Math.sin(Math.toRadians(angle + 90)) - 20 * Math.sin(Math.toRadians(-angle - 90))), 40, 40);
        }
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
        if (inputs.contains(KeyEvent.VK_B)) {
            if (shootDelay <= 0 && playerData[1] > 0) {
                Bomb bomb = new Bomb(x, y, Color.CYAN, angle);
                gui.addBomb(bomb);
                shootDelay = maxShootDelay;
                playerData[1]--;
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
        playerData = AsteroidsGUI.getPlayerData();
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
            shipArea = shieldActive ? new Area((Shape) shield) : new Area((Shape) shipBody);

            shipArea.intersect(asteroidArea);
            hasCollided = !shipArea.isEmpty();
            if (hasCollided) {
                if (shieldActive) {
                    asteroid.shatter(gui);
                    asteroidList.remove(asteroid);
                    break;
                } else {
                    if (immortalCounter < 0) {
                        asteroid.shatter(gui);
                        asteroidList.remove(asteroid);
                        if (asteroid.healthValue() == 5) {
                            playerData[0] -= 2;
                        } else {
                            playerData[0]--;
                        }
                        immortalCounter = 80;
                        break;
                    }
                }
            }
        }

        Area bulletArea;
        Bullet bullet;
        ArrayList<Bullet> bulletList = gui.getEnemyBulletsList();

        for (int i = 0; i < bulletList.size(); i++) {
            bullet = bulletList.get(i);
            bulletArea = new Area((Shape) bullet.getBody());
            shipArea = shieldActive ? new Area((Shape) shield) : new Area((Shape) shipBody);

            shipArea.intersect(bulletArea);
            hasCollided = !shipArea.isEmpty();
            if (hasCollided) {
                if (shieldActive) {
                    bulletList.remove(bullet);
                } else {
                    if (immortalCounter < 0) {
                        bulletList.remove(bullet);
                        playerData[0]--;
                        System.out.println("Lives: " + playerData[0]);
                        immortalCounter = 80;
                        break;
                    }
                }
            }
        }

        Area alienArea;
        Alien alien;
        ArrayList<Alien> alienList = gui.getAlienList();
        for (int i = 0; i < alienList.size(); i++) {
            alien = alienList.get(i);
            alienArea = new Area((Shape) alien.getAlienBody());
            shipArea = shieldActive ? new Area((Shape) shield) : new Area((Shape) shipBody);

            shipArea.intersect(alienArea);
            hasCollided = !shipArea.isEmpty();
            if (hasCollided) {
                if (shieldActive) {
                    alienList.remove(alien);
                } else {
                    if (immortalCounter < 0) {
                        alienList.remove(alien);
                        playerData[0] -= 2;
                        System.out.println("Lives: " + playerData[0]);
                        immortalCounter = 80;
                        break;
                    }
                }
            }
        }

        immortalCounter--;

        for (int i = 0; i < powerupsList.size(); i++) {
            powerup = powerupsList.get(i);
            powerupArea = new Area((Shape) powerup.getBody());
            shipArea = new Area((Shape) shipBody);
            shipArea.intersect(powerupArea);
            hasCollided = !shipArea.isEmpty();
            if (hasCollided) {
                if (powerup.getType().equals(PowerupType.HEALTH) && playerData[0] < 5) {
                    playerData[0]++;
                } else if (powerup.getType().equals(PowerupType.BOMB)) {
                    playerData[1]++;
                } else if (powerup.getType().equals(PowerupType.SHIELD)) {
                    shieldActive = true;
                    shieldCounter = 300;
                }
                powerupsList.remove(i);
            }
        }
    }

    public double getShootDelay() {
        return shootDelay;
    }

    public void setShootDelay(double newDelay) {
        shootDelay = newDelay;
    }

    public void paintComponent(Graphics2D g2d) {
        updatePoints();
        AffineTransform tx = new AffineTransform();
        AffineTransform a = new AffineTransform();
        tx.translate(-img.getWidth() / 2.0, 0);
        tx.rotate(Math.toRadians(-angle), img.getWidth() / 2.0, 0);
        a.rotate(Math.toRadians(-angle), x, y);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        Shape newShape = a.createTransformedShape(shipBody);
        shipBody = (Path2D.Double) newShape;
        if (immortalCounter > 0) {
            if ((Math.ceil(immortalCounter / (double) 8)) % 2 == 0) {
                g2d.drawImage(img, op, (int) x, (int) y);
            }
        } else {
            g2d.drawImage(img, op, (int) x, (int) y);
        }
        g2d.setColor(new Color(0, 255, 255, 75));
        if (shieldActive) {
            if (shieldCounter < 100) {
                if ((Math.ceil(shieldCounter / (double) 8)) % 2 == 0) {
                    g2d.fill(shield);
                }
            } else {
                g2d.fill(shield);
            }
        }
    }
}
