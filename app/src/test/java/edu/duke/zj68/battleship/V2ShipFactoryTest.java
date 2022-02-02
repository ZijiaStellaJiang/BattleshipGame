package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(expectedName, testShip.getName());
    for (Coordinate c: expectedLocs) {
      assertEquals(true, testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(c,true));
    }
  }
  @Test
  public void test_make_ship() {
    V2ShipFactory f1 = new V2ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'V');
    Ship<Character> dst = f1.makeDestroyer(v1_2);
    checkShip(dst,"Destroyer",'d',new Coordinate(1,2),new Coordinate(2,2), new Coordinate(3,2));
    V2ShipFactory f = new V2ShipFactory();
    Placement h0_2 = new Placement(new Coordinate(0,2), 'h');
    Ship<Character> sub2 = f.makeSubmarine(h0_2);
    checkShip(sub2, "Submarine", 's', new Coordinate(0,2),new Coordinate(0,3));
    V2ShipFactory f2 = new V2ShipFactory();
    Placement h2_2 = new Placement(new Coordinate(2,2), 'l');
    assertThrows(IllegalArgumentException.class, () -> f2.makeSubmarine(h2_2));
    V2ShipFactory f3 = new V2ShipFactory();
    Placement r2_2 = new Placement(new Coordinate(2,2), 'R');
    Ship<Character> bat= f3.makeBattleship(r2_2);
    checkShip(bat, "Battleship", 'b', new Coordinate(2,2), new Coordinate(3,2), new Coordinate(4,2), new Coordinate(3,3));
    V2ShipFactory f4 = new V2ShipFactory();
    Placement u0_1 = new Placement(new Coordinate(0,1), 'u');
    Ship<Character> car = f4.makeCarrier(u0_1);
    checkShip(car, "Carrier", 'c', new Coordinate(0,1), new Coordinate(1,1),new Coordinate(2,1), new Coordinate(3,1), new Coordinate(2,2), new Coordinate(3,2), new Coordinate(4,2));
    V2ShipFactory f5 = new V2ShipFactory();
    Placement error = new Placement(new Coordinate(3,2), 'g');
    assertThrows(IllegalArgumentException.class, () -> f5.makeBattleship(error));
  }

}
