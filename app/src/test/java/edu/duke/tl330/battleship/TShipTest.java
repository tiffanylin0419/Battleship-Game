package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TShipTest {
  @Test
  public void test_getName(){
     TShip<Character> s1=new TShip<Character>("Battleship",new Coordinate(1,1),'U','b','*');
     assertEquals("Battleship",s1.getName());
  }
  private void checkCoord(Ship<Character> testShip, Coordinate... expectedLocs) {
    HashSet<Coordinate> coord = new HashSet<Coordinate>(Arrays.asList(expectedLocs));
    assertEquals(testShip.getCoordinates(), coord);
  }

  
  @Test
  public void test_makeUpCoords() {
    TShip<Character> s1=new TShip<Character>("Battleship",new Coordinate(1,1),'U','b','*');
    checkCoord(s1,new Coordinate(1, 2),new Coordinate(2, 1),new Coordinate(2, 2),new Coordinate(2, 3));

    TShip<Character> s2=new TShip<Character>("Battleship",new Coordinate(1,1),'R','b','*');
checkCoord(s2,new Coordinate(1, 1),new Coordinate(2, 1),new Coordinate(2, 2),new Coordinate(3,1));

TShip<Character> s3=new TShip<Character>("Battleship",new Coordinate(1,1),'D','b','*');
checkCoord(s3,new Coordinate(1, 1),new Coordinate(1, 2),new Coordinate(1, 3),new Coordinate(2,2));

TShip<Character> s4=new TShip<Character>("Battleship",new Coordinate(1,1),'L','b','*');
checkCoord(s4,new Coordinate(1, 2),new Coordinate(2, 1),new Coordinate(2, 2),new Coordinate(3,2));
  }

}
