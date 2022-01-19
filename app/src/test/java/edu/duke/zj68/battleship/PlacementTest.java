package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_cor_and_ori() {
    Coordinate c1 = new Coordinate (2,5);
    Placement p1 = new Placement(c1,'B');
    Coordinate c2 = new Coordinate (2,5);
    Placement p2 = new Placement(c1,'c');
    assertEquals(c1,p1.getCoordinate());
    assertEquals('B',p1.getOrientation());
    assertEquals(c2,p2.getCoordinate());
    assertEquals('C',p2.getOrientation());
  }
  @Test
  public void test_toString() {
    Coordinate c1 = new Coordinate(3,4);
    Placement p1 = new Placement(c1,'D');
    assertEquals("(3, 4) , D",p1.toString());
  }
  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1,2);
    Coordinate c2 = new Coordinate(1,2);
    Coordinate c3 = new Coordinate(1,3);
    Coordinate c4 = new Coordinate(3,2);
    Placement p1 = new Placement(c1,'V');
    Placement p2 = new Placement(c2,'v');
    Placement p3 = new Placement(c3,'A');
    Placement p4 = new Placement(c3,'B');
    Placement p5 = new Placement(c4,'A');
    assertEquals(p1,p2);
    assertEquals(p1,p1);
    assertNotEquals(p1,p3);
    assertNotEquals(p3,p4);
    assertNotEquals(p3,p5);
    assertNotEquals(p4,p5);
    assertNotEquals(p1,"(1, 2) , V");
  }
  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1,2);
    Coordinate c2 = new Coordinate(1,2);
    Coordinate c3 = new Coordinate(1,3);
    Coordinate c4 = new Coordinate(3,2);
    Placement p1 = new Placement(c1,'V');
    Placement p2 = new Placement(c2,'v');
    Placement p3 = new Placement(c3,'A');
    Placement p4 = new Placement(c3,'B');
    Placement p5 = new Placement(c4,'A');
    assertEquals(p1.hashCode(),p2.hashCode());
    assertNotEquals(p1.hashCode(),p3.hashCode());
    assertNotEquals(p1.hashCode(),p4.hashCode());
    assertNotEquals(p4.hashCode(),p5.hashCode());
  }
  @Test
  public void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("A0V");
    Coordinate c1 = new Coordinate(0,0);
    assertEquals(c1,p1.getCoordinate());
    assertEquals('V',p1.getOrientation());
    Placement p2 = new Placement("B3H");
    Coordinate c2 = new Coordinate("B3");
    assertEquals(c2,p2.getCoordinate());
    assertEquals('H',p2.getOrientation());
    Placement p3 = new Placement("D5H");
    Coordinate c3 = new Coordinate(3,5);
    assertEquals(c3,p3.getCoordinate());
    assertEquals('H',p3.getOrientation());
  }
  @Test
  public void test_string_constructor_error_cases(){
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAH"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("00V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0:"));
  }
}

