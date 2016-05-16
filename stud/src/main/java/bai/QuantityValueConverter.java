package bai;

import com.thoughtworks.xstream.converters.SingleValueConverter;

import javafx.beans.property.SimpleIntegerProperty;

public class QuantityValueConverter implements SingleValueConverter{
	@Override
	public boolean canConvert(Class type) {
		return type.equals(SimpleIntegerProperty.class);
	}

	@Override
	public String toString(Object obj) {
		return String.format("%d", ((SimpleIntegerProperty)obj).get());
	}

	@Override
	public Object fromString(String str) {
		return new SimpleIntegerProperty(Integer.parseInt(str));
	}
}
