package io.github.mwttg.games.playground.common;

import org.joml.Matrix4f;

public final class ProjectionMatrix {

  private ProjectionMatrix() {
  }

  public static Matrix4f get640x480() {
    return new Matrix4f().setOrtho(0.0f, 20.0f, 0.0f, 15.0f, 0.01f, 100.0f);
  }

  // mapping a 320x180 screen pixel perfect to 1920x1080
  public static Matrix4f get320x180() {
    return new Matrix4f().setOrtho(0.0f, 32.0f, 0.0f, 18.0f, 0.01f, 100.0f);
  }

  // mapping a 320x180 screen pixel perfect to 1920x1080
  public static Matrix4f get320x180New() {
    return new Matrix4f().setOrtho(0.0f, 20.0f, 0.0f, 10.0f, 0.01f, 100.0f);
  }

}
