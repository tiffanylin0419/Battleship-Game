package edu.duke.tl330.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private final T myData;
  private final T onHit;

  public SimpleShipDisplayInfo(T md, T oh) {
    this.myData = md;
    this.onHit = oh;
  }

  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if(hit){
      return onHit;
    }
    return myData;
  }
}
