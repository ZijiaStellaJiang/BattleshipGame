package edu.duke.zj68.battleship;

public interface Board<T> {
  public int getWidth();

  public int getHeight();

  public Ship<T> fireAt(Coordinate c);

  public String tryAddShip(Ship<T> toAdd);

  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);
}
