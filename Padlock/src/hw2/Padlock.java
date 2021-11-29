package hw2;

/**
 * Model of a standard combination padlock
 * @author Siddharthan Prakash
 */
public class Padlock 
{
	/**
	 * Width of the teeth on each disc in the mechanism, expressed 
	 * in degrees of rotation.
	 */
	public static final int TOOTH = 2;
	
	/**
	 * Current position of disk1 in the padlock.
	 */
	private int disk1;
	
	/**
	 * Current position of disk2 in the padlock.
	 */
	private int disk2;
	
	/**
	 * Current position of disk3 in the padlock.
	 */
	private int disk3;
	
	/**
	 * The combination of disk1 that opens the padlock.
	 */
	private int combo1;
	
	/**
	 * The combination of disk2 that opens the padlock.
	 */
	private int combo2;
	
	/**
	 * The combination of disk3 that opens the padlock.
	 */
	private int combo3;
	
	/**
	 * Checks if padlock is open.
	 */
	private boolean open;
	
	/**
	 * Checks if disks are aligned to open the padlock.
	 */
	private boolean aligned;
	
	/**
	 * Constructs a padlock with the given combination.
	 * @param n1 - first number in combination
	 * @param n2 - second number in combination
	 * @param n3 - third number in combination
	 */
	public Padlock(int n1, int n2, int n3)
	{
		disk1 = 4;
		disk2 = 2;
		disk3 = 0;
		combo3 = normalize(n3);
		combo2 = normalize(n2 + TOOTH);
		combo1 = normalize(n1 - ( 2 * TOOTH));
		open = true;
	}
	
	/**
	 * Returns the current position of the given disc 
	 * (1, 2, or 3, where disc 3 is the front disc attached to the dial).
	 * @param which - which disc (1, 2, or 3) to return the position of
	 * @return - current position of the given disc
	 */
	public int getDiscPosition(int which)
	{
		if (which == 1) 
		{
			which = disk1;
		}
		else if (which == 2) 
		{
			which = disk2;
		}
		else if (which == 3) 
		{
			which = disk3;
		}
		else
		{
			which = -1;
		}
		
		return which;
	}
	
	/**
	 * Determines whether the lock is currently open.
	 * @return - true if the lock is open, false otherwise
	 */
	public boolean isOpen()
	{
		return open;
	}
	
	/**
	 * Returns true if all three discs are aligned, that is, for all discs the 
	 * current position is equal to the offset.
	 * @return - true if all three discs are aligned for the lock to open, false otherwise
	 */
	public boolean isAligned()
	{
		if ((disk1 == combo1) && (disk2 == combo2) && (disk3 == combo3))
		{
			aligned = true;
		}
		else 
		{
			aligned = false;
		}
		
		return aligned;
	}
	
	/**
	 * Makes the degrees positive and under 360 degrees.
	 * @param x - degrees of disk
	 * @return - the degrees in positive and under 360 degrees
	 */
	private int normalize(int x)
	{
		x = (x + 360) % 360;	//Makes degrees less than 360
		if (x < 0)				//Makes degrees positive
		{
			x += 360;
		}
		return x;
	}
	
	/**
	 * Sets the positions of the three discs to given angles, as closely as
	 * possible while ensuring the positions are valid.
	 * @param n1 - position for disc 1
	 * @param n2 - position for disc 2
	 * @param n3 - position for disc 3
	 */
	public void setPositions(int n1, int n2, int n3)
	{
		disk1 = normalize(n1);
		disk2 = normalize(n2);
		disk3 = normalize(n3);
		
		if (normalize(disk2 - disk3) < 2)
		{
			disk2++;
		}
		if (normalize(disk1 - disk2) < 2)
		{
			disk1++;
		}
	}
	
	/**
	 * Opens the lock, if possible. Does nothing unless isAligned is true.
	 */
	public void open()
	{
		isAligned();
		if (aligned)
		{
			open = true;
		}
		else
		{
			open = false;
		}
	}
	
	/**
	 * Closes the lock, whether or not the discs are aligned.
	 */
	public void close()
	{
		open = false;
	}
	
	/**
	 * Turns the dial (disc 3) the given number of degrees, where 
	 * a positive number represents a counterclockwise rotation 
	 * and a negative number represents a clockwise rotation.
	 * @param degrees - amount to turn the dial
	 */
	public void turn(int degrees)
	{
		//the difference in degrees between disk 2 and disk 3
		int difference23 = disk2 - disk3;
		//the difference in degrees between disk 1 and disk 2
		int difference12 = disk1 - disk2;
		
		if (degrees < 0)
		{
			difference23 = -difference23;
			difference12 = -difference12;
		}
		
		difference23 = normalize(difference23) - TOOTH;
		difference12 = normalize(difference12) - TOOTH;
		
		//Turning disk3
		disk3 += degrees;
		disk3 = normalize(disk3);
		
		//Turning disk2
		int change2 = 0;
		if (degrees > 0)
		{
			if (degrees > difference23)
			{
				change2 = degrees - difference23;
				disk2 += change2;
			}
		}
		else 
		{
			if (Math.abs(degrees) > difference23)
			{
				change2 = (-1) * (Math.abs(degrees) - difference23);
				disk2 += change2;
			}
		}
		disk2 = normalize(disk2);
		
		//Turning disk1
		int change1 = 0;
		if (degrees > 0)
		{
			if (change2 > difference12)
			{
				change1 = change2 - difference12;
				disk1 += change1;
			}
		}
		else
		{
			if (Math.abs(change2) > difference12)
			{
				change1 = (-1) * (Math.abs(change2) - difference12);
				disk1 += change1;
			}
		}
		disk1 = normalize(disk1);
	}
	
	/**
	 * Turns the dial (disc 3) counterclockwise until its position is the given number.
	 * @param number - new dial position
	 */
	public void turnLeftTo(int number)
	{
		number = normalize(number - disk3);
		turn(number);
	}
	
	/**
	 * Turns the dial (disc 3) clockwise until its position is the given number.
	 * @param number - new dial position
	 */
	public void turnRightTo(int number)
	{
		number = (-1)*normalize(disk3 - number);
		turn(number);
	}
	
	/**
	 * Set the three discs to random, valid positions.
	 * @param rand - Random instance to use for selecting the three positions.
	 */
	public void randomizePositions(java.util.Random rand)
	{
		int x = rand.nextInt(360);
		disk1 = x;
		disk2 = x;
		disk3 = x;
	}
	
}
