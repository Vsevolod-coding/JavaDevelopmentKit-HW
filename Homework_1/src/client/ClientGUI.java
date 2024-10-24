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
    private JTextArea chatHistory;
    private JTextField msgField;
    private JButton sendBtn;
    private ServerWindow server;

    private JTextField ipField;
    private JTextField portField;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JPanel loginPanel;
    private JPanel chatPanel;

    private boolean isConnected = false;
    private String clientName;

    public ClientGUI(ServerWindow server) {
        this.server = server;

        setTitle("Client");
        setSize(400,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Панель для входа
        loginPanel = new JPanel(new GridLayout(5,2,10,10));
        ipField = new JTextField();
        portField = new JTextField();
        nameField = new JTextField();
        passwordField = new JPasswordField();
        loginBtn = new JButton("Login");

        loginPanel.add(new JLabel("IP:"));
        loginPanel.add(ipField);
        loginPanel.add(new JLabel("Port:"));
        loginPanel.add(portField);
        loginPanel.add(new JLabel("Your name:"));
        loginPanel.add(nameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel(""));
        loginPanel.add(loginBtn);

        // Панель для чата
        chatPanel = new JPanel(new BorderLayout());
        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        msgField = new JTextField();
        sendBtn = new JButton("Send");

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(msgField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(chatHistory);
        chatPanel.add(scrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        add(loginPanel);

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
        if (isConnected) {
            String message = msgField.getText().trim();
            if (!message.isEmpty()) {
                String formattedMsg = clientName + ": " + message;
                chatHistory.append(formattedMsg + "\n");
                server.logMessage(formattedMsg);
                msgField.setText("");
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