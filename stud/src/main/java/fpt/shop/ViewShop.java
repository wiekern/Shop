package fpt.shop;

import java.io.IOException;
import java.nio.file.Paths;

import fpt.com.Product;
import fpt.com.SerializableStrategy;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private ChoiceBox<String> strategyChoicebox; 
	private ObservableList<String> comboList = FXCollections.observableArrayList(
			"BinaryStrategy",
			"XMLStrategy",
			"XStreamStrategy",
			"JDBCStrategy",
			"OpenJPAWithConfig",
			"OpenJPAWithoutConfig");
	private Button btnLoad;
	private Button btnStore;
	private HBox strategyHBox;
	private final String serDir = Paths.get(".").toAbsolutePath().normalize().toString() + "/";
//	private static ViewShop instance = null;
//	
//	public static ViewShop getInstance() {
//		if (instance == null) {
//			instance = new ViewShop(ModelShop.getInstance());
//		}
//		return instance;
//	}
	
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
		warenShowList.setPrefWidth(300);
		warenShowList.setPrefHeight(400);
		
		productNameField  = new TextField();
		//productNameField.textProperty().bind(warenShowList.getSelectionModel().selectedItemProperty().);
		productPriceField = new TextField();
		//productPriceField.textProperty().bind(warenShowList.getSelectionModel().getSelectedItem().priceProperty());
		productCountField = new TextField();
		
		btnAddProduct = new Button("Add");
		btnDelProduct = new Button("Delete");
		btnAddProduct.setPrefWidth(70);
		btnDelProduct.setPrefWidth(70);
		
		hBox = new HBox(8);
		hBox.setPrefWidth(200);
		hBox.setPadding(new Insets(10, 0, 0, 0));
		vBox = new VBox();
		vBox.setPrefWidth(220);
		vBox.setPadding(new Insets(0, 10, 0, 0));
		
		strategyChoicebox = new ChoiceBox<String>();
		strategyChoicebox.setPrefWidth(300);
		btnLoad = new Button("Load");
		btnStore = new Button("Store");
		btnLoad.setPrefWidth(70);
		btnStore.setPrefWidth(70);
		strategyHBox = new HBox(8);
	}
	
	public void makeUp() {
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
		
		strategyHBox.getChildren().addAll(btnLoad, btnStore);
		strategyChoicebox.setTooltip(new Tooltip("Select Strategy"));
		strategyChoicebox.setItems(comboList);
		
		mainPane.add(strategyChoicebox, 0, 0);
		mainPane.add(strategyHBox, 1, 0);
		mainPane.add(warenShowList, 0, 1);
		mainPane.add(vBox, 1, 1);	
	}
	
	private void getObjFromDatabase(SerializableStrategy strategy, String filename) {
		Product tempProduct;
		
		try {
			strategy.open(filename);
			while((tempProduct = strategy.readObject()) != null) {
				modelShop.add(tempProduct);
			}
		} catch (IOException e) {
			System.out.println("Got object from database failed.");
		} finally {
			if (strategy != null) {
				try {
					strategy.close();
				} catch (IOException e) {
					System.out.println("closing strategy resource failed.");
				}
			}
		}
	}
	
	private void storeObjToDatabase(SerializableStrategy strategy, String fielname) {
		try {
			ObservableList<Product> currentProducts = modelShop.getDelegate();
			strategy.open(fielname);
			for(Product p: currentProducts) {
				System.out.println(p.getName());
				strategy.writeObject(p);
			}
		} catch (IOException e) {
			System.out.println("Stored object to database failed.");
			e.printStackTrace();
		} finally {
			if (strategy != null) {
				try {
					strategy.close();
				} catch (IOException e) {
					System.out.println("closing strategy resource failed.");
				}
			}
		}
	}
		
	public void setBtnActionLoadStrategy() {
		btnLoad.setOnAction((v) -> {
			SerializableStrategy strategy = null;
			String filename = "";
			
			//Default strategy.
			String s = "BinaryStrategy";
			if(strategyChoicebox.getValue() != null) {
				s = strategyChoicebox.getValue().toString();
			}
			
			switch (s) {
			case "BinaryStrategy":
				strategy = new BinaryStrategy();
				filename = serDir + "products.ser";
				break;
			case "XMLStrategy":
				strategy = new XMLStrategy();
				filename = serDir + "products.xml";
				break;
			case "XStreamStrategy":
				strategy = new XStreamStrategy();
				filename = serDir + "products.xml";
				break;
			case "JDBCStrategy":
				strategy = new JDBCStrategy();
				break;
			case "OpenJPAWithConfig":
				strategy = new OpenJPAStrategy(OpenJPAStrategy.GetFacMethod.WithConfig);
				break;
			case "OpenJPAWithoutConfig":
				strategy = new OpenJPAStrategy(OpenJPAStrategy.GetFacMethod.WithoutConfig);
				break;	
			default:
				break;
			}
			if (strategy != null)	getObjFromDatabase(strategy, filename);
			
			// refreshing the Listview.
			warenShowList.refresh();
		});
	}
	
	public void setBtnActionStoreStrategy() {
		btnStore.setOnAction((v) -> {
			SerializableStrategy strategy = null;
			String filename = "";
			
			String s = "BinaryStrategy";
			if(strategyChoicebox.getValue() != null) {
				s = strategyChoicebox.getValue().toString();
			} 
			
			switch (s) {
			case "BinaryStrategy":
				strategy = new BinaryStrategy();
				filename = serDir + "products.ser";
				break;
			case "XMLStrategy":
				strategy = new XMLStrategy();
				filename = serDir + "products.xml";
				break;
			case "XStreamStrategy":
				strategy = new XStreamStrategy();
				filename = serDir + "products.xml";
				break;
			case "JDBCStrategy":
				strategy = new JDBCStrategy();
				((JDBCStrategy)strategy).printMetaData();
				break;
			case "OpenJPAWithConfig":
				strategy = new OpenJPAStrategy(OpenJPAStrategy.GetFacMethod.WithConfig);
				break;
			case "OpenJPAWithoutConfig":
				strategy = new OpenJPAStrategy(OpenJPAStrategy.GetFacMethod.WithoutConfig);
				break;	
			default:
				break;
			}	
			if (strategy != null)	storeObjToDatabase(strategy, filename);
			
			// refreshing the Listview.
			warenShowList.refresh();
		});
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
				System.out.println("Please filling out all fields.");
				return ;
			} else {
				String productName = productNameField.getText();
				double productPrice = 0;
				int productCount = 0;
				try {
					productPrice = Double.parseDouble(productPriceField.getText());
					productCount = Integer.parseInt(productCountField.getText());}
				catch (NumberFormatException numE){
					System.out.println(numE.getMessage());
					System.out.println("Please intput a number with accepted format.");
					return ;
				}

				//ID generated by OPENJPA
//				Product product = new fpt.shop.Product(productName, productPrice, productCount);
//				modelShop.add(product);
				
				// Instead of IDGenerator we will generate ID by OpenJPA.
				try{
					IDGenerator generator = new IDGenerator();
					long productId = generator.generateId();
					Product product = new fpt.shop.Product(productName, productId, productPrice, productCount);
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
				if (warenShowList.getItems().size() == 0) {
					return ;
				};
				if (warenShowList.getSelectionModel().getSelectedItem() != null) {
					productNameField.setText(warenShowList.getSelectionModel().getSelectedItem().getName());
					productPriceField.setText(Double.toString(warenShowList.getSelectionModel().getSelectedItem().getPrice()));
					productCountField.setText(Integer.toString(warenShowList.getSelectionModel().getSelectedItem().getQuantity()));
				}	
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
							setText("Name: " + item.getName() + " | ID: " + item.getId());
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
