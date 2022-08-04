package Client;


import java.text.ParseException;

public class Main {
    private static GUIClient guiClient = new GUIClient();


    public static void main(String[] args) throws ParseException {
        guiClient.createAndShowGUI();
    }
}
