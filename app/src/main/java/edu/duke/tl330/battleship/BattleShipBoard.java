package edu.duke.tl330.battleship;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class implements a board for battleship game.
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashSet<Coordinate> enemyMisses;
  final T missInfo;

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Constructs a BattleShipBoard with the specified width and height
   *
   * @param w is the width of the newly constructed board.
   * @param h is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new NoCollisionRuleChecker<T>(new InBoundsRuleChecker<T>(null)), missInfo);// new
    // NoCollisionRuleChecker<>(new
    // InBoundsRuleChecker<T>(null))
  }

  /**
   * Constructs a BattleShipBoard with the specified width and height
   * 
   * @param w                is the width of the newly constructed board.
   * @param h                is the height of the newly constructed board.
   * @param placementChecker is an object used to check the rules.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */
  public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width = w;
    this.height = h;
    this.myShips = new ArrayList<Ship<T>>();
    this.placementChecker = placementChecker;
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
  }

  @Override
  public String tryAddShip(Ship<T> toAdd) {
    String s = placementChecker.checkPlacement(toAdd, this);
    if (s == null) {
      myShips.add(toAdd);
    }
    return s;
  }

  /**
   * @param takes a Coordinate,
   *              sees which (if any) Shipvoccupies that coordinate.
   *              If one is found, we return whatever displayInfo it has at those
   *              coordinates (for now, just 's').
   *              If none is found, we return null.
   */
  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }

  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);

  }

  //true if all ship is sunk
  @Override
  public boolean noShips() {
    for (Ship<T> i : myShips) {
      if (!i.isSunk()) {
        return false;
      }
    }
    return true;
  }

  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    if (isSelf == false && enemyMisses.contains(where)) {
      return missInfo;// 'X'
    }
    return null;
  }

  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;
  }
}
