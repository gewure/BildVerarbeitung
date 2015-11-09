package imageanalyzer.pipes;

import thirdparty.interfaces.Readable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class ImageSourcePipe implements Readable<PlanarImage>{
    private static final String FILELOAD = "fileload";

    private final String _filePath;

    public ImageSourcePipe(String filePath) {
        _filePath = filePath;
    }

    @Override
    public PlanarImage read() throws StreamCorruptedException {
        return JAI.create(FILELOAD, _filePath);
    }
}
