package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    Coordinate upLeft = new Coordinate(0,1);
    RectangleShip<Character> s = new RectangleShip<Character>(upLeft,1,3,'s','*');
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(new Coordinate(0,1));
    expected.add(new Coordinate(1,1));
    expected.add(new Coordinate(2,1));
    assertEquals(expected,s.makeCoords(upLeft,1,3));
  }

  @Test
  public void test_constructor() {
    Coordinate c1 = new Coordinate(2,1);
    RectangleShip<Character> s = new RectangleShip<Character>(c1,1,2,'s','*');
    Coordinate c2 = new Coordinate(3,1);
    assertEquals(true, s.occupiesCoordinates(c1));
    assertEquals(true, s.occupiesCoordinates(c2));
  }
}
