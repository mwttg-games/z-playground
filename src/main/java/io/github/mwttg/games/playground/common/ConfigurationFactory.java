package io.github.mwttg.games.playground.common;

import io.github.mwttg.games.opengl.basic.utilities.gamewindow.OpenGlConfiguration;
import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.PlayerConfiguration;
import io.github.mwttg.games.platform.ecs.SceneConfiguration;

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
        0.25f,
        0.25f,
        0.25f,
        0.1f,
        100.0f);
  }

  public static GameState createGameState() {
    final var playerConfiguration = createPlayerConfiguration();
    final var sceneConfiguration = createSceneConfiguration();
    return new GameState(playerConfiguration, sceneConfiguration);

  }
  private static PlayerConfiguration createPlayerConfiguration() {
    return new PlayerConfiguration(8.0f, 10.0f);
  }

  private static SceneConfiguration createSceneConfiguration() {
    return new SceneConfiguration(10.0f, 20.0f);
  }
}
