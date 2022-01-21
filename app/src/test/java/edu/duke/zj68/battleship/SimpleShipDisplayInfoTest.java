package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    SimpleShipDisplayInfo<Character> s = new SimpleShipDisplayInfo<Character>('s','x');
    Coordinate c1 = new Coordinate(0,1);
    Coordinate c2 = new Coordinate(1,1);
    assertEquals('x',s.getInfo(c1,true));
    assertEquals('s',s.getInfo(c2,false));
  }

}
