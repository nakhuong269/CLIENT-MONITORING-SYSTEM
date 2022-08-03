package Server;

import javax.swing.*;

public class Main {
    private static GUIServer guiServer;


    public static void main(String[] args) {
        guiServer = new GUIServer();

        guiServer.createAndShowGUI();
    }
}
