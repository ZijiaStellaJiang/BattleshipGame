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
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> shipCoords = theShip.getCoordinates();
    for (Coordinate c: shipCoords) {
      if(theBoard.whatIsAtForSelf(c)!=null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }
}
