package edu.duke.tl330.battleship;

public interface Board<T> {
  public int getWidth();
  public int getHeight();
  public String tryAddShip(Ship<T> toAdd);
  public String tryMoveShip(Ship<T> oldShip,Ship<T> newShip);
  public int scanFor(Coordinate coord,T ch);
  public T whatIsAtForSelf(Coordinate where);
  public T whatIsAtForEnemy(Coordinate where);
  public Ship<T> getShipAt(Coordinate c);
  public Ship<T> fireAt(Coordinate c);
  public boolean noShips();
}

