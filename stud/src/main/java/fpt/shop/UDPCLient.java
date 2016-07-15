package fpt.shop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPCLient {
	private InetAddress iAddress = null;
	private DatagramSocket dSocket;
	private byte[] sendBuff = new byte[1024];
	private byte[] recvBuff = new byte[1024];
	
	private UDPFetchtimeListener networkListener;
	
	public UDPCLient() {

	}

	public void fetchTime() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					iAddress = InetAddress.getByName("localhost");
				} catch (UnknownHostException e1) {
					System.out.println("couldn't resolve Hostname localhost.");
					e1.printStackTrace();
				}
				try {
					dSocket = new DatagramSocket();
				} catch (SocketException e) {
					e.printStackTrace();
				} 
				String rcvData = null;
				String command = "time";
				try {
					sendBuff = command.getBytes("UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
					System.out.println("Client: time command encoded in UTF-8 failed.");
				}
		
				DatagramPacket sendPacket = new DatagramPacket(sendBuff, sendBuff.length, iAddress, 6667);
				DatagramPacket recvPacket = new DatagramPacket(recvBuff, recvBuff.length);
				while (true) {
					try {
						dSocket.send(sendPacket);
						//System.out.println("UDP Client: Sent packet ok.");
					} catch (IOException e) {
						System.out.println("Sent packet to UDP server failed.");
						e.printStackTrace();
					}
					
					try {
						//System.out.println("UDP Client: waiting to receive.");
						dSocket.receive(recvPacket);
						rcvData = new String(recvPacket.getData());
						//System.out.println("UDP Client:" + rcvData);
						getNetworkListener().updateTime(rcvData);
						Thread.sleep(1000);
					} catch (IOException e) {
						System.out.println("Received packet from UDP server failed.");
						e.printStackTrace();
					} catch (InterruptedException e) {
						System.out.println("UDP Client in sleep but interrupted.");
					}
				}	
			}
		}).start();
	}
	
	public UDPFetchtimeListener getNetworkListener() {
		return this.networkListener;
	}

	public void setUDPListener(UDPFetchtimeListener nl) {
		this.networkListener = nl;
		
	}
		
}
