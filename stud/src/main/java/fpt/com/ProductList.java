package fpt.com;

import java.io.Serializable;

/**
 * This interface is used in exercise one.
 * 
 * @author Jens Kapitza
 * 
 */
public interface ProductList extends Serializable,Iterable<Product> {

	/**
	 * 
	 * Adds a {@link Product} to the list.
	 * 
	 * @param e
	 *            the product
	 */
	boolean add(Product e);

	/**
	 * Delete a {@link Product} from list.
	 * 
	 * @param product
	 *            the product.
	 * @return true if product was deleted, false if product deletion failed.
	 */
	boolean delete(Product product);

	/**
	 * @return the size of the list.
	 */
	int size();

	/**
	 * Find a {@link Product} in the list
	 * 
	 * @param id
	 *            the product id
	 * @return the product or null if product is not present
	 */
	Product findProductById(long id);

	/**
	 * Find a {@link Product} in the list
	 * 
	 * @param name
	 *            the product name
	 * @return the product or null if product is not present
	 */
	Product findProductByName(String name);

}
