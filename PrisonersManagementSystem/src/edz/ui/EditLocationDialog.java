package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import edz.data.location.Location;
import net.miginfocom.swing.MigLayout;

public class EditLocationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameTextField;
	private JTextField addressTextField;
	private JButton closeButton;
	private JButton saveButton;
	private Location location;
	private JTextPane descriptionTextPane;

	public EditLocationDialog(Location location) {
		this.location = location;
		setUI();
		setData();
		addEventHandlers();
	}

	private void setUI(){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][grow][]"));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				saveButton = new JButton("Save");
				saveButton.setActionCommand("Save");
				buttonPane.add(saveButton);
			}
			{
				closeButton = new JButton("Close");
				closeButton.setActionCommand("Close");
				buttonPane.add(closeButton);
			}
		}
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmInfo = new JMenuItem("info");
		menuBar.add(mntmInfo);
		
		JLabel lblName = new JLabel("Name");
		contentPanel.add(lblName, "cell 0 0,alignx trailing");
		
		nameTextField = new JTextField();
		contentPanel.add(nameTextField, "cell 1 0,growx");
		nameTextField.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		contentPanel.add(lblDescription, "cell 0 1");
		
		descriptionTextPane = new JTextPane();
		contentPanel.add(descriptionTextPane, "cell 1 1,grow");
		
		JLabel lblAddress = new JLabel("Address");
		contentPanel.add(lblAddress, "cell 0 2,alignx trailing");
		
		addressTextField = new JTextField();
		contentPanel.add(addressTextField, "cell 1 2,growx");
		addressTextField.setColumns(10);
	}
	
	private void updateData(){
		location.setName(nameTextField.getText());
		location.setDescription(descriptionTextPane.getText());
		location.setAddress(addressTextField.getText());
	}
	
	private void setData(){
		nameTextField.setText(location.getName());
		descriptionTextPane.setText(location.getDescription());
		addressTextField.setText(location.getAddress());
	}
	
	private void addEventHandlers(){
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateData();
				EditLocationDialog.this.dispose();
			}
			
		});
		closeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				location = null;
				EditLocationDialog.this.dispose();
			}
			
		});
	}
	
	public Location getLocationValue(){
		return location;
	}
}
