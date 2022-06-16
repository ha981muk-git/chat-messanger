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

import edu.fra.uas.net.model.Group;
import edu.fra.uas.net.model.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
 *
 */
public class Server extends JFrame {

	private static final long serialVersionUID = 1L;
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
	private final JTable tableClients = new JTable();
	private final JButton btnDeleteClient = new JButton("Delete");
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panelClients = new JPanel();
	private final JPanel panelGroups = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable tableGroups = new JTable();
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
		btnStartServer.setBounds(10, 11, 114, 23);

		contentPane.add(btnStartServer);
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

		String colsClient[] = { "check", "username", "host", "port" };
		DefaultTableModel tableModel = new DefaultTableModel(colsClient, 0) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Boolean.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		for (int i = 0; i < users.size(); i++) {
			String username = users.get(i).getUsername();
			String host = users.get(i).getHost();
			int port = users.get(i).getPort();
			tableModel.addRow(new Object[0]);
			tableModel.setValueAt(false, i, 0);
			tableModel.setValueAt(username, i, 1);
			tableModel.setValueAt(host, i, 2);
			tableModel.setValueAt(port, i, 3);

		}
		tabbedPane.setBounds(10, 47, 569, 226);

		contentPane.add(tabbedPane);

		tabbedPane.addTab("Clients", null, panelClients, null);
		panelClients.setLayout(null);
		scrollPane.setBounds(10, 11, 549, 149);

		panelClients.add(scrollPane);
		scrollPane.setViewportView(tableClients);
		tableClients.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
				new String[] { "Checked", "UserName", "Host", "Port" }) {
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { Object.class, String.class, String.class, Integer.class };

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

		tableClients.setModel(tableModel);
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
		tableGroups.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Check", "Name of Grupp", "Number of Client" }));

		String colsGroup[] = { "check", "name of Group", "count of Group" };
		DefaultTableModel tableModelGroup = new DefaultTableModel(colsGroup, 0) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return Boolean.class;
				case 1:
					return String.class;
				case 2:
					return Integer.class;
				default:
					return String.class;
				}
			}
		};
		for (int i = 0; i < groups.size(); i++) {

			String name = groups.get(i).getName();
			int count = groups.get(i).getUsers().size();
			tableModelGroup.addRow(new Object[0]);
			tableModelGroup.setValueAt(false, i, 0);
			tableModelGroup.setValueAt(name, i, 1);
			tableModelGroup.setValueAt(count, i, 2);
		}
		tableGroups.setModel(tableModelGroup);
		btnDeleteGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeleteActionPerformed(tableGroups);
			}
		});
		btnDeleteGroup.setBounds(30, 164, 89, 23);

		panelGroups.add(btnDeleteGroup);

		tfIPAdress.setText("127.0.0.1");
		tfPort.setText("8080");

	}

	/**
	 * Starts the already initialized frame, making it visible and ready to interact
	 * with the user.
	 */

	public void start() {
		setVisible(true);
	}
	
	private void btnDeleteActionPerformed(JTable jTable) {
		DefaultTableModel tblModel =(DefaultTableModel) jTable.getModel();
		int countRow = jTable.getRowCount();
		for(int i = 0; i < countRow; i++) {
			boolean check = (boolean) jTable.getValueAt(i, 0);
			if(check) {
				tblModel.removeRow(i);
				users.remove(i);
				i--;
				countRow--;
			}
		}
	}
}
