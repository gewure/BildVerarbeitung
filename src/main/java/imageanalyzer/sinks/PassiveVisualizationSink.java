package imageanalyzer.sinks;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.sinks.generic.PassiveSink;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.util.function.Consumer;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class PassiveVisualizationSink extends PassiveSink<JAIDrawable> {
    private final Consumer<PlanarImage> _visualisationConsumer;

    public PassiveVisualizationSink(Consumer<PlanarImage> visualisationConsumer) {
        super();
        _visualisationConsumer = visualisationConsumer;
    }

    @Override
    public void write(JAIDrawable value)
    throws StreamCorruptedException {

        if (_visualisationConsumer != null) {
            _visualisationConsumer.accept(value.getDrawable());
        } else {
            throw new StreamCorruptedException("visualisation consumer is not registered");
        }
    }
}
