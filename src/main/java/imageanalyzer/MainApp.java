package imageanalyzer;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.datacontainers.ImageVisualizer;
import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.filters.*;
import imageanalyzer.pipes.ImageSourcePipe;
import thirdparty.interfaces.Readable;
import thirdparty.pipes.BufferedSyncPipe;

import java.awt.*;
import java.io.StreamCorruptedException;
import java.util.LinkedList;


public class MainApp {

    private static final int BUFFER_SIZE = 1;
    private static final String IMAGE_FILE_PATH = "loetstellen.jpg";

    public static void main(String[] args) {

        LinkedList<Coordinate> centroids = new LinkedList<>();
        centroids.add(new Coordinate(5, 50)); //TODO


        Readable<JAIDrawable> sourcePipe = new ImageSourcePipe(IMAGE_FILE_PATH);
        BufferedSyncPipe<JAIDrawable> roiPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> thresholdPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> medianPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> openingPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> blackWhitePipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> centroidsPipe = new BufferedSyncPipe<>(BUFFER_SIZE);

        new ROIFilter(
            sourcePipe,
            roiPipe,
            new Rectangle(0, 35, 448, 105)
        );

        new ThresholdFilter(
            roiPipe,
            thresholdPipe,
            new double[] {0},
            new double[] {25},
            new double[] {255}
        );

        new MedianFilter(
            thresholdPipe,
            medianPipe,
            8
        );

        new OpeningFilter(
            medianPipe,
            openingPipe
        );

        new ThresholdFilter(
            openingPipe,
            blackWhitePipe,
            new double[] {0},
            new double[] {250},
            new double[] {0}
        );


        new InversionFilter(
            blackWhitePipe,
            centroidsPipe
        );


        VisualizationFilter vf = new VisualizationFilter(
            (Readable<JAIDrawable>) centroidsPipe,
            ImageVisualizer::displayImage
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
