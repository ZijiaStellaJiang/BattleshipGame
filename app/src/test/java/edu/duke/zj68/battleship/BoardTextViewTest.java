package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader,String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w,h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader,view.makeHeader());
    assertEquals(expectedBody,view.makeBody());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected,view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2,2);
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    String expectedBody = "A  |  A\n"+"B  |  B\n";
    assertEquals(expectedHeader,view.makeHeader());
    assertEquals(expectedBody, view.makeBody());
    String expected = expectedHeader+expectedBody+expectedHeader;
    assertEquals(expected,view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_3by2(){
    String exHead = "  0|1|2\n";
    String exBody = "A  |  |  A\n"+"B  |  |  B\n";
    emptyBoardHelper(3,2,exHead,exBody);
  }
  @Test
  public void test_display_empty_3by5(){
    String exHead = "  0|1|2\n";
    String exBody = "A  |  |  A\n"+"B  |  |  B\n"+"C  |  |  C\n"+"D  |  |  D\n"+"E  |  |  E\n";
    emptyBoardHelper(3,5,exHead,exBody);
  }
  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20);
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
 }

  private void shipBoardHelper(int w, int h, ArrayList<Coordinate> s, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h);
    BoardTextView view = new BoardTextView(b1);
    for (Coordinate where : s) {
      Ship<Character> ship = new BasicShip(where);
      b1.tryAddShip(ship);
    }
   assertEquals(expectedHeader,view.makeHeader());
    assertEquals(expectedBody,view.makeBody());
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
    String exHead = "  0|1|2|3\n";
    String exBody = "A  |s|  |  A\n"+"B  |  |  |sB\n"+"Cs|  |  |  C\n";
    shipBoardHelper(4, 3, shipLocation, exHead, exBody);
  }

}
