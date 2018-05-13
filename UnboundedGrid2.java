package info.gridworld.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnboundedGrid2 <E> extends AbstractGrid<E>{
	private Object[][] occupantArray; // the array storing the grid elements
	private int curRowsNum;
	private int curColsNum;
	private final int defaultSize = 16; 

    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public UnboundedGrid2()
    {
    	curRowsNum = defaultSize;
    	curColsNum = defaultSize;
        occupantArray = new Object[curRowsNum][curColsNum];
    }

    public int getNumRows()
    {
        return -1;
    }

    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return -1;
    }

    public boolean isValid(Location loc)
    {
    	if(loc.getCol() < 0 || loc.getRow() < 0) {
    		return false;
    	}
        return true;
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
            for (int c = 0; c < getNumCols(); c++)
            {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, c);
                if (get(loc) != null)
                    theLocations.add(loc);
            }
        }

        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if(loc.getCol() >= curColsNum || loc.getRow() >= curRowsNum) {
        	return (E) null;
        }
        return (E) occupantArray[loc.getRow()][loc.getCol()]; // unavoidable warning
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        // Add the object to the grid.
        E oldOccupant = get(loc);
        if(loc.getCol() >= curRowsNum || loc.getRow() >= curColsNum ) {
        	int oldRowsNum = curRowsNum;
        	int oldColsNum = curColsNum;
        	while(loc.getCol() >= curRowsNum || loc.getRow() >= curColsNum) {
        		curRowsNum *= 2;
        		curColsNum *= 2;
        	}
        	Object[][] newArray = new Object[curRowsNum][curColsNum];
        	for(int i = 0;i < oldRowsNum;++i) {
        		for(int j = 0;j < oldColsNum;++j){
        			newArray[i][j] = occupantArray[i][j];
        		}
        	}
        	occupantArray = newArray;
        }
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        E r = get(loc);
        if(loc.getRow() < curRowsNum && loc.getCol() < curColsNum) {
        	occupantArray[loc.getRow()][loc.getCol()] = null;
        }
        return r;
    }
}
