package edu.duke.tl330.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
  private final String name;

  public String getName() {
    return name;
  }

  // constructor
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height),makeOffset(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
    this.name = name;

  }

  // constructor
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));
  }

  // constructor for 1*1, only used for testing
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);

  }

  static ArrayList<Coordinate> makeOffset(Coordinate upperLeft, int width, int height) {
    ArrayList<Coordinate> offset = new ArrayList<Coordinate>();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        offset.add(new Coordinate(j, i));
      }
    }
    return offset;
  };

  // return the coords where the ship occupies
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();

    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    for (Coordinate of : RectangleShip.makeOffset(upperLeft, width, height)) {
      coords.add(new Coordinate(r + of.getRow(), c + of.getColumn()));
    }
    return coords;
  }

}
