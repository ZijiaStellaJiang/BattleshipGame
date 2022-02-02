package edu.duke.zj68.battleship;

import java.util.HashSet;

public class ZShapedShip<T> extends BasicShip<T> {
  private final String name;
  public ZShapedShip(String name, Placement placement, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(placement),myDisplayInfo,enemyDisplayInfo);
    this.name = name;
  }
  public ZShapedShip(String name,Placement placement, T data, T onHit) {
    this(name, placement, new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<>(null, data));
  }

  @Override
  public String getName() {
    return this.name;
  }

  static HashSet<Coordinate> makeCoords(Placement place) {
    HashSet<Coordinate> shipCoords = new HashSet<>();
    Coordinate upperLeft = place.getCoordinate();
    Character orientation = place.getOrientation();
    int Row = upperLeft.getRow();
    int Col = upperLeft.getColumn();
    if ( orientation == 'U') {
      for (int i = Row;i<Row+4;i++) {
        shipCoords.add(new Coordinate(i,Col));
      }
      for (int i = Row+2;i<Row+5;i++) {
        shipCoords.add(new Coordinate(i,Col+1));
      }
    }
    else if ( orientation == 'R') {
      for (int i = Col+1;i<Col+5;i++) {
        shipCoords.add(new Coordinate(Row,i));
      }
      for (int i = Col;i<Col+3;i++) {
        shipCoords.add(new Coordinate(Row+1,i));
      }
    }
    else if ( orientation == 'D') {
      for (int i = Row;i<Row+3;i++) {
        shipCoords.add(new Coordinate(i,Col));
      }
      for (int i = Row+1;i<Row+5;i++) {
        shipCoords.add(new Coordinate(i,Col+1));
      }
    }
    else {
      for (int i = Col+2;i<Col+5;i++) {
        shipCoords.add(new Coordinate(Row,i));
      }
      for (int i = Col;i<Col+4;i++) {
        shipCoords.add(new Coordinate(Row+1,i));
      }
    }
    return shipCoords;
  }

}
