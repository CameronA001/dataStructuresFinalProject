package robotgohome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * A class extends that JLabel and represents the monster
 * 
 * @author CameronAbanes
 * @version 5/6/25
 */
public class BadGuy extends JLabel{
	int currPos=1;
	String name;

	/**
	 * Constructor for the monster, sets the size of the image based on gridsize
	 * @param gridsize the number of cells
	 */
	BadGuy(int gridsize){
		int size = (int)Math.sqrt(gridsize)*15;
		size = (int)Math.sqrt(gridsize)*15;
		if (size <= 45) {
			size += 30;
		}
		if(size >= 120) {
			size -= 30;
		}
		ImageIcon robotImg = ImageResize.resizeImage("assets/cacodemon.png",size);
		this.setIcon(robotImg);
	}

	/**
	 * A method to get the neighbors of a certain cell, used in the BFS below
	 * 
	 * @param cell the current cell whose neighbors you want to find
	 * @param gridSize the amount of cells in the frame
	 * @return a List containing all the neighboring cells of the selected cell
	 */
	private static List<Integer> getNeighbors(int cell, int gridSize){
		List<Integer> neighbors = new ArrayList<>();
		int gridSqrd = (int)Math.sqrt(gridSize);
		int[] leftColumns = Movement.getLeftEdges(gridSize);
		int[] rightColumns = Movement.getRightEdges(gridSize);
		int[] topRow = Movement.getTopRow(gridSize);
		int[] bottomRow = Movement.getBottomRow(gridSize);

		int zeroBasedIndex = cell-1;

		//up
		final int up = cell - gridSqrd;
		if (up >= 1 && (Arrays.stream(topRow).noneMatch(x -> x == up+gridSqrd))) {
			neighbors.add(up);
		}

		// Down
		int down = cell + gridSqrd;
		if (down <= gridSize && (Arrays.stream(bottomRow).noneMatch(x -> x == down-gridSqrd))) {
			neighbors.add(down);
		}

		// Left
		int left = cell - 1;
		if (left >= 1 &&  (Arrays.stream(leftColumns).noneMatch(x -> x == left+1))) {
			neighbors.add(left);
		}

		// Right
		int right = cell + 1;
		if (right <= gridSize && (Arrays.stream(rightColumns).noneMatch(x -> x == right-1))) {
			neighbors.add(right);
		}

		return neighbors;

	}

	/**
	 * Moves the badGuy using the path defined in BFS below
	 * 
	 * @param target the cell you want to get to (current position of robot)
	 * @param gridsize the number of cells
	 */
	public void moveBFS(int target, int gridsize) {
		List<Integer> path = BFS(this.currPos, target, gridsize);

		if (path != null && path.size() > 1) {
			this.currPos = path.get(1); // move one step
		}
	}

	/**
	 * BFS algorithm 
	 * 
	 * @param start the starting cell (current monster position)
	 * @param goal the goal cell (current robot position)
	 * @param gridsize number of cells
	 * @return A list of integers that represents the path to the robot
	 */
	private static List<Integer> BFS(int start, int goal, int gridsize) {
		Queue<List<Integer>> queue = new LinkedList<>();
		Set<Integer> visited = new HashSet<>();

		List<Integer> startPath = new ArrayList<>();
		startPath.add(start);
		queue.add(startPath);
		visited.add(start);

		while (!queue.isEmpty()) {
			List<Integer> currentPath = queue.poll();
			int current = currentPath.get(currentPath.size() - 1);

			if (current == goal) {
				return currentPath;
			}

			for (int neighbor : BadGuy.getNeighbors(current, gridsize)) {
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					List<Integer> newPath = new ArrayList<>(currentPath);
					newPath.add(neighbor);
					queue.add(newPath);
				}
			}
		}

		return null; // No path found
	}


	/**
	 * sets the position randomly of the monster, making sure it doesn't spawn on top of the robot or house
	 * 
	 * @param gridSize number of cells
	 * @param robotCurr current position of the robot
	 * @param houseCurr position of the house
	 */
	public void setPosRand(int gridSize, int robotCurr, int houseCurr) {
		this.currPos = MainFrame.generateRandLoc(gridSize);
		while((currPos == houseCurr)||currPos==robotCurr) {
			this.currPos = MainFrame.generateRandLoc(gridSize);
		}
	}
	
	/**
	 * if the robot is powered and at the same position of the monster, removes the monster from the cell and the Hashtable
	 * 
	 * @param robot Robot object
	 * @param monster BadGuy object
	 * @param badGuys Hashtable of all badguys currently on screen
	 * @param cells the array of CellPanel, updated to show monster having been eaten
	 * @return boolean true if the monster has been eaten and false otherwise
	 */
	public boolean checkEaten(Robot robot, BadGuy monster, Hashtable<String, BadGuy> badGuys, CellPanel[] cells) {
		if((robot.getPos() == monster.getPos())&&(robot.getPowered()==true)) {
			badGuys.remove(monster.getName());
			return true;
		}

		return false;
	}

	/**
	 * setter for the monster, which is then used to place into Hashtable
	 * 
	 * @param name the name of the monster
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter for monster name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * used to set positoin of monster
	 * @param newPos the position you want the monster to be plaed at
	 */
	public void setPos(int newPos) {
		this.currPos = newPos;
	}

	/**
	 * getter for current positon of the monster
	 * @return
	 */
	public int getPos() {
		return this.currPos;
	}
}
