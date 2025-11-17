package robotgohome;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The driver class for the program, creates a simple JFrame window and takes a user input for the number of cells
 * 
 * @author CameronAbanes
 * @version 5/6/25
 */

public class Driver extends JFrame {

	public static void main(String[] args) {
		new Driver();
	}
	
	/**
	 * Constructor for driver class
	 */
	Driver() {
		this.setTitle("Robot Go Home!");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(400, 600); // Make sure window size is large enough
		this.setLayout(null);

		JTextField frameSize = new JTextField();
		JLabel enterNumCells = new JLabel("Enter the # of cells you want: (Squareable numbers only)");
		JLabel title = new JLabel("ROBOT GO HOME!");
		JTextArea instructions = new JTextArea("Help the robot go home in the least amount of moves!\n\n"
				+ "Use WASD to move, dodge the monsters and use the mushroom to be able to eat them!\n\n"
				+ "Use B to go back a move");
		JButton start = new JButton("Start Game");
		
		//checks if the number entered is a valid squareable int, and if so starts new game
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int numCells = Integer.parseInt(frameSize.getText());
					if (!startMain(numCells)) {
						enterNumCells.setForeground(Color.RED);
						enterNumCells.setText("Please enter a valid number!");
					} else {
						Driver.this.dispose();
						new MainFrame(numCells, -1, -1);
					}
				} catch (NumberFormatException ex) {
					enterNumCells.setForeground(Color.RED);
					enterNumCells.setText("Please enter a valid number!");
				}
			}
		});

		instructions.setEditable(false);
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setOpaque(false);
		
		enterNumCells.setFont(new Font("Arial", Font.BOLD, 12));
		title.setFont(new Font("Arial", Font.BOLD, 25));
		instructions.setFont(new Font("Arial", Font.BOLD, 12));
		
		title.setForeground(Color.RED);

		instructions.setBounds(50, 100, 300, 150); 
		start.setBounds(125, 400, 100, 50); 
		frameSize.setBounds(90, 500, 200, 25); 
		enterNumCells.setBounds(25, 350, 350, 30);
		title.setBounds(75, 50, 250, 40);

		// Add components to JFrame
		this.add(title);
		this.add(frameSize);
		this.add(enterNumCells);
		this.add(instructions);
		this.add(start);

		this.setVisible(true); // Make sure the window is visible after all components are added
	}

	
	/**This function simply returns true or false depending on if the nubmer of cells entered is a squareable number
	 * 
	 * @param numCells the number of cells the user wants to have in the program
	 * @return a boolean on whether or not the int entered is a valid squareable number
	 */
	public boolean startMain(int numCells) {
		return (int) Math.sqrt(numCells) * (int) Math.sqrt(numCells) == numCells;
	}
}
