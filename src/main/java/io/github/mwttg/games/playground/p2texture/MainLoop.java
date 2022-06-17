package io.github.mwttg.games.playground.p2texture;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteComponent;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteSystem;
import io.github.mwttg.games.playground.common.PlaneFactory;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

final class MainLoop {

  private final SpriteComponent level;
  private final Matrix4f model = new Matrix4f();
  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get640x480();

  public MainLoop() {
    final var textureId = Texture.create("./data/p2/level.png");
    final var planeData = PlaneFactory.create(20, 15);
    this.level = SpriteComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);

  }

  void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      SpriteSystem.draw(level, model, view, projection);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
