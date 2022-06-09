package edu.fra.uas.net.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import edu.fra.uas.net.model.User;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class Server extends JFrame {

	private static List<User> users = new ArrayList<User>();

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;

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
					frame.setVisible(true);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 399);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			
			
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_1 = new JMenu("Help");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("About");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar.add(menuBar_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("start Server");
		btnNewButton.setBounds(10, 11, 114, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("stop");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(134, 11, 89, 23);
		contentPane.add(btnNewButton_1);

		JLabel lblNewLabel = new JLabel("IP Adresse ");
		lblNewLabel.setBounds(233, 13, 114, 19);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(306, 12, 96, 20);
		contentPane.add(textField);

		JLabel lblNewLabel_1 = new JLabel("Port");
		lblNewLabel_1.setBounds(411, 15, 49, 14);
		contentPane.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setText("     ");
		textField_1.setColumns(10);
		textField_1.setBounds(471, 12, 67, 20);
		contentPane.add(textField_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 68, 510, 160);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setSurrendersFocusOnKeystroke(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Check", "username", "host", "port"
			}
		) {
			Class[] columnTypes = new Class[] {
				Boolean.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setToolTipText("");
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Sitka Text", Font.PLAIN, 11));
		table.setFillsViewportHeight(true);
		table.setColumnSelectionAllowed(true);
		table.setBorder(UIManager.getBorder("CheckBox.border"));
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);

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

//			Object[] data = { false, username, host, port };
//			tableModel.addRow(data);
			
			tableModel.addRow(new Object[0]);
			tableModel.setValueAt(false, i, 0);
			tableModel.setValueAt(username, i, 1);
			tableModel.setValueAt(host, i, 2);
			tableModel.setValueAt(port, i, 3);
			
		}

		table.setModel(tableModel);

	}
	
	public void close() {
		WindowEvent closeWindo = new WindowEvent(this , WindowEvent.WINDOW_CLOSING );
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindo);
		
	}
}
