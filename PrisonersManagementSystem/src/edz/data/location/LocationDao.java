package edz.data.location;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edz.dao.Dao;
import edz.database.DbConstants;

public class LocationDao extends Dao {
	
	private static final String TABLE_NAME = DbConstants.LOCATIONS_TABLE_NAME;
	private static LocationDao instance;

	protected LocationDao() throws FileNotFoundException, IOException {
		super(TABLE_NAME);
	}

	public static LocationDao getInstance(){
		if (instance == null){
			try {
				instance = new LocationDao();
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
				+ "%s varchar(50)," //2
				+ "%s text," //3
				+ "%s varchar(50)," //4
				+ "PRIMARY KEY (%s));", //5
				TABLE_NAME, //0
				Fields.ID.getName(), //1
				Fields.NAME.getName(), //2
				Fields.DESCRIPTION.getName(), //3
 				Fields.ADDRESS.getName(), //4
 				Fields.ID.getName()//5
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void add(Location location){
		String sql = String.format("INSERT INTO %s (" //0
				+ "%s," //1
				+ "%s," //2
				+ "%s)" //3
				+ " VALUES ( '%s'," //4
				+ " '%s'," //5
				+ " '%s' " //6
				+ ");", 
				TABLE_NAME, //0
				Fields.NAME.getName(), //1
				Fields.DESCRIPTION.getName(), //2
				Fields.ADDRESS.getName(), //3
				location.getName(), //4
				location.getDescription(), //5
				location.getAddress() //6
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void update(Location location){
		String sql = String.format("UPDATE %s SET " //0
				+ "%s = '%s'," //1
				+ " %s = '%s', " //2
				+ "%s = '%s'" //3
				+ "WHERE %s = %d",  //4
				TABLE_NAME, //0
				Fields.NAME.getName(), location.getName(), //1
				Fields.DESCRIPTION.getName(), location.getDescription(), //2
				Fields.ADDRESS.getName(), location.getAddress(), //3
				Fields.ID.getName(), location.getId()
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(Location location){
		String sql = String.format("DELETE FROM %s WHERE %s = %d", TABLE_NAME, Fields.ID.getName(), location.getId());
		try{
			super.executeUpdate(sql);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Location> getLocationList() throws SQLException{
		
		ArrayList<Location> result = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", TABLE_NAME);
		ResultSet resultSet;
		
		resultSet = super.executeSelect(sql);
		while (resultSet.next()) {
			result.add(new Location.Builder(Integer.valueOf(resultSet.getString(Fields.ID.getName()))).name(resultSet.getString(Fields.NAME.getName())).description(resultSet.getString(Fields.DESCRIPTION.getName())).address(resultSet.getString(Fields.ADDRESS.getName())).build());
		}
		close(resultSet.getStatement());
		
		return result;
	}
	
	public enum Fields{
		
		ID("id", "int", 1, 1),
		NAME("name", "varchar", 50, 2),
		DESCRIPTION("description", "text", 1, 3),
		ADDRESS("address", "varchar", 50, 4);
		
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
