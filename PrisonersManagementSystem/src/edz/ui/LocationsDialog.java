package edz.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import edz.data.location.Location;
import edz.data.location.LocationDao;
import edz.ui.model.LocationsTableModel;
import net.miginfocom.swing.MigLayout;

public class LocationsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton addButton;
	private JButton deleteButton;
	private JButton editButton;
	private JTable table;
	private LocationsTableModel model;
	private JButton closeButton;
	
	public LocationsDialog() {
		setUI();
		addEventHandlers();
		setData();
	}
	
	private void setUI(){
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new MigLayout("fill, insets 0", "[][][]", "[2][][][]"));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				closeButton = new JButton("Close");
				closeButton.setActionCommand("Cancel");
				buttonPane.add(closeButton);
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
		
		TitledBorder border = new TitledBorder("Locations list");
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.TOP);
		JPanel tablePanel = new JPanel(new MigLayout("fill, insets 0"));
		tablePanel.setBorder(border);
		contentPanel.add(tablePanel, "cell 0 1 3 3,grow");
		
		model = new LocationsTableModel();
		table = new JTable(model);
		tablePanel.add(new JScrollPane(table));
	}
	
	private void addEventHandlers(){
		addButton.addActionListener(new UIController.AddLocation(LocationsDialog.this));
		editButton.addActionListener(new UIController.EditLocation(LocationsDialog.this));
		deleteButton.addActionListener(new UIController.DeleteLocation(LocationsDialog.this));
		closeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				LocationsDialog.this.dispose();
			}
			
		});
		
	}

	private void setData(){
		try{
			model.setLocationList(LocationDao.getInstance().getLocationList());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void setLocationsList(ArrayList<Location> locationsList){
		model.setLocationList(locationsList);
		model.fireTableDataChanged();
	}
	
	public Location getSelectedLocation(){
		return new Location.Builder((int)model.getValueAt(table.getSelectedRow(), 0)).name(String.valueOf(model.getValueAt(table.getSelectedRow(), 1))).description(String.valueOf(model.getValueAt(table.getSelectedRow(), 2))).address(String.valueOf(model.getValueAt(table.getSelectedRow(), 3))).build();
	}
}
