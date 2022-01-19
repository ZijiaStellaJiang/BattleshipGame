package edu.duke.zj68.battleship;

public class Placement {
  private final Coordinate where;
  private final char orientation;
  public Coordinate getCoordinate() {
    return this.where;
  }
  public char getOrientation() {
    return this.orientation;
  }
  public Placement(Coordinate where, char orientation) {
    /**
     *case insensitive: if lower case , transfer into upper case
     */
    if(orientation<'A' ||orientation > 'Z') {
      orientation -= 32;
    }
    this.where = where;
    this.orientation = orientation;
  }
  /**
   *This constructs placement from string
   *A2V to ((0,2),'V')
   *throw IllegalArgumentException if input format is invaldorientation
   */
  public Placement(String descr) {
    String upper = descr.toUpperCase();
    String coorStr = upper.substring(0,2);
    this.where = new Coordinate(coorStr);
    char oriLetter = upper.charAt(2);
    if(oriLetter != 'V' && oriLetter != 'H') {
      throw new IllegalArgumentException ("Placement orientation shoulb be Vertical(V) of Horizontal(H), but it "+oriLetter);
    }
    this.orientation = oriLetter;
  }

  @Override
  public String toString() {
    return "(" + where.getRow() + ", " + where.getColumn() + ") , " + orientation ;
  }
  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Placement p = (Placement) o;
      return where.equals(p.where)  && orientation==p.orientation;
    }
    return false;
  }
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
