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

  private void shipBoardHelper(int w, int h, ArrayList<Coordinate> s, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h,'X');
    BoardTextView view = new BoardTextView(b1);
    for (Coordinate where : s) {
      Ship<Character> ship = new RectangleShip<Character>(where,'s','*');
      b1.tryAddShip(ship);
    }
   assertEquals(expectedHeader,view.makeHeader());
    assertEquals(expectedBody,view.makeBody((c)-> b1.whatIsAtForSelf(c)));
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected,view.displayMyOwnBoard());
  }
  @Test
  public void test_display_ship_board_4by3() {
    Coordinate c1 = new Coordinate(0,1);
    Coordinate c2 = new Coordinate(1,3);
    Coordinate c3 = new Coordinate("C0");
    ArrayList<Coordinate> shipLocation = new ArrayList<Coordinate>();
    shipLocation.add(c1);
    shipLocation.add(c2);
    shipLocation.add(c3);
    String exHead = "  0| 1| 2| 3\n";
    String exBody = "A  |s |  |  A\n"+"B  |  |  |s B\n"+"Cs |  |  |  C\n";
    shipBoardHelper(4, 3, shipLocation, exHead, exBody);
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

}
