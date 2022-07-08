package io.github.mwttg.games.playground.p6controls;

import io.github.mwttg.games.platform.ecs.component.movement.PlayerActionComponent;
import io.github.mwttg.games.platform.ecs.system.movement.PlayerActionSystem;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLoop {

  private static final Logger LOG = LoggerFactory.getLogger(MainLoop.class);

  private final PlayerActionComponent component;

  MainLoop() {
    component = new PlayerActionComponent();
  }

  void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      PlayerActionSystem.update(gameWindowId, component);
      LOG.info("Left = '{}' - Right = '{}' - Jump = '{}'", component.isMoveLeft() ? 1 : 0, component.isMoveRight() ? 1 : 0,
          component.isJumpUp() ? 1 : 0);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }

}
