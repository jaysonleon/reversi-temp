import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hw05.model.BasicReversi;
import hw05.model.Player;
import hw05.model.ReversiModel;
import hw06.strategy.AvoidNextToCorner;
import hw06.strategy.CaptureMax;
import hw06.strategy.CombinedStrategy;
import hw06.strategy.CompleteStrategy;
import hw06.strategy.FallibleReversiStrategy;
import hw06.strategy.InfallibleReversiStrategy;
import hw06.strategy.MovetoCorner;
import hw06.model.ReadonlyReversiModel;
import hw06.view.ReversiGUI;
import hw06.view.ReversiView;
import hw07.controller.NewController;
import hw07.model.HumanReversiPlayer;
import hw07.model.MachineReversiPlayer;
import hw07.model.ReversiPlayer;
import hw08.strategy.ProvStratToOurStratAdapter;
import hw08.model.ModelToProvModelAdapter;
import hw08.view.ProvViewToOurViewAdapter;
import hw09.model.AbstractReversi;
import provider.model.ReadOnlyReversiModel;
import provider.strategy.AvoidHexNearCornersStrategy;
import provider.strategy.GreedyStrategy;
import provider.strategy.TakeCornerStrategy;
import provider.view.SimpleReversiView;

/**
 * Runs reversi.
 * Controls for game:
 * 1. Click on a hex to highlight the hex
 * 2. If the user:
 * - clicks on a highlighted hex again,
 * - clicks on a hex that is not highlighted, or
 * - clicks outside the game board
 * the hex will be deselected
 * 3. If the hex is highlighted, the user can press the 'enter' key to try to make a move:
 * - if the move is valid it will be made
 * - if the move is invalid, the user will be notified
 * 4. If the user has no available moves, they must pass their turn by pressing the 'p' key.
 * 5. Play until either a player cannot make any moves, the board is full,
 * if both players consecutively pass, or if a user quits.
 */
public final class Reversi {
  /**
   * Main method for running Reversi.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();
    for (String arg : args) {
      sb.append(arg).append(" ");
    }
    String str = sb.toString();
    Scanner scan = new Scanner(str);

    ReversiModel model = new BasicReversi();

    model = makeModel(scan, model);

    String p1Type = scan.next();
    p1Type = getPlayerType(p1Type);
    ReversiPlayer player1;
    player1 = createPlayer(p1Type, model, Player.BLACK, scan);

    String p2Type = scan.next();
    p2Type = getPlayerType(p2Type);

    ReversiPlayer player2;
    player2 = createPlayer(p2Type, model, Player.WHITE, scan);

    ReadonlyReversiModel n = model;
  //    ReadOnlyReversiModel adapter = new ModelToProvModelAdapter(model);
  //    SimpleReversiView view = new SimpleReversiView(adapter);
  //    ReversiView v1 = new ReversiGUI(n);
  //    ReversiView v2 = new ProvViewToOurViewAdapter(adapter, view);

    ReversiView v1 = new ReversiGUI(n);
    ReversiView v2 = new ReversiGUI(n);


    NewController c1 = new NewController(model, player1, v1);
    NewController c2 = new NewController(model, player2, v2);
    model.startGame();
  }

  /**
   * Creates a model based on the size and board type specified by the user.
   *
   * @param scan  the scanner
   * @param model the model
   * @return a reversiModel object to represent the model
   */
  private static ReversiModel makeModel(Scanner scan, ReversiModel model) {
    // determine the type of board to use (square or hex)
    String hexOrSquare = scan.next();
    boolean isHex;
    if (hexOrSquare.equalsIgnoreCase("false")
            || hexOrSquare.equalsIgnoreCase("f")) {
      isHex = false;
    } else {
      isHex = true;
    }
    // determine the size of the board
    if (scan.hasNextInt()) {
      int size = scan.nextInt();
      if (size < 3) {
        System.out.println("Invalid size");
      } else {
        model = new AbstractReversi(size, isHex);
      }
    } else {
      if (isHex) {
        model = new AbstractReversi(6, isHex);
        scan.next();
      } else {
        model = new AbstractReversi(8, isHex);
        scan.next();

      }

    }
    return model;
  }

  /**
   * Gets the type of player specified by the user.
   *
   * @param type the type of player
   * @return a string to represent the type of player
   */
  private static String getPlayerType(String type) {
    String in = type;
    if (!in.equalsIgnoreCase("human")
            && !in.equalsIgnoreCase("machine")
            && !in.equalsIgnoreCase("h")
            && !in.equalsIgnoreCase("m")) {
      in = "Human";
    }
    return in;
  }

  /**
   * Creates a player based on the type of player specified by the user.
   *
   * @param type  the type of player
   * @param model the model
   * @param piece the piece of the player
   * @param scan  the scanner
   * @return a reversiPlayer object to represent the player
   */
  private static ReversiPlayer createPlayer(String type, ReversiModel model, Player piece,
                                            Scanner scan) {
    if (type.equalsIgnoreCase("Human") || type.equalsIgnoreCase("H")) {
      return new HumanReversiPlayer(piece);
    } else {
      return new MachineReversiPlayer(model, createStrategy(scan, model), piece);
    }
  }

  /**
   * Creates a strategy based on the type of strategy specified by the user.
   *
   * @param scan the scanner
   * @return a strategy object to represent the strategy
   */
  private static InfallibleReversiStrategy createStrategy(Scanner scan,
                                                          ReadonlyReversiModel model) {
    String stratType = scan.next();
    if (stratType.equalsIgnoreCase("single")
            || stratType.equalsIgnoreCase("s")) {
      return getSingleStrategy(scan, model);
    } else {
      return new CompleteStrategy(getMultiStrategy(scan, model));
    }
  }

  /**
   * Gets a single strategy for the machine player to use,
   * based on the type of strategy specified by the user.
   *
   * @param scan the scanner
   * @return a strategy object to represent the strategy
   */
  private static InfallibleReversiStrategy getSingleStrategy(Scanner scan,
                                                             ReadonlyReversiModel model) {
    String strat = scan.next();
    InfallibleReversiStrategy strategy;

    switch (strat.toLowerCase()) {
      case "avoidcorners":
        strategy = new CompleteStrategy(new AvoidNextToCorner());
        break;
      case "capturecorners":
        strategy = new CompleteStrategy(new MovetoCorner());
        break;
      case "strategy1":
        strategy = new CompleteStrategy(new ProvStratToOurStratAdapter(new GreedyStrategy()));
        break;
      case "strategy2":
        strategy = new CompleteStrategy(new ProvStratToOurStratAdapter(
                new AvoidHexNearCornersStrategy()));
        break;
      case "strategy3":
        strategy = new CompleteStrategy(new ProvStratToOurStratAdapter(new TakeCornerStrategy()));
        break;
      default:
        strategy = new CompleteStrategy(new CaptureMax());
        break;
    }
    return strategy;
  }

  /**
   * Gets multiple strategies for the machine player to use,
   * based on the strategies specified by the user.
   *
   * @param scan the scanner
   * @return a strategy object to represent the strategy
   */
  private static FallibleReversiStrategy getMultiStrategy(Scanner scan,
                                                          ReadonlyReversiModel model) {
    int numStrats = scan.nextInt();

    List<FallibleReversiStrategy> strats = new ArrayList<>();

    String in;
    for (int i = 0; i < 3; i++) {
      if (i < numStrats) {
        in = scan.next();
        System.out.println(in);
      } else {
        in = "break";
        System.out.println(in);
      }
      switch (in.toLowerCase()) {
        case "avoidcorners":
          strats.add(new AvoidNextToCorner());
          break;
        case "capturecorners":
          strats.add(new MovetoCorner());
          break;
        case "strategy1":
          strats.add(new ProvStratToOurStratAdapter(new GreedyStrategy()));
          break;
        case "strategy2":
          strats.add(new ProvStratToOurStratAdapter(new AvoidHexNearCornersStrategy()));
          break;
        case "strategy3":
          strats.add(new ProvStratToOurStratAdapter(new TakeCornerStrategy()));
          break;
        case "break":
          break;
        default:
          strats.add(new CaptureMax());
          break;
      }
    }
    if (strats.size() > 2) {
      return new CombinedStrategy(strats.get(0),
              new CombinedStrategy(strats.get(1), strats.get(2)));
    } else {
      return new CombinedStrategy(strats.get(0), strats.get(1));
    }
  }
}