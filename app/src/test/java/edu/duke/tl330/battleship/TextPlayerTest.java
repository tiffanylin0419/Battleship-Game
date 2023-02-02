package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h);
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); // did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
      bytes.reset(); // clear out bytes for next time around
    }

  }

  @Test
  void test_do_one_placement() throws IOException {

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 10, "B2V\n", bytes);
    player.doOnePlacement();
    String expectedHeader = "  0|1|2|3|4|5|6|7|8|9\n";
    String expected = expectedHeader +
        "A  | | | | | | | | |  A\n" +
        "B  | |d| | | | | | |  B\n" +
        "C  | |d| | | | | | |  C\n" +
        "D  | |d| | | | | | |  D\n" +
        "E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +

        expectedHeader;

    assertEquals("Player A where do you want to place a Destroyer?\n" + expected + "\n", bytes.toString());

  }

  
  @Test
  public void test_SmallPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 10, "B2V\nC5H\nd7v\nd1h\nA4h\nA9H\nB8H\nH3H\nE0V\ni2h\n", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Submarine", player.shipCreationFns.get("Submarine"));
    String expectedHeader = "  0|1|2|3|4|5|6|7|8|9\n";
    String expected = "Player A where do you want to place a Submarine?\n"+
      expectedHeader +
        "A  | | | | | | | | |  A\n" +
        "B  | |s| | | | | | |  B\n" +
        "C  | |s| | | | | | |  C\n" +
        "D  | | | | | | | | |  D\n" +
        "E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader ;

    assertEquals(expected, bytes.toString());

  }

  @Test
  public void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 10,
        "B2V\nC5H\nd7v\nd1h\nA4h\nA9v\nB8v\nH3H\nE0V\ni2h\n" ,
        bytes);
    player.doPlacementPhase();

    String expectedHeader = "  0|1|2|3|4|5|6|7|8|9\n";
    String expected = expectedHeader +
        "A  | | | | | | | | |  A\n" +
        "B  | | | | | | | | |  B\n" +
        "C  | | | | | | | | |  C\n" +
        "D  | | | | | | | | |  D\n" +
        "E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
        "\nPlayer A: you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n" +
      "Player A where do you want to place a Submarine?\n" +
        expectedHeader +
        "A  | | | | | | | | |  A\n" +
        "B  | |s| | | | | | |  B\n" +
        "C  | |s| | | | | | |  C\n" +
        "D  | | | | | | | | |  D\n" +
        "E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
      "Player A where do you want to place a Submarine?\n" +
        expectedHeader +
        "A  | | | | | | | | |  A\n" +
        "B  | |s| | | | | | |  B\n" +
        "C  | |s| | |s|s| | |  C\n" +
        "D  | | | | | | | | |  D\n" +
        "E  | | | | | | | | |  E\n" +
        "F  | | | | | | | | |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
      "Player A where do you want to place a Destroyer?\n" +
        expectedHeader +
        "A  | | | | | | | | |  A\n" +
        "B  | |s| | | | | | |  B\n" +
        "C  | |s| | |s|s| | |  C\n" +
        "D  | | | | | | |d| |  D\n" +
        "E  | | | | | | |d| |  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
      "Player A where do you want to place a Destroyer?\n" +
        expectedHeader +
        "A  | | | | | | | | |  A\n" +
        "B  | |s| | | | | | |  B\n" +
        "C  | |s| | |s|s| | |  C\n" +
        "D  |d|d|d| | | |d| |  D\n" +
        "E  | | | | | | |d| |  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
       "Player A where do you want to place a Destroyer?\n" +
        expectedHeader +
        "A  | | | |d|d|d| | |  A\n" +
        "B  | |s| | | | | | |  B\n" +
        "C  | |s| | |s|s| | |  C\n" +
        "D  |d|d|d| | | |d| |  D\n" +
        "E  | | | | | | |d| |  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
       "Player A where do you want to place a Battleship?\n" +
        expectedHeader +
        "A  | | | |d|d|d| | |b A\n" +
        "B  | |s| | | | | | |b B\n" +
        "C  | |s| | |s|s| | |b C\n" +
        "D  |d|d|d| | | |d| |b D\n" +
        "E  | | | | | | |d| |  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
       "Player A where do you want to place a Battleship?\n" +
        expectedHeader +
        "A  | | | |d|d|d| | |b A\n" +
        "B  | |s| | | | | |b|b B\n" +
        "C  | |s| | |s|s| |b|b C\n" +
        "D  |d|d|d| | | |d|b|b D\n" +
        "E  | | | | | | |d|b|  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
       "Player A where do you want to place a Battleship?\n" +
        expectedHeader +
        "A  | | | |d|d|d| | |b A\n" +
        "B  | |s| | | | | |b|b B\n" +
        "C  | |s| | |s|s| |b|b C\n" +
        "D  |d|d|d| | | |d|b|b D\n" +
        "E  | | | | | | |d|b|  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | |b|b|b|b| | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
       "Player A where do you want to place a Carrier?\n" +
        expectedHeader +
        "A  | | | |d|d|d| | |b A\n" +
        "B  | |s| | | | | |b|b B\n" +
        "C  | |s| | |s|s| |b|b C\n" +
        "D  |d|d|d| | | |d|b|b D\n" +
        "E c| | | | | | |d|b|  E\n" +
        "F c| | | | | | |d| |  F\n" +
        "G c| | | | | | | | |  G\n" +
        "H c| | |b|b|b|b| | |  H\n" +
        "I c| | | | | | | | |  I\n" +
        "J c| | | | | | | | |  J\n" +
        expectedHeader +
        "Player A where do you want to place a Carrier?\n" +
        expectedHeader +
      
        "A  | | | |d|d|d| | |b A\n" +
        "B  | |s| | | | | |b|b B\n" +
        "C  | |s| | |s|s| |b|b C\n" +
        "D  |d|d|d| | | |d|b|b D\n" +
        "E c| | | | | | |d|b|  E\n" +
        "F c| | | | | | |d| |  F\n" +
        "G c| | | | | | | | |  G\n" +
        "H c| | |b|b|b|b| | |  H\n" +
        "I c| |c|c|c|c|c|c| |  I\n" +
        "J c| | | | | | | | |  J\n" +
        expectedHeader;
    
    assertEquals(expected, bytes.toString());
    //String a1=expected.substring(3900,4000);//0~1;
    //String a2= bytes.toString().substring(3900,4000);
    //assertEquals(a1,a2);
  }


}
