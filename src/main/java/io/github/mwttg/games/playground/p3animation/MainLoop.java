package io.github.mwttg.games.playground.p3animation;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.component.SpriteAnimationComponent;
import io.github.mwttg.games.platform.ecs.system.SpriteAnimationSystem;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

final class MainLoop {

  private final SpriteAnimationComponent component;
  private final Matrix4f model = new Matrix4f().translate(10, 4, 0);
  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180();

  MainLoop() {
    final var textureId = Texture.create("./data/p3/battery.png");
    final var planeData = MeshFactory.createAnimatedSprite(7, 1.0f, 1.0f);
    final var timings = List.of(1000, 750, 500, 250, 150, 150, 1000);
    this.component = SpriteAnimationComponent.create(planeData.geometry(), planeData.textureCoordinates(), textureId, timings);
  }

  void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      SpriteAnimationSystem.draw(component, model, view, projection);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
