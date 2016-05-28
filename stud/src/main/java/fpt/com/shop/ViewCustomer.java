package fpt.com.shop;

import fpt.com.Product;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

public class ViewCustomer {
	
	private GridPane mainPane;
	private ListView<Product> productListView;
	private Button btnBuy;
	private TableView<Product> productTableView;
	
	public ViewCustomer() {
		mainPane = new GridPane();
		productTableView = new TableView<>();
		productListView = new ListView<>();
		TableColumn<Product, String> nameCol = new TableColumn<>("Name");
		TableColumn<Product, String> priceCol = new TableColumn<>("Price");
		TableColumn<Product, String> buyCountCol = new TableColumn<>("ButCount");
		
		productTableView.getColumns().addAll(nameCol, priceCol, buyCountCol);
		
		mainPane.add(productTableView, 1, 0);
		mainPane.add(productListView, 0, 0);
		btnBuy = new Button("Buy");
		mainPane.add(btnBuy, 1, 1);
		GridPane.setHalignment(btnBuy, HPos.RIGHT);
	}
	
	public GridPane getMainPane() {
		return mainPane;
	}
}
