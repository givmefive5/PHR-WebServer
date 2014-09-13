package phr.dao.sqlimpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import phr.exceptions.DataAccessException;

import com.mchange.v2.c3p0.DataSources;

public class SqlConnectionManager {
	final private String DRIVER_NAME = "com.mysql.jdbc.Driver";
	final private String URL = "jdbc:mysql://localhost:3306/phr";
	final private String USER = "root";
	final private String PASSWORD = "1234";
	private DataSource dataSourceUnpooled;
	private DataSource dataSourcePooled;
	private static SqlConnectionManager instance = null;

	public Connection getConnection() throws DataAccessException {
		Connection connection = null;
		try {
			connection = dataSourcePooled.getConnection();
		} catch (SQLException e) {
			throw new DataAccessException("Cannot get connection to pool", e);
		}

		return connection;
	}

	public static SqlConnectionManager getInstance() throws DataAccessException {
		// double-checked locking
		if (instance == null) {
			synchronized (SqlConnectionManager.class) {
				if (instance == null) {
					try {
						instance = new SqlConnectionManager();
					} catch (SQLException e) {
						System.err.println("Cannot create connection pool SQL");
						throw new DataAccessException(
								"Cannot create connection pool instance", e);
					} catch (ClassNotFoundException e) {
						System.err
								.println("Cannot create connection pool Class.forName");
						throw new DataAccessException(
								"Cannot create connection pool instance", e);
					}
				}
			}
		}
		return instance;
	}

	private SqlConnectionManager() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_NAME);
		dataSourceUnpooled = DataSources
				.unpooledDataSource(URL, USER, PASSWORD);
		Map<String, String> overrideSettings = new HashMap<String, String>();
		overrideSettings.put("maxStatements", "200");
		overrideSettings.put("maxPoolSize", "50");
		dataSourcePooled = DataSources.pooledDataSource(dataSourceUnpooled);
	}
}
