package fpt.com;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.thoughtworks.xstream.XStream;

public interface SerializableStrategy {

	/**
	 * read product form io-stream. ensure stream is open.
	 * 
	 * @return the serialized object from the io-stream
	 * @throws IOException
	 *             if stream is closed
	 * @throws ClassNotFoundException
	 *             if the class of the object is not present
	 */
	Product readObject() throws IOException;

	/**
	 * write product to the io-stream
	 * 
	 * @param obj
	 *            the object for serialization
	 * @throws IOException
	 *             if stream is closed or we can't write
	 */
	void writeObject(Product obj) throws IOException;

	/**
	 * close the io-stream
	 * 
	 * @throws IOException
	 *             if stream can't be closed.
	 */
	void close() throws IOException;

	/**
	 * open the io-stream
	 * 
	 * @param path
	 *            the path for the file storage
	 * @throws IOException
	 *             if the stream can't be created
	 */
	default void open(Path path) throws IOException {
		if (path != null) {
			ByteArrayInputStream in = null;
			if (Files.exists(path)) {
				byte[] data = Files.readAllBytes(path);
				in = new ByteArrayInputStream(data);
			}
			OutputStream out = Files.newOutputStream(path);
			open(in, out);
		}
	}

	/**
	 * open the io-stream
	 * 
	 * @param path
	 *            the path for the file storage
	 * @throws IOException
	 *             if the stream can't be created
	 */
	default void open(String... path) throws IOException {
		if (path != null && path.length > 0 && path[0] != null) {
			open(Paths.get("", path));
		}
	}

	/**
	 * open the io-stream
	 * 
	 * @param input
	 *            the file data if previews file exists, otherwise null
	 * @param output
	 *            the new output
	 * @throws IOException
	 *             if any error occurs
	 */
	void open(InputStream input, OutputStream output) throws IOException;

	default XStream createXStream(Class<? extends Product> clazz) {
		XStream xstream = new XStream();
		xstream.processAnnotations(clazz);
		xstream.autodetectAnnotations(true);
		// alias DB stuff  sollte auch als Annotation gehen
		xstream.aliasSystemAttribute(null, "class");
		
		// prioritaet aendern da BinSer Externalizable nutzt!
		xstream.registerConverter(
				new ExternalizableReflectionConverter(xstream),
				XStream.PRIORITY_LOW);

		return xstream;
	}
}
