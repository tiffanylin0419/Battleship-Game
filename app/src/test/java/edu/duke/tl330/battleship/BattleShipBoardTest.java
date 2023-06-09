package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  private <T> void checkWhatIsAtBoardSelf(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); i++) {
      for (int j = 0; j < b.getHeight(); j++) {
        assertEquals(b.whatIsAtForSelf(new Coordinate(i, j)), expected[i][j]);
      }
    }
  }

  private <T> void checkWhatIsAtBoardEnemy(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < b.getWidth(); i++) {
      for (int j = 0; j < b.getHeight(); j++) {
        assertEquals(b.whatIsAtForEnemy(new Coordinate(i, j)), expected[i][j]);
      }
    }
  }

  @Test
  public void test_whatIsAtForEnemy() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 3, 'X');
    Character[][] arr = { { null, null, null }, { null, null, null }, { null, null, null } };
    Ship<Character> s1 = new RectangleShip<Character>(new Coordinate(0, 1), 's', '*');
    b1.tryAddShip(s1);
    checkWhatIsAtBoardEnemy(b1, arr);
    b1.fireAt(new Coordinate(0, 1));
    arr[0][1] = 's';
    checkWhatIsAtBoardEnemy(b1, arr);
    b1.fireAt(new Coordinate(0, 2));
    arr[0][2] = 'X';
    checkWhatIsAtBoardEnemy(b1, arr);
  }

  @Test
  public void test_tryAddShip() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 3, 'X');
    Character[][] arr = { { null, null, null }, { null, null, null }, { null, null, null } };
    checkWhatIsAtBoardSelf(b1, arr);
    Ship<Character> s1 = new RectangleShip<Character>(new Coordinate(0, 1), 's', '*');
    assertEquals(null, b1.tryAddShip(s1));
    arr[0][1] = 's';
    checkWhatIsAtBoardSelf(b1, arr);
    Ship<Character> s2 = new RectangleShip<Character>(new Coordinate(2, 2), 's', '*');
    assertEquals(null, b1.tryAddShip(s2));
    arr[2][2] = 's';
    checkWhatIsAtBoardSelf(b1, arr);

    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s3 = sf.makeDestroyer(new Placement("B0H"));
    assertEquals(null, b1.tryAddShip(s3));
    arr[1][0] = 'd';
    arr[1][1] = 'd';
    arr[1][2] = 'd';
    checkWhatIsAtBoardSelf(b1, arr);

    Ship<Character> s4 = sf.makeSubmarine(new Placement("A2H"));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", b1.tryAddShip(s4));
    Ship<Character> s5 = sf.makeSubmarine(new Placement("C1H"));
    assertEquals("That placement is invalid: the ship overlaps another ship.", b1.tryAddShip(s5));

    checkWhatIsAtBoardSelf(b1, arr);
  }

  private void test_hit(Board<Character> b, Ship<Character> s1, Ship<Character> s2) {
    assertSame(s1, s2);
    assertTrue(s1.isSunk());
  }

  private void test_noHit(Ship<Character> s1) {
    assertFalse(s1.isSunk());
  }

  @Test
  public void test_fireAt() {
    Board<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeSubmarine(new Placement("A0H"));
    Ship<Character> s2 = sf.makeDestroyer(new Placement("B3V"));
    Ship<Character> s3 = sf.makeBattleship(new Placement("B1L"));
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    b.tryAddShip(s3);

    test_noHit(s1);
    test_noHit(s2);
    test_noHit(s3);
    test_hit(b, s1, b.fireAt(new Coordinate(0, 0)));
    test_hit(b, s2, b.fireAt(new Coordinate(3, 3)));
    test_hit(b, s3, b.fireAt(new Coordinate(3, 2)));
  }

  @Test
  public void test_noShips() {
    Board<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeSubmarine(new Placement("A0H"));
    Ship<Character> s2 = sf.makeDestroyer(new Placement("B3V"));
    Ship<Character> s3 = sf.makeBattleship(new Placement("B1L"));
    b.tryAddShip(s1);
    b.tryAddShip(s2);
    b.tryAddShip(s3);

    assertFalse(b.noShips());

    b.fireAt(new Coordinate(0, 0));
    b.fireAt(new Coordinate(3, 3));
    b.fireAt(new Coordinate(3, 2));
    assertTrue(b.noShips());
  }

  @Test
  public void test_tryMoveShip() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeBattleship(new Placement("A0U"));
    b.tryAddShip(s1);
    b.fireAt(new Coordinate(1, 1));

    Ship<Character> s2 = sf.makeBattleship(new Placement("B1R"));
    assertEquals(null, b.tryMoveShip(s1, s2));
    Character[][] arr_self = {
        { null, null, null, null, null },
        { null, 'b', null, null, null },
        { null, '*', 'b', null, null },
        { null, 'b', null, null, null },
        { null, null, null, null, null } };

    checkWhatIsAtBoardSelf(b, arr_self);

    Character[][] arr_enemy = {
        { null, null, null, null, null },
        { null, 'b', null, null, null },
        { null, null, null, null, null },
        { null, null, null, null, null },
        { null, null, null, null, null } };

    checkWhatIsAtBoardEnemy(b, arr_enemy);
    b.fireAt(new Coordinate(0, 1));

    arr_enemy[0][1]='X';
    checkWhatIsAtBoardSelf(b, arr_self);
    checkWhatIsAtBoardEnemy(b, arr_enemy);

    Ship<Character> s3 = sf.makeBattleship(new Placement("D1R"));
    assertEquals("That placement is invalid: the ship overlaps another ship.", b.tryMoveShip(s1, s3));
  }

  
  @Test
  public void test_scanFor(){
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(5, 5, 'X');
    V1ShipFactory sf = new V1ShipFactory();
    Ship<Character> s1 = sf.makeCarrier(new Placement("A0U"));
    b.tryAddShip(s1);
     assertEquals(1,b.scanFor(new Coordinate(4,4),'c'));
    assertEquals(0,b.scanFor(new Coordinate(4,4),'d'));
    assertEquals(4,b.scanFor(new Coordinate(3,3),'c'));
    assertEquals(6,b.scanFor(new Coordinate(2,2),'c'));
  }
}
