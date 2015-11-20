package imageanalyzer.datasources;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.datasources.generic.PassiveSource;
import imageanalyzer.util.JAIOperators;

import javax.media.jai.JAI;
import java.io.StreamCorruptedException;
import java.util.LinkedList;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class JAIDrawableSourcePassive extends PassiveSource<JAIDrawable> {
    private final LinkedList<JAIDrawable> _drawableQueue;
    private final String _filePath;

    public JAIDrawableSourcePassive(String filePath) {
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
