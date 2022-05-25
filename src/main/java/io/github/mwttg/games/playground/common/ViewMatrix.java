package io.github.mwttg.games.playground.common;

import org.joml.Matrix4f;

public final class ViewMatrix {

  private ViewMatrix() {
  }
   public static Matrix4f get() {
     return new Matrix4f().setLookAt(0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
   }
}
