package edz.ui.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edz.data.crime.Crime;

public class CrimesTableModel extends AbstractTableModel {
	
	private static final String[] COLUMN_NAMES = { "ID","Title", "Description", "Date", "prisonerID"};
	public static final Integer[] TABLE_COLUMN_SIZE = {10, 40, 40, 40, 10};
	
	private ArrayList<Crime> crimeList = new ArrayList<Crime>();
	
	public CrimesTableModel(){
		super();
	}
	
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	@Override
	public int getRowCount() {
		return crimeList.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		switch(columnIndex){
		case 0:
			return crimeList.get(rowIndex).getId();
		case 1:
			return crimeList.get(rowIndex).getTitle();
		case 2:
			return crimeList.get(rowIndex).getDescription();
		case 3:
			return crimeList.get(rowIndex).getDate();
		case 4:
			return crimeList.get(rowIndex).getPrisonerId();
		default:
			return "";
		}
	}
	
	public Crime getCrime(int row){
		return crimeList.get(row-1); 
	}
	
	public void setCrimesList(ArrayList<Crime> prisonersList){
		this.crimeList = prisonersList;
	}

}
