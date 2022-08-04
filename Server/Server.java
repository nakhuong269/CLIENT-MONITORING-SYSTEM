package Server;


import java.io.IOException;
import java.net.*;



public class Server{

    private String IP;
    private int Port;
    ServerSocket serverSocket;

    public Server() throws UnknownHostException {
            IP = InetAddress.getLocalHost().getHostAddress();
            Port = 6000;
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(Port);
    }

    public void CloseSocket() throws IOException {
        serverSocket.close();
    }

    public String getIP()
    {
        return IP;
    }

    public int getPort()
    {
        return Port;
    }
}
