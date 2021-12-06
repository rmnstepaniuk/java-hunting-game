package huntingGame;

import huntingGame.sprites.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Spawner {
    private static final Random random = new Random();
    private final static int SCREEN_WIDTH = Main.SCREEN_WIDTH;
    private final static int SCREEN_HEIGHT = Main.SCREEN_HEIGHT ;
    private Spawner() {}

    public static Wolf spawnWolf() {
        return new Wolf(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT));
    }

    public static Hare spawnHare() {
        return new Hare(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT));
    }
    public static List<Deer> spawnDeer(int quantity) {
        int n = 16 + 3;
        int[][] spawningSector =    {{n, 2*n, 3*n, 4*n, 5*n, 6*n, 7*n, 8*n, 9*n},
                                    {n, 2*n, 3*n, 4*n, 5*n, 6*n, 7*n, 8*n, 9*n}};
        ArrayList<Deer> deer = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int x = spawningSector[random.nextInt(2)][random.nextInt(9)];
            int y = spawningSector[random.nextInt(2)][random.nextInt(9)];
            deer.add(new Deer(x, y));
        }
        return deer;
    }
}
