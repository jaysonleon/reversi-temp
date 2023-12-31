import org.junit.Assert;
import org.junit.Test;

import model.model.Hex;
import model.player.Player;
import model.model.Cell;
import model.model.Tile;

/**
 * Tests for Hex & Player classes.
 */
public class TestTile {

  Tile c = new Cell(3, 4);

  @Test
  public void testHexConstructor() {
    Tile tile = new Hex(3, 4);
    Assert.assertEquals(3, tile.getQ());
    Assert.assertEquals(4, tile.getR());
    Assert.assertEquals(-7, tile.getS());
  }

  @Test
  public void testHexEquals() {
    Tile tile1 = new Hex(3, 4);
    Tile tile2 = new Hex(3, 4);
    Tile tile3 = new Hex(3, 5);
    Tile tile4 = new Hex(4, 4);
    Assert.assertEquals(tile1, tile2);
    Assert.assertNotEquals(tile1, tile3);
    Assert.assertNotEquals(tile1, tile4);
    tile1.changePlayer(Player.BLACK);
    tile2.changePlayer(Player.WHITE);
    Assert.assertNotEquals(tile1, tile2);
    Assert.assertEquals(tile1, tile1);
    Assert.assertNotEquals(null, tile1);
  }

  @Test
  public void testHexHashCode() {
    Tile tile1 = new Hex(3, 4);
    Tile tile2 = new Hex(3, 4);
    Tile tile3 = new Hex(3, 5);
    Tile tile4 = new Hex(4, 4);
    Assert.assertEquals(tile1.hashCode(), tile2.hashCode());
    Assert.assertNotEquals(tile1.hashCode(), tile3.hashCode());
    Assert.assertNotEquals(tile1.hashCode(), tile4.hashCode());
  }

  @Test
  public void testHexChangePlayer() {
    Tile tile = new Hex(3, 4);
    Assert.assertEquals(Player.EMPTY, tile.getPlayerAt());
    tile.changePlayer(Player.BLACK);
    Assert.assertEquals(Player.BLACK, tile.getPlayerAt());
    tile.changePlayer(Player.WHITE);
    Assert.assertEquals(Player.WHITE, tile.getPlayerAt());
  }

  @Test
  public void testHexToString() {
    Tile tile = new Hex(3, 4);
    Assert.assertEquals("_", tile.toString());
    tile.changePlayer(Player.BLACK);
    Assert.assertEquals("X", tile.toString());
    tile.changePlayer(Player.WHITE);
    Assert.assertEquals("O", tile.toString());
  }

  @Test
  public void testPlayerToString() {
    Player p = Player.BLACK;
    Assert.assertEquals("X", p.toString());
    p = Player.WHITE;
    Assert.assertEquals("O", p.toString());
    p = Player.EMPTY;
    Assert.assertEquals("_", p.toString());
  }

  @Test
  public void testGetters() {
    Tile tile = new Hex(3, 4);
    Assert.assertEquals(3, tile.getQ());
    Assert.assertEquals(4, tile.getR());
    Assert.assertEquals(-7, tile.getS());
    Assert.assertEquals(0, tile.getQ() + tile.getR() + tile.getS());
  }

  @Test
  public void testPlacePlayerEmpty() {
    Tile tile = new Hex(3, 4);
    Assert.assertEquals(Player.EMPTY, tile.getPlayerAt());
    tile.placePlayerEmpty(Player.BLACK);
    Assert.assertEquals(Player.BLACK, tile.getPlayerAt());
    Assert.assertThrows(IllegalStateException.class, () -> tile.placePlayerEmpty(Player.WHITE));
    Assert.assertEquals(Player.BLACK, tile.getPlayerAt());
  }

  @Test
  public void testHasPlayer() {
    Tile tile = new Hex(3, 4);
    Assert.assertFalse(tile.hasPlayer());
    tile.placePlayerEmpty(Player.BLACK);
    Assert.assertTrue(tile.hasPlayer());
  }

  @Test
  public void testCellConstructor() {
    Assert.assertEquals(3, c.getQ());
    Assert.assertEquals(4, c.getR());
    Assert.assertThrows(UnsupportedOperationException.class, c::getS);
  }

  @Test
  public void testCellChangePlayer() {
    Assert.assertFalse(c.hasPlayer());
    Assert.assertEquals(Player.EMPTY, c.getPlayerAt());
    c.changePlayer(Player.WHITE);
    Assert.assertTrue(c.hasPlayer());
    Assert.assertEquals(Player.WHITE, c.getPlayerAt());
    c.changePlayer(Player.BLACK);
    Assert.assertTrue(c.hasPlayer());
    Assert.assertEquals(Player.BLACK, c.getPlayerAt());
  }

  @Test
  public void testCellEquals() {
    Tile c1 = new Cell(3, 4);
    Tile c2 = new Cell(3, 4);
    Tile c3 = new Cell(3, 5);
    Tile c4 = new Cell(4, 4);
    Tile c5 = new Cell(3,4);
    Assert.assertEquals(c1, c2);
    Assert.assertEquals(c1.hashCode(), c2.hashCode());
    Assert.assertNotEquals(c1, c3);
    Assert.assertNotEquals(c1.hashCode(), c3.hashCode());
    Assert.assertNotEquals(c1, c4);
    c1.changePlayer(Player.BLACK);
    c5.changePlayer(Player.WHITE);
    Assert.assertNotEquals(c1, c5);
    Assert.assertNotEquals(c1.hashCode(), c5.hashCode());
    c5.changePlayer(Player.BLACK);
    Assert.assertEquals(c1, c5);
    Assert.assertEquals(c1.hashCode(), c5.hashCode());
    Assert.assertNotEquals(new Hex(3,4), c1);
  }

  @Test
  public void testCellToString() {
    Assert.assertEquals("_", c.toString());
    c.changePlayer(Player.BLACK);
    Assert.assertEquals("X", c.toString());
    c.changePlayer(Player.WHITE);
    Assert.assertEquals("O", c.toString());
  }
}

