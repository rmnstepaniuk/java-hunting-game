package huntingGame.sprites.agents;

import huntingGame.Main;

public class Wolf extends Agent {

    public Wolf(int x, int y) {
        super(x, y);
        initWolf();
    }

    private void initWolf() {
        loadImage("res/entities/wolf.png");
        getImageDimensions();
    }

    public void move() {
        
    }
}
