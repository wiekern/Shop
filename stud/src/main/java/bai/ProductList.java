package bai;

import java.util.ArrayList;
import fpt.com.Product;

public class ProductList extends ArrayList<Product> implements fpt.com.ProductList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public boolean add(Product e) {
		// TODO Auto-generated method stub
		return super.add(e);
	}

	@Override
	public boolean delete(Product product) {
		// TODO Auto-generated method stub
		return super.remove(product);
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
}
