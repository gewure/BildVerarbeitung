package imageanalyzer.datacontainers;

import javax.media.jai.PlanarImage;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class JAIDrawable extends GenericDrawable<PlanarImage> {
    public JAIDrawable(PlanarImage drawable) {
        super(drawable);
    }
}
