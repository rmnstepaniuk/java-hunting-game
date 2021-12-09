package huntingGame.sprites.agents;

import huntingGame.Main;

public class Hare extends Agent {

    public Hare(int x, int y) {
        super(x, y);
        initHare();
    }

    private void initHare() {
        loadImage("res/entities/hare.png");
        getImageDimensions();
    }

    public void move() {

    }
}
