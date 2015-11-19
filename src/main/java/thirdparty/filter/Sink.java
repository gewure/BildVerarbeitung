package thirdparty.filter;

import thirdparty.interfaces.Readable;

public abstract class Sink<T> implements Readable<T>, Runnable{
    public abstract void run();
}
