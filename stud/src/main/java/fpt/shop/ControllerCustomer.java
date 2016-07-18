package fpt.shop;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;

public class ControllerCustomer {
	private ViewCustomer vCustomer;
	private ModelShop mShop;

	public ControllerCustomer() {
	}
	
	public void link(ViewCustomer view, ModelShop model) {
		this.vCustomer = view;
		this.mShop = model;
		
		vCustomer.getProductTableView().setItems(mShop.getOrderList());
		vCustomer.getProductListView().setItems(mShop.getDelegate());
		vCustomer.setProductListCellFactory();
		mShop.addListener(new ListChangeListener() {
			@Override
			public void onChanged(Change c) {
				vCustomer.getProductListView().refresh();	
			}
		});
		
		vCustomer.setBtnActionChat();
		vCustomer.setBtnActionSendmsg();
		vCustomer.setBtnActionBuy();
		vCustomer.setBtnActionAddWare();
		
		UDPCLient udpClient = new UDPCLient();
		// UDP Client is a Listener for updating time.
		udpClient.setUDPListener(date -> {
	 		Platform.runLater(() -> {
	 			vCustomer.getClock().setText(date);
    		});	
        });	
		udpClient.fetchTime();
        
		// includes a reference of TCP client to send order to warehouse.
        TCPClient tcpClient = new TCPClient();
        vCustomer.setTcpClient(tcpClient);
        
	}

}
