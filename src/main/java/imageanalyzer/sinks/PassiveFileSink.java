package imageanalyzer.sinks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;


/**
 * Created by f00 on 19.11.15.
 */
    public class PassiveFileSink implements PassiveSink<String> {

        private FileWriter fileWriter;
        private File f;

    /**
     * Constructor
     * @param file
     */
        public PassiveFileSink(File file) {
            this.f = file;

            if(file.exists()) {
                this.f.delete();
            }

            try {

                this.f.createNewFile();
                fileWriter = new FileWriter(this.f);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void write(String value) throws StreamCorruptedException {
            saveString(value);
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

