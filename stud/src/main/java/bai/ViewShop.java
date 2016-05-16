package bai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import fpt.com.Product;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
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
	private ComboBox<String> strategyComboBox; 
	private Button btnLoad;
	private Button btnStore;
	private HBox strategyHBox;
	private final String serDir = "/Users/yafei/Desktop/SS_2016/Fortgeschnittene Programmiertechniken/E1/stud/";
	
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
		strategyComboBox = new ComboBox<>();
		btnLoad = new Button("Load");
		btnStore = new Button("Store");
		btnLoad.setPrefWidth(70);
		btnStore.setPrefWidth(70);
		strategyHBox = new HBox(8);
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
		
		strategyHBox.getChildren().addAll(btnLoad, btnStore);
		strategyComboBox.setPrefWidth(200);
		strategyComboBox.setPromptText("slect Strategy");
		
		hBox.setPrefWidth(200);
		mainPane.add(strategyComboBox, 0, 0);
		mainPane.add(strategyHBox, 1, 0);
		mainPane.add(warenShowList, 0, 1);
		mainPane.add(vBox, 1, 1);	
		
		strategyComboBox.getItems().addAll(
				"BinaryStrategy",
				"XMLStrategy",
				"XStreamStrategy");
	}
	
	public void setBtnActionLoadStrategy() {
		btnLoad.setOnAction((v) -> {
			//System.out.println(strategyComboBox.getValue());
			String s = "BinaryStrategy";
			if(strategyComboBox.getValue() != null) {
				s = strategyComboBox.getValue();
			}
			switch (s) {
			case "BinaryStrategy":
				Product binaryProduct = null;
				BinaryStrategy bStrategy = new BinaryStrategy();
				try {
					bStrategy.open(serDir + "products.ser");
					binaryProduct = bStrategy.readObject();
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (bStrategy != null) {
						try {
							bStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if (binaryProduct != null) modelShop.add(binaryProduct);
				
				try {
					Files.delete(Paths.get("", serDir + "products.ser"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "XMLStrategy":
				Product xmlProduct = null;
				XMLStrategy xmlStrategy = new XMLStrategy();
				try {
					xmlStrategy.open(serDir + "products.xml");
					xmlProduct = xmlStrategy.readObject();
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (xmlStrategy != null) {
						try {
							xmlStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				if (xmlProduct != null)  modelShop.add(xmlProduct);
				
				try {
					Files.delete(Paths.get("", serDir + "products.xml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case "XStreamStrategy":
				Product xstreamProduct = null;
				XStreamStrategy xstreamStrategy = new XStreamStrategy();
				try {
					xstreamStrategy.open(serDir + "products.xml");
					xmlProduct = xstreamStrategy.readObject();
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (xstreamStrategy != null) {
						try {
							xstreamStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				if (xstreamProduct != null)  modelShop.add(xstreamProduct);
				
				try {
					Files.delete(Paths.get("", serDir + "products.xml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		});
	}
	
	public void setBtnActionStoreStrategy() {
		btnStore.setOnAction((v) -> {
			ObservableList<Product> currentProducts = warenShowList.getItems();
			if (currentProducts == null) {
				return ;
			}
			String s = "BinaryStrategy";
			if(strategyComboBox.getValue() != null) {
				s = strategyComboBox.getValue();
			}
			switch (s) {
			case "BinaryStrategy":
				BinaryStrategy bStrategy = new BinaryStrategy();
				try {
					bStrategy.open(serDir + "products.ser");
					for(Product p: currentProducts) {
						bStrategy.writeObject(p);
					}
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (bStrategy != null) {
						try {
							bStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case "XMLStrategy":
				//System.out.println("XML");
				XMLStrategy xmlStrategy = new XMLStrategy();
				try {
					xmlStrategy.open(serDir + "products.xml");
					for(Product p: currentProducts) {
						xmlStrategy.writeObject(p);
					}
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (xmlStrategy != null) {
						try {
							xmlStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}		
				break;
			case "XStreamStrategy":
				XStreamStrategy xstreamStrategy = new XStreamStrategy();
				try {
					xstreamStrategy.open(serDir + "products.xml");
					for(Product p: currentProducts) {
						xstreamStrategy.writeObject(p);
					}
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (xstreamStrategy != null) {
						try {
							xstreamStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}		
				break;
			default:
				break;
			}	
		});
	}
	
	public void setBtnActionStoreStrategy1() {
		btnStore.setOnAction((v) -> {
			Product currentProduct = warenShowList.getSelectionModel().getSelectedItem();
			if (currentProduct == null) {
				return ;
			}
			String s = "BinaryStrategy";
			if(strategyComboBox.getValue() != null) {
				s = strategyComboBox.getValue();
			}
			switch (s) {
			case "BinaryStrategy":
				BinaryStrategy bStrategy = new BinaryStrategy();
				try {
					bStrategy.open(serDir + "products.ser");
					bStrategy.writeObject(currentProduct);
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (bStrategy != null) {
						try {
							bStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case "XMLStrategy":
				XMLStrategy xmlStrategy = new XMLStrategy();
				try {
					xmlStrategy.open(serDir + "products.xml");
					xmlStrategy.writeObject(currentProduct);
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (xmlStrategy != null) {
						try {
							xmlStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}		
				break;
			case "XStreamStrategy":
				XStreamStrategy xstreamStrategy = new XStreamStrategy();
				try {
					xstreamStrategy.open(serDir + "products.xml");
					xstreamStrategy.writeObject(currentProduct);
				} catch (IOException ioE) {
					ioE.printStackTrace();
				} finally {
					if (xstreamStrategy != null) {
						try {
							xstreamStrategy.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}		
				break;
			default:
				break;
			}	
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
