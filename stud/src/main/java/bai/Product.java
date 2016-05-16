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
		return this.id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public double getPrice() {
		return this.price.get();
	}

	@Override
	public void setPrice(double price) {
		this.price.set(price);;
	}

	@Override
	public int getQuantity() {
		return this.quantity.get();
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
		
	}

	@Override
	public String getName() {
		return this.name.get();
	}

	@Override
	public void setName(String name) {
		this.name.set(name);
	}

	@Override
	public ObservableValue<String> nameProperty() {
		return this.name;
	}

	@Override
	public ObservableValue<Number> priceProperty() {
		return this.price;
	}

	@Override
	public ObservableValue<Number> quantityProperty() {
		return this.quantity;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(name.get());
		out.writeDouble(price.get());
		out.writeInt(quantity.get());
		out.writeLong(id);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		setName((String)in.readObject());
		setPrice(in.readDouble());
		setQuantity(in.readInt());
		setId(in.readLong());	
	}
}
