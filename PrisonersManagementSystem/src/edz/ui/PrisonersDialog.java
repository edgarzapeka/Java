package edz.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import edz.data.prisoner.Prisoner;
import edz.ui.model.PrisonersTableModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PrisonersDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonsPanel;
	private JPanel tableListPanel;
	private ArrayList<Prisoner> prisonersList;
	private JTable table;
	private PrisonersTableModel model;
	private JButton btnClose;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	
	private TableColumn column = null;
	

	/**
	 * Create the dialog.
	 */
	public PrisonersDialog(ArrayList<Prisoner> prisonersList) {
		this.prisonersList = prisonersList;
		setUI();
		setData();
		addEventHandlers();
		
		for (int i = 0; i < PrisonersTableModel.tableColumnsSize.length; i++) {
		    column = table.getColumnModel().getColumn(i);
		    column.setPreferredWidth(PrisonersTableModel.tableColumnsSize[i]);
		}
	}
	
	private void setUI(){
		
		setBounds(100, 100, 700, 300);
		contentPanel.setLayout(new MigLayout("fill", "[][][]", "[2][][][]"));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mntmInfo = new JMenuItem("Info");
		menuBar.add(mntmInfo);
		
		buttonsPanel = new JPanel(new MigLayout("fill, insets 0"));
		
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		buttonsPanel.add(addButton, "left");
		buttonsPanel.add(editButton, "center");
		buttonsPanel.add(deleteButton, "right");
		
		contentPanel.add(buttonsPanel, "cell 0 0 3 1,alignx center,aligny top");
		
		tableListPanel = new JPanel(new MigLayout("fill, insets 0"));
		model = new PrisonersTableModel();
		table = new JTable(model);
		
		tableListPanel.add(new JScrollPane(table), "growx");
		contentPanel.add(tableListPanel, "cell 0 1 3 1,grow");
		
		btnClose = new JButton("Close");
		contentPanel.add(btnClose, "cell 2 3,alignx right");
		getContentPane().add(contentPanel);
	}
	
	public void setPrisonersList(ArrayList<Prisoner> list){
		prisonersList = list;
		setData();
	}
	
	private void setData(){
		model.setPrisonersList(prisonersList);
		model.fireTableDataChanged();
	}
	
	private void addEventHandlers(){
		btnClose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				PrisonersDialog.this.dispose();
			}
		});
		addButton.addActionListener(new UIController.AddPrisoner(PrisonersDialog.this));
		deleteButton.addActionListener(new UIController.DeletePrisoner( PrisonersDialog.this));
		editButton.addActionListener(new UIController.EditPrisoner( PrisonersDialog.this));
	}
	
	public Prisoner getSelectedPrisoner(){
		TableModel modell = table.getModel();
		return new Prisoner.Builder((int)modell.getValueAt(table.getSelectedRow(), 0)) //
				.firstName(String.valueOf(modell.getValueAt(table.getSelectedRow(), 1))) //
				.lastName(String.valueOf(modell.getValueAt(table.getSelectedRow(), 2))) //
				.age((int)modell.getValueAt(table.getSelectedRow(), 3)) //
				.race(String.valueOf(modell.getValueAt(table.getSelectedRow(), 4))) //
				.sex(String.valueOf(modell.getValueAt(table.getSelectedRow(), 5))) //
				.releaseDate(String.valueOf(modell.getValueAt(table.getSelectedRow(), 6))) //
				.confinmentDate(String.valueOf(modell.getValueAt(table.getSelectedRow(), 7))) //
				.location((int)modell.getValueAt(table.getSelectedRow(), 8)) //
				.build(); //
	}
}
