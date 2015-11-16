package thirdparty.pipes;

import thirdparty.interfaces.IOable;
import thirdparty.interfaces.Writable;
import thirdparty.interfaces.Readable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 16-Nov-15.
 */
public class Pipe<T> implements IOable<T, T> {
    private Readable<T> input;
    private Writable<T> output;

    public Pipe(Readable<T> input, Writable<T> output)
    throws InvalidParameterException {

        this.input = input;
        this.output = output;
    }

    public Pipe(Readable<T> input)
    throws InvalidParameterException {

    }

    public Pipe(Writable<T> output)
    throws InvalidParameterException {

    }

    @Override
    public T read() throws StreamCorruptedException {
        return null;
    }

    @Override
    public void write(T value) throws StreamCorruptedException {

    }
}
