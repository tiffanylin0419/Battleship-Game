package edu.duke.tl330.battleship;

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  // if myPieces.get(c) is null, c is not part of this Ship
  // if myPieces.get(c) is false, c is part of this ship and has not been hit
  // if myPieces.get(c) is true, c is part of this ship and has been hit
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;

  // constructor with Iterable<Coordinate>
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
    this.myPieces = new HashMap<Coordinate, Boolean>();
    this.myDisplayInfo=myDisplayInfo;
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    if (myPieces.get(where) == null) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    //TODO this is not right.  We need to
    //look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }

}
