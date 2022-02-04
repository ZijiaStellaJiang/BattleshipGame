package edu.duke.zj68.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character> {
  /**
   *createship helper
   *if invalid orientation, throw an exception
   *if horizontal, reverse value of w and h
   */
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
    char orientation = where.getOrientation();
    if (orientation != 'V' && orientation != 'H') {
      throw new IllegalArgumentException("Orientaion should be 'V' or 'H', but is "+orientation);
    }
    if(orientation == 'H') {
      int temp = w;
      w = h;
      h = temp;
    }
    Ship<Character> created = new RectangleShip<Character>(name,where,w,h,letter,'*');
    return created;
  }

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where,1,2,'s',"Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where,1,4,'b',"Battleship");
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where,1,6,'c',"Carrier");
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where,1,3,'d',"Destroyer");
  }

}
