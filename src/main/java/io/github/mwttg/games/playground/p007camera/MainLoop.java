package io.github.mwttg.games.playground.p007camera;

import io.github.mwttg.games.platform.Timer;
import io.github.mwttg.games.platform.camera.ProjectionMatrix;
import io.github.mwttg.games.platform.camera.ScrollViewMatrix;
import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.SceneEntity;
import io.github.mwttg.games.playground.common.ViewMatrix;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public class MainLoop {

  private final Matrix4f view1 = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get();
  private final long windowId;

  private final SceneEntity sceneEntity;
  private final PlayerEntity playerEntity;
  private final Timer timer;

  public MainLoop(long windowId) {
    this.windowId = windowId;
    this.sceneEntity = createScene();
    this.playerEntity = PlayerEntity.create(windowId, 9.0f, 8.0f, "./data/player/player.json");
    this.timer = new Timer();
  }

  void loop() {
    timer.reset();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      // timing
      final var deltaTime = timer.getDeltaTime();
      final var solidGrid = sceneEntity.sensorComponent();

      // physics
      playerEntity.update(deltaTime, solidGrid);

      // render
      final var view = ScrollViewMatrix.get(playerEntity.transform());
      sceneEntity.draw(view, projection);
      playerEntity.draw(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }

  private SceneEntity createScene() {
    final var gridFilename = "./data/p007/solid-grid.json";
    final var textureFilename = "./data/p007/level.png";
    return SceneEntity.create(gridFilename, textureFilename, 60, 30);
  }
}
