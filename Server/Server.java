package Server;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;


public class Server extends Thread{

    private String IP;
    private int Port;
    ServerSocket serverSocket;
    private ArrayList<ClientHandler> listClient;
    FileHandler fileHandler;
    public ArrayList<ClientHandler> getListClient() {
        return listClient;
    }

    BufferedWriter writerLog;

    public String getIP()
    {
        return IP;
    }

    public int getPort()
    {
        return Port;
    }


    public Server() throws UnknownHostException {
            IP = InetAddress.getLocalHost().getHostAddress();
            Port = 6000;
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(Port);
        listClient = new ArrayList<ClientHandler>();
        System.out.println("Server is listening on port " + Port);

        writerLog = new BufferedWriter(new FileWriter("Server/Log/ServerLog.txt",true));

        Thread threadServer = new Thread(){
            @Override
            public void run() {
                while (serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        Socket socket = serverSocket.accept();

                        System.out.println("New client connected " + socket);

                        //Create new thread for client
                        ClientHandler clientHandler = new ClientHandler(socket);
                        clientHandler.start();

                    } catch (IOException e) {
                        break;
                    }
                }
            }
        };
        threadServer.start();
    }

    public void CloseServer() throws IOException {
        serverSocket.close();
        writerLog.close();
    }


    public void addNewClient(ClientHandler client)
    {
        this.listClient.add(client);
        Main.getGuiServer().updateList_Client();
    }

    public void removeClient(ClientHandler client)
    {
        this.listClient.remove(client);
        Main.getGuiServer().updateList_Client();
    }

    public ClientHandler findClient(String ipClient)
    {
        for (ClientHandler client: this.listClient) {
            if(client.getSocket().getInetAddress().getHostAddress().equals(ipClient))
            {
                return client;
            }
        }
        return null;
    }

    public void WriteLog(String log) throws IOException {
        writerLog.write(log);
        writerLog.newLine();
        writerLog.flush();
    }

}
