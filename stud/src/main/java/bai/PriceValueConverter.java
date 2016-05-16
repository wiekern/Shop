package bai;

import com.thoughtworks.xstream.converters.SingleValueConverter;

import javafx.beans.property.SimpleDoubleProperty;

public class PriceValueConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class type) {
		return type.equals(SimpleDoubleProperty.class);
	}

	@Override
	public String toString(Object obj) {
		return String.format("%.2f", ((SimpleDoubleProperty)obj).get());
	}

	@Override
	public Object fromString(String str) {
		return new SimpleDoubleProperty(Double.parseDouble(str));
	}

}
