package io.github.mwttg.games.playground.p006movement;

import io.github.mwttg.games.platform.ecs.entity.debug.PlayerEntity;
import io.github.mwttg.games.playground.common.ConfigurationFactory;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MainLoop {

  private static final Logger LOG = LoggerFactory.getLogger(MainLoop.class);

  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180();
  private final PlayerEntity playerEntity;

  private final long windowId;

  MainLoop(final long windowId) {
    this.windowId = windowId;

    final var playerConfiguration = ConfigurationFactory.createPlayerConfiguration();
    final var sceneConfiguration = ConfigurationFactory.createSceneConfiguration();
    final var textureFilename = "./data/p006/player.png";
    this.playerEntity = PlayerEntity.create(windowId, textureFilename, playerConfiguration, sceneConfiguration);
  }

  void loop() {
    playerEntity.timer().reset();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      playerEntity.updateLogic();
      playerEntity.updateRender(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }
}
