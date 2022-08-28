package Server.src;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private  Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.dis = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = dis.readUTF();
                System.out.println("Receive: " + received);

                if (received.equals("Connect")) {
                    Main.getServer().addNewClient(this);
                } else if (received.equals("Close")) {
                    Main.getServer().removeClient(this);
                    dis.close();
                    dos.close();
                    socket.close();
                    break;
                } else if (received.equals("Log")) {
                    String log = dis.readUTF();
                    System.out.println(log);
                    Main.getServer().WriteLog(log);
                    Main.getGuiServer().fillTable(log);
                }else if(received.equals("Path"))
                {
                    dos.writeUTF("C:/Users/AD/Downloads");
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
