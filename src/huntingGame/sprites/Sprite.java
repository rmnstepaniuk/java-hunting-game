package huntingGame.sprites;

import javax.swing.*;
import java.awt.*;

public class Sprite {

    protected int x, y;
    protected int width, height;
    protected boolean visible;
    protected Image image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String ImageName) {
        ImageIcon ii = new ImageIcon(ImageName);
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
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public boolean isVisible() {
        return this.visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
