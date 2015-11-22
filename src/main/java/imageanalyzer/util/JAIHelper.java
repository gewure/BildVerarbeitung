package imageanalyzer.util;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class JAIHelper {
    private static final String FILE_OUTPUT_FORMAT = "JPEG";

    /**
     * Saves planar image to local storage.
     *
     * @param image image to be saved.
     * @param name preferred name of the file.
     */
    public static void saveImage(PlanarImage image, String name) {
        String fileOutputPath = name + "." + FILE_OUTPUT_FORMAT;

        //Saves image.
        JAI.create(
            JAIOperators.FILE_STORE.getOperatorValue(),
            image,
            fileOutputPath,
            FILE_OUTPUT_FORMAT
        );
    }
}
