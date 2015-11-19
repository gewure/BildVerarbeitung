package imageanalyzer.util;

import thirdparty.interfaces.Writable;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 19.11.15.
 */
public abstract class ActiveSource<T> extends ActiveData implements Writable<T>{
    protected Writable<T> postData;
    public Object ENDING_SIGNAL = null;

    public ActiveSource(Writable<T> postData) {
        this.postData = postData;
    }

    @Override
    public void write(T value) throws StreamCorruptedException {
        postData.write(value);
    }
}
