package edu.duke.zj68.battleship;

public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }

  /**
   *check if the ship is not collide with anything else
   *all the squares ths ship needs should be empty
   */
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> shipCoords = theShip.getCoordinates();
    for (Coordinate c: shipCoords) {
      if(theBoard.whatIsAt(c)!=null) {
        return false;
      }
    }
    return true;
  }
}
