package Server;

import Client.Client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class GUIServer extends JPanel implements ActionListener{
    JPanel pn1, pn2, pn3, pn4, pn_list_client, pn_action_client, pn_search_client;
    boolean btnSaveModeServer  = false;

    JTextField tf_search_client;

    String[] colHeader = {"ID","Time", "Action", "IP Client", "Explain"};
    JList list_client, list_search_client;
    JLabel lb_server_ip, lb_server_port;

    JTable tableAction;
    JButton btnStart, btnSearchClient;

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

        pn_list_client = new JPanel(new BorderLayout());
        pn_list_client.setBorder(BorderFactory.createTitledBorder("List Client"));
        tf_search_client = new JTextField(null,8);
        pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2,BoxLayout.X_AXIS));
        btnSearchClient = new JButton("Search");
        btnSearchClient.addActionListener(this);
        btnSearchClient.setActionCommand("btnSearch");
        pn2.add(tf_search_client);
        pn2.add(Box.createRigidArea(new Dimension(7, 2)));
        pn2.add(btnSearchClient);

        DefaultListModel model_list_client = new DefaultListModel();
        list_client = new JList();
        list_client.setModel(model_list_client);

        DefaultListModel model_search_client = new DefaultListModel();
        list_search_client = new JList();
        list_search_client.setVisibleRowCount(3);
        list_search_client.setModel(model_search_client);

        pn_search_client = new JPanel();
        pn_search_client.setLayout(new BoxLayout(pn_search_client,BoxLayout.Y_AXIS));
        pn_search_client.add(pn2);
        pn_search_client.add(Box.createRigidArea(new Dimension(0, 6)));
        pn_search_client.add(new JScrollPane(list_search_client));
        pn_search_client.setBorder(BorderFactory.createTitledBorder("Search Client"));


        pn_list_client.add(pn_search_client,BorderLayout.PAGE_END);
        pn_list_client.add(new JScrollPane(list_client),BorderLayout.CENTER);

        pn_action_client = new JPanel();
        pn_action_client.setLayout(new BorderLayout());
        pn_action_client.setBorder(BorderFactory.createTitledBorder("Action of clients"));

        DefaultTableModel tableModel = new DefaultTableModel(colHeader,0);
        tableAction = new JTable(tableModel);
        tableAction.setDefaultEditor(Object.class,null);

        pn_action_client.add(new JScrollPane(tableAction),BorderLayout.CENTER);

        add(pn1, BorderLayout.PAGE_START);
        add(pn_list_client, BorderLayout.EAST);
        add(pn_action_client,BorderLayout.CENTER);
    }

    public void updateList_Client()
    {
        DefaultListModel model1 = (DefaultListModel) list_client.getModel();
        model1.clear();
        for(int i = 0 ; i< Main.getServer().getListClient().size();i++)
        {
            model1.addElement(Main.getServer().getListClient().get(i).getSocket().getInetAddress().getHostAddress());
        }
    }

    public void createAndShowGUI() throws UnknownHostException {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = GUIServer.this;
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.setMinimumSize(new Dimension(700,400));
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
                    Main.getServer().startServer();

                    lb_server_ip.setText("IP : " + Main.getServer().getIP());
                    lb_server_port.setText("Port : " + Main.getServer().getPort());

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
                        Main.getServer().CloseServer();

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
        else if(str.equals("btnSearch"))
        {
            if(btnSaveModeServer == true) {
                DefaultListModel model_search_client = (DefaultListModel) list_search_client.getModel();
                model_search_client.clear();
                if (Main.getServer().findClient(tf_search_client.getText()) != null) {

                    model_search_client.addElement(Main.getServer().findClient(tf_search_client.getText()).getSocket().getInetAddress().getHostAddress());
                } else {
                    model_search_client.clear();
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
        rowdata[4] = data[3];
        model.addRow(rowdata);
    }
}
