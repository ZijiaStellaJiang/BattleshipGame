package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    Coordinate upLeft = new Coordinate(0,1);
    RectangleShip<Character> s = new RectangleShip<Character>("h3ship",upLeft,1,3,'s','*');
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(0,1));
    expected.add(new Coordinate(1,1));
    expected.add(new Coordinate(2,1));
    assertEquals(expected,s.makeCoords(upLeft,1,3));
  }

  @Test
  public void test_constructor() {
    Coordinate c1 = new Coordinate(2,1);
    RectangleShip<Character> s = new RectangleShip<Character>("h2ship",c1,1,2,'s','*');
    Coordinate c2 = new Coordinate(3,1);
    assertEquals(true, s.occupiesCoordinates(c1));
    assertEquals(true, s.occupiesCoordinates(c2));
  }

  @Test
  public void test_occupy_and_hit() {
    Coordinate c1 = new Coordinate(1,0);
    Coordinate c2 = new Coordinate(0,1);
    RectangleShip<Character> s1 = new RectangleShip<Character>("h3ship",c1, 1, 3, 's', '*');
    RectangleShip<Character> s2 = new RectangleShip<Character>("w2ship",c2, 2, 1, 's', '*');
    assertEquals(false, s1.occupiesCoordinates(new Coordinate(1,1)));
    assertEquals(false, s2.occupiesCoordinates(c1));
    assertThrows(IllegalArgumentException.class, () -> s1.recordHitAt(c2));
    Coordinate hit = new Coordinate(0,2);
    s2.recordHitAt(hit);
    assertEquals(true,s2.wasHitAt(hit));
    assertEquals(false,s1.wasHitAt(new Coordinate(3,0)));
  }

  @Test
  public void test_is_sunk() {
    Coordinate c1 = new Coordinate(1,0);
    Coordinate c2 = new Coordinate(0,1);
    RectangleShip<Character> s1 = new RectangleShip<Character>("h3ship",c1, 1, 3, 's', '*');
    RectangleShip<Character> s2 = new RectangleShip<Character>("w2ship",c2, 2, 1, 's', '*');
    s1.recordHitAt(c1);
    s2.recordHitAt(c2);
    s2.recordHitAt(new Coordinate(0,2));
    assertEquals(true,s2.isSunk());
    assertEquals(false,s1.isSunk());
  }

  @Test
  public void test_getDisplayInfo_and_getName() {
     Coordinate c = new Coordinate(1,0);
     RectangleShip<Character> s = new RectangleShip<Character>("h3ship",c, 1, 3, 's', '*');
     Coordinate hit = new Coordinate(2,0);
     s.recordHitAt(hit);
     assertEquals('s',s.getDisplayInfoAt(c,true));
     assertEquals(null,s.getDisplayInfoAt(c, false));
     assertEquals('*',s.getDisplayInfoAt(hit,true));
     
     assertEquals("h3ship",s.getName());
  }
  @Test
  public void test_get_coordinates() {
    V1ShipFactory f1 = new V1ShipFactory();
    Placement v1_2 = new Placement(new Coordinate(1,2), 'V');
    Ship<Character> dst = f1.makeDestroyer(v1_2);
    Iterable<Coordinate> dstCoors = dst.getCoordinates();
    for(Coordinate c: dstCoors) {
      assertEquals(true,dst.occupiesCoordinates(c));
    }
    
  }
}
