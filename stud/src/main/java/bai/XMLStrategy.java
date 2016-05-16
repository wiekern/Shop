package bai;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fpt.com.Product;

public class XMLStrategy implements fpt.com.SerializableStrategy {
	
	private XMLEncoder xmlEncoder;
	private XMLDecoder xmlDecoder;

	@Override
	public Product readObject() throws IOException {
		if (xmlDecoder != null) {
			Product product = (Product)xmlDecoder.readObject();
			return product;
		} else {
			return null;
		}
	}

	@Override
	public void writeObject(Product obj) throws IOException {
		if (xmlEncoder != null) {
			xmlEncoder.writeObject(obj);
			xmlEncoder.flush();
		}	
	}

	@Override
	public void close() throws IOException {
		if (xmlEncoder != null) {
			xmlEncoder.close();
		}
		
		if (xmlDecoder != null) {
			xmlDecoder.close();
		}
		
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		if(output != null) {
			xmlEncoder = new XMLEncoder(output);
		}
		
		if(input != null) {
			xmlDecoder = new XMLDecoder(input);
		}
	}

}
