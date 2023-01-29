package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
  }

  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); i++) {
      for (int j = 0; j < b.getHeight(); j++) {
        assertEquals(b.whatIsAt(new Coordinate(i, j)), expected[i][j]);
      }
    }
  }

  @Test
  public void test_tryAddShip() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 3);
    Character[][] arr = { { null, null, null }, { null, null, null }, { null, null, null } };
    checkWhatIsAtBoard(b1, arr);
    Ship<Character> s1 = new RectangleShip<Character>(new Coordinate(0, 1), 's', '*');
    b1.tryAddShip(s1);
    arr[0][1]='s';
    checkWhatIsAtBoard(b1, arr);
    Ship<Character> s2 = new RectangleShip<Character>(new Coordinate(2, 2), 's', '*');
    b1.tryAddShip(s2);
    arr[2][2]='s';
    checkWhatIsAtBoard(b1, arr);

    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s3 = sf.makeDestroyer(new Placement("B0H"));
    b1.tryAddShip(s3);
    arr[1][0]='d';
    arr[1][1]='d';
    arr[1][2]='d';
    checkWhatIsAtBoard(b1, arr);
    
    Ship<Character> s4 = sf.makeSubmarine(new Placement("A2H"));
    assertFalse(b1.tryAddShip(s4));
    Ship<Character> s5 = sf.makeSubmarine(new Placement("C1H"));
    assertFalse(b1.tryAddShip(s5));

    checkWhatIsAtBoard(b1, arr);
  }
}
