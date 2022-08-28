package Client.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.file.*;
import java.time.Instant;

import static java.nio.file.StandardWatchEventKinds.*;

public class FolderMonitor extends Thread{
    private String path;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public FolderMonitor(Client client)
    {
        this.socket = client.getSocket();
        this.dis = client.getDis();
        this.dos = client.getDos();
    }

    @Override
    public void run() {
        try {
            //Send
            dos.writeUTF("Path");

            //Receive
            path = dis.readUTF();


            Path currentRelativePath = Paths.get("");
            path = currentRelativePath.toAbsolutePath().toString();

            System.out.println(path);

            //Mornitoring folder
            FileSystem fs = FileSystems.getDefault();

            WatchService ws = fs.newWatchService();
            Path pTemp = Paths.get(path);
            pTemp.register(ws, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            while (true) {
                WatchKey key = ws.take();
                for (WatchEvent<?> e : key.pollEvents()) {

                    String explain = "";
                    if(e.kind() == ENTRY_CREATE)
                    {
                        explain = "A new file " + e.context() + " was created";
                    }else if(e.kind() == ENTRY_MODIFY)
                    {
                        explain = "A file " + e.context() + " was modified";
                    }
                    else {
                        explain = "A file "+ e.context() + " was deleted";
                    }

                    //Log Server
                    String logServer= Instant.now() + "|"
                            + e.kind().name().replace("ENTRY_","") + "|"
                            + socket.getLocalAddress().getHostAddress() + "|" + explain + " " + path;
                    //Send LogServer
                    dos.writeUTF("Log");
                    dos.writeUTF(logServer);

                    //Log Client
                    String logClient = Instant.now() + "|"
                            + e.kind().name().replace("ENTRY_","") + "|"
                            + explain + " " + path;

                    //Show Log JTable
                    Main.getGuiClient().fillTable(logClient);

                    //Write LogClient
                    Main.getClient().WriteLog(logClient);
                }
                key.reset();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
