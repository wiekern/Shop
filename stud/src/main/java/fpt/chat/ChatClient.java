package fpt.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.scene.control.TextArea;


public class ChatClient extends UnicastRemoteObject implements ClientService {

	private static final long serialVersionUID = 4366699286302610133L;
	private String clientName;
	private String msgFromServer;
	private TextArea chatArea;

	public TextArea getChatArea() {
		return chatArea;
	}

	public void setChatArea(TextArea chatArea) {
		this.chatArea = chatArea;
	}

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
		chatArea.appendText(s + "\n");
		chatArea.setScrollTop(Double.MAX_VALUE);
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
