package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Character> ssdi=new SimpleShipDisplayInfo<Character>('d','x');
    assertEquals(ssdi.getInfo(new Coordinate(2,3),true),'x');
    assertEquals(ssdi.getInfo(new Coordinate(2,3),false),'d');
  }

}
