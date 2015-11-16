package imageanalyzer.util;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class JAIHelper {
    private static final String FILE_OUTPUT_FORMAT = "JPEG";

    public static void saveImage(PlanarImage image, String filterName) {
        String fileOutputPath = filterName + "." + FILE_OUTPUT_FORMAT;

        JAI.create(
            JAIOperators.FILE_STORE.getOperatorValue(),
            image,
            fileOutputPath,
            FILE_OUTPUT_FORMAT
        );
    }
}
