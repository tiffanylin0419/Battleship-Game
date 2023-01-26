/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

class AppTest {
  @Test
  void test_read_placement() throws IOException {
    StringReader sr = new StringReader("B2V\nC8H\na4v\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20);
    App app = new App(b, sr, ps);

    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length; i++) {
      Placement p = app.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }

  }
  @Test
  void test_do_one_placement()throws IOException{
    StringReader sr = new StringReader("B2V\n");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 10);
    App app = new App(b, sr, ps);
    app.doOnePlacement();
    String expectedHeader= "  0|1|2|3|4|5|6|7|8|9\n";
    String expected=
      expectedHeader+
      "A  | | | | | | | | |  A\n"+
      "B  | |s| | | | | | |  B\n"+
      "C  | | | | | | | | |  C\n"+
      "D  | | | | | | | | |  D\n"+
      "E  | | | | | | | | |  E\n"+
      "F  | | | | | | | | |  F\n"+
      "G  | | | | | | | | |  G\n"+
      "H  | | | | | | | | |  H\n"+
      "I  | | | | | | | | |  I\n"+
      "J  | | | | | | | | |  J\n"+
      expectedHeader;
    
    
    assertEquals("Where would you like to put your ship?\n"+expected+"\n", bytes.toString());
    
    
    }
}
