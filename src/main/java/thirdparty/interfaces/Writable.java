package thirdparty.interfaces;

import java.io.StreamCorruptedException;

public interface Writable<T> {
	void write(T value) throws StreamCorruptedException;
}
