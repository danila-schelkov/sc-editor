package dev.donutquine.editor.gui.navigation;

public interface NavigableAsset<K, V> {
    NavigationHistory<K> getNavigationHistory();

    V getById(K id);
}
