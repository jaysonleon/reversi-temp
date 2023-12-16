import org.junit.Assert;
import org.junit.Test;

import hw05.model.BasicReversi;
import hw05.model.Hex;
import hw05.model.HexBoard;
import hw05.model.Player;
import hw05.model.ReversiModel;
import hw09.model.Board;
import hw09.model.SquareBoard;

/**
 * Tests for the Board class.
 */
public class TestBoard {

  Board hexB = new HexBoard(11);
  Board sqB = new HexBoard(8);

  @Test
  public void testBoardConstructors() {
    Assert.assertEquals(11, hexB.getDim());
    Assert.assertEquals(11, hexB.getHeight());
    Assert.assertEquals(11, hexB.getWidth());
    Assert.assertEquals(8, sqB.getDim());
    Assert.assertEquals(8, sqB.getHeight());
    Assert.assertEquals(8, sqB.getWidth());
  }

  @Test
  public void testHexCopyBoard() {
    ReversiModel m = new BasicReversi();
    m.startGame();
    m.playMove(3,6);
    Board b1 = m.getBoard();
    Board b2 = m.copyBoard();
    Assert.assertEquals(b1, b2);
    Assert.assertEquals(b1.hashCode(), b2.hashCode());
  }

  @Test
  public void testHexBoardGetTileAt() {
    Assert.assertNull(hexB.getTileAt(0,0));
    Assert.assertNull(hexB.getTileAt(-1, 3));
    Assert.assertNull(hexB.getTileAt(16, 3));
    Assert.assertNull(sqB.getTileAt(-1, 0));
    Assert.assertNull(sqB.getTileAt(10, 0));
    Assert.assertNull(sqB.getTileAt(0, -1));
    Assert.assertNull(sqB.getTileAt(0, 10));

  }

  @Test
  public void testBoardSetTileAt() {
    Assert.assertThrows(IllegalArgumentException.class, () -> hexB.setTileAt(-1, 3));
    Assert.assertThrows(IllegalArgumentException.class, () -> hexB.setTileAt(16, 3));
    Assert.assertThrows(IllegalArgumentException.class, () -> sqB.setTileAt(-1, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> sqB.setTileAt(10, 0));
    Assert.assertThrows(IllegalArgumentException.class, () -> sqB.setTileAt(0, -1));
    Assert.assertThrows(IllegalArgumentException.class, () -> sqB.setTileAt(0, 10));
  }

  @Test
  public void testHexBoardEquals() {
    Board b1 = new HexBoard(11);
    Board b2 = new HexBoard(11);
    Assert.assertEquals(b1.hashCode(), b1.hashCode());
    Assert.assertEquals(b1, b2);
    Assert.assertEquals(b1.hashCode(), b2.hashCode());
    b1.setTileAt(3, 6);
    Assert.assertNotEquals(b1, b2);
    Assert.assertNotEquals(b1.hashCode(), b2.hashCode());
    Board b3 = new HexBoard(12);
    Assert.assertNotEquals(b1, b3);
    Assert.assertNotEquals(b1.hashCode(), b3.hashCode());
    Assert.assertNotEquals(b1, null);
    Assert.assertNotEquals(b1, new SquareBoard(4));
  }

  @Test
  public void testSquareBoardEquals() {
    Board b1 = new SquareBoard(8);
    b1.addStartingCells(8, 8);
    Board b2 = new SquareBoard(8);
    b2.addStartingCells(8, 8);
    Assert.assertEquals(b1.hashCode(), b2.hashCode());
    Assert.assertEquals(b1, b2);
    b1.getTileAt(3, 3).changePlayer(Player.WHITE);
    Assert.assertNotEquals(b1, b2);
    Assert.assertNotEquals(b1.hashCode(), b2.hashCode());
    Board b3 = new SquareBoard(10);
    b3.addStartingCells(10, 10);
    Assert.assertNotEquals(b2, b3);
    Assert.assertNotEquals(b2.hashCode(), b3.hashCode());
    Assert.assertNotEquals(b3, null);
    Assert.assertNotEquals(b3, new HexBoard(11));
  }

}
