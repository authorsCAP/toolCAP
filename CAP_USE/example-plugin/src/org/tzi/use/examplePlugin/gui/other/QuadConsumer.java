package org.tzi.use.examplePlugin.gui.other;

@FunctionalInterface
public interface QuadConsumer<A, B, C, D> {
  void accept(A a, B b, C c, D d);
}
