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
    this.myDisplayInfo = myDisplayInfo;
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
  }

  protected void checkCoordinateInThisShip(Coordinate c) {
    if (myPieces.get(c) == null) {
      throw new IllegalArgumentException("Coordinate " + c + " not in ship.");
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
    for (boolean value : myPieces.values()) {
      if (value) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where) == true;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    boolean hit = wasHitAt(where);
    return myDisplayInfo.getInfo(where, hit);
  }

  @Override
  public Iterable<Coordinate> getCoordinates(){
    return myPieces.keySet();
  }
}
