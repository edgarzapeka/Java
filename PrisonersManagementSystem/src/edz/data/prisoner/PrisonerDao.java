package edz.data.prisoner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edz.dao.Dao;
import edz.data.location.Location;
import edz.data.location.LocationDao.Fields;
import edz.database.DbConstants;

public class PrisonerDao extends Dao {
	
	private static final String TABLE_NAME = DbConstants.PRISONERS_TABLE_NAME;
	private static PrisonerDao instance;

	protected PrisonerDao() throws FileNotFoundException, IOException {
		super(TABLE_NAME);
	}
	
	public static PrisonerDao getInstance(){
		if (instance == null){
			try {
				instance = new PrisonerDao();
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
				+ "%s VARCHAR(50)," //2
				+ "%s VARCHAR(50)," //3
				+ "%s int," //4
				+ "%s VARCHAR(11)," //5
				+ "%s VARCHAR(11)," //6
				+ "%s date," //7
				+ "%s date," //8
				+ "%s int," //9
				+ "PRIMARY KEY(%s)," //10
				+ "CONSTRAINT chk_Sex CHECK (%s IN ('male', 'female', 'non-binary'))," //11
				+ "CONSTRAINT chk_Race CHECK(%s IN ('American', 'Indian', 'Asian', 'Black', 'White'))," //12
				+ "FOREIGN KEY(%s) REFERENCES Locations(id)" //13
				+ ");", 
				TABLE_NAME, //0
				Fields.ID.getName(), //1
				Fields.FIRST_NAME.getName(), //2
				Fields.LAST_NAME.getName(), //3
				Fields.AGE.getName(), //4
				Fields.RACE.getName(), //5
				Fields.SEX.getName(), //6
				Fields.RELEASE_DATE.getName(), //7
				Fields.CONFINMENT_DATE.getName(), //8
				Fields.LOCATION.getName(), //9
				Fields.ID.getName(), //10
				Fields.SEX.getName(), //11
				Fields.RACE.getName(), //12
				Fields.LOCATION.getName() //13
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void add(Prisoner prisoner){
		String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s ) VALUES(" //0
				+ "'%s'," //1
				+ "'%s'," //2
				+ "%d, " //3
				+ "'%s', "//4
				+ "'%s', " //5
				+ "'%s'," //6
				+ "'%s',"
				+ "%d" //7
				+ ");", TABLE_NAME, //0
				Fields.FIRST_NAME.getName(), Fields.LAST_NAME.getName(), Fields.AGE.getName(), Fields.RACE.getName(), Fields.SEX.getName(), Fields.RELEASE_DATE.getName(), Fields.CONFINMENT_DATE.getName(), Fields.LOCATION.getName(), //0
				prisoner.getFirstName(), //1
				prisoner.getLastName(), //2
				prisoner.getAge(), //3
				prisoner.getRace(), //4
				prisoner.getSex(), //5
				prisoner.getReleaseDate(), //6
				prisoner.getConfinmentDate(), //7
				prisoner.getLocation()
				);
		try{
			super.executeUpdate(sql);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void update(Prisoner prisoner){
		String sql = String.format("UPDATE %s SET " //0
				+ "%s = '%s'," //1
				+ "%s = '%s'," //2
				+ "%s = %d," //3
				+ "%s = '%s'," //4
				+ "%s = '%s'," //5
				+ "%s = '%s'," //6
				+ "%s = %d " //7
				+ "WHERE %s = %d;", //8
				TABLE_NAME, //0
				Fields.FIRST_NAME.getName(), prisoner.getFirstName(), //1
				Fields.LAST_NAME.getName(), prisoner.getLastName(), //2
				Fields.AGE.getName(), prisoner.getAge(), //3
				Fields.RACE.getName(), prisoner.getRace(), //4
				Fields.SEX.getName(), prisoner.getSex(), //5
				Fields.RELEASE_DATE.getName(), prisoner.getReleaseDate(), //6
				Fields.LOCATION.getName(), prisoner.getLocation(), //7
				Fields.ID.getName(), prisoner.getId() //8
				);
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(Prisoner prisoner){
		String sql = String.format("DELETE FROM %s WHERE %s = %d", TABLE_NAME, Fields.ID.getName(), prisoner.getId());
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Prisoner> getPrisonersList() throws SQLException{
		
		ArrayList<Prisoner> result = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", TABLE_NAME);
		ResultSet resultSet;
		
		resultSet = super.executeSelect(sql);
		while (resultSet.next()) {
			result.add(new Prisoner.Builder(Integer.valueOf(resultSet.getString(Fields.ID.getName())))
					.firstName(resultSet.getString(Fields.FIRST_NAME.getName()))
					.lastName(resultSet.getString(Fields.LAST_NAME.getName()))
					.age(Integer.valueOf(resultSet.getString(Fields.AGE.getName())))
					.race(resultSet.getString(Fields.RACE.getName()))
					.sex(resultSet.getString(Fields.SEX.getName()))
					.releaseDate(resultSet.getString(Fields.RELEASE_DATE.getName()))
					.confinmentDate(resultSet.getString(Fields.CONFINMENT_DATE.getName()))
					.location(Integer.valueOf(resultSet.getString(Fields.LOCATION.getName())))
					.build());
		}
		close(resultSet.getStatement());
		
		return result;
	}
	
	public enum Fields{
		
		ID("id", "int", 1, 1), //PK
		FIRST_NAME("first_name", "varchar", 50, 2),
		LAST_NAME("last_name", "varchar", 50, 3),
		AGE("age", "int", 1, 4),
		RACE("race", "varchar", 11, 5),
		SEX("sex", "varchar", 11, 6),
		RELEASE_DATE("release_date", "date", 1, 7),
		CONFINMENT_DATE("confinment_date", "date", 1, 8),
		LOCATION("location", "int", 1, 9); // FK
		
		private final String name;
		private final String type;
		private final int length;
		private final int column;
		
		Fields(String name, String type, int length, int column){
			this.name = name;
			this.type = type;
			this.length = length;
			this.column = column;
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
