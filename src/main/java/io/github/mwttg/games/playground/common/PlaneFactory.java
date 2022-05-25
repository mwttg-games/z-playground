package io.github.mwttg.games.playground.common;

public final class PlaneFactory {

  private PlaneFactory() {
  }

  public static PlaneData create(final float width, final float height) {
    return new PlaneData(
        new float[] {
            width, 0.0f, 0.0f,
            0.0f, height, 0.0f,
            0.0f, 0.0f, 0.0f,
            width, 0.0f, 0.0f,
            width, height, 0.0f,
            0.0f, height, 0.0f},
        new float[] {
            1.0f, 0.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f});
  }

  public record PlaneData(float[] geometry, float[] textureCoordinates) {
  }
}
