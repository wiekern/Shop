package fpt.chat;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ChatMain {

	public static void main(String[] args) {
		try {
			ChatServer chatServer = new ChatServer();
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new NullSecurityManager());
			}

			LocateRegistry.createRegistry(1099);
			try {
				// Dienstname: ChatServer
				Naming.rebind("//localhost:1099/ChatServer", chatServer);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} 
			System.out.println("ChatServer started...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
