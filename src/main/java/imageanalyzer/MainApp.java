package imageanalyzer;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.datacontainers.ImageVisualizer;
import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.filters.*;
import imageanalyzer.filters.SourcePassive;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;
import imageanalyzer.pipes.Pipe;

import java.awt.*;
import java.io.File;
import java.io.StreamCorruptedException;
import java.util.LinkedList;


public class MainApp {

    private static final int BUFFER_SIZE = 1;
    private static final String IMAGE_FILE_PATH = "loetstellen.jpg";
    private static final String RESULT_TXT = "result.txt";

    public void taskApull() {
        //TODO
    }

    public void taskApush() {
        //TODO
    }

    public void taskB() {
        //Threaded!
    }

    @Deprecated
    public static void main(String[] args) {

        /* the should-coordinates of the 'centroids' */
        LinkedList<Coordinate> centroids = new LinkedList<>();
        centroids.add(new Coordinate(8, 80));
        centroids.add(new Coordinate(72, 80));
        centroids.add(new Coordinate(137, 83));
        centroids.add(new Coordinate(203, 84));
        centroids.add(new Coordinate(266, 85));
        centroids.add(new Coordinate(329, 85));
        centroids.add(new Coordinate(396, 85));

        Readable<JAIDrawable> sourcePipe = new SourcePassive(IMAGE_FILE_PATH);

        /* Region Of Interest*/
        ROIFilter rf = new ROIFilter(
            (Readable<JAIDrawable>) sourcePipe,
            new Rectangle(0, 35, 448, 105)
        );

        Pipe<JAIDrawable> roiPipe = new Pipe<>((Writable<JAIDrawable>) rf);

        /* Threshold */
        ThresholdFilter tf = new ThresholdFilter(
            (Readable<JAIDrawable>) roiPipe,
            new double[] {0},
            new double[] {25},
            new double[] {255}
        );

        Pipe<JAIDrawable> thresholdPipe = new Pipe<>((Writable<JAIDrawable>) tf);

        /* Median to eleminate black pixels */
        MedianFilter mf = new MedianFilter(
            (Readable<JAIDrawable>) thresholdPipe,
            8
        );

        Pipe<JAIDrawable> medianPipe = new Pipe<>((Writable<JAIDrawable>) mf);

        /* identify soldering places */
        OpeningFilter of = new OpeningFilter(
            (Readable<JAIDrawable>) medianPipe
        );

        Pipe<JAIDrawable> openingPipe = new Pipe<>((Writable<JAIDrawable>) of);

        /* Threshold Filter again all except coldering places to black*/
        ThresholdFilter bwf = new ThresholdFilter(
            (Readable<JAIDrawable>) openingPipe,
            new double[] {0},
            new double[] {250},
            new double[] {0}
        );

        Pipe<JAIDrawable> blackWhitePipe = new Pipe<>((Writable<JAIDrawable>) bwf);

        /* Inversion of the result all black -> white, all white -> black */
        InversionFilter inf = new InversionFilter(
            (Readable<JAIDrawable>) blackWhitePipe
        );

        Pipe<JAIDrawable> visualisationPipe = new Pipe<>((Writable<JAIDrawable>) inf);

        VisualizationFilter vf = new VisualizationSinkActive(
            (Readable<JAIDrawable>) visualisationPipe,
            ImageVisualizer::displayImage
        );

        /* TODO write the difference between should and is into a file */
        File resultTxt = new File(RESULT_TXT);
        new CheckResultsSink(
                centroidsPipe,
                resultTxt
        );
//        new Thread(
//            new CalcCentroidsFilter(blackWhitePipe.)
//        ).start();

    }
}
