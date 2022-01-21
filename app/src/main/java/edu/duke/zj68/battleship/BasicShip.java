package edu.duke.zj68.battleship;

import java.util.HashMap;

public abstract class  BasicShip<T> implements Ship<T> {
  /**
   *contains mapping from the ship's coordinates to whether this coordinate is hit
   *true if hit, false if not been hit, null if the ship does't contain the coordinate
   */
  protected HashMap<Coordinate,Boolean> myPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;
  public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo){
    this.myDisplayInfo = myDisplayInfo;
    myPieces = new HashMap<Coordinate,Boolean>();
    for (Coordinate c:where) {
      myPieces.put(c, false);
    }
  }

  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    // TODO this is not right
    //we need to look up the hit status of this coordinate
    return myDisplayInfo.getInfo(where, false);
  }

}
