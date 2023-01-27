package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords_width() {
    Coordinate c = new Coordinate(1, 2);
    HashSet<Coordinate> cc = new HashSet<Coordinate>();
    cc.add(new Coordinate(1, 2));
    cc.add(new Coordinate(2, 2));
    cc.add(new Coordinate(3, 2));
    assertEquals(cc, RectangleShip.makeCoords(c, 3, 1));
  }

  @Test
  public void test_makeCoords_height() {
    Coordinate c = new Coordinate(2, 3);
    HashSet<Coordinate> cc = new HashSet<Coordinate>();
    cc.add(new Coordinate(2, 3));
    cc.add(new Coordinate(2, 4));
    cc.add(new Coordinate(2, 5));
    cc.add(new Coordinate(2, 6));
    assertEquals(cc, RectangleShip.makeCoords(c, 1, 4));
  }

  @Test
  public void test_constructor() {
    RectangleShip<Character> rs = new RectangleShip<Character>(new Coordinate(2, 0), 1, 4, 's', '*');
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 0)), true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 1)), true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 2)), true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 3)), true);
  }

  @Test
  public void test_HitAt() {
    RectangleShip<Character> rs = new RectangleShip<Character>(new Coordinate(2, 0), 1, 4, 's', '*');
    assertThrows(IllegalArgumentException.class,()->rs.recordHitAt(new Coordinate(3, 1)));
    rs.recordHitAt(new Coordinate(2, 1));
    assertTrue(rs.wasHitAt(new Coordinate(2, 1)));
    assertFalse(rs.wasHitAt(new Coordinate(2, 0)));
    assertFalse(rs.wasHitAt(new Coordinate(2, 2)));
    assertFalse(rs.wasHitAt(new Coordinate(2, 3)));
    assertThrows(IllegalArgumentException.class,()->rs.wasHitAt(new Coordinate(2, 4)));
    assertThrows(IllegalArgumentException.class,()->rs.wasHitAt(new Coordinate(3, 1)));
  }

  @Test
  public void test_isSunk(){
    RectangleShip<Character> rs = new RectangleShip<Character>(new Coordinate(1,2), 3, 1, 's', '*');
    assertFalse(rs.isSunk());
    rs.recordHitAt(new Coordinate(2, 2));
    assertTrue(rs.isSunk());
  }

  @Test
  public void test_getDisplayInfoAt(){
    RectangleShip<Character> rs = new RectangleShip<Character>(new Coordinate(1,2), 3, 1, 's', '*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1,2)),'s');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(2,2)),'s');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(3,2)),'s');
    rs.recordHitAt(new Coordinate(2, 2));
    assertEquals(rs.getDisplayInfoAt(new Coordinate(1,2)),'s');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(2,2)),'*');
    assertEquals(rs.getDisplayInfoAt(new Coordinate(3,2)),'s');
    assertThrows(IllegalArgumentException.class,()->rs.getDisplayInfoAt(new Coordinate(3,3)));
    
  }
}
