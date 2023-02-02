package edu.duke.tl330.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class TextPlayer {
  final BufferedReader inputReader;
  final PrintStream out;
  final Board<Character> theBoard;
  final BoardTextView view;
  private String name;
  final AbstractShipFactory<Character> factory;

  // constructor
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader input, PrintStream out,
      AbstractShipFactory<Character> factory) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = input;
    this.out = out;// System.out;
    this.factory = new V1ShipFactory();
  }

  // @param String
  // return placement
  public Placement readPlacement(String prompt) throws IOException {
    // out.println(prompt);
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }

  // read input
  // place one whip and print board
  public void doOnePlacement() throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a Destroyer?");
    // RectangleShip<Character> s = new RectangleShip<Character>(p.getWhere(), 's',
    // '*');
    Ship<Character> s = factory.makeDestroyer(p);
    theBoard.tryAddShip(s);
    out.println(view.displayMyOwnBoard());
  }

  public void doPlacementPhase() throws IOException {
    this.out.println(this.view.displayMyOwnBoard());
    this.out.println("Player " + name
        + ": you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6");
    doOnePlacement();
  }

}
