package io.github.mwttg.games.playground.p004screen;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.draw.AnimatedSprite;
import io.github.mwttg.games.platform.draw.Drawable;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

class MainLoop {

  private final Drawable component;
  private final Matrix4f model = new Matrix4f();
  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180();

  MainLoop() {
    final var textureId = Texture.create("./data/p004/screen-320x180.png");
    final var planeData = MeshFactory.createAnimatedSprite(2, 32.0f, 18.0f);
    final var timings = List.of(1000, 1000);
    this.component = AnimatedSprite.create(planeData.geometry(), planeData.textureCoordinates(), textureId, timings);
  }

  void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      component.draw(model, view, projection);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
