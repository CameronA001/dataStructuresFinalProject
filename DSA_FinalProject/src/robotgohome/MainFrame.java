package robotgohome;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.util.Stack;

/**
 * A class that extends JFrame and is the main window in which the game is played.
 * 
 * @author cameron Abanes
 * @version 5/6/25
 */

public class MainFrame extends JFrame implements KeyListener{
	private Timer botTimer;
	CellPanel[] cells;
	PowerUp[] powerUps = new PowerUp[2];
	Robot robot = new Robot();
	House house = new House();
	int pos;
	int gridSqrd;
	int gridsize;
	int originalRobot;
	int originalHouse;
	int nameBadGuy =2;
	public Hashtable<String, BadGuy> badGuys = new Hashtable<>();
	Stack<Integer> moves = new Stack<>();
	
	/**
	 * The constructor for the mainframe, adds all necessary elements, checks whether or not you are restarting a game or starting a new one
	 * 
	 * @param gridSize the number of cells to be added to the frame (squareable numbers only)
	 * @param houseOriginal the position of the house in the last game (set to -1 if new game, then it is random)
	 * @param robotOriginal the start position of the robot in the last game (set to -1 if new game, then it is random)
	 */
	MainFrame(int gridSize,int houseOriginal,int robotOriginal){

		this.gridsize = gridSize;
		this.cells = new CellPanel[gridSize+1];
		
		gridSqrd = (int)Math.sqrt(gridSize);
		int frameSize = gridSqrd*150;

		this.setTitle("Robot Go Home!");	
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(frameSize,frameSize);
		this.addKeyListener(this);
		this.setLayout(new GridLayout(gridSqrd,gridSqrd));

		robot = new Robot(gridSize);
		house = new House(gridSize);

		if((robotOriginal<0)&&(houseOriginal<0)) {
			robot.setPosRand(gridSize, house);
			house.setPosRand(gridSize, robot);

			originalRobot = robot.getPos();
			originalHouse = house.getPos();
		}
		else {
			robot.setPos(robotOriginal);
			house.setPos(houseOriginal);
			originalRobot = robotOriginal;
			originalHouse = houseOriginal;
		}

		//add cells to frame
		for(int i = 1;i<=gridSize;i++) {
			cells[i] = new CellPanel();
			this.add(cells[i]);
		}

		cells[robot.getPos()].add(robot);
		cells[house.getPos()].add(house);
		moves.push(robot.getPos());

		//initialize first enemy
		BadGuy firstBad = new BadGuy(gridsize);
		firstBad.setPosRand(gridSize, robot.getPos(), house.getPos());
		firstBad.setName("1");
		badGuys.put("1", firstBad);

		addBad();
		moveBad();
		startWinTimer();
		this.setVisible(true);
	}

	/**
	 * empty methods, necessary for keyActionListener to work properly
	 */
	@Override
	public void keyPressed(KeyEvent w) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stud
	}
	
	
	/**
	 * takes in key inputs and moves the robot accordingly by removing the robot JLabel at the current frame, updating the positon, and adding it at the new position. 
	 * Also pushes the new position to the stack, allowing users to go back moves
	 * 
	 * @param 'a' moves the robot left
	 * @param 'd' moves robot right
	 * @param 's' moves robot down
	 * @param 'w' moves robot up
	 * @param 'b' moves the robot back a move
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyChar()) {
		case 'a':
			Movement.removeCells(robot, cells);
			pos = Movement.moveHorizontal(robot, -1,gridsize, cells, badGuys, this);
			Movement.changePosRobot(robot, pos, cells);
			moves.push(pos);
			break;

		case 'd':
			Movement.removeCells(robot, cells);
			pos = Movement.moveHorizontal(robot, 1,gridsize, cells, badGuys ,this);
			moves.push(pos);
			Movement.changePosRobot(robot, pos, cells);
			break;

		case 's':
			Movement.removeCells(robot, cells);
			pos = Movement.moveVertical(robot, gridSqrd,gridsize, cells, badGuys ,this);
			moves.push(pos);
			Movement.changePosRobot(robot, pos, cells);
			break;

		case 'w':
			Movement.removeCells(robot, cells);
			pos = Movement.moveVertical(robot, 0-gridSqrd, gridsize, cells, badGuys ,this);
			Movement.changePosRobot(robot, pos, cells);
			moves.push(pos);
			break;

		case 'b':
			if(!moves.isEmpty()) {
				Movement.removeCells(robot, cells);
				Movement.changePosRobot(robot, moves.pop(), cells);
			}
		}
	}

	/**
	 * iterates through Hashtable of badGuys, checks if the badGuy is at same spot at robot (and whether or not he will be eaten depending on powered state of robot)
	 * similarly to robot move, it removes the badGuy JLabel at the current cell, then updates its positon using BFS with the target being the robots current positon, and adds it to the new cells 
	 * also increments the score if the monster is eaten. Repeats every second.
	 */
	public void moveBad() {
		this.botTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(String key : badGuys.keySet()) {
					BadGuy currBad = badGuys.get(key);
					boolean eaten = currBad.checkEaten(robot, currBad, badGuys, cells);
					Movement.removeCellsBad(currBad, cells);
					currBad.moveBFS(robot.getPos(),gridsize);
					if(!eaten) {
						Movement.changePosBad(currBad, cells);
					}
					else {
						badGuys.remove(key);
						robot.incrementScore(5);
					}

				}
				MainFrame.this.checkChangeBadGuy(robot, badGuys);
			}
		});
		botTimer.start();
	}
	
	/**
	 * for every BadGuy object in badGuys, checks if it has been eaten (called when robot moves) and upadtes cells and Hashtable according
	 * @param robot Robot object
	 * @param badGuys Hashtable containing all badGuy objects
	 */
	public void checkChangeBadGuy(Robot robot, Hashtable<String, BadGuy> badGuys) {
		for(String key : badGuys.keySet()) {
			BadGuy currBad = badGuys.get(key);
			boolean eaten = currBad.checkEaten(robot, currBad, badGuys, cells);
			if(eaten) {
				Movement.removeCellsBad(currBad, cells);
				badGuys.remove(key);
				robot.incrementScore(5);
			}

		}
	}

	/**
	 * Repeats every 6 seconds. If the amount of badGuys on the screen is less than 4, creates a new instance of badGuy and adds it to Hashtable as well as to the appropriate cell. 
	 */
	private void addBad() {

		this.botTimer = new Timer(6000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					BadGuy newBad = new BadGuy(gridsize);
					newBad.setName(String.valueOf(nameBadGuy));
					badGuys.put(String.valueOf(nameBadGuy), newBad);
					newBad.setPosRand(gridsize, robot.getPos(), house.getPos());
					cells[newBad.getPos()].add(newBad);
					nameBadGuy++;
			}
		});
		botTimer.start();
	}

	/**
	 * A timer that runs every tenth of a second to check win conditions using checkWinCon
	 * utilizes a Timer[] array so that it can be stopped within the timer loop
	 */
	private void startWinTimer() {
		final Timer[] winCheckTimer = new Timer[1];
		winCheckTimer[0] = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean winLoss = checkWinCon(robot.getPos(), house.getPos());
				if (winLoss) {
					winCheckTimer[0].stop();
				}
			}
		});
		winCheckTimer[0].start();
	}

	/**
	 * this method iterates through the Hashtable of badguys, checking their current position in comparison with the robots. It also checks the robots position against the house, and updates accordingly.
	 * 
	 * @param robotCurr current position of the robot
	 * @param houseCurr positon of the house
	 * @return boolean, true if the player wins or loses, otherwise false and the timer above continues
	 */
	public boolean checkWinCon(int robotCurr, int houseCurr) {
		for(String key: badGuys.keySet()) {
			if (robotCurr == houseCurr) {
				robot.incrementScore(8);
				new WinWindow(originalHouse,originalRobot, gridsize, true, robot.getScore(), robot.getTravel());
				this.dispose();
				return true;
			}
			else if((badGuys.get(key).getPos()==robotCurr)&&(robot.getPowered()==false)){
				new WinWindow(originalHouse,originalRobot, gridsize, false, robot.getScore(),robot.getTravel());
				this.dispose();
				return true;
			}

		}
		return false;
	}


	/**
	 * used to randomly set the location of objects, generates random number between 1 and the number of cells inclusive
	 * 
	 * @param gridSize number of cells in the grid
	 * @return randomNum a random int between 1 and the size of the grid inclusive
	 */
	static public int generateRandLoc(int gridSize) {
		int randomNum = (int)(Math.random()*gridSize);
		return randomNum;
	}

}
