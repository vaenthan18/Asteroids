package asteroids;

import asteroids.components.GameComponent;
import asteroids.components.gameboard.Explanation;
import asteroids.components.gameboard.MainMenu;
import asteroids.components.gameboard.Score;
import asteroids.components.gameboard.ScoreMenu;
import asteroids.components.gameitems.*;
import asteroids.listeners.MoveListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public final class AsteroidsGUI extends JPanel implements Runnable {

    private JFrame frame = new JFrame("Asteroids");
    private ArrayList<GameComponent> components = new ArrayList<>();
    private ArrayList<Bullet> bulletList = new ArrayList<>();
    private ArrayList<Bullet> enemyBulletList = new ArrayList<>();
    private ArrayList<Asteroid> asteroidList = new ArrayList<>();
    private ArrayList<Powerups> powerupsList = new ArrayList<>();
    private ArrayList<Alien> alienList = new ArrayList<>();
    private ArrayList<Bomb> bombList = new ArrayList<>();
    private static ArrayList<ScoreData> scoreData = new ArrayList<>();
    private int frameLength = 1000;
    private int frameHeight = 600;
    private int maxAsteroidDelay = 250;
    private int asteroidDelay = 0;
    private int maxAlienDelay = 1500;
    private int alienDelay = 0;
    private static Clip bgmClip;
    public static Color bgc = Color.decode("#000c28");

    private static int[] playerData = new int[3];
    //Lives, Bombs,Score

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
        readScores();
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
    }

    public void readScores() {
        try {
            scoreData.clear();
            BufferedReader in = new BufferedReader(new FileReader("scores.txt"));
            for (int i = 0; i < 10; i++) {
                String playerName = in.readLine();
                int playerScore = Integer.valueOf(in.readLine());
                scoreData.add(new ScoreData(playerName, playerScore));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printScores() {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("scores.txt")));
            for (int i = 0; i < 10; i++) {
                out.println(scoreData.get(i).getName());
                out.println(scoreData.get(i).getScore());
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        player = new Player(500, 300, Color.WHITE, .1);
        bulletList.clear();
        components.clear();
        asteroidList.clear();
        powerupsList.clear();
        alienList.clear();
        enemyBulletList.clear();
        bombList.clear();
        maxAsteroidDelay = 250;
		maxAlienDelay = 1500;
		alienDelay = 0;
		asteroidDelay = 0;
        playerData[0] = 5;
        playerData[1] = 2;
        playerData[2] = 0;
        maxAsteroidDelay = 250;
    }

    public void run() {
        while (running) {
            if (playerData[0] <= 0) {
                stop();
                makeScoreMenu();
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
            for (int i = 0; i < enemyBulletList.size(); i++) {
                enemyBulletList.get(i).update(this);
            }
            for (int i = 0; i < alienList.size(); i++) {
                alienList.get(i).update(this);
            }
            for (int i = 0; i < bombList.size(); i++) {
                bombList.get(i).update(this);
            }
            spawnAsteroid();
            spawnAlien();
            repaint(); //Draws objects
            try {
                Thread.sleep(17);
            } catch (Exception e) {
            }
        }
    }

    public void makeScoreboard() {
        //setup of the scoreboard (clears board, gets scores)
        readScores();
        Score newBoard = new Score(this);
        frame.getContentPane().removeAll();
        frame.remove(frame.getContentPane());
        frame.add(newBoard);
        frame.setContentPane(newBoard);
        frame.repaint();
        frame.revalidate();
    }

    public void makeMenu() {
        //makes menu - clear board, make menu, etc.
        MainMenu menu = new MainMenu(this);
        frame.getContentPane().removeAll();
        frame.remove(frame.getContentPane());
        frame.add(menu);
        frame.setContentPane(menu);
        frame.repaint();
        frame.revalidate();
    }

    public void makeInstructions() {
        //makes menu - clear board, make menu, etc.
        Explanation explanation = new Explanation(this);
        frame.getContentPane().removeAll();
        frame.remove(frame.getContentPane());
        frame.add(explanation);
        frame.setContentPane(explanation);
        frame.repaint();
        frame.revalidate();
    }

    public void makeScoreMenu() {
        //make the score menu - clear menu, add text boxes, etc.
        ScoreMenu scoreMenu = new ScoreMenu(this);
        frame.getContentPane().removeAll();
        frame.remove(frame.getContentPane());
        frame.add(scoreMenu);
        frame.setContentPane(scoreMenu);
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
        if (running) {
            for (int i = 0; i < components.size(); i++) {
                components.get(i).paintComponent(g2);
            }
            for (int i = 0; i < bulletList.size(); i++) {
                bulletList.get(i).paintComponent(g2);
            }
            for (int i = 0; i < asteroidList.size(); i++) {
                asteroidList.get(i).paintComponent(g2);
            }
            for (int i = 0; i < powerupsList.size(); i++) {
                powerupsList.get(i).paintComponent(g2);
            }
            for (int i = 0; i < enemyBulletList.size(); i++) {
                enemyBulletList.get(i).paintComponent(g2);
            }
            for (int i = 0; i < alienList.size(); i++) {
                alienList.get(i).paintComponent(g2);
            }
            for (int i = 0; i < bombList.size(); i++) {
                bombList.get(i).paintComponent(g2);
            }
            g.setColor(Color.RED);
            g.fillRect(25 + 70 * playerData[0], 25, 350 - 70 * playerData[0], 15);
            g.setColor(Color.GREEN);
            g.fillRect(25, 25, 70 * playerData[0], 15);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.BOLD, 14));
            g.drawString("HP: " + playerData[0] * 10 + "/50", 175, 38);
            g.setFont(new Font("Helvetica", Font.BOLD, 16));
            g.setColor(Color.WHITE);
            g.drawString("S C O R E: " + playerData[2], 25, 70);
            g.setColor(Color.decode("#a80000"));
            g.drawString("B O M B S: " + playerData[1], 25, 100);
            if (player.shieldActive) {
                g.setColor(Color.CYAN);
                g.drawString("SHIELD ACTIVE", 25, 130);
            }
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

    public void spawnAlien() {
        alienDelay++;
        if (alienDelay > maxAlienDelay && playerData[2] > 1000 && alienList.size() <= 2) {
            int y = (int) (Math.random() * 500) + 50;
            int x = Math.random() > 0.5 ? frameLength : 0;
            int velocity = x == 0 ? 3 : -3;
            alienList.add(new Alien(x, y, Color.WHITE, this, velocity));
            alienDelay = 0;
            maxAlienDelay -= (maxAlienDelay > 250) ? 10 : 0;
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
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Sound/smol.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
        }
        bulletList.add(newBullet);
    }

    public void addAlienBullet(Bullet newBullet) {
        enemyBulletList.add(newBullet);
    }

    public void removePlayerBullet(Bullet newBullet) {
        bulletList.remove(newBullet);
    }

    public ArrayList getBulletList() {
        return bulletList;
    }

    public ArrayList getBombList() {
        return bombList;
    }

    public void removeBomb(Bomb bomb) {
        bombList.remove(bomb);
    }

    public void addBomb(Bomb bomb) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Sound/thicc.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
        }
        bombList.add(bomb);
    }

    public static void playBGM() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("Sound/bgm.wav"));
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioIn);
            FloatControl gainControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            bgmClip.start();
        } catch (Exception e) {
        }
    }

    public static void loop() {
        bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public ArrayList getPowerupsList() {
        return powerupsList;
    }

    public ArrayList getAlienList() {
        return alienList;
    }

    public void addPowerup(Powerups powerup) {
        powerupsList.add(powerup);
    }

    public ArrayList getAsteroidList() {
        return asteroidList;
    }

    public ArrayList getEnemyBulletsList() {
        return enemyBulletList;
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

    public void removeAlien(Alien alien) {
        alienList.remove(alien);
    }

    public static int[] getPlayerData() {
        return playerData;
    }

    public static ArrayList<ScoreData> getScoreData() {
        return scoreData;
    }

    public JFrame getFrame() {
        return frame;
    }

    public boolean isRunning() {
        return running;
    }

    public static void main(String[] args) {
        playBGM();
        loop();
        AsteroidsGUI gui = new AsteroidsGUI(); //MAIN HERE
    }
}
