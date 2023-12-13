import org.junit.Assert;
import org.junit.Test;

import hw05.model.BasicReversi;
import hw05.model.Hex;
import hw05.model.HexBoard;
import hw05.model.ReversiModel;
import hw09.model.Board;

/**
 * Tests for the Board class.
 */
public class TestBoard {

  @Test
  public void testCopyBoard() {
    ReversiModel m = new BasicReversi();
    m.startGame();
    m.playMove(3,6);
    Board b1 = m.getBoard();
    Board b2 = m.copyBoard();
    Assert.assertEquals(b1, b2);
    Assert.assertEquals(b1.hashCode(), b2.hashCode());
  }

  @Test
  public void testBoardGetHexAt() {
    HexBoard b = new HexBoard(11);
    Assert.assertNull(b.getTileAt(0,0));
    Assert.assertNull(b.getTileAt(-1, 3));
    Assert.assertNull(b.getTileAt(16, 3));
  }

  @Test
  public void testBoardSetHexAt() {
    HexBoard b = new HexBoard(11);
    Assert.assertThrows(IllegalArgumentException.class, () -> b.setTileAt(-1, 3));
    Assert.assertThrows(IllegalArgumentException.class, () -> b.setTileAt(16, 3));
  }

  @Test
  public void testBoardEquals() {
    HexBoard b1 = new HexBoard(11);
    HexBoard b2 = new HexBoard(11);
    Assert.assertEquals(b1.hashCode(), b1.hashCode());
    Assert.assertEquals(b1, b2);
    Assert.assertEquals(b1.hashCode(), b2.hashCode());
    b1.setTileAt(3, 6);
    Assert.assertNotEquals(b1, b2);
    Assert.assertNotEquals(b1.hashCode(), b2.hashCode());
    HexBoard b3 = new HexBoard(12);
    Assert.assertNotEquals(b1, b3);
    Assert.assertNotEquals(b1.hashCode(), b3.hashCode());
    Assert.assertNotEquals(b1, null);
    Assert.assertNotEquals(b1, new Hex(4, 5));
  }
}
