package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIHelper;
import imageanalyzer.util.JAIOperators;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.image.renderable.ParameterBlock;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class OpeningFilter extends DataTransformationFilter<JAIDrawable> {

    private static final String FILTER_NAME = "opening";
    private static final int KERNEL_SIZE_X = 12;
    private static final int KERNEL_SIZE_Y = 13;
    private static final float[] KERNEL_VALUES = {
       0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
       0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
       0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
       0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
       1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
       1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
       1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
       1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
       1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
       0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
       0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
       0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
       0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
    };

    private static final KernelJAI KERNEL = new KernelJAI(KERNEL_SIZE_X, KERNEL_SIZE_Y, KERNEL_VALUES);

    public OpeningFilter(Readable<JAIDrawable> input, Writable<JAIDrawable> output)
    throws InvalidParameterException {
        super(input, output);
    }

    public OpeningFilter(Readable<JAIDrawable> input)
    throws InvalidParameterException {
        super(input);
    }

    public OpeningFilter(Writable<JAIDrawable> output)
    throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(JAIDrawable image) {
        //Eroding and Dilating (Opening) source image.
        PlanarImage erodedImage = performTransformationStep(JAIOperators.ERODE, image.getDrawable());
        PlanarImage dilatedImage = performTransformationStep(JAIOperators.DILATE, erodedImage);

        //Coping image properties.
        copyImageProperties(image.getDrawable(), dilatedImage);

        //Saving the result to JAIDrawable container.
        image.setDrawable(dilatedImage);

        //Saving the current process as a file.
        JAIHelper.saveImage(image.getDrawable(), FILTER_NAME);
    }

    /**
     * Applies given JAIOperator to given image.
     *
     * @param operator JAIOperator to be applied to image.
     * @param image Image that will be transformed by given JAIOperator.
     * @return Transformed Planar image
     */
    private PlanarImage performTransformationStep(JAIOperators operator, PlanarImage image) {
        //Transforming image according to the given JAI operator.
        return JAI.create(
             operator.getOperatorValue(),
             new ParameterBlock().add(KERNEL).addSource(image)
         );
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
