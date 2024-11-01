package server.ui;

import server.domain.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ServerWindow includes controls to start and stop the server and displays
 * a log of server messages.
 */
public class ServerWindow extends JFrame implements ServerView {
    private final static int WIN_WIDTH = 450;
    private final static int WIN_HEIGHT = 350;

    private JTextArea serverLog; // Area for displaying the server log
    private JButton startBtn, stopBtn; // Buttons to control server start/stop
    private JScrollPane scrollPane;

    private ServerController serverController;

    /**
     * Constructs the ServerWindow and initializes its graphical components.
     */
    public ServerWindow() {
        setupServerWindow();
        setupContent();
        setVisible(true);
    }

    /**
     * Associates a ServerController with this view, enabling control over server operations.
     *
     * @param serverController the server controller to link with this view.
     */
    @Override
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * Sets up the main window properties for the server UI.
     */
    private void setupServerWindow() {
        setTitle("Server");
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Configures and arranges content components within the server window.
     * This includes the server log and control buttons.
     */
    private void setupContent() {
        serverLog = new JTextArea();
        serverLog.setEditable(false);

        scrollPane = new JScrollPane(serverLog);
        add(scrollPane, BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    /**
     * Creates and returns a panel with Start and Stop buttons for server control.
     */
    private Component createButtons() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1,2,5,10));
        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");

        stopBtn.setEnabled(false); // Disable Stop button initially

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        buttonsPanel.add(startBtn);
        buttonsPanel.add(stopBtn);

        return buttonsPanel;
    }

    private void startServer() {
        serverController.startServer();
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
    }

    private void stopServer() {
        serverController.stopServer();
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
    }

    @Override
    public void showMessage(String message) {
        serverLog.append(message + System.lineSeparator());
    }
}