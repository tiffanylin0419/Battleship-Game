package edu.duke.tl330.battleship;

public class Coordinate {
  private final int row;
  private final int column;

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  // Constructor
  public Coordinate(int r, int c) {
    this.column = c;
    this.row = r;
  }

  @Override
  public boolean equals(Object other) {
    if (other != null && other.getClass().equals(getClass())) {
      Coordinate otherPoint = (Coordinate) other;// casting
      return row == otherPoint.getRow() && column == otherPoint.getColumn();
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    return "(" + row + ", " + column + ")";
  }

  // convert number character to int input'0'-'9' outpur 0-9
  private int charToInt(char c) {
    if (c < '0' || c > '9') {
      throw new IllegalArgumentException("Row number must be from 0-9\n");
    }
    return c - '0';
  }

  // Constructor from string
  public Coordinate(String descr) {
    descr = descr.toUpperCase();
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Input length should be from 2\n");
    }
    char letter = descr.charAt(0);
    if (letter < 'A' || letter > 'Z') {
      throw new IllegalArgumentException("Column letter must be from a-z or A-Z");
    }
    // this(r,c);
    this.column = charToInt(descr.charAt(1));
    this.row = descr.charAt(0) - 'A';
  }
}
