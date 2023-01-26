package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_where_and_orientation() {
    Placement c = new Placement(new Coordinate(1, 2), 'H');
    assertEquals(c.getOrientation(), 'H');
    assertEquals(c.getWhere(), new Coordinate(1, 2));
    assertThrows(IllegalArgumentException.class, () -> new Placement(new Coordinate(1, 2), 'B'));
  }

  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    Placement p1 = new Placement(c1, 'h');
    Placement p2 = new Placement(c2, 'H');
    Placement p3 = new Placement(c4, 'h');
    Placement p4 = new Placement(c3, 'v');
    assertEquals(p1, p1); // equals should be reflexsive
    assertEquals(p1, p2); // different objects bu same contents
    assertNotEquals(p1, p3); // different contents
    assertNotEquals(p1, p4);
    assertNotEquals(p3, p4);
    assertNotEquals(p1, "(c1, d)"); // different types
  }

  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(0, 3);
    Coordinate c4 = new Coordinate(2, 1);
    Placement p1 = new Placement(c1, 'h');
    Placement p2 = new Placement(c2, 'H');
    Placement p3 = new Placement(c4, 'h');
    Placement p4 = new Placement(c3, 'v');

    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
  }

  @Test
  void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("A0V");
    assertEquals(new Coordinate(0, 0), p1.getWhere());
    assertEquals('V', p1.getOrientation());
    Placement p2 = new Placement("Z0v");
    assertEquals(new Coordinate(25, 0), p2.getWhere());
    assertEquals('V', p2.getOrientation());
    Placement p3 = new Placement("A9H");
    assertEquals(new Coordinate(0, 9), p3.getWhere());
    assertEquals('H', p3.getOrientation());
    Placement p4 = new Placement("c5h");
    assertEquals(new Coordinate(2, 5), p4.getWhere());
    assertEquals('H', p4.getOrientation());

  }

  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("00"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("10"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("30"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("a'h"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("s00v"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("."));
    assertThrows(IllegalArgumentException.class, () -> new Placement("eev"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("f0f"));
  }

}
