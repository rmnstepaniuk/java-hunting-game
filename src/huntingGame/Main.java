package huntingGame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 700;
    public Main() {
        initUI();
    }

    private void initUI() {
        add(new Panel());

        setTitle("Hunting game");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
	    EventQueue.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
