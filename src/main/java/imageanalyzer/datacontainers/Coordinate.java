package imageanalyzer.datacontainers;

/**
 * Created by sereGkaluv on 09-Nov-15.
 */
public class Coordinate {
    public final int _x;
    public final int _y;

    public Coordinate(int x, int y) {
        _x = x;
        _y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (o instanceof Coordinate) {
            Coordinate c = (Coordinate) o;
            return _x == c._x && _y == c._y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * _x + _y;
    }

    @Override
    public String toString() {
        return "[" + _x + "," + _y + "]";
    }

}
