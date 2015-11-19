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

    private static void runPullTaskA() {
        /* Loading the data */
        SourcePassive sp = new SourcePassive(IMAGE_FILE_PATH);

        /* Region Of Interest */
        ROIFilter rf = new ROIFilter(
            sp,
            new Rectangle(0, 35, 448, 105)
        );

        /* Threshold all black elements to white */
        ThresholdFilter tf = new ThresholdFilter(
            (Readable<JAIDrawable>) rf,
            new double[] {0},
            new double[] {25},
            new double[] {255}
        );

        /* Median to eliminate noise */
        MedianFilter mf = new MedianFilter(
            (Readable<JAIDrawable>) tf,
            8
        );

        /* Mark circle-like regions (soldering places) */
        OpeningFilter of = new OpeningFilter(
            (Readable<JAIDrawable>) mf
        );

        /* Threshold Filter all except soldering places to black */
        ThresholdFilter bwf = new ThresholdFilter(
            (Readable<JAIDrawable>) of,
            new double[] {0},
            new double[] {250},
            new double[] {0}
        );

        /* Inversion of the result all black -> white, all white -> black */
        InversionFilter inf = new InversionFilter((Readable<JAIDrawable>) bwf);

        /* Showing the result */
        new Thread(
            new VisualizationSinkActive(inf, ImageVisualizer::displayImage)
        ).start();
    }

    private static void runPushTaskA() {
        /* Showing the result */
        VisualizationSinkPassive vf = new VisualizationSinkPassive(ImageVisualizer::displayImage);

        /* Inversion of the result all black -> white, all white -> black */
        InversionFilter inf = new InversionFilter(vf);

        /* Threshold Filter all except soldering places to black */
        ThresholdFilter bwf = new ThresholdFilter(
            (Writable<JAIDrawable>) inf,
            new double[] {0},
            new double[] {250},
            new double[] {0}
        );

        /* Mark circle-like regions (soldering places) */
        OpeningFilter of = new OpeningFilter(
            (Writable<JAIDrawable>) bwf
        );

        /* Median to eliminate noise */
        MedianFilter mf = new MedianFilter(
            (Writable<JAIDrawable>) of,
            8
        );

        /* Threshold all black elements to white */
        ThresholdFilter tf = new ThresholdFilter(
            (Writable<JAIDrawable>) mf,
            new double[] {0},
            new double[] {25},
            new double[] {255}
        );

        /* Region Of Interest */
        ROIFilter rf = new ROIFilter(
            (Writable<JAIDrawable>) tf,
            new Rectangle(0, 35, 448, 105)
        );

        /* Loading the data */
        new Thread(
            new SourceActive(rf, IMAGE_FILE_PATH)
        ).start();
    }

    private static void taskB() {
        //Threaded!
    }

    @Deprecated
    public static void main(String[] args) {

        /* the should-coordinates of the soldering places */
        LinkedList<Coordinate> solderingPlaces = new LinkedList<>();
        solderingPlaces.add(new Coordinate(8, 80));
        solderingPlaces.add(new Coordinate(72, 80));
        solderingPlaces.add(new Coordinate(137, 83));
        solderingPlaces.add(new Coordinate(203, 84));
        solderingPlaces.add(new Coordinate(266, 85));
        solderingPlaces.add(new Coordinate(329, 85));
        solderingPlaces.add(new Coordinate(396, 85));

        runPushTaskA();
    }
}
