package edu.duke.zj68.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  /**
   *check if the ship is in bound of the board
   *all coordinates in the ship should not extend the board's width and height
   *the coordinates also should not <0
   */
  @Override
  protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> shipCoords = theShip.getCoordinates();
    for (Coordinate c:shipCoords) {
      if(c.getRow()<0 || c.getRow()>=theBoard.getHeight() || c.getColumn()<0 || c.getColumn()>=theBoard.getWidth()) {
        return false;
      }
    }
    return true;
  }

}
