package edu.duke.zj68.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class TextPlayer {
  private final String name;
  private final Board<Character> theBoard;
  private final BoardTextView view;
  private final BufferedReader inputReader;
  private final PrintStream out;
  private final AbstractShipFactory<Character> shipFactory;

  public TextPlayer(String name, Board<Character> b, BufferedReader inputReader
                    /*Reader inputSource*/, PrintStream out, V1ShipFactory f) {
    this.name = name;
    this.theBoard = b;
    this.view = new BoardTextView(b);
    this.inputReader = inputReader;//new BufferedReader(inputSource);
    this.out = out;
    this.shipFactory = f;
  }
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    return new Placement(s);
  }
  public void doOnePlacement() throws IOException {
    Placement toPlace = readPlacement("Player "+name+" where do you want to place a Destroyer?");
    Ship<Character> s = shipFactory.makeDestroyer(toPlace);
    theBoard.tryAddShip(s);
    out.println(view.displayMyOwnBoard());
  }
  public void doPlacementPhase() throws IOException {
    out.println(view.displayMyOwnBoard());
    String promptInstruction = "Player "+name+": you are going to place the following ships (which are all rectangular). For each ship, type the coordinate of the upper left side of the ship, followed by either H (for horizontal) or V (for vertical). For example M4H would place a ship horizontally starting at M4 and going to the right. You have\n\n"+"2 'Submarine' ships that are 1x2\n3 'Destroyers' that are 1x3\n3 'Battleships' that are 1x4\n2 'Carriers' that are 1x6\n";
    out.println(promptInstruction);
    doOnePlacement();
  }
}
