package edu.duke.zj68.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  private final ArrayList<Ship<T> > myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  final T missInfo;
  @Override
  public int getWidth (){
    return this.width;
  }
  @Override
  public int getHeight(){
    return this.height;
  }
  /**
   *Construct a BatttleShipBoard with the specified width and height
   *w and h is the width and height of newly constructed board
   *throws IllegalArgumentException if width or height are <=0
   */
  public BattleShipBoard (int w,int h,PlacementRuleChecker<T> placementChecker, T missInfo){
    if (w <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
    }
    if (h <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
    }
    this.width=w;
    this.height=h;
    this.myShips = new ArrayList<Ship<T> >();
    this.placementChecker = placementChecker;
    this.enemyMisses = new HashSet<Coordinate>();
    this.missInfo = missInfo;
  }
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)),missInfo);
  }
  /**
   *shoot at a coordinate
   *@return the ship if hit, null if miss
   */
  @Override
  public Ship<T> fireAt(Coordinate c) {
    for(Ship<T> s : myShips) {
      if (s.occupiesCoordinates(c)) {
        s.recordHitAt(c);
        return s;
      }
    }
    enemyMisses.add(c);
    return null;
  }
  /**
   *try to add the ship to the list
   *@return null if placement valid, 
   *otherwise a string that describe what is wrong
   */
  @Override
  public String tryAddShip(Ship<T> toAdd) {
    //String placementProblem = placementChecker.checkPlacement(toAdd, this);
    if (placementChecker.checkPlacement(toAdd, this)==null) {
      this.myShips.add(toAdd);
      return null;
    }
    return placementChecker.checkPlacement(toAdd, this);
  }
  protected T whatIsAt(Coordinate where, boolean isSelf) {
    for (Ship<T> s:myShips) {
      if (s.occupiesCoordinates(where)) {
        return s.getDisplayInfoAt(where,isSelf);
      }
    }
    if (isSelf==false && enemyMisses.contains(where)) {
      return missInfo;
    }
    return null;
  }
  @Override
  public T whatIsAtForSelf(Coordinate where) {
    return whatIsAt(where, true);
  }
  @Override
  public T whatIsAtForEnemy(Coordinate where) {
    return whatIsAt(where, false);
  }
  @Override
  public boolean checkLose() {
    for (Ship<T> s : myShips) {
      if (s.isSunk() == false) {
        return false;
      }
    }
    return true;
  }
  
}
