package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader,String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w,h,'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader,view.makeHeader());
    assertEquals(expectedBody,view.makeBody((c)-> b1.whatIsAtForSelf(c)));
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected,view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2,2,'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0| 1\n";
    String expectedBody = "A  |  A\n"+"B  |  B\n";
    assertEquals(expectedHeader,view.makeHeader());
    assertEquals(expectedBody, view.makeBody((c)-> b1.whatIsAtForSelf(c)));
    String expected = expectedHeader+expectedBody+expectedHeader;
    assertEquals(expected,view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_3by2(){
    String exHead = "  0| 1| 2\n";
    String exBody = "A  |  |  A\n"+"B  |  |  B\n";
    emptyBoardHelper(3,2,exHead,exBody);
  }
  @Test
  public void test_display_empty_3by5(){
    String exHead = "  0| 1| 2\n";
    String exBody = "A  |  |  A\n"+"B  |  |  B\n"+"C  |  |  C\n"+"D  |  |  D\n"+"E  |  |  E\n";
    emptyBoardHelper(3,5,exHead,exBody);
  }
  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20,'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27,'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
 }

  @Test
  public void test_display_enemy_board() {
    Board<Character> b = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view = new BoardTextView(b);
    V1ShipFactory f1= new V1ShipFactory();
    Ship<Character> des = f1.makeDestroyer(new Placement(new Coordinate("A3"), 'v'));
    b.tryAddShip(des);
    String myView = "  0| 1| 2| 3\n" +
                                   "A  |  |  |d A\n" +
                                   "B  |  |  |d B\n" +
                                   "C  |  |  |d C\n" +
                                    "  0| 1| 2| 3\n" ;
    assertEquals(myView,view.displayMyOwnBoard());
    b.fireAt(new Coordinate("A0"));
    b.fireAt(new Coordinate("B3"));
    String enemyView = "  0| 1| 2| 3\n" +
                                          "AX |  |  |  A\n" +
                                          "B  |  |  |d B\n" +
                                          "C  |  |  |  C\n" +
                                          "  0| 1| 2| 3\n" ;
   assertEquals(enemyView,view.displayEnemyBoard());
  }
  @Test
  public void test_display_own_with_enemy() {
    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView view_own = new BoardTextView(b1);
    BoardTextView view_enemy = new BoardTextView(b2);
    V1ShipFactory f1= new V1ShipFactory();
    Ship<Character> des = f1.makeDestroyer(new Placement(new Coordinate("A3"), 'v'));
    b1.tryAddShip(des);
    V1ShipFactory f2= new V1ShipFactory();
    Ship<Character> sub = f2.makeSubmarine(new Placement(new Coordinate("B0"), 'h'));
    b2.tryAddShip(sub);
    Coordinate fire_own = new Coordinate("B3");
    Coordinate fire_ene = new Coordinate(0,2);
    b1.fireAt(fire_own);
    b2.fireAt(fire_ene);
    String expected ="Your Ocean                     Player B's Ocean\n"+
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n" +
                                     "A  |  |  |d A                A  |  |X |  A\n" +
                                     "B  |  |  |* B                B  |  |  |  B\n" +
                                     "C  |  |  |d C                C  |  |  |  C\n" +
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n";
    assertEquals(expected,view_own.displayMyBoardWithEnemyNextToIt(view_enemy, "Your Ocean", "Player B's Ocean"));
  }
@Test
  public void test_after_move_display() {
    Board<Character> b = new BattleShipBoard<Character>(4, 4, 'X');
    BoardTextView view = new BoardTextView(b);
    V2ShipFactory f1= new V2ShipFactory();
    Ship<Character> old= f1.makeDestroyer(new Placement(new Coordinate("b0"), 'h'));
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> newS = f2.makeDestroyer(new Placement("c1h"));
    b.tryAddShip(old);
    b.fireAt(new Coordinate("b1"));
    b.fireAt(new Coordinate("a0"));
    b.fireAt(new Coordinate("c1"));
    b.removeShip(old);
    b.moveShipProcess(old, newS);
    String myView = "  0| 1| 2| 3\n" +
                                   "A  |  |  |  A\n" +
                                   "B  |  |  |  B\n" +
                                   "C  |d |* |d C\n" +
                                   "D  |  |  |  D\n"+
                                    "  0| 1| 2| 3\n" ;
    assertEquals(myView,view.displayMyOwnBoard());
    String enemyView = "  0| 1| 2| 3\n" +
                                   "AX |  |  |  A\n" +
                                   "B  |d |  |  B\n" +
                                   "C  |X |  |  C\n" +
                                   "D  |  |  |  D\n"+
                                    "  0| 1| 2| 3\n" ;
   assertEquals(enemyView,view.displayEnemyBoard());
    
}

}
