package bai;

import bai.ModelShop;
import bai.ViewShop;
import javafx.collections.ListChangeListener;

public class ControllerShop {
	private ViewShop vShop;
	private ModelShop mShop;
	
	public ControllerShop() {
		super();
	}
	
	public void link(ModelShop model, ViewShop view) {
		this.vShop = view;
		this.mShop = model;
	
		//this.mShop.registerObeserver(view);
		mShop.addListener(new ListChangeListener() {
			@Override
			public void onChanged(Change c) {
				vShop.getWarenShowList().refresh();
			}
			
		});
		vShop.getWarenShowList().setItems((mShop.getDelegate()));
		vShop.setWarenListCellFactory();
		vShop.clickWaren();
		vShop.setBtnActionAddProduct();
		vShop.setBtnActionDeleteProduct();
		vShop.setBtnActionLoadStrategy();
		vShop.setBtnActionStoreStrategy();
	}
}
