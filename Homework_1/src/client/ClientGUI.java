package client;

import server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private final JTextArea chatHistory = new JTextArea();
    private final JTextField msgField = new JTextField();
    private final JButton sendBtn = new JButton("Send");
    private final ServerWindow server;

    private final JPanel loginPanel = new JPanel(new GridLayout(2,3, 5, 5));
    private final JTextField ipField = new JTextField("localhost");
    private final JTextField portField = new JTextField("8888");
    private final JTextField nameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField("12345678");
    private final JButton loginBtn = new JButton("Login");
    private final JPanel chatPanel = new JPanel(new BorderLayout());

    private boolean isConnected = false;
    private String clientName;

    public ClientGUI(ServerWindow server) {
        this.server = server;

        setTitle("Client");
        setSize(400,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        ipField.setEditable(false);
        portField.setEditable(false);
        passwordField.setEditable(false);

        loginPanel.add(ipField);
        loginPanel.add(portField);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel("Enter your name:"));
        loginPanel.add(nameField);
        loginPanel.add(loginBtn);

        chatHistory.setEditable(false);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(msgField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(chatHistory);
        chatPanel.add(scrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        add(loginPanel, BorderLayout.NORTH);

        msgField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String ip = ipField.getText().trim();
        String port = portField.getText().trim();
        clientName = nameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (ip.isEmpty() || port.isEmpty() || clientName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Все поля должны быть заполнены!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Имитация подключения к серверу
            if (server.isRunning()) {
                isConnected = true;
                switchToChatPanel();
                server.addClient(this);
                server.logMessage(clientName + " connected to the server.");
                loadChatHistory();
            } else {
                JOptionPane.showMessageDialog(this, "Server is not running!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void switchToChatPanel() {
        getContentPane().removeAll();
        add(chatPanel);
        revalidate();
        repaint();
    }

    private void loadChatHistory() {
        chatHistory.setText(""); // Очищаем текущее содержимое
        try (BufferedReader reader = new BufferedReader(new FileReader(server.getHISTORY_LOG_FILE()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                chatHistory.append(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("No previous chat history found.");
        }
    }

    private void sendMessage() {
        String message = msgField.getText().trim();
        if (!message.isEmpty()) {
            if (server.isRunning()) {
                String formattedMsg = clientName + ": " + message;
                server.receiveMessage(formattedMsg);
                msgField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Сервер остановлен. Сообщения не могут быть отправлены.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updateChatHistory(String message) {
        chatHistory.append(message + "\n");
    }

    @Override
    public void dispose() {
        if (isConnected) {
            server.removeClient(this);
            server.logMessage(clientName + " disconnected from the server.");
        }
        super.dispose();
    }
}