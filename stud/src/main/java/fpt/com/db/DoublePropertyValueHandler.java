package fpt.com.db;

import javafx.beans.property.SimpleDoubleProperty;

import org.apache.openjpa.meta.JavaTypes;

public class DoublePropertyValueHandler extends AbstractPropertyValueHandler {

	private static final long serialVersionUID = 6977270441354674752L;

	@Override
	protected Object mapToObject(Object value) {
		return new SimpleDoubleProperty(new Double(value.toString()));
	}

	@Override
	int getDatabaseColumnType() {
		return JavaTypes.NUMBER;
	}

	
}
