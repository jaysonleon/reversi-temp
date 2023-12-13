import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hw05.model.BasicReversi;
import hw05.model.Player;
import hw05.model.ReversiModel;
import hw06.model.ReadonlyReversiModel;
import hw06.strategy.CaptureMax;
import hw06.strategy.CompleteStrategy;
import hw06.view.ReversiView;
import hw07.controller.NewController;
import hw07.model.HumanReversiPlayer;
import hw07.model.MachineReversiPlayer;
import hw07.model.ReversiPlayer;

/**
 * Test the new completed controller for a Reversi game.
 */
public class TestNewController {
  private ReversiModel m;
  private ReversiPlayer p1;
  private ReversiPlayer p2;
  private MockView v;

  @Before
  public void setUp() {
    CompleteStrategy strat = new CompleteStrategy(new CaptureMax());
    StringBuilder log1 = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    m = new MockModel(new BasicReversi(), log1);
    p1 = new HumanReversiPlayer(Player.BLACK);
    p2 = new HumanReversiPlayer(Player.WHITE);

    v = new MockView(m, log2);
  }

  @Test
  public void testPassingTurnsEndsGame() {
    StringBuilder log = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    mock.startGame();
    mock.pass();
    mock.pass();

    Assert.assertTrue(m.isGameOver());

  }

  @Test
  public void testPassDoesntEndGame() {
    StringBuilder log = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    HumanReversiPlayer p1 = new HumanReversiPlayer(Player.BLACK);
    HumanReversiPlayer p2 = new HumanReversiPlayer(Player.WHITE);
    ReversiView v = new MockView(mock, log);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p2, v);

    mock.startGame();
    c1.pass();
    Assert.assertFalse(mock.isGameOver());
    c2.pass();
    Assert.assertTrue(mock.isGameOver());
  }

  @Test
  public void testMoveHuman1() {
    StringBuilder log = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    HumanReversiPlayer p1 = new HumanReversiPlayer(Player.BLACK);
    NewController c = new NewController(mock, p1, v);
    mock.startGame();
    c.makeMove(3, 6);
    Assert.assertEquals(Player.WHITE, mock.getTurn());

  }

  @Test
  public void testHumanMoveNotTheirTurn() {
    StringBuilder log = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    ReversiView v = new MockView(mock, log);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p2, v);
    mock.startGame();
    c2.placePiece();
    Assert.assertEquals(Player.BLACK, mock.getTurn());
    Assert.assertTrue(log.toString().contains("showMessage(It is not your turn)"));

    c1.pass();
    Assert.assertEquals(Player.WHITE, mock.getTurn());
    log.setLength(0);

    c1.placePiece();
    Assert.assertTrue(log.toString().contains("showMessage(It is not your turn)"));
  }

  @Test
  public void testPassTurn() {
    StringBuilder log = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    ReversiView v = new MockView(mock, log);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p2, v);

    mock.startGame();
    c1.pass();
    c2.pass();
    Assert.assertTrue(mock.isGameOver());
    Assert.assertEquals(Player.WHITE, mock.getTurn());
    Assert.assertTrue(log.toString().contains("showMessage(It's your turn)"));
  }

  @Test
  public void testPass2X() {
    StringBuilder log = new StringBuilder();
    ReversiModel model = new MockModel(new BasicReversi(),log);
    ReadonlyReversiModel n = model;
    model.startGame();

    ReversiView gui = new MockView(n, log);
    ReversiView gui2 = new MockView(n, log);

    p1 = new HumanReversiPlayer(Player.BLACK);
    p2 = new HumanReversiPlayer(Player.WHITE);

    NewController c1 = new NewController(model, p1, gui);
    NewController c2 = new NewController(model, p2, gui2);

    c1.pass();
    c2.pass();
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testHumanUpdateView() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    ReversiView v = new MockView(mock, log);
    ReversiView v2 = new MockView(mock, log2);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p2, v2);
    mock.startGame();
    c1.updateView();

    // had to hardcode move here since we had an issue with our mockView.
    //  because convertCoords returns null
    c1.makeMove(3, 6);
    Assert.assertTrue(log.toString().contains("playMove"));
  }

  @Test
  public void testAIUpdateView() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    ReversiView v = new MockView(mock, log);
    ReversiView v2 = new MockView(mock, log2);
    MachineReversiPlayer p3 = new MachineReversiPlayer(mock,
            new CompleteStrategy(new CaptureMax()), Player.WHITE);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p3, v2);
    mock.startGame();
    c1.pass();
    c2.updateView();
    Assert.assertTrue(log.toString().contains("playMove(6, 3)"));
  }

  @Test
  public void testViewWithModel() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuilder log3 = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    ReversiView v = new MockView(mock, log2);
    ReversiView v2 = new MockView(mock, log3);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p2, v2);
    mock.startGame();
    c1.passTurn();
    Assert.assertTrue(log.toString().contains("pass()"));
    c1.placePiece();
    Assert.assertTrue(log2.toString().contains("showMessage(It is not your turn)"));
    c2.placePiece();
    Assert.assertTrue(log3.toString().contains("showMessage(Illegal move)"));
    c2.selectHex();

    Assert.assertTrue(log3.toString().contains("selectHex()"));
    c1.selectHex();
    Assert.assertTrue(log2.toString().contains("showMessage(It is not your turn)"));
    c2.pass();
    c2.updateView();
    Assert.assertTrue(log3.toString().contains("showMessage(It's a tie!)"));
  }

  @Test
  public void testBlackWinEndingMessage() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuilder log3 = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    ReversiView v = new MockView(mock, log2);
    ReversiView v2 = new MockView(mock, log3);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p2, v2);
    mock.startGame();
    c1.makeMove(3, 6);
    c2.pass();
    c1.pass();
    c1.updateView();
    Assert.assertTrue(log2.toString().contains("showMessage(The winner is p1(black"));
  }

  @Test
  public void testWhiteWinEndingMessage() {
    StringBuilder log = new StringBuilder();
    StringBuilder log2 = new StringBuilder();
    StringBuilder log3 = new StringBuilder();
    ReversiModel mock = new MockModel(new BasicReversi(), log);
    ReversiView v = new MockView(mock, log2);
    ReversiView v2 = new MockView(mock, log3);
    NewController c1 = new NewController(mock, p1, v);
    NewController c2 = new NewController(mock, p2, v2);
    mock.startGame();
    c1.pass();
    c2.makeMove(4,4);
    c1.pass();
    c2.pass();
    c1.updateView();
    System.out.println(log2);
    Assert.assertTrue(log2.toString().contains("showMessage(The winner is p2(white"));
  }

}
