package fpt.shop;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class TCPOrderServer {
	private int portNumber = 6666;
	private ServerSocket serverSock;
	private Order sumOrder, lastOrder;
	private HashMap<Long, fpt.com.Product> hashmapForSumOrder = new HashMap<>();
	private TCPOrderListener tcpOrderListener;
	
	public TCPOrderServer() {
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
				} catch (IOException e) {
					e.printStackTrace();
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
		setLastOrder(order);
		
		long id;
		int quantityOfNew, quantityOfOld;
		Product oldProduct;
		// speichere eingehende Bestellung in HashMap
		for (fpt.com.Product p: order) {
			Product tmpProduct = new Product(p.getName(), p.getId(), p.getPrice(), p.getQuantity());;
			id = tmpProduct.getId();
			quantityOfNew = tmpProduct.getQuantity();
			if (hashmapForSumOrder.get(id) == null) {
				hashmapForSumOrder.put(id, tmpProduct);
			} else {
				oldProduct = (Product) hashmapForSumOrder.get(id);
				quantityOfOld = oldProduct.getQuantity();
				oldProduct.setQuantity(quantityOfNew + quantityOfOld);
				
				hashmapForSumOrder.put(id, oldProduct);
			}
		}
		
		Set<Long> keyset = hashmapForSumOrder.keySet();
		this.sumOrder = new Order();
		for (Long key: keyset) {
			this.sumOrder.add(hashmapForSumOrder.get(key));
		}
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
