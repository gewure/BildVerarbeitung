package imageanalyzer.datasources;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.datasources.generic.ActiveSource;
import imageanalyzer.util.JAIOperators;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
import java.io.StreamCorruptedException;
import java.util.LinkedList;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class JAIDrawableSource extends ActiveSource<JAIDrawable> {
    private LinkedList<JAIDrawable> _drawableQueue;
    private String _filePath;

    public JAIDrawableSource(String filePath) {
        this(null, filePath);
    }

    public JAIDrawableSource(Writable<JAIDrawable> writable, String filePath) {
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
        //Source queue, contains all drawable elements.
        _drawableQueue.add(
            new JAIDrawable(JAI.create(
                JAIOperators.FILE_LOAD.getOperatorValue(),
                _filePath
            ))
        );
    }
}
