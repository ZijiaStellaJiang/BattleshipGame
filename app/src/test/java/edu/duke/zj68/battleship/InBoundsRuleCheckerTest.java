package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_inBounds_ruleChecker() {
    PlacementRuleChecker<Character> inBChecker1 = new InBoundsRuleChecker<Character>(null);
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(4, 3, inBChecker1);
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> dst1 = f1.makeDestroyer(new Placement(new Coordinate("A3"), 'V'));
    V1ShipFactory f2 = new V1ShipFactory();
    Ship<Character> bats1 = f2.makeBattleship(new Placement(new Coordinate(0,1), 'H'));
    assertEquals(true,inBChecker1.checkPlacement(dst1, b1));
    assertEquals(false,inBChecker1.checkPlacement(bats1, b1));
    PlacementRuleChecker<Character> inBChecker2 = new InBoundsRuleChecker<Character>(null);
    BattleShipBoard<Character> b2 = new BattleShipBoard<>(6, 10,inBChecker2);
    V1ShipFactory f3 = new V1ShipFactory();
    Ship<Character> car1 = f3.makeCarrier(new Placement(new Coordinate(8,5), 'V'));
    assertEquals(false,inBChecker2.checkPlacement(car1, b2));
  }

}