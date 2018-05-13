package info.gridworld.grid;

import java.util.ArrayList;

/*
 * a better version of implemention for BoundedGrid using SparseGridNode array
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E>{
	//storing the occupants in the grid
	private SparseGridNode[] occupantArray;
	private int rows;
	private int cols;
	
	public SparseBoundedGrid(int rowNum,int colNum) {
		if(rows <= 0) {
			throw new IllegalArgumentException("rows <= 0");
		}
		if(cols <= 0) {
			throw new IllegalArgumentException("cols <= 0");
		}
			
		rows = rowNum;
		cols = colNum;
		occupantArray = new SparseGridNode[rows];
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
        	SparseGridNode curNode = occupantArray[r];
        	while(curNode != null) {
        		Location curLoc = new Location(r,curNode.getCol());
        		theLocations.add(curLoc);
        		curNode = curNode.getNext();
        	}
        }

        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        SparseGridNode curNode = occupantArray[loc.getRow()];
        while(curNode != null) {
        	if(curNode.getCol() == loc.getCol()) {
        		return (E) curNode.getOccupant();
        	}
        	curNode = curNode.getNext();
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
        // remove the object from the grid
        E oldOccupant = get(loc);
        SparseGridNode curNode = occupantArray[loc.getRow()];
        //add the object to the grid
        occupantArray[loc.getRow()] = new SparseGridNode(obj,loc.getCol(),curNode);
        return oldOccupant;
    }

    public E remove(Location loc)
    {
    	//location invalid exception
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        E r = get(loc);
        SparseGridNode curNode = occupantArray[loc.getRow()];
        SparseGridNode lastNode;
        if(curNode.getCol() == loc.getCol()) {
        	occupantArray[loc.getRow()] = curNode.getNext();
        }
        else {
        	lastNode = curNode;
        	curNode = curNode.getNext();
        	while(curNode != null) {
            	if(curNode.getCol() == loc.getCol()) {
            		lastNode.setNext((curNode.getNext()));
            		break;
            	}
            	else {
            		lastNode = curNode;
            		curNode = curNode.getNext();
            	}           	
            }
        }
        
        return r;
    }
}
