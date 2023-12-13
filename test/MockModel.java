import hw05.model.HexBoard;
import hw05.model.Player;
import hw05.model.ReversiModel;
import hw07.model.ModelFeatures;
import hw09.model.Board;
import hw09.model.Tile;

/**
 * Represents a mock of the ReversiModel. Used for testing validity of strategies.
 */
public class MockModel implements ReversiModel {

  private final ReversiModel model;
  private StringBuilder log;

  /**
   * Constructs a ModelMock.
   * @param model the model to mock
   * @param log the log of calls to the model
   */
  public MockModel(ReversiModel model, StringBuilder log) {
    super();
    this.model = model;
    this.log = log;
  }

  @Override
  public void addFeatures(ModelFeatures features) {
    log.append("addFeatures()" + "\n");
    model.addFeatures(features);
  }

  @Override
  public void notifyPlayerTurn(Player p) {
    log.append("notifyPlayerTurn(" + p + ")" + "\n");
    model.notifyPlayerTurn(p);
  }

  @Override
  public int getHeight() {
    log.append("getHeight()\n");
    return model.getHeight();
  }

  @Override
  public int getWidth() {
    log.append("getWidth()\n");
    return model.getWidth();
  }

  @Override
  public int getSideLen() {
    log.append("getSideLen()\n");
    return model.getSideLen();
  }

  @Override
  public Status getStatus() {
    log.append("getStatus()\n");
    return model.getStatus();
  }

  @Override
  public Board getBoard() {
    log.append("getBoard()\n");
    return model.getBoard();
  }

  @Override
  public int getScore(Player player) {
    log.append("getScore(" + player + ")" + "\n");
    return model.getScore(player);
  }

  @Override
  public Tile getTileAt(int q, int r) {
    log.append("getHexAt(" + q + ", " + r + ")" + "\n");
    return model.getTileAt(q, r);
  }

  @Override
  public Player nextTurn() {
    log.append("nextTurn()\n");
    return model.nextTurn();
  }

  @Override
  public Player getTurn() {
    log.append("getTurn()\n");
    return model.getTurn();
  }

  @Override
  public Player determineWinner() {
    log.append("determineWinner()\n");
    return model.determineWinner();
  }

  @Override
  public Board copyBoard() {
    log.append("copyBoard()\n");
    return model.copyBoard();
  }

  @Override
  public boolean isValidMove(int q, int r, Player player) {
    log.append("isValidMove(" + q + ", " + r + ", " + player + ")" + "\n");
    return model.isValidMove(q, r, player);
  }

  @Override
  public int moveScore(int q, int r) {
    log.append("moveScore(" + q + ", " + r + ")" + "\n");
    return model.moveScore(q, r);
  }

  @Override
  public boolean hasValidMoves(Player player) {
    log.append("hasValidMoves(" + player + ")" + "\n");
    return model.hasValidMoves(player);
  }

  @Override
  public void startGame() {
    model.startGame();
  }

  @Override
  public void playMove(int q, int r) {
    log.append("playMove(" + q + ", " + r + ")" + "\n");
    model.playMove(q, r);
  }

  @Override
  public void pass() {
    log.append("pass()" + "\n");
    model.pass();
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }
}
