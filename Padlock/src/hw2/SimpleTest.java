package hw2;

/**
 * A few usage examples for the Padlock class...
 */
public class SimpleTest
{
  public static void main(String[] args)
  {
    // try getting and setting the disc positions
    Padlock p = new Padlock(10, 20, 30);
    printPositions(p);                   // expected 4 2 0
    p.setPositions(42, 137, 17);         // order of args is back to front (disc 1, 2, 3)
    printPositions(p);                   // expected 42 137 17
    
    // Can we normalize the angles?
    p.setPositions(-90, 800, 42);
    printPositions(p);                   // expected 270 80 42
    System.out.println();
    
    // Try out the locking logic 
    // Should initially be open
    System.out.println(p.isOpen());      // expected true
    p.close();
    System.out.println(p.isOpen());      // expected false
    
    // Shouldn't open, since discs are not aligned
    System.out.println(p.isAligned());   // expected false
    p.open();
    System.out.println(p.isOpen());      // expected false
 
    // But should open if we align the discs
    // Since the given combo was 10, 20, 30, and tooth width
    // is 2, the offsets should be 6, 22, 30
    p.setPositions(6, 22, 30);  
    System.out.println(p.isAligned());   // expected true

    // now we should be able to open it
    p.open();
    System.out.println(p.isOpen());      // expected true
    System.out.println();
    
    // Try out the turn() method
    // first test - make sure the front disc position
    // ends up correct
    p.setPositions(4, 2, 0);
    p.turn(10);
    System.out.println(p.getDiscPosition(3)); // expected 10
    p.turn(-100);
    System.out.println(p.getDiscPosition(3));  // expected 270
    p.turn(800);
    System.out.println(p.getDiscPosition(3));  // expected 350
    System.out.println();
    
    // Next, how does disc 3 affect disc 2? 
    // Difference when moving ccw is 90 - 30 = 60 degrees,
    // less the tooth width, leaving 58 degrees.
    // We're rotating 70 degrees ccw, so disc 2 is 
    // 12 degrees ccw to angle 102.
    p.setPositions(0, 90, 30); 
    p.turn(70);
    printPositions(p);  // expected 0 102 100
   
    // Try another one...
    // Difference when turning ccw is 20 - 350 = -330,
    // i.e. 30 degrees, less the tooth width leaves 28 degrees.
    // We're rotating 50 degrees ccw, so disc 2 is pushed 22 
    // degrees ccw.
    p.setPositions(0, 20, 350);
    p.turn(50);  
    printPositions(p);  // expected 0 42 40
    System.out.println();
    
    // Try similar examples with clockwise rotation
    // Difference when moving clockwise is 20 - 350 = -330 or
    // 30 degrees less tooth width leaves 28 degrees. 
    // We're rotating 40 degrees cw, so disc 2 will be pushed
    // 12 degrees cw.
    // Disc 3 ends up at 20 - 40 = -20 or 340 degrees.
    // Disc 2 ends up at 350 - 12 = 338 degrees.
    p.setPositions(0, 350, 20);
    p.turn(-40);  
    printPositions(p);  // expected 0 338 340
    System.out.println();

    
    // Difference when moving clockwise is 30 - 40 which is -10 or
    // 350 degrees, less the tooth width is 348.
    // We're rotating 400 degrees clockwise, so disc 2 is 
    // pushed 52 degrees cw.
    // Disc 3 ends up at 30 - 400 = -370 or 350.
    // Disc 2 ends up at 40 - 52 = -12 or 348.  
    p.setPositions(45, 40, 30); 
    p.turn(-400);
    printPositions(p);  // expected 45 348 350
    
    // What about disc 1?
    // Same example as above, rotating clockwise, but 
    // disc 1 starts at 10 degrees.
    // Clockwise difference between disc 1 and disc 2 is 
    // 40 - 10 = 30, less the tooth width is 28.
    // From previous example, disc 2 is rotating 52 degrees cw, 
    // so it will push disc 1 clockwise 24 degrees.
    // New disc 1 position is 10 - 24 = -44 or 346.
    p.setPositions(10, 40, 30); 
    p.turn(-400); 
    printPositions(p);  // expected 346 348 350
    System.out.println();
    
    // Try out turn-to-number methods with the combination 10, 20, 30
    p.turn(-720);       // spin it twice clockwise...
    p.turnRightTo(10);  // ... and continue clockwise to 10
    p.turn(360 - Padlock.TOOTH);  // spin left a full revolution...
    p.turnLeftTo(20);             // ... continue to 20
    p.turnRightTo(30);  // and turn left to third number
    printPositions(p); // 6, 22, 30
    System.out.println(p.isAligned());  // expected true
  }
  
  private static void printPositions(Padlock p)
  {
    int c = p.getDiscPosition(3);
    int b = p.getDiscPosition(2);
    int a = p.getDiscPosition(1);
    System.out.println(a + " " + b + " " + c);
  }
}
