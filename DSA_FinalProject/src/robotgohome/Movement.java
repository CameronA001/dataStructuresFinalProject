package robotgohome;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

/**
 * This class controls the movement of all objects throughout the cell array.
 * 
 * @author Cameron Abanes
 * @version 5/6/25
 */
public class Movement {
	
	public static int newPos;
	public int[] columns;
	public int[] rows;
	static int prevLocation=0;	
	
	/**
	 * This method checks if the robot will fall of the planet or not, incrementsTravel fee, checks whether or not it hit a power up, and has a chance to spawn a pwoerUp
	 * 
	 * @param robot Robot object
	 * @param change the amount/direction you want to move the robot
	 * @param gridsize the number of cells
	 * @param cells the CellPanel array
	 * @return the new Position of the robot
	 */
	public static int moveHorizontal(Robot robot, int change, int gridsize, CellPanel[] cells, Hashtable<String, BadGuy> badGuys, MainFrame frame) {
		
		int robotCurr = robot.getPos();
		final int finalRobot = robot.getPos();
		boolean edgeRobot = false;
		boolean edgeHouse = false;
		Random rand = new Random();

		//gets the edges
		int[] leftColumns = Movement.getLeftEdges(gridsize);
		int[] rightColumns = Movement.getRightEdges(gridsize);

		//if robot moving left, checks whether robot is on the left edge
		if(change<0) {
			edgeRobot = (Arrays.stream(leftColumns).anyMatch(x -> x == finalRobot));
		}
		
		//vice versa
		if(change>0) {
			edgeRobot = (Arrays.stream(rightColumns).anyMatch(x -> x == finalRobot));

		}

		if ((edgeRobot == false)) {
			
			if((robotCurr+change)<=gridsize&&(robotCurr+change)>0) {
				robotCurr+=change;
			}
			
			frame.checkChangeBadGuy(robot, badGuys);
			
			robot.incrementTravel();
		}
		
		if(prevLocation != 0) {
			robot.checkPowerUp(prevLocation, cells, robotCurr);
		}
		//randomly spawns in powerup, 1 in 10 chance
        int randomNum = rand.nextInt(15);
        if (randomNum==3) {
        	prevLocation = PowerUp.addMushroom(gridsize, cells, prevLocation);
        }
        

		return robotCurr;
	}

	
	/**
	 * Works very similarly to moveHorizontal, but instead checks the top and bottom rows for if the robot will fall offs
	 * 
	 * @param robot Robot object
	 * @param change the amount/direction you want to move the robot
	 * @param gridsize the number of cells
	 * @param cells the CellPanel array
	 * @return the new Position of the robot
	 */
	public static int moveVertical(Robot robot, int change, int gridsize, CellPanel[] cells, Hashtable<String, BadGuy> badGuys, MainFrame frame) {

		final int finalRobot = robot.getPos();
		int robotCurr = robot.getPos();
		boolean edgeRobot = false;
		Random rand = new Random();

		int[] topRow = Movement.getTopRow(gridsize);
		int[] bottomRow = Movement.getBottomRow(gridsize);

		//if robot moving left, checks whether robot is on the left edge
		if(change>0) {
			edgeRobot = (Arrays.stream(bottomRow).anyMatch(x -> x == finalRobot));
		}
		
		//vice versa
		if(change<0) {
			edgeRobot = (Arrays.stream(topRow).anyMatch(x -> x == finalRobot));

		}

		if (edgeRobot == false) {
			robot.incrementTravel();

			if((robotCurr+change)<=gridsize&&(robotCurr+change)>0) {
				robotCurr+=change;
			}
			
			frame.checkChangeBadGuy(robot, badGuys);

		}
		
		if(prevLocation != 0) {
			robot.checkPowerUp(prevLocation, cells, robotCurr);
		}

        int randomNum = rand.nextInt(15);
        
        //remove the previous powerup at that location and repaitn
        if (randomNum==3) {
        	prevLocation = PowerUp.addMushroom(gridsize, cells, prevLocation);
        }
	
		return robotCurr;
	}

	/**
	 * removes the robot object at the current position of the robot, used to update position
	 * @param robot Robot object
	 * @param house position of house
	 * @param cells Cellpanel Array
	 */
	public static void removeCells(Robot robot,  CellPanel[] cells) {
		cells[robot.getPos()].remove(robot);
		cells[robot.getPos()].revalidate();
		cells[robot.getPos()].repaint();
	}
	
	/**
	 * Very similar to previous, but for BadGuy object
	 * @param badGuy BadGuy object to be removed
	 * @param cells CellPanel array
	 */
	public static void removeCellsBad(BadGuy badGuy,CellPanel[] cells) {
		cells[badGuy.getPos()].remove(badGuy);
		cells[badGuy.getPos()].revalidate();
		cells[badGuy.getPos()].repaint();
	}
	
	/**
	 * Similar again to previous, but for powerUp object
	 * @param powerUp powerUp object to be removed
	 * @param cells CellPanel array
	 */
	public static void removePowerUp(PowerUp powerUp,CellPanel[] cells) {
		cells[powerUp.getPos()].remove(powerUp);
		cells[powerUp.getPos()].revalidate();
		cells[powerUp.getPos()].repaint();
	}
	
	/**
	 * Does essentially the opposite of removeCells, adding the robot object to the new positoin
	 * @param robot Robot object
	 * @param pos position to be moved to
	 * @param cells CellPanel array
	 */
	public static void changePosRobot(Robot robot,int pos, CellPanel[] cells) {
		robot.setPos(pos);
		cells[robot.getPos()].add(robot);
		cells[robot.getPos()].revalidate();
		cells[robot.getPos()].repaint();
	}
	
	/**
	 * Does the same thing as changePosRobot but for BadGuy objects
	 * @param badGuy BadGuy objects
	 * @param cells CellPanel array
	 */
	public static void changePosBad(BadGuy badGuy, CellPanel[] cells) {
		cells[badGuy.getPos()].add(badGuy);
		cells[badGuy.getPos()].revalidate();
		cells[badGuy.getPos()].repaint();
	}
	
	/**
	 * given the gridsize returns all cels that are at the very top row
	 * @param gridsize number of cells
	 * @return array of cells that are at very top row
	 */
	public static int[] getTopRow(int gridsize) {
		int sqrdGrid = (int)Math.sqrt(gridsize);
		int[] rows = new int[gridsize+1];
		//top row

		for(int i=1;i<=sqrdGrid;i++) {
			rows[i] =i;
		}
		return rows;
	}

	/**
	 * given the gridsize returns all cels that are at the very bottom row
	 * @param gridsize number of cells
	 * @return array of cells that are at very bottom row
	 */
	public static int[] getBottomRow(int gridsize) {
		//bottom row
		int sqrdGrid = (int)Math.sqrt(gridsize);
		int[] rows = new int[gridsize+1];
		for(int i=gridsize-sqrdGrid+1;i<=gridsize;i++) {
			rows[i] = i;
		}
		return rows;
	}

	/**
	 * given the gridsize returns all cels that are at the very left column
	 * @param gridsize number of cells
	 * @return array of cells that are at very left column
	 */
	public static int[] getLeftEdges(int gridsize) {
		//left column
		int sqrdGrid = (int)Math.sqrt(gridsize);
		int[] columns = new int[gridsize+1];

		for(int i=1;i<=gridsize-sqrdGrid+1;i+=sqrdGrid) {
			columns[i]=i;
		}
		return columns;
	}

	/**
	 * given the gridsize returns all cels that are at the very right column
	 * @param gridsize number of cells
	 * @return array of cells that are at very right column
	 */
	public static int[] getRightEdges(int gridsize) {
		//right column
		int sqrdGrid = (int)Math.sqrt(gridsize);
		int[] columns = new int[gridsize+1];
		for(int i=sqrdGrid;i<=gridsize;i+=sqrdGrid) {
			columns[i]=i;
		}
		return columns;
	}
}

