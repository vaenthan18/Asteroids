package asteroids;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import asteroids.components.GameComponent;
import asteroids.components.gameitems.Bullet;
import asteroids.listeners.MoveListener;
import asteroids.components.gameitems.Player;

public final class AsteroidsGUI extends JPanel implements Runnable {

    private JFrame frame = new JFrame("Asteroids");
    private JPanel mainPanel = new JPanel(); //Layout
    private ArrayList<GameComponent> components = new ArrayList<>();
    private ArrayList<Bullet> bulletList = new ArrayList<>();
    
    public Player player = new Player(500, 300, Color.WHITE, .1);
    private boolean running = false;

    
    
    public void start() {
        Thread thread = new Thread(this);
        running = true;
        thread.start();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            // Check for collision, draw objects and sleep
            for (GameComponent i : components) {
                i.update(this); //Updates state of game objects.
            }
            for (int i = 0; i < bulletList.size(); i++) { //Don't change this to a for each
                bulletList.get(i).update(this); //Updates state of game objects.
            }
            repaint(); //Draws objects
            try {
                Thread.sleep(17);
            } catch (Exception e) {
            }
        }
    }

    public AsteroidsGUI() {
        // Add the plane and control panels to the main panel
        //JPanel mainPanel = new JPanel();
        //mainPanel.add(space);
        components.add(player);
        // Configure the frame
        frame.addKeyListener(new MoveListener(this));
        frame.setVisible(true);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setFocusable(true);
        start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (GameComponent i : components) {
            i.paintComponent(g2);
        }
        for (Bullet i : bulletList) {
            i.paintComponent(g2); //Paints state of Bullets.
        }
        
    }

    public void addPlayerBullet(Bullet newBullet) {
        System.out.println(bulletList.size());
        bulletList.add(newBullet);
    }
    
    public void removePlayerBullet(Bullet newBullet) {
        bulletList.remove(newBullet);
    }
    
    public static void main(String[] args) {
        AsteroidsGUI gui = new AsteroidsGUI(); //MAIN HERE
    }
}
