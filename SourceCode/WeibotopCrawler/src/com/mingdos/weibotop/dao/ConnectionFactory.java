package com.mingdos.weibotop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static final String URL = "";
	public static final String USER = "mingda";
	public static final String PASS = "qwerasdf";
	
	public static Connection getConnnection() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mariadb://localhost:3307/weibotopdb?user=" + USER + "&password=" + PASS);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Class Not Found", e);
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception", e);
		}
	}

}
