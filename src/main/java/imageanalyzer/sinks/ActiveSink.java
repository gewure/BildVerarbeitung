package imageanalyzer.sinks;

import imageanalyzer.util.ActiveData;
import imageanalyzer.util.EOFException;
import thirdparty.interfaces.Readable;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 19.11.15.
 */
public abstract class ActiveSink<T> extends ActiveData implements Readable<T> {

    protected Readable<T> preData;
    public Object ENDING_SIGNAL = null;

    public ActiveSink(Readable<T> preData) {
        this.preData = preData;
    }

    @Override
    public T read() throws StreamCorruptedException, EOFException {
        return preData.read();
    }
}