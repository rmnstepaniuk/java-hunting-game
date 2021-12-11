package huntingGame.sprites.agents;

public class Deer extends Agent {

    public Deer(int x, int y) {
        super(x, y);
        this.maxSpeed = 1.1;
        this.viewRadius = 110;
        initDeer();
    }

    private void initDeer() {
        loadImage("res/entities/deer.png");
        getImageDimensions();
    }
}
