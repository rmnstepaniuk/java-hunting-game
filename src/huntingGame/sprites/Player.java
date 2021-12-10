package huntingGame.sprites;

import huntingGame.Main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Player extends Sprite {

    private double dx, dy;
    private List<Bullet> bullets;
    private int directionCode;
    private int ammo;
    private int score;
    private double speed;

    public Player(int x, int y) {
        super(x, y);
        score = 0;
        speed = 1.1;
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

    public void updateScore(int score) {
        this.score += score;
    }

    public void move() {
        if (this.getX() == 0 || this.getX() == Main.SCREEN_WIDTH ||
            this.getY() == 0 || this.getY() == Main.SCREEN_HEIGHT) {
            setVisible(false);
        }
        this.position.x += dx * speed;
        this.position.y += dy * speed;
    }

    private void fire() {
        if (ammo > 0) {
            bullets.add(new Bullet(this.getX() + (int) dx, this.getY() + (int) dy, this.directionCode));
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
