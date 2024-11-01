import client.domain.ClientController;
import client.ui.ClientGUI;
import server.domain.ServerController;
import server.repository.FileIO;
import server.ui.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerController server = new ServerController(new ServerWindow(), new FileIO());

        new ClientController(new ClientGUI(), server);
        new ClientController(new ClientGUI(), server);
    }
}