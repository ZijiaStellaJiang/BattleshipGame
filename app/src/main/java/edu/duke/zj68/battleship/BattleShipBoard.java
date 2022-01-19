package edu.duke.zj68.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final ArrayList<Ship<T> > myShips;
  public int getWidth (){
    return this.width;
  }
  public int getHeight(){
    return this.height;
  }
  /**
   *Construct a BatttleShipBoard with the specified width and height
   *w and h is the width and height of newly constructed board
   *throws IllegalArgumentException if width or height are <=0
   */
  public BattleShipBoard (int w,int h){
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width=w;
    this.height=h;
    this.myShips = new ArrayList<Ship<T> >();
  }

  /**
   *add the ship to the list and return true
   */
  public boolean tryAddShip(Ship<T> toAdd) {
    this.myShips.add(toAdd);
    return true;
  }

  public T whatIsAt(Coordinate where) {
    for (Ship<T> s:myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where);
      }
    }
    return null;
  }
}
