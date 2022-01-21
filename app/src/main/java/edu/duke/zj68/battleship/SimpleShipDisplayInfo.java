package edu.duke.zj68.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private final T myData;
  private final T onHit;
  public SimpleShipDisplayInfo (T myData, T onHit) {
    this.myData = myData;
    this.onHit = onHit;
  }
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    // TODO Auto-generated method stub
    if(hit == true) {
      return onHit;
    }
    return myData;
  }
}
