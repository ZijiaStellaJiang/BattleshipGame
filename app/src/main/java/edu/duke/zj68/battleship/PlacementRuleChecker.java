package edu.duke.zj68.battleship;

public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }
  /**
   *@return null if placement valid for certain rule
   *@return the string that indicate the invalid message otherwise
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);
  public String checkPlacement(Ship<T> theShip, Board<T>  theBoard) {
    //if we fail out own rule, stop the placement is not legal,return the description message
    if(checkMyRule(theShip,theBoard)!=null) {
      return checkMyRule(theShip, theBoard);
    }
    //otherwise, ask the rest of the chain 
    if(next != null) {
      return next.checkPlacement(theShip,theBoard);
    }
    //if there are no more rules, then the placement is legal
    return null;
  }
  
}
