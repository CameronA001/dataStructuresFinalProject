package robotgohome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//using modified code from Sherri Weitl-Harms for selectionSort

/**
 * This class handles the sorting and file editing 
 * 
 * @author Cameron Abanes
 * @version 5/6/25
 * */
public class Sorter {
	
	/**
	 * This method is an implementation of the selection sort that is used to sort the scores from the file
	 * @param items ArrayList containing the list of scores to be sorted
	 */
	public static <T extends Comparable<? super T>> void selectionSort(ArrayList<Integer> items) {

		for (int i = 0; i < items.size()-1; i++) {
			int indexOfSmallest = findSmallestStartingAt(items, i);
			Collections.swap(items, i, indexOfSmallest);
		}
	}

	/**
	 * 
	 *finds the smallest integer, used by selectionSort
	 * @param items ArrayList containing the list of scores to be sorted
	 * @param startIndex integer to start sort
	 * @return int that represents teh smallest int in list
	 */
	private static <T extends Comparable<? super T>> int findSmallestStartingAt(ArrayList<Integer> items, int startIndex) {
		int indexOfMin = startIndex;
		for (int i = startIndex+1; i < items.size(); i++) {
			if (items.get(i).compareTo(items.get(indexOfMin)) < 0) {
				indexOfMin = i;
			}
		}
		return indexOfMin;
	}

	/**
	 * Reads in file and adds the scores already in there to the ArrayList
	 * @param scores ArrayList to add scores to
	 * @return scores ArrayList updated with scores from file
	 */
	public static ArrayList<Integer> readFile(ArrayList<Integer> scores){
		try {
			File scoresToDelete = new File("Scores.txt");
			Scanner scanner = new Scanner(scoresToDelete);
			while(scanner.hasNextLine()) {
				scores.add(Integer.valueOf(scanner.nextLine()));
			}
		}catch(Exception e) {
			System.out.println("File not found");
		}
		return scores;
	}

	/**
	 * Makes the file blank, allowing for simple rewriting with the ArrayList created with readFile
	 */
	public static void eraseFile(){

		try {
			FileWriter writer = new FileWriter("Scores.txt", false);
			writer.write("");
			writer.close();
		}catch(Exception e) {
			System.out.println("File not found");
		}

	}

	/**
	 * writes the updates ArrayList to the file line by line
	 * @param scores updated ArrayList with scores from old file as well as new score from last played game
	 */
	public static void addToFile(ArrayList<Integer> scores) {
		try {
			File highScores = new File("Scores.txt");
			FileWriter writer = new FileWriter("Scores.txt");

			for(int i = scores.size()-1;i>=0;i--) {
				writer.write(String.valueOf(scores.get(i))+"\n");
			}
			writer.close();
		}
		catch(IOException e) {
			System.out.println("An error occured");
			e.printStackTrace();
		}
	}
}
