package edz.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	public static final String DB_DRIVER_KEY = "db.driver";
	public static final String DB_URL_KEY = "db.url";
	public static final String DB_USER_KEY = "db.user";
	public static final String DB_PASSWORD_KEY = "db.password";


	private static Connection connection;
	private final Properties properties = new Properties();

	private static Database instance;

	private Database() throws FileNotFoundException, IOException {
		properties.load(new FileInputStream(DbConstants.DB_PROPERTIES_FILENAME));
	}

	public static Database getInstance() throws FileNotFoundException, IOException {
		if (instance == null)
			instance = new Database();
		return instance;
	}

	public Connection getConnection() throws SQLException {
		if (connection != null) {
			return connection;
		}

		try {
			connect();
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}

		return connection;
	}

	private void connect() throws ClassNotFoundException, SQLException {
		Class.forName(properties.getProperty(DB_DRIVER_KEY));
		connection = DriverManager.getConnection(properties.getProperty(DB_URL_KEY), properties.getProperty(DB_USER_KEY),
				properties.getProperty(DB_PASSWORD_KEY));
	}

	public void shutdown() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean tableExists(String targetTableName) throws SQLException {
		ResultSet resultSet = null;
		String tableName = null;
		try {
			DatabaseMetaData databaseMetaData = Database.getInstance().getConnection().getMetaData();

			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				tableName = resultSet.getString("TABLE_NAME");
				if (tableName.equalsIgnoreCase(targetTableName)) {
					return true;
				}
			}
		} catch (Exception e) {

		} finally {
			resultSet.close();
		}

		return false;
	}

}
