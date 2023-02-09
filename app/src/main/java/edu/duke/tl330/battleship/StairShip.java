package edu.duke.tl330.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class StairShip<T> extends BasicShip<T> {
  private final String name;

  public String getName() {
    return name;
  }

  // constructor in class
  public StairShip(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> myDisplayInfo,
      ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orientation), makeOffset(upperLeft, orientation), myDisplayInfo, enemyDisplayInfo);
    this.name = name;
  }

  // constructor used
  public StairShip(String name, Coordinate upperLeft, char orientation, T data, T onHit) {
    this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit),
        new SimpleShipDisplayInfo<T>(null, data));

  }

  static ArrayList<Coordinate> makeOffset(Coordinate upperLeft, char orientation) {
    ArrayList<Coordinate> offset = new ArrayList<Coordinate>();
    if (orientation == 'U') {
      offset.add(new Coordinate(0, 0));
      offset.add(new Coordinate(1, 0));
      offset.add(new Coordinate(2, 0));
      offset.add(new Coordinate(3, 0));
      offset.add(new Coordinate(2, 1));
      offset.add(new Coordinate(3, 1));
      offset.add(new Coordinate(4, 1));
    } else if (orientation == 'D') {
      offset.add(new Coordinate(4, 1));
      offset.add(new Coordinate(3, 1));
      offset.add(new Coordinate(2, 1));
      offset.add(new Coordinate(1, 1));
      offset.add(new Coordinate(2, 0));
      offset.add(new Coordinate(1, 0));
      offset.add(new Coordinate(0, 0));
    } else if (orientation == 'R') {
      offset.add(new Coordinate(0, 4));
      offset.add(new Coordinate(0, 3));
      offset.add(new Coordinate(0, 2));
      offset.add(new Coordinate(0, 1));
      offset.add(new Coordinate(1, 2));
      offset.add(new Coordinate(1, 1));
      offset.add(new Coordinate(1, 0));
    } else {// L
      offset.add(new Coordinate(1, 0));
      offset.add(new Coordinate(1, 1));
      offset.add(new Coordinate(1, 2));
      offset.add(new Coordinate(1, 3));
      offset.add(new Coordinate(0, 2));
      offset.add(new Coordinate(0, 3));
      offset.add(new Coordinate(0, 4));
    }

    return offset;
  }

  // return the coords where the ship occupies
  static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation) {
    int r = upperLeft.getRow();
    int c = upperLeft.getColumn();
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    for (Coordinate of : StairShip.makeOffset(upperLeft, orientation)) {
      coords.add(new Coordinate(r + of.getRow(), c + of.getColumn()));
    }
    return coords;
  }

}
