package huntingGame.sprites.agents;

public class Hare extends Agent {

    public Hare(int x, int y) {
        super(x, y);
        this.maxSpeed = 1.05;
        this.viewRadius = 100;
        initHare();
    }

    private void initHare() {
        loadImage("res/entities/hare.png");
        getImageDimensions();
    }
}
