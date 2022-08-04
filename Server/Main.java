package Server;

import javax.swing.*;
import java.net.UnknownHostException;

public class Main {
    private static GUIServer guiServer;

    static {
        try {
            guiServer = new GUIServer();
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(Main.guiServer ,"Failed start GUI server");
        }
    }

    public static void main(String[] args) throws UnknownHostException {
         guiServer.createAndShowGUI();
    }
}
