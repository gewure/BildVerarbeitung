package imageanalyzer.sinks;

import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.sinks.generic.ActiveSink;
import thirdparty.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.io.StreamCorruptedException;
import java.util.function.Consumer;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class VisualizationSink extends ActiveSink<JAIDrawable> {
    private final Consumer<PlanarImage> _visualisationConsumer;

    public VisualizationSink(Consumer<PlanarImage> visualisationConsumer) {
        super();
        _visualisationConsumer = visualisationConsumer;
    }

    public VisualizationSink(Readable<JAIDrawable> readable, Consumer<PlanarImage> visualisationConsumer) {
        super(readable);
        _visualisationConsumer = visualisationConsumer;
    }

    @Override
    public void write(JAIDrawable value) throws StreamCorruptedException {
        if (value != null && _visualisationConsumer != null) {
            _visualisationConsumer.accept(value.getDrawable());
        }
    }
}
