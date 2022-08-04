package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GUIServer extends JPanel implements ActionListener{

    Server server = new Server();
    JPanel pn1, pn2, pn3, pn4;


    boolean btnSaveModeServer  = false;

    JLabel lb_server_ip;
    JLabel lb_server_port;
    JLabel lb2;
    JLabel lb3;

    CardLayout clMain;

    JButton btnStart;

    BoxLayout bl1;

    public GUIServer() throws UnknownHostException {
        super(new BorderLayout());


        pn1 = new JPanel();

        lb_server_ip = new JLabel();
        lb_server_ip.setForeground(Color.RED);
        lb_server_port = new JLabel();
        lb_server_port.setForeground(Color.RED);
        pn1.add(lb_server_ip);
        pn1.add(Box.createRigidArea(new Dimension(20,0)));
        pn1.add(lb_server_port);

        lb_server_ip.setText("IP : ");
        lb_server_port.setText("Port : ");


        btnStart = new JButton("Start");
        btnStart.addActionListener(this);
        btnStart.setActionCommand("btnStart");
        pn1.add(Box.createRigidArea(new Dimension(20, 0)));
        pn1.add(btnStart);




        add(pn1, BorderLayout.PAGE_START);
    }

    public void createAndShowGUI() throws UnknownHostException {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new GUIServer();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.setMinimumSize(new Dimension(500,400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if(str.equals("btnStart"))
        {
            if(btnSaveModeServer == false) {
                try {
                    server.startServer();

                    lb_server_ip.setText("IP : " + server.getIP());
                    lb_server_port.setText("Port : " + server.getPort());

                    btnSaveModeServer = true;
                    btnStart.setText("Close");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,"Failed start server");
                }
            }
            else {
                int choose = JOptionPane.showConfirmDialog(this, "Do you want to disconnect?", "Close Server", JOptionPane.YES_NO_OPTION);
                if (choose == JOptionPane.YES_OPTION) {
                    try {
                        server.CloseSocket();

                        lb_server_ip.setText("IP : ");
                        lb_server_port.setText("Port : ");

                        btnStart.setText("Start");
                        btnSaveModeServer = false;

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Failed");
                    }
                }
            }
        }
    }
}
