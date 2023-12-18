import org.junit.Assert;
import org.junit.Test;

import model.model.HexReversi;
import view.text.ReversiTextView;


/**
 * This class, TestView, is responsible for testing the Reversi game view functionality.
 * It contains test methods to ensure that the view correctly displays the game board and scores
 * before and after specific moves.
 */
public class TestView {

  HexReversi m = new HexReversi();
  ReversiTextView v = new ReversiTextView(m);

  @Test
  public void testInitialToString() {
    m.startGame();
    Assert.assertEquals(
            "     _ _ _ _ _ _ \n"
                    + "    _ _ _ _ _ _ _ \n"
                    + "   _ _ _ _ _ _ _ _ \n"
                    + "  _ _ _ _ _ _ _ _ _ \n"
                    + " _ _ _ _ X O _ _ _ _ \n"
                    + "_ _ _ _ O _ X _ _ _ _ \n"
                    + " _ _ _ _ X O _ _ _ _  \n"
                    + "  _ _ _ _ _ _ _ _ _   \n"
                    + "   _ _ _ _ _ _ _ _    \n"
                    + "    _ _ _ _ _ _ _     \n"
                    + "     _ _ _ _ _ _      \n", v.toString());
  }

  @Test
  public void ViewAfterMove() {
    HexReversi model = new HexReversi(3);
    ReversiTextView view = new ReversiTextView(model);
    model.startGame();
    model.playMove(0,3);
    Assert.assertEquals(
            "  _ _ _ \n"
                    + " _ X O _ \n"
                    + "_ X _ X _ \n"
                    + " X X O _  \n"
                    + "  _ _ _   \n", view.toString());
    model.playMove(3,3);
    Assert.assertEquals(
            "  _ _ _ \n"
                    + " _ X O _ \n"
                    + "_ X _ O _ \n"
                    + " X X O O  \n"
                    + "  _ _ _   \n", view.toString());
    model.playMove(4,1);
    Assert.assertEquals(
            "  _ _ _ \n"
                    + " _ X X X \n"
                    + "_ X _ O _ \n"
                    + " X X O O  \n"
                    + "  _ _ _   \n", view.toString());
    model.playMove(3,0);
    Assert.assertEquals(
            "  _ O _ \n"
                    + " _ X O X \n"
                    + "_ X _ O _ \n"
                    + " X X O O  \n"
                    + "  _ _ _   \n", view.toString());
    model.playMove(1,4);
    Assert.assertEquals(
            "  _ O _ \n"
                    + " _ X O X \n"
                    + "_ X _ X _ \n"
                    + " X X X O  \n"
                    + "  _ X _   \n", view.toString());
    model.playMove(1,1);
    Assert.assertEquals(
            "  _ O _ \n"
                    + " O O O X \n"
                    + "_ X _ X _ \n"
                    + " X X X O  \n"
                    + "  _ X _   \n", view.toString());
  }
}
