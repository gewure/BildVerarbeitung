package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIHelper;
import imageanalyzer.util.JAIOperators;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
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
        image.setDrawable(JAI.create(
            JAIOperators.THRESHOLD.getOperatorValue(),
            pb
        ));

        JAIHelper.saveImage(image.getDrawable(), FILTER_NAME);
    }

    private ParameterBlock prepareParameterBlock(JAIDrawable image, double[]... parameters) {
        ParameterBlock pb = new ParameterBlock();

        for (double[] parameterGroup : parameters) {
            pb.add(parameterGroup);
        }
        return pb.addSource(image.getDrawable());
    }
}
