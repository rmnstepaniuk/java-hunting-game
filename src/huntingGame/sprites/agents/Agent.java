package huntingGame.sprites.agents;

import huntingGame.Main;

import javax.swing.*;
import javax.vecmath.Vector2d;
import java.awt.*;

public class Agent {
//    protected int x, y;
    protected Vector2d location = new Vector2d();
    protected int width, height;
    protected boolean visible;
    protected boolean alive;
    protected Image image;

    public Agent(int x, int y) {
        this.location.x = x;
        this.location.y = y;
        visible = true;
        alive = true;
    }

    public int getX() {
        return (int) this.location.x;
    }
    public int getY() {
        return (int) this.location.y;
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
        return new Rectangle((int)this.location.x, (int) this.location.y, this.width, this.height);
    }

    public void move() {

    }
}
