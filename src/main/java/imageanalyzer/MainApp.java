package imageanalyzer;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;

public class MainApp {

    public static void main(String[] args) {


        /*********** 1. das Bild laden und visualisieren */

        PlanarImage image = JAI.create("fileload", "src/main/resources/loetstellen.jpg");

        /*********** 2. eine ROI (region of interest1) definieren */
        /* Achtung: man muß nicht den komplizierten ROI Operator in JAI benutzen, sondern kann das Ganze
        ganz einfach so realisieren: */

        Rectangle rectangle = new Rectangle(int x, int y, int width, int height); //
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

        /*********** 3. einen Operator zur Bildsegmentierung auswählen: Threshold Operator /*

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
