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
    assertEquals(null,inBChecker1.checkPlacement(dst1, b1));
    assertEquals("That placement is invalid: the ship goes off the right of the board.",inBChecker1.checkPlacement(bats1, b1));
    PlacementRuleChecker<Character> inBChecker2 = new InBoundsRuleChecker<Character>(null);
    BattleShipBoard<Character> b2 = new BattleShipBoard<>(6, 10,inBChecker2);
    V1ShipFactory f3 = new V1ShipFactory();
    Ship<Character> car1 = f3.makeCarrier(new Placement(new Coordinate(8,5), 'V'));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.",inBChecker2.checkPlacement(car1, b2));
    V1ShipFactory f4 = new V1ShipFactory();
    Ship<Character> sub1 = f4.makeSubmarine(new Placement(new Coordinate(-1,2), 'v'));
    assertEquals("That placement is invalid: the ship goes off the top of the board.",inBChecker2.checkPlacement(sub1, b2));
  }

}
