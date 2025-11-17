package robotgohome;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that extends JPanel, called in MainFrame, with each instance representing a cell
 * 
 * @author Cameron Abanes
 * @version 5/6/25
 */
public class CellPanel extends JPanel{
	/**
	 * Constructor for the panel, sets background color to white and creates border
	 */
	CellPanel(){
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	}
	
}
