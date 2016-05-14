package fpt.com.db;

import javafx.beans.value.WritableValue;

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.AbstractValueHandler;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.jdbc.sql.DBDictionary;

public abstract class AbstractPropertyValueHandler extends AbstractValueHandler {

	private static final long serialVersionUID = 6977270441354674782L;

	public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
		if (val instanceof String) {
			return val;
		}
		return (val == null) ? null : ((WritableValue<?>) val).getValue();
	}

	abstract protected Object mapToObject(Object value);
	

	public Object toObjectValue(ValueMapping vm, Object val) {
		if (val == null)
			return null;
		return mapToObject(val);
	}
	
	abstract int getDatabaseColumnType();
	
	

	@Override
	public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
		DBDictionary dict = vm.getMappingRepository().getDBDictionary();
		DBIdentifier colName = DBIdentifier.newColumn(name,
				dict != null ? dict.delimitAll() : false);

		Column col = new Column();
		col.setIdentifier(colName);
		col.setJavaType(getDatabaseColumnType());
		return new Column[] { col };
	}

}
