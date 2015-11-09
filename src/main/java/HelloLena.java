import java.awt.*;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.*;
import javax.swing.*;
import com.sun.media.jai.widget.DisplayJAI;

public class HelloLena
{
 public static void main(String[] args)
 {
// Load the image which file name was passed as the first argument to
// the application.
   float[] kernelMatrix;
   KernelJAI kernel = null;
   kernelMatrix = new float[]    {0, -1, 0, 
			  -1, 8, -1,
                                             0, -1, 0 };

   kernel = new KernelJAI(3, 3, kernelMatrix);
	  
   PlanarImage image = JAI.create("fileload", "C:\\lehre\\ml\\hastieBook\\lena512.jpg");
 
// Get some information about the image
   String imageInfo =
   "Dimensions: "+image.getWidth()+"x"+image.getHeight()+ " Bands:"+image.getNumBands();

// Create a frame for display.
   JFrame frame = new JFrame();
   frame.setTitle("DisplayJAI: lena512.jpg");

// Get the JFrame’ ContentPane.
   Container contentPane = frame.getContentPane();
   contentPane.setLayout(new BorderLayout());

// prepare the parameters for a filter operation with the mask "kernelmatrix"
   ParameterBlock pb = new ParameterBlock();
   pb.addSource(image);
   pb.add(kernel);
 
// apply a filter operation with the mask "kernelmatrix"
   image = JAI.create("convolve", pb);

// Create an instance of DisplayJAI.
   DisplayJAI dj = new DisplayJAI(image);
 

// Add to the JFrame’ ContentPane an instance of JScrollPane
// containing the DisplayJAI instance.
   contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
 
// Add a text label with the image information.
   contentPane.add(new JLabel(imageInfo),BorderLayout.SOUTH);

// Set the closing operation so the application is finished.   
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   frame.setSize(400,200); // adjust the frame size.
   frame.setVisible(true); // show the frame.
 }
 }