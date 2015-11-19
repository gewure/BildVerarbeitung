package imageanalyzer;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.datacontainers.ImageVisualizer;
import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.filters.*;
import imageanalyzer.pipes.ImageSourcePipe;
import thirdparty.interfaces.Readable;
import thirdparty.pipes.BufferedSyncPipe;

import java.awt.*;
import java.io.File;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public class MainApp {

    private static final int BUFFER_SIZE = 1;
    private static final String IMAGE_FILE_PATH = "loetstellen.jpg";
    private static final String RESULT_TXT = "result.txt";

    /* the target-coordinates of the 'centroids' in the image */
    ArrayList<Coordinate> targetCentroids = new ArrayList<Coordinate>() {{
        add(new Coordinate(8, 80));
         add(new Coordinate(72, 80));
        add(new Coordinate(137, 83));
        add(new Coordinate(203, 84));
        add(new Coordinate(266, 85));
        add(new Coordinate(329, 85));
        add(new Coordinate(396, 85));
    }};

    /**
     * pull strategy
     */
    public void taskApull() {

        thirdparty.interfaces.Readable<JAIDrawable> sourcePipe = new ImageSourcePipe(IMAGE_FILE_PATH);
        BufferedSyncPipe<JAIDrawable> roiPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> thresholdPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> medianPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> openingPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> blackWhitePipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> centroidsPipe = new BufferedSyncPipe<>(BUFFER_SIZE);

        /* Region Of Interest*/
        new ROIFilter(
                sourcePipe,
                roiPipe,
                new Rectangle(0, 35, 448, 105)
        );

        /* Threshold */
        new ThresholdFilter(
                roiPipe,
                thresholdPipe,
                new double[]{0},
                new double[]{25},
                new double[]{255}
        );

        /* Median to eleminate black pixels */
        new MedianFilter(
                thresholdPipe,
                medianPipe,
                8
        );

        /* identify loetstellen */
        new OpeningFilter(
                medianPipe,
                openingPipe
        );

        /* Threshold Filter again and Inversion to eleminate everything else around our loetstellen */
        new ThresholdFilter(
                openingPipe,
                blackWhitePipe,
                new double[]{0},
                new double[]{250},
                new double[]{0}
        );

        /* invert */
        new InversionFilter(
                blackWhitePipe,
                centroidsPipe
        );

        /* visualize it */
        VisualizationFilter vf = new VisualizationFilter(
                (Readable<JAIDrawable>) centroidsPipe,
                ImageVisualizer::displayImage
        );

        /* file where the Differences between the should-and-is Coordinates is written*/
        File resultTxt = new File(RESULT_TXT);

        /* get the Centroids TODO*/
        //CalcCentroidsFilter calcCentroidsFilter = new CalcCentroidsFilter(planImageToCalcFrom, new ActiveFileSink(resultTxt));

        //TODO
//        new CheckResultsSink(
//                centroidsPipe,
//                resultTxt
//        );

        try {
            vf.read();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * push strategy
     */
    public void taskApush() {
        //TODO
    }

    /**
     * multithreaded
     */
    public void taskB() {
        //Threaded!
    }

    @Deprecated
    public static void main(String[] args) {
        MainApp mainApp= new MainApp();

        if (args[0]=="push")
            mainApp.taskApush();

        if(args[0]=="pull")
            mainApp.taskApull();

        if(args[0]=="threaded")
            mainApp.taskB();

    }
}
