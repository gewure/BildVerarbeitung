package imageanalyzer.sinks.generic;

import thirdparty.interfaces.Readable;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 19.11.15.
 */
public abstract class ActiveSink<T> extends PassiveSink<T> implements Runnable {
    private Readable<T> _readable;

    protected ActiveSink() {
        //passive mode
    }

    protected ActiveSink(Readable<T> readable) {
        //active mode
        _readable = readable;
    }

    @Override
    public void run() {
        if (_readable != null) {

            try {

                T input;

                do {

                    input = _readable.read();
                    write(input);

                } while (input != null);  //reading and sinking all the data

            } catch (StreamCorruptedException e) {
                System.out.print("Thread reports error: ");
                System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
                e.printStackTrace();
            }

        } else {
            System.out.println("Auto-switched to passive mode. Input is not set.");
        }
    }
}
