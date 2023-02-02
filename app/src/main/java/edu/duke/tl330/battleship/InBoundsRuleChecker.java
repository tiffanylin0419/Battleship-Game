package edu.duke.tl330.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  // constructor
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    int height = theBoard.getHeight();
    int width = theBoard.getWidth();
    for (Coordinate c : theShip.getCoordinates()) {
      if (c.getColumn() < 0) {
        return "That placement is invalid: the ship goes off the left of the board.";
      }
      if (width <= c.getColumn()) {
        return "That placement is invalid: the ship goes off the right of the board.";
      }
      if (c.getRow() < 0) {
        return "That placement is invalid: the ship goes off the top of the board.";
      }
      if (height <= c.getRow()) {
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
    }
    return null;
  }
}
