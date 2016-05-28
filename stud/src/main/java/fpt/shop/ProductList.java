package fpt.shop;

import java.util.ArrayList;
import fpt.com.Product;

public class ProductList extends ArrayList<Product> implements fpt.com.ProductList {
	private static final long serialVersionUID = 2831444849399808145L;

	@Override
	public boolean add(Product e) {
		return super.add(e);
	}

	@Override
	public boolean delete(Product product) {
		return super.remove(product);
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
}
