import org.junit.Assert;
import org.junit.Test;

import model.model.ReversiModel;
import model.model.AbstractReversi;

public class TestAbstractModel {
  ReversiModel hex = new AbstractReversi(6, true);
  ReversiModel sq = new AbstractReversi(8, false);

  @Test
  public void testConstructorThrowsCorrectly() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new AbstractReversi(2, true));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new AbstractReversi(2, false));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            new AbstractReversi(3, false));
  }

}
