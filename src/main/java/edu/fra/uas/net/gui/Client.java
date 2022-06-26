package edu.fra.uas.net.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JTextField;

import edu.fra.uas.net.model.User;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;

/**
 * 
 * to start client-GUI
 * 
 * @author harsh
 *
 */
public class Client {

	private static List<User> users = new ArrayList<>();

	private JFrame frame;
	private final Panel panelUserList = new Panel();
	private final Panel panelMessage = new Panel();
	private final Panel panelUserSearch = new Panel();
	private final JTextField textFieldUserSearch = new JTextField();
	private final Panel panelClientsName = new Panel();
	private final JLabel lblClientsName = new JLabel("  Client's Name");
	private final Panel panelClients = new Panel();
	private final Panel panelUser = new Panel();
	private final JLabel lbClientName = new JLabel("  Client's Name 1");
	private final JButton btnUserSearch = new JButton("Search");
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panelConfig = new JPanel();
	private final JPanel panelChat = new JPanel();
	private final JButton btnSelectContacts = new JButton(" Select Contacts");
	private final JButton btnCreateGroup = new JButton("Create Group");
	private final JLabel lbClientLastMessage = new JLabel("Typping...");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JPanel panelConfigServerUser = new JPanel();
	private final JButton btnSave = new JButton("Save");
	private final JLabel lblServerIp = new JLabel("IP Address :");
	private final JLabel lblServerPort = new JLabel("Port           :");
	private final JTextField textFieldServerIp = new JTextField("127.0.0.1");
	private final JTextField textFieldServerPort = new JTextField("8080");
	private final Panel panelConfigServer = new Panel();
	private final JLabel lblServer = new JLabel("Server        :");
	private final Panel panelConfigUser = new Panel();
	private final JLabel lblUserIp = new JLabel("IP Address  :");
	private final JTextField textFieldUserIp = new JTextField("192.168.1.20");
	private final JLabel lblUserPort = new JLabel("Port            :");
	private final JTextField textFieldUserPort = new JTextField("5000");
	private final JLabel lblUser = new JLabel("Client         :");
	private final JTextField textFieldCurrentMessage = new JTextField();
	private final JButton btnMessageSend = new JButton("Send");
	private final JButton btnFileAdd = new JButton("Add File");
	private final Panel panelMessageAddFileAndSend = new Panel();
	private final JTextField textFieldPreviousMessage = new JTextField();
	private final Panel panelUser_1 = new Panel();
	private final JLabel lbClientName_1 = new JLabel((String) null);
	private final JLabel lbClientLastMessage_1 = new JLabel("Typping...");
	private final Panel panelUser_2 = new Panel();
	private final JLabel lbClientName_2 = new JLabel((String) null);
	private final JLabel lbClientLastMessage_2 = new JLabel("Typping...");
	private final Panel panelUser_3 = new Panel();
	private final JLabel lbClientName_3 = new JLabel((String) null);
	private final JLabel lbClientLastMessage_3 = new JLabel("Typping...");
	private final JLabel lblUsersName = new JLabel("User's Name:");
	private final JTextField textFieldUserName = new JTextField("Client");
	private final JComboBox comboBox = new JComboBox();
	private final JMenuBar menuBar = new JMenuBar();

	public Client() {
		initialize();
	}

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		users.add(new User("client1", "localhost", 1010));
		users.add(new User("client2", "localhost", 1011));
		users.add(new User("client3", "localhost", 1012));
		users.add(new User("client4", "localhost", 1013));

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textFieldPreviousMessage.setBounds(10, 79, 441, 334);
		textFieldPreviousMessage.setColumns(10);
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 872, 631);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		tabbedPane.setBounds(6, 6, 860, 569);

		frame.getContentPane().add(tabbedPane);

		tabbedPane.addTab("Chat", null, panelChat, null);
		panelChat.setLayout(null);
		panelMessage.setBounds(369, 2, 470, 511);
		panelChat.add(panelMessage);
		panelMessage.setLayout(null);
		panelClientsName.setForeground(Color.LIGHT_GRAY);
		panelClientsName.setBounds(0, 0, 470, 73);

		panelMessage.add(panelClientsName);
		panelClientsName.setLayout(null);
		lblClientsName.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblClientsName.setBounds(6, 6, 442, 67);
		panelClientsName.add(lblClientsName);
		panelMessageAddFileAndSend.setBounds(0, 416, 470, 95);

		panelMessage.add(panelMessageAddFileAndSend);
		panelMessageAddFileAndSend.setLayout(null);
		textFieldCurrentMessage.setBounds(12, 6, 302, 83);
		panelMessageAddFileAndSend.add(textFieldCurrentMessage);
		textFieldCurrentMessage.setColumns(10);
		btnFileAdd.setBounds(326, 6, 125, 40);
		panelMessageAddFileAndSend.add(btnFileAdd);
		btnMessageSend.setBounds(326, 49, 125, 40);
		panelMessageAddFileAndSend.add(btnMessageSend);

		panelMessage.add(textFieldPreviousMessage);
		btnMessageSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelClients.setBounds(0, 44, 365, 479);
		panelChat.add(panelClients);
		panelClients.setLayout(null);
		scrollPane.setBounds(6, 6, 353, 454);
		panelClients.add(scrollPane);
		textFieldUserSearch.setBounds(6, 11, 199, 41);
		textFieldUserSearch.setColumns(10);
		scrollPane.setViewportView(panelUserList);
		panelUserList.setBackground(Color.WHITE);
		panelUserList.setLayout(null);
		panelUserSearch.setBackground(Color.WHITE);
		panelUserSearch.setBounds(0, 0, 349, 68);

		panelUserList.add(panelUserSearch);
		panelUserSearch.setLayout(null);

		panelUserSearch.add(textFieldUserSearch);
		btnUserSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnUserSearch.setBounds(217, 13, 100, 39);

		panelUserSearch.add(btnUserSearch);
		comboBox.setBounds(77, 25, 52, 27);

		panelUserSearch.add(comboBox);
		panelUser.setBounds(6, 70, 343, 66);
		panelUserList.add(panelUser);
		panelUser.setBackground(Color.LIGHT_GRAY);
		panelUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Test");
				;
			}
		});
		panelUser.setLayout(null);
		lbClientName.setBounds(6, 6, 287, 21);

		panelUser.add(lbClientName);
		lbClientLastMessage.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lbClientLastMessage.setBounds(16, 29, 61, 16);

		panelUser.add(lbClientLastMessage);

		lbClientName.setText(users.get(0).getUsername());
		panelUser_1.setLayout(null);
		panelUser_1.setBackground(Color.LIGHT_GRAY);
		panelUser_1.setBounds(6, 142, 343, 66);

		panelUserList.add(panelUser_1);
		lbClientName_1.setBounds(6, 6, 287, 21);

		panelUser_1.add(lbClientName_1);
		lbClientLastMessage_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lbClientLastMessage_1.setBounds(16, 29, 61, 16);

		panelUser_1.add(lbClientLastMessage_1);
		panelUser_2.setLayout(null);
		panelUser_2.setBackground(Color.LIGHT_GRAY);
		panelUser_2.setBounds(6, 214, 343, 66);

		panelUserList.add(panelUser_2);
		lbClientName_2.setBounds(6, 6, 287, 21);

		panelUser_2.add(lbClientName_2);
		lbClientLastMessage_2.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lbClientLastMessage_2.setBounds(16, 29, 61, 16);

		panelUser_2.add(lbClientLastMessage_2);
		panelUser_3.setLayout(null);
		panelUser_3.setBackground(Color.LIGHT_GRAY);
		panelUser_3.setBounds(6, 286, 343, 66);

		panelUserList.add(panelUser_3);
		lbClientName_3.setBounds(6, 6, 287, 21);

		panelUser_3.add(lbClientName_3);
		lbClientLastMessage_3.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lbClientLastMessage_3.setBounds(16, 29, 61, 16);

		panelUser_3.add(lbClientLastMessage_3);
		btnSelectContacts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DynamicMessagePane();
			}

		});
		btnSelectContacts.setBounds(25, 2, 145, 29);
		panelChat.add(btnSelectContacts);
		btnCreateGroup.setBounds(182, 2, 135, 29);
		btnCreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelChat.add(btnCreateGroup);

		tabbedPane.addTab("Config", null, panelConfig, null);
		panelConfig.setLayout(null);
		panelConfigServerUser.setBackground(UIManager.getColor("Button.background"));
		panelConfigServerUser.setBorder(null);
		panelConfigServerUser.setBounds(200, 128, 448, 270);

		panelConfig.add(panelConfigServerUser);
		panelConfigServerUser.setLayout(null);
		btnSave.setBounds(175, 235, 117, 29);

		panelConfigServerUser.add(btnSave);
		panelConfigServer.setBackground(Color.LIGHT_GRAY);
		panelConfigServer.setBounds(61, 21, 316, 83);

		panelConfigServerUser.add(panelConfigServer);
		panelConfigServer.setLayout(null);
		lblServerIp.setBounds(6, 27, 90, 16);
		panelConfigServer.add(lblServerIp);
		textFieldServerIp.setBounds(108, 22, 139, 26);
		panelConfigServer.add(textFieldServerIp);
		textFieldServerIp.setColumns(10);
		lblServerPort.setBounds(6, 52, 78, 16);
		panelConfigServer.add(lblServerPort);
		textFieldServerPort.setBounds(108, 47, 61, 26);
		panelConfigServer.add(textFieldServerPort);
		textFieldServerPort.setColumns(10);
		lblServer.setBounds(6, -1, 90, 26);

		panelConfigServer.add(lblServer);
		panelConfigUser.setLayout(null);
		panelConfigUser.setBackground(Color.LIGHT_GRAY);
		panelConfigUser.setBounds(61, 124, 316, 105);

		panelConfigServerUser.add(panelConfigUser);
		lblUserIp.setBackground(Color.LIGHT_GRAY);
		lblUserIp.setBounds(6, 27, 90, 16);

		panelConfigUser.add(lblUserIp);
		textFieldUserIp.setColumns(10);
		textFieldUserIp.setBounds(108, 22, 139, 26);

		panelConfigUser.add(textFieldUserIp);
		lblUserPort.setBounds(6, 52, 78, 16);

		panelConfigUser.add(lblUserPort);
		textFieldUserPort.setColumns(10);
		textFieldUserPort.setBounds(108, 47, 61, 26);

		panelConfigUser.add(textFieldUserPort);
		lblUser.setBounds(6, -1, 91, 26);

		panelConfigUser.add(lblUser);
		lblUsersName.setBounds(6, 80, 90, 16);

		panelConfigUser.add(lblUsersName);
		textFieldUserName.setColumns(10);
		textFieldUserName.setBounds(108, 75, 139, 26);

		panelConfigUser.add(textFieldUserName);

		frame.setJMenuBar(menuBar);
		for (int i = 0; i < users.size(); i++) {
			panelUser.add(new JLabel(users.get(i).getUsername()));
		}

	}
}
