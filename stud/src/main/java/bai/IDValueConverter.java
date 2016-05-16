package bai;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public class IDValueConverter implements SingleValueConverter{

	@Override
	public boolean canConvert(Class type) {
		return type.equals(long.class);
	}

	@Override
	public String toString(Object obj) {
		return String.format("%06d", (long)obj);
	}

	@Override
	public Object fromString(String str) {
		return Long.parseLong(str);
	}

}
