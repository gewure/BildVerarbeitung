package imageanalyzer.datacontainers;

import com.sun.media.jai.widget.DisplayJAI;

import javax.media.jai.PlanarImage;
import javax.swing.*;
import java.awt.*;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class ImageVisualizer {
    private static final String WINDOW_TITLE = "IMAGE VISUALIZER";

    private ImageVisualizer() {
    }

    public static void displayImage(PlanarImage image) {
        //Get some information about the image.
        String imageInfo = "Dimensions: " + image.getWidth() + "x" + image.getHeight() + " Bands:" + image.getNumBands();

        //Create a frame for display.
        JFrame frame = new JFrame();
        frame.setTitle(WINDOW_TITLE);

        //Get the JFrame ContentPane.
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        //Create an instance of DisplayJAI.
        DisplayJAI dj = new DisplayJAI(image);

        //Add to the JFrame ContentPane an instance of JScrollPane
        //containing the DisplayJAI instance.
        contentPane.add(new JScrollPane(dj), BorderLayout.CENTER);

        //Add a text label with the image information.
        contentPane.add(new JLabel(imageInfo), BorderLayout.SOUTH);

        //Set the closing operation so the application is finished.
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();

        frame.setResizable(false);
        frame.setVisible(true); //Show the frame.
    }
}
