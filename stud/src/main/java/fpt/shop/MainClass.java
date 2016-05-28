package fpt.shop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainClass extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		ModelShop modelShop = new ModelShop();
//		ViewShop viewShop = new ViewShop(modelShop);
//		viewShop.makeUp();
//		
//		Scene rootScene = new Scene(viewShop.getMainPane(), 400, 400);
//		
//		ControllerShop cShop = new ControllerShop();
//		cShop.link(modelShop, viewShop); 
//		
//		Stage customerStage = new Stage();
//		ViewCustomer viewCustomer = new ViewCustomer();
//		Scene customerScene = new Scene(viewCustomer.getMainPane(), 500, 400);
//		customerStage.setScene(customerScene);
//		customerStage.show();
//		
//		primaryStage.setScene(rootScene);
//		primaryStage.show();
//		
//		//Database
//		JDBCConnector connector = new JDBCConnector();
//		if (connector.connectDB()) {
//			System.out.println("connected successfully.");
//			connector.showAlltables();
//		}
//		
//		connector.closeConnection();
		
		Product product = new Product("1", 1, 1);
		JPAEntityManagerFactory.JPAPersistenceProduct(product);
		
		JDBCConnector connector = new JDBCConnector();
		if (connector.connectDB()) {
			System.out.println("connected successfully.");
			Product p = connector.read(1);
			System.out.println(p.getName());
		}
		
		
	}

}
