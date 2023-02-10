package edu.duke.tl330.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TextPlayerTest {

  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }

  private TextPlayer createComputerPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("B", board, input, output, shipFactory, false);
  }

  @Test
  public void test_readCoordinate_error()throws IOException{
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "z2\nC8\na4v\n", bytes);
    player.readCoordinate();
    assertEquals("Coordinate not in board, enter again.\n",bytes.toString());
    TextPlayer computer = createComputerPlayer(10, 20, "z2\n", bytes);
 
     assertThrows(EOFException.class,()-> computer.readCoordinate());
  }
  
  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

    TextPlayer computer = createComputerPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
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

      p = computer.readPlacement(prompt);
      assertEquals(p, expected[i]);
      assertEquals("", bytes.toString());
      bytes.reset();
    }
  }

  @Test
  void test_read_placement_error() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(10, 20, "", bytes);
    assertThrows(EOFException.class, () -> player1.doOnePlacement());

    TextPlayer player2 = createTextPlayer(10, 20, "A0Q", bytes);
    assertThrows(IllegalArgumentException.class, () -> player2.doOnePlacement());

    TextPlayer player3 = createTextPlayer(10, 20, "AAV", bytes);
    assertThrows(IllegalArgumentException.class, () -> player3.doOnePlacement());
  }

  @Test
  void test_readCoordinate() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 20, "B2\nC8\na4\n", bytes);
    Coordinate c = player.readCoordinate();
    assertEquals(new Coordinate(1, 2), c);

    TextPlayer player1 = createTextPlayer(10, 20, "", bytes);
    assertThrows(IOException.class, () -> player1.readCoordinate());
  }

  @Test
  void test_do_one_placement_error() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 10, "B9h\nd9h\n", bytes);
    // player.doOnePlacement();
    // String expected = "Player A where do you want to place a Destroyer?\nThat
    // placement is invalid: the ship goes off the right of the board.\n";
    assertThrows(EOFException.class, () -> player.doOnePlacement());
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
  public void test_readAction_noInput() {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(5, 5, "", bytes);
    assertThrows(EOFException.class, () -> player1.readAction(""));

  }

  @Test
  void test_doOneAction_computer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer computer1 = createComputerPlayer(5, 5, "a0u\nC5\n", bytes);
    TextPlayer computer2 = createComputerPlayer(5, 5, "F\nC3\nf\nb0\n", bytes);
    computer1.doOnePlacement("Carrier", computer1.shipCreationFns.get("Carrier"));
    computer2.doOneAction(computer1);
    computer2.doOneAction(computer1);
    assertEquals("Player B missed!\nPlayer B hit your Carrier at B0!\n", bytes.toString());
  }

  @Test
  public void test_doOneAction() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(5, 5, "F\nC3\nC5\n", bytes);
    TextPlayer player2 = createTextPlayer(5, 5, "A3U\nm\na0\nm\nd4\nd1r\nm\nd4\nd0R\ns\nE4\ns\nm\nd4\na2u\nm\nf\na0\n",
        bytes);

    player2.doOnePlacement("Carrier", player2.shipCreationFns.get("Carrier"));
    player2.doOneAction(player1);
    player2.doOneAction(player1);
    player2.doOneAction(player1);
    player2.doOneAction(player1);
    String expectedHeader = "  0|1|2|3|4";
    String expected = "Player A where do you want to place a Carrier?\n" +
        expectedHeader + "\n" +
        "A  | | |c|  A\n" +
        "B  | | |c|  B\n" +
        "C  | | |c|c C\n" +
        "D  | | |c|c D\n" +
        "E  | | | |c E\n" +
        expectedHeader + "\n" +
        "Possible actions for Player A:\n\n F Fire at a square\n M Move a ship to another square (2 remaining)\n S Sonar scan (1 remaining)\n\nPlayer A, what would you like to do?\n"
        +
        "\n\n" +
        "Possible actions for Player A:\n\n F Fire at a square\n M Move a ship to another square (1 remaining)\n S Sonar scan (1 remaining)\n\nPlayer A, what would you like to do?\n"
        +
        "Submarines occupy 0 squares\nDestroyers occupy 0 squares\nBattleships occupy 0 squares\nCarriers occupy 0 squares\n"
        +
        "Possible actions for Player A:\n\n F Fire at a square\n M Move a ship to another square (1 remaining)\n S Sonar scan (0 remaining)\n\nPlayer A, what would you like to do?\n"
        +
        "No more scans.\n" +
        "\n" +
        "Possible actions for Player A:\n\n F Fire at a square\n M Move a ship to another square (0 remaining)\n S Sonar scan (0 remaining)\n\nPlayer A, what would you like to do?\n"
        +
        "No more moves.\n" +
        "Player A's turn:\n" +
        "     Your ocean                           Player A's ocean\n" +
        expectedHeader + "                  " + expectedHeader + "\n" +
        "A  | |c| |  A                A  | | | |  A\n" +
        "B  | |c| |  B                B  | | | |  B\n" +
        "C  | |c|c|  C                C  | | | |  C\n" +
        "D  | |c|c|  D                D  | | | |  D\n" +
        "E  | | |c|  E                E  | | | |  E\n" +
        expectedHeader + "                  " + expectedHeader + "\n" +
        "You missed!\n";

    assertEquals(expected, bytes.toString());

  }

  @Test
  public void test_doScan_computer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer computer = createComputerPlayer(10, 10,
        "B2\nB2V\nC9h\nC5H\nd7v\nd1h\nA4h\nd3u\na3r\ne8l\nf0u\nf3l\nh5R\n", bytes);
    TextPlayer computer1 = createComputerPlayer(10, 10,
        "B2V\nB2V\nC9h\nC5H\nd7v\nd1h\nA4h\nd3u\na3rne8l\nf0u\nf3l\nh5R\n", bytes);
    computer.doScan(computer1);
    String expected = "Player B used a special action\n";
    assertEquals(expected, bytes.toString());
  }
  @Test
  public void test_doMove_computer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer computer = createComputerPlayer(10, 10,
                                               "a2u\nc2\nd2d\nC5H\nd7v\nd1h\nA4h\nd3u\na3r\ne8l\nf0u\nf3l\nh5R\n", bytes);
    TextPlayer computer1 = createComputerPlayer(10, 10,
                                                "B2V\nB2V\nC9h\nC5H\nd7v\nd1h\nA4h\nd3u\na3rne8l\nf0u\nf3l\nh5R\n", bytes);
    computer.doOnePlacement("Carrier", computer.shipCreationFns.get("Carrier"));
    computer.doMove(computer1);
    String expected = "Player B used a special action\n";
    assertEquals(expected, bytes.toString());
  }

  
  @Test
  public void test_playOneTurn() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player1 = createTextPlayer(5, 5, "C2\nC4\n", bytes);
    TextPlayer player2 = createTextPlayer(5, 5, "B2V\n", bytes);
    player2.doOnePlacement("Submarine", player2.shipCreationFns.get("Submarine"));
    player1.playOneTurn(player2);

    String expectedHeader = "  0|1|2|3|4";
    String expected = "Player A where do you want to place a Submarine?\n" +
        expectedHeader + "\n" +
        "A  | | | |  A\n" +
        "B  | |s| |  B\n" +
        "C  | |s| |  C\n" +
        "D  | | | |  D\n" +
        "E  | | | |  E\n" +
        expectedHeader + "\n" +
        "Player A's turn:\n" +
        "     Your ocean                           Player A's ocean\n" +
        expectedHeader + "                  " + expectedHeader + "\n" +
        "A  | | | |  A                A  | | | |  A\n" +
        "B  | | | |  B                B  | | | |  B\n" +
        "C  | | | |  C                C  | | | |  C\n" +
        "D  | | | |  D                D  | | | |  D\n" +
        "E  | | | |  E                E  | | | |  E\n" +
        expectedHeader + "                  " + expectedHeader + "\n" +
        "You hit a submarine!\n";

    assertEquals(expected, bytes.toString());

    player1.playOneTurn(player2);
    expected += "Player A's turn:\n" +
        "     Your ocean                           Player A's ocean\n" +
        expectedHeader + "                  " + expectedHeader + "\n" +
        "A  | | | |  A                A  | | | |  A\n" +
        "B  | | | |  B                B  | | | |  B\n" +
        "C  | | | |  C                C  | |s| |  C\n" +
        "D  | | | |  D                D  | | | |  D\n" +
        "E  | | | |  E                E  | | | |  E\n" +
        expectedHeader + "                  " + expectedHeader + "\n" +
        "You missed!\n";
    assertEquals(expected, bytes.toString());
  }

  @Test
  public void test_SmallPlacementPhase_small() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(5, 5, "B2V\nC5H\nd7v\nd1h\nA4h\nA9H\nB8H\nH3H\nE0V\ni2h\n", bytes);
    player.doOnePlacement("Submarine", player.shipCreationFns.get("Submarine"));
    String expectedHeader = "  0|1|2|3|4\n";
    String expected = "Player A where do you want to place a Submarine?\n" +
        expectedHeader +
        "A  | | | |  A\n" +
        "B  | |s| |  B\n" +
        "C  | |s| |  C\n" +
        "D  | | | |  D\n" +
        "E  | | | |  E\n" +
        expectedHeader;

    assertEquals(expected, bytes.toString());

  }

  @Test
  public void test_SmallPlacementPhase_small_computer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer computer = createComputerPlayer(5, 5, "e2V\nC5H\nd7v\nd1h\nA4h\nA9H\nB8H\nH3H\nE0V\ni2h\n", bytes);
    computer.doOnePlacement("Submarine", computer.shipCreationFns.get("Submarine"));
    String expected = "";
    assertEquals(expected, bytes.toString());

  }

  @Test
  public void test_SmallPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 10, "B2V\nC5H\nd7v\nd1h\nA4h\nA9H\nB8H\nH3H\nE0V\ni2h\n", bytes);
    V1ShipFactory shipFactory = new V1ShipFactory();
    player.doOnePlacement("Submarine", player.shipCreationFns.get("Submarine"));
    String expectedHeader = "  0|1|2|3|4|5|6|7|8|9\n";
    String expected = "Player A where do you want to place a Submarine?\n" +
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
        expectedHeader;

    assertEquals(expected, bytes.toString());

  }

  @Test
  public void test_doPlacementPhase_computer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer computer = createComputerPlayer(10, 10,
        "B2V\nB2V\nC9h\nC5H\nd7v\nd1h\nA4h\nd3u\na3r\ne8l\nf0u\nf3l\nh5R\n", bytes);
    computer.doPlacementPhase();
    String expected = "";
    assertEquals(expected, bytes.toString());
  }

  @Test
  public void test_doPlacementPhase() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer(10, 10,
        "B2V\nB2V\nC9h\nC5H\nd7v\nd1h\nA4h\nd3u\na3r\ne8l\nf0u\nf3l\nh5R\n",
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
        "\nPlayer A: you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6\n"
        +
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
        "That placement is invalid: the ship overlaps another ship.\n" +
        "Player A where do you want to place a Submarine?\n" +
        "That placement is invalid: the ship goes off the right of the board.\n" +
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
        "A  | | | |d|d|d| | |  A\n" +
        "B  | |s| | | | | | |  B\n" +
        "C  | |s| | |s|s| | |  C\n" +
        "D  |d|d|d|b| | |d| |  D\n" +
        "E  | | |b|b|b| |d| |  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
        "Player A where do you want to place a Battleship?\n" +
        expectedHeader +
        "A  | | |b|d|d|d| | |  A\n" +
        "B  | |s|b|b| | | | |  B\n" +
        "C  | |s|b| |s|s| | |  C\n" +
        "D  |d|d|d|b| | |d| |  D\n" +
        "E  | | |b|b|b| |d| |  E\n" +
        "F  | | | | | | |d| |  F\n" +
        "G  | | | | | | | | |  G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
        "Player A where do you want to place a Battleship?\n" +
        expectedHeader +
        "A  | | |b|d|d|d| | |  A\n" +
        "B  | |s|b|b| | | | |  B\n" +
        "C  | |s|b| |s|s| | |  C\n" +
        "D  |d|d|d|b| | |d| |  D\n" +
        "E  | | |b|b|b| |d| |b E\n" +
        "F  | | | | | | |d|b|b F\n" +
        "G  | | | | | | | | |b G\n" +
        "H  | | | | | | | | |  H\n" +
        "I  | | | | | | | | |  I\n" +
        "J  | | | | | | | | |  J\n" +
        expectedHeader +
        "Player A where do you want to place a Carrier?\n" +
        expectedHeader +
        "A  | | |b|d|d|d| | |  A\n" +
        "B  | |s|b|b| | | | |  B\n" +
        "C  | |s|b| |s|s| | |  C\n" +
        "D  |d|d|d|b| | |d| |  D\n" +
        "E  | | |b|b|b| |d| |b E\n" +
        "F c| | | | | | |d|b|b F\n" +
        "G c| | | | | | | | |b G\n" +
        "H c|c| | | | | | | |  H\n" +
        "I c|c| | | | | | | |  I\n" +
        "J  |c| | | | | | | |  J\n" +
        expectedHeader +
        "Player A where do you want to place a Carrier?\n" +
        "That placement is invalid: the ship overlaps another ship.\n" +
        "Player A where do you want to place a Carrier?\n" +
        expectedHeader +

        "A  | | |b|d|d|d| | |  A\n" +
        "B  | |s|b|b| | | | |  B\n" +
        "C  | |s|b| |s|s| | |  C\n" +
        "D  |d|d|d|b| | |d| |  D\n" +
        "E  | | |b|b|b| |d| |b E\n" +
        "F c| | | | | | |d|b|b F\n" +
        "G c| | | | | | | | |b G\n" +
        "H c|c| | | | |c|c|c|c H\n" +
        "I c|c| | | |c|c|c| |  I\n" +
        "J  |c| | | | | | | |  J\n" +
        expectedHeader;

    assertEquals(expected, bytes.toString());
    // String a1=expected.substring(3900,4000);//0~1;
    // String a2= bytes.toString().substring(3900,4000);
    // assertEquals(a1,a2);
  }

}
