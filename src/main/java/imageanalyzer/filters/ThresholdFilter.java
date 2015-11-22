package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIHelper;
import imageanalyzer.util.JAIOperators;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class ThresholdFilter extends DataTransformationFilter<JAIDrawable> {

    private static final String FILTER_NAME = "threshold";

    private final double[][] _parameters;

    public ThresholdFilter(Readable<JAIDrawable> input, Writable<JAIDrawable> output, double[]... parameters)
    throws InvalidParameterException {
        super(input, output);
        _parameters = parameters;
    }

    public ThresholdFilter(Writable<JAIDrawable> output, double[]... parameters)
    throws InvalidParameterException {
        super(output);
        _parameters = parameters;
    }

    public ThresholdFilter(Readable<JAIDrawable> input, double[]... parameters)
    throws InvalidParameterException {
        super(input);
        _parameters = parameters;
    }

    @Override
    protected void process(JAIDrawable image) {
        ParameterBlock pb = prepareParameterBlock(image, _parameters);

        //Creating a new Planar Image according to parameter block, applying JAI Operator (filter).
        PlanarImage newImage = JAI.create(
            JAIOperators.THRESHOLD.getOperatorValue(),
            pb
        );

        //Coping image properties.
        copyImageProperties(image.getDrawable(), newImage);

        //Saving the result to JAIDrawable container.
        image.setDrawable(newImage);

        //Saving the current process as a file.
        JAIHelper.saveImage(image.getDrawable(), FILTER_NAME);
    }

    /**
     * Prepares parameter block.
     *
     * @param image source image
     * @param parameters array of double parameters. 1 element - color from, 2 element - color to,
     *                   3 element - color value that will replace all colors in range between 1 and 2 element
     * @return New instance of prepared parameter block
     */
    private ParameterBlock prepareParameterBlock(JAIDrawable image, double[]... parameters) {
        ParameterBlock pb = new ParameterBlock();

        for (double[] parameterGroup : parameters) {
            pb.add(parameterGroup);
        }
        return pb.addSource(image.getDrawable());
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
