package edz.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	public static JTextField loginTextField;
	private JButton btnSignin;
	private JButton btnRegistration;
	public static JPasswordField passwordTextField;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setUI();
		addEventHandlers();
	}
	
	private void setUI(){
		setTitle("Prisoners Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnInfo = new JMenu("Info");
		menuBar.add(mnInfo);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnInfo.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][grow]"));
		
		JLabel lblLogin = new JLabel("Login:");
		contentPane.add(lblLogin, "cell 1 1");
		
		loginTextField = new JTextField();
		contentPane.add(loginTextField, "cell 1 2,growx");
		loginTextField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		contentPane.add(lblPassword, "cell 1 3");
		
		passwordTextField = new JPasswordField();
		contentPane.add(passwordTextField, "cell 1 4,growx");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 7 2 2,grow");
		
		btnSignin = new JButton("SignIn");
		btnSignin.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnSignin);
		
		btnRegistration = new JButton("Registration");
		btnRegistration.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(btnRegistration);
	}

	private void addEventHandlers(){
		btnSignin.addActionListener(new UIController.SignIn());
		btnRegistration.addActionListener(new UIController.Registration());
	}
	
}
