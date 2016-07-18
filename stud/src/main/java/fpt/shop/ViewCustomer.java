package fpt.shop;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

import fpt.chat.ChatClient;
import fpt.chat.ChatService;
import fpt.com.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Node;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	private Button btnChat;
	private TextField clientNameText;
	private TextField buyQuantityText;
	private HBox buttons;
	
	private Dialog<TextArea> chatDialog;
	private GridPane chatGrid;
	private TextArea chatArea;
	private TextField msgInput;
	private Button btnSendMsg;
	private ButtonType btnCloseChat;
	private HBox sendMsgHBox;
	
	private TCPClient tcpClient;
	
	private ChatService chatServiceRemote;
	private ChatClient chatClient;

	public ViewCustomer() {
		mainPane = new GridPane();
		clock = new Label();
		
		productTableViewInit();
		loginDialogInit();
		
		mainPane.add(productTableView, 1, 0);
		mainPane.add(productListView, 0, 0);
		
		buttons = new HBox(5);
		btnBuy = new Button("Buy");
		btnAddWare = new Button("Add");
		btnChat = new Button("Login");
		clientNameText = new TextField();
		clientNameText.setPromptText("login name");
		clientNameText.setPrefWidth(100);
		buyQuantityText = new TextField();
		buyQuantityText.setPromptText("buy number");
		buyQuantityText.setPrefWidth(100);
		buttons.getChildren().add(buyQuantityText);
		buttons.getChildren().add(btnAddWare);
		buttons.getChildren().add(clientNameText);
		buttons.getChildren().add(btnChat);
		
		mainPane.add(buttons, 0, 1);
		mainPane.add(clock, 1, 1);
		mainPane.add(btnBuy, 1, 1);
		GridPane.setHalignment(btnBuy, HPos.RIGHT);
		GridPane.setHalignment(btnAddWare, HPos.LEFT);
		
		chatDialogInit();
	}
	
	public ViewCustomer(ModelShop model) {
		this();
		this.modelShop = model;
	}
	
	
	protected void chatDialogInit() {
		chatDialog = new Dialog<>();
		msgInput = new TextField();
		msgInput.setPrefWidth(250);
		chatArea = new TextArea();
		chatArea.setPrefWidth(250);
		chatArea.setEditable(false);
		chatArea.setFocusTraversable(false);
		chatArea.setWrapText(true);
		btnSendMsg = new Button("Send");
		btnCloseChat = new ButtonType("Exit", ButtonData.CANCEL_CLOSE);
		chatDialog.getDialogPane().getButtonTypes().add(btnCloseChat);
		sendMsgHBox = new HBox(5);
		sendMsgHBox.getChildren().add(msgInput);
		sendMsgHBox.getChildren().add(btnSendMsg);
		chatGrid = new GridPane();
		chatGrid.add(chatArea, 0, 0);
		chatGrid.add(sendMsgHBox, 0, 1);
		chatDialog.getDialogPane().setContent(chatGrid);
		chatDialog.setOnCloseRequest((e) -> {
			try {
				chatServiceRemote.logout(chatClient.getName());
			} catch (Exception e1) {
				System.out.println("User: " + chatClient.getName() + "logout failed.");
				e1.printStackTrace();
			}
			chatDialog.close();
		});
		
		// support Enter to send message.
		msgInput.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
			if (ev.getCode() == KeyCode.ENTER) {
				btnSendMsg.fire();
				ev.consume();
			}
		});
	}
	
	protected void loginDialogInit() {
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
		loginDialog.getDialogPane().getButtonTypes().add(btnLogin);
		Node btnLoginTmp = loginDialog.getDialogPane().lookupButton(btnLogin);
		btnLoginTmp.setDisable(true);
		
		username.textProperty().addListener((ob, oldValue, newValue) -> {
			btnLoginTmp.setDisable(newValue.trim().isEmpty());
		});
		
		loginDialog.getDialogPane().setContent(loginGrid);
		
		// Default focus on name
		Platform.runLater(() -> username.requestFocus());
		
		loginDialog.setResultConverter(new Callback<ButtonType, User>() {

			@Override
			public User call(ButtonType param) {
				if (param == btnLogin) {
					User u = new User(username.getText(), password.getText());
					if (u.getUsername().equals("admin") && u.getPasswrod().equals("admin")) {
						System.out.println("name and password right.");
						return u;
					} else {
						return null;
					}
				}
				return null;
			}
		});
	}
	
	protected void productTableViewInit() {
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
		buyCountCol.setEditable(true);
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

		productTableView.getColumns().addAll(nameCol, priceCol, buyCountCol);
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
	
	public void setBtnActionSendmsg() {
		btnSendMsg.setOnAction((v) -> {
			try {
				chatServiceRemote.send(chatClient.getClientName() + ": " + msgInput.getText());
//				chatArea.appendText(chatClient.getMsgFromServer() + "\n");
//				chatArea.setScrollTop(Double.MAX_VALUE);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Client send message to server failed.");
			}
		});
	}
	
	public void setBtnActionChat() {
		btnChat.setOnAction((v) -> {
			try {
				if (clientNameText.getText().trim().isEmpty()) {
					System.out.println("Please input a valid username to login.");
	        		return ;
				}
	        	String name = clientNameText.getText();
	        	System.out.println("name :" +name);

	        	try {
					setChatServiceRemote((ChatService)Naming.lookup("//localhost:1099/ChatServer"));
				} catch (MalformedURLException e) {
					System.out.println("To bind incorrect Service URL: //localhost:1099/ChatServer");
				} catch (NotBoundException e) {
					System.out.println("No registed service for //localhost:1099/ChatServer");
				}
	        	List<String> userList = chatServiceRemote.getUserList();
	        	for(String s: userList) {
	        		if (s.equals(name)) {
	        			System.out.println("User existed, please choose a different name.");
	        			return ;
	        		}
	        	}
	        	
	        	setChatClient(new ChatClient(name));
	        	chatClient.setChatArea(this.chatArea);
	        	try {
					Naming.rebind("//localhost:1099/" + name, getChatClient());
				} catch (MalformedURLException e) {
					System.out.println("To bind incorrect Service URL: " + "//localhost:1099/" + name);
				}
				chatServiceRemote.login(clientNameText.getText());
			} catch (RemoteException e) {
				System.out.println("fail to invoke a remote api.");
			} 
			chatDialog.showAndWait();
		});
	}
	
	public void setBtnActionBuy() {
		btnBuy.setOnAction((v) -> {
			ObservableList<Product> ol = FXCollections.observableArrayList(productTableView.getItems());
			if (ol.isEmpty()) {
				System.out.println("No products to buy.");
				return ;
			} else {
				Order order = new Order();
				for (Product p: ol) {
					order.add(p);
					modelShop.getOrderList().remove(p);
				}
				tcpClient.sendOrder(order);
			}
		});
	}
	
	public void setBtnActionAddWare() {
		btnAddWare.setOnAction((v) -> {
			Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
			if (selectedProduct == null) {
				System.out.println("No selected product.");
				return ;
			}
			
			// Test user and password.
			Optional<User> loginResult = loginDialog.showAndWait();
			if (!loginResult.isPresent()) {
				System.out.println("User or Password incorrect.");
			} else {
				ObservableList<Product> obl = modelShop.getOrderList();
				int amount = 0;
				int stockQuantity = selectedProduct.getQuantity();;
				try{
					amount = Integer.parseInt(buyQuantityText.getText());
				} catch (NumberFormatException e) {
					System.out.println("Please input a valid number.");
				}
				
				if ((amount > 0) && (amount <= stockQuantity)) {
					for (Product p: obl) {
						if (p.getName().equals(selectedProduct.getName())) {
							if (p.getQuantity() + amount > stockQuantity) {
								System.out.println("You have already bought some. Stock is not enough.");
							} else {
								p.setQuantity(p.getQuantity() + amount);
							}
							username.clear();
							password.clear();
							loginDialog.setResult(null);
							return ;
						}
					}
					
					Product product = new fpt.shop.Product(selectedProduct.getName(), selectedProduct.getId(), selectedProduct.getPrice(), amount);
					modelShop.getOrderList().add(product);
				} else {
					System.out.println("Stock is not enough.");
				}
			} 
			
			username.clear();
			password.clear();
			loginDialog.setResult(null);
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
	
	public TCPClient getTcpClient() {
		return tcpClient;
	}

	public void setTcpClient(TCPClient tcpClient) {
		this.tcpClient = tcpClient;
	}

	public ChatService getChatServiceRemote() {
		return chatServiceRemote;
	}

	public void setChatServiceRemote(ChatService service) {
		this.chatServiceRemote = service;
	}
	
	public ChatClient getChatClient() {
		return chatClient;
	}

	public void setChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
}

