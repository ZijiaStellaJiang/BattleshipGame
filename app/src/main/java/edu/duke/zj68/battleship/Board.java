package edu.duke.zj68.battleship;

import java.util.HashMap;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public Ship<T> selectShip (Coordinate choose);

  public Ship<T> fireAt(Coordinate c);

  public String tryAddShip(Ship<T> toAdd);

  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);

  public boolean checkLose() ;

  public void moveShipProcess(Ship<T> oldShip, Ship<T> afterMove);

  public void removeShip(Ship<T> toRemove) ;

  public HashMap<String , Integer> doSonarScan(Coordinate center) ;
}
