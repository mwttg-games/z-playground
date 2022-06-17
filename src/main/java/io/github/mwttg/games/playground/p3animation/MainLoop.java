package io.github.mwttg.games.playground.p3animation;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.ecs.system.draw.SpriteAnimationSystem;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

final class MainLoop {

  private final SpriteAnimationComponent battery1;
  private final Matrix4f model1 = new Matrix4f().translate(10, 9, 0);
  private final SpriteAnimationComponent battery2;
  private final Matrix4f model2 = new Matrix4f().translate(16, 9, 0);
  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180();

  MainLoop() {
    // battery 1
    final var textureId1 = Texture.create("./data/p3/battery.png");
    final var planeData1 = MeshFactory.createAnimatedSprite(7, 1.0f, 1.0f);
    final var timings1 = List.of(1000, 750, 500, 250, 150, 150, 1000);
    this.battery1 =
        SpriteAnimationComponent.create(planeData1.geometry(), planeData1.textureCoordinates(), textureId1, timings1);

    //battery 2
    final var textureId2 = Texture.create("./data/p3/battery2.png");
    final var planeData2 = MeshFactory.createAnimatedSprite(9, 4.0f, 4.0f);
    final var timings2 = List.of(1000, 75, 125, 175, 250, 500, 750, 875, 1000);
    this.battery2 =
        SpriteAnimationComponent.create(planeData2.geometry(), planeData2.textureCoordinates(), textureId2, timings2);

  }

  void loop(final long gameWindowId) {
    while (!GLFW.glfwWindowShouldClose(gameWindowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      SpriteAnimationSystem.draw(battery1, model1, view, projection);
      SpriteAnimationSystem.draw(battery2, model2, view, projection);

      GLFW.glfwSwapBuffers(gameWindowId);
      GLFW.glfwPollEvents();
    }
  }
}
