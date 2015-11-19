package imageanalyzer.filters;

import imageanalyzer.datacontainers.JAIDrawable;
import thirdparty.filter.DataTransformationFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import java.security.InvalidParameterException;

/**
 * Created by f00 on 16.11.15.
 * active-Filter: pulls data himself
 */
public class CheckResultsSink extends DataTransformationFilter <JAIDrawable> {


    public CheckResultsSink(Readable<JAIDrawable> input) throws InvalidParameterException {
        super(input);
    }

    public CheckResultsSink(Readable<JAIDrawable> input, Writable<JAIDrawable> output) throws InvalidParameterException {
        super(input, output);
    }

    public CheckResultsSink(Writable<JAIDrawable> output) throws InvalidParameterException {
        super(output);
    }

    @Override
    protected void process(JAIDrawable entity) {

    }
}
