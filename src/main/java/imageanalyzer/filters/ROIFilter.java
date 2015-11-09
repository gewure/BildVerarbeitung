package imageanalyzer.filters;

import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.security.InvalidParameterException;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class ROIFilter extends DataTransformationFilter<PlanarImage> {

    public ROIFilter(Readable<PlanarImage> input) throws InvalidParameterException {
        super(input);
    }

    public ROIFilter(Writable<PlanarImage> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(PlanarImage entity) {
        Rectangle rectangle = new Rectangle(0, 35, 448, 105);

        entity = PlanarImage.wrapRenderedImage(
            entity.getAsBufferedImage(rectangle, entity.getColorModel())
        );
    }
}
