package server;

import client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ServerWindow extends JFrame {
    private final static int WIN_WIDTH = 450;
    private final static int WIN_HEIGHT = 350;

    private final JTextArea serverLog = new JTextArea();
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");
    private ArrayList<ClientGUI> clients = new ArrayList<>();

    private final String PATH_TO_HISTORY_LOG_FILE = "src/chat_history.txt";
    private boolean isRunning = false;

    public ServerWindow() {
        checkAndOrCreateHistoryFile();
        setupServerWindow();
        setupContent();
        setVisible(true);
    }

    private void setupServerWindow() {
        setTitle("Server");
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupContent() {
        serverLog.setEditable(false);
        stopBtn.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(serverLog);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startBtn);
        buttonPanel.add(stopBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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
    }

    private void startServer() {
        isRunning = true;

        // Sending message only to server chat
        logMessage("Server started.");

        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
    }

    private void stopServer() {
        // Sending message only to server chat
        logMessage("Server stopped.");

        isRunning = false;

        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getPATH_TO_HISTORY_LOG_FILE() {
        return PATH_TO_HISTORY_LOG_FILE;
    }

    public void logMessage(String message) {
        serverLog.append(message + "\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_HISTORY_LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error while saving messages into the file.");
            e.printStackTrace();
        }
    }

    private void checkAndOrCreateHistoryFile() {
        File file = new File(PATH_TO_HISTORY_LOG_FILE);
        if (file.exists()) {
            System.out.println("The chat history file already exists.");
        } else {
            try {
                if (!file.createNewFile()) {
                    System.err.println("Failed to create chat history file.");
                }
                System.out.println("A new file has been created to store chat history.\nPath to the file: " + file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(ClientGUI client) {
        clients.add(client);
    }

    public void removeClient(ClientGUI client) {
        clients.remove(client);
    }

    // Updating messages in client chat (only if client is connected to the server)
    private void updateClients(String message) {
        for (ClientGUI client : clients) {
            if (client.isConnected()) {
                client.updateChatHistory(message);
            }
        }
    }

    // Sending a message to the log and to the client chat at once (only if server is running)
    public void receiveMessage(String message) {
        if (isRunning) {
            logMessage(message);
            updateClients(message);
        }
    }
}