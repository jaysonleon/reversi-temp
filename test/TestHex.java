import org.junit.Assert;
import org.junit.Test;

import hw05.model.Hex;
import hw05.model.Player;
import hw09.model.Tile;

/**
 * Tests for Hex & Player classes.
 */
public class TestHex {

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
}

