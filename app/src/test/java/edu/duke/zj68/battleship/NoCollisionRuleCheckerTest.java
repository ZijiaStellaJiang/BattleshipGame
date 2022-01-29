package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

//import sun.jvm.hotspot.memory.VirtualSpace;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_noCollision_ruleChecker() {
    PlacementRuleChecker<Character> noCoChecker1 = new NoCollisionRuleChecker<Character>(null);
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(3, 5, noCoChecker1,'X');
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> sub1 = f1.makeSubmarine(new Placement(new Coordinate(2,1), 'v'));
    b1.tryAddShip(sub1);
    V1ShipFactory f2 = new V1ShipFactory();
    Ship<Character> dst_collide = f2.makeDestroyer(new Placement(new Coordinate("a1"), 'V'));
    assertEquals("That placement is invalid: the ship overlaps another ship.", noCoChecker1.checkPlacement(dst_collide, b1));
    V1ShipFactory f3 = new V1ShipFactory();
    Ship<Character> dst = f3.makeDestroyer(new Placement(new Coordinate(1,0), 'H'));
    assertEquals(null, noCoChecker1.checkPlacement(dst, b1));
  }
  @Test
  public void test_inBound_noCollide_combineChecker() {
    PlacementRuleChecker<Character> noCoChecker1 = new NoCollisionRuleChecker<Character>(null);
    PlacementRuleChecker<Character> inBChecker1 = new InBoundsRuleChecker<Character>(noCoChecker1);
    BattleShipBoard<Character> b1 = new BattleShipBoard<>(3, 5, inBChecker1,'X');
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> sub1 = f1.makeSubmarine(new Placement(new Coordinate(2,1), 'v'));
    b1.tryAddShip(sub1);
    V1ShipFactory f2 = new V1ShipFactory();
    Ship<Character> dst = f2.makeDestroyer(new Placement(new Coordinate("a0"), 'h'));
    assertEquals(null, inBChecker1.checkPlacement(dst, b1));
    V1ShipFactory f3 = new V1ShipFactory();
    Ship<Character> sub2 = f3.makeSubmarine(new Placement(new Coordinate(0,1), 'v'));
    assertEquals(null, inBChecker1.checkPlacement(sub2,b1));
    b1.tryAddShip(dst);
    assertEquals("That placement is invalid: the ship overlaps another ship.", inBChecker1.checkPlacement(sub2,b1));
    V1ShipFactory f4 = new V1ShipFactory();
    Ship<Character> car = f4.makeCarrier(new Placement(new Coordinate(2,-1),'h'));
    assertEquals("That placement is invalid: the ship goes off the left of the board.",inBChecker1.checkPlacement(car, b1)); 
  }

}
