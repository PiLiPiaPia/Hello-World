package info.gridworld.grid;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

public class SparseBoundedGrid2<E> extends AbstractGrid<E> {
	//storing occupants in the grid using ArrayList of LinkedList
	private ArrayList<LinkedList> occupantArray;
	//num of columns
	private int cols;
	//num of rows
	private int rows;
	public SparseBoundedGrid2(int rowsNum,int colsNum) {
		if(rowsNum <= 0){
			throw new IllegalArgumentException("rowsNum <= 0");
		}
		if(colsNum <= 0) {
			throw new IllegalArgumentException("colsNum <= 0");
		}
		rows = rowsNum;
		cols = colsNum;
		occupantArray = new ArrayList<LinkedList>();
		for(int i = 0;i < rows;++i) {
			occupantArray.add(new LinkedList<OccupantInCol>());
		}
	}
	
	public int getNumRows()
    {
        return rows;
    }

    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return cols;
    }

    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
        	LinkedList<OccupantInCol> curRow = occupantArray.get(r);
        	if(curRow != null) {
        		for(OccupantInCol occu:curRow) {
        			Location curLoc = new Location(r,occu.getCol());
        			theLocations.add(curLoc);
        		}
        	}
        }

        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        LinkedList<OccupantInCol> curRow = occupantArray.get(loc.getRow());
        if(curRow != null) {
        	for(OccupantInCol occu : curRow) {
            	if(occu.getCol() == loc.getCol()) {
            		return (E) occu;
            	}
            }
        }
        //not found any occupant on the location
        return (E) null; 
    }

    public E put(Location loc, E obj)
    {
    	//invalid exception
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        //null exception
        if (obj == null)
            throw new NullPointerException("obj == null");
        // remove the object from the grid if exists
        E oldOccupant = remove(loc);
        LinkedList<OccupantInCol> curRow = occupantArray.get(loc.getRow());
        curRow.add(new OccupantInCol(obj,loc.getCol()));
        return oldOccupant;
    }

    public E remove(Location loc)
    {
    	//location invalid exception
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
       
        E r = get(loc);
        //return null if not found
        if(r == null) {
        	return null;
        }
        
        //find the OccupantInCol in the location and remove it
        LinkedList<OccupantInCol> curRow = occupantArray.get(loc.getRow());
        if(curRow != null) {
        	Iterator<OccupantInCol> it = curRow.iterator();
        	while(it.hasNext()) {
        		if(it.next().getCol() == loc.getCol()){
        			it.remove();
        			break;
        		}
        	}
        }
        return r;
    }
	
}
