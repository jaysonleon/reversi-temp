package hw09.textView;

import hw05.model.ReversiModel;
import hw05.view.TextualView;

/**
 * Represents a string representation of the Square Reversi game board.
 */
public class SquareTextView implements TextualView {
  private final ReversiModel model;

  /**
   * Constructs a SquareTextView object with the given model.
   *
   * @param model the model
   */
  public SquareTextView(ReversiModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int q = 0; q < model.getWidth(); q++) {
      for (int r = 0; r < model.getHeight(); r++) {
        result.append(model.getTileAt(q, r).toString()).append(" ");
      }

      result.append("\n");
    }

    return result.toString().trim();
  }
}
