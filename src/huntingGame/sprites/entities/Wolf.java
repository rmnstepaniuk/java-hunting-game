package huntingGame.sprites.entities;

import huntingGame.Main;

public class Wolf extends Entity {

    public Wolf(int x, int y) {
        super(x, y);
        initWolf();
    }

    private void initWolf() {
        loadImage("res/wolf.png");
        getImageDimensions();
    }

    public void move() {
        if (x == 0 || x == Main.SCREEN_WIDTH || y == 0 || y == Main.SCREEN_HEIGHT) {
            setVisible(false);
        }
    }
}
