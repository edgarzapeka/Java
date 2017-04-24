package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edz.data.prisoner.Prisoner;
import edz.data.user.User;
import edz.data.util.Validator;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class RegistrationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField IDTextField;
	private JTextField FirstNameTextField;
	private JTextField LastNameTextField;
	private JTextField AgeTextField;
	private JTextField PhoneTextField;
	private JTextField EmailTextField;
	private JButton okButton;
	private JButton cancelButton;
	private User user;
	private JPasswordField passwordTextField;
	private JMenuItem mntmWhyMyData;

	/**
	 * Create the dialog.
	 */
	public RegistrationDialog(User user) {
		
		this.user = user;
		
		createUI();
		setData();
		setActionListeners();
	}
	
private void createUI(){
		
		setTitle("Registration");
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnInfo = new JMenu("Info");
				menuBar.add(mnInfo);
				{
					mntmWhyMyData = new JMenuItem("Why my data is incorrect?");
					mnInfo.add(mntmWhyMyData);
				}
			}
		}
		setBounds(100, 100, 450, 347);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][]"));
		{
			JLabel lblId = new JLabel("ID");
			contentPanel.add(lblId, "cell 0 0,alignx trailing");
		}
		{
			IDTextField = new JTextField();
			IDTextField.setEnabled(false);
			contentPanel.add(IDTextField, "cell 1 0,growx");
			IDTextField.setColumns(10);
		}
		{
			JLabel lblFirstName = new JLabel("First Name");
			contentPanel.add(lblFirstName, "cell 0 1,alignx trailing");
		}
		{
			FirstNameTextField = new JTextField();
			contentPanel.add(FirstNameTextField, "cell 1 1,growx");
			FirstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "cell 0 2,alignx trailing");
		}
		{
			LastNameTextField = new JTextField();
			contentPanel.add(LastNameTextField, "cell 1 2,growx");
			LastNameTextField.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "cell 0 3,alignx trailing");
		}
		{
			passwordTextField = new JPasswordField();
			contentPanel.add(passwordTextField, "cell 1 3,growx");
			passwordTextField.setColumns(10);
		}
		{
			JLabel lblAge = new JLabel("Age");
			contentPanel.add(lblAge, "cell 0 4,alignx trailing");
		}
		{
			AgeTextField = new JTextField();
			contentPanel.add(AgeTextField, "cell 1 4,growx");
			AgeTextField.setColumns(10);
		}
		{
			JLabel lblPhone = new JLabel("Phone");
			contentPanel.add(lblPhone, "cell 0 5,alignx trailing");
		}
		{
			PhoneTextField = new JTextField();
			contentPanel.add(PhoneTextField, "cell 1 5,growx");
			PhoneTextField.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			contentPanel.add(lblEmail, "cell 0 6,alignx trailing");
		}
		{
			EmailTextField = new JTextField();
			contentPanel.add(EmailTextField, "cell 1 6,growx");
			EmailTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private Boolean setUser(){
		if (Validator.name(FirstNameTextField.getText()) && Validator.name(LastNameTextField.getText()) && Validator.password(String.valueOf(passwordTextField.getPassword())) && Validator.age(AgeTextField.getText()) && Validator.phone(PhoneTextField.getText()) && Validator.email(EmailTextField.getText())){
			LocalDate date = LocalDate.now();
			user = new User.Builder(FirstNameTextField.getText(), LastNameTextField.getText())
					.password(String.valueOf(passwordTextField.getPassword()))//
					.id(Integer.valueOf(IDTextField.getText()))
					.age(Integer.valueOf(AgeTextField.getText())) //
					.phone(PhoneTextField.getText()) //
					.email(EmailTextField.getText()) //
					.date(date.toString()) //
					.build(); //
			return true;
		}
		return false;
	}
	
	private void setData(){
		if (user != null){
			IDTextField.setText(String.valueOf(user.getId()));
			FirstNameTextField.setText(user.getFirstName());
			LastNameTextField.setText(user.getLastName());
			passwordTextField.setText(user.getPassword());
			AgeTextField.setText(String.valueOf(user.getAge()));
			PhoneTextField.setText(user.getPhone());
			EmailTextField.setText(user.getEmail());
		}
	}
	
	private void setActionListeners(){
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (setUser()){
					RegistrationDialog.this.dispose();
				} else{
					JOptionPane.showMessageDialog(RegistrationDialog.this, "Incorrect data!");
				}
			}
			
		});
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				RegistrationDialog.this.dispose();
			}
			
		});
		mntmWhyMyData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = "First Name and Last Name can be with one whitespace. Must contain only alphabetical symbols. Examples: Alfred, Al Arabi, Jonny, Robins";
				String age = "Maximum 2 digit numbers. Examples: 18, 35, 64, 5";
				String password = "Minimum 8 characters at least 1 Alphabet and 1 Number. Examples: 1234567a, Simple123password";
				String phone = "10 digit number, only numbers. Examples: 6045523492, 6049282395.";
				String email = "Just regular email with domain name and @. Examples: asd@mail.ru, jonnyboy@gmail.com, campbell@rambler.com";
				String date = "Date must be in format YYYY-MM-DD. MM must be not higher than 12. Examples: 1983-05-23, 1999-01-01, 2000-08-14";
				JOptionPane.showMessageDialog(RegistrationDialog.this, String.format("%s \n %s \n %s \n %s \n %s \n %s \n ", name, age, password, phone, email, date));
			}
			
		});
	}

	public User getUser(){
		return user;
	}

}
