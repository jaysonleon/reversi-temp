import org.junit.Assert;
import org.junit.Test;

import hw05.model.BasicReversi;
import hw05.model.Hex;
import hw05.model.Player;
import hw05.model.ReversiModel;
import hw06.model.ReadonlyReversiModel.Status;
import hw05.view.ReversiTextView;
import hw09.model.Tile;

/**
 * Tests for BasicReversi.
 */
public class TestBasicReversi {

  BasicReversi m = new BasicReversi();
  ReversiTextView v = new ReversiTextView(m);

  @Test
  public void testSomething() {
    m.startGame();
    Assert.assertEquals(11, m.getHeight());
    Assert.assertEquals(11, m.getWidth());
    Assert.assertEquals(Player.WHITE, m.nextTurn());
    Assert.assertEquals(Status.Playing, m.getStatus());
    m.pass();
    Assert.assertEquals(Player.BLACK, m.nextTurn());
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
  public void testCustomGame() {
    BasicReversi model = new BasicReversi(3);
    model.startGame();
    Assert.assertEquals((3 * 2) - 1, model.getHeight());
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
    Assert.assertEquals(Player.BLACK, model.determineWinner());
  }

  @Test
  public void example2XPassEndsGame() {
    m.startGame();
    Assert.assertThrows(IllegalStateException.class, () -> m.playMove(5, 5));
    m.pass();
    Assert.assertFalse(m.isGameOver());
    m.pass();
    Assert.assertTrue(m.isGameOver());
  }

  @Test
  public void testGetHeight() {
    BasicReversi model = new BasicReversi(4);
    model.startGame();
    Assert.assertEquals((4 * 2) - 1, model.getHeight());
  }

  @Test
  public void InvalidSideLength() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(2));
  }

  @Test
  public void ValidGetHeight() {
    BasicReversi m2 = new BasicReversi(4);
    m2.startGame();
    //m.startGame();
    Assert.assertEquals(7, m2.getHeight());
    //Assert.assertEquals(11, m.getHeight());
  }

  @Test
  public void ValidGetWidth() {
    BasicReversi m2 = new BasicReversi(4);
    m2.startGame();
    //m.startGame();
    Assert.assertEquals(7, m2.getWidth());
    //Assert.assertEquals(11, m.getWidth());
  }

  @Test
  public void testStatusAndPass() {
    m.startGame();
    Assert.assertEquals(Player.WHITE, m.nextTurn());
    Assert.assertEquals(Status.Playing, m.getStatus());
    m.pass();
    Assert.assertEquals(Player.BLACK, m.nextTurn());
    Assert.assertEquals(Status.Playing, m.getStatus());
    m.pass();
    Assert.assertEquals(Status.Over, m.getStatus());
    Assert.assertThrows(IllegalStateException.class, () -> m.pass());
  }

  @Test
  public void testGetScore() {
    m.startGame();
    Assert.assertEquals(3, m.getScore(Player.BLACK));
    Assert.assertEquals(3, m.getScore(Player.WHITE));
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
  public void testNextTurn() {
    m.startGame();
    Assert.assertEquals(Player.WHITE, m.nextTurn());
    m.pass();
    Assert.assertEquals(Player.BLACK, m.nextTurn());
  }

  @Test
  public void testStartGame() {
    m.startGame();
    Assert.assertThrows(IllegalStateException.class, () -> m.startGame());
    Assert.assertEquals(3, m.getScore(Player.BLACK));
    Assert.assertEquals(3, m.getScore(Player.WHITE));
    Assert.assertEquals(11, m.getHeight());
    Assert.assertEquals(11, m.getWidth());
    Assert.assertEquals(Status.Playing, m.getStatus());
    Assert.assertEquals(Player.WHITE, m.nextTurn());
  }

  @Test
  public void InvalidPlayMove() {
    Assert.assertThrows(IllegalStateException.class, () -> m.playMove(3, 6));
    m.startGame();
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(-1, 3));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(16, 3));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(3, -1));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(3, 16));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(0,0));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(5,6));
    Assert.assertThrows(IllegalStateException.class, () -> m.playMove(5,5));
  }

  @Test
  public void testIsGameOverNoMoves() {
    BasicReversi model = new BasicReversi(3);
    model.startGame();
    model.playMove(0,3);
    model.playMove(3,3);
    model.playMove(4,1);
    model.playMove(3,0);
    model.playMove(1,4);
    model.playMove(1,1);
    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(Player.BLACK, model.determineWinner());
  }

  @Test
  public void testIsGameOverWithPass() {
    m.startGame();
    m.pass();
    m.pass();
    Assert.assertTrue(m.isGameOver());
    Assert.assertEquals(Player.EMPTY, m.determineWinner());
  }

  @Test
  public void WinnerBeforeAndDuringTheGame() {
    Assert.assertThrows(IllegalStateException.class, m::determineWinner);
    BasicReversi model = new BasicReversi(3);
    model.startGame();
    Assert.assertThrows(IllegalStateException.class, model::determineWinner);
    model.playMove(0,3);
    model.playMove(3,3);
    model.playMove(4,1);
    model.playMove(3,0);
    model.playMove(1,4);
    model.playMove(1,1);
    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(Player.BLACK, model.determineWinner());
  }

  @Test
  public void testGetHexAt() {
    m.startGame();
    Assert.assertNull(m.getTileAt(0, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.getTileAt(-1, 3));
    Tile tile = new Hex(5,6);
    tile.changePlayer(Player.WHITE);
    Assert.assertEquals(tile, m.getTileAt(5,6));
  }

  @Test
  public void testGameEndBy2XPass() {
    m.startGame();
    Assert.assertThrows(IllegalStateException.class, () -> m.playMove(5, 5));
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
    Assert.assertThrows(IllegalStateException.class, () -> m.playMove(5, 5));
    m.playMove(4, 4);
    m.playMove(8, 3);
    m.playMove(9, 0);
    m.playMove(3, 4);
    m.playMove(2, 7);
    m.playMove(9, 4);
    m.playMove(10,4);
    m.playMove(2, 8);
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(0,0));
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
  public void testMoveScore() {
    m.startGame();
    Assert.assertEquals(2, m.moveScore(3, 6));
    m.playMove(3,6);
    m.playMove(2,6);
    m.playMove(2,7);
    m.playMove(2,8);
    m.playMove(6,6);
    m.playMove(7,6);
    Assert.assertEquals(3, m.moveScore(4,7));
    m.playMove(4,7);
  }

  @Test
  public void testGetSideLen() {
    m.startGame();
    Assert.assertEquals(6, m.getSideLen());

    ReversiModel m2 = new BasicReversi(4);
    m2.startGame();
    Assert.assertEquals(4, m2.getSideLen());
  }

  @Test
  public void testGetTurn() {
    m.startGame();
    Assert.assertEquals(Player.BLACK, m.getTurn());
    m.playMove(3,6);
    Assert.assertEquals(Player.WHITE, m.getTurn());
    m.playMove(2,6);
    Assert.assertEquals(Player.BLACK, m.getTurn());
  }

  @Test
  public void testIsValidMove() {
    m.startGame();
    Assert.assertTrue(m.isValidMove(3,6, Player.BLACK));
    Assert.assertFalse(m.isValidMove(3,5, Player.BLACK));
    Assert.assertFalse(m.isValidMove(3,6, Player.WHITE));
  }
}
