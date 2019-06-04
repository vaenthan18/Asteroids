package asteroids;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import asteroids.components.GameComponent;
import asteroids.components.gameitems.*;
import asteroids.listeners.MoveListener;
import asteroids.listeners.MouseClickListener;

import java.util.Random;

public final class AsteroidsGUI extends JPanel implements Runnable {

    private JFrame frame = new JFrame("Asteroids");
    private JPanel menuPanel = new JPanel();
    private JPanel mainPanel = new JPanel(); //Layout
    private ArrayList<GameComponent> components = new ArrayList<>();
    private ArrayList<Bullet> bulletList = new ArrayList<>();
    private ArrayList<Asteroid> asteroidList = new ArrayList<>();
    private ArrayList<Powerups> powerupsList = new ArrayList<>();
    private int frameLength = 1000;
    private int frameHeight = 600;
    private int maxAsteroidDelay = 250;
    private int asteroidDelay = 0;
    public static Color bgc = Color.decode("#240046");

    private SpaceBackground sbg = new SpaceBackground(this);
    //Has it's own run method for precise twinkle frequency.
    //Also doesn't get painted in an arraylist.
    
    public Player player = new Player(500, 300, Color.WHITE, .1);
    private boolean running = false;



    public void start() {
        //When the start button is clicked the menu is deleted and the game is added
        running = true;
        frame.getContentPane().removeAll();
        frame.remove(menuPanel);
        frame.add(this);
        frame.setContentPane(this);
        frame.repaint();
        frame.revalidate();
        components.add(player);
        System.out.println("houoio");
        asteroidList.add(new Asteroid(750, 250, bgc, 90, 5, 50, 3));
        Thread thread = new Thread(this);
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
            for (int i = 0; i < asteroidList.size(); i++) { //Don't change this to a for each
                asteroidList.get(i).update(this); //Updates state of game objects.
            }
            for (int i = 0; i < powerupsList.size(); i++) {
                powerupsList.get(i).update(this);
            }
            spawnAsteroid();
            repaint(); //Draws objects
            try {
                Thread.sleep(17);
            } catch (Exception e) {
            }
        }
    }

    public AsteroidsGUI() {
        // Add the plane and control panels to the main panel
        components.add(player);
        // Configure the frame
        frame.addKeyListener(new MoveListener(this));
        frame.setVisible(true);
        frame.setSize(frameLength, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Start with the menu
        makeMenu(this);
        frame.setFocusable(true);
    }

    public void makeMenu(AsteroidsGUI gui) {
        //Menu creation
        menuPanel.setLayout(null);
        menuPanel.setBackground(bgc);
        frame.add(menuPanel);

        JLabel title = new JLabel("ASTEROIDS", SwingConstants.CENTER);
        title.setBounds(350, 100, 300, 100);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Courier", Font.PLAIN, 55));

        MouseClickListener buttonPressed = new MouseClickListener(gui);

        JButton startButton = new JButton("START");
        startButton.setBounds(450, 350, 100, 50);
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        startButton.setFont(new Font("Courier", Font.PLAIN, 20));
        startButton.addActionListener(buttonPressed);
        startButton.setActionCommand("start");

        JButton scoreButton = new JButton("SCORES");
        scoreButton.setBounds(450, 425, 100, 50);
        scoreButton.setBackground(Color.BLACK);
        scoreButton.setForeground(Color.WHITE);
        scoreButton.setOpaque(true);
        scoreButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        scoreButton.setFont(new Font("Courier", Font.PLAIN, 20));
        scoreButton.addActionListener(buttonPressed);
        scoreButton.setActionCommand("score");

        JLabel shopButton = new JLabel("Shop");

        menuPanel.add(startButton);
        menuPanel.add(scoreButton);
        menuPanel.add(title);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		sbg.paintComponent(g2);
		if (running == true) {
            for (int i = 0; i < components.size(); i++) {
                components.get(i).paintComponent(g2);
            }
            for (int i = 0; i < bulletList.size(); i++) {
                bulletList.get(i).paintComponent(g2); //Paints state of Bullets.
            }
            for (int i = 0; i < asteroidList.size(); i++) {
                asteroidList.get(i).paintComponent(g2);
            }
            for (int i = 0; i < powerupsList.size(); i++) {
                powerupsList.get(i).paintComponent(g2);
            }
        } else if (running == false) {

        }
        this.setBackground(bgc);
    }

    public void spawnAsteroid() {
        asteroidDelay++;
        if (asteroidDelay > maxAsteroidDelay && asteroidList.size() < 15) {
            createAsteroid();
            asteroidDelay = 0;
            maxAsteroidDelay -= (maxAsteroidDelay > 0) ? 10 : 0;
        }
    }

    public void createAsteroid() {
        Random random = new Random();
        int spawnX = random.nextInt(2) == 0 ? 0 : random.nextInt(frameLength);
        int spawnY = spawnX == 0 ? random.nextInt(frameHeight) : 0;

        Color spawnColor = bgc;
        double angle = random.nextInt(360);
        double maxHealth = 5;
        double radius = 50;
        double velocity = 3;
        asteroidList.add(new Asteroid(spawnX, spawnY, spawnColor, angle, maxHealth, radius, velocity));
    }

    public void addPlayerBullet(Bullet newBullet) {
        bulletList.add(newBullet);
    }

    public void removePlayerBullet(Bullet newBullet) {
        bulletList.remove(newBullet);
    }

    public ArrayList getBulletList() {
        return bulletList;
    }

    public ArrayList getPowerupsList() {
        return powerupsList;
    }

	public void addPowerup(Powerups powerup) {
		powerupsList.add(powerup);
	}

    public ArrayList getAsteroidList() {
        return asteroidList;
    }

    public void addAsteroid(Asteroid asteroid) {
        asteroidList.add(asteroid);
    }

    public void removeAsteroid(Asteroid asteroid) {
        asteroidList.remove(asteroid);
    }
    
    public int getFrameLength() {
        return frameLength;
    }
    
    public int getFrameHeight() {
        return frameHeight;
    }

    public static void main(String[] args) {
        //make the intial menu
        //makeMenu();
        AsteroidsGUI gui = new AsteroidsGUI(); //MAIN HERE
    }
}