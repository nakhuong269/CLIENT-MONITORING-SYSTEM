package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.file.*;
import java.time.Instant;

import static java.nio.file.StandardWatchEventKinds.*;

public class FolderMonitor extends Thread{
    private String path = "C:/Users/AD/Downloads";
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
            //Mornitoring folder
            FileSystem fs = FileSystems.getDefault();

            WatchService ws = fs.newWatchService();
            Path pTemp = Paths.get(path);
            pTemp.register(ws, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            while (true) {
                WatchKey key = ws.take();
                for (WatchEvent<?> e : key.pollEvents()) {
                    String log =  socket.getLocalAddress().getHostAddress() + "|" + e.kind().name() + "|" + e.context()
                            + "|" + Instant.now() + "|";
                    dos.writeUTF("Log");
                    dos.writeUTF(log);
                    System.out.println(log);
                }
                key.reset();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
