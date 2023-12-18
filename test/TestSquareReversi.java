import org.junit.Assert;
import org.junit.Test;

import model.player.Player;
import model.model.ReversiModel;
import view.text.TextualView;

import model.model.ReadonlyReversiModel.Status;

import model.model.Board;
import model.model.Cell;
import model.model.SquareReversi;
import view.text.SquareTextView;

public class TestSquareReversi {

  ReversiModel m = new SquareReversi();
  ReversiModel m2 = new SquareReversi(4);

  // test to make sure AbstractReversi throws correct exceptions when given invalid inputs \
  // for sideLength, for both square and hex Reversi.

  @Test
  public void testConstructorThrows() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new SquareReversi(2));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new SquareReversi(3));
  }

  @Test
  public void testSquareModelSetUp() {
    Assert.assertEquals(8, m.getSideLen());
    Assert.assertEquals(8, m.getHeight());
    Assert.assertEquals(8, m.getWidth());
    Assert.assertEquals(8, m.getBoard().getDim());
  }

  @Test
  public void testSquareGameSetUp() {
    m.startGame();
    Assert.assertEquals(Player.BLACK, m.getTurn());
    Assert.assertEquals(2, m.getScore(Player.BLACK));
    Assert.assertEquals(2, m.getScore(Player.WHITE));
  }

  @Test
  public void testSquarePlayMove() {
    Assert.assertThrows(IllegalStateException.class, () -> m.playMove(0, 0));
    m.startGame();
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(-1, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(10, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(0, -1));
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(0, 10));
    // hex is occupied
    Assert.assertThrows(IllegalArgumentException.class, () -> m.playMove(3, 3));
    // invalid move
    Assert.assertThrows(IllegalStateException.class, () -> m.playMove(0,0));
    // valid move
    m.playMove(3, 5);
    Assert.assertEquals(Player.WHITE, m.getTurn());
    Assert.assertEquals(4, m.getScore(Player.BLACK));
    Assert.assertEquals(1, m.getScore(Player.WHITE));
    Assert.assertFalse(m.isGameOver());
  }

  @Test
  public void testSquarePaas() {
    Assert.assertThrows(IllegalStateException.class, () -> m.pass());
    m.startGame();
    m.pass();
    Assert.assertEquals(Player.WHITE, m.getTurn());
    m.pass();
    Assert.assertEquals(Status.Over, m.getStatus());
    Assert.assertTrue(m.isGameOver());
  }

  @Test
  public void testFullGameToCompletion() {
    m.startGame();
    TextualView view = new SquareTextView(m);
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

  @Test
  public void testSquareGetBoard() {
    m.startGame();
    Board b1 = m.getBoard();
    m.pass();
    Board b2 = m.getBoard();
    Assert.assertEquals(b1, b2);
  }

  @Test
  public void testSquareGetTileAt() {
    m.startGame();
    Assert.assertEquals(new Cell(3,3, Player.BLACK), m.getTileAt(3, 3));
    Assert.assertEquals(new Cell(3, 4, Player.WHITE), m.getTileAt(3, 4));
    Assert.assertEquals(new Cell(4, 3, Player.WHITE), m.getTileAt(4, 3));
    Assert.assertEquals(new Cell(4, 4, Player.BLACK), m.getTileAt(4, 4));
    Assert.assertEquals(new Cell(0,0), m.getTileAt(0, 0));
  }

  @Test
  public void testSquareNextTurn() {
    m.startGame();
    Assert.assertEquals(Player.BLACK, m.getTurn());
    Assert.assertEquals(Player.WHITE, m.nextTurn());
    m.pass();
    Assert.assertEquals(Player.BLACK, m.nextTurn());
  }

  @Test
  public void testSquareDetermineWinner() {
    // tie
    Assert.assertThrows(IllegalStateException.class, () -> m.determineWinner());
    m.startGame();
    Assert.assertThrows(IllegalStateException.class, () -> m.determineWinner());
    m.pass();
    m.pass();
    Assert.assertEquals(Player.EMPTY, m.determineWinner());
    // Black wins
    ReversiModel m2 = new SquareReversi();
    m2.startGame();
    m2.playMove(3,5);
    m2.pass();
    m2.pass();
    Assert.assertEquals(Player.BLACK, m2.determineWinner());
    // White wins
    ReversiModel m3 = new SquareReversi();
    m3.startGame();
    m3.pass();
    m3.playMove(2,3);
    m3.pass();
    m3.pass();
    Assert.assertEquals(Player.WHITE, m3.determineWinner());
  }

  @Test
  public void testSquareCopyBoard() {
    m.startGame();
    Board b1 = m.getBoard();
    Board b2 = m.copyBoard();
    Assert.assertEquals(b1, b2);
  }

  @Test
  public void testSquareIsValidMove() {
    m.startGame();
    Assert.assertFalse(m.isValidMove(0, 0, Player.BLACK));
    Assert.assertFalse(m.isValidMove(3, 3, Player.BLACK));
    Assert.assertFalse(m.isValidMove(3, 3, Player.WHITE));
    Assert.assertTrue(m.isValidMove(3, 5, Player.BLACK));
    Assert.assertFalse(m.isValidMove(3, 5, Player.WHITE));
    Assert.assertFalse(m.isValidMove(0, 0, Player.EMPTY));
    Assert.assertFalse(m.isValidMove(0, 0, null));
  }

  @Test
  public void testSquareMoveScore() {
    m.startGame();
    Assert.assertEquals(2, m.moveScore(3, 5));
    Assert.assertEquals(0, m.moveScore(3, 3));
    Assert.assertEquals(0, m.moveScore(0, 0));
    m.playMove(3, 5);
    m.playMove(2, 3);
    m.playMove(1, 2);
    Assert.assertEquals(2, m.getScore(Player.WHITE));
    Assert.assertEquals(3, m.moveScore(3, 6));
    m.playMove(3, 6);
    Assert.assertEquals(5, m.getScore(Player.WHITE));
  }

  @Test
  public void testSquareHasValidMoves() {
    m2.startGame();
    TextualView view = new SquareTextView(m2);
    m2.playMove(1, 3);
    m2.pass();
    m2.playMove(2, 0);
    Assert.assertFalse(m2.hasValidMoves(Player.WHITE));
    Assert.assertFalse(m2.hasValidMoves(Player.BLACK));

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
}
