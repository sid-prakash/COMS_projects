package edu.iastate.cs228.hw2;

/**
 * 
 * @author Siddharthan Prakash
 *
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException
	{	
		System.out.println("Performance of Four Sorting Algorithms in Point Scanning");
		System.out.println("keys  1 (random integers)  2 (file input)  3 (exit)");
		
		int trial = 1;
		boolean loop = true;
		Scanner s = new Scanner(System.in);
		
		while (loop) {
			System.out.print("Trial " + trial + ": ");
			int type = s.nextInt();
			
			PointScanner[] scanners = new PointScanner[4];
			
			if (type == 1) {
				System.out.print("Enter number of random points: ");
				int num = s.nextInt();
				System.out.println();
				Random rand = new Random();
				Point[] points = generateRandomPoints(num, rand);
				
				scanners[0] = new PointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(points, Algorithm.MergeSort);
				scanners[3] = new PointScanner(points, Algorithm.QuickSort);
			}
			else if (type == 2) {
				System.out.println("Points from a file");
				System.out.print("File name: "); //src/PointTest.txt
				String fileName = s.next();
				
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
			}
			else if (type == 3) {
				loop = false;
				break;
			}
			else {
				throw new IllegalArgumentException("Please enter 1, 2, or 3");
			}
			
			System.out.println("algorithm \t size \t time (ns)");
			System.out.println("----------------------------------");
			
			for(int i = 0; i < scanners.length; i++) {
				scanners[i].scan();
				scanners[i].writeMCPToFile();
				System.out.println(scanners[i].stats());
			}
			
			System.out.println("----------------------------------");
			System.out.println();
			
			trial++;
		}
		s.close();
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1) {
			throw new IllegalArgumentException("Need 2 or more points to compare");
		}
		
		Point[] p = new Point[numPts];
		
		for(int i = 0; i < numPts; i++) {
			int x = rand.nextInt(101) - 50;
			int y = rand.nextInt(101) - 50;
			p[i] = new Point(x, y);
		}
		
		return p;  
	}
	
}
