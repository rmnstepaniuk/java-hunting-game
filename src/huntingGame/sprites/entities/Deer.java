package huntingGame.sprites.entities;

import huntingGame.Main;

public class Deer extends Entity {

    public Deer(int x, int y) {
        super(x, y);
        initDeer();
    }

    private void initDeer() {
        loadImage("res/entities/deer.png");
        getImageDimensions();
    }
}
