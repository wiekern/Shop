package fpt.shop;

import java.util.Optional;
import java.util.regex.Pattern;

import fpt.com.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ViewCustomer {
	private ModelShop modelShop;
	private GridPane mainPane;
	private ListView<Product> productListView;
	private Button btnBuy;
	private Button btnAddWare;
	private TableView<Product> productTableView;
	private Dialog<User> loginDialog;
	private Label labelName;
	private Label labelPassword;
	private TextField username;
	private PasswordField password;
	private GridPane loginGrid;
	private ButtonType btnLogin;
	private Label clock;
	private TCPClient tcpClient;
	
	public ViewCustomer() {
		mainPane = new GridPane();
		clock = new Label("zeit");
		productTableView = new TableView<>();
		productTableView.setEditable(true);
		productListView = new ListView<>();
		productListView.setPrefWidth(350);
		TableColumn<Product, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		TableColumn<Product, Integer> buyCountCol = new TableColumn<>("Count");
		buyCountCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
		buyCountCol.setCellFactory(new Callback<TableColumn<Product, Integer>, TableCell<Product, Integer>>() {
            public TableCell<Product, Integer> call(TableColumn<Product, Integer> p) {
                TableCell<Product, Integer> cell = new TableCell<Product, Integer>() {
                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : item.toString());
                    }
                };
				return cell;
            }
		});
//		buyCountCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product,Integer>>() {
//			@Override
//			public void handle(CellEditEvent<Product, Integer> event) {
//				((Product) event.getTableView().getItems().get(event.getTablePosition().getRow())).setQuantity(event.getNewValue());
//			}
//		});
		
		loginGrid = new GridPane();
		loginDialog = new Dialog<>();
		loginDialog.setHeaderText("Login");
		labelName = new Label("Name: ");
		labelPassword = new Label("Passwrod: ");
		username = new TextField();
		password = new PasswordField();
		btnLogin = new ButtonType("Login", ButtonData.OK_DONE);
		loginGrid.add(labelName, 1, 1);
		loginGrid.add(username, 2, 1);
		loginGrid.add(labelPassword, 1, 2);
		loginGrid.add(password, 2, 2);
		loginDialog.getDialogPane().setContent(loginGrid);
		loginDialog.getDialogPane().getButtonTypes().add(btnLogin);
		
		loginDialog.setResultConverter(new Callback<ButtonType, User>() {

			@Override
			public User call(ButtonType param) {
				if (param == btnLogin) {
					User u = new User(username.getText(), password.getText());
					if (u.getUsername().equals("admin") && u.getPasswrod().equals("admin")) {
						return u;
					} else {
						return null;
					}
				}
				return null;
			}
		});
		
		productTableView.getColumns().addAll(nameCol, priceCol, buyCountCol);
		
		mainPane.add(productTableView, 1, 0);
		mainPane.add(productListView, 0, 0);
		btnBuy = new Button("Buy");
		btnAddWare = new Button("Add");
		mainPane.add(btnAddWare, 0, 1);
		mainPane.add(clock, 1, 1);
		mainPane.add(btnBuy, 1, 1);
		GridPane.setHalignment(btnBuy, HPos.RIGHT);
		GridPane.setHalignment(btnAddWare, HPos.LEFT);
	}
	
	public ViewCustomer(ModelShop model) {
		this();
		this.modelShop = model;
	}
	
	public Label getClock() {
		return this.clock;
	}
	
	public GridPane getMainPane() {
		return mainPane;
	}
	
	public TableView<Product> getProductTableView() {
		return this.productTableView;
	}
	
	public ListView<Product> getProductListView() {
		return this.productListView;
	}
	
	public void setBtnActionBuy() {
		btnBuy.setOnAction((v) -> {
			Order order = new Order();
			ObservableList<Product> ol = FXCollections.observableArrayList(productTableView.getItems());
			for (Product p: ol) {
				order.add(p);
			}
			tcpClient.sendOrder(order);
		});
	}
	
	public void setBtnActionAddWare() {
		btnAddWare.setOnAction((v) -> {
			// Test user and password.
			Optional<User> loginResult = loginDialog.showAndWait();
			if (!loginResult.isPresent()) {
				System.out.println("User or Password incorrect.");
				return ;
			} else {			
				Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
				if (selectedProduct != null) {
					Product product = new fpt.shop.Product(selectedProduct.getName(), selectedProduct.getId(), selectedProduct.getPrice(), 1);
					modelShop.getOrderList().add(product);
					//productTableView.getItems().add(product);
				}
			}
		});
	}
	
	public void setProductListCellFactory() {
		Callback<ListView<Product>, ListCell<Product>> cb = new Callback<ListView<Product>, ListCell<Product>>() {

			@Override
			public ListCell<Product> call(ListView<Product> param) {
				ListCell<Product> cell = new ListCell<Product>() {
					@Override
					protected void updateItem(Product item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText("Name: " + item.getName() + " |Price: " + item.getPrice() + "EUR |Stock: " + item.getQuantity());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
		};
		productListView.setCellFactory(cb);
	}
	
//	public void setProductTableViewCellFactory() {
//		Callback<ListView<Order>, ListCell<Order>> cb = new Callback<TableView<Order>, ListCell<Order>>() {
//
//			@Override
//			public ListCell<Order> call(ListView<Order> param) {
//				ListCell<Order> cell = new ListCell<Order>() {
//					@Override
//					protected void updateItem(Order item, boolean empty) {
//						super.updateItem(item, empty);
//
//					}
//				};
//				return cell;
//			}
//		};
//		productListView.setCellFactory(cb);
//	}

	public TCPClient getTcpClient() {
		return tcpClient;
	}

	public void setTcpClient(TCPClient tcpClient) {
		this.tcpClient = tcpClient;
	}
}

class IntegerEditCell extends TableCell<Product, Integer> {
	private final TextField textField = new TextField();
	private final Pattern  intPattern = Pattern.compile("\\d+");
	
	public IntegerEditCell() {
		textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused) {
				
			}
		});
		textField.setOnAction(event -> {
			
		});
	}
	
	private void processEdit() {
		String text = textField.getText();
		if (intPattern.matcher(text).matches()) {
			
		}
	}
}
