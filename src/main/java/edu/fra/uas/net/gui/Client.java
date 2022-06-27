package edu.fra.uas.net.gui;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.fra.uas.net.chat.ChatClient;
import edu.fra.uas.net.model.User;
import edu.fra.uas.net.utill.Constant;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * to start client-GUI
 *
 * @author harsh
 * @author kalnaasan
 */
public class Client extends JFrame {

    private ChatClient chatClient;
    private static List<User> users = new ArrayList<>();

    private JPanel contentPane;
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu jMenuFile = new JMenu("File");
    private final JMenuItem jMenuItemExit = new JMenuItem("Exit");
    private final JMenu jMenuHelp = new JMenu("Help");
    private final JMenuItem jMenuItemAbout = new JMenuItem("About");
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private final JPanel panelConfig = new JPanel();
    private final JPanel panelConfigServerClient = new JPanel();
    private final Panel panelConfigServer = new Panel();
    private final JLabel lblServerIp = new JLabel("IP-Address:");
    private final JTextField textFieldServerIpAddress = new JTextField();
    private final JLabel lblServerPort = new JLabel("Port:");
    private final JTextField textFieldServerPort = new JTextField();
    private final JLabel lblServer = new JLabel("Server:");
    private final JButton btnSaveConfig = new JButton("Save and Start");
    private final Panel panelConfigClient = new Panel();
    private final JLabel lblUserIp = new JLabel("IP-Address:");
    private final JTextField textFieldClientIpAddress = new JTextField();
    private final JLabel lblUserPort = new JLabel("Port:");
    private final JTextField textFieldClientPort = new JTextField();
    private final JLabel lblUser = new JLabel("Client:");
    private final JLabel lblUsersName = new JLabel("Username:");
    private final JTextField textFieldClientUsername = new JTextField();
    private final JPanel panelChat = new JPanel();
    private final JPanel panelClientsList = new JPanel();
    private final JComboBox comboBoxUsernames = new JComboBox();
    private final JButton btnCreateGroup = new JButton("Create Group");
    private final JButton btnSearch = new JButton("Search");
    private final JPanel panelChatMessages = new JPanel();
    private final JLabel lblUsername = new JLabel("Username");
    private final JButton btnFileAdd = new JButton("Add File");
    private final JButton btnMessageSend = new JButton("Send");
    private final JTextArea textAreaMessages = new JTextArea();
    private final JTextArea textAreaSendMessage = new JTextArea();
    private final JPanel panelClients = new JPanel();
    private final JMenuItem mntmStopClient = new JMenuItem("Stop Client");

    /**
     * Create the frame.
     */
    public Client() {
        this.initialize();
        this.initMenuBar();
        this.initAddActionListener();
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

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Client frame = new Client();
                    frame.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * init. GUI
     */
    private void initialize() {
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(Constant.FRAME_X, Constant.FRAME_Y, 862, 624);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(Constant.BORDER, Constant.BORDER, Constant.BORDER, Constant.BORDER));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        tabbedPane.setBounds(0, 0, 860, 569);
        contentPane.add(tabbedPane);

        panelConfig.setLayout(null);
        tabbedPane.addTab("Config", null, panelConfig, null);

        panelConfigServerClient.setLayout(null);
        panelConfigServerClient.setBorder(null);
        panelConfigServerClient.setBackground(UIManager.getColor("Button.background"));
        panelConfigServerClient.setBounds(200, 128, 448, 270);
        panelConfig.add(panelConfigServerClient)
        ;
        panelConfigServer.setLayout(null);
        panelConfigServer.setBackground(Color.LIGHT_GRAY);
        panelConfigServer.setBounds(61, 21, 316, 83);
        panelConfigServerClient.add(panelConfigServer);

        lblServerIp.setBounds(6, 27, 90, 16);
        panelConfigServer.add(lblServerIp);

        textFieldServerIpAddress.setColumns(10);
        textFieldServerIpAddress.setText(Constant.SERVE_HOSTNAME);
        textFieldServerIpAddress.setBounds(108, 22, 139, 26);
        panelConfigServer.add(textFieldServerIpAddress);

        lblServerPort.setBounds(6, 52, 78, 16);
        panelConfigServer.add(lblServerPort);

        textFieldServerPort.setColumns(10);
        textFieldServerPort.setText(String.valueOf(Constant.SERVER_PORT));
        textFieldServerPort.setBounds(108, 47, 61, 26);
        panelConfigServer.add(textFieldServerPort);

        lblServer.setBounds(6, -1, 90, 26);
        panelConfigServer.add(lblServer);

        btnSaveConfig.setBounds(175, 235, 117, Constant.BUTTON_HEIGHT);
        panelConfigServerClient.add(btnSaveConfig);

        panelConfigClient.setLayout(null);
        panelConfigClient.setBackground(Color.LIGHT_GRAY);
        panelConfigClient.setBounds(61, 124, 316, 105);
        panelConfigServerClient.add(panelConfigClient);

        lblUserIp.setBackground(Color.LIGHT_GRAY);
        lblUserIp.setBounds(6, 27, 90, 16);
        panelConfigClient.add(lblUserIp);

        textFieldClientIpAddress.setColumns(10);
        textFieldClientIpAddress.setText(Constant.CLIENT_HOSTNAME);
        textFieldClientIpAddress.setBounds(108, 22, 139, 26);
        panelConfigClient.add(textFieldClientIpAddress);

        lblUserPort.setBounds(6, 52, 78, 16);
        panelConfigClient.add(lblUserPort);

        textFieldClientPort.setColumns(10);
        textFieldClientPort.setText(String.valueOf(Constant.CLIENT_PORT));
        textFieldClientPort.setBounds(108, 47, 61, 26);
        panelConfigClient.add(textFieldClientPort);

        lblUser.setBounds(6, -1, 91, 26);
        panelConfigClient.add(lblUser);

        lblUsersName.setBounds(6, 80, 90, 16);
        panelConfigClient.add(lblUsersName);

        textFieldClientUsername.setColumns(10);
        textFieldClientUsername.setText("Client" + Constant.CLIENT_PORT);
        textFieldClientUsername.setBounds(108, 75, 139, 26);
        panelConfigClient.add(textFieldClientUsername);

        panelChat.setLayout(null);
        tabbedPane.addTab("Chat", null, panelChat, null);
        tabbedPane.setEnabledAt(1, false);

        panelClientsList.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelClientsList.setBounds(5, 5, 200, 530);
        panelClientsList.setLayout(null);
        panelChat.add(panelClientsList);

        comboBoxUsernames.setBounds(5, 85, 190, Constant.BUTTON_HEIGHT);
        panelClientsList.add(comboBoxUsernames);

        btnCreateGroup.setBounds(5, 5, 190, Constant.BUTTON_HEIGHT);
        panelClientsList.add(btnCreateGroup);

        btnSearch.setBounds(5, 45, 190, Constant.BUTTON_HEIGHT);
        panelClientsList.add(btnSearch);

        panelClients.setBounds(5, 125, 190, 391);
        panelClients.setLayout(null);
        panelClientsList.add(panelClients);

        panelChatMessages.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelChatMessages.setBounds(215, 5, 640, 530);
        panelChatMessages.setLayout(null);
        panelChat.add(panelChatMessages);

        lblUsername.setFont(new Font("Dialog", Font.BOLD, 20));
        lblUsername.setBounds(10, 10, 200, 15);
        panelChatMessages.add(lblUsername);

        btnFileAdd.setBounds(508, 445, 120, Constant.BUTTON_HEIGHT);
        panelChatMessages.add(btnFileAdd);

        btnMessageSend.setBounds(508, 485, 120, Constant.BUTTON_HEIGHT);
        panelChatMessages.add(btnMessageSend);

        textAreaMessages.setEditable(false);
        textAreaMessages.setBounds(10, 39, 616, 397);
        panelChatMessages.add(textAreaMessages);

        textAreaSendMessage.setBounds(10, 445, 485, 70);
        panelChatMessages.add(textAreaSendMessage);

        for (User user : users) {
            Component[] components = panelClients.getComponents();
            int length = components.length;
            System.out.println(length);
            JPanel jPanel = this.generateClientPanel(user);
            if (length != 0) {
                jPanel.setBounds(
                        (int) components[length - 1].getBounds().getX(),
                        (int) components[length - 1].getBounds().getY() + 80,
                        (int) components[length - 1].getBounds().getWidth(),
                        (int) components[length - 1].getBounds().getHeight()
                );
            }
            panelClients.add(jPanel);
        }
    }

    private void initMenuBar() {
        jMenuFile.add(mntmStopClient);
        jMenuFile.add(jMenuItemExit);
        menuBar.add(jMenuFile);
        jMenuHelp.add(jMenuItemAbout);
        menuBar.add(jMenuHelp);
        setJMenuBar(menuBar);
    }

    private void initAddActionListener() {
        mntmStopClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to stop the client ?",
                        "Stop Client", dialogButton);
                if (dialogResult == 0) {
                    tabbedPane.setEnabledAt(0, true);
                    tabbedPane.setEnabledAt(1, false);
                    tabbedPane.setSelectedIndex(0);
                    try {
                        stopClient();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        jMenuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
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
                String infoMessage = "This Chat Client is developed by ";
                infoMessage += "Mohammed Dawoud, Kaddour Alnaasan and Harsh Mukhiya";
                JOptionPane.showMessageDialog(null, infoMessage, "About Chat_Server ", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnSaveConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setEnabledAt(0, false);
                tabbedPane.setEnabledAt(1, true);
                tabbedPane.setSelectedIndex(1);
                try {
                    startClient();
                } catch (SocketException | UnknownHostException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnCreateGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnFileAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnMessageSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    private void startClient() throws SocketException, UnknownHostException {
        this.chatClient = new ChatClient(
                textFieldServerIpAddress.getText(),
                Integer.parseInt(textFieldServerPort.getText()),
                textFieldClientIpAddress.getText(),
                Integer.parseInt(textFieldClientPort.getText()),
                textFieldClientUsername.getText()
        );
    }
    private void stopClient() throws IOException {
        this.chatClient.deregister();
    }

    /**
     * Starts the already initialized frame, making it visible and ready to interact
     * with the user.
     */
    public void start() {
        setVisible(true);
    }

    private JPanel generateClientPanel(User user) {
        JPanel panelClient = new JPanel();
        panelClient.setBackground(Color.LIGHT_GRAY);
        panelClient.setBounds(5, 5, 180, 70);
        panelClient.setLayout(null);

        JLabel lblClient = new JLabel(user.getUsername());
        lblClient.setFont(new Font("Dialog", Font.BOLD, 16));
        lblClient.setBounds(12, 12, 70, 15);
        panelClient.add(lblClient);

        JLabel lblLastMessage = new JLabel("Typing...");
        lblLastMessage.setFont(new Font("Dialog", Font.BOLD, 10));
        lblLastMessage.setBounds(12, 39, 70, 15);
        panelClient.add(lblLastMessage);

        panelClient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, user.getUsername());
            }
        });

        return panelClient;
    }
}
