package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://jbstv.synology.me:3307/kdlibrary";

	public static Connection getConnection() throws Exception {
		Class.forName(DRIVER);
		Connection con = DriverManager.getConnection(URL, "kdlibrary", "Kd03131223!");
		return con;

	}

}
