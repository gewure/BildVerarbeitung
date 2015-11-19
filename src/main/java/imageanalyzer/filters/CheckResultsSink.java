package imageanalyzer.filters;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.sinks.ActiveFileSink;
import thirdparty.interfaces.Readable;

import java.io.File;
import java.util.List;

/**
 * Created by f00 on 16.11.15.
 * active-Filter: pulls data himself
 */
public class CheckResultsSink extends ActiveFileSink {

    List<Coordinate> targetCentroids;
    List<Coordinate> differences;

    /**
     * Constructor
     *
     * @param file
     * @param pre
     */
    public CheckResultsSink(File file, Readable<String> pre, List<Coordinate> centroids) {
        super(file, pre);
       // this.targetCentroids = centroids;
    }

//    public CheckResultsSink(Readable<JAIDrawable> input) throws InvalidParameterException {
//        super(input);
//    }
//
//    public CheckResultsSink(Readable<JAIDrawable> input, Writable<File> output, List<Coordinate>centroids) throws InvalidParameterException {
//        super(input, output);
//    }
//
//    public CheckResultsSink(Writable<JAIDrawable> output) throws InvalidParameterException {
//        super(output);
//    }

    protected void process(Readable entity) {
       for(int i = 0; i< targetCentroids.size(); i++)
            differences.add(compareCoordinates(targetCentroids.get(i),i));
    }

    /**
     * Compares one by one Coordinate and prints the difference
     *
     * @param co coordinate
     * @param i index to loop
     *
     * @return Difference-Coordinate
     */
    private Coordinate compareCoordinates(Coordinate co, int i) {
        int diffX = Math.abs(targetCentroids.get(i)._x - co._x);
        int diffY = Math.abs(targetCentroids.get(i)._y - co._y);

        return new Coordinate(diffX, diffY);
    }


}
