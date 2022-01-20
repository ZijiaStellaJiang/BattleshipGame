/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.zj68.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {
  /*@Test void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
        }*/
  @Test
  void test_read_placement() throws IOException {
    StringReader sr = new StringReader("B2V\nC8H\na4v\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes,true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20);
    App app = new App(b, sr, ps);
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1,2),'V');
    expected[1] = new Placement(new Coordinate(2,8),'H');
    expected[2] = new Placement(new Coordinate(0,4),'V');
    for (int i=0 ; i<expected.length ; i++) {
      Placement p = app.readPlacement(prompt);
      assertEquals(p,expected[i]);
      assertEquals(prompt+"\n",bytes.toString());
      bytes.reset();
    }
  }

  @Test
  void test_do_one_placement() throws IOException {
    StringReader sr = new StringReader("A1H\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes,true);
    Board<Character> b = new BattleShipBoard<Character>(2, 3);
    App app = new App(b, sr, ps);
    app.doOnePlacement();
    String expected = "  0|1\n"+"A  |sA\n"+"B  |  B\n"+"C  |  C\n"+"  0|1\n";
    assertEquals("Where would you like to put your ship?\n"+expected+"\n",bytes.toString());
  }
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT,mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes,true);
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    try{
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected,actual);
  }
}
