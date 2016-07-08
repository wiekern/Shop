package fpt.shop;

import java.net.Socket;

public interface TCPOrderListener {
	public void updateWarehouse(Socket sock);
}
