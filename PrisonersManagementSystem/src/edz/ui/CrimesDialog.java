package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import edz.data.crime.Crime;
import edz.ui.model.CrimesTableModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;

public class CrimesDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ArrayList<Crime> crimesList;
	private CrimesTableModel model;
	private JTable table;
	private JButton addButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JButton editButton;

	/**
	 * Create the dialog.
	 */
	public CrimesDialog(ArrayList<Crime> crimesList) {
		this.crimesList = crimesList;
		setUI();
		setData();
		addEventHandlers();
	}
	
	public void setUI(){
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("fill, insets 0", "[][][]", "[2][][][]"));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JPanel buttonsPanel = new JPanel(new MigLayout("fill, insets 0"));
		contentPanel.add(buttonsPanel, "cell 0 0 3 1,alignx center,aligny top");
		
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		
		buttonsPanel.add(addButton, "left");
		buttonsPanel.add(editButton, "center");
		buttonsPanel.add(deleteButton, "right");
		
		TitledBorder border = new TitledBorder("Crimes list");
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.TOP);
		JPanel tablePanel = new JPanel(new MigLayout("fill, insets 0"));
		tablePanel.setBorder(border);
		contentPanel.add(tablePanel, "cell 0 1 3 3,grow");
		
		model = new CrimesTableModel();
		table = new JTable(model);
		tablePanel.add(new JScrollPane(table), "growx");
	}
	
	private void setData(){
		model.setCrimesList(crimesList);
		model.fireTableDataChanged();
	}
	
	public void setCrimeList(ArrayList<Crime> crimesList){
		this.crimesList = crimesList;
		setData();
	}
	
	private void addEventHandlers(){
		addButton.addActionListener(new UIController.AddCrime(CrimesDialog.this));
		deleteButton.addActionListener(new UIController.DeleteCrime(CrimesDialog.this));
		editButton.addActionListener(new UIController.EditCrime(CrimesDialog.this));
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CrimesDialog.this.dispose();
			}
			
		});
	}
	
	public Crime getSelectedCrime(){
		return new Crime((int)model.getValueAt(table.getSelectedRow(), 0), String.valueOf(model.getValueAt(table.getSelectedRow(), 1)), String.valueOf(model.getValueAt(table.getSelectedRow(), 2)), String.valueOf(model.getValueAt(table.getSelectedRow(), 3)), (int)model.getValueAt(table.getSelectedRow(), 4));
	}

}
