package fpt.shop;

import fpt.com.Product;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;

public class ModelShop extends ModifiableObservableListBase<Product> {
	private final ObservableList<Product> delegate;
	private final ObservableList<Product> orderList;
	private static ModelShop instance = null;
	
	public static ModelShop getInstance() {
		if (instance == null) {
			instance = new ModelShop();
		}
		return instance;
	}
	
	public ModelShop() {
		delegate = FXCollections.observableArrayList(new ProductList());
		orderList = FXCollections.observableArrayList(new Order());
	}
	
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
		// Avoid to add product with same ID, but to use OpenJPA, the default ID will be null.
		// We allow products with same ID, after persistence via OpenJPA we get a new ID.
//		for(Product e: delegate) {
//			if (e.getId() == element.getId()) {
//				System.out.println("Didn't add a product with same ID: " + e.getId());
//				return ;
//			}
//		}
		delegate.add(index, element);
	}

	@Override
	protected Product doSet(int index, Product element) {
		return delegate.set(index, element);
	}

	@Override
	protected Product doRemove(int index) {
		return delegate.remove(index);
	}

	public ObservableList<Product> getOrderList() {
		return this.orderList;
	}

}
