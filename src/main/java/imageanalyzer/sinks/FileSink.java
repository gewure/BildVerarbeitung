package imageanalyzer.sinks;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.sinks.generic.ActiveSink;
import thirdparty.interfaces.Readable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.List;

/**
 * Created by f00 on 16.11.15.
 * active-Filter: pulls data himself
 */
public class FileSink extends ActiveSink<List<Coordinate>> {

    private List<Coordinate> _targetCentroids;
    private FileWriter _fileWriter;

    public FileSink(List<Coordinate> centroids, File file) {
        this(null, centroids, file);
    }

    public FileSink(Readable<List<Coordinate>> readable, List<Coordinate> solderingPlaces, File file) {
        super(readable);

        try {
            if (file.exists()) file.delete();
            if (file.createNewFile()) {
                _fileWriter = new FileWriter(file);
                System.out.println("Save results as .txt: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void write(List<Coordinate> value) throws StreamCorruptedException {
        try {

            if (value != null) {

                for(int i = 0; i < _targetCentroids.size(); ++i) {
                    _fileWriter.write(compareCoordinates(value.get(i), i) + "\r\n");
                }

            } else {
                _fileWriter.close();
            }
        } catch (IOException e) {
            throw new StreamCorruptedException(e.getMessage());
        }
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
        int diffX = Math.abs(_targetCentroids.get(i)._x - co._x);
        int diffY = Math.abs(_targetCentroids.get(i)._y - co._y);

        return new Coordinate(diffX, diffY);
    }
}
