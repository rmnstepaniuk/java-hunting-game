package huntingGame;

import huntingGame.sprites.*;
import huntingGame.sprites.agents.*;

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
    private List<Agent> agents;
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

        player = new Player(20, 20);
        initEntities();

        int DELAY = 10;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void initEntities() {
        agents = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            agents.add(Spawner.spawnWolf());
            agents.add(Spawner.spawnHare());
        }
//        int deerPopulation = 0;
//        while (deerPopulation < 3) {
//            deerPopulation = random.nextInt(10);
//        }
//        agents.addAll(Spawner.spawnDeer(2));
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
        for (Agent agent : agents) {
            if (agent.isVisible()) {
                g2d.drawImage(agent.getImage(), agent.getX(), agent.getY(), this);
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
        Rectangle agentHitBox;
        Rectangle bulletHitBox;

        for (Agent agent : agents) {
            entityHitBox = agent.getBounds();
            if (playerHitBox.intersects(entityHitBox) && agent.isAlive()) {
                if (agent instanceof Hare) {
                    agent.setAlive(false);
                    agent.loadImage("res/entities/hareDead.png");
                }
                else {
                    player.setVisible(false);
                    inGame = false;
                    return;
                }
            }
            else if (playerHitBox.intersects(entityHitBox) && !agent.isAlive()) {
                agent.setVisible(false);
                if (agent instanceof Wolf) {
                    player.updateScore(10);
                }
                if (agent instanceof Deer) {
                    player.updateScore(5);
                }
                if (agent instanceof Hare) {
                    player.updateScore(1);
                }
            }
            for (Agent agent1 : agents) {
                agentHitBox = agent1.getBounds();
                if (!agent.equals(agent1)) {
                    if (entityHitBox.intersects(agentHitBox) && agent.isAlive() && agent1.isAlive()) {
                        if (agent instanceof Wolf) {
                            if (agent1 instanceof Hare){
                                agent1.setAlive(false);
                                agent1.setVisible(false);
                                agent1.loadImage("res/entities/hareDead.png");
                            }
                            else if (agent1 instanceof Deer) {
                                agent1.setAlive(false);
                                agent1.setVisible(false);
                                agent1.loadImage("res/entities/deerDead.png");
                            }
                         }
                    }
                }
            }
        }

        List<Bullet> bullets = player.getBullets();
        for (Bullet bullet : bullets) {
            bulletHitBox = bullet.getBounds();
            for (Agent agent : agents) {
                entityHitBox = agent.getBounds();

                if (bulletHitBox.intersects(entityHitBox)) {
                    bullet.setVisible(false);
                    agent.setAlive(false);
                    if (agent instanceof Wolf) {
                        agent.loadImage("res/entities/wolfDead.png");
                    }
                    if (agent instanceof Hare) {
                        agent.loadImage("res/entities/hareDead.png");
                    }
                    if (agent instanceof Deer) {
                        agent.loadImage("res/entities/deerDead.png");
                    }
                }
            }
        }
    }

    private void updateEntities() {
        Agent agent;
        Sprite target;
        if (agents.size() == 0) {
            inGame = false;
            return;
        }
        else {
            for (int i = 0; i < agents.size(); i++) {
                agent = agents.get(i);
                if (agent.isAlive()) {
                    agent.checkEdges();
                    target = agent.findTarget((ArrayList<Agent>) agents, player);
                    if (target == null) {
                        target = player;
                    }
                    agent.update(target.getPosition());
                }
                if (!agent.isVisible()) {
                    agents.remove(i);
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
