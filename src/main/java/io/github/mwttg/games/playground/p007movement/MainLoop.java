package io.github.mwttg.games.playground.p007movement;

import io.github.mwttg.games.platform.ecs.entity.SceneEntity;
import io.github.mwttg.games.platform.ecs.entity.debug.PlayerEntity;
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
  private final PlayerEntity playerEntity;

  private final Timer timer;

  public MainLoop(long windowId) {
    this.windowId = windowId;
    this.sceneEntity = createScene();
    this.playerEntity = createPlayer();
    this.timer = new Timer();
  }

  private SceneEntity createScene() {
    final var gridFilename = "./data/p007/level-solid-grid.json";
    final var textureFilename = "./data/p007/level.png";
    return SceneEntity.create(gridFilename, textureFilename, 20, 10);
  }

  private PlayerEntity createPlayer() {
    final var gameState = ConfigurationFactory.createGameState();
    final var textureFilename = "./data/p006/player.png";
    return PlayerEntity.create(windowId, textureFilename, 5.0f, 2.0f, gameState);
  }

  void loop() {
    var deltaTime = timer.getDeltaTime();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      deltaTime = timer.getDeltaTime();

      // physics
      playerEntity.updateLogic(deltaTime, sceneEntity.solidGridComponent());

      // render
      sceneEntity.draw(view, projection);
      playerEntity.updateRender(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }
}
