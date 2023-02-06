package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class StairShipTest {
  @Test
  public void test_getName() {
    StairShip<Character> s1 = new StairShip<Character>("Carrier", new Coordinate(1, 1), 'U', 'c', '*');
    assertEquals("Carrier", s1.getName());
  }

  private void checkCoord(Ship<Character> testShip, Coordinate... expectedLocs) {
    HashSet<Coordinate> coord = new HashSet<Coordinate>(Arrays.asList(expectedLocs));
    assertEquals(testShip.getCoordinates(), coord);
  }

  @Test
  public void test_makeUpCoords() {
    StairShip<Character> s1 = new StairShip<Character>("Carrier", new Coordinate(1, 1), 'U', 'c', '*');
    checkCoord(s1, new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(3, 1), new Coordinate(3, 2), new Coordinate(4, 1), new Coordinate(4, 2), new Coordinate(5, 2));

    StairShip<Character> s2 = new StairShip<Character>("Carrier", new Coordinate(1, 1), 'R', 'c', '*');
    checkCoord(s2, new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(2, 3));

    StairShip<Character> s3 = new StairShip<Character>("Carrier", new Coordinate(1, 1), 'D', 'c', '*');
    checkCoord(s3, new Coordinate(1, 1), new Coordinate(2,1), new Coordinate(2, 2), new Coordinate(3, 1), new Coordinate(3, 2), new Coordinate(4, 2), new Coordinate(5, 2));

    StairShip<Character> s4 = new StairShip<Character>("Carrier", new Coordinate(1, 1), 'L', 'c', '*');
    checkCoord(s4, new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(2, 3), new Coordinate(2, 4));
  }

}
