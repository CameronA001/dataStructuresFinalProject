package robotgohome;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class extends JFrame and is called when the player either wins or loses
 * @author Cameron Abanes
 * @version 5/6/25
 */
public class WinWindow extends JFrame{
	
	/**
	 * Constructor for WinWindow that also completes file reading and writing, and shows score and total travel fee
	 * 
	 * @param originalHouse original position of the house, used in case user wants to restart same game
	 * @param originalRobot same as originalHouse but for robot
	 * @param gridsize number of cells, again used to restart the same game
	 * @param win boolean to see whether or not the user won, text changes depending on this
	 * @param score the score the user got
	 * @param travelFee the total distance the user traveled in dollars
	 */
	WinWindow(int originalHouse, int originalRobot, int gridsize, boolean win, int score, int travelFee){

		ArrayList<Integer> scores = new ArrayList<Integer>();
		scores.add(score);
		WinWindow.scoring(scores);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(600,300);
		this.setLayout(null);
		
		JLabel winCon = new JLabel();
		JLabel scoreLabel = new JLabel();
		JLabel travelTotal = new JLabel();
		
		travelTotal.setText("Total Travel Fee: $"+travelFee);
		travelTotal.setBounds(200, 150,200,50);
		travelTotal.setFont((new Font("Arial", Font.BOLD, 20)));
		
		scoreLabel.setText("Score: "+String.valueOf(score));
		scoreLabel.setBounds(250,200,200,50);
		scoreLabel.setFont((new Font("Arial", Font.BOLD, 20)));
		
		if(win == true) {
			winCon.setText("YOU WON!");
			this.setTitle("You Won!");
		}
		else {
			winCon.setText("YOU LOST!");
			this.setTitle("You Lost!");
		}
		winCon.setFont((new Font("Arial", Font.BOLD, 24)));
		winCon.setForeground(Color.BLACK);
		winCon.setBounds(225,50,200,50);
		
		this.add(winCon);
		this.add(scoreLabel);
		this.add(travelTotal);
		
		JButton resetButton = new JButton("Click to restart this game!"); 
		JButton newButton = new JButton("Click to start a new game!");
		
		resetButton.addActionListener(e->{
			 WinWindow.this.dispose();
			new MainFrame(gridsize, originalHouse, originalRobot);
		});


		
		newButton.addActionListener(e->{
			 WinWindow.this.dispose();
			new MainFrame(gridsize, -1, -1);
		});

		
		resetButton.setBounds(75,100,200,50);	
		newButton.setBounds(300,100,200,50);
		
		this.add(newButton);
		this.add(resetButton);
		
		this.setVisible(true);
	
	}
	
	/**
	 * handles scoring and reading and writing to file
	 * @param scores ArrayList of scores
	 */
	public static void scoring(ArrayList<Integer> scores) {
		scores = Sorter.readFile(scores);
		Sorter.eraseFile();
		Sorter.selectionSort(scores);
		Sorter.addToFile(scores);
	}
}
