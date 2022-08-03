package Server;


import java.io.IOException;
import java.net.*;



public class Server{

    private String IP;
    private int Port;
    ServerSocket serverSocket;

    public Server() {
        try
        {
            IP = InetAddress.getLocalHost().getHostAddress();
            Port = 6000;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void startServer()
    {
        try  {
            serverSocket = new ServerSocket(Port);

            System.out.println("Server is listening on port " + Port);

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void CloseSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
