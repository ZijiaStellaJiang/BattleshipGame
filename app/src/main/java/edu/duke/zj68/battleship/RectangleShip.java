package edu.duke.zj68.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T>{
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
  private final String name;
  public RectangleShip(String name, Coordinate upperLeft, int width, int height,ShipDisplayInfo<T> myDisplayInfo,ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft,width,height),myDisplayInfo,enemyDisplayInfo);
    this.name = name;
  }
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft,width,height,new SimpleShipDisplayInfo<T>(data,onHit),new SimpleShipDisplayInfo<T>(null, data));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship",upperLeft,1,1,data,onHit);
  }
  @Override
  public String getName() {
    return this.name;
  }
}
