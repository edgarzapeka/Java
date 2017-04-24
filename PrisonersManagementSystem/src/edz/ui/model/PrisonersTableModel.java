package edz.ui.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import edz.data.prisoner.Prisoner;

public class PrisonersTableModel extends AbstractTableModel {
	
	private static final String[] COLUMN_NAMES = { "ID","First Name", "Last Name", "Age", "Race", "Sex", "Release date", "Confinment date", "Location" };
	//private static final Object[] COLUMN_TYPES = {Integer.class, String.class, String.class, Integer.class, String.class, String.class, String.class, String.class, Integer.class};
	public static final Integer[] tableColumnsSize = {10, 40, 40, 30, 70, 70, 100, 100, 30};
	
	private ArrayList<Prisoner> prisonersList = new ArrayList<Prisoner>();
	
	public PrisonersTableModel(){
		super();
	}
	
	public String getColumnName(int col) {
		return COLUMN_NAMES[col];
	}

	@Override
	public int getRowCount() {
		return prisonersList.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		switch(columnIndex){
		case 0:
			return prisonersList.get(rowIndex).getId();
		case 1:
			return prisonersList.get(rowIndex).getFirstName();
		case 2:
			return prisonersList.get(rowIndex).getLastName();
		case 3:
			return prisonersList.get(rowIndex).getAge();
		case 4:
			return prisonersList.get(rowIndex).getRace();
		case 5:
			return prisonersList.get(rowIndex).getSex();
		case 6:
			return prisonersList.get(rowIndex).getReleaseDate();
		case 7:
			return prisonersList.get(rowIndex).getConfinmentDate();
		case 8:
			return prisonersList.get(rowIndex).getLocation();
		default:
			return "";
		}
	}
	
	public Prisoner getPrisoner(int row){
		return prisonersList.get(row-1); 
	}
	
	public void setPrisonersList(ArrayList<Prisoner> prisonersList){
		this.prisonersList = prisonersList;
	}

}
