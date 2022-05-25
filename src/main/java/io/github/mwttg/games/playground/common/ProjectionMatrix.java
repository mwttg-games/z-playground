package io.github.mwttg.games.playground.common;

import org.joml.Matrix4f;

public final class ProjectionMatrix {

  private ProjectionMatrix() {
  }

  public static Matrix4f get() {
    return new Matrix4f().setOrtho(0.0f, 20.0f, 0.0f, 15.0f, 0.01f, 100.0f);
  }
}
