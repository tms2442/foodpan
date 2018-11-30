package aurora_food_pantry;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DBconnect {
	private Connection connection;
	private int numQueries;
	private String DBName;
	private String PWFilePath;
	private String PassW;
	private String UserId;
	DBconnect(String DBName, String PWFilePath ) {
		this.DBName = DBName;
		this.PWFilePath = PWFilePath;
		
		this.setConnect();
	}
	
	private void setConnect() {
		
		try {
			String dbInfo = "jdbc:mysql://127.0.0.1/pantry";
			connection = DriverManager.getConnection(dbInfo, "root", "");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet doQuery( String SQL ) throws SQLException {
		
		Statement statement = connection.createStatement();
		
		ResultSet resultSet = statement.executeQuery( SQL );
		return resultSet;
	}
	
}
