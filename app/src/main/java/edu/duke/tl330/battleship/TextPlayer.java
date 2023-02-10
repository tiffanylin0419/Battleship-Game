package edu.duke.tl330.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  final BufferedReader inputReader;
  final PrintStream out;
  final Board<Character> theBoard;
  final BoardTextView view;
  final String name;
  final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  final ActionCount action;
  private boolean isHuman;

  // constructor
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader input, PrintStream out,
      AbstractShipFactory<Character> shipFactory, boolean isHuman) {
    this(name, theBoard, input, out, shipFactory);
    this.isHuman = isHuman;
  }

  // constructor
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader input, PrintStream out,
      AbstractShipFactory<Character> shipFactory) {
    this.name = name;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = input;
    this.out = out;// System.out;
    this.shipFactory = shipFactory;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    setupShipCreationMap();
    setupShipCreationList();
    this.action = new ActionCount();
    this.isHuman = true;
  }

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
  }

  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }

  // @param String
  // return placement
  public Placement readPlacement(String prompt) throws IOException {
    // out.println(prompt);
    if (isHuman) {
      out.print(prompt);
    }
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("No input.\n");
    }
    return new Placement(s);
  }

  public Coordinate readCoordinate() throws IOException {
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("No input.\n");
    }
    Coordinate c = new Coordinate(s);
    while (c.getColumn() < 0 || c.getColumn() >= theBoard.getWidth() || c.getRow() < 0
        || c.getRow() >= theBoard.getHeight()) {
      if (isHuman) {
        out.println("Coordinate not in board, enter again.");
      }
      s = inputReader.readLine();
      if (s == null) {
        throw new EOFException("No input.\n");
      }
      c = new Coordinate(s);
    }
    return c;
  }

  public char readAction(String prompt) throws IOException {
    if (isHuman) {
      out.print(prompt);
    }
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException("No input.\n");
    } else {
      s = s.toUpperCase();
      if (s.equals("F") || s.equals("M") || s.equals("S")) {
        return s.charAt(0);
      } else {
        return readAction("Input must be F, M or S\n");
      }
    }
  }

  public void doOneAction(TextPlayer enemy) throws IOException {
    char act = readAction("Possible actions for Player " + name
        + ":\n\n F Fire at a square\n M Move a ship to another square (" + action.getMove()
        + " remaining)\n S Sonar scan (" + action.getScan() + " remaining)\n\nPlayer "
        + name + ", what would you like to do?\n");
    while (true) {
      if (act == 'F') {
        playOneTurn(enemy);
        return;
      } else if (act == 'M') {
        if (action.canMove()) {
          if (doMove(enemy) == false) {
            act = readAction("");
            continue;
          }
          action.doMove();
          return;
        } else {
          act = readAction("No more moves.\n");
        }
      } else {// 'S'
        if (action.canScan()) {
          doScan(enemy);
          action.doScan();
          return;
        } else {
          act = readAction("No more scans.\n");
        }
      }
    }
  }

  // read input
  // place one whip and print board
  public void doOnePlacement() throws IOException {
    Placement p = readPlacement("Player " + name + " where do you want to place a Destroyer?\n");
    // RectangleShip<Character> s = new RectangleShip<Character>(p.getWhere(), 's',
    // '*');
    Ship<Character> s = shipFactory.makeDestroyer(p);
    String error = theBoard.tryAddShip(s);
    while (error != null) {
      out.println(error);

      p = readPlacement("Player " + name + " where do you want to place a Destroyer?\n");
      Ship<Character> s1 = shipFactory.makeDestroyer(p);
      error = theBoard.tryAddShip(s1);
    }
    out.println(view.displayMyOwnBoard());

  }

  // called with
  // doOnePlacement("Submarine", (p)->shipFactory.makeSubmarine(p));
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    Placement p = readPlacement("Player " + name +
        " where do you want to place a " + shipName + "?\n");
    Ship<Character> s = createFn.apply(p);
    String error = theBoard.tryAddShip(s);
    while (error != null) {
      if (isHuman) {
        out.println(error);
      }
      p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?\n");
      s = createFn.apply(p);
      error = theBoard.tryAddShip(s);
    }
    if (isHuman) {
      out.print(view.displayMyOwnBoard());
    }
  }

  // print empty board
  // display game info and ask for input
  // display board with ship added
  public void doPlacementPhase() throws IOException {
    if (isHuman) {
      out.println(this.view.displayMyOwnBoard());
      out.println("Player " + name
          + ": you are going to place the following ships (which are all\nrectangular). For each ship, type the coordinate of the upper left\nside of the ship, followed by either H (for horizontal) or V (for\nvertical).  For example M4H would place a ship horizontally starting\nat M4 and going to the right.  You have\n\n2 \"Submarines\" ships that are 1x2\n3 \"Destroyers\" that are 1x3\n3 \"Battleships\" that are 1x4\n2 \"Carriers\" that are 1x6");
    }
    for (String i : shipsToPlace) {
      doOnePlacement(i, shipCreationFns.get(i));
    }
  }

  public void playOneTurn(TextPlayer enemy) throws IOException {
    // Board<Character> theBoardEnemy,BoardTextView viewEnemy, String nameEnemy){
    if (isHuman) {
      out.print(view.displayMyBoardWithEnemyNextToIt(enemy.view, name, enemy.name));
    }
    Coordinate c = readCoordinate();

    Ship<Character> s = enemy.theBoard.fireAt(c);

    if (s != null) {
      if (isHuman) {
        out.println("You hit a " + s.getName().toLowerCase() + "!");
      } else {
        out.println("Player " + name + " hit your " + s.getName() + " at " + c.toLetterString() + "!");
      }
    } else {
      if (isHuman) {
        out.println("You missed!");
      } else {
        out.println("Player " + name + " missed!");
      }

    }
  }

  public boolean doMove(TextPlayer enemy) throws IOException {
    Coordinate c = readCoordinate();
    Ship<Character> oldShip = theBoard.getShipAt(c);
    if (oldShip == null) {
      return false;
    }
    Placement p = readPlacement("");
    Ship<Character> newShip = shipCreationFns.get(oldShip.getName()).apply(p);
    if (theBoard.tryMoveShip(oldShip, newShip) != null) {
      return false;
    }
    if (!isHuman) {
      out.println("Player " + name + " used a special action");
    }
    return true;
  }

  public void doScan(TextPlayer enemy) throws IOException {

    Coordinate c = readCoordinate();
    if (isHuman) {
      out.println("Submarines occupy " + enemy.theBoard.scanFor(c, 's') + " squares");
      out.println("Destroyers occupy " + enemy.theBoard.scanFor(c, 'd') + " squares");
      out.println("Battleships occupy " + enemy.theBoard.scanFor(c, 'b') + " squares");
      out.println("Carriers occupy " + enemy.theBoard.scanFor(c, 'c') + " squares");
    } else {
      out.println("Player " + name + " used a special action");
    }
  }
}
