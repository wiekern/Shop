package fpt.shop;

import java.util.ArrayList;
import fpt.com.Product;
import javafx.collections.ObservableList;

public class Order extends ArrayList<Product> implements fpt.com.Order {
	private static final long serialVersionUID = -2137139322489860936L;
	private int numberOfProducts = 0;
	private double sum = 0.0f;

	public Order() {
	}
	
	public Order(ObservableList<Product> obList) {
		super(obList);
	}

	@Override
	public boolean add(Product p) {
		if (super.add(p)) {
			this.numberOfProducts += p.getQuantity();
			this.sum += p.getPrice() * p.getQuantity();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(Product p) {
		if (super.remove(p)) {
			this.numberOfProducts -= p.getQuantity();
			this.sum -= p.getPrice() * p.getQuantity();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		return this.size();
	}

	@Override
	public Product findProductById(long id) {
		for(Product product: this){
			if (product.getId() == id) {
				return product;
			}
		}
		return null;
	}
	
	@Override
	public Product findProductByName(String name) {
		for(Product product: this){
			if (product.getName() == name) {
				return product;
			}
		}
		return null;
	}

	@Override
	public double getSum() {
		return this.sum;
	}

	@Override
	public int getQuantity() {
		return this.numberOfProducts;
	}
}
