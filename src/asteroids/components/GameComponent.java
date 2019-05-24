package asteroids.components;

import asteroids.AsteroidsGUI;

import java.awt.*;

public abstract class GameComponent {

    protected int x;
    protected int y;
    protected Color color;

    public GameComponent(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void update(AsteroidsGUI at);

    public abstract void paintComponent(Graphics2D g2);

}