package bai;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;
import fpt.com.Product;

public class XStreamStrategy implements fpt.com.SerializableStrategy {
	
	private XStream xs;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public XStreamStrategy() {
		xs = createXStream(bai.Product.class);
		xs.useAttributeFor(bai.Product.class, "id");
		xs.aliasField("anzahl",bai.Product.class, "quantity");
		xs.aliasField("preis",bai.Product.class, "price");
		xs.alias("ware", bai.Product.class);
		xs.registerConverter(new IDValueConverter());
		xs.registerConverter(new PriceValueConverter());
		xs.registerConverter(new NameValueConverter());
		xs.registerConverter(new QuantityValueConverter());
	}
	
	public XStream getXs() {
		return xs;
	}
	
	@Override
	public Product readObject() throws IOException {
		if (ois != null) {
			try {
				return (Product) ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (EOFException eofE) {
				return null;
			}
		} 
	
		return null;
	}

	@Override
	public void writeObject(Product obj) throws IOException {
		if (oos != null) {
			oos.writeObject(obj);
			oos.flush();
		}
	}

	@Override
	public void close() throws IOException {
		if (oos != null) {
			oos.close();
		}
		
		if (ois != null) {
			ois.close();
		}
		
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		if (input != null) {
			ois = xs.createObjectInputStream(input);
		}
		
		if (output != null) {
			oos = xs.createObjectOutputStream(output, "waren");
		}
	}

}
