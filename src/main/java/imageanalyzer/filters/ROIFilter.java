package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.util.JAIHelper;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class ROIFilter extends DataTransformationFilter<JAIDrawable> {

    private static final String FILTER_NAME = "roi";

    private final Rectangle _roi;

    public ROIFilter(Readable<JAIDrawable> input, Writable<JAIDrawable> output , Rectangle roi)
    throws InvalidParameterException {
        super(input, output);
        _roi = roi;
    }

    public ROIFilter(Readable<JAIDrawable> input, Rectangle roi)
    throws InvalidParameterException {
        super(input);
        _roi = roi;
    }

    public ROIFilter(Writable<JAIDrawable> output, Rectangle roi)
    throws InvalidParameterException {
        super(output);
        _roi = roi;
    }

    @Override
    protected void process(JAIDrawable image) {
        image.setDrawable(PlanarImage.wrapRenderedImage(
            image.getDrawable().getAsBufferedImage(
                _roi,
                image.getDrawable().getColorModel()
            )
        ));

        JAIHelper.saveImage(image.getDrawable(), FILTER_NAME);
    }
}
