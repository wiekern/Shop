package fpt.shop;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnector {
	
	private static final String DBURL = "jdbc:postgresql://java.is.uni-due.de/ws1011";
	private static final String DBDRIVER = "org.postgresql.Driver";
	private static final String USERNAME = "ws1011";
	private static final String PASSWORD = "ftpw10";
	private String[] types = new String[] {"TABLE"};
	private Connection dbConnection = null;
	private boolean isConnected = false;
	
	public JDBCConnector() {
		
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public Connection getDbConnection() {
		return dbConnection;
	}
	
	public boolean connectDB() {
		try {
			Class.forName(DBDRIVER);
			dbConnection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//System.err.println(e.getClass().getName() + ": " + e.getMessage());
			isConnected = false;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			//System.err.println(e.getClass().getName() + ": " + e.getMessage());	
			isConnected = false;
			return false;
		}
		
		System.out.println("Opened database successfully.");
		isConnected = true;
		return true;
	}
	
	public void closeConnection() {
		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
	}
	
	public void showAlltables() {
		if (!isConnected) return;
		
		DatabaseMetaData metaData = null;
		try {
			metaData = dbConnection.getMetaData();
			ResultSet rs = metaData.getTables(null, "public", "%", types);
			while(rs.next()) {
				System.out.println(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public long insert(String name, double price, int quantity) {
		if (!isConnected) return -1;
		String insertQuery = "INSERT INTO products (name, price, quantity)"
				+ "VALUES(?,?,?)";
		try {
			PreparedStatement insertProduct = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			insertProduct.setString(1, name);
			insertProduct.setDouble(2, price);
			insertProduct.setInt(3, quantity);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("The INSERT Statement is incorrect.");
			return -1;
		}
		
		try {
			return IDGenerator.generateId();
		} catch (IDOverflowException e) {
			e.printStackTrace();
			System.err.println("ID overflow, cannot insert more products.");
			return -1;
		}

	}
	
	public void insert(fpt.com.Product product) {
		if (!isConnected) return;
		
		String insertQuery = "INSERT INTO products (name, price, quantity)"
							+ "VALUES(?,?,?)";
		try {
			PreparedStatement insertProduct = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			insertProduct.setString(1, product.getName());
			insertProduct.setDouble(2, product.getPrice());
			insertProduct.setInt(3, product.getQuantity());
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("The INSERT Statement via Product is incorrect.");
		}
	}
	
	public Product read(long productId) {
		if (!isConnected) return null;
		
		String readQuery = "SELECT id,name,price,quantity FROM products WHERE id=?";
		PreparedStatement readProduct = null;
		ResultSet rs = null;
		Product product = null;
		String pName;
		double pPrice;
		int pQuantity;
		int pId;
		try {
			System.out.println(readQuery);
			readProduct = dbConnection.prepareStatement(readQuery);
			readProduct.setLong(1, productId);
			rs = readProduct.executeQuery();
			while(rs.next()) {
				pName = rs.getString("name");
				pPrice = rs.getDouble("price");
				pQuantity = rs.getInt("quantity");
				pId = rs.getInt("id");
				System.out.println("name:" + pName + " price:" + pPrice + " quantity:" + pQuantity + " id:" + pId);
				product = new Product(pName, pId, pPrice, pQuantity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("The SELECT Statement is incorrect.");
			return null;
		}
		
		return product;
	}
	

}
