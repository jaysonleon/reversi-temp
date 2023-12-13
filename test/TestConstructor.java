import org.junit.Assert;
import org.junit.Test;

import hw05.model.BasicReversi;
import hw05.model.Player;
import hw05.view.ReversiTextView;
import hw06.model.ReadonlyReversiModel.Status;

/**
 * Tests for the constructor of the Reversi game.
 */
public class TestConstructor {

  BasicReversi m = new BasicReversi();
  ReversiTextView v = new ReversiTextView(m);

  @Test
  public void testSomething() {
    m.startGame();
    Assert.assertEquals(11, m.getHeight());
    Assert.assertEquals(11, m.getWidth());
    Assert.assertEquals(Player.BLACK, m.getTurn());
    Assert.assertEquals(Status.Playing, m.getStatus());
    m.pass();
    Assert.assertEquals(Player.WHITE, m.getTurn());
  }

  @Test
  public void testToString() {
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
  public void testPlayMove() {
    m.startGame();
    m.playMove(3, 6);
    Assert.assertEquals(5, m.getScore(Player.BLACK));
    Assert.assertEquals(2, m.getScore(Player.WHITE));
    m.playMove(2, 6);
    Assert.assertEquals(3, m.getScore(Player.BLACK));
    Assert.assertEquals(5, m.getScore(Player.WHITE));
    m.playMove(2, 7);
    Assert.assertEquals(5, m.getScore(Player.BLACK));
    Assert.assertEquals(4, m.getScore(Player.WHITE));
  }

  @Test
  public void testGameEndBy2XPass() {
    m.startGame();
    m.playMove(3, 6);
    m.playMove(2, 6);
    m.playMove(6, 3);
    m.playMove(7, 4);
    m.playMove(6, 6);
    m.playMove(7, 6);
    m.playMove(8, 4);
    m.playMove(6, 2);
    m.playMove(7, 2);
    m.playMove(8, 1);
    m.playMove(4, 4);
    m.playMove(8, 3);
    m.playMove(9, 0);
    m.playMove(3, 4);
    m.playMove(2, 7);
    m.playMove(9, 4);
    m.playMove(10,4);
    m.playMove(2, 8);
    m.playMove(2, 4);
    m.playMove(8, 2);
    m.playMove(1, 6);
    m.playMove(0, 6);
    m.playMove(1, 8);
    m.playMove(8, 5);
    m.playMove(8, 6);
    m.playMove(9, 6);
    m.playMove(8, 7);
    m.playMove(9, 2);
    m.playMove(6, 7);
    m.playMove(1, 4);
    m.playMove(10, 1);
    m.playMove(6, 8);
    m.playMove(6, 9);
    m.playMove(0, 8);
    m.playMove(0, 9);
    m.playMove(7,8);
    m.playMove(10, 5);
    m.playMove(10, 2);
    m.playMove(10, 3);
    m.playMove(0, 10);
    m.playMove(5, 2);
    m.pass();
    m.playMove(4, 7);
    m.playMove(4, 8);
    m.playMove(2, 5);
    m.pass();
    m.playMove(4, 3);
    m.playMove(4, 2);
    m.playMove(2, 3);
    m.playMove(3, 2);
    m.playMove(3, 8);
    m.playMove(2, 9);
    m.playMove(1, 10);
    m.playMove(2,10);
    m.playMove(4, 1);
    m.playMove(5, 0);
    m.playMove(5, 8);
    m.playMove(4, 9);
    m.playMove(0, 7);
    m.playMove(6, 1);
    m.playMove(7, 0);
    m.pass();
    Assert.assertFalse(m.isGameOver());
    m.playMove(3, 10);
    m.playMove(4, 10);
    m.playMove(0, 5);
    m.pass();
    m.pass();
    Assert.assertTrue(m.isGameOver());
    Assert.assertEquals(Status.Over, m.getStatus());
  }


  @Test
  public void testCustomGame() {
    BasicReversi model = new BasicReversi(5);
    model.startGame();
    Assert.assertEquals((5 * 2) - 1, model.getHeight());
  }

  @Test
  public void testSmallGameNoMovesLeft() {
    BasicReversi model = new BasicReversi(3);
    model.startGame();
    model.playMove(0,3);
    model.playMove(3,3);
    model.playMove(4,1);
    model.playMove(3,0);
    model.playMove(1,4);
    model.playMove(1,1);
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void example2XPassEndsGame() {
    m.startGame();
    m.pass();
    Assert.assertFalse(m.isGameOver());
    m.pass();
    Assert.assertTrue(m.isGameOver());
  }
}
