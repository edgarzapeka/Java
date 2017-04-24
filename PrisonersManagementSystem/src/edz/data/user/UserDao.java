package edz.data.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Field;

import edz.dao.Dao;
import edz.database.DbConstants;

public class UserDao extends Dao {
	
	private static final String TABLE_NAME = DbConstants.USERS_TABLE_NAME;
	
	private static UserDao instance;
	
	protected UserDao() throws FileNotFoundException, IOException {
		super(TABLE_NAME);
	}
	
	public static UserDao getInstance() throws FileNotFoundException, IOException {
		if (instance == null)
			instance = new UserDao();

		return instance;
	}

	@Override
	public void create() {
		String sql = String.format("CREATE TABLE %s (" //0
				+ "%s int NOT NULL AUTO_INCREMENT, " //1
				+ "%s VARCHAR(50)," //2
				+ "%s VARCHAR(50)," //3
				+ "%s int," //4
				+ "%s VARCHAR(50)," //5
				+ "%s VARCHAR(10)," //6
				+ "%s VARCHAR(150)," //7
				+ "%s date, PRIMARY KEY(%s));", //8
				TABLE_NAME,//0
				Fields.ID.getName(), //1 
				Fields.FIRST_NAME.getName(), //2
				Fields.LAST_NAME.getName(), //3
				Fields.AGE.getName(), //4
				Fields.PASSWORD.getName(), //5
				Fields.PHONE.getName(), //6
				Fields.EMAIL.getName(), //7
				Fields.DATE.getName(),//8
				Fields.ID.getName()); //9
		try{
			super.executeUpdate(sql);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void update(User user){
		
		String sql = String.format("UPDATE %s SET " //0
				+ "%s=%d," //1
				+ "%s='%s'," //2
				+ "%s='%s'," //3
				+ "%s=%d," //4
				+ "%s='%s'," //5
				+ "%s='%s'," //6
				+ "%s='%s'" //7
				//+ "%s='%s'" //8
				+ " WHERE %s=%d;", TABLE_NAME, //0
				Fields.ID.getName(), user.getId(), //1
				Fields.FIRST_NAME.getName(), user.getFirstName(), //2
				Fields.LAST_NAME.getName(), user.getLastName(), //3
				Fields.AGE.getName(), user.getAge(), //4
				Fields.PASSWORD.getName(), user.getPassword(),//5
				Fields.PHONE.getName(), user.getPhone(), //6
				Fields.EMAIL.getName(), user.getEmail(), //7
				//Fields.DATE.getName(), user.getDate(), //8
				Fields.ID.getName(), user.getId()); //9
		try {
			super.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(User user){
		String sql = String.format("INSERT INTO %s VALUES(" //0
				+ "%d," //1
				+ "'%s'," //2
				+ "'%s'," //3
				+ "%d," //4
				+ "'%s'," //5
				+ "'%s'," //6
				+ "'%s'," //7
				+ "'%s');" //8
				, TABLE_NAME, //0
				user.getId(), //1
				user.getFirstName(), //2
				user.getLastName(), //3
				user.getAge(), //4
				user.getPassword(), //5
				user.getPhone(), //6
				user.getEmail(), //7
				user.getDate()); //8
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete(User user){
		String sql = String.format("DELETE FROM %s WHERE id=%d", TABLE_NAME, user.getId());
		try{
			super.executeUpdate(sql);
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public User getUser(String login, String password){
		String sql = String.format("SELECT * FROM %s WHERE email='%s' AND password='%s'", TABLE_NAME, login, password);
		User user = null;
		ResultSet selected;
		int count = 0;
		try{
			selected = super.executeSelect(sql);
			while(selected.next()){
				user = new User.Builder(selected.getString(Fields.FIRST_NAME.getName()) //1
						, selected.getString(Fields.LAST_NAME.getName())) //2
						.id(Integer.valueOf(selected.getString(Fields.ID.getName()))) //3
						.password(selected.getString(Fields.PASSWORD.getName())) //4
						.age(Integer.valueOf(selected.getString(Fields.AGE.getName()))) //5
						.phone(selected.getString(Fields.PHONE.getName())) //6
						.email(selected.getString(Fields.EMAIL.getName())) //7
						.date(selected.getString(Fields.DATE.getName())) //8
						.build(); //
				count++;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		return user;
	}
	
	public enum Fields{
		
		ID("id", "int", 1, 1),
		FIRST_NAME("first_name", "varchar", 50, 2),
		LAST_NAME("last_name", "varchar", 50, 3),
		AGE("age", "int", 1, 4),
		PASSWORD("password", "varchar", 50, 5),
		PHONE("phone", "varchar", 10, 6),
		EMAIL("email", "varchar", 150, 7),
		DATE("date", "date", 1, 8);
		
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
		
		public String getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public int getLength() {
			return length;
		}

		public int getColumn() {
			return column;
		}
	}

}
