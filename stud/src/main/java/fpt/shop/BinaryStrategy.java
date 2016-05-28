package fpt.shop;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.sun.javafx.iio.ios.IosDescriptor;

import fpt.com.Product;

public class BinaryStrategy implements fpt.com.SerializableStrategy {
	
	private Product product;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	@Override
	public Product readObject() throws IOException {
		if (ois != null) {
			try {
				product = (Product)ois.readObject();
			} catch (EOFException eofE) {
				return null;
			} catch (IOException IoE) {
				IoE.printStackTrace();	
			} catch (ClassNotFoundException notFoundE) {
				notFoundE.printStackTrace();
			} 
			return product;
		} else {
			return null;
		}
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
		if (ois != null) {
			ois.close();
		}
		
		if (oos != null) {
			oos.close();
		}
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		if(input != null) {
			ois = new ObjectInputStream(input);
		}
		
		if(output != null) {
			oos = new ObjectOutputStream(output);
		}
	}
}
