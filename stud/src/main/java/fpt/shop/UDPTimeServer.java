package fpt.shop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UDPTimeServer {
	public void UDPServer() {
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					DatagramSocket dSocket = new DatagramSocket(6667);
					byte rcvBuffer[] = new byte[1024];
					byte sendBuffer[] = new byte[1024];
					while (true) {
						DatagramPacket rcvPacket = new DatagramPacket(rcvBuffer, rcvBuffer.length);
						try {
							//System.out.println("Blocked to accept new packet from UDP Client.");
							dSocket.receive(rcvPacket);
							InetAddress clientIA = rcvPacket.getAddress();
							int clientPort = rcvPacket.getPort();
							System.out.println("Client Port:" + clientPort + ", IP:" + rcvPacket.getAddress().toString());
							String strData = new String(rcvPacket.getData());
							if (strData.substring(0, 4).equals("time")) {
								Date localDate = new Date();
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
								sendBuffer = (simpleDateFormat.format(localDate)).getBytes("UTF-8");
								DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientIA, clientPort);
								dSocket.send(sendPacket);
							} else {
								System.out.println("UDP Server: The Command not supported.");
							}
						} catch (UnsupportedEncodingException e) {
							System.out.println("UDP Server: encoding Date in UTF-8 failed.");
							
						} catch (IOException e) {
							System.out.println("UDP Server: receive failed.");
						}
					}
				} catch (BindException e) {
					System.out.println("UDP Time Server bind failed, address already in use.Please close the running UDP running Server");
					System.exit(0);
				} catch (SocketException e) {
					e.printStackTrace();
					return ;
				}
			}
		}).start();
	}
}
