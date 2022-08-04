package Client;

import Server.GUIServer;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

public class GUIClient extends JPanel implements ActionListener {
    JPanel pn1, pn2, pn3, pn4;
    boolean btnSaveModeClient  = false;
    JLabel lb_client_ip, lb_client_port;
    JFormattedTextField ftf_client_ip, ftf_client_port;

    JButton btnConnect;

    Client client = new Client();

    public GUIClient(){
        super(new BorderLayout());


        pn1 = new JPanel();

        lb_client_ip = new JLabel();
        lb_client_ip.setForeground(Color.RED);
        lb_client_port = new JLabel();
        lb_client_port.setForeground(Color.RED);

        try {
            ftf_client_ip = new JFormattedTextField(new MaskFormatter("***.***.***.***"));
            ftf_client_ip.setFont(new Font("Arial", Font.BOLD, 12));
            ftf_client_ip.setPreferredSize(new Dimension(100, 25));
            ftf_client_port = new JFormattedTextField();
            ftf_client_port.setFont(new Font("Arial", Font.BOLD, 12));
            ftf_client_port.setPreferredSize(new Dimension(100, 25));
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Please input valid IP");
        }

        pn1.add(lb_client_ip);
        pn1.add(Box.createRigidArea(new Dimension(10,0)));
        pn1.add(ftf_client_ip);
        pn1.add(Box.createRigidArea(new Dimension(10,0)));
        pn1.add(lb_client_port);
        pn1.add(Box.createRigidArea(new Dimension(10,0)));
        pn1.add(ftf_client_port);

        lb_client_ip.setText("IP : ");
        lb_client_port.setText("Port : ");


        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(this);
        btnConnect.setActionCommand("btnConnect");
        pn1.add(Box.createRigidArea(new Dimension(20, 0)));
        pn1.add(btnConnect);




        add(pn1, BorderLayout.PAGE_START);
    }
    public void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new GUIClient();
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
        if(str.equals("btnConnect"))
        {
            if(btnSaveModeClient == false) {
                try {
                    client.startClient(ftf_client_ip.getText(), Integer.parseInt(ftf_client_port.getText()));
                    btnConnect.setText("Disconnect");
                    btnSaveModeClient = true;
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,"Connect failed");
                }catch (NumberFormatException nex)
                {
                    JOptionPane.showMessageDialog(this,"Please input valid Port");
                }
            }
            else {
                int choose = JOptionPane.showConfirmDialog(this, "Do you want to disconnect?", "Close Client", JOptionPane.YES_NO_OPTION);
                if (choose == JOptionPane.YES_OPTION) {
                    try {
                        client.closeSocket();
                        btnSaveModeClient = false;
                        btnConnect.setText("Connect");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,"Failed");
                    }
                }
            }
        }
    }
}
