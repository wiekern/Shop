package bai;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Observer;

import bai.ProductList;
import fpt.com.Product;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

public class ModelShop extends ModifiableObservableListBase<Product> {
	private final ObservableList<Product> delegate;
	//private ArrayList<Observer> observers;
	
	public ModelShop() {
		//observers = new ArrayList<>();
		delegate = FXCollections.observableArrayList(new ProductList());
	}
	
//	public void registerObeserver(Observer observer) {
//		this.observers.add(observer);
//	}
//	
//	public void informObeserver() {
//		for(Observer ob: this.observers) {
//			ob.update(null, ob);
//		}
//	}
	
	public ObservableList<Product> getDelegate() {
		return this.delegate;
	}

	@Override
	public Product get(int index) {
		// TODO Auto-generated method stub
		return delegate.get(index);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return delegate.size();
	}

	@Override
	protected void doAdd(int index, Product element) {
		// TODO Auto-generated method stub
//		for(Product e: delegate) {
//			if (e.getId() == element.getId()) {
//				System.out.println(e.getId());
//				return ;
//			}
//		}
		delegate.add(index, element);
		//informObeserver();
	}

	@Override
	protected Product doSet(int index, Product element) {
		// TODO Auto-generated method stub
		Product productSeted = delegate.set(index, element);
		//informObeserver();
		return productSeted;
	}

	@Override
	protected Product doRemove(int index) {
		// TODO Auto-generated method stub
		Product productRemoved = delegate.remove(index);
		//informObeserver();
		return productRemoved;
		
	}

}
