package io.github.mwttg.games.playground.p3animation;

import io.github.mwttg.games.opengl.basic.utilities.cleanup.CleanUp;
import io.github.mwttg.games.opengl.basic.utilities.gamewindow.GameWindow;
import io.github.mwttg.games.playground.common.ConfigurationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Playground3 {

  private static final Logger LOG = LoggerFactory.getLogger(Playground3.class);

  public static void main(String[] args) {
    final var config = ConfigurationFactory.createOpenGlConfiguration();
    LOG.info(config.prettyPrint());
    final var id = GameWindow.create(config);

    new MainLoop().loop(id);

    CleanUp.purge();
  }
}
