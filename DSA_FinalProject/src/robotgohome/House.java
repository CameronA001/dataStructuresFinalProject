package robotgohome;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class House extends JLabel{
	int currPos;

	/**
	 * blank constructor used in MainFrame to instatiate generic house object
	 */
	House(){
		
	}
	
	/**
	 * paramaterized constructor for house object that sets the size of the image based on the gridsize
	 * @param gridSize number of cells
	 */
	House(int gridSize){
		int size = (int)Math.sqrt(gridSize)*15;
		if (size <= 45) {
			size += 30;
		}
		if(size >= 120) {
			size -= 30;
		}
		ImageIcon robotImg = ImageResize.resizeImage("assets/house.png",size);
		this.setIcon(robotImg);
	}

	/**
	 * randomly sets the position of teh house
	 * @param gridSize number of cells
	 */
	public void setPosRand(int gridSize, Robot robot) {
		this.currPos = MainFrame.generateRandLoc(gridSize);
	}
	
	/**
	 * used when restarting a game to set the house at the previous position
	 * @param newPos desired position of the house
	 */
	public void setPos(int newPos) {
		this.currPos = newPos;
	}
	
/**
 * returns the position of the house
 * @return currPos the position of the house
 */
	public int getPos() {
		return this.currPos;
	}
}

