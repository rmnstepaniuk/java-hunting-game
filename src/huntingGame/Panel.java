package huntingGame;

import huntingGame.sprites.*;
import huntingGame.sprites.entities.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Panel extends JPanel implements ActionListener {

    Timer timer;
    private Player player;
    private List<Wolf> wolves;
    private boolean inGame;
    private final Random random = new Random();

    public Panel() {
        initPanel();
    }

    private void initPanel() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        inGame = true;

        player = new Player(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT / 2);

        initEntities();

        int DELAY = 10;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initEntities() {
        wolves = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            wolves.add(new Wolf(random.nextInt(Main.SCREEN_WIDTH), random.nextInt(Main.SCREEN_HEIGHT)));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            drawGame(g);
        }
        else {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGameOver(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        String gameOverMessage = "GAME OVER";
        Font font = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fontMetrics = getFontMetrics(font);

        g2d.setColor(Color.white);
        g2d.setFont(font);
        g2d.drawString(gameOverMessage, (Main.SCREEN_WIDTH - fontMetrics.stringWidth(gameOverMessage)) / 2, Main.SCREEN_HEIGHT / 2);
    }

    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

        List<Bullet> bullets = player.getBullets();
        for (Bullet bullet : bullets) {
            g2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }
        g2d.setColor(Color.white);
        for (Wolf wolf : wolves) {
            if (wolf.isVisible()) {
                g2d.drawImage(wolf.getImage(), wolf.getX(), wolf.getY(), this);
            }
        }
        g2d.setColor(Color.white);
        g2d.drawString("Ammo left: " + player.getAmmo(), Main.SCREEN_WIDTH / 2, 10);
        g2d.drawString("Score: " + player.getScore(), Main.SCREEN_WIDTH / 2, 20);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateBullets();
        updatePlayer();
        updateEntities();

        checkCollision();

        repaint();
    }

    private void inGame() {
        if (!inGame) {
            timer.stop();
        }
    }

    private void checkCollision() {
        Rectangle playerHitBox = player.getBounds();
        Rectangle wolfHitBox;
        Rectangle bulletHitBox;

        for (Wolf wolf : wolves) {
            wolfHitBox = wolf.getBounds();

            if (playerHitBox.intersects(wolfHitBox) && wolf.isAlive()) {
                player.setVisible(false);
                inGame = false;
                return;
            }
            else if (playerHitBox.intersects(wolfHitBox) && !(wolf.isAlive())) {
                player.setScore(player.getScore() + 1);
                wolf.setVisible(false);
            }
        }
        List<Bullet> bullets = player.getBullets();

        for (Bullet bullet : bullets) {
            bulletHitBox = bullet.getBounds();

            for (Wolf wolf : wolves) {
                wolfHitBox = wolf.getBounds();

                if (bulletHitBox.intersects(wolfHitBox)) {
                    bullet.setVisible(false);
                    wolf.setAlive(false);
                    wolf.loadImage("res/wolfDead.png");
                }
            }
        }
    }

    private void updateEntities() {
        Wolf wolf;
        for (int i = 0; i < wolves.size(); i++) {
            wolf = wolves.get(i);
            if (wolf.isAlive()) {
                wolf.move();
            }
            else if (wolf.isVisible()) {}
            else {
                wolves.remove(i);
            }
        }
    }

    private void updatePlayer() {
        if (player.isVisible()) {
            player.move();
        }
        else {
            inGame = false;
        }
    }

    private void updateBullets() {
        List<Bullet> bullets = player.getBullets();
        Bullet bullet;
        for (int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if (bullet.isVisible()) {
                bullet.move(bullet.getBulletDirectionCode());
            }
            else {
                bullets.remove(bullet);
            }
        }
    }

    private class TAdapter implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }
}
