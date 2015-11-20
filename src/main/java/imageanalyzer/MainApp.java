package imageanalyzer;

import imageanalyzer.datacontainers.Coordinate;
import imageanalyzer.datacontainers.JAIDrawable;
import imageanalyzer.datasources.JAIDrawableSource;
import imageanalyzer.filters.*;
import imageanalyzer.sinks.FileSink;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;
import thirdparty.pipes.BufferedSyncPipe;

import javax.media.jai.PlanarImage;
import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.List;

public class MainApp {
    private static final int BUFFER_SIZE = 4;
    private static final String IMAGE_FILE_PATH = "loetstellen.jpg";
    private static final String RESULT_FILE_PATH = "result.txt";

    private static final List<Coordinate> SOLDERING_PLACES;

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

    private static long _overallElapsedTime;


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

        /* Extracting PlanarImage from JAIDrawable container */
        JAIDrawableAdapterFilter af = new JAIDrawableAdapterFilter(inf);

        /* Calculating centroids */
        CalcCentroidsFilter ccf = new CalcCentroidsFilter(af);

        /* Writing analysis results to the file */
        activate(new FileSink(
            ccf,
            SOLDERING_PLACES,
            new File(RESULT_FILE_PATH)
        ));
    }

    private static void runPushTaskA() {
        /* Writing analysis results to the file */
        FileSink fs = new FileSink(
            SOLDERING_PLACES,
            new File(RESULT_FILE_PATH)
        );

        /* Calculating centroids */
        CalcCentroidsFilter ccf = new CalcCentroidsFilter(fs);

        /* Extracting PlanarImage from JAIDrawable container */
        JAIDrawableAdapterFilter af = new JAIDrawableAdapterFilter(ccf);

        /* Inversion of the result all black -> white, all white -> black */
        InversionFilter inf = new InversionFilter(af);

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
        activate(new JAIDrawableSource(rf, IMAGE_FILE_PATH));
    }

    private static void runTaskB() {
        BufferedSyncPipe<JAIDrawable> sourcePipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> roiPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> thresholdPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> medianPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> openingPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> blackWhitePipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<JAIDrawable> inversePipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<PlanarImage> adapterPipe = new BufferedSyncPipe<>(BUFFER_SIZE);
        BufferedSyncPipe<List<Coordinate>> coordinatesPipe = new BufferedSyncPipe<>(BUFFER_SIZE);


        /* Loading the data */
        activate(new JAIDrawableSource(sourcePipe , IMAGE_FILE_PATH));

        /* Region Of Interest */
        activate(new ROIFilter(
            sourcePipe,
            roiPipe,
            new Rectangle(0, 35, 448, 105)
        ));

        /* Threshold all black elements to white */
        activate(new ThresholdFilter(
            roiPipe,
            thresholdPipe,
            new double[] {0},
            new double[] {25},
            new double[] {255}
        ));

        /* Median to eliminate noise */
        activate(new MedianFilter(
            thresholdPipe,
            medianPipe,
            8
        ));

        /* Mark circle-like regions (soldering places) */
        activate(new OpeningFilter(
            medianPipe,
            openingPipe
        ));

        /* Threshold Filter all except soldering places to black */
        activate(new ThresholdFilter(
            openingPipe,
            blackWhitePipe,
            new double[] {0},
            new double[] {250},
            new double[] {0}
        ));

        /* Inversion of the result all black -> white, all white -> black */
        activate(new InversionFilter(
            blackWhitePipe,
            inversePipe
        ));

        /* Extracting PlanarImage from JAIDrawable container */
        activate(new JAIDrawableAdapterFilter(
            inversePipe,
            adapterPipe
        ));

        /* Calculating centroids */
        activate(new CalcCentroidsFilter(
            adapterPipe,
            coordinatesPipe
        ));

        /* Writing analysis results to the file */
        activate(new FileSink(
            coordinatesPipe,
            SOLDERING_PLACES,
            new File(RESULT_FILE_PATH)
        ));
    }

    private static void activate(Runnable runnable) {
        new Thread(() -> {
            runnable.run();

            Long elapsedTime = ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() / 1000000; //ms.
            _overallElapsedTime += elapsedTime;

            System.out.print(
                "Time elapsed for THIS task: " + elapsedTime + "ms.\r\n" +
                "Time elapsed for ALL tasks: " + _overallElapsedTime + "ms."
            );
        }).start();
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
