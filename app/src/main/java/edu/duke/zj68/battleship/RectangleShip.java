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
  public RectangleShip(Coordinate upperLeft, int width, int height,ShipDisplayInfo<T> displayInfo) {
    super(makeCoords(upperLeft,width,height),displayInfo);
  }
  public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(upperLeft,width,height,new SimpleShipDisplayInfo<T>(data,onHit));
  }
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this(upperLeft,1,1,data,onHit);
  }
}
