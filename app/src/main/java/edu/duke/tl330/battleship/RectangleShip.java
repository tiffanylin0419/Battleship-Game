package edu.duke.tl330.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  private final String name;

  public String getName() {
    return name;
  }

  //constructor
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T>enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo,enemyDisplayInfo);
    this.name=name;
    
  }

  //constructor
  public RectangleShip(String name,Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name,upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }

  // constructor for 1*1, only used for testing
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship",upperLeft, 1, 1, data, onHit);
    
  }

  // return the coords where the ship occupies
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int r = upperLeft.getRow();
        int c = upperLeft.getColumn();
        coords.add(new Coordinate(r + j, c + i));
      }
    }
    return coords;
  }

}
