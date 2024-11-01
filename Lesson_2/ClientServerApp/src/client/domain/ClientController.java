package client.domain;

import client.ui.ClientView;
import server.domain.ServerController;

/**
 * Controls client interactions, managing server connection, message sending, and status updates.
 */
public class ClientController {
    private String clientName;
    private ServerController serverController;
    private boolean isConnected = false;
    private ClientView clientView;

    /**
     * Initializes the controller with a client view and server controller.
     * @param clientView the view for displaying messages and errors
     * @param serverController the server to connect to
     */
    public ClientController(ClientView clientView, ServerController serverController) {
        this.clientView = clientView;
        this.serverController = serverController;
        clientView.setClientController(this);
    }

    /**
     * Attempts to connect to the server with the given client name.
     * @param clientName the name of the client
     */
    public void connectToServer(String clientName) {
        this.clientName = clientName;
        // Simulating a connection to a server
        if (serverController.getIsRunning()) {
            if (isConnected) {
                showErrorDoubleConnection();
            } else {
                isConnected = true;
                serverController.connectClient(this);
                String chatHistory = serverController.loadHistory();
                if (chatHistory != null) {
                    showMessage(chatHistory);
                }
                showSuccessfulConnection();
            }
        } else {
            showErrorServerIsNotRunning();
        }
    }

    /**
     * Disconnects the client when the window closes.
     */
    public void disconnectOnWindowClose() {
        if (isConnected) {
            isConnected = false;
            serverController.disconnectClient(this);
        }
    }

    /**
     * Sends a message to the view to display.
     * @param message the message to show
     */
    private void showMessage(String message) {
        clientView.showMessage(message);
    }

    /**
     * Receives a message from the server and displays it in the client view.
     * @param message the received message
     */
    public void receiveMessage(String message) {
        showMessage(message);
    }

    /**
     * Sends a message to the server if the client is connected.
     * @param message the message to send
     */
    public void sendMessage(String message) {
        // client restriction: login required to send messages
        if (isConnected) {
            if (!message.isEmpty()) {
                if (serverController.getIsRunning()) {
                    serverController.sendMsgToAll(clientName + ": " + message);
                } else {
                    showErrorServerHasStopped();
                }
            }
        } else {
            showErrorNotLoggedIn();
        }
    }

    // Display methods for various connection and error statuses
    private void showSuccessfulConnection() {
        clientView.showSuccessfulConnection();
    }

    private void showErrorDoubleConnection() {
        clientView.showErrorDoubleConnection();
    }

    private void showErrorServerIsNotRunning() {
        clientView.showErrorServerIsNotRunning();
    }

    private void showErrorNotLoggedIn() {
        clientView.showErrorNotLoggedIn();
    }

    private void showErrorServerHasStopped() {
        clientView.showErrorServerHasStopped();
    }

    public String getClientName() {
        return clientName;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
