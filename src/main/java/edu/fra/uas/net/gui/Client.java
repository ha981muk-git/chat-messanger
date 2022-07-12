package edu.fra.uas.net.gui;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.fra.uas.net.chat.ChatClient;
import edu.fra.uas.net.model.Message;
import edu.fra.uas.net.utill.Constant;
import edu.fra.uas.net.utill.Observer;
import edu.fra.uas.net.utill.Parser;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import java.awt.Panel;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

/**
 * to start client-GUI
 *
 * @author harsh
 * @author kalnaasan
 */
public class Client extends JFrame {

    private ChatClient chatClient;
    private String currentUsername;
    private HashMap<String, List<Message>> messages = new HashMap<>();

    private final JPanel contentPane = new JPanel();
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu jMenuFile = new JMenu("File");
    private final JMenuItem jMenuItemExit = new JMenuItem("Exit");
    private final JMenu jMenuHelp = new JMenu("Help");
    private final JMenuItem jMenuItemAbout = new JMenuItem("About");
    private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
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
    private final JComboBox<String> comboBoxUsernames = new JComboBox<>();
    private final JButton btnCreateGroup = new JButton("Create Group");
    private final JButton btnSearch = new JButton("Search");
    private final JPanel panelChatMessages = new JPanel();
    private final JLabel lblUsername = new JLabel("Username");
    private final JButton btnFileAdd = new JButton("Add File");
    private final JButton btnMessageSend = new JButton("Send");
    private final JTextArea textAreaMessages = new JTextArea();
    private final JTextArea textAreaSendMessage = new JTextArea();
    private final JMenuItem mntmStopClient = new JMenuItem("Stop Client");
    private final JList<String> listClients = new JList<>();
    private final DefaultListModel<String> defaultListModel = new DefaultListModel<>();
    private final JScrollPane scrollPaneSendMessage = new JScrollPane();
    private final JScrollPane scrollPaneMessages = new JScrollPane();
    private final JTextField textFieldCreateGroup = new JTextField();

    /**
     * Create the frame.
     */
    public Client() {
        textFieldCreateGroup.setBounds(5, 45, 190, 30);
        textFieldCreateGroup.setColumns(10);
        setTitle("Client-Chat");
        this.setResizable(false);
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
     * Starts the already initialized frame, making it visible and ready to interact
     * with the user.
     */
    public void start() {
        setVisible(true);
    }

    /**
     * init. GUI
     */
    private void initialize() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(Constant.FRAME_X, Constant.FRAME_Y, Constant.CLIENT_GUI_WIDTH, Constant.CLIENT_GUI_HEIGHT);

        contentPane.setBorder(new EmptyBorder(Constant.BORDER, Constant.BORDER, Constant.BORDER, Constant.BORDER));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        tabbedPane.setBounds(0, 0, Constant.CLIENT_GUI_WIDTH, 569);
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

        btnSaveConfig.setBounds(165, 235, 140, Constant.BUTTON_HEIGHT);
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
        panelClientsList.setBounds(Constant.COMPONENT_X5, Constant.COMPONENT_Y5, 200, 530);
        panelClientsList.setLayout(null);
        panelChat.add(panelClientsList);

        btnCreateGroup.setBounds(
                Constant.COMPONENT_X5, Constant.COMPONENT_Y5,
                Constant.CLIENT_LIST_CLIENTS_WIDTH, Constant.BUTTON_HEIGHT);
        panelClientsList.add(btnCreateGroup);

        btnSearch.setBounds(5, 85, Constant.CLIENT_LIST_CLIENTS_WIDTH, Constant.BUTTON_HEIGHT);
        panelClientsList.add(btnSearch);

        comboBoxUsernames.setBounds(5, 125,
                Constant.CLIENT_LIST_CLIENTS_WIDTH, Constant.BUTTON_HEIGHT);
        comboBoxUsernames.addItem("select client");
        panelClientsList.add(comboBoxUsernames);

        listClients.setModel(defaultListModel);
        listClients.setBounds(5, 167, 190, 351);

        panelClientsList.add(listClients);

        panelClientsList.add(textFieldCreateGroup);

        panelChatMessages.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelChatMessages.setBounds(215, Constant.COMPONENT_Y5, 640, 530);
        panelChatMessages.setLayout(null);
        panelChat.add(panelChatMessages);

        lblUsername.setFont(new Font("Dialog", Font.BOLD, 18));
        lblUsername.setBounds(Constant.COMPONENT_X10, 10, 200, 15);
        panelChatMessages.add(lblUsername);
        btnFileAdd.setEnabled(false);

        btnFileAdd.setBounds(508, 445, 120, Constant.BUTTON_HEIGHT);
        panelChatMessages.add(btnFileAdd);
        btnMessageSend.setEnabled(false);

        btnMessageSend.setBounds(508, 485, 120, Constant.BUTTON_HEIGHT);
        panelChatMessages.add(btnMessageSend);
        scrollPaneMessages.setBounds(Constant.COMPONENT_X10, 39, 616, 397);

        panelChatMessages.add(scrollPaneMessages);
        scrollPaneMessages.setViewportView(textAreaMessages);

        textAreaMessages.setEditable(false);
        scrollPaneSendMessage.setBounds(Constant.COMPONENT_X10, 445, 485, 70);

        panelChatMessages.add(scrollPaneSendMessage);
        textAreaSendMessage.setEnabled(false);
        scrollPaneSendMessage.setViewportView(textAreaSendMessage);
    }

    /**
     * init. MenuBar
     */
    private void initMenuBar() {
        jMenuFile.add(mntmStopClient);
        jMenuFile.add(jMenuItemExit);
        menuBar.add(jMenuFile);
        jMenuHelp.add(jMenuItemAbout);
        menuBar.add(jMenuHelp);
        setJMenuBar(menuBar);
    }

    /**
     * add Action Listener to Components
     */
    private void initAddActionListener() {
        mntmStopClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopClient();
            }
        });
        jMenuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(contentPane, "Do you want to close the Server ?",
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
                JOptionPane.showMessageDialog(contentPane, infoMessage,
                        "About Chat_Server ", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnSaveConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTitle(textFieldClientUsername.getText() + " - Client-Chat");
                startClient();
            }
        });
        btnCreateGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nameGroup = textFieldCreateGroup.getText();
                if ((nameGroup != null) && (nameGroup.length() >= 3) && (nameGroup.length() <= 14)) {
                    createGroup("G:" + nameGroup);
                }
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    chatClient.readClients();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnFileAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnMessageSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        comboBoxUsernames.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String username = (String) comboBoxUsernames.getSelectedItem();
                if (username != null && !username.equalsIgnoreCase("select client")) {
                    currentUsername = username;
                    addClientOrGroupToChatList(username);
                }
            }
        });
        listClients.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentUsername = listClients.getSelectedValue();
                lblUsername.setText(currentUsername);
                loadMessagesForSelectedClient(currentUsername);
                textAreaSendMessage.setEnabled(true);
                btnMessageSend.setEnabled(true);
            }
        });
    }

    private void createGroup(String nameGroup) {
        try {
            byte[] data = Parser.createByteArray(
                    Parser.GROUP_CREATE, this.chatClient.getUser().getUsername(), nameGroup);
            this.chatClient.sendRequest(data);
            this.comboBoxUsernames.addItem(nameGroup);
            this.defaultListModel.add(this.defaultListModel.getSize(), nameGroup);
            this.textFieldCreateGroup.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * to stop connection with server
     */
    private void stopClient() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(contentPane, "Do you want to stop the client ?",
                "Stop Client", dialogButton);
        if (dialogResult == 0) {
            tabbedPane.setEnabledAt(0, true);
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setSelectedIndex(0);
            try {
                this.chatClient.deregister();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * to start connection with server
     */
    private void startClient() {
        tabbedPane.setEnabledAt(0, false);
        tabbedPane.setEnabledAt(1, true);
        tabbedPane.setSelectedIndex(1);
        try {
            this.chatClient = new ChatClient(
                    textFieldServerIpAddress.getText(),
                    Integer.parseInt(textFieldServerPort.getText()),
                    textFieldClientIpAddress.getText(),
                    Integer.parseInt(textFieldClientPort.getText()),
                    textFieldClientUsername.getText()
            );
            this.attachObserver();
        } catch (SocketException | UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * to send message to server
     */
    private void sendMessage() {
        try {
            String sender = this.chatClient.getUser().getUsername();
            String receiver = this.currentUsername;
            String msg = this.textAreaSendMessage.getText();
            Message message = new Message(sender, receiver, Parser.MESSAGE_TYPE_MESSAGE, msg.getBytes());

            if (currentUsername.contains("G:")) {
                message.setReceiver("server");
                message.setNameGroup(receiver);
            }
            // send Message to server
            chatClient.sendMessage(message);
            // save message to stack
            this.addMessageToHasMap(message);
            // clear message area
            this.textAreaSendMessage.setText("");
            this.loadMessagesForSelectedClient(receiver);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * to add name of client to list
     *
     * @param name name of client
     */
    private void addClientOrGroupToChatList(String name) {
        boolean isFound = false;
        String[] chats = new String[this.defaultListModel.getSize()];
        for (int i = 0; i < this.defaultListModel.getSize(); i++) {
            chats[i] = this.defaultListModel.getElementAt(i);
        }

        for (String client : chats) {
            if (client.equalsIgnoreCase(name)) {
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            this.defaultListModel.add(this.defaultListModel.size(), name);
            
            if ((name.charAt(0) == 'G') && (name.charAt(1) == ':')) {
                try {
                    byte[] data = Parser.createByteArray(Parser.GROUP_JOIN, this.textFieldClientUsername.getText(), name);
                    this.chatClient.sendRequest(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * to load chat of selected client in GUI
     *
     * @param username name of client
     */
    private void loadMessagesForSelectedClient(String username) {
        this.textAreaMessages.setText("");
        List<Message> messagesClient = this.messages.get(username);
        if (messagesClient != null) {
            for (Message message : messagesClient) {
                if (message.getType() == Parser.MESSAGE_TYPE_MESSAGE) {
                    this.textAreaMessages.append(
                            message.getSender() + ": " + new String(message.getContent()) + "\n\n");
                }
            }
        }
    }

    /**
     * to start listen to backend
     */
    private void attachObserver() {
        this.chatClient.attach(
                new Observer() {
                    @Override
                    public void updateClientMessage(String msg) {
                        JOptionPane.showMessageDialog(contentPane, msg, "Client-Info", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void updateClientUsernames(String[] usernames) {
                        updateUsernamesList(usernames);
                    }

                    @Override
                    public void updateClientMessage(Message message) {
                        addMessageToHasMap(message);
                        if (message.getNameGroup().equals("null")) {
                            addClientOrGroupToChatList(message.getSender());
                            if (currentUsername != null && currentUsername.equalsIgnoreCase(message.getSender())) {
                                loadMessagesForSelectedClient(message.getSender());
                            }
                        } else {
                            addClientOrGroupToChatList(message.getNameGroup());
                            if (currentUsername != null && currentUsername.equalsIgnoreCase(message.getNameGroup())) {
                                loadMessagesForSelectedClient(message.getNameGroup());
                            }
                        }
                    }
                }
        );
    }

    /**
     * get list of usernames from backend
     *
     * @param usernames names of clients
     */
    private void updateUsernamesList(String[] usernames) {
        comboBoxUsernames.removeAllItems();
        comboBoxUsernames.addItem("Select Client");
        for (String username : usernames) {
            comboBoxUsernames.addItem(username);
        }
    }

    /**
     * add message to stack
     *
     * @param message {@link Message}
     */
    private void addMessageToHasMap(Message message) {
        String client = message.getSender();

        if (!message.getNameGroup().equals("null")) {
            client = message.getNameGroup();
        }

        if (!messages.containsKey(client)) {
            messages.put(client, null);
        }

        List<Message> clientMessages = messages.get(client);
        if (clientMessages == null) {
            clientMessages = new ArrayList<>();
        }

        clientMessages.add(message);
        messages.put(client, clientMessages);
    }
}
