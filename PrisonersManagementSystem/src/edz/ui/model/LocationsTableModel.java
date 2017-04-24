package edz.ui.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edz.data.location.Location;

public class LocationsTableModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "ID","Name", "Description", "Address"};
	public static final Integer[] TABLE_COLUMN_SIZE = {10, 30, 50, 40};
	
	private ArrayList<Location> locationsList = new ArrayList<>();
	
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	
	@Override
	public int getRowCount() {
		return locationsList.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return locationsList.get(rowIndex).getId();
		case 1:
			return locationsList.get(rowIndex).getName();
		case 2:
			return locationsList.get(rowIndex).getDescription();
		case 3:
			return locationsList.get(rowIndex).getAddress();
		default:
			return null;
		}
	}

	public Location getLocation(int row){
		return locationsList.get(row-1); 
	}
	
	public void setLocationList(ArrayList<Location> locationsList){
		this.locationsList = locationsList;
	}

	
}
