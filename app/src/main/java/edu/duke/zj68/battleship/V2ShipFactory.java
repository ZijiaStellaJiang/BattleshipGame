package edu.duke.zj68.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character> {
  protected Ship<Character> createRectangleShip (Placement where, int w, int h, char letter, String name) {
    char orientation = where.getOrientation();
    if (orientation != 'V' && orientation != 'H') {
      throw new IllegalArgumentException("Orientaion should be 'V' or 'H', but is "+orientation);
    }
    if(orientation == 'H') {
      int temp = w;
      w = h;
      h = temp;
    }
    Ship<Character> created = new RectangleShip<Character>(name,where.getCoordinate(),w,h,letter,'*');
    return created;
  }
  protected void checkShapedShip(Placement where) {
    char orientation = where.getOrientation();
    if (orientation != 'U' && orientation != 'R' && orientation != 'D' && orientation != 'L') {
      throw new IllegalArgumentException("Orientaion should be 'U', 'D', 'R' or 'L', but is "+orientation);
    }
  }

  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createRectangleShip(where, 1, 2, 's', "Submarine");
  }

  @Override
  public Ship<Character> makeBattleship(Placement where) {
    checkShapedShip(where);
    return new TShapedShip<Character>("Battleship", where, 'b', '*');
  }

  @Override
  public Ship<Character> makeCarrier(Placement where) {
    checkShapedShip(where);
    return new ZShapedShip<Character>("Carrier", where, 'c', '*');
  }

  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createRectangleShip(where, 1, 3, 'd', "Destroyer");
  }
  

}
