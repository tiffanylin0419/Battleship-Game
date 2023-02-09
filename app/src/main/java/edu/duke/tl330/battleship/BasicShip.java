package edu.duke.tl330.battleship;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {
  // if myPieces.get(c) is null, c is not part of this Ship
  // if myPieces.get(c) is false, c is part of this ship and has not been hit
  // if myPieces.get(c) is true, c is part of this ship and has been hit
  protected HashMap<Coordinate, Boolean> myPieces;
  protected ArrayList<Coordinate> offset;

  protected ShipDisplayInfo<T> myDisplayInfo;
  protected ShipDisplayInfo<T> enemyDisplayInfo;

  // constructor with Iterable<Coordinate>
  public BasicShip(Iterable<Coordinate> where, ArrayList<Coordinate> offset, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    this.myPieces = new HashMap<Coordinate, Boolean>();
    this.offset = offset;

    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
    for (Coordinate c : where) {
      this.myPieces.put(c, false);
    }
  }

  @Override
  public Coordinate getUpperLeft() {
    int x = -1, y = -1;
    for (Coordinate c : myPieces.keySet()) {
      if (x == -1 || c.getColumn() < x) {
        x = c.getColumn();
      }
      if (y == -1 || c.getRow() < y) {
        y = c.getRow();
      }
    }
    return new Coordinate(y, x);
  }

  @Override
  public ArrayList<Coordinate> getOffset(){
    return offset;
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
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    checkCoordinateInThisShip(where);
    boolean hit = wasHitAt(where);
    if (myShip) {
      return myDisplayInfo.getInfo(where, hit);
    }
    return enemyDisplayInfo.getInfo(where, hit);
  }

  @Override
  public Iterable<Coordinate> getCoordinates() {
    return myPieces.keySet();
  }
}
