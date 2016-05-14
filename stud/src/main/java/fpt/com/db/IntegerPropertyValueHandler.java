package fpt.com.db;

import javafx.beans.property.SimpleIntegerProperty;

import org.apache.openjpa.meta.JavaTypes;

public class IntegerPropertyValueHandler extends AbstractPropertyValueHandler {

	private static final long serialVersionUID = 6977370441354674752L;

	@Override
	protected Object mapToObject(Object value) {
		return new SimpleIntegerProperty(new Integer(value.toString()));
	}

	@Override
	int getDatabaseColumnType() {
		return JavaTypes.NUMBER;
	}

}
