package edu.fra.uas.net.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import edu.fra.uas.net.chat.ChatServer;
import edu.fra.uas.net.model.Group;
import edu.fra.uas.net.model.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

/**
 * <p>
 * GUI Server
 *
 * @author Mohammed Dawoud
 * @author Kaddour Alnaasan
 */
public class Server extends JFrame {

    private static final long serialVersionUID = 1L;
    private static ChatServer chatServer;
    private static List<User> users = new ArrayList<>();
    private static List<Group> groups = new ArrayList<>();

    private JPanel contentPane;
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu jMenuFile = new JMenu("File");
    private final JMenu jMenuHelp = new JMenu("Help");
    private final JMenuItem jMenuItemAbout = new JMenuItem("About");
    private final JMenuItem jMenuItemExit = new JMenuItem("Exit");
    private final JButton btnStartServer = new JButton("start Server");
    private final JButton btnStop = new JButton("stop");
    private final JLabel lblIPAdress = new JLabel("IP Adresse:");
    private final JTextField tfIPAdress = new JTextField();
    private final JLabel lblPort = new JLabel("Port:");
    private final JTextField tfPort = new JTextField();
    private static final JTable tableClients = new JTable();
    private final JButton btnDeleteClient = new JButton("Delete");
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private final JPanel panelClients = new JPanel();
    private final JPanel panelGroups = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane();
    private static final JTable tableGroups = new JTable();
    private final JScrollPane scrollPane_1 = new JScrollPane();
    private final JButton btnDeleteGroup = new JButton("Delete");

    /**
     * Create the frame.
     */
    public Server() {
        initGUI();
    }

    /**
     * Launch the application.
     *
     * @param args all variables
     */
    public static void main(String[] args) {
        users.add(new User("client1", "localhost", 1010));
        users.add(new User("client2", "localhost", 1011));
        users.add(new User("client3", "localhost", 1012));
        users.add(new User("client4", "localhost", 1013));

        Group group = new Group("Group1");
        group.setUsers(users);

        groups.add(group);
        groups.add(new Group("Group2"));
        groups.add(new Group("Group3"));
        groups.add(new Group("Group4"));
        groups.add(new Group("Group5"));

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Server frame = new Server();
                    frame.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * initGUI Server
     */
    private void initGUI() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 595, 339);

        setJMenuBar(menuBar);

        menuBar.add(jMenuFile);
        jMenuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to close the Server ?",
                        "Exit Server", dialogButton);
                if (dialogResult == 0) {
                    dispose();
                    System.exit(NORMAL);
                }

            }
        });

        jMenuFile.add(jMenuItemExit);

        menuBar.add(jMenuHelp);
        jMenuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String infoMessage = "This Chat Server is developed by Mohammed Dawoud, Kaddour Alnaasan and Harsh Mukhiya";

                JOptionPane.showMessageDialog(null, infoMessage, "About Chat_Server ", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        jMenuItemAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                jMenuItemExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });
            }
        });

        jMenuHelp.add(jMenuItemAbout);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        btnStartServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Server.chatServer = new ChatServer(tfIPAdress.getText(), Integer.parseInt(tfPort.getText()));
                    String infoMessage = "The Server is started!";
                    JOptionPane.showMessageDialog(null, infoMessage, "Server Info ", JOptionPane.INFORMATION_MESSAGE);
                    btnStartServer.setEnabled(false);
                    btnStop.setEnabled(true);
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }
        });
        btnStartServer.setBounds(10, 11, 120, 23);

        contentPane.add(btnStartServer);
        btnStop.setEnabled(false);
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to stop the Server ?",
                        "Server Info", dialogButton);
                if (dialogResult == 0) {
                    Server.chatServer.stopAndClose();
                    btnStartServer.setEnabled(true);
                    btnStop.setEnabled(false);
                }
            }
        });
        btnStop.setBounds(134, 11, 89, 23);

        contentPane.add(btnStop);
        lblIPAdress.setBounds(244, 13, 92, 19);

        contentPane.add(lblIPAdress);
        tfIPAdress.setColumns(10);
        tfIPAdress.setBounds(336, 12, 96, 20);

        contentPane.add(tfIPAdress);
        lblPort.setBounds(442, 15, 40, 14);

        contentPane.add(lblPort);
        tfPort.setText("     ");
        tfPort.setColumns(10);
        tfPort.setBounds(492, 12, 67, 20);

        contentPane.add(tfPort);

        tabbedPane.setBounds(10, 47, 569, 226);

        contentPane.add(tabbedPane);

        tabbedPane.addTab("Clients", null, panelClients, null);
        panelClients.setLayout(null);
        scrollPane.setBounds(10, 11, 549, 149);

        panelClients.add(scrollPane);
        scrollPane.setViewportView(tableClients);
        tableClients.setModel(
                new DefaultTableModel(
                        new Object[][]{
                        },
                        new String[]{
                                "Checked", "UserName", "Host", "Port"
                        }
                ) {
                    Class[] columnTypes = new Class[]{
                            Boolean.class, String.class, String.class, Integer.class
                    };

                    public Class getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                });
        tableClients.setToolTipText("");
        tableClients.setSurrendersFocusOnKeystroke(true);
        tableClients.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableClients.setForeground(Color.BLACK);
        tableClients.setFont(new Font("Sitka Text", Font.PLAIN, 11));
        tableClients.setFillsViewportHeight(true);
        tableClients.setColumnSelectionAllowed(true);
        tableClients.setBorder(UIManager.getBorder("CheckBox.border"));
        tableClients.setBackground(Color.WHITE);

        btnDeleteClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDeleteActionPerformed(tableClients);
            }
        });
        btnDeleteClient.setBounds(30, 164, 89, 23);
        panelClients.add(btnDeleteClient);

        tabbedPane.addTab("Groups", null, panelGroups, null);
        panelGroups.setLayout(null);
        scrollPane_1.setBounds(10, 11, 549, 149);

        panelGroups.add(scrollPane_1);
        scrollPane_1.setViewportView(tableGroups);
        tableGroups.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Checked", "Name of Group", "Count of Clients"
                }
        ) {
            Class[] columnTypes = new Class[]{
                    Boolean.class, String.class, Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });

        btnDeleteGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDeleteActionPerformed(tableGroups);
            }
        });
        btnDeleteGroup.setBounds(30, 164, 89, 23);

        panelGroups.add(btnDeleteGroup);

        tfIPAdress.setText("127.0.0.1");
        tfPort.setText("8080");

        for (int i = 0; i < users.size(); i++) {
            Server.addClientToTable(users.get(i));
        }
        for (Group group : groups) {
            this.addGroupToTable(group);
        }

    }

    /**
     * Starts the already initialized frame, making it visible and ready to interact
     * with the user.
     */

    public void start() {
        setVisible(true);
    }

    private void btnDeleteActionPerformed(JTable jTable) {
        DefaultTableModel tblModel = (DefaultTableModel) jTable.getModel();
        int countRow = jTable.getRowCount();
        for (int i = 0; i < countRow; i++) {
            boolean check = (boolean) jTable.getValueAt(i, 0);
            if (check) {
                tblModel.removeRow(i);
                users.remove(i);
                i--;
                countRow--;
            }
        }
    }

    /**
     * to add client to Clients-table
     *
     * @param user A Client
     * @author M.Dawoud
     */
    public static void addClientToTable(User user) {
        DefaultTableModel tableModel = (DefaultTableModel) Server.tableClients.getModel();
        tableModel.addRow(new Object[]{user.isToDelete(), user.getUsername(), user.getHost(), user.getPort()});
    }

    /**
     * to add Group to Groups-table
     *
     * @param Group A Group of Clients
     * @author M.Dawoud
     */
    public void addGroupToTable(Group group) {
        DefaultTableModel tableModel = (DefaultTableModel) Server.tableGroups.getModel();
        tableModel.addRow(new Object[]{group.isToDelete(), group.getName(), group.getUsers().size()});
    }

    /**
     * to delete client from Clients-Table
     *
     * @param username a Name of client
     * @author kalnaasan
     */
    public static void deleteUserFromTable(String username) {
        DefaultTableModel tblModel = (DefaultTableModel) Server.tableClients.getModel();
        for (int i = 0; i < Server.tableClients.getRowCount(); i++) {
            String clientUsername = (String) Server.tableClients.getValueAt(i, 1);
            if (username.equals(clientUsername)) {
                tblModel.removeRow(i);
            }
        }
    }
}
