package imageanalyzer.sinks.generic;

import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 19.11.15.
 */
public abstract class ActiveSink<T> extends PassiveSink<T> implements Runnable {
    private final Readable<T> _readable;

    protected ActiveSink(Readable<T> readable) {
        _readable = readable;
    }

    @Override
    public void run() {
        try {

            if (_readable != null) {

                T input;

                do {

                    input = _readable.read();
                    write(input);

                } while(input != null);  //reading and sinking all the data

            } else {
                throw new StreamCorruptedException("input source is null");
            }
        } catch (StreamCorruptedException e) {
            System.out.print("Thread reports error: ");
            System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
            e.printStackTrace();
        }
    }
}