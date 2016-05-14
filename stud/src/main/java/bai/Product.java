package bai;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class Product implements fpt.com.Product, java.io.Externalizable{


	
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleDoubleProperty price = new SimpleDoubleProperty();
	private SimpleIntegerProperty quantity = new SimpleIntegerProperty();
	private long id;

	public Product() {
		this("", 0, 0, 0);
	}
	
	public Product(String name, long id, double price, int quantity) {
		this.name.set(name);
		this.price.set(price);;
		this.quantity.set(quantity);
		this.id = id;
	}
	
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return this.price.get();
	}

	@Override
	public void setPrice(double price) {
		// TODO Auto-generated method stub
		this.price.set(price);;
	}

	@Override
	public int getQuantity() {
		// TODO Auto-generated method stub
		return this.quantity.get();
	}

	@Override
	public void setQuantity(int quantity) {
		// TODO Auto-generated method stub
		this.quantity.set(quantity);
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name.get();
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name.set(name);
	}

	@Override
	public ObservableValue<String> nameProperty() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public ObservableValue<Number> priceProperty() {
		// TODO Auto-generated method stub
		return this.price;
	}

	@Override
	public ObservableValue<Number> quantityProperty() {
		// TODO Auto-generated method stub
		return this.quantity;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}
}
