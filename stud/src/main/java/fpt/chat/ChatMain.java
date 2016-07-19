package fpt.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class ChatMain {

	public static void main(String[] args) {
		try {

			/*  With security manager.
			 * grant{
			 *		permission java.security.AllPermission;
			 * };
			 */
//			System.setProperty("java.security.policy",  
//					ChatMain.class.getResource("java.policy").toString());  
//			if (System.getSecurityManager() == null) {
//				System.setSecurityManager(new SecurityManager());
//			}
			
			ChatServer chatServer = new ChatServer();
			
			Registry reg = LocateRegistry.createRegistry(1099);
			// Dienstname: ChatServer
			reg.rebind("ChatServer", chatServer);
			System.out.println("ChatServer started...");
		} catch (ExportException e) {
			System.out.println("RMI registry port in use, to run again please close the running ChatMain. ");
			System.exit(0);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ;
		}
	}

}
