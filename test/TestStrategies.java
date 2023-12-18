import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.model.HexReversi;
import model.player.Player;
import model.model.ReversiModel;
import strategy.AvoidNextToCorner;
import strategy.CaptureMax;
import strategy.CombinedStrategy;
import strategy.CompleteStrategy;
import model.model.ReadonlyReversiModel;
import strategy.MovetoCorner;

/**
 * Tests for strategies.
 */
public class TestStrategies {
  ReversiModel m = new HexReversi();

  @Test
  public void testChooseBestMoveAtStart() {
    m.startGame();
    ReadonlyReversiModel n = m;
    CompleteStrategy strat = new CompleteStrategy(new CaptureMax());

    Assert.assertEquals(new Point(6,3), strat.chooseMove(n, Player.BLACK));
    m.playMove(6,3);
    Assert.assertEquals(5, m.getScore(Player.BLACK));
    Assert.assertEquals(new Point(7,2), strat.chooseMove(n, Player.WHITE));
  }

  /**
   * Test to transcribe calls to model from strategies.
   */
  @Test
  public void testChooseBestMoveTranscript() {
    StringBuilder log = new StringBuilder();
    MockModel mock = new MockModel(m, log);
    m.startGame();
    CompleteStrategy strat = new CompleteStrategy(new CaptureMax());

    // points for possible moves for black
    List<String> blackMoves = new ArrayList<>();
    blackMoves.add("moveScore(6, 3)\n");
    blackMoves.add("moveScore(4, 4)\n");
    blackMoves.add("moveScore(7, 4)\n");
    blackMoves.add("moveScore(3, 6)\n");
    blackMoves.add("moveScore(6, 6)\n");
    blackMoves.add("moveScore(4, 7)\n");

    // assert the correct move is made for black
    Assert.assertEquals(new Point(6,3), strat.chooseMove(mock, Player.BLACK));
    // assert all possible moves are checked for black
    for (String s : blackMoves) {
      Assert.assertTrue(log.toString().contains(s));
    }
  }

  @Test
  public void testChooseBestMove() {
    m.startGame();
    ReadonlyReversiModel n = m;
    CompleteStrategy strat = new CompleteStrategy(new CaptureMax());

    Assert.assertEquals(new Point(6,3), strat.chooseMove(n, Player.BLACK));
    m.playMove(6,3);
    m.playMove((strat.chooseMove(n, Player.WHITE)).x, (strat.chooseMove(n, Player.WHITE)).y);
    Assert.assertEquals(new Point(4,4), strat.chooseMove(n, Player.BLACK));
    m.playMove(4,4);
    m.playMove((strat.chooseMove(n, Player.WHITE)).x, (strat.chooseMove(n, Player.WHITE)).y);
    Assert.assertEquals(new Point(4,7), strat.chooseMove(n, Player.BLACK));
  }

  @Test
  public void testChooseMoveNoMoves() {
    ReversiModel model = new HexReversi(3);
    model.startGame();
    CompleteStrategy strat = new CompleteStrategy(new CaptureMax());

    // play moves to fill entire board, no valid moves left for either player
    model.playMove(0,3);
    model.playMove(1,1);
    model.playMove(3,0);
    model.playMove(4,1);
    model.playMove(3,3);
    model.playMove(1,4);

    // assert no moves are available for either player
    Assert.assertThrows(IllegalStateException.class, () -> strat.chooseMove(model, Player.BLACK));
    model.pass();
    Assert.assertThrows(IllegalStateException.class, () -> strat.chooseMove(model, Player.WHITE));
    model.pass();
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testCombinedStrategies() {
    ReversiModel model = new HexReversi(3);
    ReadonlyReversiModel n = model;
    model.startGame();
    CombinedStrategy s = new CombinedStrategy(new MovetoCorner(),
            new CombinedStrategy(new AvoidNextToCorner(), new CaptureMax()));
    CompleteStrategy strat = new CompleteStrategy(s);

    Assert.assertEquals(new Point(3,0), strat.chooseMove(n, Player.BLACK));
    model.playMove(strat.chooseMove(n, Player.BLACK).x, strat.chooseMove(n, Player.BLACK).y);
    model.playMove(strat.chooseMove(n, Player.WHITE).x, strat.chooseMove(n, Player.WHITE).y);
    model.playMove(strat.chooseMove(n, Player.BLACK).x, strat.chooseMove(n, Player.BLACK).y);
    model.playMove(strat.chooseMove(n, Player.WHITE).x, strat.chooseMove(n, Player.WHITE).y);
    model.playMove(strat.chooseMove(n, Player.BLACK).x, strat.chooseMove(n, Player.BLACK).y);
    model.playMove(strat.chooseMove(n, Player.WHITE).x, strat.chooseMove(n, Player.WHITE).y);

    Assert.assertThrows(IllegalStateException.class, () -> strat.chooseMove(n, Player.BLACK));
    Assert.assertThrows(IllegalStateException.class, () -> strat.chooseMove(n, Player.WHITE));
  }

}
