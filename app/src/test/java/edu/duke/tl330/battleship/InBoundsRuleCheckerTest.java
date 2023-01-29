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
    InBoundsRuleChecker<Character> rc =new InBoundsRuleChecker<Character>(null);
    
    assertTrue(rc.checkPlacement(s1,b));
    assertTrue(rc.checkPlacement(s2,b));
    assertTrue(rc.checkPlacement(s3,b));
    assertFalse(rc.checkPlacement(s4,b));
    assertFalse(rc.checkPlacement(s5,b));
    assertFalse(rc.checkPlacement(s6,b));

  }

}
