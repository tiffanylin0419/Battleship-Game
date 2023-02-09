package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TShipTest {
  @Test
  public void test_getName() {
    TShip<Character> s1 = new TShip<Character>("Battleship", new Coordinate(1, 1), 'U', 'b', '*');
    assertEquals("Battleship", s1.getName());
  }

  @Test
  public void test_getUpperLeft() {
    Coordinate c1=new Coordinate(1, 1);
    TShip<Character> s1 = new TShip<Character>("Battleship", c1, 'U', 'b', '*');
    assertEquals(c1,s1.getUpperLeft());

    TShip<Character> s2 = new TShip<Character>("Battleship", c1, 'R', 'b', '*');
    assertEquals(c1,s2.getUpperLeft());

    TShip<Character> s3 = new TShip<Character>("Battleship", c1, 'D', 'b', '*');
    assertEquals(c1,s3.getUpperLeft());
    
    TShip<Character> s4 = new TShip<Character>("Battleship", c1, 'L', 'b', '*');
    assertEquals(c1,s4.getUpperLeft());
  }

  private void checkCoord(Ship<Character> testShip, Coordinate... expectedLocs) {
    HashSet<Coordinate> coord = new HashSet<Coordinate>(Arrays.asList(expectedLocs));
    assertEquals(testShip.getCoordinates(), coord);
  }

  @Test
  public void test_makeCoords() {
    TShip<Character> s1 = new TShip<Character>("Battleship", new Coordinate(1, 1), 'U', 'b', '*');
    checkCoord(s1, new Coordinate(1, 2), new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(2, 3));

    TShip<Character> s2 = new TShip<Character>("Battleship", new Coordinate(1, 1), 'R', 'b', '*');
    checkCoord(s2, new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(3, 1));

    TShip<Character> s3 = new TShip<Character>("Battleship", new Coordinate(1, 1), 'D', 'b', '*');
    checkCoord(s3, new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(2, 2));

    TShip<Character> s4 = new TShip<Character>("Battleship", new Coordinate(1, 1), 'L', 'b', '*');
    checkCoord(s4, new Coordinate(1, 2), new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(3, 2));
  }

  @Test
   public void test_makeOffset_up() {
    ArrayList<Coordinate> cc = new ArrayList<Coordinate>();
    cc.add(new Coordinate(0, 1));
    cc.add(new Coordinate(1, 0));
    cc.add(new Coordinate(1, 1));
    cc.add(new Coordinate(1, 2));
    assertEquals(cc, TShip.makeOffset(new Coordinate(2, 5), 'U'));
  }

   @Test
   public void test_makeOffset_right() {
      ArrayList<Coordinate> cc = new ArrayList<Coordinate>();
      cc.add(new Coordinate(1, 1));
      cc.add(new Coordinate(0, 0));
      cc.add(new Coordinate(1, 0));
      cc.add(new Coordinate(2, 0));
      assertEquals(cc, TShip.makeOffset(new Coordinate(2, 5), 'R'));
   }

  @Test
  public void test_makeOffset_down() {
    ArrayList<Coordinate> cc = new ArrayList<Coordinate>();
     cc.add(new Coordinate(1, 1));
     cc.add(new Coordinate(0, 2));
     cc.add(new Coordinate(0, 1));
     cc.add(new Coordinate(0, 0));
     
     assertEquals(cc, TShip.makeOffset(new Coordinate(2, 5), 'D'));
  }

  @Test
  public void test_makeOffset_left() {
    ArrayList<Coordinate> cc = new ArrayList<Coordinate>();
    cc.add(new Coordinate(1, 0));
    cc.add(new Coordinate(2, 1));
    cc.add(new Coordinate(1, 1));
    cc.add(new Coordinate(0, 1));
    assertEquals(cc, TShip.makeOffset(new Coordinate(2, 5), 'L'));
  }
    

}
