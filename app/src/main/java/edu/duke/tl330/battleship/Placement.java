package edu.duke.tl330.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;

  public Coordinate getWhere() {
    return where;
  }

  public char getOrientation() {
    return orientation;
  }

  //must be h,H or v,V
  public void checkOrientation(char c){
    if(c=='H' || c=='V'){return;}
    else{
      throw new IllegalArgumentException("Orientation has to be h, H, v, V\n");
    }
  }
  // Constructor
  // always keep orientation uppercase
  public Placement(Coordinate where, char orientation) {
    orientation = Character.toUpperCase(orientation);
    this.where = where;
    checkOrientation(orientation);
    this.orientation = orientation;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Placement otherPoint = (Placement) other;// casting
      return where.equals(otherPoint.getWhere()) && orientation == otherPoint.getOrientation();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    return "(" + where.toString() + ", " + orientation + ")";
  }

  // Constructor from string
  // always keep orientation uppercase
  public Placement(String descr) {
    descr = descr.toUpperCase();
    if (descr.length() != 3) {
      throw new IllegalArgumentException("Input length should be of length 3\n");
    }
    char letter = descr.charAt(2);
    this.where = new Coordinate(descr.substring(0, 2));
    checkOrientation(descr.charAt(2));
    this.orientation = descr.charAt(2);
  }

}
