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
		
		TCPOrderServer orderServer = new TCPOrderServer();
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
						printWarehouseStatus(orderServer);
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
	
	public static void printWarehouseStatus(TCPOrderServer server) {
		System.out.println("Order eingegangen:");
		for (fpt.com.Product p: server.getLastOrder()) {
			System.out.println(p.getName() + "\t" + p.getQuantity() + "\t" + p.getPrice() + " EUR");	
		}
		
		System.out.println("\nOrders gesamt");
		System.out.println("=================================");
		for (fpt.com.Product p: server.getSumOrder()) {
			System.out.println(p.getName() + "\t" + p.getQuantity() + "\t" + p.getPrice()*p.getQuantity() + " EUR");	
		}
		System.out.println("=================================");
		System.out.println("Gesamtanzahl: " + server.getSumOrder().getQuantity());
		System.out.println("Gesamtwert: " + server.getSumOrder().getSum() + " EUR\n");

	}
	
}
