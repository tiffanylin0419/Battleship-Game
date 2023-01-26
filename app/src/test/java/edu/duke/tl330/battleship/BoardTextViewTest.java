package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expected){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    String expectedHeader= "  0|1\n";
    String expected=
      expectedHeader+
      "A  |  A\n"+
      "B  |  B\n"+
      expectedHeader;
    emptyBoardHelper(2, 2, expectedHeader, expected);
  }

  @Test
  public void test_display_empty_3by2(){
    String expectedHeader= "  0|1|2\n";
    String expected=
      expectedHeader+
      "A  |  |  A\n"+
      "B  |  |  B\n"+
      expectedHeader;
    emptyBoardHelper(3, 2, expectedHeader, expected);
  }
  
  @Test
  public void test_display_empty_3by5(){
    String expectedHeader= "  0|1|2\n";
    String expected=
      expectedHeader+
      "A  |  |  A\n"+
      "B  |  |  B\n"+
      "C  |  |  C\n"+
      "D  |  |  D\n"+
      "E  |  |  E\n"+
      expectedHeader;
    emptyBoardHelper(3, 5, expectedHeader, expected);
  }
  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

}
