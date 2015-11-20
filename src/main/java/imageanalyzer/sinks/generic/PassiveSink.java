package imageanalyzer.sinks.generic;

import thirdparty.interfaces.Writable;

/**
 * Created by sereGkaluv on 20-Nov-15.
 */
public abstract class PassiveSink<T> implements Writable<T> {
    protected PassiveSink() {
    }
}
