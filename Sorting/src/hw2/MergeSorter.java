package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.InputMismatchException;


/**
 * @author Siddharthan Prakash
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "merge sort";  
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int n = pts.length;
		if (n <= 1) {
			return;
		}
		int m = n/2;
		
		Point[] a = new Point[m];
		Point[] b = new Point[n - m];
		
		for (int i = 0; i < m; i++) {
			a[i] = pts[i];
		}
		
		int k = 0;
		for (int i = m; i < n; i++) {
			b[k] = pts[i];
			k++;
		}
		
		mergeSortRec(a);
		mergeSortRec(b);
		
		Point[] temp = new Point[pts.length];
		temp = merge(a, b);
		
		for (int i = 0; i < temp.length; i++) {
			pts[i] = temp[i];
		}
	}
	
	
	/**
	 * Helper method to perform merge sort, called in mergeSortRec.
	 * This method merges two Point arrays together by comparing and 
	 * sorting the values from the given arrays. Returns a Point array that 
	 * includes the sorted values from array a and b.
	 * 
	 * @param a
	 * @param b
	 * @return Point[] c: merges two Point arrays
	 */
	private Point[] merge(Point a[], Point b[]) {
		int p = a.length;
		int q = b.length;
		
		Point c[] = new Point[p+q];
		
		int r = 0;
		int l = 0;
		int i = 0;
		
		while(r < p && l < q) {
			if(pointComparator.compare(a[r],b[l]) == -1) {
				c[i] = a[r];
				r++;
			}
			else {
				c[i] = b[l];
				l++;
			}
			i++;
		}
		while(r < p) {
			c[i] = a[r];
			r++;
			i++;
		}
		while(l < q) {
			c[i] = b[l];
			l++;
			i++;
		}
		return c;
	}


}
