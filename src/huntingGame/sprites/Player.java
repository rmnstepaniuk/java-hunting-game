package huntingGame.sprites;

import huntingGame.Main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Player extends Sprite {

    private int dx, dy;
    private List<Bullet> bullets;
    private int directionCode;
    private int ammo;
    private int score;

    public Player(int x, int y) {
        super(x, y);
        score = 0;
        initPlayer();
    }

    private void initPlayer() {
        ammo = 10;
        bullets = new ArrayList<>();
        loadImage("res/player.png");
        getImageDimensions();
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void move() {
        if (x == 0 || x == Main.SCREEN_WIDTH || y == 0 || y == Main.SCREEN_HEIGHT) {
            setVisible(false);
        }
        x += dx;
        y += dy;
    }

    private void fire() {
        if (ammo > 0) {
            bullets.add(new Bullet(x + dx, y + dy, this.directionCode));
            ammo--;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Out of ammo");
        }

    }

    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            fire();
        }
        if (keyCode == KeyEvent.VK_A) {
            this.directionCode = 4;
            dx = -1;
        }
        if (keyCode == KeyEvent.VK_D) {
            this.directionCode = 6;
            dx = 1;
        }
        if (keyCode == KeyEvent.VK_W) {
            this.directionCode = 8;
            dy = -1;
        }
        if (keyCode == KeyEvent.VK_S) {
            this.directionCode = 2;
            dy = 1;
        }
    }

    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_A) {
            dx = 0;
        }
        if (keyCode == KeyEvent.VK_D) {
            dx = 0;
        }
        if (keyCode == KeyEvent.VK_W) {
            dy = 0;
        }
        if (keyCode == KeyEvent.VK_S) {
            dy = 0;
        }
    }
}
