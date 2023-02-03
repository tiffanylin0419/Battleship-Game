package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  private void success_add(V1ShipFactory sf,Board<Character> b,PlacementRuleChecker<Character> rc,String str){
    Ship<Character> s = sf.makeDestroyer(new Placement(str));
    assertEquals(null,rc.checkPlacement(s, b));
    b.tryAddShip(s);
  }

  private void failure_add(V1ShipFactory sf,Board<Character> b,PlacementRuleChecker<Character> rc,String str){
   Ship<Character> s = sf.makeDestroyer(new Placement(str));
   assertEquals("That placement is invalid: the ship overlaps another ship.",rc.checkPlacement(s, b));
  }

  @Test
  public void test_checkMyRule() {
    V1ShipFactory sf = new V1ShipFactory();
    PlacementRuleChecker<Character> rc = new NoCollisionRuleChecker<Character>(null);
    Board<Character> b = new BattleShipBoard<Character>(7, 10,'X');
    success_add(sf,b,rc,"A0H");
    success_add(sf,b,rc,"B2V");
    success_add(sf,b,rc,"C3H");
    success_add(sf,b,rc,"D5V");

    failure_add(sf,b,rc,"F3H");
    failure_add(sf,b,rc,"B4V");
    failure_add(sf,b,rc,"D0H");
  }
  
  @Test
  public void test_checkMyRule_2(){
    V1ShipFactory sf = new V1ShipFactory();
    PlacementRuleChecker<Character> rc = new NoCollisionRuleChecker<Character>(new InBoundsRuleChecker<Character>(null));
    Board<Character> b = new BattleShipBoard<Character>(7, 10,'X');
    
    success_add(sf,b,rc,"A0H");
    success_add(sf,b,rc,"B2V");
    success_add(sf,b,rc,"C3H");
    success_add(sf,b,rc,"D5V");
    
    failure_add(sf,b,rc,"E4H");
    failure_add(sf,b,rc,"B3V");
    failure_add(sf,b,rc,"B4V");
    }
}
