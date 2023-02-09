package edu.duke.tl330.battleship;

public class ActionCount {
  private int move;
  private int scan;
  public ActionCount(){
    this.move=2;
    this.scan=1;
  }
  public int getMove(){
    return move;}
  public int getScan(){
    return scan;
  }
  
  public boolean canScan(){
    if(scan>0){
      scan-=1;
      return true;
    }
    return false;
  }
  public boolean canMove(){
    if(move>0){
      move-=1;
      return true;
    }
    return false;
  }
}
