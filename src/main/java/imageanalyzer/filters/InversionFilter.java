package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIHelper;
import imageanalyzer.util.JAIOperators;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class InversionFilter extends DataTransformationFilter<JAIDrawable> {

    private static final String FILTER_NAME = "inversion";

    public InversionFilter(Readable<JAIDrawable> input, Writable<JAIDrawable> output)
    throws InvalidParameterException {
        super(input, output);
    }

    public InversionFilter(Readable<JAIDrawable> input)
    throws InvalidParameterException {
        super(input);
    }

    public InversionFilter(Writable<JAIDrawable> output)
    throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(JAIDrawable image) {
        //Creating a new Planar Image according to parameter block, applying JAI Operator (filter)
        //(inverses colors on the picture)
        PlanarImage newImage = JAI.create(
            JAIOperators.INVERT.getOperatorValue(),
            image.getDrawable()
        );

        //Coping image properties.
        copyImageProperties(image.getDrawable(), newImage);

        //Saving the result to JAIDrawable container.
        image.setDrawable(newImage);

        //Saving the current process as a file.
        JAIHelper.saveImage(image.getDrawable(), FILTER_NAME);
    }

    /**
     * Copies all the parameters from source to new image.
     *
     * @param sourceImage image from which properties will be copied.
     * @param newImage image to which properties will be copied.
     */
    private void copyImageProperties(PlanarImage sourceImage, PlanarImage newImage) {
        newImage.setProperty(
            JAIOperators.THRESHOLD_X.getOperatorValue(),
            sourceImage.getProperty(JAIOperators.THRESHOLD_X.getOperatorValue())
        );

        newImage.setProperty(
            JAIOperators.THRESHOLD_Y.getOperatorValue(),
            sourceImage.getProperty(JAIOperators.THRESHOLD_Y.getOperatorValue())
        );
    }
}
