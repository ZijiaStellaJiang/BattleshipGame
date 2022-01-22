package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs){
    assertEquals(expectedName, testShip.getName());
    for(Coordinate c:expectedLocs) {
      assertEquals(true,testShip.occupiesCoordinates(c));
      assertEquals(expectedLetter,testShip.getDisplayInfoAt(c));
    }
  }
  @Test
  public void test_make_ship() {
    V1ShipFactory f1 = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'V');
    Ship<Character> dst = f1.makeDestroyer(v1_2);
    checkShip(dst,"Destroyer",'d',new Coordinate(1,2),new Coordinate(2,2), new Coordinate(3,2));
    V1ShipFactory f2 = new V1ShipFactory();
    Placement h0_2 = new Placement(new Coordinate(0,2), 'h');
    Ship<Character> bats = f2.makeBattleship(h0_2);
    checkShip(bats, "Battleship", 'b', new Coordinate(0,2),new Coordinate(0,3),new Coordinate(0,4),new Coordinate(0,5));
    V1ShipFactory f3 = new V1ShipFactory();
    Placement error = new Placement(new Coordinate(1,2), 'B');
    assertThrows(IllegalArgumentException.class, () -> f3.makeCarrier(error));
    V1ShipFactory f4 = new V1ShipFactory();
    Placement h2_2 = new Placement(new Coordinate(2,2), 'v');
    Ship<Character> sub = f4.makeSubmarine(h2_2);
    checkShip(sub, "Submarine", 's', new Coordinate(2,2),new Coordinate(3,2));
  }

}
