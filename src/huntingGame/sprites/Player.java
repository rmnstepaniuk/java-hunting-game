package huntingGame.sprites;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Player extends Sprite {

    private int dx, dy;
    private List<Bullet> bullets;
    private int directionCode;
    private int ammo = 10;

    public Player(int x, int y) {
        super(x, y);

        initPlayer();
    }

    private void initPlayer() {
        bullets = new ArrayList<>();
        loadImage("res/player.png");
        getImageDimensions();
    }
    public void move() {
        x += dx;
        y += dy;
    }

    public List<Bullet> getBullets() {
        return bullets;
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
