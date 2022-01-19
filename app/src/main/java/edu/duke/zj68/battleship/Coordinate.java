package edu.duke.zj68.battleship;

public class Coordinate {
  private final int row;
  private final int column;
  public int getRow(){
    return this.row;
  }
  public int getColumn(){
    return this.column;
  }
  public Coordinate(int r, int c){
    this.row = r;
    this.column = c;
  }
  /**
   *This constructs coordinate from string
   *A2 to (0,2)
   *throw IllegalArgumentException if input format is invalid
   */
  public Coordinate (String descr) {
    String upper = descr.toUpperCase();
    char rowLetter = upper.charAt(0);  //get rowletter and transfer to int
    if (rowLetter<'A' || rowLetter >'Z') {
      throw new IllegalArgumentException("Coordinate row must be among A to Z, but is "+rowLetter);
    }
    this.row = rowLetter - 'A';
    String colStr = upper.substring(1);  //get column substring and transfer to int 
    int colNum = Integer.parseInt(colStr);
    if (colNum<0 || colNum > 9) {
      throw new IllegalArgumentException ("Coordinate column must be a number among 0-9, but is "+colStr);
    }
    this.column = colNum;
  }
  
  @Override
  public boolean equals(Object o){
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row==c.row && column ==c.column;
    }
    return false;
  }
  @Override
  public String toString() {
    return "("+row+", "+column+")";
  }
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
