package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import thirdparty.filter.DataEnrichmentFilter;
import thirdparty.interfaces.*;
import thirdparty.interfaces.Readable;

import javax.media.jai.PlanarImage;
import java.security.InvalidParameterException;

/**
 * Created by f00 on 20.11.15.
 * Passie/Active Adapter for CalcCentroidsFilter
 */
public class JAIDrawableAdapterFilter extends DataEnrichmentFilter<JAIDrawable, PlanarImage>  {

    public JAIDrawableAdapterFilter(Readable<JAIDrawable> input, Writable<PlanarImage> output)
    throws InvalidParameterException {
        super(input, output);
    }

    public JAIDrawableAdapterFilter(Readable<JAIDrawable> input)
    throws InvalidParameterException {
        super(input);
    }

    public JAIDrawableAdapterFilter(Writable<PlanarImage> output)
    throws InvalidParameterException {
        super(output);
    }

    /* say true if data has been processed, false if error*/
    @Override
    protected boolean fillEntity(JAIDrawable drawable, PlanarImage entity) {
        if(drawable!= null) entity = drawable.getDrawable();
        return true;
    }

    @Override
    protected PlanarImage getNewEntityObject() {
        return null;
    }
}
