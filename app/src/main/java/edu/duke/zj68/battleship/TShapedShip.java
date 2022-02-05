
package edu.duke.zj68.battleship;

import java.util.HashSet;

public class TShapedShip<T> extends BasicShip<T> {
  private final String name;
  public TShapedShip(String name, Placement placement, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(placement),myDisplayInfo,enemyDisplayInfo,placement);
    this.name = name;
  }
  public TShapedShip(String name, Placement placement, T data, T onHit) {
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
    int upLeftRow = upperLeft.getRow();
    int upLeftCol = upperLeft.getColumn();
    if (orientation=='U') {
      shipCoords.add(new Coordinate(upLeftRow,upLeftCol+1));
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol));
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol+1));
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol+2));
    }
    else if( orientation=='R') {
      shipCoords.add(new Coordinate(upLeftRow,upLeftCol));
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol));
      shipCoords.add(new Coordinate(upLeftRow+2,upLeftCol));
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol+1));
    }
    else if (orientation=='D') {
      shipCoords.add(new Coordinate(upLeftRow,upLeftCol));
      shipCoords.add(new Coordinate(upLeftRow,upLeftCol+1));
      shipCoords.add(new Coordinate(upLeftRow,upLeftCol+2));
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol+1));
    }
    else {
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol));
      shipCoords.add(new Coordinate(upLeftRow,upLeftCol+1));
      shipCoords.add(new Coordinate(upLeftRow+1,upLeftCol+1));
      shipCoords.add(new Coordinate(upLeftRow+2,upLeftCol+1));
      }
    return shipCoords;
  }

}
