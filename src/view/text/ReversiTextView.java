package view.text;

import model.model.ReversiModel;

/**
 * Represents a string representation of the Reversi game board.
 */
public class ReversiTextView implements TextualView {
  private final ReversiModel model;

  public ReversiTextView(ReversiModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    int halfHeight = model.getHeight() / 2;

    for (int r = 0; r < model.getHeight(); r++) {
      // Calculate the number of spaces for indentation based on the row
      int spaces = Math.abs(r - halfHeight);

      // Add the required number of spaces for indentation
      result.append(" ".repeat(Math.max(0, spaces)));

      for (int q = 0; q < model.getWidth(); q++) {
        if (model.getTileAt(q, r) == null) {
          if (r > halfHeight) {
            result.append(" ");
          }
        } else if (model.getTileAt(q, r).hasPlayer()) {
          result.append(model.getTileAt(q, r).toString()).append(" ");
        } else {
          result.append(model.getTileAt(q, r).toString()).append(" ");
        }
      }

      result.append("\n");
    }

    return result.toString();
  }


}
