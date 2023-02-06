package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName,
      char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);
    assertEquals(testShip.getDisplayInfoAt(expectedLocs[0],true), expectedLetter);
    for (Coordinate i : expectedLocs) {
      assertTrue(testShip.occupiesCoordinates(i));
    }
  }

  @Test
  public void test_makeAllBoats() {
    V1ShipFactory f = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> d = f.makeDestroyer(v1_2);
    checkShip(d, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
    Ship<Character> s = f.makeSubmarine(v1_2);
    checkShip(s, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2,2));

    Placement v4_3 = new Placement(new Coordinate(4, 3), 'L');
    Ship<Character> b = f.makeBattleship(v4_3);
    checkShip(b, "Battleship", 'b', new Coordinate(4, 4), new Coordinate(5, 3), new Coordinate(5, 4), new Coordinate(6, 4));
    Ship<Character> c = f.makeCarrier(v4_3);
    checkShip(c, "Carrier", 'c', new Coordinate(4, 5), new Coordinate(4, 6), new Coordinate(4, 7), new Coordinate(5, 3), new Coordinate(5, 4), new Coordinate(5, 5), new Coordinate(5, 6));
  }

}
