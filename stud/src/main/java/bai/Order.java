package bai;

import java.util.ArrayList;
import fpt.com.Product;

public class Order extends ArrayList<Product> implements fpt.com.Order {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double sum;
	private int quantity;

	@Override
	public boolean add(Product e) {
		// TODO Auto-generated method stub
		if (super.add(e)) {
			this.sum += e.getPrice();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(Product p) {
		// TODO Auto-generated method stub
		if (super.remove(p)) {
			this.sum -= p.getPrice();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size();
	}

	@Override
	public Product findProductById(long id) {
		// TODO Auto-generated method stub
		for(Product product: this){
			if (product.getId() == id) {
				return product;
			}
		}
		return null;
	}
	
	@Override
	public Product findProductByName(String name) {
		// TODO Auto-generated method stub
		for(Product product: this){
			if (product.getName() == name) {
				return product;
			}
		}
		return null;
	}

	@Override
	public double getSum() {
		// TODO Auto-generated method stub
		return this.sum;
	}

	@Override
	public int getQuantity() {
		// TODO Auto-generated method stub
		return this.quantity;
	}

}
