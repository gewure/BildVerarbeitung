package thirdparty.filter;

import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;



public abstract class DataTransformationFilter<T> extends AbstractFilter<T,T> {

    public DataTransformationFilter(Readable<T> input, Writable<T> output) throws InvalidParameterException {
        super(input, output);

    }

    public DataTransformationFilter(Readable<T> input) throws InvalidParameterException {
        super(input);

    }

    public DataTransformationFilter(Writable<T> output) throws InvalidParameterException {
        super(output);

    }

    public T read() throws StreamCorruptedException {
        T entity = readInput();
        if (entity != null) process(entity);
        return entity;
    }

    public void write(T value) throws StreamCorruptedException {
        if (value != null) process(value);
        writeOutput(value);
    }
    
    /**
     * does the transformation on indsys.entity
     * @param entity
     */
    protected abstract void process(T entity);

    public void run() {
        T input = null;
        try {
            do {
    
                input = readInput();
                if (input != null) {
                    process(input);
                    writeOutput(input);
                }
                
            }while(input != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }
    
    

}
