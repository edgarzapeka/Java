package edz.data.crime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edz.dao.Dao;
import edz.data.prisoner.Prisoner;
import edz.data.prisoner.PrisonerDao.Fields;
import edz.database.DbConstants;

public class CrimeDao extends Dao {
	
	private static final String TABLE_NAME = DbConstants.CRIMES_TABLE_NAME;
	private static CrimeDao instance;

	protected CrimeDao() throws FileNotFoundException, IOException {
		super(TABLE_NAME);
	}
	
	public static CrimeDao getInstance(){
		if(instance == null){
			try {
				instance = new CrimeDao();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	@Override
	public void create() throws SQLException {
		String sql = String.format("CREATE TABLE %s (" //0
				+ "%s int NOT NULL AUTO_INCREMENT," //1
				+ "%s VARCHAR(150)," //2
				+ "%s TEXT," //3
				+ "%s date," //4
				+ "%s int," //5
				+ "PRIMARY KEY (%s)," //6
				+ "FOREIGN KEY (%s) REFERENCES Prisoners(id));", //7
				TABLE_NAME, //0
				Fields.ID.getName(), //1
				Fields.TITLE.getName(), //2
				Fields.DESCRIPTION.getName(), //3
				Fields.DATE.getName(), //4
				Fields.PRISONER.getName(), //5
				Fields.ID.getName(), //6
				Fields.PRISONER.getName() //7
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void add(Crime crime){
		String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ('%s', '%s', '%s', %d ); ", //0
				TABLE_NAME, //
				Fields.TITLE.getName(), //
				Fields.DESCRIPTION.getName(), //
				Fields.DATE.getName(), //
				Fields.PRISONER.getName(), //
				crime.getTitle(), //
				crime.getDescription(), //
				crime.getDate(), //
				crime.getPrisonerId() //
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void update(Crime crime){
		String sql = String.format("UPDATE %s SET " //0
				+ "%s = '%s'," //1
				+ "%s = '%s'," //2
				+ "%s = '%s'," //3
				+ "%s = %d " //4
				+ "WHERE %s = %d", //4
				TABLE_NAME, //0
				Fields.TITLE.getName(), crime.getTitle(), //1
				Fields.DESCRIPTION.getName(), crime.getDescription(), //2
				Fields.DATE.getName(), crime.getDate(), //3
				Fields.PRISONER.getName(), crime.getPrisonerId(), //3
				Fields.ID.getName(), crime.getId() //4
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(Crime crime){
		String sql = String.format("DELETE FROM %s WHERE %s = %d", TABLE_NAME, Fields.ID.getName(), crime.getId());
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Crime> getCrimesList() throws NumberFormatException, SQLException{
		ArrayList<Crime> result = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", TABLE_NAME);
		ResultSet resultSet;
		
		resultSet = super.executeSelect(sql);
		while (resultSet.next()) {
			Crime c = new Crime(Integer.valueOf(resultSet.getString(Fields.ID.getName())),
					resultSet.getString(Fields.TITLE.getName()),
					resultSet.getString(Fields.DESCRIPTION.getName()),
					resultSet.getString(Fields.DATE.getName()),
					Integer.valueOf(resultSet.getString(Fields.PRISONER.getName()))
			);
			result.add(c);
		}
		close(resultSet.getStatement());
		
		return result;
	}
	
	public enum Fields{
		
		ID("id", "int", 1, 1),
		TITLE("title", "varchar", 150, 2),
		DESCRIPTION("description", "text", 1, 3),
		DATE("date", "date", 1, 4),
		PRISONER("prisoner_id", "int", 1, 5);
		
		private String name;
		private String type;
		private int length;
		private int column;
		
		Fields(String name, String type, int length, int column){
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public int getLength() {
			return length;
		}

		public int getColumn() {
			return column;
		}
	}

}
