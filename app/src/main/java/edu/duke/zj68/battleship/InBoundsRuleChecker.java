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
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> shipCoords = theShip.getCoordinates();
    for (Coordinate c:shipCoords) {
      if(c.getRow()<0) {return "That placement is invalid: the ship goes off the top of the board.";}
      if(c.getRow()>=theBoard.getHeight()) {
        return "That placement is invalid: the ship goes off the bottom of the board.";
      }
      if(c.getColumn()<0) {return "That placement is invalid: the ship goes off the left of the board.";}
      if(c.getColumn()>=theBoard.getWidth()) {
        return "That placement is invalid: the ship goes off the right of the board.";
      }
    }
    return null;
  }

}
