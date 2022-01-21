package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  @Test
  public void test_width_and_height() {
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(10,20);
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }
  @Test
  public void test_invalid_dimensions(){
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10,0));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0,20));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10,-5));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8,20));
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
    BattleShipBoard<Character> b1 = new BattleShipBoard<Character>(2, 3);
    //Character[][] expected = {{null,'s'},{null,'s'},{null,null}};
    Coordinate c1 = new Coordinate(0,1);
    Coordinate c2 = new Coordinate(1,1);
    Ship<Character> toAdd1 = new RectangleShip<Character>(c1,'s','*');
     Ship<Character> toAdd2 = new RectangleShip<Character>(c2,'s','*');
     for (int i = 0; i<b1.getHeight(); i++) {
       for (int j = 0; j<b1.getWidth(); j++) {
         Coordinate c = new Coordinate(i, j);
         assertEquals(null,b1.whatIsAt(c));
       }
     }
    assertEquals(true,b1.tryAddShip(toAdd1));
    assertEquals(true,b1.tryAddShip(toAdd2));
    assertEquals('s',b1.whatIsAt(c1));
    assertEquals('s',b1.whatIsAt(c2));
  }
}
