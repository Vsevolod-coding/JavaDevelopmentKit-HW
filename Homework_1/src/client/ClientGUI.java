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
    private final static int WIN_WIDTH = 400;
    private final static int WIN_HEIGHT = 500;

    private final JTextField ipField = new JTextField("localhost");
    private final JTextField portField = new JTextField("8888");
    private final JPasswordField passwordField = new JPasswordField("12345678");
    private String clientName;
    private final JTextField nameField = new JTextField();
    private final JButton loginBtn = new JButton("Login");

    private final JTextArea chatHistory = new JTextArea();
    private final JTextField msgField = new JTextField();
    private final JButton sendBtn = new JButton("Send");

    private final ServerWindow server;
    private boolean isConnected = false;

    public ClientGUI(ServerWindow server) {
        this.server = server;

        setupClientWindow();
        setupContent();
        setVisible(true);
        server.addClient(this);
    }

    private void setupClientWindow() {
        setTitle("Client window");
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void setupContent() {
        JPanel loginPanel = new JPanel(new GridLayout(2,3, 5, 5));

        // Top panel for Login
        loginPanel.add(ipField);
        loginPanel.add(portField);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel("Enter your name:"));
        loginPanel.add(nameField);
        loginPanel.add(loginBtn);
        add(loginPanel, BorderLayout.NORTH);

        // Non-editable fields (ip, port, pwd)
        ipField.setEditable(false);
        portField.setEditable(false);
        passwordField.setEditable(false);

        // Chat field
        chatHistory.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatHistory);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel (typing/sending msgs)
        JPanel inputPanelBottom = new JPanel(new BorderLayout());
        inputPanelBottom.add(msgField, BorderLayout.CENTER);
        inputPanelBottom.add(sendBtn, BorderLayout.EAST);
        add(inputPanelBottom, BorderLayout.SOUTH);

        // ActionListener (Enter key)
        msgField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // client restriction: login required to send messages
                isLoggedInOrNot();
            }
        });

        // ActionListener (Button)
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // client restriction: login required to send messages
                isLoggedInOrNot();
            }
        });

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checking if the fields on the top are filled in
                checkTopFields();
            }
        });
    }

    private void isLoggedInOrNot() {
        if (!isConnected) {
            JOptionPane.showMessageDialog(ClientGUI.this, "First you need to login.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            sendMessage();
        }
    }

    private void checkTopFields() {
        String ip = ipField.getText().trim();
        String port = portField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        clientName = nameField.getText().trim();

        if (ip.isEmpty() || port.isEmpty() || clientName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            connectToServer();
        }
    }

    private void connectToServer() {
        // Simulating a connection to a server
        if (server.isRunning()) {
            if (isConnected) {
                JOptionPane.showMessageDialog(this, "You are already connected to the server!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                isConnected = true;
                server.receiveMessage(clientName + " connected to the server.");
                loadChatHistory();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Server is not running!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Loading chat history only when client is connected to the server
    private void loadChatHistory() {
        chatHistory.setText("");
        try (BufferedReader reader = new BufferedReader(new FileReader(server.getPATH_TO_HISTORY_LOG_FILE()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                chatHistory.append(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("No previous chat history found.");
        }
    }

    private void sendMessage() {
        clientName = nameField.getText().trim();
        String message = msgField.getText().trim();
        if (!message.isEmpty()) {
            if (server.isRunning()) {
                String formattedMsg = clientName + ": " + message;
                server.receiveMessage(formattedMsg);
                msgField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "The server has stopped. Messages cannot be sent.", "Error", JOptionPane.ERROR_MESSAGE);
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
            server.receiveMessage(clientName + " disconnected from the server.");
        }
        super.dispose();
    }

    public boolean isConnected() {
        return isConnected;
    }
}