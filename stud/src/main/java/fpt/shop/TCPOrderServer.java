package fpt.shop;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.ObservableList;

public class TCPOrderServer {
	private int portNumber = 6666;
	private ServerSocket serverSock;
	private Order sumOrder, lastOrder;
	private Map<Long, fpt.shop.ProductList> hashmapForSumOrder = new HashMap<>();
	private TCPOrderListener tcpOrderListener;
	private static TCPOrderServer instance = null;
	
	public TCPOrderServer() {
		
	}
	
	public static TCPOrderServer getInstance() {
		if (instance == null) {
			instance = new TCPOrderServer();
		}
		return instance;
	}
	
	public void TCPServer() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					serverSock = new ServerSocket(portNumber);
					
					while (true) {
						Socket connectionSock = serverSock.accept();
						getTCPOrderListener().updateWarehouse(connectionSock);
					}
				} catch (BindException e) {
					System.out.println("TCP Order Server bind failed, address already in use. Please close the running TCP Order Server.");
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
					return ;
				} 
			}
		}).start();
	}

	public TCPOrderListener getTCPOrderListener() {
		return this.tcpOrderListener;
	}

	public void setTCPListener(TCPOrderListener tcpOrderListener) {
		this.tcpOrderListener = tcpOrderListener;
	}
	
	public void acceptOrder(Order order) {
		// speichere eingegangene Bestellung.
		synchronized (this) {
			setLastOrder(order);
			
			long id;
			int quantityOfNew, quantityOfOld;
			Product oldProduct;
			// speichere eingehende Bestellung in HashMap
			for (fpt.com.Product p: order) {
				ProductList tmpProductList;
				Product tmpProduct = new Product(p.getName(), p.getId(), p.getPrice(), p.getQuantity());;
				id = tmpProduct.getId();
				quantityOfNew = tmpProduct.getQuantity();
				if ((tmpProductList = hashmapForSumOrder.get(id)) == null) {
					tmpProductList = new ProductList();
					tmpProductList.add(tmpProduct);
					hashmapForSumOrder.put(id, tmpProductList);
				} else {
					oldProduct = (Product) tmpProductList.get(0);
					// id and name are same.
					if (tmpProduct.getName().equals(oldProduct.getName())) {
						quantityOfOld = oldProduct.getQuantity();
						oldProduct.setQuantity(quantityOfNew + quantityOfOld);
						hashmapForSumOrder.put(id, tmpProductList);
					} else {
						tmpProductList.add(tmpProduct);
						hashmapForSumOrder.put(id, tmpProductList);
					}
				}
			}
			
			this.sumOrder = new Order();
			for(Map.Entry<Long, fpt.shop.ProductList> entry : hashmapForSumOrder.entrySet()) {
				//long key = entry.getKey();
				for (fpt.com.Product value : entry.getValue()) {
					this.sumOrder.add(value);
				}
			}
			printWarehouseStatus(TCPOrderServer.getInstance());
		}
	}
	
	public void printWarehouseStatus(TCPOrderServer server) {
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

	public Order getSumOrder() {
		return sumOrder;
	}
	
	public void setSumOrder(Order order) {
		this.sumOrder = order;
	}

	public Order getLastOrder() {
		return lastOrder;
	}

	public void setLastOrder(Order lastOrder) {
		this.lastOrder = new Order();
		for (fpt.com.Product p: lastOrder) {	
			Product productToLastorder = new Product(p.getName(), p.getId(), p.getPrice(), p.getQuantity());
			this.lastOrder.add(productToLastorder);	
		}
	}
}
