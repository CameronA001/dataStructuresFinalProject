package robotgohome;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * a JLabel class that represents the powerUp mushroom
 * 
 * @author Cameron Abanes
 * @version 5/6/25
 */
public class PowerUp extends JLabel{
	
	int currPos;
	
	/**
	 * a constructor for the powerUp, setting the image size based on the amount of cells
	 * @param gridsize the number of cells
	 */
	PowerUp(int gridsize){
		int size = (int)Math.sqrt(gridsize)*15;
		if (size <= 45) {
			size += 30;
		}
		if(size >= 120) {
			size -= 30;
		}
		ImageIcon robotImg = ImageResize.resizeImage("assets/mushroom.png",size);
		this.setIcon(robotImg);
	}
	
	/**
	 * checks the cell of prevLocation (set to 0 if first instance of powerUp), removes it from that location, and spawns a new one at randomLocation
	 * 
	 * @param gridsize the number of cells
	 * @param cells the array of CellPanel objects representing all cells/
	 * @param prevLocation the location of the previously spawned powerUp
	 * @return the location of the new powerUp
	 */
	public static int addMushroom(int gridsize, CellPanel[] cells, int prevLocation) {
    	if(prevLocation != 0) {
        	for (Component comp : cells[prevLocation].getComponents()) {
        	    if (comp instanceof PowerUp) {
        	        cells[prevLocation].remove(comp);
        	        cells[prevLocation].revalidate();
        	        cells[prevLocation].repaint();
        	        break; // Assuming only one PowerUp per cell
        	    }
        	}
    	}
    	
		int location = MainFrame.generateRandLoc(gridsize);
		cells[location].add(new PowerUp(gridsize));
		cells[location].revalidate();
		cells[location].repaint();
	
		return location;
	}
	
	/**
	 * getter for the position of the current power up
	 * @return currPos the position of the current power up
	 */
	public int getPos() {
		return this.currPos;
	}
	
}
