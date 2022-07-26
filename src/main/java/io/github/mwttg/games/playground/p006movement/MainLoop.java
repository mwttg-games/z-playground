package io.github.mwttg.games.playground.p006movement;

import io.github.mwttg.games.opengl.basic.utilities.geometry.MeshFactory;
import io.github.mwttg.games.opengl.basic.utilities.texture.Texture;
import io.github.mwttg.games.platform.ecs.GameState;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteAnimationComponent;
import io.github.mwttg.games.platform.ecs.component.draw.SpriteStatesAnimationComponent;
import io.github.mwttg.games.platform.ecs.component.movement.TileSize;
import io.github.mwttg.games.platform.ecs.entity.PlayerEntity;
import io.github.mwttg.games.platform.ecs.entity.SceneEntity;
import io.github.mwttg.games.platform.ecs.system.Timer;
import io.github.mwttg.games.playground.common.ConfigurationFactory;
import io.github.mwttg.games.playground.common.ProjectionMatrix;
import io.github.mwttg.games.playground.common.ViewMatrix;
import java.util.List;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL41;

public class MainLoop {

  private final Matrix4f view = ViewMatrix.get();
  private final Matrix4f projection = ProjectionMatrix.get320x180New();
  private final long windowId;

  private final SceneEntity sceneEntity;
  private final PlayerEntity playerEntity;
  private final GameState gameState;
  private final Timer timer;

  public MainLoop(long windowId) {
    this.windowId = windowId;
    this.sceneEntity = createScene();
    this.playerEntity = createPlayer();
    this.gameState = ConfigurationFactory.createGameState();
    this.timer = new Timer();
  }

  private SceneEntity createScene() {
    final var gridFilename = "./data/p006/level-solid-grid.json";
    final var textureFilename = "./data/p006/level.png";
    return SceneEntity.create(gridFilename, textureFilename, 20, 10);
  }

  private PlayerEntity createPlayer() {
    final var tileSize = new TileSize(1.0f, 1.0f);
    final var spriteComponent = createPlayerSpriteComponent(tileSize);
    return PlayerEntity.create(windowId, tileSize, spriteComponent, 5.0f, 8.0f);
  }

  private static SpriteStatesAnimationComponent createPlayerSpriteComponent(final TileSize tileSize) {
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

    final var spriteComponent = new SpriteStatesAnimationComponent();
    spriteComponent.addState("walkLeft", walkLeft);
    spriteComponent.addState("walkRight", walkRight);
    spriteComponent.addState("idleLeft", idleLeft);
    spriteComponent.addState("idleRight", idleRight);
    spriteComponent.setCurrentState("idleRight");

    return spriteComponent;
  }

  void loop() {
    timer.reset();
    while (!GLFW.glfwWindowShouldClose(windowId)) {
      GL41.glClear(GL41.GL_COLOR_BUFFER_BIT | GL41.GL_DEPTH_BUFFER_BIT);

      // timing
      final var deltaTime = timer.getDeltaTime();

      // physics
      playerEntity.update(sceneEntity.solidGridComponent(), deltaTime, gameState);

      // render
      sceneEntity.draw(view, projection);
      playerEntity.draw(view, projection);

      GLFW.glfwSwapBuffers(windowId);
      GLFW.glfwPollEvents();
    }
  }
}
