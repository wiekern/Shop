package fpt.shop;

import fpt.com.db.AbstractDatabaseStrategy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnector extends AbstractDatabaseStrategy {
	
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
			System.out.println("Class of Database driver not found.");
			isConnected = false;
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed with the incorrect SQL statement.");	
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
		ResultSet idRS = null;
		String insertQuery = "INSERT INTO products (name, price, quantity)"
				+ "VALUES(?,?,?)";
		String updateQuery = "UPDATE products SET name=" + name + ",price=" + price + ",quantity=" + quantity + "WHERE id=?";
		long id = -1;
		//String productIdQuery = "SLECT id FROM products WHERE name=" + name + ",price=" + price + "quantity=" + quantity;
		try {
			PreparedStatement insertProduct = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			insertProduct.setString(1, name);
			insertProduct.setDouble(2, price);
			insertProduct.setInt(3, quantity);
			System.out.println("Hallo.");
			if (!insertProduct.execute()) {
				System.out.println("insert product via JDBC failed, try to update it.");
				idRS = insertProduct.getGeneratedKeys();
				while(idRS.next()) {
					id = idRS.getLong(1);
					System.out.println("Generated id=" + id + ", to update this record.");
					PreparedStatement updateProduct = dbConnection.prepareStatement(updateQuery);
					updateProduct.setLong(1, id);
					
				}
			};
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("The INSERT Statement is incorrect.");
		}
		
		return id;
	}
	
	public void insert(fpt.com.Product product) {
		insert(product.getName(), product.getPrice(), product.getQuantity());
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

	@Override
	public fpt.com.Product readObject() throws IOException {
		return null;
	}

	@Override
	public void writeObject(fpt.com.Product obj) throws IOException {
		
	}

	@Override
	public void close() throws IOException {
		
	}

	@Override
	public void open() throws IOException {
		if (!isConnected) {
			connectDB();
		}
	}
	

}
