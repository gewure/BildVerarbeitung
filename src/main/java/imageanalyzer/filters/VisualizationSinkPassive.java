package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.function.Consumer;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class VisualizationSinkPassive implements Writable<JAIDrawable> {
    private final Consumer<PlanarImage> _visualisationConsumer;

    public VisualizationSinkPassive(Consumer<PlanarImage> visualisationConsumer)
    throws InvalidParameterException {
        _visualisationConsumer = visualisationConsumer;
    }

    @Override
    public void write(JAIDrawable value) throws StreamCorruptedException {
        if (_visualisationConsumer != null) {
            _visualisationConsumer.accept(value.getDrawable());
        } else {
            throw new NullPointerException("visualisation consumer is not registered");
        }
    }
}
