package bai;

import fpt.com.Product;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class ViewShop {
	private ModelShop modelShop;
	private GridPane mainPane;
	private ListView<Product> warenShowList;
	private TextField productNameField;
	private TextField productPriceField;
	private TextField productCountField;
	private VBox vBox;
	private HBox hBox;
	private Button btnAddProduct;
	private Button btnDelProduct;
	
	public ViewShop(ModelShop model) {
		initialize();
		this.modelShop = model;
	}
	
	public GridPane getMainPane() {
		return this.mainPane;
	}
	
	public ListView<Product> getWarenShowList() {
		return this.warenShowList;
	}
	
	public TextField getProductNameField() {
		return this.productNameField;
	}
	
	public TextField getProductPriceField() {
		return this.productPriceField;
	}
	
	public TextField getProductCountField() {
		return this.productCountField;
	}
	
	public Button getBtnAddProduct() {
		return this.btnAddProduct;
	}
	
	public Button getBtnDelProduct() {
		return this.btnDelProduct;
	}
	
	private void initialize() {
		mainPane = new GridPane();
		mainPane.setHgap(5);
		warenShowList = new ListView<>();
		productNameField  = new TextField();
		productPriceField = new TextField();
		productCountField = new TextField();
		btnAddProduct = new Button("Add");
		btnDelProduct = new Button("Delete");
		btnAddProduct.setPrefWidth(70);
		btnDelProduct.setPrefWidth(70);
		hBox = new HBox(8);
		hBox.setPadding(new Insets(10, 0, 0, 0));
		vBox = new VBox();
		vBox.setPrefWidth(200);
	}
	
	public void makeUp() {
		warenShowList.setPrefWidth(200);
		warenShowList.setPrefHeight(400);
	
		Label nameLabel = new Label("Name:");
		Label priceLabel = new Label("Price:");
		Label countLabel = new Label("Count:");
	
		vBox.getChildren().add(nameLabel);
		vBox.getChildren().add(productNameField);
		vBox.getChildren().add(priceLabel);
		vBox.getChildren().add(productPriceField);
		vBox.getChildren().add(countLabel);
		vBox.getChildren().add(productCountField);
		vBox.getChildren().add(hBox);
		hBox.getChildren().add(btnAddProduct);
		hBox.getChildren().add(btnDelProduct);
		
		hBox.setPrefWidth(200);
		mainPane.add(warenShowList, 0, 0);
		mainPane.add(vBox, 1, 0);	
	}
	
	public void setBtnActionDeleteProduct() {
		btnDelProduct.setOnAction((e) -> {
			warenShowList.getItems().remove(warenShowList.getSelectionModel().getSelectedItem());
			productNameField.setText("");
			productPriceField.setText("");
			productCountField.setText("");
			
			for(Product p: modelShop.getDelegate()) {
				System.out.println(p.getName());
			}
		});
	}
	
	public void setBtnActionAddProduct() {
		btnAddProduct.setOnAction((e) -> {
			if (productNameField.getText().trim().isEmpty()
				|| productPriceField.getText().trim().isEmpty()
				|| productCountField.getText().trim().isEmpty()) {
				return ;
			} else {
				String productName = productNameField.getText();
				double productPrice = Double.parseDouble(productPriceField.getText());
				int productCount = Integer.parseInt(productCountField.getText());
				try{
					IDGenerator generator = new IDGenerator();
					long productId = generator.generateId();
					Product product = new bai.Product(productName, productId, productPrice, productCount);
					modelShop.add(product);
				} catch (IDOverflowException idException){
					idException.printStackTrace();
				}
			}
		});
	}
	
	public void clickWaren() {
		warenShowList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (warenShowList.getItems().size() == 0) {
					return ;
				}
				//productNameField.setText(warenShowList.getSelectionModel().getSelectedItem().nameProperty());
				productNameField.setText(warenShowList.getSelectionModel().getSelectedItem().getName());
				productPriceField.setText(Double.toString(warenShowList.getSelectionModel().getSelectedItem().getPrice()));
				productCountField.setText(Integer.toString(warenShowList.getSelectionModel().getSelectedItem().getQuantity()));
			}
		});
	}
	
	public void setWarenListCellFactory() {
		Callback<ListView<Product>, ListCell<Product>> cb = new Callback<ListView<Product>, ListCell<Product>>() {

			@Override
			public ListCell<Product> call(ListView<Product> param) {
				ListCell<Product> cell = new ListCell<Product>() {
					@Override
					protected void updateItem(Product item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getName() + " " + item.getId());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
		};
		warenShowList.setCellFactory(cb);
	}
}
