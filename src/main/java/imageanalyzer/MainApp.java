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
import java.util.LinkedList;


public class MainApp {

    private static final int BUFFER_SIZE = 1;
    private static final String IMAGE_FILE_PATH = "loetstellen.jpg";

    public static void main(String[] args) {

        LinkedList<Coordinate> centroids = new LinkedList<>();
        centroids.add(new Coordinate(5, 50)); //TODO


        Readable<JAIDrawable> sourcePipe = new SourcePassive(IMAGE_FILE_PATH);


        ROIFilter rf = new ROIFilter(
            (Readable<JAIDrawable>) sourcePipe,
            new Rectangle(0, 35, 448, 105)
        );

        Pipe<JAIDrawable> roiPipe = new Pipe<>((Writable<JAIDrawable>) rf);

        ThresholdFilter tf = new ThresholdFilter(
            (Readable<JAIDrawable>) roiPipe,
            new double[] {0},
            new double[] {25},
            new double[] {255}
        );

        Pipe<JAIDrawable> thresholdPipe = new Pipe<>((Writable<JAIDrawable>) tf);

        MedianFilter mf = new MedianFilter(
            (Readable<JAIDrawable>) thresholdPipe,
            8
        );

        Pipe<JAIDrawable> medianPipe = new Pipe<>((Writable<JAIDrawable>) mf);

        OpeningFilter of = new OpeningFilter(
            (Readable<JAIDrawable>) medianPipe
        );

        Pipe<JAIDrawable> openingPipe = new Pipe<>((Writable<JAIDrawable>) of);

        ThresholdFilter bwf = new ThresholdFilter(
            (Readable<JAIDrawable>) openingPipe,
            new double[] {0},
            new double[] {250},
            new double[] {0}
        );

        Pipe<JAIDrawable> blackWhitePipe = new Pipe<>((Writable<JAIDrawable>) bwf);

        InversionFilter inf = new InversionFilter(
            (Readable<JAIDrawable>) blackWhitePipe
        );


        new VisualizationSinkActive(ImageVisualizer::displayImage);

//        new Thread(
//            new CalcCentroidsFilter(blackWhitePipe.)
//        ).start();

    }
}
