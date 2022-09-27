package io.github.mwttg.games.playground.p006movement;

import io.github.mwttg.games.platform.Timer;
import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.WorldEntity;
import io.github.mwttg.games.platform.world.SceneDefinition;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public class MainLoop {

  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180New();
  private final long windowId;

  private final WorldEntity worldEntity;
  private final PlayerEntity playerEntity;
  private final Timer timer;

  public MainLoop(long windowId) {
    this.windowId = windowId;
    this.worldEntity = createWorld();
    this.playerEntity = PlayerEntity.create(windowId, 9.0f, 8.0f, "./data/player/player.json");
    this.timer = new Timer();
  }

  void loop() {
    timer.reset();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      // timing
      final var deltaTime = timer.getDeltaTime();
      final var solidGrid = worldEntity.getCurrentScene().sensorComponent();

      // physics
      playerEntity.update(deltaTime, solidGrid);

      // render
      worldEntity.draw(view, projection);
      playerEntity.draw(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }

  private WorldEntity createWorld() {
    final var scene = SceneDefinition.create("./data/p006/world.json");
    final var world = Map.of(scene.sceneId(), scene);

    return new WorldEntity(scene.sceneId(), world);
  }
}
