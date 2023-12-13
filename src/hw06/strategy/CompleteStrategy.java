package hw06.strategy;

import java.awt.Point;
import java.util.Optional;

import hw05.model.Player;
import hw06.model.ReadonlyReversiModel;

/**
 * Represents a strategy for an AI player to choose the best available move, using one fallible
 *  strategy.
 */
public class CompleteStrategy implements InfallibleReversiStrategy {

  private final FallibleReversiStrategy strategy;

  /**
   * Constructs a CompleteStrategy.
   * @param strat the strategy to use
   */
  public CompleteStrategy(FallibleReversiStrategy strat) {
    this.strategy = strat;
  }

  @Override
  public Point chooseMove(ReadonlyReversiModel model, Player player) throws IllegalStateException {
    Optional<Point> ans = this.strategy.chooseMove(model, player);
    if (ans.isPresent()) {
      return ans.get();
    }
    throw new IllegalStateException("No valid moves");
  }
}
