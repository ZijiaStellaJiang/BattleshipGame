package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader,String expectedBody) {
    Board b1 = new BattleShipBoard(w,h);
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader,view.makeHeader());
    assertEquals(expectedBody,view.makeBody());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected,view.displayMyOwnBoard());
  }
  @Test
  public void test_display_empty_2by2() {
    Board b1 = new BattleShipBoard(2,2);
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
    Board wideBoard = new BattleShipBoard(11,20);
    Board tallBoard = new BattleShipBoard(10,27);
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
 } 

}
