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

    public static void main(String[] args) {

        /* the should-values of the 'centroids' */
        LinkedList<Coordinate> centroids = new LinkedList<>();
        centroids.add(new Coordinate(8, 80));
        centroids.add(new Coordinate(72, 80));
        centroids.add(new Coordinate(137, 83));
        centroids.add(new Coordinate(203, 84));
        centroids.add(new Coordinate(266, 85));
        centroids.add(new Coordinate(329, 85));
        centroids.add(new Coordinate(396, 85));


        Readable<JAIDrawable> sourcePipe = new ImageSourcePipe(IMAGE_FILE_PATH);
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

        /* Threshold Filter again and Inversion to eleminate every else around our loetstellen */
        new ThresholdFilter(
                openingPipe,
                blackWhitePipe,
                new double[]{0},
                new double[]{250},
                new double[]{0}
        );


        new InversionFilter(
                blackWhitePipe,
                centroidsPipe
        );


        /* visualize it! */
        VisualizationFilter vf = new VisualizationFilter(
                (Readable<JAIDrawable>) centroidsPipe,
                ImageVisualizer::displayImage
        );

        /* TODO count the Lötstellen, find the center and write the difference between should and is into a file */
        File resultTxt = new File(RESULT_TXT);
        new LoetstellenCounterFilter(
                centroidsPipe,
                resultTxt
        );

        try {
            vf.read();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }

//        new Thread(
//            new CalcCentroidsFilter(blackWhitePipe.)
//        ).start();


    }
}
