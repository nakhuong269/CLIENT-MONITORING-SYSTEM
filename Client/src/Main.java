package Client.src;


import java.text.ParseException;

public class Main {
    private static GUIClient guiClient = new GUIClient();

    private static Client client = new Client();

    public static GUIClient getGuiClient() {
        return guiClient;
    }

    public static Client getClient() {
        return client;
    }

    public static void main(String[] args) throws ParseException {
        guiClient.createAndShowGUI();
    }
}
