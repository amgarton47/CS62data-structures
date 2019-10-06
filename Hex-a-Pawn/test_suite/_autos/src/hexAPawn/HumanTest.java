package hexAPawn;

import java.util.ArrayList;
import java.util.Iterator;

import compression.AutograderCompTest;

import structure5.ReadStream;

/**
 * 
 * @author Peter Mawhorter
 * @date 5/2/17
 * @version 0.0.1
 * 
 * Copied from Alana Anderson's submission.
 */

public class HumanTest implements Player {
  private char player;
  
  /**
   * The constructor for HumanPlayer initializes a new HumanPlayer
   * with a given character
   * 
   * @param player
   */
  public HumanPlayer(char player) {
    this.player = player;
  }

  
  /**
   * Method play asks a human player for his/her move and 
   * uses a GameTree to recursively play with its opponent until
   * one player wins
   * 
   * @param node
   * @param opponent
   */
  @Override
  public Player play(GameTree node, Player opponent) {
    // get and print board
    HexBoard board = node.getBoard();
    System.out.println(board);
    
    // check if not win for opponent
    if (!board.win(HexBoard.opponent(player))) {
      // get moves 
      ArrayList<HexMove> moves = board.moves(player);
      ArrayList<GameTree> nodes = node.getChildren();

      // print out moves
      Iterator<HexMove> i = moves.iterator();
      int j = 0;
      while(i.hasNext()) {
        System.out.println(j + ". " + i.next());
        j++;
      }
      
      // ask for decision
      ReadStream rs = new ReadStream();
      int yourMove = rs.readInt();
      
      // make move and recurse
      node = nodes.get(yourMove);
      return opponent.play(node, this);
    
      
    } else {
      // else opponent won
      return opponent;
    }
  }

  /**
   * Method displayWinner prints the winner of a game
   * 
   * @param winner
   */
  public void displayWinner(Player winner) {
    System.out.println("Congratulations, " + winner.getPlayer() +  ". You won!");
  }
  
  /**
   * Method getPlayer returns the player character
   */
  public char getPlayer() {
    return this.player;
  }
  
  /**
   * Main is used to test HumanPlayer by setting up a
   * game between two humans
   * 
   * @param args
   */
  public static void main(String[] args)  {
    new AutograderCompTest();
    
    HexBoard hb = new HexBoard(3,3);
    char w = HexBoard.WHITE;
    char b = HexBoard.BLACK;
    GameTree gt = new GameTree(hb, w, null, null);
    HumanPlayer hp = new HumanPlayer(w);
    HumanPlayer hp2 = new HumanPlayer(b);
    
    Player winner = hp.play(gt, hp2);
    hp.displayWinner(winner);
  }
}
