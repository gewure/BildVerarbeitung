
package dataContainers;

public class Coordinate {

	public final int _x;
	public final int _y;
	
	public Coordinate(int x, int y){
		_x = x;
		_y = y;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o){
        	return true;
        }
        if (!(o instanceof Coordinate)){
        	return false;
        }
        Coordinate c = (Coordinate) o;
        if (_x == c._x && _y == c._y){
        	return true;
        }
        else{
        	return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 * _x + _y;
    }
    
    @Override
    public String toString(){
    	return "[" + _x + "," + _y + "]";
    }

}
