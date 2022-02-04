package edu.duke.zj68.battleship;

import java.util.HashMap;
import java.util.HashSet;

public class SonarScan<T> extends ExtraAction<T> {
  Coordinate center;
  int sonarRadius;
  public SonarScan(Board<T> theBoard, int allowedTimes, Coordinate center, int  radius) {
    super(theBoard, allowedTimes);
    this.center = center;
    this.sonarRadius = radius;
  }
  /**
   *@return the set of coordinates the sonar can scan
   */
  public HashSet<Coordinate> sonarCoords () {
    HashSet<Coordinate> sonarRange = new HashSet<>();
    int row = center.getRow();
    int col = center.getColumn();
    for( int r = row-sonarRadius; r< row+sonarRadius+1;r++) {
      int rowDif = row>r ? row-r : r-row ;
      for (int c = col-(3-rowDif); c<col+(3-rowDif)+1 ; c++) {
        sonarRange.add(new Coordinate(r,c));
      }
    }
    return sonarRange;
  }

  public int getShipSquareNumber( String shipName, HashSet<Coordinate> sonarRange) {
    int count = 0;
    for (Coordinate c : sonarRange) {
      Ship<T> s = theBoard.selectShip(c);
      if(s!=null && s.getName()==shipName) {
        count++;
      }
    }
    return count;
  }
  public HashMap<String , Integer> doSonarScan() {
    HashMap<String , Integer> shipSquareNum = new HashMap<>(); 
    HashSet<Coordinate> sonarRange = sonarCoords();
    shipSquareNum.put("Submarine", getShipSquareNumber("Submarine", sonarRange));
    shipSquareNum.put("Destroyer", getShipSquareNumber("Destroyer", sonarRange));
    shipSquareNum.put("Battleship", getShipSquareNumber("Battleship", sonarRange));
    shipSquareNum.put("Carrier", getShipSquareNumber("Carrier", sonarRange));
    return shipSquareNum;
  }
  
}
