package edu.duke.tl330.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display.
   * 
   * @param toDisplay is the Board to display
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep = ""; // start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This makes the whole board
   * display my board
   * 
   * @return the String that is the given board
   */
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }

  /**
   * This makes the whole board
   * display enemy board
   * 
   * @return the String that is the given board
   */
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  // can display own or enemy board
  // public String displayMyOwnBoard() {
  protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
    StringBuilder ans = new StringBuilder("");
    ans.append(makeHeader());
    for (int i = 0; i < toDisplay.getHeight(); i++) {
      char letter = (char) (i + 65);
      ans.append(letter + " ");
      StringBuilder line = new StringBuilder("");
      String sep = "";
      for (int j = 0; j < toDisplay.getWidth(); j++) {
        line.append(sep);
        Character c = getSquareFn.apply(new Coordinate(i, j));
        if (c != null) {
          line.append(c);
        } else {
          line.append(" ");
        }
        sep = "|";
      }
      ans.append(line + " " + letter + "\n");
    }
    ans.append(makeHeader());
    return ans.toString(); // this is a placeholder for the moment
  }

  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    String s = "Player " + myHeader + "'s turn:\n" +
        "     " + "Your ocean" + "                           Player " + enemyHeader + "'s ocean\n";
    String[] line1 = displayMyOwnBoard().split("\n");
    String[] line2 = enemyView.displayEnemyBoard().split("\n");
    s += line1[0] + "                  " + line2[0] + "\n";
    for (int i = 1; i < line1.length - 1; i++) {
      s += line1[i] + "                " + line2[i] + "\n";
    }
    s += line1[line1.length - 1] + "                  " + line2[line1.length - 1] + "\n";
    return s;

  }
}
