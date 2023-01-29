package edu.duke.tl330.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    int height = theBoard.getHeight();
    int width = theBoard.getWidth();
    for (Coordinate c : theShip.getCoordinates()) {
      if (c.getColumn() < 0 || width <= c.getColumn() || c.getRow() < 0 || height <= c.getRow()) {
        return false;
      }
    }
    return true;
  }
}
