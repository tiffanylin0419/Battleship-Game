package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ActionCountTest {
  @Test
  public void test_canScan() {
    ActionCount a = new ActionCount();
    assertTrue(a.canScan());
    a.doScan();
    assertEquals(0, a.getScan());
    assertTrue(a.canMove());
    a.doMove();
    assertEquals(1, a.getMove());
    assertTrue(a.canMove());
    a.doMove();
    assertFalse(a.canScan());
    assertFalse(a.canMove());
  }

}
