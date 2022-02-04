package edu.duke.zj68.battleship;

/**
 *abstract class that contain all the extra actions player can make
 *with the remainging use times
 */
public abstract class ExtraAction <T>{
  protected Board<T> theBoard;
  protected int remain;

  public ExtraAction(Board<T> b, int r) {
    this.theBoard = b;
    this.remain = r;
  }

  public int getRemain() {
    return this.remain;
  }
  
}
