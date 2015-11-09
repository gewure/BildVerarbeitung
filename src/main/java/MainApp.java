
public class MainApp {

    public static void main(String[] args) {

        //TODO!
        //1. das Bild laden und visualisieren

        //2. eine ROI (region of interest1) definieren

        //3. einen Operator zur Bildsegmentierung auswählen: Threshold Operator

        //3a. Parameterwerte des Operators wählen

        //4. beseitige lokale Störungen (z.B. schwarzer Fleck im 2. Anschluss von rechts)

        //4a.wähle Parameter des Filters: Größe der Maske zur Medianberechnung

        //5. nun bleiben noch die Kabelanschlüsse der „balls“; man nutzt die Kreisform der Balls aus und
        //benutzt einen Opening-Operator mit kreisförmiger Maske (in JAI: "erode" und „dilate“):

        //5a. wähle Parameter des Operators: Größe der Maske (Alternative: laufe mehrmals mit dem Operator
        //über das Bild)

        //6.Resultatbild (ein Bild, in dem nur die „balls“ als Scheiben zu sehen sind.)in einer Datei abspeichern,
        // aber nicht als Sink realisieren, sondern nach der Abspeicherung das unveränderte Bild weiterleiten.

        //7.ie Scheiben zählen, ihre Zentren (Centroid, siehe unten) bestimmen, und prüfen, ob sie im
        // Toleranzbereich der Qualitätskontrolle liegen. Letztere Information wird bei Erzeugung des Filters im
        // "main" als Initialisierungsdaten an das Filterobjekt übergeben. Resultat in eine Datei schreiben.
    }
}
