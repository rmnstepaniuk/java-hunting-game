package huntingGame;

import huntingGame.sprites.Bullet;
import huntingGame.sprites.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Panel extends JPanel implements ActionListener {

    Timer timer;
    private Player player;

    public Panel() {
        initPanel();
    }

    private void initPanel() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        player = new Player(40, 60);

        int DELAY = 10;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
        List<Bullet> bullets = player.getBullets();
        for (Bullet bullet : bullets) {
            g2d.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateBullets();
        updatePlayer();

        repaint();
    }

    private void updatePlayer() {
        player.move();
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
