package edu.fra.uas.net.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import edu.fra.uas.net.model.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Server extends JFrame {

	private static List<User> users = new ArrayList<User>();

	private JPanel contentPane;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnNewMenu = new JMenu("File");
	private final JMenu mnNewMenu_1 = new JMenu("Help");
	private final JMenuItem mntmNewMenuItem = new JMenuItem("About");
	private final JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
	private final JButton btnNewButton = new JButton("start Server");
	private final JButton btnNewButton_1 = new JButton("stop");
	private final JLabel lblNewLabel = new JLabel("IP Adresse ");
	private final JTextField textField = new JTextField();
	private final JLabel lblNewLabel_1 = new JLabel("Port");
	private final JTextField textField_1 = new JTextField();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();

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
					Server frame = new Server();
					frame.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Server() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 682, 360);
		
		setJMenuBar(menuBar);
		
		menuBar.add(mnNewMenu);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		
		
		mnNewMenu.add(mntmNewMenuItem_1);
		
		menuBar.add(mnNewMenu_1);
		
		mnNewMenu_1.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		btnNewButton.setBounds(10, 11, 114, 23);
		
		contentPane.add(btnNewButton);
		btnNewButton_1.setBounds(131, 11, 89, 23);
		
		contentPane.add(btnNewButton_1);
		lblNewLabel.setBounds(230, 15, 114, 19);
		
		contentPane.add(lblNewLabel);
		textField.setColumns(10);
		textField.setBounds(293, 12, 96, 20);
		
		contentPane.add(textField);
		lblNewLabel_1.setBounds(414, 15, 49, 14);
		
		contentPane.add(lblNewLabel_1);
		textField_1.setText("     ");
		textField_1.setColumns(10);
		textField_1.setBounds(444, 12, 67, 20);
		
		contentPane.add(textField_1);
		scrollPane.setBounds(36, 79, 508, 133);
		
		contentPane.add(scrollPane);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"Checked", "UserName", "Host", "Port"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, String.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setToolTipText("");
		table.setSurrendersFocusOnKeystroke(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setBorder(UIManager.getBorder("CheckBox.border"));
		table.setBackground(Color.WHITE);
		
		String col[] = { "check", "username", "host", "port" };
		DefaultTableModel tableModel = new DefaultTableModel(col, 0){
			public Class<?> getColumnClass(int column){
				switch (column) {
				case 0: return Boolean.class;
				case 1: return String.class;
				case 2: return String.class;
				case 3: return String.class;
				
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

		table.setModel(tableModel);
		scrollPane.setViewportView(table);
	}
	/**
	 * Starts the already initialized frame, making it
	 * visible and ready to interact with the user.
	 */
	
	public void start() {
		setVisible(true);
	}
	
	private void close() {
		WindowEvent closeWindo = new WindowEvent(this , WindowEvent.WINDOW_CLOSING );
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindo);
		
	}
}
