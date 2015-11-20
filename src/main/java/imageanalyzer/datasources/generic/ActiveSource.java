package imageanalyzer.datasources.generic;

import thirdparty.interfaces.Writable;

import java.io.StreamCorruptedException;

/**
 * Created by f00 on 19.11.15.
 */
public abstract class ActiveSource<T> extends PassiveSource<T> implements Runnable {
    private Writable<T> _writable;

    protected ActiveSource() {
        //passive mode
    }

    protected ActiveSource(Writable<T> writable) {
        //active mode
        _writable = writable;
    }

    @Override
    public void run() {
        if (_writable != null) {

            try {

                T input;

                do {

                    input = read();
                    _writable.write(input);

                } while (input != null); //reading and forwarding all the data

            } catch (StreamCorruptedException e) {
                System.out.print("Thread reports error: ");
                System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
                e.printStackTrace();
            }

        } else {
            System.out.println("Auto-switched to passive mode. Output is not set.");
        }
    }
}
