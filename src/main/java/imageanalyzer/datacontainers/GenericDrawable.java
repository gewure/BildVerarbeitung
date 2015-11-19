package imageanalyzer.datacontainers;

/**
 * Created by sereGkaluv on 15-Nov-15.
 */
public class GenericDrawable<T> implements IDrawable<T> {
    private T _drawable;

    public GenericDrawable(T drawable) {
        _drawable = drawable;
    }

    @Override
    public T getDrawable() {
        return _drawable;
    }

    @Override
    public void setDrawable(T drawable) {
        _drawable = drawable;
    }
}
