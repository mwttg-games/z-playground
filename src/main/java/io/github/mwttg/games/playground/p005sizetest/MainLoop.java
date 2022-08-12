package io.github.mwttg.games.playground.p005sizetest;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.draw.SpriteComponent;
import io.github.mwttg.games.platform.draw.SpriteSystem;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public class MainLoop {

  private final SpriteComponent component;
  private final Matrix4f model = new Matrix4f();
  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180();

  MainLoop() {
    final var textureId = Texture.create("./data/p005/level.png");
    final var planeData = MeshFactory.createSprite(32.0f, 18.0f);
    this.component = SpriteComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId);
  }

  void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      SpriteSystem.draw(component, model, view, projection);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
