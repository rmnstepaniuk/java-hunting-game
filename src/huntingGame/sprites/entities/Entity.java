package huntingGame.sprites.entities;

import javax.swing.*;
import java.awt.*;

public class Entity {
    protected int x, y;
    protected int width, height;
    protected boolean visible;
    protected boolean alive;
    protected Image image;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
        alive = true;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public Image getImage() {
        return this.image;
    }
    protected void getImageDimensions() {
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }
    public boolean isVisible() {
        return this.visible;
    }
    public boolean isAlive() {
        return this.alive;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void setAlive(boolean status) {
        this.alive = status;
    }

    public void loadImage(String ImageName) {
        ImageIcon ii = new ImageIcon(ImageName);
        this.image = ii.getImage();
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}
