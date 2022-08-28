package Server.src;

import javax.swing.*;
import java.net.UnknownHostException;

public class Main {
    private static GUIServer guiServer;
    private static Server server;

    public static GUIServer getGuiServer() {
        return guiServer;
    }

    public static Server getServer() {
        return server;
    }
    public static void setServer(Server server) {
        Main.server = server;
    }

    static {
        try {
            guiServer = new GUIServer();
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(Main.guiServer ,"Failed start GUI server");
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        server = new Server();
        guiServer.createAndShowGUI();
    }
}
