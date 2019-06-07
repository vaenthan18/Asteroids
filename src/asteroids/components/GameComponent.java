package asteroids.components;

import asteroids.AsteroidsGUI;

import java.awt.*;

public abstract class GameComponent {

    protected double x;
    protected double y;
    protected Color color;

    public GameComponent(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void update(AsteroidsGUI at);

    public abstract void paintComponent(Graphics2D g2);

}
