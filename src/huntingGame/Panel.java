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
    private List<Entity> entities;
    private boolean inGame;
    private final Random random = new Random();

    public Panel() {
        initPanel();
    }

    private void initPanel() {
        addKeyListener(new TAdapter());
        setBackground(Color.green);
        setFocusable(true);
        inGame = true;

        player = new Player(Main.SCREEN_WIDTH / 2, Main.SCREEN_HEIGHT / 2);

        initEntities();

        int DELAY = 10;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initEntities() {
        entities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entities.add(Spawner.spawnWolf());
            entities.add(Spawner.spawnHare());
        }
        int deerPopulation = 0;
        while (deerPopulation < 3) {
            deerPopulation = random.nextInt(10);
        }
        entities.addAll(Spawner.spawnDeer(deerPopulation));
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
        String scoreMessage = "Your score is: " + player.getScore();

        Font font = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics fontMetrics = getFontMetrics(font);

        g2d.setColor(Color.white);
        g2d.setFont(font);
        g2d.drawString(gameOverMessage, (Main.SCREEN_WIDTH - fontMetrics.stringWidth(gameOverMessage)) / 2, Main.SCREEN_HEIGHT / 2 - 20);
        g2d.drawString(scoreMessage, (Main.SCREEN_WIDTH - fontMetrics.stringWidth(scoreMessage)) / 2, Main.SCREEN_HEIGHT / 2 + 20);
    }

    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

        List<Bullet> bullets = player.getBullets();
        for (Bullet bullet : bullets) {
            g2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }
        g2d.setColor(Color.white);
        for (Entity entity : entities) {
            if (entity.isVisible()) {
                g2d.drawImage(entity.getImage(), entity.getX(), entity.getY(), this);
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
        Rectangle entityHitBox;
        Rectangle bulletHitBox;

        for (Entity entity : entities) {
            entityHitBox = entity.getBounds();
            if (playerHitBox.intersects(entityHitBox) && entity.isAlive()) {
                if (entity instanceof Hare) {
                    entity.setAlive(false);
                    entity.loadImage("res/entities/hareDead.png");
                }
                else {
                    player.setVisible(false);
                    inGame = false;
                    return;
                }
            }
            else if (playerHitBox.intersects(entityHitBox) && !entity.isAlive()) {
                entity.setVisible(false);
                if (entity instanceof Wolf) {
                    player.updateScore(10);
                }
                if (entity instanceof Deer) {
                    player.updateScore(5);
                }
                if (entity instanceof Hare) {
                    player.updateScore(1);
                }
            }
        }

        List<Bullet> bullets = player.getBullets();
        for (Bullet bullet : bullets) {
            bulletHitBox = bullet.getBounds();
            for (Entity entity : entities) {
                entityHitBox = entity.getBounds();

                if (bulletHitBox.intersects(entityHitBox)) {
                    bullet.setVisible(false);
                    entity.setAlive(false);
                    if (entity instanceof Wolf) {
                        entity.loadImage("res/entities/wolfDead.png");
                    }
                    if (entity instanceof Hare) {
                        entity.loadImage("res/entities/hareDead.png");
                    }
                    if (entity instanceof Deer) {
                        entity.loadImage("res/entities/deerDead.png");
                    }
                }
            }
        }
    }

    private void updateEntities() {
        Entity entity;
        if (entities.size() == 0) {
            inGame = false;
            return;
        }
        else {
            for (int i = 0; i < entities.size(); i++) {
                entity = entities.get(i);
                if (entity.isAlive()) {
                    entity.move();
                }
                if (!entity.isVisible()) {
                    entities.remove(i);
                }
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
