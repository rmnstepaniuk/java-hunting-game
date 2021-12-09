package huntingGame.sprites.agents;

public class Deer extends Agent {

    public Deer(int x, int y) {
        super(x, y);
        initDeer();
    }

    private void initDeer() {
        loadImage("res/entities/deer.png");
        getImageDimensions();
    }
}
