package edu.duke.tl330.battleship;

public interface Board<T> {
  public int getWidth();
  public int getHeight();
  public String tryAddShip(Ship<T> toAdd);
  public T whatIsAt(Coordinate where);
  public Ship<T> fireAt(Coordinate c);
}

