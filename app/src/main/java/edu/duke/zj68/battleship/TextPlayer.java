package edu.duke.zj68.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

public class TextPlayer {
  private final String name;
  private final Board<Character> theBoard;
  private final BoardTextView view;
  private final BufferedReader inputReader;
  private final PrintStream out;
  private final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character> > > shipCreationFns;

  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer",(p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }
  public TextPlayer(String name, Board<Character> b, BufferedReader inputReader, PrintStream out, V1ShipFactory f) {
    this.name = name;
    this.theBoard = b;
    this.view = new BoardTextView(b);
    this.inputReader = inputReader;
    this.out = out;
    this.shipFactory = f;
    this.shipsToPlace = new ArrayList<String>();
    setupShipCreationList();
    this.shipCreationFns = new HashMap<String, Function<Placement,Ship<Character> > >();
    setupShipCreationMap();
  }
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s==null) {
      throw new EOFException("Empty input");
    }
    return new Placement(s);
  }
  public void doOnePlacement(String shipName, Function<Placement,Ship<Character> > createFn) throws IOException {
    try {
      Placement toPlace = readPlacement("Player "+name+" where do you want to place a "+shipName+"?");
      Ship<Character> s = createFn.apply(toPlace);
      //if placement invalid and unable to make a ship, keep asking to read placement
      while(theBoard.tryAddShip(s)!= null) {
        out.println(theBoard.tryAddShip(s));
        toPlace = readPlacement("Player "+name+" where do you want to place a "+shipName+"?");
        s= createFn.apply(toPlace);
      }
      out.println(view.displayMyOwnBoard());
    }
    catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      doOnePlacement(shipName, createFn);
    }
    /**
       catch (EOFException e) {
      out.println(e.getMessage());
      doOnePlacement(shipName, createFn);
    }
    */
  }
  public void doPlacementPhase() throws IOException {
    out.println(view.displayMyOwnBoard());
    String promptInstruction = "Player "+name+": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical). For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n\n"+"2 'Submarine' ships that are 1x2\n3 'Destroyers' that are 1x3\n3 'Battleships' that are 1x4\n2 'Carriers' that are 1x6\n";
    out.println(promptInstruction);
    for (String name : shipsToPlace) {
      doOnePlacement(name, shipCreationFns.get(name));
    }
  }
}
