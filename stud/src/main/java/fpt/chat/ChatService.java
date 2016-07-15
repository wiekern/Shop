package fpt.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {
	public void login(String s) throws RemoteException;
	public void logout(String s) throws RemoteException;
	public void send(String s) throws RemoteException;
	public List<String> getUserList() throws RemoteException;
	
}
