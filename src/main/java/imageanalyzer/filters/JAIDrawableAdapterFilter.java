package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import thirdparty.filter.AbstractFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 20.11.15.
 */
public class JAIDrawableAdapterFilter extends AbstractFilter<JAIDrawable, PlanarImage> {

    public JAIDrawableAdapterFilter(Readable<JAIDrawable> input, Writable<PlanarImage> output)
    throws InvalidParameterException {
        super(input, output);
    }

    public JAIDrawableAdapterFilter(Readable<JAIDrawable> input)
    throws InvalidParameterException {
        super(input);
    }

    public JAIDrawableAdapterFilter(Writable<PlanarImage> output)
    throws InvalidParameterException {
        super(output);
    }

    @Override
    public PlanarImage read()
    throws StreamCorruptedException {
        JAIDrawable input = readInput();
        return input != null ? input.getDrawable() : null;
    }

    @Override
    public void write(JAIDrawable drawable)
    throws StreamCorruptedException {
        if (drawable != null) {
            writeOutput(drawable.getDrawable());
        } else {
            sendEndSignal();
        }
    }

    @Override
    public void run() {
        try {

            PlanarImage output;

            do {

                output = read();
                if (output != null) writeOutput(output);

            } while(output != null);

            sendEndSignal();
        } catch (StreamCorruptedException e) {
            System.out.print("Thread reports error: ");
            System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
            e.printStackTrace();
        }
    }
}
