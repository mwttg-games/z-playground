package io.github.mwttg.games.playground.p002texture;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.PlaneFactory;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.platform.draw.Sprite;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

final class MainLoop {

  private final Drawable level;
  private final Matrix4f model = new Matrix4f();
  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get640x480();

  public MainLoop() {
    final var textureId = Texture.create("./data/p002/level.png");
    final var planeData = PlaneFactory.create(20, 15);
    this.level = Sprite.create(planeData.geometry(), planeData.textureCoordinates(), textureId);

  }

  void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      level.draw(model, view, projection);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
