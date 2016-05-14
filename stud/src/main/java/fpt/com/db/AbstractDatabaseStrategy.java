package fpt.com.db;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import fpt.com.SerializableStrategy;

public abstract class AbstractDatabaseStrategy implements SerializableStrategy {

	public abstract void open() throws IOException;

	@Override
	public void open(Path path) throws IOException {
		open();
	}

	@Override
	public void open(InputStream input, OutputStream output) throws IOException {
		open();
	}

}
