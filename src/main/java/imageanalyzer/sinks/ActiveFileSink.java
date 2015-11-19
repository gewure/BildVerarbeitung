package imageanalyzer.sinks;

import imageanalyzer.util.EOFException;
import thirdparty.interfaces.Readable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;

/**
 * Created by f00 on 19.11.15.
 */
public class ActiveFileSink extends ActiveSink<String> implements Runnable {

    private FileWriter fileWriter;
    private File f;

    /**
     * Constructor
     * @param file
     * @param pre
     */
    public ActiveFileSink(File file, Readable<String> pre) {
        super(pre);

        f = file;

        if (file.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
            fileWriter = new FileWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process() {

        try {
            String value = read();
            saveString(value);
            stop();
        } catch (StreamCorruptedException | EOFException e) {
            System.out.println(e.getMessage());
            stop();
        }
    }

    /**
     *
     * @param line
     */
    private void saveString(String line) {
        System.out.println("save results as .txt: " + f.getName());

        try {

            fileWriter.write(line + "\r\n");
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}