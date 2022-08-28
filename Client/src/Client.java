package Client.src;

import java.io.*;
import java.net.Socket;
import java.time.Instant;


public class Client{
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    BufferedWriter writerLog;
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

        writerLog = new BufferedWriter(new FileWriter("ClientLog.txt",true));


        dos.writeUTF("Connect");

        //Log for server
        String logServer =  Instant.now() + "|" + "LOG-IN" + "|" + socket.getLocalAddress().getHostAddress() + "| ";
        dos.writeUTF("Log");
        dos.writeUTF(logServer);

        //Log for client
        String logClient =  Instant.now() + "|" + "LOG-IN" + "| ";

        //Show Log JTable
        Main.getGuiClient().fillTable(logClient);

        //Write ClientLog
        WriteLog(logClient);


        //Monitoring
        WatchFolder();
    }

    public void closeSocket() throws IOException{
        //Log for Server
        String logServer =  Instant.now() + "|" + "LOG-OUT" + "|" + socket.getLocalAddress().getHostAddress() + "| ";
        dos.writeUTF("Log");
        dos.writeUTF(logServer);

        //Send message for server
        dos.writeUTF("Close");
        dos.close();
        dis.close();
        socket.close();

        //Log for Client
        String logClient =  Instant.now() + "|" + "LOG-OUT" + "| ";

        //Show Log JTable
        Main.getGuiClient().fillTable(logClient);

        //Write ClientLog
        WriteLog(logClient);

        writerLog.close();
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
    public void WriteLog(String log) throws IOException {
        writerLog.write(log);
        writerLog.newLine();
        writerLog.flush();
    }
}
