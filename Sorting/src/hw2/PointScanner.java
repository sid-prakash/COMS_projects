package edu.iastate.cs228.hw2;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * 
 * @author Siddharthan Prakash
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	/**
	 * array of points operated on by a sorting algorithm. 
	 * stores ordered points after a call to sort(). 
	 */
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	
	private Algorithm sortingAlgorithm;   // "selection sort", "insertion sort", "mergesort", or
										  // "quicksort". Initialized by a subclass constructor.
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("IllegalArgumentException");
		}
		
		this.points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			this.points[i] = pts[i];
		}
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		try {
			File fileName = new File(inputFileName);
			Scanner s = new Scanner(fileName);
			
			ArrayList<Integer> num = new ArrayList<Integer>();
			int count = 0;
			
			while (s.hasNextInt()) {
				num.add(s.nextInt());
				count++;
			}
			
			if (count % 2 != 0) {
				throw new InputMismatchException();
			}
			points = new Point[count/2];
			s.close();
			
			int j = 0;
			for(int i = 0; i < count; i+=2) {
				int x = num.get(i);
				int y = num.get(i+1);
				Point p = new Point(x, y);
				points[j] = p;
				j++;
			}
			
		}
		catch (FileNotFoundException e) {
			System.out.print("File not found");
		}
		
		sortingAlgorithm = algo;
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{  
		AbstractSorter aSorter = null;
		
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(this.points);
		}
		else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(this.points);
		}
		else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(this.points);
		}
		else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(this.points);
		}
		
		
		long start = System.nanoTime();
		
		aSorter.setComparator(0);
		aSorter.sort();
		int xMedian = aSorter.getMedian().getX();
		aSorter.setComparator(1);
		aSorter.sort();
		int yMedian = aSorter.getMedian().getY();
		
		long end = System.nanoTime();
		
		scanTime = end - start;
		medianCoordinatePoint = new Point(xMedian, yMedian);
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String s = sortingAlgorithm  + "\t" + points.length + "\t" + scanTime;
		return s;  
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: " + medianCoordinatePoint.toString(); 
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		String outputFileName = "src/edu/iastate/cs228/hw2/" + sortingAlgorithm + ".txt";
		File f = new File(outputFileName);
		PrintWriter pw = new PrintWriter(f);
		pw.println(toString());
		pw.close();
	}

		
}
