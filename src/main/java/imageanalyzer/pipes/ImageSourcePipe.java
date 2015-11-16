package imageanalyzer.pipes;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIOperators;
import thirdparty.interfaces.Readable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class ImageSourcePipe implements Readable<JAIDrawable>{
    private final String _filePath;

    public ImageSourcePipe(String filePath) {
        _filePath = filePath;
    }

    @Override
    public JAIDrawable read() throws StreamCorruptedException {
        return new JAIDrawable(JAI.create(
            JAIOperators.FILE_LOAD.getOperatorValue(),
            _filePath
        ));
    }
}
