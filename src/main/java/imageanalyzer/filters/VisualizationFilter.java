package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;
import java.util.function.Consumer;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class VisualizationFilter extends DataTransformationFilter<JAIDrawable> {

    private final Consumer<PlanarImage> _visualisationConsumer;

    public VisualizationFilter(Readable<JAIDrawable> input, Consumer<PlanarImage> visualisationConsumer)
    throws InvalidParameterException {
        super(input);
        _visualisationConsumer = visualisationConsumer;
    }

    public VisualizationFilter(Writable<JAIDrawable> output, Consumer<PlanarImage> visualisationConsumer)
    throws InvalidParameterException {
        super(output);
        _visualisationConsumer = visualisationConsumer;
    }

    @Override
    protected void process(JAIDrawable image) {
        if (_visualisationConsumer != null) _visualisationConsumer.accept(image.getDrawable());
    }
}
