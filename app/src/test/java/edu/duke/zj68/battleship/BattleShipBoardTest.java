package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10,20,'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }
  @Test
  public void test_invalid_dimensions(){
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10,0,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0,20,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10,-5,'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8,20,'X'));
  }

  /**private<T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    /**
     *this check for empty board
     
    for (int i = 0; i<b.getHeight(); i++) {
       for (int j = 0; j<b.getWidth(); j++) {
         Coordinate c = new Coordinate(i, j);
         assertEquals(null,b.whatIsAt(c));
         if(expected[i][j]!=null) {
           Ship<Character> toAdd = new BasicShip(c);
           assertEquals(true,b.tryAddShip(toAdd));
           assertEquals('s',b.whatIsAt(c));
         }
       }
       }
    
       }*/
  @Test
  public void test_ship_in_board_2by3() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(2, 3,'X');
    //Character[][] expected = {{null,'s'},{null,'s'},{null,null}};
    Coordinate c1 = new Coordinate(0,1);
    Coordinate c2 = new Coordinate(1,1);
    Ship<Character> toAdd1 = new RectangleShip<Character>(new Placement(c1, 'h'),'s','*');
     Ship<Character> toAdd2 = new RectangleShip<Character>(new Placement(c2, 'h'),'s','*');
     for (int i = 0; i<b1.getHeight(); i++) {
       for (int j = 0; j<b1.getWidth(); j++) {  
       Coordinate c = new Coordinate(i, j);
         assertEquals(null,b1.whatIsAtForSelf(c));
       }
     }
    assertEquals(null,b1.tryAddShip(toAdd1));
    assertEquals(null,b1.tryAddShip(toAdd2));
    assertEquals('s',b1.whatIsAtForSelf(c1));
    assertEquals('s',b1.whatIsAtForSelf(c2));
  }
  @Test
  public void  test_tryAddShip_with_checker() {
    BattleShipBoard b1 = new BattleShipBoard<>(4, 4,'X');
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> sub1 = f1.makeSubmarine(new Placement(new Coordinate("C1"), 'v'));
    assertEquals(null,b1.tryAddShip(sub1));
    V1ShipFactory f2 = new V1ShipFactory();
    Ship<Character> dst1 = f2.makeDestroyer(new Placement(new Coordinate("A0"), 'H'));
    assertEquals(null, b1.tryAddShip(dst1));
    V1ShipFactory f3 = new V1ShipFactory();
    Ship<Character> Bats1_collide = f3.makeBattleship(new Placement(new Coordinate("A2"), 'v'));
    assertEquals("That placement is invalid: the ship overlaps another ship.", b1.tryAddShip(Bats1_collide));
    V1ShipFactory f4 = new V1ShipFactory();
    Ship<Character> Car1_outBound = f4.makeCarrier(new Placement(new Coordinate("C3"), 'v'));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", b1.tryAddShip(Car1_outBound));
    V1ShipFactory f5 = new V1ShipFactory();
    Ship<Character> sub2 = f1.makeSubmarine(new Placement(new Coordinate("C2"), 'h'));
    assertEquals(null ,b1.tryAddShip(sub2));
  }
  @Test
  public void test_fire_at() {
    BattleShipBoard b1 = new BattleShipBoard<>(4, 4,'X');
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> sub1 = f1.makeSubmarine(new Placement(new Coordinate("C1"), 'v'));
    b1.tryAddShip(sub1);
    assertSame(null,b1.fireAt(new Coordinate("D2")));
    assertSame(sub1,b1.fireAt(new Coordinate("D1")));
    assertFalse(sub1.isSunk());
    b1.fireAt(new Coordinate(2,1));
    assertTrue(sub1.isSunk());
  }
  @Test
  public void test_whatIsAt_forEnemy() {
    BattleShipBoard b1 = new BattleShipBoard<>(4, 4,'X');
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> sub1 = f1.makeSubmarine(new Placement(new Coordinate("C1"), 'v'));
    b1.tryAddShip(sub1);
    V1ShipFactory f2 = new V1ShipFactory();
    Ship<Character> dst1 = f2.makeDestroyer(new Placement(new Coordinate("A0"), 'H'));
    b1.tryAddShip(dst1);
    Coordinate fire1 = new Coordinate("B1");
    b1.fireAt(fire1);
    assertEquals('X',b1.whatIsAtForEnemy(fire1));
    assertEquals(null ,b1.whatIsAtForEnemy( new Coordinate(0,0)));
    Coordinate fire3 = new Coordinate("A2");
    b1.fireAt(fire3);
    assertEquals('d',b1.whatIsAtForEnemy(fire3));
    assertEquals(null,b1.whatIsAtForEnemy(new Coordinate("B2")));
  }
  @Test
  public void test_check_lose() {
    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    V1ShipFactory f1= new V1ShipFactory();
    Ship<Character> des = f1.makeDestroyer(new Placement(new Coordinate("A3"), 'v'));
    b1.tryAddShip(des);
    V1ShipFactory f2= new V1ShipFactory();
    Ship<Character> sub = f2.makeSubmarine(new Placement(new Coordinate("B0"), 'h'));
    b1.tryAddShip(sub);
    b1.fireAt(new Coordinate("A3"));
    b1.fireAt(new Coordinate("b3"));
    b1.fireAt(new Coordinate("c3"));
    assertEquals(false, b1.checkLose());
    b1.fireAt(new Coordinate("A0"));
    b1.fireAt(new Coordinate("b0"));
    assertEquals(false, b1.checkLose());
    b1.fireAt(new Coordinate("b1"));
    assertEquals(true,b1.checkLose());
  }
  /*
  @Test
  public void test_remove_ship() {
    Board<Character> b = new BattleShipBoard<Character>(4, 3,'X');
    V2ShipFactory f1 = new V2ShipFactory();
    Ship<Character> dst = f1.makeDestroyer(new Placement(new Coordinate("b0"), 'h'));
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> sub = f2.makeSubmarine(new Placement(new Coordinate("c0"),'v'));
    b.tryAddShip(dst);
    b.tryAddShip(sub);
    b.fireAt(new Coordinate("b1"));
    Coordinate expected = new Coordinate(1,1);
    assertEquals(true, b.removeShip(dst).contains(expected));
    assertEquals(true,b.removeShip(sub).isEmpty());
    assertEquals(null,b.selectShip(expected));
  }
  
  @Test
  public void test_find_new_coordinate() {
    Board<Character> b = new BattleShipBoard<>(4, 4,'X');
    V2ShipFactory f1 = new V2ShipFactory();
    Ship<Character> old = f1.makeDestroyer(new Placement("b0h"));
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> newShip = f2.makeDestroyer(new Placement("c1h"));
    Coordinate hit = new  Coordinate("b1");
    assertEquals(new Coordinate("c2"),b.findNewCoordinate(old, hit, newShip));
  }
  */
}
