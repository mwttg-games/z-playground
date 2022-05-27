package io.github.mwttg.games.playground.p2texture;

import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.component.DrawComponent;
import io.github.mwttg.games.platform.ecs.system.DrawSystem;
import io.github.mwttg.games.playground.common.PlaneFactory;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public final class MainLoop2 {

  private final DrawComponent level;
  private final Matrix4f model = new Matrix4f();
  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get640x480();

  public MainLoop2() {
    final var textureId = Texture.create("./data/p2/level.png");
    final var planeData = PlaneFactory.create(20, 15);
    this.level = DrawComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);

  }

  public void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      DrawSystem.draw(level, model, view, projection);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
