package fpt.shop;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	private Socket sock = null;
	private String serverHost = "localhost";
	private int serverPort = 6666;
	private ObjectOutputStream ois = null;
	public ObjectOutputStream getOis() {
		return ois;
	}

	private boolean wasInit = false;
	
	TCPClient() {
		tcpClientInit();
	}

	public void tcpClientInit() {
		try {
			sock = new Socket(serverHost, serverPort);
			ois = new ObjectOutputStream(sock.getOutputStream());

		} catch (ConnectException e) {
			System.out.println("Connection refused. Please check status of the server.");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		wasInit = true;
	}
	
	public void sendOrder(Order order) {
		if (!wasInit) {
			tcpClientInit();
		}
		
		if (ois != null) {
			try {
				ois.writeObject(order);
				ois.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeConnection() {
		if (sock != null) {
			try {
				sock.close();
				wasInit = false;
			} catch (IOException e) {
				System.out.println("Closing TCP connection in Client failed.");
				e.printStackTrace();
			}
		}
	}
}
