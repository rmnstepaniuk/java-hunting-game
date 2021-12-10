package huntingGame.sprites;

import javax.swing.*;
import javax.vecmath.Vector2d;
import java.awt.*;

public class Sprite {

    protected Vector2d position = new Vector2d();
    protected int width, height;
    protected boolean visible;
    protected Image image;

    public Sprite(int x, int y) {
        this.position.x = x;
        this.position.y = y;
        visible = true;
    }

    public void loadImage(String ImagePath) {
        ImageIcon ii = new ImageIcon(ImagePath);
        this.image = ii.getImage();
    }

    protected void getImageDimensions() {
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public Image getImage() {
        return this.image;
    }
    public int getX() {
        return (int) this.position.x;
    }
    public int getY() {
        return (int) this.position.y;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.getX(), this.getY(), this.width, this.height);
    }
}
