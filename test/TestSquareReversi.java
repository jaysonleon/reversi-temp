import org.junit.Assert;
import org.junit.Test;

import hw05.model.Player;
import hw05.model.ReversiModel;
import hw05.view.TextualView;
import hw09.model.AbstractReversi;
import hw09.model.SquareReversi;
import hw09.textView.SquareTextView;

public class TestSquareReversi {

  ReversiModel m = new AbstractReversi(8, false);

  // test to make sure AbstractReversi throws correct exceptions when given invalid inputs \
  // for sideLength, for both square and hex Reversi.
  @Test
  public void testAbstractThrowsCorrectly() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new AbstractReversi(2, true));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new AbstractReversi(2, false));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new AbstractReversi(3, false));
  }

  @Test
  public void testSquareTextView() {
    m.startGame();
    TextualView view = new SquareTextView(m);
    Assert.assertEquals("_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ X O _ _ _ \n"
            + "_ _ _ O X _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _ \n"
            + "_ _ _ _ _ _ _ _", view.toString());
  }

  @Test
  public void testFullGameToCompletion() {
    m.startGame();
    m.playMove(3,5);
    m.playMove(2,3);
    m.playMove(1,2);
    m.playMove(3,6);
    m.playMove(5,3);
    m.pass();
    m.playMove(3,7);
    Assert.assertTrue(m.isGameOver());
    Assert.assertEquals(Player.BLACK, m.determineWinner());
  }

  @Test
  public void testFullGame2XPass() {
    m.startGame();
    m.pass();
    Assert.assertFalse(m.isGameOver());
    m.pass();
    Assert.assertTrue(m.isGameOver());
    Assert.assertEquals(Player.EMPTY, m.determineWinner());
  }
}
