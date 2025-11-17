package robotgohome;


import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JLabel;

/**
 * A class that extends JLabel and represents the robot
 * 
 * @author Cameron Abanes
 * @version 5/6/26
 */
public class Robot extends JLabel{

	private Timer powerTimer;
	int currPos;
	boolean powered=false;
	int size;
	int score;
	int travelFee;

	/**
	 * blank constructor used to instatiate robot object in MainFrame
	 */
	Robot(){

	}

	/**
	 * robot constructor that uses gridsize to set the size of its image
	 * @param gridSize the amount of cells
	 */
	Robot(int gridSize){

		this.size = (int)Math.sqrt(gridSize)*15;
		if (this.size <= 45) {
			this.size += 30;
		}
		if(this.size >= 120) {
			this.size -= 30;
		}
		
		this.score = 0;
		
		ImageIcon robotImg = ImageResize.resizeImage("assets/robot.jpg",this.size);
		this.setIcon(robotImg);
	}

	/**
	 * checks whether or not the robot is at the same cell as a powerup, and if it is, increments score by 3, removes the powerUp from the cell and sets the powered state to true. 
	 * It then starts a 3 second timer where the robots image is change to a ball of electricity, after which it is "depowered" changing its image back to the original and setting powered to false
	 * 
	 * @param powerUpLocation
	 * @param cells
	 * @param currPosition
	 */
	public void checkPowerUp(int powerUpLocation, CellPanel[] cells, int currPosition) {
		if(powerUpLocation == currPosition) {
			
			this.score+=3;
			
			//checks the current cell of the robot for any instance of PowerUp
	        for (Component comp : cells[currPosition].getComponents()) {
	            if (comp instanceof PowerUp) {
	                cells[currPosition].remove(comp);
	                cells[currPosition].revalidate();
	                cells[currPosition].repaint();
	                break;
	            }
	        }

			this.powered = true;
			ImageIcon electricImg = ImageResize.resizeImage("assets/electricity.jpg",this.size);
			this.setIcon(electricImg);
			
			this.powerTimer = new Timer(3000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					powered = false;
					ImageIcon robotImg = ImageResize.resizeImage("assets/robot.jpg",Robot.this.size);
					Robot.this.setIcon(robotImg);
				}

			}
					);
			powerTimer.setRepeats(false);
			powerTimer.start();

		}

	}

	/**
	 * getter for powered
	 * @return powered state of robot
	 */
	public boolean getPowered() {
		return this.powered;
	}
	
	/**
	 * randomly sets the position of the robot given gridsize
	 * @param gridSize the number of cells
	 */
	public void setPosRand(int gridSize, House house) {
		while((this.getPos() == house.getPos()) || (Math.abs(house.getPos()-this.getPos()) <= (int)Math.sqrt(gridSize)+3)) {
			this.currPos = MainFrame.generateRandLoc(gridSize);
		}
	}

	/**
	 * sets the positon of the robot to a given cell
	 * @param newPos the cell you want the robot to be at
	 */
	public void setPos(int newPos) {
		this.currPos = newPos;
	}

	/**
	 * getter for the current position of the robot
	 * @return this.currPos current positoin of the robot
	 */
	public int getPos() {
		return this.currPos;
	}
	
	/**
	 * getter for the score associated with the robot (used to pass into WinWindow)
	 * @return this.score the score
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * used to increment the score by a given int
	 * @param increment the amount you want to increase score by
	 */
	public void incrementScore(int increment) {
		this.score +=increment; 
	}
	
	/**
	 * Increments travel fee, called every time the robot moves
	 */
	public void incrementTravel() {
		this.travelFee +=1;
	}
	
	/**
	 * returns the distance traveled by the robot
	 * @return travelFee, distance traveled by robot in dollars
	 */
	public int getTravel() {
		return this.travelFee;
	}
}
