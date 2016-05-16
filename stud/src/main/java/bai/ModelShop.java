package bai;

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
		return delegate.get(index);
	}

	@Override
	public int size() {
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
		return delegate.set(index, element);
		//informObeserver();
	}

	@Override
	protected Product doRemove(int index) {
		return delegate.remove(index);
		//informObeserver();
	}

}
