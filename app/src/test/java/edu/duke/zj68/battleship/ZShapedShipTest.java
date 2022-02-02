package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ZShapedShipTest {
  @Test
  public void test_makeCoords() {
    Placement place1 = new Placement(new Coordinate(0,1), 'u');
    ZShapedShip<Character> up = new ZShapedShip<Character>("carrier", place1, 'c', '*');
    HashSet<Coordinate> upExpected = new HashSet<>();
    upExpected.add(new Coordinate("a1"));
    upExpected.add(new Coordinate("b1"));
    upExpected.add(new Coordinate("c1"));
    upExpected.add(new Coordinate("d1"));
    upExpected.add(new Coordinate("c2"));
    upExpected.add(new Coordinate("d2"));
    upExpected.add(new Coordinate("e2"));
    assertEquals(upExpected,up.makeCoords(place1));
    Placement place2 = new Placement(new Coordinate(2,1), 'r');
    ZShapedShip<Character> right = new ZShapedShip<Character>("carrier", place2, 'c', '*');
    HashSet<Coordinate> rightExpected = new HashSet<>();
    rightExpected.add(new Coordinate("c2"));
    rightExpected.add(new Coordinate("c3"));
    rightExpected.add(new Coordinate("c4"));
    rightExpected.add(new Coordinate("c5"));
    rightExpected.add(new Coordinate("d1"));
    rightExpected.add(new Coordinate("d2"));
    rightExpected.add(new Coordinate("d3"));
    assertEquals(rightExpected,right.makeCoords(place2));
    Placement place3 = new Placement(new Coordinate(2,4),'d');
    ZShapedShip<Character> down = new ZShapedShip<Character>("carrier", place3, 'c', '*');
    HashSet<Coordinate> downExpected = new HashSet<>();
    downExpected.add(new Coordinate("c4"));
    downExpected.add(new Coordinate("d4"));
    downExpected.add(new Coordinate("e4"));
    downExpected.add(new Coordinate("d5"));
    downExpected.add(new Coordinate("e5"));
    downExpected.add(new Coordinate("f5"));
    downExpected.add(new Coordinate("g5"));
    assertEquals(downExpected ,down.makeCoords(place3));
  }
  @Test
  public void test_constructor_and_get_name() {
    Placement place = new Placement(new Coordinate(0,1),'l');
    ZShapedShip<Character> left = new ZShapedShip<Character>("carrier", place, 'c','*');
    assertEquals(true,left.occupiesCoordinates(new Coordinate("A3")));
    assertEquals(true,left.occupiesCoordinates(new Coordinate("a4")));
    assertEquals(true,left.occupiesCoordinates(new Coordinate("a5")));
    assertEquals(true,left.occupiesCoordinates(new Coordinate("b1")));
    assertEquals(true,left.occupiesCoordinates(new Coordinate("b2")));
    assertEquals(true,left.occupiesCoordinates(new Coordinate("b3")));
    assertEquals(true,left.occupiesCoordinates(new Coordinate("b4")));
    assertEquals("carrier",left.getName());
  }

}
