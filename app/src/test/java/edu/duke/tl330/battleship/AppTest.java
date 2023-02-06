/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {

   private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
     BufferedReader input = new BufferedReader(new StringReader(inputData));
     PrintStream output = new PrintStream(bytes, true);
     Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
     V1ShipFactory shipFactory = new V1ShipFactory();
     return new TextPlayer("A", board, input, output, shipFactory);
   }
  
  @Test
  public void test_doAttackingPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer p1=createTextPlayer(3, 2, "A0h\na1\na1\na2\n", bytes);
    TextPlayer p2=createTextPlayer(3, 2, "A0h\nb1\na2\n", bytes);
    App a=new App(p1,p2);
    p1.doOnePlacement();
    p2.doOnePlacement();
    a.doAttackingPhase();
    String expected="Player A where do you want to place a Destroyer?\n"+
      "  0|1|2\n"+
      "A d|d|d A\n"+
      "B  | |  B\n"+
      "  0|1|2\n\n"+
      "Player A where do you want to place a Destroyer?\n"+
      "  0|1|2\n"+
      "A d|d|d A\n"+
      "B  | |  B\n"+
      "  0|1|2\n\n"+
      "Player A's turn:\n"+
      "     Your ocean                           Player A's ocean\n"+
      "  0|1|2                    0|1|2\n"+
      "A d|d|d A                A  | |  A\n"+
      "B  | |  B                B  | |  B\n"+
      "  0|1|2                    0|1|2\n"+
      "You hit a destroyer!\n"+
      "Player A win. Player A lose.\n"
      
      

      ;
    assertEquals(expected,bytes.toString());
  }

  @Disabled
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  public void test_main() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    } finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  }

  
}
