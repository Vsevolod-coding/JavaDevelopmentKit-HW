package server;

import client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ServerWindow extends JFrame {
    private JTextArea serverLog;
    private JButton startBtn;
    private JButton stopBtn;
    private boolean isRunning = false;
    private final String HISTORY_LOG_FILE = "chat_history.txt";
    private ArrayList<ClientGUI> connectedClients = new ArrayList<>();

    public ServerWindow() {
        setTitle("Server");
        setSize(450,350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        serverLog = new JTextArea();
        serverLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(serverLog);


        startBtn = new JButton("Start");
        stopBtn = new JButton("Stop");
        stopBtn.setEnabled(false);

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

        JPanel buttonPanel  = new JPanel();
        buttonPanel.add(startBtn);
        buttonPanel.add(stopBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startServer() {
        isRunning = true;
        logMessage("Server started.");
        checkAndOrCreateHistoryFile();
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
    }

    private void stopServer() {
        isRunning = false;
        logMessage("Server stopped.");

        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getHISTORY_LOG_FILE() {
        return HISTORY_LOG_FILE;
    }

    public void logMessage(String message) {
        serverLog.append(message + "\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error while saving messages into the file.");
            e.printStackTrace();
        }
    }

    private void checkAndOrCreateHistoryFile() {
        File file = new File(HISTORY_LOG_FILE);
        if (file.exists()) {
            System.out.println("Файл для хранения истории чата уже существует.");
        } else {
            try {
                if (file.createNewFile()) {
                    System.out.println("Создан новый файл для хранения истории чата по пути: " + file.getPath());
                } else {
                    System.out.println("Не удалось создать файл для хранения истории чата.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(ClientGUI client) {
        connectedClients.add(client);
    }

    public void removeClient(ClientGUI client) {
        connectedClients.remove(client);
    }

    private void updateClients(String message) {
        for (ClientGUI client : connectedClients) {
            client.updateChatHistory(message);
        }
    }

    public void receiveMessage(String message) {
        if (isRunning) {
            logMessage(message);
            updateClients(message);
        }
    }
}