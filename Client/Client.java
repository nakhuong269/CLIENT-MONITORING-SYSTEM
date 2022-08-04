package Client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Client(){ }

    public Socket getSocket() {
        return socket;
    }

    public void startClient(String ipServer, int portServer) throws IOException
    {
        socket = new Socket(ipServer, portServer);
    }

    public void closeSocket() throws IOException {
        socket.close();
    }
}
