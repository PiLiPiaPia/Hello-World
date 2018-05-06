package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<Location> crossLocation = new Stack<Location>();
	public Integer stepCount = 0;
	public Map<String,Boolean> locationVisitState = new HashMap<String,Boolean>();
	boolean hasShown = false;//final message has been shown
	private final int numOfValidDirection = 4;
	private final int degreeOffset = 90;
	int countDirection[] = new int[numOfValidDirection];
	
	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
		for(int i = 0;i < numOfValidDirection;++i) {
			countDirection[i] = 0;
		}
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		} 
		else {
			Grid<Actor> gr = getGrid();
			last = crossLocation.peek();
			crossLocation.pop();
			Location backward = crossLocation.peek();
			setDirection(getLocation().getDirectionToward(backward));
			moveTo(backward);
			Flower flower = new Flower(getColor());
			flower.putSelfInGrid(gr, last);
			countDirection[backward.getDirectionToward(last)/degreeOffset]--;
			last = backward;
			next = null;
			stepCount++;
		}
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> valid = new ArrayList<Location>();
		for(int i = 0;i < numOfValidDirection;++i) {
			Location temp = getLocation().getAdjacentLocation(i*degreeOffset);
			if(!gr.isValid(temp)) {
				continue;
			}
			else if(gr.get(temp) == null) {
				valid.add(temp);
			}
			else if((gr.get(temp) instanceof Rock) && (gr.get(temp).getColor().equals(Color.RED))) {
				isEnd = true;
				setDirection(getLocation().getDirectionToward(temp));
				return null;
			}
		}
		return valid;
	}

	
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		Location curLoc = getLocation();
		if(crossLocation.empty()) {
			crossLocation.push(curLoc);
		}
		setDirection(curLoc.getDirectionToward(next));
		moveTo(next);
		crossLocation.push(next);
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, curLoc);
		last = next;
		next = null;
	}
	


    /**
     * Tests whether this bug can move forward into a location that is empty or
     * contains a flower.
     * @return true if this bug can move.
     */
    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location curLoc = getLocation();
        Location next = curLoc.getAdjacentLocation(getDirection());
        ArrayList<Location> availableLocations = getValid(curLoc);
        ArrayList<Integer> bestLocationList = new ArrayList<Integer>();
        if(availableLocations == null || availableLocations.size() == 0) {
        	return false;
        }
        else {
        	//方向概率估计
        	for(int i = 0;i < availableLocations.size();++i) {
        		int directionIndex = (curLoc.getDirectionToward(availableLocations.get(i)))/degreeOffset;
        		for(int j = 0;j < countDirection[directionIndex];++j) {
        			bestLocationList.add(i);
        		}
        	}
        	int selectIndex = (int)(Math.random() * bestLocationList.size());
        	next = (bestLocationList.size() == 0)?availableLocations.get(0):availableLocations.get(bestLocationList.get(selectIndex));
        	countDirection[curLoc.getDirectionToward(next)/degreeOffset]++;
        	System.out.println(bestLocationList.toString());
        	return true;
        }
        // not ok to move onto any other actor
    }
	
	
}
