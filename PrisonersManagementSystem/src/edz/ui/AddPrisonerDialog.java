package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edz.data.location.Location;
import edz.data.location.LocationDao;
import edz.data.prisoner.Prisoner;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class AddPrisonerDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField AgeTextField;
	private JTextField releaseDateTextField;
	private JTextField confinmentDateTextField;
	private JComboBox raceComboBox;
	private JComboBox sexComboBox;
	private JComboBox locationComboBox;
	private Prisoner prisoner;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public AddPrisonerDialog() {
		setUI();
		setData();
		addEventHandlers();
	}
	
	private void setUI(){
		setBounds(100, 100, 450, 351);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]"));
		{
			JLabel lblFirstName = new JLabel("First name: ");
			contentPanel.add(lblFirstName, "cell 0 0,alignx trailing");
		}
		{
			firstNameTextField = new JTextField();
			contentPanel.add(firstNameTextField, "cell 1 0,growx");
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last name: ");
			contentPanel.add(lblLastName, "cell 0 1,alignx trailing");
		}
		{
			lastNameTextField = new JTextField();
			contentPanel.add(lastNameTextField, "cell 1 1,growx");
			lastNameTextField.setColumns(10);
		}
		{
			JLabel lblAge = new JLabel("Age: ");
			contentPanel.add(lblAge, "cell 0 2,alignx trailing");
		}
		{
			AgeTextField = new JTextField();
			contentPanel.add(AgeTextField, "cell 1 2,growx");
			AgeTextField.setColumns(10);
		}
		{
			JLabel lblRace = new JLabel("Race: ");
			contentPanel.add(lblRace, "cell 0 3,alignx trailing");
		}
		{
			raceComboBox = new JComboBox();
			contentPanel.add(raceComboBox, "cell 1 3,growx");
		}
		{
			JLabel lblSex = new JLabel("Sex: ");
			contentPanel.add(lblSex, "cell 0 4,alignx trailing");
		}
		{
			sexComboBox = new JComboBox();
			contentPanel.add(sexComboBox, "cell 1 4,growx");
		}
		{
			JLabel lblReleaseDate = new JLabel("Release date: ");
			contentPanel.add(lblReleaseDate, "cell 0 5,alignx trailing");
		}
		{
			releaseDateTextField = new JTextField();
			contentPanel.add(releaseDateTextField, "cell 1 5,growx");
			releaseDateTextField.setColumns(10);
		}
		{
			JLabel lblConfinmentDate = new JLabel("Confinment date: ");
			contentPanel.add(lblConfinmentDate, "cell 0 6,alignx trailing");
		}
		{
			confinmentDateTextField = new JTextField();
			contentPanel.add(confinmentDateTextField, "cell 1 6,growx");
			confinmentDateTextField.setColumns(10);
		}
		{
			JLabel lblLocation = new JLabel("Location: ");
			contentPanel.add(lblLocation, "cell 0 7,alignx trailing");
		}
		{
			locationComboBox = new JComboBox();
			contentPanel.add(locationComboBox, "cell 1 7,growx");
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
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenuItem mntmInfo = new JMenuItem("Info");
				menuBar.add(mntmInfo);
			}
		}
	}
	
	private void setData(){
		raceComboBox.addItem("American");
		raceComboBox.addItem("Indian");
		raceComboBox.addItem("Asian");
		raceComboBox.addItem("Black");
		raceComboBox.addItem("White");
		
		sexComboBox.addItem("male");
		sexComboBox.addItem("female");
		sexComboBox.addItem("non-binary");
		
		LocationDao instance = LocationDao.getInstance();
		try {
			ArrayList<Location> locations = instance.getLocationList();
			
			for(Location l : locations){
				locationComboBox.addItem(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setPrisoner(){
		Location l = (Location) locationComboBox.getSelectedItem(); 
		prisoner = new Prisoner.Builder(-1) //
				.firstName(firstNameTextField.getText()) // 
				.lastName(lastNameTextField.getText()) //
				.age(Integer.valueOf(AgeTextField.getText())) //
				.race(raceComboBox.getSelectedItem().toString()) //
				.sex(sexComboBox.getSelectedItem().toString()) //
				.releaseDate(releaseDateTextField.getText()) //
				.confinmentDate(confinmentDateTextField.getText()) //
				.location(l.getId()) //
				.build(); //
	}
	
	private void addEventHandlers(){
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setPrisoner();
				AddPrisonerDialog.this.dispose();
			}
			
		});
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AddPrisonerDialog.this.dispose();
			}
			
		});
	}
	
	public Prisoner getPrisoner(){
		return prisoner;
	}

}
