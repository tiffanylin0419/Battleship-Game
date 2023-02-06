package edu.duke.tl330.battleship;

import java.util.HashSet;

public class StairShip<T> extends BasicShip<T> {
  private final String name;

  public String getName() {
    return name;
  }

  // constructor in class
  public StairShip(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  // constructor used
  public StairShip(String name, Coordinate upperLeft, char orientation, T data, T onHit) {
    this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));

  }

  // return the coords where the ship occupies
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation) {
    if (orientation == 'U') {
      return makeUpCoords(upperLeft);
    } else if (orientation == 'R') {
      return makeRightCoords(upperLeft);
    } else if (orientation == 'D') {
      return makeDownCoords(upperLeft);
    } else {// L
      return makeLeftCoords(upperLeft);
    }

  }

  static HashSet<Coordinate> makeUpCoords(Coordinate upperLeft) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    coords.add(new Coordinate(r, c ));
    coords.add(new Coordinate(r+1, c ));
    coords.add(new Coordinate(r+2, c ));
    coords.add(new Coordinate(r+2, c+1 ));
    coords.add(new Coordinate(r+3, c ));
    coords.add(new Coordinate(r+3, c+1 ));
    coords.add(new Coordinate(r+4, c+1 ));
    return coords;
  }

  static HashSet<Coordinate> makeRightCoords(Coordinate upperLeft) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    coords.add(new Coordinate(r, c+1 ));
    coords.add(new Coordinate(r, c+2 ));
    coords.add(new Coordinate(r, c+3 ));
    coords.add(new Coordinate(r, c+4 ));
    coords.add(new Coordinate(r+1, c ));
    coords.add(new Coordinate(r+1, c+1 ));
    coords.add(new Coordinate(r+1, c+2 ));
    return coords;
  }

  static HashSet<Coordinate> makeLeftCoords(Coordinate upperLeft) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    coords.add(new Coordinate(r, c+2 ));
    coords.add(new Coordinate(r, c+3 ));
    coords.add(new Coordinate(r, c+4 ));
    coords.add(new Coordinate(r+1, c ));
    coords.add(new Coordinate(r+1, c+1 ));
    coords.add(new Coordinate(r+1, c+2 ));
    coords.add(new Coordinate(r+1, c+3 ));
    return coords;
  }

  static HashSet<Coordinate> makeDownCoords(Coordinate upperLeft) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    coords.add(new Coordinate(r, c ));
    coords.add(new Coordinate(r+1, c ));
    coords.add(new Coordinate(r+1, c+1 ));
    coords.add(new Coordinate(r+2, c ));
    coords.add(new Coordinate(r+2, c+1 ));
    coords.add(new Coordinate(r+3, c+1 ));
    coords.add(new Coordinate(r+4, c+1 ));
    return coords;
  }

}

