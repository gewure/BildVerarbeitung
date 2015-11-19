package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import thirdparty.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.function.Consumer;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class VisualizationSinkActive implements Readable<JAIDrawable>, Runnable {
    private final Readable<JAIDrawable> _input;
    private final Consumer<PlanarImage> _visualisationConsumer;

    public VisualizationSinkActive(Readable<JAIDrawable> input, Consumer<PlanarImage> visualisationConsumer)
    throws InvalidParameterException {
        _input = input;
        _visualisationConsumer = visualisationConsumer;
    }

    @Override
    public JAIDrawable read() throws StreamCorruptedException {
        if (_input != null && _visualisationConsumer != null) {
            _visualisationConsumer.accept(_input.read().getDrawable());
        }
        return null;
    }

    @Override
    public void run() {
        try {
            JAIDrawable input;

            if (_input != null) {

                do {
                    input = read();
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
