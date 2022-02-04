package edu.duke.zj68.battleship;

/**
 *This interface represents any type of ship in this game
 */
public interface Ship<T> {
  /**
   *check if this ship occupies the given coordinate
   *@param where is the Coordinate to check
   *@return true if where is inside this ship, false if not
   */
  public boolean occupiesCoordinates(Coordinate where);
  
  /**
   *check if the ship has been hit in all of its locations meaning it has been sunk
   *@return true if sunk,false if not
   */
  public boolean isSunk();
  
  /**
   *make this ship record that it has been hit at the given coordinate.
   *@param where is the coordinate that were hit(must be part of the ship)
   *@throws illegalArgumentException if where is not part of the ship
   */
  public void recordHitAt(Coordinate where);
  
  /**
   *check if this ship was hit at the specific coordinates(must be part of the ship)
   *@param where is the coordinate to check
   *@return true if is hit, false if not
   *@throws IllegaArgumentException if the coordinates are not part of this ship
   */
  public boolean wasHitAt(Coordinate where);
  
  /**
   *return the view-specific information at the given coordinate.(must be part of the ship)
   *@param where is the coordinate to return information for
   *@param myShip indicate whether is my own ship
   *@throws IllegalArgumentException if where is not part of the ship
   *@return the view-specific information at that coordinate
   */
  public T getDisplayInfoAt(Coordinate where, boolean myShip);
  
  /**
   *get the name of this ship, such as "submarine"
   *@return the name of this ship
   */
  public String getName();

  /**
   *get all of the coordinates that this ship occupies
   *@return An Iterable with the coordinates that this Ship occupies
   */
  public Iterable<Coordinate> getCoordinates();

  public Placement getPlacement();
}
