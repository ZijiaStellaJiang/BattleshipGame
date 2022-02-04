package edu.duke.zj68.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{
  private final String name;
  /**
   *generate a set of Coordinates for a rectangel ship starting at  upperLeft
   *with width and height
   */
  static HashSet<Coordinate> makeCoords (Coordinate upperLeft, int width, int height) {
    HashSet<Coordinate> shipCoordinates = new HashSet<Coordinate>();
    int upLeftRow  = upperLeft.getRow();
    int upLeftCol = upperLeft.getColumn();
    for (int i = upLeftRow;i<(upLeftRow+height) ;i++) {
      for (int j = upLeftCol; j<(upLeftCol+width) ; j++) {
        Coordinate c = new Coordinate(i,j);
        shipCoordinates.add(c);
      }
    }
    return shipCoordinates;
  }
  
  public RectangleShip(String name, Placement where/*Coordinate upperLeft*/, int width, int height,ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(where.getCoordinate(),width,height),myDisplayInfo,enemyDisplayInfo,where);
    this.name = name;
  }
  public RectangleShip(String name, Placement where/*Coordinate upperLeft*/, int width, int height, T data, T onHit) {
    this(name, where,width,height,new SimpleShipDisplayInfo<T>(data,onHit),new SimpleShipDisplayInfo<T>(null, data));
  }
  public RectangleShip(Placement where/*Coordinate upperLeft*/, T data, T onHit) {
    this("testship",where,1,1,data,onHit);
  }
  @Override
  public String getName() {
    return this.name;
  }
}
