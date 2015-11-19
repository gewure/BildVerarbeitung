package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIOperators;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class SourceActive implements Writable<JAIDrawable>, Runnable {

    private final Writable<JAIDrawable> _output;
    private final String _filePath;

    public SourceActive(Writable<JAIDrawable> output, String filePath) {
        _output = output;
        _filePath = filePath;
    }

    @Override
    public void write(JAIDrawable value) throws StreamCorruptedException {
        _output.write(value);
    }

    @Override
    public void run() {
        try {
            if (_output != null) {
                _output.write(getEntity());
            } else {
                throw new StreamCorruptedException("output source is null");
            }
        } catch (StreamCorruptedException e) {
            System.out.print("Thread reports error: ");
            System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
            e.printStackTrace();
        }
    }

    public JAIDrawable getEntity() {
        return new JAIDrawable(JAI.create(
            JAIOperators.FILE_LOAD.getOperatorValue(),
            _filePath
        ));
    }
}
