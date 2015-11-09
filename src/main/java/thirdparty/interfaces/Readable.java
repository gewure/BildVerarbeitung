package thirdparty.interfaces;

import java.io.StreamCorruptedException;

public interface Readable<T>  {
	public T read() throws StreamCorruptedException;
}
