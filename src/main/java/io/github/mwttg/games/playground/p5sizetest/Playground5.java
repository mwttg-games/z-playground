package io.github.mwttg.games.playground.p5sizetest;

import io.github.mwttg.games.opengl.basic.utilities.cleanup.CleanUp;
import io.github.mwttg.games.opengl.basic.utilities.gamewindow.GameWindow;
import io.github.mwttg.games.playground.common.ConfigurationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Playground5 {
  private static final Logger LOG = LoggerFactory.getLogger(Playground5.class);

  public static void main(String[] args) {
    final var config = ConfigurationFactory.createOpenGlConfiguration();
    LOG.info(config.prettyPrint());
    final var id = GameWindow.create(config);

    new MainLoop().loop(id);

    CleanUp.purge();
  }
}
