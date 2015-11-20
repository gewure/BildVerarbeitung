package imageanalyzer.datasources;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.datasources.generic.ActiveSource;
import imageanalyzer.util.JAIOperators;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
import java.io.StreamCorruptedException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class JAIDrawableSourceActive extends ActiveSource<JAIDrawable> {
    private final LinkedList<JAIDrawable> _drawableQueue;
    private final String _filePath;

    public JAIDrawableSourceActive(Writable<JAIDrawable> writable, String filePath) {
        super(writable);
        _filePath = filePath;

        _drawableQueue = new LinkedList<>();
        fillDrawableQueue();
    }

    @Override
    public JAIDrawable read()
    throws StreamCorruptedException {
        return _drawableQueue.pollFirst();
    }

    private void fillDrawableQueue() {
        _drawableQueue.add(
            new JAIDrawable(JAI.create(
                JAIOperators.FILE_LOAD.getOperatorValue(),
                _filePath
            ))
        );
    }
}
