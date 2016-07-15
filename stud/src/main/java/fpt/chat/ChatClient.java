package fpt.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements ClientService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4366699286302610133L;
	private String clientName;
	private String msgFromServer;

	public ChatClient() throws RemoteException {
		this("");
	}
	
	public ChatClient(String name) throws RemoteException {
		clientName = name;
	}

	@Override
	public void send(String s) {
		// Server sends message to client
		setMsgFromServer(s);
	}

	@Override
	public String getName() {
		return clientName;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public void setClientName(String name) {
		this.clientName = name;
	}

	public String getMsgFromServer() {
		return msgFromServer;
	}

	public void setMsgFromServer(String msgFromServer) {
		this.msgFromServer = msgFromServer;
	}

}
