package edu.duke.tl330.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {

  public RectangleShip(Coordinate upperLeft, int width, int height,ShipDisplayInfo<T> myDisplayInfo) {
    super(makeCoords(upperLeft, width, height),myDisplayInfo);
  }

  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft, 1, 1, data, onHit);
  }
  
  // return the coords where the ship occupies
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int r = upperLeft.getRow();
        int c = upperLeft.getColumn();
        coords.add(new Coordinate(r + i, c + j));
      }
    }
    return coords;
  }
}
