package hw3;
import java.util.ArrayList;
import java.util.Random;

import api.Descriptor;
import api.Direction;
import api.MoveResult;
import api.Shift;
import api.TilePosition;

/**
 * The Powers class contains the state and logic for an implementation of a game
 * similar to "2048".  The basic underlying state is an n by n grid of tiles, 
 * represented by integer values.  A zero in a cell is considered to be
 * "empty", and all other cells contain some power of two.  The game is played
 * by calling the method <code>shiftGrid()</code>, selecting one of the four
 * directions (LEFT, RIGHT, UP, DOWN). Each row or column is then collapsed
 * according to the algorithm described in the methods of <code>ShiftUtil</code>.
 * <p>
 * Whenever two cells are <em>merged</em>, the score is increased by the 
 * combined value of the two cells.
 * 
 * @author Siddharthan Prakash
 */
public class Powers
{
	/**
	 * Object created from ShiftUtil class
	 */
	private ShiftUtil shift;
	
	/**
	 * Generates new tile position and values
	 * when the game starts and each time tiles 
	 * are moved
	 */
	private Random rand;
	
	/**
	 * 2D array that represents the game grid
	 */
	private int[][] panel;
	
	/**
	 * Represents the score the user has during the game
	 */
	private int score;
  
  
  /**
   * Constructs a game with a grid of the given size, using a default
   * random number generator.  Initially there should be two
   * nonempty cells in the grid selected by the method <code>generateTile()</code>.
   * @param givenSize
   *   size of the grid for this game
   * @param givenUtil
   *   instance of ShiftUtil to be used in this game
   */
  public Powers(int givenSize, ShiftUtil givenUtil)
  {
    shift = givenUtil;
    panel = new int [givenSize][givenSize];
    score = 0;
    rand = new Random();
  }
  
  /**
   * Constructs a game with a grid of the given size, using the given
   * instance of <code>Random</code> for its random number generator.
   * Initially there should be two nonempty cells in the grid selected 
   * by the method <code>generateTile()</code>.
   * @param givenSize
   *   size of the grid for this game
   * @param givenUtil
   *   instance of ShiftUtil to be used in this game
   * @param givenRandom
   *   given instance of Random
   */
  public Powers(int givenSize, ShiftUtil givenUtil, Random givenRandom)
  {
	  shift = givenUtil;
	  panel = new int [givenSize][givenSize];
	  score = 0;
	  rand = givenRandom;
	  
	  
	  generateTile();
	  generateTile();
	  
	  
	  
  }
  
  /**
   * Returns the value in the cell at the given row and column.
   * @param row
   *   given row
   * @param col
   *   given column
   * @return
   *   value in the cell at the given row and column
   */
  public int getTileValue(int row, int col)
  {
    return panel[row][col];
  }
  
  /**
   * Sets the value of the cell at the given row and column.
   * <em>NOTE: This method should not normally be used by clients outside
   * of a testing environment.</em>
   * @param row
   *   given row
   * @param col
   *   given col
   * @param value
   *   value to be set
   */
  public void setValue(int row, int col, int value)
  {
    panel[row][col] = value;
  }
  
  /**
   * Returns the size of this game's grid.
   * @return
   *   size of the grid
   */
  public int getSize()
  {
    return panel.length;
  }
  
  /**
   * Returns the current score.
   * @return
   *   score for this game
   */
  public int getScore()
  {
    return score;
  }
  
  /**
   * Copy a row or column from the grid into a new one-dimensional array.  
   * There are four possible actions depending on the given direction:
   * <ul>
   *   <li>LEFT - the row indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array from left to right
   *   <li>RIGHT - the row indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array in reverse (from right to left)
   *   <li>UP - the column indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array from top to bottom
   *   <li>DOWN - the row indicated by the index <code>rowOrColumn</code> is
   *   copied into the new array in reverse (from bottom to top)
   * </ul>
   * @param rowOrColumn
   *   index of the row or column
   * @param dir
   *   direction from which to begin copying
   * @return
   *   array containing the row or column
   */
  public int[] getRowOrColumn(int rowOrColumn, Direction dir)
  {
    int[] arr = new int[getSize()];
    
    if (dir == Direction.DOWN) {
    	int count = 0;
    	for (int i = getSize() - 1; i >= 0; i--) {
    		arr[count] = panel[i][rowOrColumn];
    		count++;
    	}
    }
    else if (dir == Direction.UP) {
    	int count = 0;
    	for (int i = 0; i < getSize(); i++) {
    		arr[count] = panel[i][rowOrColumn];
    		count++;
    	}
    }
    else if (dir == Direction.LEFT) {
    	int count = 0;
    	for (int i = 0; i < getSize(); i++) {
    		arr[count] = panel[rowOrColumn][i];
    		count++;
    	}
    }
    else {
    	int count = 0;
    	for (int i = getSize() - 1; i >= 0; i--) {
    		arr[count] = panel[rowOrColumn][i];
    		count++;
    	}
    }
    
    return arr;
  }
  
  /**
   * Updates the grid by copying the given one-dimensional array into
   * a row or column of the grid.
   * There are four possible actions depending on the given direction:
   * <ul>
   *   <li>LEFT - the given array is copied into the the row indicated by the 
   *   index <code>rowOrColumn</code> from left to right
   *   <li>RIGHT - the given array is copied into the the row indicated by the 
   *   index <code>rowOrColumn</code> in reverse (from right to left)
   *   <li>UP - the given array is copied into the column indicated by the 
   *   index <code>rowOrColumn</code> from top to bottom
   *   <li>DOWN - the given array is copied into the column indicated by the 
   *   index <code>rowOrColumn</code> in reverse (from bottom to top)
   * </ul>
   * @param arr
   *   the array from which to copy
   * @param rowOrColumn
   *   index of the row or column
   * @param dir
   *   direction from which to begin copying
   */
  public void setRowOrColumn(int[] arr, int rowOrColumn, Direction dir)
  {
	  if (dir == Direction.DOWN) {
	    	int count = 0;
	    	for (int i = getSize() - 1; i >= 0; i--) {
	    		panel[i][rowOrColumn] = arr[count];
	    		count++;
	    	}
	    }
	    else if (dir == Direction.UP) {
	    	int count = 0;
	    	for (int i = 0; i < getSize(); i++) {
	    		panel[i][rowOrColumn] = arr[count];
	    		count++;
	    	}
	    }
	    else if (dir == Direction.LEFT) {
	    	int count = 0;
	    	for (int i = 0; i < getSize(); i++) {
	    		panel[rowOrColumn][i] = arr[count];
	    		count++;
	    	}
	    }
	    else {
	    	int count = 0;
	    	for (int i = getSize() - 1; i >= 0; i--) {
	    		panel[rowOrColumn][i] = arr[count];
	    		count++;
	    	}
	    }

  }

  /**
   * Plays one step of the game by shifting the grid in the given direction.
   * Returns a <code>MoveResult</code> object containing a list of Descriptor 
   * objects describing all moves performed, and a <code>TilePosition</code> object describing
   * the position and value of a newly added tile, if any. 
   * If no tiles are actually moved, the returned <code>MoveResult</code> object contains an 
   * empty list and has a null value for the new <code>TilePosition</code>.
   * <p>
   * If any tiles are moved or merged, a new tile is selected according to the 
   * <code>generate()</code> method and is added to the grid.
   * <p>
   * The shifting of each individual row or column must be performed by the 
   * method <code>shiftAll</code> of <code>ShiftUtil</code>. 
   * 
   * @param dir
   *   direction in which to shift the grid
   * @return
   *   MoveResult object containing move descriptors and new tile position
   */
  public MoveResult doMove(Direction dir)
  {
    
	MoveResult move = new MoveResult();
    Descriptor d;
    TilePosition tp;
    ArrayList<Shift> arr = new ArrayList<Shift> ();
    int[] rowOrCol = new int[getSize()];
    
    
    for (int i = 0; i < getSize(); i++) {
    	arr.addAll(shift.shiftAll(getRowOrColumn(i, dir)));
    	
    	//adds the rows or columns to the list
    	rowOrCol = getRowOrColumn(i, dir);
    	shift.shiftAll(rowOrCol);
    	setRowOrColumn(rowOrCol,i,dir);
    	
    	//Calculating the score and implementing into UI
    	for (int j = 0; j < arr.size(); j++) {
    		d = new Descriptor(arr.get(j), i, dir);
    		move.addMove(d);
    		if (d.isMerge()) {
    			score += rowOrCol[d.getNewIndex()];
    		}
    	}
    	
    	arr.clear();
    }
    
    // sets tiles
    if(move.getMoves().size() > 0) {
    	tp = generateTile();
        move.setNewTile(tp);
        panel[tp.getRow()][tp.getCol()] = tp.getValue();
    }
    
    
    return move;
  }

  /**
   * Use this game's instance of <code>Random</code> to generate
   * a new tile.  The tile's row and column must be an empty cell
   * of the grid, and the tile's value is either 2 or 4. 
   * The tile is selected in such a way that
   * <ul>
   *   <li>All empty cells of the grid are equally likely
   *   <li>The tile's value is a 2 with 90% probability and a 4 with 10% probability
   * </ul>
   * This method does not modify the grid.  If the grid has no
   * empty cells, returns null.
   * @return
   *   a new TilePosition containing the row, column, and value of the
   *   selected new tile, or null if the grid has no empty cells
   */
  public TilePosition generateTile()
  {
	TilePosition tp;
	MoveResult m = new MoveResult();
	int newVal = 2;
	rand = new Random();
	int row;
	int col;
    if (rand.nextInt(10) == 0) {
    	newVal = 4;
    }
    
    while(true) {
    	row = rand.nextInt(getSize());
    	col = rand.nextInt(getSize());
    	if (panel[row][col] == 0) {
    		panel[row][col] = newVal;
    		break;
    	}
    	
    }
    
    tp = new TilePosition(row, col, newVal);
    m.setNewTile(tp);
    
    return tp;
  }

}
  










