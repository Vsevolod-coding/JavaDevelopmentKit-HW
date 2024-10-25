import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 590;
    private static final int WINDOW_WIDTH = 540;

    private final JButton btnStart = new JButton("New Game");
    private final JButton btnExit = new JButton("Exit");

    private Map map = new Map();
    private SettingsWindow settings = new SettingsWindow(this);

    GameWindow() {
        setupGameWindow();
        setupContent();
        setVisible(true);
    }

    private void setupGameWindow() {
        setTitle("TicTacToe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void setupContent() {
        JPanel panBottom = new JPanel(new GridLayout(1,2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);
        add(panBottom, BorderLayout.SOUTH);
        add(map);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.setVisible(true);
            }
        });
    }

    void startNewGame(int mode, int fSzX, int fSzY, int wLen) {
        map.startNewGame(mode, fSzX, fSzY, wLen);
    }
}
