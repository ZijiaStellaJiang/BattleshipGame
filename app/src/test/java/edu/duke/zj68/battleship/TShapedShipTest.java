package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class TShapedShipTest {
  @Test
  public void test_makeCoords() {
    Placement place1 = new Placement(new Coordinate(0,1),'u');
    TShapedShip<Character> up = new TShapedShip<Character>("ba", place1, 'b', '*');
    HashSet<Coordinate> upExpected = new HashSet<>();
    upExpected.add(new Coordinate("a2"));
    upExpected.add(new Coordinate("b1"));
    upExpected.add(new Coordinate("b2"));
    upExpected.add(new Coordinate("b3"));
    assertEquals(upExpected,up.makeCoords(place1));
    Placement place2 = new Placement(new Coordinate(2,1),'r');
    TShapedShip<Character> right = new TShapedShip<Character>("ba", place2, 'b', '*');
    HashSet<Coordinate> rightExpected = new HashSet<>();
    rightExpected.add(new Coordinate("c1"));
    rightExpected.add(new Coordinate("d1"));
    rightExpected.add(new Coordinate("e1"));
    rightExpected.add(new Coordinate("d2"));
    assertEquals(rightExpected,right.makeCoords(place2));
    Placement place3 = new Placement(new Coordinate(2,4),'d');
    TShapedShip<Character> down = new TShapedShip<Character>("ba", place3, 'b', '*');
    HashSet<Coordinate> downExpected = new HashSet<>();
    downExpected.add(new Coordinate("c4"));
    downExpected.add(new Coordinate("c5"));
    downExpected.add(new Coordinate("c6"));
    downExpected.add(new Coordinate("d5"));
    assertEquals(downExpected ,down.makeCoords(place3));
  }
  @Test
  public void test_constructor_and_get_name() {
    Placement place = new Placement(new Coordinate(0,1),'l');
    TShapedShip<Character> left = new TShapedShip<Character>("ba", place, 'b', '*');
    assertEquals(true, left.occupiesCoordinates(new Coordinate("b1")));
    assertEquals(false, left.occupiesCoordinates(new Coordinate("c1")));
    assertEquals("ba", left.getName());
  }

}
