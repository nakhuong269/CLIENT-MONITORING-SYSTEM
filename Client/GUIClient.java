package Client;

import Server.GUIServer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    JPanel pn1, pn_action;
    boolean btnSaveModeClient  = false;
    JLabel lb_client_ip, lb_client_port;
    JTextField tf_client_ip, tf_client_port;
    String[] colHeader = {"ID","Time", "Action", "Explain"};

    JTable tableAction;
    JButton btnConnect;

    public GUIClient(){
        super(new BorderLayout());


        pn1 = new JPanel();

        lb_client_ip = new JLabel();
        lb_client_ip.setForeground(Color.RED);
        lb_client_port = new JLabel();
        lb_client_port.setForeground(Color.RED);

        tf_client_ip = new JTextField(null, 10);
        tf_client_ip.setFont(new Font("Arial", Font.BOLD, 12));
        tf_client_ip.setPreferredSize(new Dimension(100, 25));
        tf_client_port = new JTextField();
        tf_client_port.setFont(new Font("Arial", Font.BOLD, 12));
        tf_client_port.setPreferredSize(new Dimension(100, 25));

        pn1.add(lb_client_ip);
        pn1.add(Box.createRigidArea(new Dimension(10,0)));
        pn1.add(tf_client_ip);
        pn1.add(Box.createRigidArea(new Dimension(10,0)));
        pn1.add(lb_client_port);
        pn1.add(Box.createRigidArea(new Dimension(10,0)));
        pn1.add(tf_client_port);

        lb_client_ip.setText("IP : ");
        lb_client_port.setText("Port : ");


        btnConnect = new JButton("Connect");
        btnConnect.addActionListener(this);
        btnConnect.setActionCommand("btnConnect");
        pn1.add(Box.createRigidArea(new Dimension(20, 0)));
        pn1.add(btnConnect);

        DefaultTableModel tableModel = new DefaultTableModel(colHeader,0);
        tableAction = new JTable(tableModel);
        tableAction.setDefaultEditor(Object.class,null);

        pn_action = new JPanel();
        pn_action.setLayout(new BorderLayout());
        pn_action.setBorder(BorderFactory.createTitledBorder("Action"));
        pn_action.add(new JScrollPane(tableAction),BorderLayout.CENTER);

        add(pn1, BorderLayout.PAGE_START);
        add(pn_action,BorderLayout.CENTER);
    }
    public void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = GUIClient.this;
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
                    Main.getClient().startClient(tf_client_ip.getText(), Integer.parseInt(tf_client_port.getText()));
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
                        Main.getClient().closeSocket();
                        btnSaveModeClient = false;
                        btnConnect.setText("Connect");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this,"Failed");
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }
    public void fillTable(String log)
    {
        DefaultTableModel model = (DefaultTableModel) tableAction.getModel();
        Object [] rowdata = new Object[5];
        String[] data = log.split("\\|");

        rowdata[0] = model.getRowCount()+1;
        rowdata[1] = data[0];
        rowdata[2] = data[1];
        rowdata[3] = data[2];
        model.addRow(rowdata);
    }
}
