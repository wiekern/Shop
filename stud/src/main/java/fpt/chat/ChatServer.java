package fpt.chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatService {

	private static final long serialVersionUID = 699547173372112644L;
	private LinkedList<String> userList = null;

	public ChatServer() throws RemoteException {	
		userList = new LinkedList<>();
	}

	@Override
	public void login(String s) {
		// Client login
		if (!isLogin(s)) {
			userList.add(s);
		} else {
			System.out.println(s + "is login, don't repeat login.");
		}
	}

	@Override
	public void logout(String s) {
		// Client logout
		System.out.println("User: " + s + "logout.");
		userList.remove(s);	
	}

	@Override
	public void send(String s) {
		// Clinet sends message to server
		
		// Suche angemeldte Clients, die dem ChatServer Dienst anbieten.
		for (String username : userList) {
			try {
				// Server sendet die Nachricht an den Client zurueck.
				System.out.println("User:" + username);
				ClientService remote = (ClientService) Naming.lookup("//localhost:1099/" + username);
				remote.send(s);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> getUserList() {
		return userList;
	} 
	
	private boolean isLogin(String name) {
		for (String s : userList) {
			if (s.equals(name)) {
				return true;
			}
		}
		return false;
	}
}
