package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  private TextPlayer createTextPlayer(int w,int h, String inputData, ByteArrayOutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes,true);
    Board<Character> board = new BattleShipBoard<Character>(w,h,'X');
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
  @Test
  public void test_doOnePlacement_tryAdd_error() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 3, "A1H\nA1V\n", bytes);
    player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
    String prompt = "Player A where do you want to place a Destroyer?\n";
    String errMessage = "That placement is invalid: the ship goes off the right of the board.";
    String expected = "  0| 1\n"+"A  |d A\n"+"B  |d B\n"+"C  |d C\n"+"  0| 1\n";
    assertEquals(prompt+errMessage+"\n"+prompt+expected+"\n",bytes.toString());
  }
  @Test
  public void test_doOnePlacement_placeConstruct_error() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(2, 3, "89V\nA1V\n", bytes);
    player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
    String prompt = "Player A where do you want to place a Destroyer?\n";
    String errMessage1 = "Coordinate row must be among A to Z, but is 8\n";
    String expected = "  0| 1\n"+"A  |d A\n"+"B  |d B\n"+"C  |d C\n"+"  0| 1\n";
    assertEquals(prompt+errMessage1+prompt+expected+"\n",bytes.toString());
  }
  @Test
  public void test_read_coordinate() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10,20,"B2\nC8\na4\n",bytes);
    String prompt = "Please enter a coordinate to fire at:";
    Coordinate[] expected = new Coordinate[3];
    expected[0] = new Coordinate(1,2);
    expected[1] = new Coordinate(2,8);
    expected[2] = new Coordinate(0,4);
    for (int i=0 ; i<expected.length ; i++) {
      Coordinate p = player.readCoordinate(prompt);
      assertEquals(p,expected[i]);
      assertEquals(prompt+"\n",bytes.toString());
      bytes.reset();
    } 
  }
  @Test
  public void test_input_coordinate_null() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10,20, "", bytes);
    String prompt = "Please enter a coordinate to fire at:";
    assertThrows(EOFException.class, () -> player.readCoordinate(prompt));
  }
  @Test
  public void test_play_one_turn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    Board<Character> boardOwn = new BattleShipBoard<Character>(4, 3, 'X');
    V1ShipFactory f1 = new V1ShipFactory();
    Ship<Character> sub = f1.makeSubmarine(new Placement("B1v"));
    boardOwn.tryAddShip(sub);
    TextPlayer player = new TextPlayer("A", boardOwn, new BufferedReader(new StringReader("b3\na0\n")), new PrintStream(bytes,true), new V1ShipFactory());
    Board<Character> ene = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView eneView = new BoardTextView(ene);
    V1ShipFactory f2 = new V1ShipFactory();
    Ship<Character> des=f2.makeDestroyer(new Placement("a3v"));
    ene.tryAddShip(des);
    player.playOneTurn(ene, eneView, "B");
    String expected1 = "Your Ocean                     Player B's Ocean\n"+
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n" +
                                     "A  |  |  |  A                A  |  |  |  A\n" +
                                     "B  |s |  |  B                B  |  |  |  B\n" +
                                     "C  |s |  |  C                C  |  |  |  C\n" +
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n";
    //assertEquals(expected1+"\n"+"Player A which coordinate do you wan to fire at?\n"+"You hit a Destroyer!\n",bytes.toString());
    player.playOneTurn(ene, eneView, "B");
    String expected2 = "Your Ocean                     Player B's Ocean\n"+
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n" +
                                     "A  |  |  |  A                A  |  |  |  A\n" +
                                     "B  |s |  |  B                B  |  |  |d B\n" +
                                     "C  |s |  |  C                C  |  |  |  C\n" +
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n";
    assertEquals(expected1+"\n"+"Player A which coordinate do you want to fire at?\n"+"You hit a Destroyer!\n"+expected2+"\n"+"Player A which coordinate do you want to fire at?\n"+"You missed!\n",bytes.toString());
  }
  @Test
  public void test_playOneTrun_errorInput() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    Board<Character> boardOwn = new BattleShipBoard<Character>(4, 3, 'X');
    TextPlayer player = new TextPlayer("A", boardOwn, new BufferedReader(new StringReader("A:\na0\n")), new PrintStream(bytes,true), new V1ShipFactory());
    Board<Character> ene = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView eneView = new BoardTextView(ene);
    player.playOneTurn(ene, eneView, "B");
    String prompt = "Player A which coordinate do you want to fire at?\n";
    String expected1 = "Your Ocean                     Player B's Ocean\n"+
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n" +
                                     "A  |  |  |  A                A  |  |  |  A\n" +
                                     "B  |  |  |  B                B  |  |  |  B\n" +
                                     "C  |  |  |  C                C  |  |  |  C\n" +
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n";
    assertEquals(expected1+"\n"+prompt+"For input string: \":\""+"\n"+expected1+"\n"+prompt+"You missed!\n",bytes.toString());
  }
  @Test
  public void test_do_move_ship() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    Board<Character> boardOwn = new BattleShipBoard<Character>(4, 3, 'X');
    V2ShipFactory f1 = new V2ShipFactory();
    Ship<Character> bat1 = f1.makeBattleship(new Placement("a0d"));
    boardOwn.tryAddShip(bat1);
    TextPlayer player = new TextPlayer("A", boardOwn, new BufferedReader(new StringReader("a1\nb1d\n")), new PrintStream(bytes,true), new V2ShipFactory());
    Board<Character> ene = new BattleShipBoard<Character>(4, 3, 'X');
    BoardTextView eneView = new BoardTextView(ene);
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> des = f2.makeDestroyer(new Placement("a3v"));
    ene.tryAddShip(des);
    player.doMoveShip(ene, eneView, "B");
    String expected1 = "Your Ocean                     Player B's Ocean\n"+
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n" +
                                     "Ab |b |b |  A                A  |  |  |  A\n" +
                                     "B  |b |  |  B                B  |  |  |  B\n" +
                                     "C  |  |  |  C                C  |  |  |  C\n" +
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n";
    String expected2 = "Your Ocean                     Player B's Ocean\n"+
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n" +
                                     "A  |  |  |  A                A  |  |  |  A\n" +
                                     "B  |b |b |b B                B  |  |  |  B\n" +
                                     "C  |  |b |  C                C  |  |  |  C\n" +
                                     "  0| 1| 2| 3                  0| 1| 2| 3\n";
    assertEquals(expected1+"\n"+"Player A which ship do you want to move? You can enter any coordinate in the ship\n\n"+"Please enter the new placement\n\n"+expected2+"\n",bytes.toString());
  }
  @Test
  public void test_do_sonar_scan() throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    Board<Character> boardOwn = new BattleShipBoard<Character>(6, 6, 'X');
    V2ShipFactory f1 = new V2ShipFactory();
    Ship<Character> bat1 = f1.makeBattleship(new Placement("a0d"));
    boardOwn.tryAddShip(bat1);
    TextPlayer player = new TextPlayer("A", boardOwn, new BufferedReader(new StringReader("e5\n")), new PrintStream(bytes,true), new V2ShipFactory());
    Board<Character> ene = new BattleShipBoard<Character>(6, 6, 'X');
    BoardTextView eneView = new BoardTextView(ene);
    V2ShipFactory f2 = new V2ShipFactory();
    Ship<Character> des = f2.makeDestroyer(new Placement("a3v"));
    ene.tryAddShip(des);
    V2ShipFactory f3 = new V2ShipFactory();
    Ship<Character> car = f3.makeCarrier(new Placement("d0r"));
    ene.tryAddShip(car);
    player.doSonarScan(ene, eneView, "B");
    String expected1 = "Your Ocean                           Player B's Ocean\n"+
                                     "  0| 1| 2| 3| 4| 5                  0| 1| 2| 3| 4| 5\n" +
                                     "Ab |b |b |  |  |  A                A  |  |  |  |  |  A\n" +
                                     "B  |b |  |  |  |  B                B  |  |  |  |  |  B\n"+
                                     "C  |  |  |  |  |  C                C  |  |  |  |  |  C\n"+
                                     "D  |  |  |  |  |  D                D  |  |  |  |  |  D\n"+
                                     "E  |  |  |  |  |  E                E  |  |  |  |  |  E\n"+
                                     "F  |  |  |  |  |  F                F  |  |  |  |  |  F\n"+
                                     "  0| 1| 2| 3| 4| 5                  0| 1| 2| 3| 4| 5\n";
    String expeced2 = "Submarines occupy 0 squares\nDestroyers occupy 0 squares\nBattleships occupy 0 squares\nCarriers occupy 3 squares\n";
    assertEquals(expected1+"\n"+"Player A please enter the center coordinate for sonar scan\n\n"+expeced2,bytes.toString());
  }
}
