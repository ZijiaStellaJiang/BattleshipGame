package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class SonarScanTest {
  @Test
  public void test_get_remain() {
    SonarScan<Character> sonar = new SonarScan<>(new BattleShipBoard<Character>(2, 3, 'X'), 3, new Coordinate(1,2),0);
    assertEquals(3, sonar.getRemain());
  }
  @Test
  public void test_sonar_coords() {
    SonarScan<Character> sonar = new SonarScan<>(new BattleShipBoard<Character>(10, 10,'X'),3, new Coordinate(5,6) ,3);
    HashSet<Coordinate> ans = sonar.sonarCoords();
    Coordinate[] expected = {new Coordinate(2,6),new Coordinate(4,4), new Coordinate(6,7),new Coordinate(8,6),new Coordinate(5,8)};
    for (Coordinate c: expected) {
      assertEquals(true, ans.contains(c));
    }
    assertEquals(false, ans.contains(new Coordinate(4,9)));
  }
  @Test
  public void test_get_ship_square_number() {
    Board<Character> b = new BattleShipBoard<Character>(10, 10, 'X');
    V2ShipFactory f1 = new V2ShipFactory();
    Ship<Character> sub1=f1.makeSubmarine(new Placement(new Coordinate(3,5), 'v'));
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> sub2=f2.makeSubmarine(new Placement(new Coordinate(7,7), 'h'));
    V2ShipFactory f3 = new V2ShipFactory();
    Ship<Character> bat = f3.makeBattleship(new Placement(new Coordinate(3,7), 'd'));
    b.tryAddShip(sub1);
    b.tryAddShip(sub2);
    b.tryAddShip(bat);
    SonarScan<Character> sonar = new SonarScan<>(b ,3, new Coordinate(5,6),3);
    HashSet<Coordinate> sonarRange = sonar.sonarCoords();
    assertEquals(3,sonar.getShipSquareNumber("Submarine", sonarRange));
    assertEquals(2,sonar.getShipSquareNumber("Battleship", sonarRange));
  }
  @Test
  public void test_do_sonar() {
     Board<Character> b = new BattleShipBoard<Character>(10, 10, 'X');
    V2ShipFactory f1 = new V2ShipFactory();
    Ship<Character> sub1=f1.makeSubmarine(new Placement(new Coordinate(3,5), 'v'));
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> sub2=f2.makeSubmarine(new Placement(new Coordinate(7,7), 'h'));
    V2ShipFactory f3 = new V2ShipFactory();
    Ship<Character> bat = f3.makeBattleship(new Placement(new Coordinate(3,7), 'd'));
    V2ShipFactory f4 = new V2ShipFactory();
    Ship<Character> car = f4.makeCarrier(new Placement(new Coordinate(6,3), 'l'));
    b.tryAddShip(sub1);
    b.tryAddShip(sub2);
    b.tryAddShip(bat);
    b.tryAddShip(car);
    SonarScan<Character> sonar = new SonarScan<>(b ,3, new Coordinate(5,6),3);
    HashMap<String,Integer> map = sonar.doSonarScan();
    assertEquals(3,map.get("Submarine"));
    assertEquals(0,map.get("Destroyer"));
    assertEquals(2,map.get("Battleship"));
    assertEquals(5,map.get("Carrier"));
  }

}
