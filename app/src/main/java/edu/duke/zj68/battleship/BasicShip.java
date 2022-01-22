package edu.duke.zj68.battleship;

import java.util.HashMap;
import java.util.Iterator;

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

  protected void checkCoordinateInThisShip(Coordinate c) {
    if(occupiesCoordinates(c)==false) {
      throw new IllegalArgumentException("This coordinate is not part of the ship");
    }
  }
  
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    Iterator<Coordinate> it = myPieces.keySet().iterator();
    while(it.hasNext()) {
      if (myPieces.get(it.next())==false) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.replace(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }

  @Override
  public T getDisplayInfoAt(Coordinate where) {
    return myDisplayInfo.getInfo(where, wasHitAt(where));
  }

}
