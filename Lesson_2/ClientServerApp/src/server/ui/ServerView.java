package server.ui;

import server.domain.ServerController;

public interface ServerView {
    /**
     * Displays a message in the server view, typically in the server's log.
     *
     * @param msg the message to display in the view.
     */
    void showMessage(String msg);

    /**
     * Sets the ServerController for this view to allow interaction with the server's logic.
     *
     * @param server the server controller to be associated with the view.
     */
    void setServerController(ServerController server);
}
