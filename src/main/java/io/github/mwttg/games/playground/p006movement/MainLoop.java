package io.github.mwttg.games.playground.p006movement;

import static io.github.mwttg.games.platform.player.states.PlayerState.IDLE_LEFT;
import static io.github.mwttg.games.platform.player.states.PlayerState.IDLE_RIGHT;
import static io.github.mwttg.games.platform.player.states.PlayerState.WALK_LEFT;
import static io.github.mwttg.games.platform.player.states.PlayerState.WALK_RIGHT;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.player.TileSize;
import io.github.mwttg.games.platform.entity.PlayerEntity;
import io.github.mwttg.games.platform.entity.SceneEntity;
import io.github.mwttg.games.platform.Timer;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import java.util.List;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public class MainLoop {

  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180New();
  private final long windowId;

  private final SceneEntity sceneEntity;
  private final PlayerEntity playerEntity;
  private final Timer timer;

  public MainLoop(long windowId) {
    this.windowId = windowId;
    this.sceneEntity = createScene();
    this.playerEntity = createPlayer();
    this.timer = new Timer();
  }

  void loop() {
    timer.reset();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      // timing
      final var deltaTime = timer.getDeltaTime();
      final var solidGrid = sceneEntity.solidGridComponent();

      // physics
      playerEntity.update(deltaTime, solidGrid);

      // render
      sceneEntity.draw(view, projection);
      playerEntity.draw(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }

  private SceneEntity createScene() {
    final var gridFilename = "./data/p006/level-solid-grid.json";
    final var textureFilename = "./data/p006/level.png";
    return SceneEntity.create(gridFilename, textureFilename, 20, 10);
  }

  private PlayerEntity createPlayer() {
    final var tileSize = new TileSize(1.0f, 1.0f);
    final var spriteComponent = createPlayerSpriteComponent(tileSize);
    return PlayerEntity.create(windowId, spriteComponent, 9.0f, 8.0f);
  }

  private static Map<String, SpriteAnimationComponent> createPlayerSpriteComponent(final TileSize tileSize) {
    final var width = tileSize.width();
    final var height = tileSize.height();

    final var walkPlaneData = MeshFactory.createAnimatedSprite(6, width, height);
    final var walkTimings = List.of(140, 140, 140, 140, 140, 140);

    final var walkLeftTextureId = Texture.create("./data/p006/Player_walk_left.png");
    final var walkLeft =
        SpriteAnimationComponent.create(walkPlaneData.geometry(), walkPlaneData.textureCoordinates(), walkLeftTextureId,
            walkTimings);

    final var walkRightTextureId = Texture.create("./data/p006/Player_walk_right.png");
    final var walkRight =
        SpriteAnimationComponent.create(walkPlaneData.geometry(), walkPlaneData.textureCoordinates(), walkRightTextureId,
            walkTimings);

    final var idlePlaneData = MeshFactory.createAnimatedSprite(3, width, height);
    final var idleTimings = List.of(1500, 150, 250);

    final var idleLeftTextureId = Texture.create("./data/p006/Player_idle_left.png");
    final var idleLeft =
        SpriteAnimationComponent.create(idlePlaneData.geometry(), idlePlaneData.textureCoordinates(), idleLeftTextureId,
            idleTimings);

    final var idleRightTextureId = Texture.create("./data/p006/Player_idle_right.png");
    final var idleRight =
        SpriteAnimationComponent.create(idlePlaneData.geometry(), idlePlaneData.textureCoordinates(), idleRightTextureId,
            idleTimings);

    return Map.of(WALK_LEFT, walkLeft,
        WALK_RIGHT, walkRight,
        IDLE_LEFT, idleLeft,
        IDLE_RIGHT, idleRight);
  }
}
