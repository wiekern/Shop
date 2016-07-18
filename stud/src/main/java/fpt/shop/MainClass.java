package fpt.shop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainClass extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ModelShop modelShop = ModelShop.getInstance();
		ViewShop viewShop = new ViewShop(modelShop);
		viewShop.makeUp();
		ControllerShop cShop = new ControllerShop();
		cShop.link(modelShop, viewShop); 
		Scene rootScene = new Scene(viewShop.getMainPane(), 400, 400);

		Stage customerStage = new Stage();
		ViewCustomer viewCustomer = new ViewCustomer(modelShop);
		ControllerCustomer cCustomer = new ControllerCustomer();
		cCustomer.link(viewCustomer, modelShop);
		Scene customerScene = new Scene(viewCustomer.getMainPane(), 600, 400);
		
		customerStage.setScene(customerScene);
		customerStage.show();	
		primaryStage.setScene(rootScene);
		primaryStage.show();
	}

}
