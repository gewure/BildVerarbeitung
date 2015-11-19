package imageanalyzer.util;

/**
 * Created by f00 on 19.11.15.
 */
public abstract class ActiveData implements Runnable {

    private boolean _isRunning;

    @Override
    public void run() {
        _isRunning = true;
        while(_isRunning) {
            process();
        }
    }

    public void stop() {
        _isRunning = false;
    }

    public abstract void process();
}