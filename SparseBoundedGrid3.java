package info.gridworld.grid;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * An <code>UnboundedGrid</code> is a rectangular grid with an unbounded number of rows and
 * columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid3<E> extends AbstractGrid<E>{
	private Map<Location, E> occupantMap;
	private int rows;
	private int cols;
    /**
     * Constructs an empty unbounded grid.
     */
    public SparseBoundedGrid3(int rowsNum,int colsNum)
    {
    	if(rowsNum <= 0){
			throw new IllegalArgumentException("rowsNum <= 0");
		}
		if(colsNum <= 0) {
			throw new IllegalArgumentException("colsNum <= 0");
		}
		rows = rowsNum;
		cols = colsNum;
        occupantMap = new HashMap<Location, E>();
    }

    public int getNumRows()
    {
        return rows;
    }

    public int getNumCols()
    {
        return cols;
    }

    public boolean isValid(Location loc)
    {
    	return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> a = new ArrayList<Location>();
        for (Location loc : occupantMap.keySet())
            a.add(loc);
        return a;
    }

    public E get(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.get(loc);
    }

    public E put(Location loc, E obj)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        if (obj == null)
            throw new NullPointerException("obj == null");
        return occupantMap.put(loc, obj);
    }

    public E remove(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc == null");
        return occupantMap.remove(loc);
    }
}


