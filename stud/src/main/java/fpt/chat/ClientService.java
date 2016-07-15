package fpt.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientService extends Remote {
	public void send(String s) throws RemoteException;
	public String getName() throws RemoteException;
	
//	
//	• Registry starten
//	• Server starten und an der Registry anmelden.
//	• Client starten
//	• Client an der Registry unter Benutzernamen anmelden
//	• Dem Server den Namen des Client mitteilen.
//	• Client sendet eine Nachricht an den Server.
//	• Server holt sich aus der Registry die ihm bekannten Clients (bekannte Namen), um die erhaltene Nachricht an diese zu verteilen.
//	• Die eingehenden Nachrichten in der GUI anzeigen.
	
}
