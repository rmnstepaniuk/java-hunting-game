package huntingGame.sprites.entities;

import huntingGame.Main;

public class Hare extends Entity {

    public Hare(int x, int y) {
        super(x, y);
        initHare();
    }

    private void initHare() {
        loadImage("res/entities/hare.png");
        getImageDimensions();
    }

    public void move() {
        if (x == 0 || x == Main.SCREEN_WIDTH || y == 0 || y == Main.SCREEN_HEIGHT) {
            setVisible(false);
        }
    }
}
