package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  private TextPlayer createTextPlayer(int w,int h, String inputData, ByteArrayOutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes,true);
    Board<Character> board = new BattleShipBoard<Character>(w,h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A",board,input,output,shipFactory);
  }
  @Test
  public void test_read_placement() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10,20,"B2V\nC8H\na4v\n",bytes);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1,2),'V');
    expected[1] = new Placement(new Coordinate(2,8),'H');
    expected[2] = new Placement(new Coordinate(0,4),'V');
    for (int i=0 ; i<expected.length ; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p,expected[i]);
      assertEquals(prompt+"\n",bytes.toString());
      bytes.reset();
    }
  }
  @Test
  public void test_input_null() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10,20, "", bytes);
    String prompt = "Please enter a location for a ship:";
    assertThrows(EOFException.class, () -> player.readPlacement(prompt));
  }
  @Test
  public void test_do_one_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2,3,"A1V\n",bytes);
    player.doOnePlacement("Destroyer",player.shipCreationFns.get("Destroyer"));
    String expected = "  0| 1\n"+"A  |d A\n"+"B  |d B\n"+"C  |d C\n"+"  0| 1\n";
    assertEquals("Player A where do you want to place a Destroyer?\n"+expected+"\n",bytes.toString());
  }
  /*
  @Disabled
  @Test
  public void test_do_one_placemene_phase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer playerA = createTextPlayer(2,3,"A1V\n", bytes);
    playerA.doPlacementPhase();
    String empty = "  0| 1\n"+"A  |  A\n"+"B  |  B\n"+"C  |  C\n"+"  0| 1\n";
    String expected = "  0| 1\n"+"A  |d A\n"+"B  |d B\n"+"C  |d C\n"+"  0| 1\n";
    assertEquals(empty+"\n"+"Player A: you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical). For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n\n"+"2 'Submarine' ships that are 1x2\n3 'Destroyers' that are 1x3\n3 'Battleships' that are 1x4\n2 'Carriers' that are 1x6\n"+"\n"+"Player A where do you want to place a Destroyer?\n"+expected+"\n",bytes.toString());
  }
  */

}
