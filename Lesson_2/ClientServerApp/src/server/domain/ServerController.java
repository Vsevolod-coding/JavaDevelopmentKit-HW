package server.domain;

import client.domain.ClientController;
import server.repository.Repository;
import server.ui.ServerView;

import java.util.ArrayList;
import java.util.List;

/**
 * The ServerController class manages server operations, client connections,
 * and message broadcasting. It interacts with the server repository for message
 * history and controls the server view to display server status and messages.
 */
public class ServerController {
    private boolean isRunning = false;
    private ServerView serverView;
    private List<ClientController> clients; // List of connected clients
    private Repository<String> repository;

    /**
     * Constructs a ServerController with the specified view and repository.
     *
     * @param serverView   the view for displaying server messages and status.
     * @param repository   the repository for managing message history.
     */
    public ServerController(ServerView serverView, Repository<String> repository) {
        this.serverView = serverView;
        this.repository = repository;
        clients = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void startServer() {
        repository.checkHistoryFile();
        isRunning = true;
        serverView.showMessage("Server started.");
    }

    public void stopServer() {
        isRunning = false;
        serverView.showMessage("Server stopped.");
    }

    /**
     * Connects a client to the server and displays a connection message.
     *
     * @param client the client to connect to the server.
     */
    public void connectClient(ClientController client) {
        clients.add(client);
        serverView.showMessage(client.getClientName() + " connected to the server.");
    }

    /**
     * Disconnects a client from the server and displays a disconnection message.
     *
     * @param client the client to disconnect from the server.
     */
    public void disconnectClient(ClientController client) {
        clients.remove(client);
        serverView.showMessage(client.getClientName() + " disconnected from the server.");
    }

    /**
     * Broadcasts a message to all connected clients and updates the server view.
     *
     * @param message the message to broadcast.
     */
    public void sendMsgToAll(String message) {
        if (isRunning) {
            serverView.showMessage(message);
            updateClients(message);
            saveToHistoryFile(message);
        }
    }

    /**
     * Sends a message to each connected client.
     *
     * @param message the message to send to clients.
     */
    private void updateClients(String message) {
        for (ClientController client : clients) {
            if (client.getIsConnected()) {
                client.receiveMessage(message + System.lineSeparator());
            }
        }
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    private void saveToHistoryFile(String message) {
        repository.save(message);
    }

    public String loadHistory() {
        return repository.load();
    }
}
