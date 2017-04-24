package edz.ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import edz.data.user.User;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class UserDialog extends JDialog {

	
	private User user;
	private JLabel lblName;
	private JLabel lblAge;
	private JLabel lblEmail;
	private JLabel lblPhone;
	private JButton btnClose;
	private JButton btnEdit;
	private JMenuItem mntmPrisoners;
	private JMenuItem mntmCrimes;
	private JMenuItem mntmLocations;


	public UserDialog(User user) {
		this.user = user;
		createUI();
		setData();
		addEventHandlers();
	}

	private void createUI(){
		
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnManage = new JMenu("Manage");
			menuBar.add(mnManage);
			
			mntmPrisoners = new JMenuItem("Prisoners");
			mnManage.add(mntmPrisoners);
			
			mntmCrimes = new JMenuItem("Crimes");
			mnManage.add(mntmCrimes);
			
			mntmLocations = new JMenuItem("Locations");
			mnManage.add(mntmLocations);
		}
		setBounds(100, 100, 450, 347);
		getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[194.00,grow][grow][grow][]"));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 0 0,alignx left,growy");
		
		TitledBorder border = new TitledBorder("Information");
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.TOP);
	    
	    panel_1.setBorder(border);
	    panel_1.setLayout(new MigLayout("", "[grow]", "[][][][][][][grow][]"));
	    
	    lblName = new JLabel();
	    panel_1.add(lblName, "cell 0 0");
	    
	    lblAge = new JLabel();
	    panel_1.add(lblAge, "cell 0 1");
	    
	    lblEmail = new JLabel();
	    panel_1.add(lblEmail, "cell 0 2");
	    
	    lblPhone = new JLabel();
	    panel_1.add(lblPhone, "cell 0 3");
	    
	    btnEdit = new JButton("Edit");
	    panel_1.add(btnEdit, "cell 0 7,alignx right");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 1 0,grow");
		
		btnClose = new JButton("Close");
		getContentPane().add(btnClose, "cell 1 3,alignx right");
		btnClose.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
	}
	
	private void setData(){
		lblName.setText("Name: ");
		lblAge.setText("Age: ");
		lblEmail.setText("Email: ");
		lblPhone.setText("Phone");
		
		lblName.setText(String.format("%s  %s %s", lblName.getText(), user.getLastName(), user.getFirstName()));
		lblAge.setText(String.format("%s    %d", lblAge.getText(), user.getAge()));
		lblEmail.setText(String.format("%s  %s", lblEmail.getText(), user.getEmail()));
		lblPhone.setText(String.format("%s   %s", lblPhone.getText(), user.getPhone()));
	}
	
	public void setData(User user){
		this.user = user;
		setData();
	}
	
	private void addEventHandlers(){
		btnClose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				UserDialog.this.dispose();
			}
			
		});
		btnEdit.addActionListener(new UIController.EditUser(user,  UserDialog.this));
		mntmPrisoners.addActionListener(new UIController.PrisonersListDialog());
		mntmCrimes.addActionListener(new UIController.CrimesListDialog());
		mntmLocations.addActionListener(new UIController.LocationsListDialog());
	}
	
}
