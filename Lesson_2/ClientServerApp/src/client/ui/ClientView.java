package client.ui;

import client.domain.ClientController;

public interface ClientView {
    /**
     * Sets the ClientController instance for this view.
     * @param clientController the controller that manages client logic
     */
    void setClientController(ClientController clientController);

    /**
     * Displays a message in the client view.
     * @param msg the message to display
     */
    void showMessage(String msg);

    /**
     * Displays a window with text of successful connection message.
     */
    void showSuccessfulConnection();

    /**
     * Displays a window with error indicating the client is already connected.
     */
    void showErrorDoubleConnection();

    /**
     * Displays a window with error indicating the server is not running.
     */
    void showErrorServerIsNotRunning();

    /**
     * Displays a window with error indicating the server has stopped.
     */
    void showErrorServerHasStopped();

    /**
     * Displays a window with error indicating the client is not logged in.
     */
    void showErrorNotLoggedIn();
}
