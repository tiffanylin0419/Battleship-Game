/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.tl330.battleship;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;

public class App {
  private TextPlayer player1;
  private TextPlayer player2;

  // constructor
  public App(TextPlayer player1, TextPlayer player2) {
    this.player1 = player1;
    this.player2 = player2;

  }

  public void doPlacementPhase() throws IOException {
    this.player1.doPlacementPhase();
    this.player2.doPlacementPhase();
  }

  public void doAttackingPhase() throws IOException {
    while (true) {
      if (player1.theBoard.noShips()) {
        player1.out.println("Player " + player2.name + " win. Player " + player1.name + " lose.");
        return;
      }
      player1.doOneAction(player2);
      if (player2.theBoard.noShips()) {
        player2.out.println("Player " + player1.name + " win. Player " + player2.name + " lose.");
        return;
      }
      player2.doOneAction(player1);
    }

  }

  public static void main(String[] args) throws IOException {

    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    V1ShipFactory factory = new V1ShipFactory();
    System.out.println("Is player A computer? (Y for yes, N for no)");
    String s1 = input.readLine();
    while (!(s1.equals("Y") || s1.equals("N"))) {
      System.out.println("Input needs to be Y or N.");
      s1 = input.readLine();
    }
    System.out.println("Is player B computer? (Y for yes, N for no)");
    String s2 = input.readLine();
    while (!(s2.equals("Y") || s2.equals("N"))) {
      System.out.println("Input needs to be Y or N.");
      s2 = input.readLine();
    }
    
    TextPlayer p1 = new TextPlayer("A", b1, input, System.out, factory);
    TextPlayer p2 = new TextPlayer("B", b2, input, System.out, factory);
    App app = new App(p1, p2);
    app.doPlacementPhase();
    app.doAttackingPhase();
  }
}
