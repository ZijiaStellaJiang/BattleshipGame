package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

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
    Ship<Character> toAdd1 = new RectangleShip<Character>(c1,'s','*');
     Ship<Character> toAdd2 = new RectangleShip<Character>(c2,'s','*');
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
}
