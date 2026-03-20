package dev.donutquine.math;

// Note: It is no actually point that's values cannot be mutated, 
//  but rather an interface which exposes only immutable (const) methods.
public interface ImmutablePoint {
    public float getX();

    public float getY();
}
