package client.ui;

import client.domain.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Graphical User Interface (GUI) for the client.
 * Implements ClientView interface to connect with ClientController.
 */
public class ClientGUI extends JFrame implements ClientView {
    private final static int WIN_WIDTH = 400;
    private final static int WIN_HEIGHT = 500;

    private JTextField ipField;
    private JTextField portField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JButton loginBtn;

    private JTextArea chatHistory;
    private JTextField msgField;
    private JButton sendBtn;

    private ClientController clientController;

    /**
     * Constructor that sets up the client window and content.
     */
    public ClientGUI() {
        setupClientWindow();
        setupContent();
        setVisible(true);
    }

    private void setupClientWindow() {
        setTitle("Client window");
        setSize(WIN_WIDTH,WIN_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Adds main components to the window: header, chat history, and message input.
     */
    private void setupContent() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createChatHistory(), BorderLayout.CENTER);
        add(createBottom(), BorderLayout.SOUTH);
    }

    /**
     * Creates the header panel with fields for IP, port, password, and name, as well as a login button.
     * @return the header panel
     */
    private Component createHeaderPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(2,3, 5, 5));
        ipField = new JTextField("localhost");
        portField = new JTextField("8888");
        passwordField = new JPasswordField("12345678");
        nameField = new JTextField();
        loginBtn = new JButton("Login");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validates fields and attempts connection
                checkTopFieldsAndConnect();
            }
        });

        // Non-editable fields (ip, port, pwd)
        ipField.setEditable(false);
        portField.setEditable(false);
        passwordField.setEditable(false);

        loginPanel.add(ipField);
        loginPanel.add(portField);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel("Enter your name:"));
        loginPanel.add(nameField);
        loginPanel.add(loginBtn);

        return loginPanel;
    }

    /**
     * Creates a non-editable text area for displaying chat history.
     * @return scrollable chat history area
     */
    private Component createChatHistory() {
        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        return new JScrollPane(chatHistory);
    }

    /**
     * Creates the bottom panel with a text field for message input and a send button.
     * @return the bottom panel
     */
    private Component createBottom() {
        JPanel inputPanelBottom = new JPanel(new BorderLayout());

        msgField = new JTextField();
        // Sends message on Enter key
        msgField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(msgField.getText());
            }
        });

        sendBtn = new JButton("Send");
        // Sends message on button click
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(msgField.getText());
            }
        });

        inputPanelBottom.add(msgField, BorderLayout.CENTER);
        inputPanelBottom.add(sendBtn, BorderLayout.EAST);

        return inputPanelBottom;
    }

    /**
     * Checks if all required fields are filled before attempting a connection.
     */
    private void checkTopFieldsAndConnect() {
        String ip = ipField.getText().trim();
        String port = portField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String clientName = nameField.getText().trim();

        if (ip.isEmpty() || port.isEmpty() || clientName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            clientController.connectToServer(clientName);
        }
    }

    /**
     * Sends the typed message via the ClientController.
     * @param message the message to be sent
     */
    private void sendMessage(String message) {
        clientController.sendMessage(message);
        msgField.setText("");
    }

    @Override
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void showMessage(String msg) {
        chatHistory.append(msg);
    }

    @Override
    public void showSuccessfulConnection() {
        JOptionPane.showMessageDialog(this, "You have successfully connected to the server!", "Connected", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showErrorDoubleConnection() {
        JOptionPane.showMessageDialog(this, "You are already connected to the server!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showErrorServerIsNotRunning() {
        JOptionPane.showMessageDialog(this, "Server is not running!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showErrorServerHasStopped() {
        JOptionPane.showMessageDialog(this, "The server has stopped. Messages cannot be sent.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showErrorNotLoggedIn() {
        JOptionPane.showMessageDialog(ClientGUI.this, "First you need to login.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void dispose() {
        clientController.disconnectOnWindowClose(); // Disconnects client on window close
        super.dispose();
    }
}