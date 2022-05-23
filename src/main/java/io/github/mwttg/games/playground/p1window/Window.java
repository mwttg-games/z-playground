package io.github.mwttg.games.playground.p1window;

import io.github.mwttg.games.opengl.basic.utilities.cleanup.CleanUp;
import io.github.mwttg.games.opengl.basic.utilities.gamewindow.GameWindow;
import io.github.mwttg.games.playground.common.ConfigurationFactory;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window {

  private static final Logger LOG = LoggerFactory.getLogger(Window.class);

  public static void main(String[] args) {
    final var config = ConfigurationFactory.createOpenGlConfiguration();
    LOG.info(config.prettyPrint());
    final var id = GameWindow.create(config);

    loop(id);

    CleanUp.purge();
  }

  private static void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);
      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
