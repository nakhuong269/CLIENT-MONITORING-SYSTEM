package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.*;
import java.time.Instant;

import static java.nio.file.StandardWatchEventKinds.*;


public class Client{
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void startClient(String ipServer, int portServer) throws IOException {
        this.socket = new Socket(ipServer, portServer);
        this.dis = new DataInputStream(this.socket.getInputStream());
        this.dos = new DataOutputStream(this.socket.getOutputStream());

        dos.writeUTF("Connect");

        String log =  socket.getLocalAddress().getHostAddress() + "|" + "LOG-IN" + "|" + " "
                + "|" + Instant.now() + "|";
        dos.writeUTF("Log");
        dos.writeUTF(log);

        WatchFolder();
    }

    public void closeSocket() throws IOException, CloneNotSupportedException {
        String log =  socket.getLocalAddress().getHostAddress() + "|" + "LOG-OUT" + "|" + " "
                + "|" + Instant.now() + "|";
        dos.writeUTF("Log");
        dos.writeUTF(log);

        dos.writeUTF("Close");
        dos.close();
        dis.close();
        socket.close();
    }

    public String toString()
    {
        return this.socket.toString();
    }


    public void WatchFolder()
    {
        FolderMonitor folderMonitor = new FolderMonitor(this);
        folderMonitor.start();
    }
}
