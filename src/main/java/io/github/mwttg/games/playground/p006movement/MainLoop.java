package io.github.mwttg.games.playground.p006movement;

import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.movement.TileSize;
import io.github.mwttg.games.platform.ecs.entity.SceneEntity;
import io.github.mwttg.games.platform.ecs.entity.debug.PlayerEntity2;
import io.github.mwttg.games.platform.ecs.system.Timer;
import io.github.mwttg.games.playground.common.ConfigurationFactory;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public class MainLoop {

  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180New();
  private final long windowId;

  private final SceneEntity sceneEntity;
  private final PlayerEntity2 playerEntity;
  private final GameState gameState;
  private final Timer timer;

  public MainLoop(long windowId) {
    this.windowId = windowId;
    this.sceneEntity = createScene();
    this.playerEntity = createPlayer();
    this.gameState = ConfigurationFactory.createGameState();
    this.timer = new Timer();
  }

  private SceneEntity createScene() {
    final var gridFilename = "./data/p007/level-solid-grid.json";
    final var textureFilename = "./data/p007/level.png";
    return SceneEntity.create(gridFilename, textureFilename, 20, 10);
  }

  private PlayerEntity2 createPlayer() {
    final var textureFilename = "./data/p006/player.png";
    final var tileSize = new TileSize(1.0f, 1.0f);
    return PlayerEntity2.create(windowId, textureFilename, tileSize, 5.0f, 8.0f);
  }

  void loop() {
    timer.reset();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      // timing
      final var deltaTime = timer.getDeltaTime();

      // physics
      playerEntity.update(sceneEntity.solidGridComponent(), deltaTime, gameState);

      // render
      sceneEntity.draw(view, projection);
      playerEntity.draw(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }
}
