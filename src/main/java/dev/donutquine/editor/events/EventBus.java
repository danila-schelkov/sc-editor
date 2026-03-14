package dev.donutquine.editor.events;

import java.util.ArrayList;
import java.util.List;

public class EventBus<E> {
    private final List<EventListener<E>> listeners = new ArrayList<>();

    public void register(EventListener<E> listener) {
        this.listeners.add(listener);
    }

    public void unregister(EventListener<E> listener) {
        this.listeners.remove(listener);
    }

    public void fire(E event) {
        this.listeners.forEach(t -> t.onEvent(event));
    }
}
