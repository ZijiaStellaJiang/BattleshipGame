package edu.duke.zj68.battleship;

import java.util.function.Function;

/**
 * This calss handles textual display of a Board(i.e. converting it to a string
 * to show to the use It supports two ways to display the Board: player's own
 * board / enemy's board
 */
public class BoardTextView {
  /**
   * the board to display
   */
  private final Board<Character> toDisplay;

  /**
   * Constructs a BoardView, given the board it will display
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger tha 10*26
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10*26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  
  /**
   *This display the board with ships of one's own of the one for enemy
   */
  protected String displayAnyBoard(Function<Coordinate,Character> getSquareFn) {
    String head = makeHeader();
    String body = makeBody(getSquareFn);
    StringBuilder display = new StringBuilder(head);
    display.append(body);
    display.append(head);
    return display.toString();
  }
  public String displayMyOwnBoard() {
    return displayAnyBoard((c)-> toDisplay.whatIsAtForSelf(c));
  }
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }

  /**
   * This makes the header line, e.g. 0|1|2|3|4\n return the String that is the
   * header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  ");
    String sep = "";
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "| ";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This makes body line, e.g. A | A\n
   */
  String makeBody(Function<Coordinate,Character> getSquareFn) {
    StringBuilder ans = new StringBuilder("");
    for(int i = 0;i<toDisplay.getHeight();i++) {
      int letterNum = 65 +i;  //asc ii for the first letter "A"
      char letter = (char)letterNum;
      ans.append(letter);
      for (int j = 0; j < toDisplay.getWidth() ; j++) {
        Coordinate where = new Coordinate(i,j);
        Character displayInfo = getSquareFn.apply(where); //toDisplay.whatIsAtForSelf(where);
        if (displayInfo==null) {
          ans.append("  ");
        }
        else {
          ans.append(displayInfo);
          ans.append(" ");
        }
        if (j!=toDisplay.getWidth()-1) {
          ans.append("|");
        }
      }
    ans.append(letter);
    ans.append("\n");
    }
    return ans.toString();
  }
}
