package edu.duke.tl330.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class implements a board for battleship game.
 */
public class BattleShipBoard<T> implements Board<T> {
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private HashMap<Coordinate, T> enemyDisguise;
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
    this.enemyDisguise = new HashMap<Coordinate, T>();
    for(int i=0;i<width;i++){
      for(int j=0;j<height;j++){
        this.enemyDisguise.put(new Coordinate(i,j),null);
      }
    }
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

  @Override
  public String tryMoveShip(Ship<T> oldShip, Ship<T> newShip) {
    myShips.remove(oldShip);
    String s = placementChecker.checkPlacement(newShip, this);
    if (s == null) {
      int r_new = newShip.getUpperLeft().getRow();
      int c_new = newShip.getUpperLeft().getColumn();
      int r_old = oldShip.getUpperLeft().getRow();
      int c_old = oldShip.getUpperLeft().getColumn();
      for (int i = 0; i < oldShip.getOffset().size(); i++) {
        if (oldShip.wasHitAt(new Coordinate(r_old + oldShip.getOffset().get(i).getRow(),
            c_old + oldShip.getOffset().get(i).getColumn()))) {
          newShip.recordHitAt(new Coordinate(r_new + newShip.getOffset().get(i).getRow(),
              c_new + newShip.getOffset().get(i).getColumn()));
        }
      }
      myShips.add(newShip);
      return s;
    }
    myShips.add(oldShip);
    return s;
  }
  
  @Override
  public int scanFor(Coordinate coord,T ch){
    int count=0;
    for(int i=-3;i<=3;i++){
      for(int j=-3;j<=3;j++){
        if(Math.abs(i)+Math.abs(j)<=3){
          Coordinate c=new Coordinate(coord.getRow()+i,coord.getColumn()+j);
          Ship<T> s=getShipAt(c);
          if(s!=null &&( s.getDisplayInfoAt(c, true)==ch||s.getDisplayInfoAt(c, false)==ch)){
            count+=1;
          }
        }
      }
    }
    return count;
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

  // true if all ship is sunk
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
    if (isSelf == false) {
      if (enemyDisguise.containsKey(where)) {
        return enemyDisguise.get(where);
      }
    }
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where, isSelf);
      }
    }
    return null;
  }

  @Override
  public Ship<T> fireAt(Coordinate c) {
    Ship<T> s = getShipAt(c);
    if (s != null) {
      s.recordHitAt(c);
      enemyDisguise.put(c, s.getDisplayInfoAt(c, false));
    } else {
       enemyDisguise.put(c, missInfo);
    }
    return s;
  }

  @Override
  public Ship<T> getShipAt(Coordinate c) {
    for (Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        return s;
      }
    }
    return null;
  }

}
