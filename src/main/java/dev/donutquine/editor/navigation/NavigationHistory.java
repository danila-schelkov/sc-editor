package dev.donutquine.editor.navigation;

import java.util.ArrayList;
import java.util.List;
import dev.donutquine.editor.events.EventBus;
import dev.donutquine.editor.events.EventListener;

public class NavigationHistory<T> {
    private final EventBus<NavigationEvent> navigationEventBus = new EventBus<>();

    private final List<T> selectedIds = new ArrayList<>();

    private int historyPosition = -1;

    public boolean hasCurrent() {
        return this.historyPosition != -1; // or >= 0 && < selectedIds.size()
    }

    public T getCurrent() {
        return this.selectedIds.get(this.historyPosition);
    }

    public void add(T id) {
        if (this.historyPosition != -1 && this.historyPosition + 1 < this.selectedIds.size()) {
            this.selectedIds.subList(this.historyPosition + 1, this.selectedIds.size()).clear();
        }

        int index = this.historyPosition + 1;
        this.selectedIds.add(index, id);
        this.navigateTo(index);
    }

    public boolean hasPrevious() {
        return this.historyPosition > 0;
    }

    public boolean hasNext() {
        return this.historyPosition + 1 < this.selectedIds.size();
    }

    public void navigateToPrevious() {
        this.navigateTo(--this.historyPosition);
    }

    public void navigateToNext() {
        this.navigateTo(++this.historyPosition);
    }

    private void navigateTo(int newPosition) {
        if (newPosition < 0)
            return;
        if (newPosition >= this.selectedIds.size())
            return;

        this.historyPosition = newPosition;

        this.navigationEventBus.fire(new NavigationEvent(newPosition));
    }

    public void registerNavigationListener(EventListener<NavigationEvent> listener) {
        this.navigationEventBus.register(listener);
    }

    public void unregisterNavigationListener(EventListener<NavigationEvent> listener) {
        this.navigationEventBus.unregister(listener);
    }
}
