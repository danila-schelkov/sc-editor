package dev.donutquine.editor.events;


@FunctionalInterface
public interface EventListener<E> {
    void onEvent(E event);
}
