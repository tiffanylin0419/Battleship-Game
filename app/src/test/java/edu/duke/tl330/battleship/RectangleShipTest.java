package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  public void test_constructor(){
    RectangleShip<Character> rs=new RectangleShip<Character>(new Coordinate(2, 0),1,4,'s','*');
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 0)),true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 1)),true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 2)),true);
    assertEquals(rs.occupiesCoordinates(new Coordinate(2, 3)),true);
  }
  
  

}
