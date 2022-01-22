package edu.duke.zj68.battleship;

/**
 *This interface represents an Abstract Factory pattern for ship creation
 */
public interface AbstractShipFactory<T> {
  /**
   *Make a submarine
   *@param where specified the location and orientation of the ship to make
   *@return the ship created for the submarine
   */
  public Ship<T> makeSubmarine(Placement where);

  /**
   *Make a battleship
   *@param where specified the location and orientation of the ship to make
   *@return the ship created for the battlehsip
   */
  public Ship<T> makeBattleship(Placement where);

  /**
   *Make a carrier
   *@param where specified the location and orientation of the ship to make
   *@return the ship created for the carrier
   */
  public Ship<T> makeCarrier(Placement where);

  /**
   *Make a destroyer
   *@param where specified the location and orientation of the ship to make
   *@return the ship created for the destroyer
   */
  public Ship<T> makeDestroyer(Placement where);
}
