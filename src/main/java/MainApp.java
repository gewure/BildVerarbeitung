import javax.media.jai.PlanarImage;
import java.awt.*;
import java.awt.Rectangle;
import java.io.PipedInputStream;
import java.lang.Thread;

public class MainApp {

    public static void main(String[] args) {

        /* way to go */
        /*********** 1. das Bild laden und visualisieren */

        /*********** 2. eine ROI (region of interest1) definieren */
        /* Achtung: man muß nicht den komplizierten ROI Operator in JAI benutzen, sondern kann das Ganze
        ganz einfach so realisieren: */
//
//        Rectangle rectangle = new Rectangle(int x, int y, int width, int height)
//
//        PlanarImage image = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage(rectangle,
//                image.getColorModel()));

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

        /* THREADMODEL  */
//        Thread aThread = new Thread({
//                PipedInputStream pis = new PipedInputStream();
//
//        }
    }
}
