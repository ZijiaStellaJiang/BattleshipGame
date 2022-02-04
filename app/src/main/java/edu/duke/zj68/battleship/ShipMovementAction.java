package edu.duke.zj68.battleship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class ShipMovementAction<T> extends ExtraAction<T>  {
  private final Ship<T> toMove;
  private Placement newLocation;
  final AbstractShipFactory<Character> shipFactory;
  final HashMap<String, Function<Placement, Ship<Character> > > shipCreationFns;
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer",(p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }
  public ShipMovementAction(Board<T> theBoard, int allowedTimes, Ship<T> toMove, Placement newLocation,AbstractShipFactory<Character> f) {
    super(theBoard, allowedTimes);
    this.toMove = toMove;
    this.newLocation = newLocation;
    this.shipFactory = f;
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character> > >();
    setupShipCreationMap();
  }

  public void moveShip() {
    Placement oldPlacement = toMove.getPlacement();
    //HashSet<Coordinate> previousHit = theBoard.removeShip(toMove);
    Ship<Character> afterMove = shipCreationFns.get(toMove.getName()).apply(newLocation);
    
  }
}
