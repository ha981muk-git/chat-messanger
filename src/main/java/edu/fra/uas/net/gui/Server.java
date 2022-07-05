package edu.fra.uas.net.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
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
import edu.fra.uas.net.utill.Constant;
import edu.fra.uas.net.utill.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Objects;

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
    private static List<Group> groups = new ArrayList<>();

    private JPanel contentPane = new JPanel();
    private final JMenuBar menuBarServer = new JMenuBar();
    private final JMenu jMenuFile = new JMenu("File");
    private final JMenu jMenuHelp = new JMenu("Help");
    private final JMenuItem jMenuItemAbout = new JMenuItem("About");
    private final JMenuItem jMenuItemExit = new JMenuItem("Exit");
    private final JButton btnStartServer = new JButton("start Server");
    private final JButton btnStopServer = new JButton("stop");
    private final JLabel lblIPAddress = new JLabel("IP Address:");
    private final JTextField tfIPAddress = new JTextField();
    private final JLabel lblPort = new JLabel("Port:");
    private final JTextField tfPort = new JTextField();
    private JTable tableClients = new JTable();
    private final JButton btnDeleteClient = new JButton("Delete");
    private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
    private final JPanel panelClients = new JPanel();
    private final JPanel panelGroups = new JPanel();
    private final JScrollPane scrollPaneClients = new JScrollPane();
    private JTable tableGroups = new JTable();
    private final JScrollPane scrollPaneGroups = new JScrollPane();
    private final JButton btnDeleteGroup = new JButton("Delete");
    private static final String HOSTNAME = "127.0.0.1";


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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(Constant.FRAME_X, Constant.FRAME_Y, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

        initMenuBar();
        initAddActionListener();
        initTables();

        contentPane.setBorder(new EmptyBorder(Constant.BORDER, Constant.BORDER, Constant.BORDER, Constant.BORDER));
        contentPane.setLayout(null);

        btnStartServer.setBounds(10, 11, 120, Constant.BUTTON_HEIGHT);
        contentPane.add(btnStartServer);

        btnStopServer.setEnabled(false);
        btnStopServer.setBounds(134, 11, 89, Constant.BUTTON_HEIGHT);
        contentPane.add(btnStopServer);

        lblIPAddress.setBounds(244, 13, 92, 19);
        contentPane.add(lblIPAddress);

        tfIPAddress.setText(Server.HOSTNAME);
        tfIPAddress.setColumns(10);
        tfIPAddress.setBounds(336, 12, 96, 20);
        contentPane.add(tfIPAddress);

        lblPort.setBounds(442, 15, 40, 14);
        contentPane.add(lblPort);

        tfPort.setText(String.valueOf(Constant.SERVER_PORT));
        tfPort.setColumns(10);
        tfPort.setBounds(492, 12, 67, 20);
        contentPane.add(tfPort);

        tabbedPane.setBounds(10, 47, 569, 226);
        contentPane.add(tabbedPane);

        panelClients.setLayout(null);
        scrollPaneClients.setBounds(10, 11, Constant.SCROLL_TAB_PANE_WIDTH, Constant.SCROLL_TAB_PANE_HEIGHT);
        scrollPaneClients.setViewportView(tableClients);
        panelClients.add(scrollPaneClients);
        tabbedPane.addTab("Clients", null, panelClients, null);

        btnDeleteClient.setBounds(Constant.BUTTON_DELETE_X, Constant.BUTTON_DELETE_Y, 90, 25);
        panelClients.add(btnDeleteClient);

        panelGroups.setLayout(null);
        scrollPaneGroups.setBounds(10, 11, Constant.SCROLL_TAB_PANE_WIDTH, Constant.SCROLL_TAB_PANE_HEIGHT);
        scrollPaneGroups.setViewportView(tableGroups);
        panelGroups.add(scrollPaneGroups);
        tabbedPane.addTab("Groups", null, panelGroups, null);

        btnDeleteGroup.setBounds(Constant.BUTTON_DELETE_X, Constant.BUTTON_DELETE_Y, 90, 25);
        panelGroups.add(btnDeleteGroup);

        for (Group group : groups) {
            addGroupToTable(group);
        }
        this.setContentPane(contentPane);
    }

    /**
     * to add Menubar to GUI
     */
    private void initMenuBar() {
        this.setJMenuBar(this.menuBarServer);
        this.jMenuFile.add(this.jMenuItemExit);
        this.menuBarServer.add(this.jMenuFile);
        this.jMenuHelp.add(this.jMenuItemAbout);
        this.menuBarServer.add(this.jMenuHelp);
    }

    /**
     * to add Clients- andGroups-Table to GUI
     */
    private void initTables() {
        Objects[][] data = new Objects[][]{};
        String[] columnsClient = new String[]{"Checked", "UserName", "Host", "Port"};
        String[] columnsGroup = new String[]{"Checked", "Name of Group", "Count of Clients"};

        DefaultTableModel tableModelClients = new DefaultTableModel(data, columnsClient) {
            Class[] columnTypes = new Class[]{Boolean.class, String.class, String.class, Integer.class};

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column==0;
            }
        };

        tableClients.setModel(tableModelClients);
        this.setDesignTable(tableClients);

        DefaultTableModel tableModelGroups = new DefaultTableModel(data, columnsGroup) {
            Class[] columnTypes = new Class[]{Boolean.class, String.class, Integer.class};

            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column==0;
            }
        };
        tableGroups.setModel(tableModelGroups);
        this.setDesignTable(tableGroups);
    }

    /**
     * to set design to table
     *
     * @param table {@link JTable}
     */
    private void setDesignTable(JTable table) {
        table.setToolTipText("");
        table.setSurrendersFocusOnKeystroke(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Sitka Text", Font.PLAIN, 11));
        table.setFillsViewportHeight(true);
        table.setColumnSelectionAllowed(true);
        table.setBorder(UIManager.getBorder("CheckBox.border"));
        table.setBackground(Color.WHITE);
    }

    /**
     * to add action listener
     */
    private void initAddActionListener() {
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
        jMenuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String infoMessage = "This Chat Server is developed by ";
                infoMessage += "Mohammed Dawoud, Kaddour Alnaasan and Harsh Mukhiya";
                JOptionPane.showMessageDialog(null, infoMessage, "About Chat_Server ", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnStartServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        btnStopServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
        btnDeleteClient.addActionListener(new ActionListener() { //Client
            public void actionPerformed(ActionEvent e) {
                btnDeleteActionPerformedUsers(tableClients);
            }
        });
        btnDeleteGroup.addActionListener(new ActionListener() {   //Delete Group
            public void actionPerformed(ActionEvent e) {
                btnDeleteActionPerformedGroups(tableGroups);
            }
        });
    }

    /**
     * to start chatServer
     */
    private void startServer() {
        try {
            Server.chatServer = new ChatServer(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            this.attachObserver();
            btnStartServer.setEnabled(false);
            btnStopServer.setEnabled(true);
            tfIPAddress.setEnabled(false);
            tfPort.setEnabled(false);
            String infoMessage = "The Server is started!";
            JOptionPane.showMessageDialog(null, infoMessage, "Server Info ", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    /**
     * to stop chatServer
     */
    private void stopServer() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to stop the Server ?",
                "Server Info", dialogButton);
        if (dialogResult == 0) {
            Server.chatServer.stopAndClose();
            btnStartServer.setEnabled(true);
            btnStopServer.setEnabled(false);
            tfIPAddress.setEnabled(true);
            tfPort.setEnabled(true);
        }
    }

    /**
     * Starts the already initialized frame, making it visible and ready to interact
     * with the user.
     */
    public void start() {
        setVisible(true);
    }

    private void btnDeleteActionPerformedUsers(JTable jTable) {
        DefaultTableModel tblModel = (DefaultTableModel) jTable.getModel();
        int countRow = jTable.getRowCount();
        for (int i = 0; i < countRow; i++) {
            boolean check = (boolean) jTable.getValueAt(i, 0);
            if (check) {
                tblModel.removeRow(i);
                i--;
                countRow--;
            }
        }
    }

    private void btnDeleteActionPerformedGroups(JTable jTable) {
        DefaultTableModel tblModel = (DefaultTableModel) jTable.getModel();
        int countRow = jTable.getRowCount();
        for (int i = 0; i < countRow; i++) {
            boolean check = (boolean) jTable.getValueAt(i, 0);
            if (check) {
                tblModel.removeRow(i);
                groups.remove(i);
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
    private void addClientToTable(User user) {
        DefaultTableModel tableModel = (DefaultTableModel) this.tableClients.getModel();
        tableModel.addRow(new Object[]{user.isToDelete(), user.getUsername(), user.getHost(), user.getPort()});
    }

    /**
     * to add Group to Groups-table
     *
     * @param group A Group of Clients
     * @author M.Dawoud
     */
    private void addGroupToTable(Group group) {
        boolean isFound = false;
        DefaultTableModel tableModel = (DefaultTableModel) this.tableGroups.getModel();
        for (int i = 0; i < this.tableGroups.getRowCount(); i++) {
            String nameGroup = this.tableGroups.getValueAt(i, 1).toString();
            if (group.getName().equals(nameGroup)) {
                tableModel.setValueAt(group.getUsers().size(),i, 2);
                isFound = true;
                break;
            }
        }
        if (!isFound){
            tableModel.addRow(new Object[]{group.isToDelete(), group.getName(), group.getUsers().size()});
        }
    }

    /**
     * to delete client from Clients-Table
     *
     * @param username a Name of client
     * @author kalnaasan
     */
    private void deleteUserFromTable(String username) {
        DefaultTableModel tblModel = (DefaultTableModel) this.tableClients.getModel();
        for (int i = 0; i < this.tableClients.getRowCount(); i++) {
            String clientUsername = (String) this.tableClients.getValueAt(i, 1);
            if (username.equals(clientUsername)) {
                tblModel.removeRow(i);
            }
        }
    }

    /**
     * to delete client from Clients-Table
     *
     * @param group a Name of client
     * @author kalnaasan
     */
    private void deleteGroupFromTable(String group) {
        DefaultTableModel tblModel = (DefaultTableModel) this.tableGroups.getModel();
        for (int i = 0; i < this.tableGroups.getRowCount(); i++) {
            String groupName = (String) this.tableClients.getValueAt(i, 1);
            if (group.equals(groupName)) {
                tblModel.removeRow(i);
            }
        }
    }

    /**
     * to add Observer to Observable
     */
    private void attachObserver() {
        Server.chatServer.attach(
                new Observer() {
                    @Override
                    public void addClient(User user) {
                        addClientToTable(user);
                    }

                    @Override
                    public void deleteClient(String username) {
                        deleteUserFromTable(username);
                    }

                    @Override
                    public void addGroup(Group group) {
                        addGroupToTable(group);
                    }

                    @Override
                    public void deleteGroup(String username) {
                        deleteGroupFromTable(username);
                    }
                }
        );
    }
}
