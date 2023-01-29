package edu.duke.tl330.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  // constructor
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    for (int i = 0; i < theBoard.getHeight(); i++) {
      for (int j = 0; j < theBoard.getWidth(); j++) {
        Coordinate c = new Coordinate(i, j);
        if (theBoard.whatIsAt(c) != null && theShip.occupiesCoordinates(c)) {
          return false;
        }
      }
    }
    return true;
  }

}
