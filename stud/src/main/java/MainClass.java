import bai.ModelShop;
import bai.ViewCustomer;
import bai.ViewShop;
import bai.ControllerShop;
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
		ModelShop modelShop = new ModelShop();
		ViewShop viewShop = new ViewShop(modelShop);
		viewShop.makeUp();
		
		Scene rootScene = new Scene(viewShop.getMainPane(), 400, 400);
		
		ControllerShop cShop = new ControllerShop();
		cShop.link(modelShop, viewShop);
		//MProductList pList = new MProductList();
//		for (int i = 0; i < 10; i++) {
//			MProduct product = new MProduct("P"+i, i, i*10+2, 10);
//			modelShop.add(product);
//			System.out.println(product.getId());
//		}   
		
		Stage customerStage = new Stage();
		ViewCustomer viewCustomer = new ViewCustomer();
		Scene customerScene = new Scene(viewCustomer.getMainPane(), 500, 400);
		customerStage.setScene(customerScene);
		customerStage.show();
		
		primaryStage.setScene(rootScene);
		primaryStage.show();
		
	}

}
