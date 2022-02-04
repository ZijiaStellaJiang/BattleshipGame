package edu.duke.zj68.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;

public class TextPlayer {
  final String name;
  final Board<Character> theBoard;
  final BoardTextView view;
  final BufferedReader inputReader;
  final PrintStream out;
  final AbstractShipFactory<Character> shipFactory;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character> > > shipCreationFns;
  final HashMap<Character,String> shipNames;

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
  protected void setupShipName() {
    shipNames.put('s', "Submarine");
    shipNames.put('d',"Destroyer");
    shipNames.put('c',"Carrier");
    shipNames.put('b',"Battleship");
  }
  public TextPlayer(String name, Board<Character> b, BufferedReader inputReader, PrintStream out, /*V1ShipFactory*/ AbstractShipFactory<Character> f) {
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
    this.shipNames = new HashMap<>();
    setupShipName();
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
  }
  public void doPlacementPhase() throws IOException {
    out.println(view.displayMyOwnBoard());
    String promptInstruction = "Player "+name+": you are going to place the following ships. For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical) or U (for up direction) or D (for down direction) or L (for left direction) or R (for right direction). For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n\n"+"2 'Submarine' ships that are 1x2\n3 'Destroyers' that are 1x3\n3 'Battleships' that are 'T' shaped\n2 'Carriers' that are 'Z' shaped\n";
    out.println(promptInstruction);
    for (String name : shipsToPlace) {
      doOnePlacement(name, shipCreationFns.get(name));
    }
  }
  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    String s= inputReader.readLine();
    if(s==null) {
      throw new EOFException("Empty input");
    }
    return new Coordinate(s);
  }
  public void playOneTurn(Board<Character> enemy,BoardTextView enemyView,String enemyName) throws IOException {
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player "+enemyName+"'s Ocean"));
    try {
      Coordinate toFire = readCoordinate("Player "+name+" which coordinate do you want to fire at?");
      Ship<Character> fire = enemy.fireAt(toFire);
      if(fire==null) {
        out.println("You missed!");
      }
      else {
        Character info = fire.getDisplayInfoAt(toFire, false);
        String shipName = shipNames.get(info);
        out.println("You hit a "+shipName+"!");
      }
    }
    catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      playOneTurn(enemy, enemyView, enemyName);
    }
  }
  public Ship<Character> readShip(String prompt) throws IOException{

    out.println(prompt);
    String s = inputReader.readLine();
    if(s==null) {
      throw new EOFException("Empty input");
    }
    Coordinate inShip = new Coordinate(s);
    return theBoard.selectShip(inShip);
  }
  public void doMoveShip(Board<Character> enemy,BoardTextView enemyView,String enemyName) throws IOException{
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player "+enemyName+"'s Ocean"));
    Ship<Character> toMove = readShip("Player "+name+" which ship do you want to move? You can enter any coordinate in the ship\n");
    theBoard.removeShip(toMove);
    Placement newLocation = readPlacement("Please enter the new placement\n");
    Ship<Character> afterMove = shipCreationFns.get(toMove.getName()).apply(newLocation);
    theBoard.moveShipProcess(toMove, afterMove);
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player "+enemyName+"'s Ocean"));
  }
  public void doSonarScan(Board<Character> enemy,BoardTextView enemyView,String enemyName) throws IOException{
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your Ocean", "Player "+enemyName+"'s Ocean"));
    Coordinate center = readCoordinate("Player "+name+" please enter the center coordinate for sonar scan\n");
    HashMap<String,Integer> result = enemy.doSonarScan(center);
    out.print("Submarines occupy "+result.get("Submarine")+" squares\n");
    out.print("Destroyers occupy "+result.get("Destroyer")+" squares\n");
    out.print("Battleships occupy "+result.get("Battleship")+" squares\n");
    out.print("Carriers occupy "+result.get("Carrier")+" squares\n");
  }
}
