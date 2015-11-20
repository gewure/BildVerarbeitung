package imageanalyzer;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.datacontainers.ImageVisualizer;
import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.datasources.JAIDrawableSource;
import imageanalyzer.filters.*;
import imageanalyzer.sinks.VisualizationSink;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import java.awt.*;
import java.util.LinkedList;

public class MainApp {
    private static final int BUFFER_SIZE = 1;
    private static final String IMAGE_FILE_PATH = "loetstellen.jpg";
    private static final String RESULT_TXT = "result.txt";

    private static final LinkedList<Coordinate> SOLDERING_PLACES;

    static {
        SOLDERING_PLACES = new LinkedList<>();

        /* the should-coordinates of the soldering places */
        SOLDERING_PLACES.add(new Coordinate(8, 80));
        SOLDERING_PLACES.add(new Coordinate(72, 80));
        SOLDERING_PLACES.add(new Coordinate(137, 83));
        SOLDERING_PLACES.add(new Coordinate(203, 84));
        SOLDERING_PLACES.add(new Coordinate(266, 85));
        SOLDERING_PLACES.add(new Coordinate(329, 85));
        SOLDERING_PLACES.add(new Coordinate(396, 85));
    }


    private static void runPullTaskA() {
        /* Loading the data */
        JAIDrawableSource sp = new JAIDrawableSource(IMAGE_FILE_PATH);

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
            new VisualizationSink(inf, ImageVisualizer::displayImage)
        ).start();
    }

    private static void runPushTaskA() {
        /* Showing the result */
        VisualizationSink vs = new VisualizationSink(ImageVisualizer::displayImage);

        /* Inversion of the result all black -> white, all white -> black */
        InversionFilter inf = new InversionFilter(vs);

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
            new JAIDrawableSource(rf, IMAGE_FILE_PATH)
        ).start();
    }

    private static void runTaskB() {
        //Threaded!
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0) {

            switch (args[0]) {
                case "push" : {
                    runPushTaskA();
                    return;
                }

                case "pull" : {
                    runPullTaskA();
                    return;
                }

                case "threaded" : {
                    runTaskB();
                    return;
                }
            }
        }
    }
}
