package io.github.mwttg.games.playground.common;

import io.github.mwttg.games.opengl.basic.utilities.gamewindow.OpenGlConfiguration;

public final class ConfigurationFactory {

  private ConfigurationFactory() {
  }

  public static OpenGlConfiguration createOpenGlConfiguration() {
    return new OpenGlConfiguration(
        4,
        1,
        "playground",
        1920,
        1080,
        true,
        true,
        false,
        0.4f,
        0.4f,
        0.4f,
        0.1f,
        100.0f);
  }
}
