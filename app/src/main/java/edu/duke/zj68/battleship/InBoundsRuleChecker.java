package edu.duke.zj68.battleship;

public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
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
