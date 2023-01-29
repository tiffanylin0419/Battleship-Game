package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords_width() {
    Coordinate c = new Coordinate(1, 2);
    HashSet<Coordinate> cc = new HashSet<Coordinate>();
    cc.add(new Coordinate(1, 2));
    cc.add(new Coordinate(1, 3));
    cc.add(new Coordinate(1, 4));
    assertEquals(cc, RectangleShip.makeCoords(c, 3, 1));
  }

  @Test
  public void test_makeCoords_height() {
    Coordinate c = new Coordinate(2, 3);
    HashSet<Coordinate> cc = new HashSet<Coordinate>();
    cc.add(new Coordinate(2, 3));
    cc.add(new Coordinate(3, 3));
    cc.add(new Coordinate(4, 3));
    cc.add(new Coordinate(5, 3));
    assertEquals(cc, RectangleShip.makeCoords(c, 1, 4));
  }

  @Test
  public void test_getName() {
    RectangleShip<Character> rs1 = new RectangleShip<Character>(new Coordinate(2, 0), 's', '*');
    assertEquals(rs1.getName(), "testship");
    RectangleShip<Character> rs2 = new RectangleShip<Character>("submarine", new Coordinate(2, 0), 1, 4, 's', '*');
    assertEquals(rs2.getName(), "submarine");
  }

  @Test
  public void test_constructor() {
    RectangleShip<Character> rs = new RectangleShip<Character>("submarine", new Coordinate(2, 0), 1, 4, 's', '*');
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 0)), true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(3, 0)), true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(4, 0)), true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(5, 0)), true);
  }

  @Test
  public void test_HitAt() {
    RectangleShip<Character> rs = new RectangleShip<Character>("submarine", new Coordinate(2, 0), 1, 4, 's', '*');
    assertThrows(IllegalArgumentException.class, () -> rs.recordHitAt(new Coordinate(3, 1)));
    rs.recordHitAt(new Coordinate(2, 0));
    assertTrue(rs.wasHitAt(new Coordinate(2, 0)));
    assertFalse(rs.wasHitAt(new Coordinate(3, 0)));
    assertFalse(rs.wasHitAt(new Coordinate(4, 0)));
    assertFalse(rs.wasHitAt(new Coordinate(5, 0)));
    assertThrows(IllegalArgumentException.class, () -> rs.wasHitAt(new Coordinate(6, 0)));
    assertThrows(IllegalArgumentException.class, () -> rs.wasHitAt(new Coordinate(3, 1)));
  }

  @Test
  public void test_isSunk() {
    RectangleShip<Character> rs = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 3, 1, 's', '*');
    assertFalse(rs.isSunk());
    rs.recordHitAt(new Coordinate(1, 3));
    assertTrue(rs.isSunk());
  }

  @Test
  public void test_getDisplayInfoAt() {
    RectangleShip<Character> rs = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 3, 1, 's', '*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1, 2)), 's');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1, 3)), 's');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1, 4)), 's');
    rs.recordHitAt(new Coordinate(1, 3));
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1, 2)), 's');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1, 3)), '*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1, 4)), 's');
    assertThrows(IllegalArgumentException.class, () -> rs.getDisplayInfoAt(new Coordinate(3, 3)));

  }

  private void checkCoord(Ship<Character> testShip, Coordinate... expectedLocs) {
    HashSet<Coordinate> coord = new HashSet<Coordinate>(Arrays.asList(expectedLocs));
    assertEquals(testShip.getCoordinates(), coord);
  }

  @Test
  public void test_getCoordinates() {
    RectangleShip<Character> rs1 = new RectangleShip<Character>("submarine", new Coordinate(1, 2), 3, 1, 's', '*');
    checkCoord(rs1,new Coordinate(1, 2),new Coordinate(1, 3),new Coordinate(1, 4));
    RectangleShip<Character> rs2 = new RectangleShip<Character>("submarine", new Coordinate(5, 4), 1, 4, 's', '*');
    checkCoord(rs2,new Coordinate(5, 4),new Coordinate(6, 4),new Coordinate(7, 4),new Coordinate(8, 4));
    
  }
}
