package Server;


import Client.Client;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


public class Server{

    private String IP;
    private int Port;
    ServerSocket serverSocket;
    private ArrayList<Client> listClient;

    public ArrayList<Client> getListClient() {
        return listClient;
    }

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
        listClient = new ArrayList<Client>();
        System.out.println("Server is listening on port " + Port);


        //Tạo thread riêng cho server
        Thread threadServer = new Thread(() -> {
            while (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();

                    System.out.println("New client connected " + socket);

                    //Tạo thread cho từng client
                    ClientHandler clientHandler = new ClientHandler(socket);
                    clientHandler.start();

                } catch (IOException e) {
                    break;
                }
            }
        });
        threadServer.start();
    }

    public void CloseServer() throws IOException {
        serverSocket.close();
    }


    public void addNewClient(Client client)
    {
        this.listClient.add(client);
        System.out.println(this.listClient);
        Main.getGuiServer().updateList_Client();
    }

    public Client findClient(String ipClient)
    {
        for (Client client: this.listClient) {
            if(client.getSocket().getInetAddress().getHostAddress().equals(ipClient))
            {
                return client;
            }
        }
        return null;
    }

}
