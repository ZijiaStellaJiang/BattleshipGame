package edu.duke.zj68.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T>{
  private final int width;
  private final int height;
  final ArrayList<Ship<T> > myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses;
  final T missInfo;
  HashSet<Coordinate> hitInNewShip;
  HashMap<Coordinate, T> hitInOldShip;
  HashSet<Coordinate> enemyMissedWithNewShip;
  
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
    this.hitInNewShip = new HashSet<>();
    this.hitInOldShip = new HashMap<>();
    this.enemyMissedWithNewShip = new HashSet<>();
  }
  public BattleShipBoard(int w, int h, T missInfo) {
    this(w, h, new InBoundsRuleChecker<T>(new NoCollisionRuleChecker<T>(null)),missInfo);
  }
  
  /**
   *@param choose is the coordinate player choose to do action on
   *@return the ship that occupies choose
   *@return null is no ship found
   */
  @Override
  public Ship<T> selectShip(Coordinate choose) {
    for(Ship<T> s : myShips) {
      if (s.occupiesCoordinates(choose)) {
        return s;
      }
    }
    return null;
  }
  
  /**
   *shoot at a coordinate
   *@return the ship if hit, null if miss
   */
  @Override
  public Ship<T> fireAt(Coordinate c) {
    Ship<T> fire = selectShip(c);
    if(fire!=null) {
      if(hitInNewShip.contains(c)) {
        hitInNewShip.remove(c);
      }
      fire.recordHitAt(c);
      return fire;
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
        if(!isSelf && enemyMissedWithNewShip.contains(where) && s.wasHitAt(where)==false) {
          return missInfo;
        }
        if(isSelf || !hitInNewShip.contains(where)) {
          return s.getDisplayInfoAt(where,isSelf);
        }
        return null;
      }
    }
    if (!isSelf && hitInOldShip.containsKey(where)) {
      return hitInOldShip.get(where);
    }
    if (!isSelf && enemyMisses.contains(where)) {
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
  
  @Override
  public void moveShipProcess(Ship<T> oldShip, Ship<T> afterMove) {
    if(tryAddShip(afterMove)!=null) {
      tryAddShip(oldShip);
      for (Coordinate c: oldShip.getCoordinates()) {
        if (oldShip.wasHitAt(c)) {
          hitInOldShip.remove(c);
        }
      }
      throw new IllegalArgumentException("This is an invalid placement\n");
    }
    HashSet<Coordinate> hitInNewInThisShip = new HashSet<>();
    for(Coordinate c: oldShip.getCoordinates()) {
      if(oldShip.wasHitAt(c)) {
        hitInNewInThisShip.add(findNewCoordinate(oldShip, c, afterMove));     
      }
    }
    for(Coordinate c : hitInNewInThisShip) {
      afterMove.recordHitAt(c);
      //fireAt(c);
    }
    checkEnemyMissed(afterMove);
  }
  protected void checkEnemyMissed(Ship<T> newShip) {
    for(Coordinate c: newShip.getCoordinates()) {
      if(enemyMisses.contains(c)) {
        enemyMissedWithNewShip.add(c);
        enemyMisses.remove(c);
      }
    }
  }
  
  /**
   *remove a ship from the board
   *with needed display info change
   *record the coordinate that has been hit in the ship toRemove
   */
  @Override
  public void removeShip(Ship<T> toRemove) {
    //HashSet<Coordinate> hitInShipToRemove = new HashSet<>();
    for (Coordinate c: toRemove.getCoordinates()) {
      if (toRemove.wasHitAt(c)) {
        hitInOldShip.put(c, whatIsAt(c, false));
      }
    }
    myShips.remove(toRemove);
  }
  /**
   *find the old ship's hit's relative cooridinate in the new location ship
   *if special ship, rotate
   *if rectangel ship, stick with upper left coordinate
   */
  protected Coordinate findNewCoordinate(Ship<T> oldShip, Coordinate oldHit ,Ship<T> newShip) {
    Coordinate newCoordinate = new Coordinate(0,0);
    Coordinate oldUpper = oldShip.getPlacement().getCoordinate();
    Coordinate newUpper = newShip.getPlacement().getCoordinate();
    Character oldOri = oldShip.getPlacement().getOrientation();
    Character newOri = newShip.getPlacement().getOrientation();
    if (oldOri==newOri){
      newCoordinate= new Coordinate(newUpper.getRow()+oldHit.getRow()-oldUpper.getRow(),newUpper.getColumn()+oldHit.getColumn()-oldUpper.getColumn());
    }
    else if(oldOri=='H') {
      newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-oldUpper.getColumn(),newUpper.getColumn());
    }
    else if(oldOri=='V') {
      newCoordinate = new Coordinate(newUpper.getRow(),newUpper.getColumn()+oldHit.getRow()-oldUpper.getRow());
    }
    else if (oldShip.getName()=="Battleship") {
      if(oldOri=='U') {
        if(newOri=='R') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if(newOri=='D') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn()+2);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else{
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+2);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
      else if (oldOri=='R') {
        if(newOri=='D') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+2,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if(newOri=='L') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+2,oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else{
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
      else if(oldOri=='D') {
        if(newOri=='L') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if( newOri=='U') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn()+2);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else {
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+2);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
      else {
        if(newOri=='U') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+2,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if( newOri=='R') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+2,oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else /*(oldOri=='L' && newOri=='D') */{
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
    }
    else /*oldShip.name=="Carrier"*/{
      if(oldOri=='U') {
        if( newOri=='R') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+4,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if(newOri=='D') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+4,oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else  {
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
      else if(oldOri=='R'){
        if(newOri=='D') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if( newOri=='L') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn()+4);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else{
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+4);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
      else if(oldOri=='D') {
        if(newOri=='L') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+4,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if(newOri=='U') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+4,oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else {
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+1);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
      else {
        if(newOri=='U') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn());
          newCoordinate = new Coordinate(newUpper.getRow()+oldHit.getColumn()-refer.getColumn(),newUpper.getColumn()+refer.getRow()-oldHit.getRow());
        }
        else if(newOri=='R') {
          Coordinate refer = new Coordinate(oldUpper.getRow()+1,oldUpper.getColumn()+4);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getRow()-oldHit.getRow(),newUpper.getColumn()+refer.getColumn()-oldHit.getColumn());
        }
        else /*(oldOri=='L' && newOri=='D') */{
          Coordinate refer = new Coordinate(oldUpper.getRow(),oldUpper.getColumn()+4);
          newCoordinate = new Coordinate(newUpper.getRow()+refer.getColumn()-oldHit.getColumn(),newUpper.getColumn()+oldHit.getRow()-refer.getRow());
        }
      }
    }
    hitInNewShip.add(newCoordinate);
    return newCoordinate;
  }
  
  /**
   *@return the set of coordinates the sonar can scan
   */  
  protected HashSet<Coordinate> sonarCoords (Coordinate center) {
    HashSet<Coordinate> sonarRange = new HashSet<>();
    int row = center.getRow();
    int col = center.getColumn();
    for( int r = row-3; r< row+4;r++) {
      int rowDif = row>r ? row-r : r-row ;
      for (int c = col-(3-rowDif); c<col+(3-rowDif)+1 ; c++) {
        sonarRange.add(new Coordinate(r,c));
      }
    }
    return sonarRange;
  }
  protected int getShipSquareNumber( String shipName, HashSet<Coordinate> sonarRange) {
    int count = 0;
    for (Coordinate c : sonarRange) {
      Ship<T> s = selectShip(c);
      if(s!=null && s.getName()==shipName) {
        count++;
      }
    }
    return count;
  }
  @Override
  public HashMap<String , Integer> doSonarScan(Coordinate center) {
    HashMap<String , Integer> shipSquareNum = new HashMap<>(); 
    HashSet<Coordinate> sonarRange = sonarCoords(center);
    shipSquareNum.put("Submarine", getShipSquareNumber("Submarine", sonarRange));
    shipSquareNum.put("Destroyer", getShipSquareNumber("Destroyer", sonarRange));
    shipSquareNum.put("Battleship", getShipSquareNumber("Battleship", sonarRange));
    shipSquareNum.put("Carrier", getShipSquareNumber("Carrier", sonarRange));
    return shipSquareNum;
  }
}
