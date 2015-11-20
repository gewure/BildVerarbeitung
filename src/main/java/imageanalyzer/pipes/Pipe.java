package imageanalyzer.pipes;

import thirdparty.interfaces.IOable;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 16-Nov-15.
 */
public class Pipe<T> implements IOable<T, T> {
    private Readable<T> _input;
    private Writable<T> _output;

    public Pipe(Readable<T> input, Writable<T> output)
    throws InvalidParameterException {
        _input = input;
        _output = output;
    }

    public Pipe(Readable<T> input)
    throws InvalidParameterException {
        _input = input;
    }

    public Pipe(Writable<T> output)
    throws InvalidParameterException {
        _output = output;
    }

    @Override
    public T read() throws StreamCorruptedException {
        if (_input == null) {
            throw new StreamCorruptedException();
        }
        return _input.read();
    }

    @Override
    public void write(T value) throws StreamCorruptedException {
        if (_output == null) {
            throw new StreamCorruptedException();
        }
        _output.write(value);
    }
}
