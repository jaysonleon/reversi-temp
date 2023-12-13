import java.awt.geom.Point2D;

import hw06.controller.ViewFeatures;
import hw06.model.ReadonlyReversiModel;
import hw06.view.ReversiView;

/**
 * Represents a mock of the ReversiGUI class for testing.
 */
public class MockView implements ReversiView {

  ReadonlyReversiModel m;
  StringBuilder log;

  /**
   * Constructs a Reversi GUI.
   *
   * @param model the model for the GUI to display
   */
  public MockView(ReadonlyReversiModel model, StringBuilder log) {
    super();
    this.m = model;
    this.log = log;
  }

  @Override
  public void addFeatures(ViewFeatures viewFeatures) {
    log.append("addFeatures(" + viewFeatures + ")" + "\n");
  }

  @Override
  public void selectHex() {
    log.append("selectHex()" + "\n");
  }

  @Override
  public Point2D convertCoords() {
    log.append("convertCoords()" + "\n");
    return new Point2D.Double(5, 5);
  }

  @Override
  public void updateView() {
    log.append("updateView()" + "\n");
  }

  @Override
  public void showMessage(String message) {
    log.append("showMessage(" + message + ")" + "\n");
  }
}
