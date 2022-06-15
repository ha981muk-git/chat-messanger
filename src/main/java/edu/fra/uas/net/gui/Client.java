package edu.fra.uas.net.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Client {

	private JFrame frame;
	private final Panel panelUser = new Panel();
	private final Panel panelMessage = new Panel();
	private final Panel panelTextSendAndAdd = new Panel();
	private final Panel panelSearch = new Panel();
	private final JTextField textFieldSearch = new JTextField();
	private final JButton btnNewButton = new JButton("Add File");
	private final JButton btnNewButton_1 = new JButton("Send");
	private final JButton btnNewButton_2 = new JButton("Create Group");
	private final JButton btnNewButton_3 = new JButton(" Select Contacts");
	private final JTextField textField = new JTextField();
	private final Panel panel = new Panel();
	private final JLabel lblNewLabel = new JLabel("Client's Name");
	private final Panel panel_1 = new Panel();
	private final JTextField textField_1 = new JTextField();
	private final Panel panelClients = new Panel();
	private final Panel panel_2 = new Panel();
	private final JLabel lblNewLabel_1 = new JLabel("  Client's Name 1");
	private final JTextField textField_2 = new JTextField();
	private final Panel panel_2_1 = new Panel();
	private final JLabel lblNewLabel_1_1 = new JLabel("  Client's Name 2");
	private final JTextField textField_3 = new JTextField();
	private final Panel panel_2_2 = new Panel();
	private final JLabel lblNewLabel_1_2 = new JLabel("  Client's Name 3");
	private final JTextField textField_4 = new JTextField();
	private final Panel panel_2_3 = new Panel();
	private final JLabel lblNewLabel_1_3 = new JLabel("  Client's Name 4");
	private final JTextField textField_5 = new JTextField();
	private final Panel panel_2_4 = new Panel();
	private final JLabel lblNewLabel_1_4 = new JLabel("  Client's Name 5");
	private final JTextField textField_6 = new JTextField();
	private final Panel panel_2_5 = new Panel();
	private final JLabel lblNewLabel_1_5 = new JLabel("  Client's Name 6");
	private final JTextField textField_7 = new JTextField();
	private final Panel panel_2_6 = new Panel();
	private final JLabel lblNewLabel_1_6 = new JLabel("  Client's Name 7");
	private final JTextField textField_8 = new JTextField();
	private final JButton btnNewButton_4 = new JButton("Search");
	private final JMenuBar menuBar = new JMenuBar();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		textField_2.setBounds(6, 28, 287, 32);
		textField_2.setColumns(10);
		textField_1.setBounds(6, 6, 592, 462);
		textField_1.setColumns(10);
		textField.setBounds(6, 6, 451, 51);
		textField.setColumns(10);
		textFieldSearch.setBounds(6, 11, 236, 41);
		textFieldSearch.setColumns(10);
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBounds(100, 100, 1004, 733);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panelUser.setBackground(Color.WHITE);
		panelUser.setBounds(0, 69, 352, 627);
		
		frame.getContentPane().add(panelUser);
		panelUser.setLayout(null);
		panelSearch.setBounds(0, 0, 352, 68);
		
		panelUser.add(panelSearch);
		panelSearch.setLayout(null);
		
		panelSearch.add(textFieldSearch);
		btnNewButton_4.setBounds(246, 11, 100, 39);
		
		panelSearch.add(btnNewButton_4);
		panelClients.setBounds(10, 74, 332, 543);
		
		panelUser.add(panelClients);
		panelClients.setLayout(null);
		panel_2.setBounds(10, 10, 312, 66);
		
		panelClients.add(panel_2);
		panel_2.setLayout(null);
		lblNewLabel_1.setBounds(6, 6, 287, 21);
		
		panel_2.add(lblNewLabel_1);
		
		panel_2.add(textField_2);
		panel_2_1.setLayout(null);
		panel_2_1.setBounds(10, 92, 312, 66);
		
		panelClients.add(panel_2_1);
		lblNewLabel_1_1.setBounds(6, 6, 287, 21);
		
		panel_2_1.add(lblNewLabel_1_1);
		textField_3.setColumns(10);
		textField_3.setBounds(6, 28, 287, 32);
		
		panel_2_1.add(textField_3);
		panel_2_2.setLayout(null);
		panel_2_2.setBounds(10, 164, 312, 66);
		
		panelClients.add(panel_2_2);
		lblNewLabel_1_2.setBounds(6, 6, 287, 21);
		
		panel_2_2.add(lblNewLabel_1_2);
		textField_4.setColumns(10);
		textField_4.setBounds(6, 28, 287, 32);
		
		panel_2_2.add(textField_4);
		panel_2_3.setLayout(null);
		panel_2_3.setBounds(10, 236, 312, 66);
		
		panelClients.add(panel_2_3);
		lblNewLabel_1_3.setBounds(6, 6, 287, 21);
		
		panel_2_3.add(lblNewLabel_1_3);
		textField_5.setColumns(10);
		textField_5.setBounds(6, 28, 287, 32);
		
		panel_2_3.add(textField_5);
		panel_2_4.setLayout(null);
		panel_2_4.setBounds(10, 310, 312, 66);
		
		panelClients.add(panel_2_4);
		lblNewLabel_1_4.setBounds(6, 6, 287, 21);
		
		panel_2_4.add(lblNewLabel_1_4);
		textField_6.setColumns(10);
		textField_6.setBounds(6, 28, 287, 32);
		
		panel_2_4.add(textField_6);
		panel_2_5.setLayout(null);
		panel_2_5.setBounds(10, 382, 312, 66);
		
		panelClients.add(panel_2_5);
		lblNewLabel_1_5.setBounds(6, 6, 287, 21);
		
		panel_2_5.add(lblNewLabel_1_5);
		textField_7.setColumns(10);
		textField_7.setBounds(6, 28, 287, 32);
		
		panel_2_5.add(textField_7);
		panel_2_6.setLayout(null);
		panel_2_6.setBounds(10, 454, 312, 66);
		
		panelClients.add(panel_2_6);
		lblNewLabel_1_6.setBounds(6, 6, 287, 21);
		
		panel_2_6.add(lblNewLabel_1_6);
		textField_8.setColumns(10);
		textField_8.setBounds(6, 28, 287, 32);
		
		panel_2_6.add(textField_8);
		panelMessage.setBounds(353, 69, 656, 627);
		
		frame.getContentPane().add(panelMessage);
		panelMessage.setLayout(null);
		panelTextSendAndAdd.setBounds(28, 554, 604, 63);
		panelMessage.add(panelTextSendAndAdd);
		panelTextSendAndAdd.setLayout(null);
		btnNewButton.setBounds(458, 1, 117, 29);
		panelTextSendAndAdd.add(btnNewButton);
		btnNewButton_1.setBounds(458, 29, 117, 29);
		panelTextSendAndAdd.add(btnNewButton_1);
		
		panelTextSendAndAdd.add(textField);
		panel.setBounds(0, 0, 656, 68);
		
		panelMessage.add(panel);
		panel.setLayout(null);
		lblNewLabel.setBounds(21, 6, 247, 56);
		
		panel.add(lblNewLabel);
		panel_1.setBounds(28, 74, 604, 474);
		
		panelMessage.add(panel_1);
		panel_1.setLayout(null);
		
		panel_1.add(textField_1);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(353, 10, 172, 37);
		
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(166, 10, 183, 37);
		
		frame.getContentPane().add(btnNewButton_3);
		
		frame.setJMenuBar(menuBar);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
