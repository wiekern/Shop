package fpt.shop;

import java.io.IOException;
import java.io.ObjectInputStream;

import fpt.chat.ChatMain;

//Platform.runLater 
//UDP Server
//TCP Server Server-Port 6666, nehme Order auf.
public class Warehouse {
	
	public static void main(String[] args) {
		ChatMain.main(args);
		System.out.println("Warehouse");
		UDPTimeServer timeServer = new UDPTimeServer();
		timeServer.UDPServer();
		
		TCPOrderServer orderServer = TCPOrderServer.getInstance();
		orderServer.TCPServer();
		//updateWarehouse(), get Order object from Client.
		orderServer.setTCPListener(event -> {
			new Thread(() -> {
				ObjectInputStream inputFromClient;
				try {
					inputFromClient = new ObjectInputStream(event.getInputStream());
					while(true) {
						Order order = (Order) inputFromClient.readObject();
						orderServer.acceptOrder(order);
					}
				} catch (IOException e) {
					System.out.println("TCP Server reads data from client failed. Maybe Client have closed the connection.");
					return ;
				} catch (ClassNotFoundException e) {
					System.out.println("Class Order not found.");
					return ;
				}
			}).start();
		});
	}
}
