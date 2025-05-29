package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexaoDAO {
	private Connection con;
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/BibliotecaDB";
	private String user = "root";
	private String password = "159753";

	public Connection conectar() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
