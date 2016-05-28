package fpt.com.shop;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.openjpa.persistence.Persistent;
import org.apache.openjpa.persistence.jdbc.Strategy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

@XStreamAliasType("ware")
@XStreamAlias("ware")
@Entity
@Table(name="products")
public class Product implements fpt.com.Product, java.io.Externalizable {
	
	private static final long serialVersionUID = -3882941579501141628L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_SEQ")
	//@SequenceGenerator(name = "product_SEQ", sequenceName ="products_id_seq", allocationSize = 1)
	private long id;
	
	@Persistent
	@Strategy("fpt.com.db.StringPropertyValueHandler")
	private SimpleStringProperty name = new SimpleStringProperty();
	
	@Persistent
	@Strategy("fpt.com.db.DoublePropertyValueHandler")
	private SimpleDoubleProperty price = new SimpleDoubleProperty();
	
	@Persistent
	@Strategy("fpt.com.db.IntegerPropertyValueHandler")
	private SimpleIntegerProperty quantity = new SimpleIntegerProperty();


	protected Product() {
		//this("", 0, 0, 0);
	}
	
	protected Product(String name, double price, int quantity) {
		this.name.set(name);
		this.price.set(price);;
		this.quantity.set(quantity);
	}
	
	protected Product(String name, long id, double price, int quantity) {
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
		this.price.set(price);
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

