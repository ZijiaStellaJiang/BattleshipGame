package edu.duke.zj68.battleship;

/**
 * This calss handles textual display of a Board(i.e. converting it to a string
 * to show to the use It supports two ways to display the Board: player's own
 * board / enemy's board
 */
public class BoardTextView {
  /**
   * the board to display
   */
  private final Board toDisplay;

  /**
   * Constructs a BoardView, given the board it will display
   * 
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger tha 10*26
   */
  public BoardTextView(Board toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
          "Board must be no larger than 10*26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  /**
   *This display the empty board of one's own
   */
  public String displayMyOwnBoard() {
    String head = makeHeader();
    String body = makeBody();
    StringBuilder display = new StringBuilder(head);
    display.append(body);
    display.append(head);
    return display.toString();
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
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }

  /**
   * This makes body line, e.g. A | A\n
   */
  String makeBody() {
    StringBuilder ans = new StringBuilder("");
    for(int j = 0;j<toDisplay.getHeight();j++) {
      int letterNum = 65 +j;  //asc ii for the first letter "A"
      char letter = (char)letterNum;
      ans.append(letter);
      ans.append("  ");
      for (int i = 0; i < toDisplay.getWidth() - 1; i++) {
        ans.append("|");
        ans.append("  ");
      }
    ans.append(letter);
    ans.append("\n");
    }
    return ans.toString();
  }
}
