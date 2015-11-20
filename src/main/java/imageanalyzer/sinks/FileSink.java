package imageanalyzer.sinks;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.sinks.generic.ActiveSink;
import thirdparty.interfaces.Readable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by f00 on 16.11.15.
 */
public class FileSink extends ActiveSink<List<Coordinate>> {

    private List<Coordinate> _shouldSolderingPlaces;
    private BufferedWriter _fileWriter;
    private Integer _accuracy;

    public FileSink(Integer accuracy, List<Coordinate> solderingPlaces, String filePath) {
        this(null, accuracy, solderingPlaces, filePath);
    }

    public FileSink(Readable<List<Coordinate>> readable, Integer accuracy, List<Coordinate> solderingPlaces, String filePath) {
        super(readable);

        _accuracy = accuracy;
        _shouldSolderingPlaces = solderingPlaces;

        try {

            _fileWriter = new BufferedWriter(Files.newBufferedWriter(Paths.get(filePath)));
            System.out.println("Saving results in: " + filePath);

            _fileWriter.write("Registered accuracy: " + accuracy + "\r\n\r\n");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void write(List<Coordinate> isSolderingPlaces) throws StreamCorruptedException {
        try {

            if (_shouldSolderingPlaces != null && isSolderingPlaces != null && !isSolderingPlaces.isEmpty()) {

                for (int i = 0; i < _shouldSolderingPlaces.size(); ++i) {

                    Coordinate should = _shouldSolderingPlaces.get(i);

                    if(i < isSolderingPlaces.size()) {
                        Coordinate is = isSolderingPlaces.get(i);

                        if (isInAccuracyRange(is, should)) {
                            _fileWriter.write("PASSED. Soldering place #" + i + " is in the accuracy range.\r\n");
                        } else {
                            _fileWriter.write("FAILED. Soldering place #" + i + " is out of the accuracy range.\r\n");
                        }

                        _fileWriter.write(getCoordinatesString(is, should));

                    } else {
                        _fileWriter.write("FAILED. Soldering place #" + i + " was not detected.\r\n\r\n");
                    }
                }

                _fileWriter.flush();

            } else if (_fileWriter != null){

                if (isSolderingPlaces != null && isSolderingPlaces.isEmpty()) {
                    _fileWriter.write(
                        "INFO. Empty coordinates list received. It is ok for the end of the stream.\r\n\r\n"
                    );
                }

                _fileWriter.close();
                _fileWriter = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StreamCorruptedException(e.getMessage());
        }
    }

    /**
     * Compares one two Coordinates.
     *
     * @param is calculated coordinate.
     * @param should expected value for this coordinate.
     *
     * @return true if 'is coordinate' present inside or on accuracy border,
     *         false if it lays elsewhere.
     */
    private boolean isInAccuracyRange(Coordinate is, Coordinate should) {
        boolean isInRangeX = Math.abs(should._x - is._x) <= _accuracy;
        boolean isInRangeY = Math.abs(should._y - is._y) <= _accuracy;

        return isInRangeX && isInRangeY;
    }

    private String getCoordinatesString(Coordinate is, Coordinate should) {
        return "INFO. Expected [" + should._x + ", " + should._y + "] | Received [" + is._x + ", " + is._y + "]\r\n\r\n";
    }
}
