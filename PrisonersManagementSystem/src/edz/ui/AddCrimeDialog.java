package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import edz.data.crime.Crime;
import edz.data.prisoner.Prisoner;
import edz.data.prisoner.PrisonerDao;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

public class AddCrimeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField titleTextField;
	private JComboBox<Prisoner> prisonerComboBox;
	private JTextPane descriptionTextPane;
	private JButton cancelButton;
	private JButton okButton;
	private Crime crime;


	/**
	 * Create the dialog.
	 */
	public AddCrimeDialog() {
		setUI();
		addEventHandlers();
		setData();
	}
	
	private void setUI(){
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][grow][]"));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				okButton = new JButton("Ok");
				cancelButton.setActionCommand("Ok");
				buttonPane.add(okButton);
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
		
		TitledBorder border = new TitledBorder("Add Crime");
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.CENTER);
		contentPanel.setBorder(border);
		{
			JLabel lblTitle = new JLabel("Title");
			contentPanel.add(lblTitle, "cell 0 0,alignx trailing");
		}
		{
			titleTextField = new JTextField();
			contentPanel.add(titleTextField, "cell 1 0,growx");
			titleTextField.setColumns(10);
		}
		{
			JLabel lblDescription = new JLabel("Description");
			contentPanel.add(lblDescription, "cell 0 1");
		}
		{
			descriptionTextPane = new JTextPane();
			contentPanel.add(descriptionTextPane, "cell 1 1,grow");
		}
		{
			JLabel lblPrisoner = new JLabel("Prisoner");
			contentPanel.add(lblPrisoner, "cell 0 2,alignx trailing");
		}
		{
			prisonerComboBox = new JComboBox<Prisoner>();
			contentPanel.add(prisonerComboBox, "cell 1 2,growx");
		}
	}
	
	private void setData(){
		try{
			ArrayList<Prisoner> prisonersList = PrisonerDao.getInstance().getPrisonersList();
			for(Prisoner p : prisonersList){
				prisonerComboBox.addItem(p);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void addEventHandlers(){
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				AddCrimeDialog.this.dispose();
			}
		});
		
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setCrime();
				AddCrimeDialog.this.dispose();
			}
			
		});
	}
	
	private void setCrime(){
		LocalDate date = LocalDate.now();
		Prisoner p = (Prisoner) prisonerComboBox.getSelectedItem();
		crime = new Crime(1, titleTextField.getText(), descriptionTextPane.getText(),date.toString(), p.getId());
	}
	
	public Crime getCrime(){
		return crime;
	}

}
