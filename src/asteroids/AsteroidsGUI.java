package asteroids;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

import asteroids.components.GameComponent;
import asteroids.components.gameboard.Score;
import asteroids.components.gameitems.*;
import asteroids.listeners.MoveListener;
import asteroids.components.gameboard.MainMenu;

import java.util.Arrays;
import java.util.Random;

public final class AsteroidsGUI extends JPanel implements Runnable {

    private JFrame frame = new JFrame("Asteroids");
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
    //lives, bombs, score.
    private static int[] playerData = {3, 2, 0};
    private static String[] playerNames = new String[10];
    private static int[] playerScores = new int[10];

    private SpaceBackground sbg = new SpaceBackground(this);
    //Has it's own run method for precise twinkle frequency.
    //Also doesn't get painted in an arraylist.
    
    public Player player = new Player(500, 300, Color.WHITE, .1);
    private boolean running = false;

    public void start() {
        //When the start button is clicked the menu is deleted and the game is added
        reset();
        running = true;
        frame.addKeyListener(new MoveListener(this));
        frame.getContentPane().removeAll();
        frame.remove(frame.getContentPane());
        frame.add(this);
        frame.setContentPane(this);
        frame.repaint();
        frame.revalidate();
        components.add(player);
        System.out.println("houoio");
        asteroidList.add(new Asteroid(750, 250, bgc, 90, 5, 50, 3));
        readScores();
        Thread thread = new Thread(this);
        thread.start();
    }

    public void readScores() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("scores.txt"));
            for (int i = 0; i < 10 ; i++) {
                playerNames[i] = in.readLine();
                playerScores[i] = Integer.valueOf(in.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printScores() {
        int position = Arrays.binarySearch(playerScores, playerData[2]);
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("scores.txt")));
            out.println("placeholder");
            out.println(playerData[2]);
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        player = new Player(500, 300, Color.WHITE, .1);
        bulletList.clear();
        components.clear();
        asteroidList.clear();
        powerupsList.clear();
        playerData[0] = 3;
        playerData[1] = 2;
        playerData[2] = 0;
    }

    public void stop() {
        running = false;
    }

    public void run() {
        while (running) {
            //check if game is over
            if (playerData[0] <= 0) {
                makeScoreboard();
                stop();
                break;
            }
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

    public void makeScoreboard() {
        Score newBoard = new Score(frame, this);
        frame.getContentPane().removeAll();
        frame.remove(frame.getContentPane());
        frame.add(newBoard);
        frame.setContentPane(newBoard);
        frame.repaint();
        frame.revalidate();
    }

    public void makeMenu() {
        MainMenu menu = new MainMenu(frame, this);
        frame.getContentPane().removeAll();
        frame.remove(frame.getContentPane());
        frame.add(menu);
        frame.setContentPane(menu);
        frame.repaint();
        frame.revalidate();
    }

    public AsteroidsGUI() {
        // Add the plane and control panels to the main panel
        // Configure the frame
        frame.setVisible(true);
        frame.setSize(frameLength, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Start with the menu
        makeMenu();
        frame.setFocusable(true);
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

    public static int[] getPlayerData() {
        return playerData;
    }

}