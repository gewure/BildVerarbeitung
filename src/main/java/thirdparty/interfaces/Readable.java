package thirdparty.interfaces;

import imageanalyzer.util.EOFException;

import java.io.StreamCorruptedException;

public interface Readable<T>  {
	public T read() throws StreamCorruptedException, EOFException;
}
