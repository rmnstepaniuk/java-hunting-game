package huntingGame.sprites.agents;

public class Wolf extends Agent {

    public Wolf(int x, int y) {
        super(x, y);
        this.maxSpeed = 1.2;
        this.viewRadius = 210;
        initWolf();
    }

    private void initWolf() {
        loadImage("res/entities/wolf.png");
        getImageDimensions();
    }
}
