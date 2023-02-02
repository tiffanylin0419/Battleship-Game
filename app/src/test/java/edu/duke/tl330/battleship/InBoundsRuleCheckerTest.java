package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeSubmarine(new Placement("A0H"));
    Ship<Character> s2 = sf.makeBattleship(new Placement("E6V"));
    Ship<Character> s3 = sf.makeCarrier(new Placement("A1H"));

    Ship<Character> s4 = sf.makeDestroyer(new Placement("A5H"));
    Ship<Character> s5 = sf.makeSubmarine(new Placement("A6H"));
    Ship<Character> s6 = sf.makeSubmarine(new Placement("L0V"));
    Board<Character> b = new BattleShipBoard<Character>(7, 10);
    PlacementRuleChecker<Character> rc = new InBoundsRuleChecker<Character>(null);

    assertEquals(null, rc.checkPlacement(s1, b));
    assertEquals(null, rc.checkPlacement(s2, b));
    assertEquals(null, rc.checkPlacement(s3, b));


    assertEquals("That placement is invalid: the ship goes off the right of the board.", rc.checkPlacement(s4, b));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", rc.checkPlacement(s5, b));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", rc.checkPlacement(s6, b));

    Coordinate c1 = new Coordinate(-1, 2);
    Ship<Character> s7 = sf.makeSubmarine(new Placement(c1, 'v'));
    assertEquals("That placement is invalid: the ship goes off the top of the board.", rc.checkPlacement(s7, b));

    Coordinate c2 = new Coordinate(2, -1);
    Ship<Character> s8 = sf.makeSubmarine(new Placement(c2, 'v'));
     assertEquals("That placement is invalid: the ship goes off the left of the board.", rc.checkPlacement(s8, b));
                  
  }

}
