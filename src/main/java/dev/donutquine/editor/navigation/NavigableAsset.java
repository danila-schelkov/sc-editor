package dev.donutquine.editor.navigation;

public interface NavigableAsset<K, V> {
    NavigationHistory<K> getNavigationHistory();

    V getById(K id);
}
