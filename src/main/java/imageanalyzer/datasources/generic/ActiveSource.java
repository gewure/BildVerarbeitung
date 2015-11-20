package imageanalyzer.datasources.generic;

import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 19.11.15.
 */
public abstract class ActiveSource<T> extends PassiveSource<T> implements Runnable {
    protected final Writable<T> _writable;

    protected ActiveSource(Writable<T> writable) {
        _writable = writable;
    }

    @Override
    public void run() {
        try {

            if (_writable != null) {

                _writable.write(read());

            }else {
                throw new StreamCorruptedException("output source is null");
            }

        } catch (StreamCorruptedException e) {
            System.out.print("Thread reports error: ");
            System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
            e.printStackTrace();
        }
    }
}
