package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import edz.data.crime.Crime;
import edz.data.prisoner.Prisoner;
import edz.data.prisoner.PrisonerDao;
import net.miginfocom.swing.MigLayout;

public class EditCrimeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField titleTextField;
	private JComboBox<Prisoner> prisonerComboBox;
	private JTextPane descriptionTextPane;
	private JButton cancelButton;
	private JButton saveButton;
	private Crime crime;
	private JLabel lblDate;
	private JTextField dateTextField;

	public EditCrimeDialog(Crime crime){
		this.crime = crime;
		setUI();
		setData();
		addEventHandlers();
	}

	private void setUI(){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][grow][][]"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				saveButton = new JButton("Save");
				saveButton.setActionCommand("Save");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
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
			lblDate = new JLabel("Date:");
			contentPanel.add(lblDate, "cell 0 2,alignx trailing");
		}
		{
			dateTextField = new JTextField();
			contentPanel.add(dateTextField, "cell 1 2,growx");
			dateTextField.setColumns(10);
		}
		{
			JLabel lblPrisoner = new JLabel("Prisoner");
			contentPanel.add(lblPrisoner, "cell 0 3,alignx trailing");
		}
		{
			prisonerComboBox = new JComboBox<Prisoner>();
			contentPanel.add(prisonerComboBox, "cell 1 3,growx");
		}
	}
	
	private void setData(){
		titleTextField.setText(crime.getTitle());
		descriptionTextPane.setText(crime.getDescription());
		dateTextField.setText(crime.getDate());
		
		try{
			ArrayList<Prisoner> prisonersList = PrisonerDao.getInstance().getPrisonersList();
			for(Prisoner p : prisonersList){
				prisonerComboBox.addItem(p);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		prisonerComboBox.setSelectedItem(crime.getPrisonerId());
	}
	
	private void addEventHandlers(){
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateCrime();
				EditCrimeDialog.this.dispose();
			}
			
		});
		
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				crime = null;
				EditCrimeDialog.this.dispose();
			}
			
		});
	}
	
	private void updateCrime(){
		crime.setTitle(titleTextField.getText());
		crime.setDescription(descriptionTextPane.getText());
		crime.setDate(dateTextField.getText());
		Prisoner p = (Prisoner) prisonerComboBox.getSelectedItem();
		crime.setPrisonerId(p.getId());
	}
	
	public Crime getCrime(){
		return crime;
	}

}
