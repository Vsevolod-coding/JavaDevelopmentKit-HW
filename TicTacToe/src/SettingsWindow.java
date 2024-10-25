import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    GameWindow gameWindow;

    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 270;

    private static final String CHOOSE_FIELD_SIZE = "Выберите размер поля";
    private static final String CHOSEN_FIELD_SIZE = "Выбранный размер поля: ";
    private static final String CHOOSE_WIN_LENGTH = "Выберите длинну для победы";
    private static final String CHOSEN_WIN_LENGTH = "Выбранная длинна: ";

    private final JButton btnStart = new JButton("Start new game");

    private final JRadioButton btnHumanVsAi = new JRadioButton("Человек против компьютера");
    private final JRadioButton btnHumanVsHuman = new JRadioButton("Человек против человека");
    private final JSlider winLenSizeSlider = new JSlider(3,10);
    private final JSlider fieldSizeSlider = new JSlider(3,10);

    SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setupSettingsWindow();
        setupContent();
    }

    private void setupSettingsWindow() {
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(gameWindow);
    }

    private void setupContent() {
        // Создание блока настроек
        JPanel settings = new JPanel(new GridLayout(3, 1));

        // Выбор типа игры
        JPanel typeGame = new JPanel(new GridLayout(3, 1));
        typeGame.add(new JLabel("Выберите режим игры: "));
        ButtonGroup group1 = new ButtonGroup();
        btnHumanVsAi.setSelected(true);
        group1.add(btnHumanVsAi);
        group1.add(btnHumanVsHuman);
        typeGame.add(btnHumanVsAi);
        typeGame.add(btnHumanVsHuman);

        // Выбор длинны повторений для победы
        JPanel winLenSize = new JPanel(new GridLayout(3, 1));
        winLenSize.add(new JLabel(CHOOSE_WIN_LENGTH));
        JLabel currentWinLen = new JLabel(CHOSEN_WIN_LENGTH);
        winLenSize.add(currentWinLen);

        winLenSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int wSize = winLenSizeSlider.getValue();
                currentWinLen.setText(CHOSEN_WIN_LENGTH + wSize);
            }
        });

        winLenSize.add(winLenSizeSlider);

        // Выбор размера игрового поля
        JPanel fieldSize = new JPanel(new GridLayout(3, 1));
        fieldSize.add(new JLabel(CHOOSE_FIELD_SIZE));
        JLabel currentFSize = new JLabel(CHOSEN_FIELD_SIZE);
        fieldSize.add(currentFSize);

        fieldSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int fSize = fieldSizeSlider.getValue();
                currentFSize.setText(CHOSEN_FIELD_SIZE + fSize);
                winLenSizeSlider.setMaximum(fSize);
            }
        });

        fieldSize.add(fieldSizeSlider);

        // Заполнение окна настроек
        settings.add(typeGame);
        settings.add(fieldSize);
        settings.add(winLenSize);

        // Добавляем панель настроек на осн. окно
        add(settings);
        // Добавляем кнопку начала игры
        add(btnStart, BorderLayout.SOUTH);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
    }

    private void startNewGame() {
        int mode = 0;

        if (btnHumanVsAi.isSelected()) {
            mode = 1;
        } else if (btnHumanVsHuman.isSelected()) {
            mode = 2;
        }

        int sizeField = fieldSizeSlider.getValue();
        int sizeWin = winLenSizeSlider.getValue();

        gameWindow.startNewGame(mode, sizeField, sizeField, sizeWin);
        setVisible(false);
    }
}
