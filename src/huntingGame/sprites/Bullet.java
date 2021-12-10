package huntingGame.sprites;

public class Bullet extends Sprite {

    private final int startingX;
    private final int startingY;
    private final int bulletDirectionCode;

    public Bullet(int x, int y, int bulletDirectionCode) {
        super(x, y);
        this.startingX = x;
        this.startingY = y;
        this.bulletDirectionCode = bulletDirectionCode;
        initBullet();
    }

    private void initBullet() {
        loadImage("res/bullet.png");
        getImageDimensions();

    }
    public void move(int bulletDirectionCode) {
        int BULLET_SPEED = 10;
        int BULLET_DISTANCE = 100;
        switch (bulletDirectionCode) {
            case 8:
                this.position.y -= BULLET_SPEED;
                if (Math.abs(this.getY() - startingY) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
            case 2:
                this.position.y += BULLET_SPEED;
                if (Math.abs(this.getY() - startingY) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
            case 4:
                this.position.x -= BULLET_SPEED;
                if (Math.abs(this.getX() - startingX) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
            case 6:
                this.position.x += BULLET_SPEED;
                if (Math.abs(this.getX() - startingX) > BULLET_DISTANCE) {
                    visible = false;
                }
                break;
            default: break;
        }
    }

    public int getBulletDirectionCode() {
        return bulletDirectionCode;
    }
}
