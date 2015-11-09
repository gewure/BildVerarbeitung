package imageanalyzer.filters;

import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;
import java.util.function.Consumer;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class VisualizationFilter extends DataTransformationFilter<PlanarImage> {

    private final Consumer<PlanarImage> _visualisationConsumer;

    public VisualizationFilter(Consumer<PlanarImage> visualisationConsumer, Readable<PlanarImage> input)
    throws InvalidParameterException {
        super(input);
        _visualisationConsumer = visualisationConsumer;
    }

    public VisualizationFilter(Consumer<PlanarImage> visualisationConsumer, Writable<PlanarImage> output)
    throws InvalidParameterException {
        super(output);
        _visualisationConsumer = visualisationConsumer;
    }

    @Override
    protected void process(PlanarImage entity) {
        if (_visualisationConsumer != null) _visualisationConsumer.accept(entity);
    }
}
