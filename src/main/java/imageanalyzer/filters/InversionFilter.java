package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIHelper;
import imageanalyzer.util.JAIOperators;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.JAI;
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
        //inverting colors on the picture

        image.setDrawable(JAI.create(
            JAIOperators.INVERT.getOperatorValue(),
            image.getDrawable()
        ));

        JAIHelper.saveImage(image.getDrawable(), FILTER_NAME);
    }
}
