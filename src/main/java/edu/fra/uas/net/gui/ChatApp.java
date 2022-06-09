package edu.fra.uas.net.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

public class ChatApp extends JFrame {

	private JPanel contentPane;
	private final JButton btnExit = new JButton("Exit");
	private final JTextField tfInput = new JTextField();
	private final JLabel lblNewLabel = new JLabel("Message to Send:");
	private final JTextArea taMessages = new JTextArea();
	private final JButton btnSend = new JButton("Send");
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JButton btnNewButton = new JButton("New button");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatApp frame = new ChatApp();
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
	public ChatApp() {
		tfInput.setBounds(12, 24, 426, 19);
		tfInput.setColumns(10);
		initGUI();
	}

	private void initGUI() {
		setTitle("Chat App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 656);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(NORMAL);
			}
		});
		btnExit.setBounds(342, 234, 96, 25);

		contentPane.add(btnExit);

		contentPane.add(tfInput);
		lblNewLabel.setBounds(12, 0, 103, 15);

		contentPane.add(lblNewLabel);
		taMessages.setEditable(false);
		taMessages.setBounds(12, 87, 426, 138);

		contentPane.add(taMessages);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = tfInput.getText();
				tfInput.setText("");
				
				taMessages.append(input);
				taMessages.append("\n");
			}
		});
		btnSend.setBounds(342, 50, 96, 25);

		contentPane.add(btnSend);
		tabbedPane.setBounds(38, 287, 374, 250);
		
		contentPane.add(tabbedPane);
		
		tabbedPane.addTab("New tab", null, panel, null);
		
		tabbedPane.addTab("New tab", null, panel_1, null);
		
		panel_1.add(btnNewButton);
	}

	public void start() {
		setVisible(true);
	}
}
