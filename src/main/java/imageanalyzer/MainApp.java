package imageanalyzer;

import imageanalyzer.datacontainers.ImageVisualizer;
import imageanalyzer.filters.VisualizationFilter;
import imageanalyzer.pipes.ImageSourcePipe;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;

public class MainApp {

    private static final String IMAGE_FILE_PATH = "loetstellen.jpg";

    public static void main(String[] args) {

        new Thread(
                new VisualizationFilter(ImageVisualizer::displayImage, new ImageSourcePipe(IMAGE_FILE_PATH))
        ).start();


        /*********** 1. das Bild laden und visualisieren */

        PlanarImage image = JAI.create("fileload", "src/main/resources/loetstellen.jpg");


        /*********** 2. eine ROI (region of interest1) definieren */
        /* Achtung: man muß nicht den komplizierten ROI Operator in JAI benutzen, sondern kann das Ganze
        ganz einfach so realisieren: */



        Rectangle rectangle = new Rectangle(0, 35, 448, 105);
        // linkes oberes eck: 0, 35
        // rechtes oberes eck: 448, 35
        // linkes unteres eck: 0, 140
        // rechtes untere eck: 448, 140

         image = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage(rectangle,
                image.getColorModel()));

        /* wobei image ein PlanarImage ist, und rectangle ein java.awt.Rectangle ist, das die ROI angibt relativ
        zum Bild (rectangle = new Rectangle(int x, int y, int width, int height)). Achtung: Pixel oben links
        usgeschnittenen Bildes hat wieder die Koordinaten 0,0. Sie müssen noch etwas tun, damit Sie die
        Position dieses Pixels im Originalbild mitspeichern im Bild (der letzte Filter braucht das!)
        Option für den Benutzer: zeige das Rechteck in weiss mit dem Ausgangsbild */

        /*********** 3. einen Operator zur Bildsegmentierung auswählen: Threshold Operator */
       float[] kernelMatrix;

        KernelJAI kernel = null;
        kernelMatrix = new float[]{0, -1, 0, -1, 8, -1, 0, -1, 0};
        kernel = new KernelJAI(3, 3, kernelMatrix);

        ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        pb.add(kernel);
        /*********** 3a. Parameterwerte des Operators wählen */

        /*********** 4. beseitige lokale Störungen (z.B. schwarzer Fleck im 2. Anschluss von rechts) */

        /*********** 4a.wähle Parameter des Filters: Größe der Maske zur Medianberechnung */

        /*********** 5. nun bleiben noch die Kabelanschlüsse der „balls“; man nutzt die Kreisform der Balls aus und
        benutzt einen Opening-Operator mit kreisförmiger Maske (in JAI: "erode" und „dilate“): */

        /**********  5a. wähle Parameter des Operators: Größe der Maske (Alternative: laufe mehrmals mit dem Operator
        über das Bild) */

        /********** 6.Resultatbild (ein Bild, in dem nur die „balls“ als Scheiben zu sehen sind.)in einer Datei abspeichern,
        aber nicht als Sink realisieren, sondern nach der Abspeicherung das unveränderte Bild weiterleiten. */

        /********** 7.ie Scheiben zählen, ihre Zentren (Centroid, siehe unten) bestimmen, und prüfen, ob sie im
         Toleranzbereich der Qualitätskontrolle liegen. Letztere Information wird bei Erzeugung des Filters im
         "main" als Initialisierungsdaten an das Filterobjekt übergeben. Resultat in eine Datei schreiben. */
        // -> CalcCentroidsFilter.java(PlanarImage, LinkedList<Coordinate>

        File result = new File("result.txt"); // result for Task A)

        // Im letzten Filter sollen die Mittelpunkte der
        //"balls" bestimmt, mit Sollwerten verglichen, und das Resultat in einer einfachen Textdatei
        //abgespeichert werden

        /* THREADMODEL  */
//        Thread aThread = new Thread({
//                PipedInputStream pis = new PipedInputStream();
//
//        }
    }
}
