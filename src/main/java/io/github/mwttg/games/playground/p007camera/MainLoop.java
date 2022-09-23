package io.github.mwttg.games.playground.p007camera;

import io.github.mwttg.games.platform.Timer;
import io.github.mwttg.games.platform.camera.CameraSystem;
import io.github.mwttg.games.platform.camera.ProjectionMatrix;
import io.github.mwttg.games.platform.entity.LevelDefinition;
import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.WorldEntity;
import io.github.mwttg.games.platform.level.LevelStateComponent;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public class MainLoop {

  private final Matrix4f projection = ProjectionMatrix.get();
  private final long windowId;

  private final PlayerEntity playerEntity;
  private final WorldEntity worldEntity;
  private final Timer timer;

  public MainLoop(long windowId) {
    this.windowId = windowId;
    this.playerEntity = PlayerEntity.create(windowId, 9.0f, 8.0f, "./data/player/player.json");
    this.worldEntity = createWorld();
    this.timer = new Timer();
  }

  void loop() {
    timer.reset();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      // timing
      final var deltaTime = timer.getDeltaTime();
      final var solidGrid =
          worldEntity.levelStateComponent().getLevelComponent().sensorComponent(); //sceneEntity.sensorComponent();


      // physics
      playerEntity.update(deltaTime, solidGrid);
      worldEntity.update(playerEntity);

      // render
      final var view = CameraSystem.getViewMatrix(worldEntity, playerEntity);
      worldEntity.draw(view, projection);
      playerEntity.draw(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }

  private WorldEntity createWorld() {
    final var room1 = LevelDefinition.create("./data/p007/room1/room1.json");
    final var room2 = LevelDefinition.create("./data/p007/room2/room2.json");
    final var room3 = LevelDefinition.create("./data/p007/room3/room3.json");
    final var room4 = LevelDefinition.create("./data/p007/room4/room4.json");
    final var room5 = LevelDefinition.create("./data/p007/room5/room5.json");
    final var levels = Map.of(
        room1.levelId(), room1,
        room2.levelId(), room2,
        room3.levelId(), room3,
        room4.levelId(), room4,
        room5.levelId(), room5);
    final var levelStateComponent = new LevelStateComponent(room3.levelId(), levels);

    return new WorldEntity(levelStateComponent);
  }
}
