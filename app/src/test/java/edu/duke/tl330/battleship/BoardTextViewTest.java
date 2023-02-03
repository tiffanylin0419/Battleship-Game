package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expected) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    String expectedHeader = "  0|1\n";
    String expected = expectedHeader +
        "A  |  A\n" +
        "B  |  B\n" +
        expectedHeader;
    emptyBoardHelper(2, 2, expectedHeader, expected);
  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader +
        "A  | |  A\n" +
        "B  | |  B\n" +
        expectedHeader;
    emptyBoardHelper(3, 2, expectedHeader, expected);
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expected = expectedHeader +
        "A  | |  A\n" +
        "B  | |  B\n" +
        "C  | |  C\n" +
        "D  | |  D\n" +
        "E  | |  E\n" +
        expectedHeader;
    emptyBoardHelper(3, 5, expectedHeader, expected);
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  private void BoardHelperMy(BattleShipBoard<Character> b, String expected) {
    BoardTextView view = new BoardTextView(b);
    assertEquals(view.displayMyOwnBoard(), expected);
  }

  private void BoardHelperEnemy(BattleShipBoard<Character> b, String expected) {
    BoardTextView view = new BoardTextView(b);
    assertEquals(view.displayEnemyBoard(), expected);
  }
  @Test
  public void test_display_my_board_3by4() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(3, 4, 'X');
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate("B0"), 's', '*'));
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate(2, 2), 's', '*'));
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate(3, 2), 's', '*'));
    String expectedHeader = "  0|1|2\n";
    b1.fireAt(new Coordinate(2, 2));
b1.fireAt(new Coordinate(0, 0));
    String expectedMy = expectedHeader +
        "A  | |  A\n" +
        "B s| |  B\n" +
        "C  | |* C\n" +
        "D  | |s D\n" +
        expectedHeader;
    BoardHelperMy(b1, expectedMy);

    
    String expectedEnemy = expectedHeader +
        "A X| |  A\n" +
        "B  | |  B\n" +
        "C  | |s C\n" +
        "D  | |  D\n" +
        expectedHeader;
    BoardHelperEnemy(b1, expectedEnemy);


  }
}
