package edu.duke.tl330.battleship;

public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  // constructor
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }

  protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

  // Subclass don't need to consider any other rule, nor the chaining logic.
  // Instead,
  // our public method handles chaining rules together
  public boolean checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    // if we fail our own rule: stop the placement is not legal
    if (!checkMyRule(theShip, theBoard)) {
      return false;
    }
    // other wise, ask the rest of the chain.
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    // if there are no more rules, then the placement is legal
    return true;
  }
}
