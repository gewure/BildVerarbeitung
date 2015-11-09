package thirdparty.interfaces;

import java.io.StreamCorruptedException;

public interface Writable<T> {
	public void write(T value) throws StreamCorruptedException;
}
