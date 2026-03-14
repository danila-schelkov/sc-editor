package dev.donutquine.editor.layout;

import java.awt.Insets;
import com.formdev.flatlaf.extras.components.FlatTabbedPane;
import dev.donutquine.editor.assets.AssetFile;
import dev.donutquine.editor.assets.AssetFileManager;

public final class FileTabBar extends FlatTabbedPane {
    private final AssetFileManager manager;

    public FileTabBar(AssetFileManager manager) {
        setTabLayoutPolicy(FlatTabbedPane.SCROLL_TAB_LAYOUT);

        this.manager = manager;

        manager.registerOpenedEventListener((openedEvent) -> {
            addTab(openedEvent.file().getName(), null);
            setSelectedIndex(manager.getFiles().size() - 1);
        });

        manager.registerClosedEventListener((closedEvent) -> {
            this.removeTabAt(closedEvent.fileIndex());
        });

        addChangeListener((_changeEvent) -> {
            int selectedIndex = this.getSelectedIndex();
            if (selectedIndex == -1) return;

            selectFile(manager.getFiles().get(selectedIndex));
        });

        this.setTabsClosable(true);
        this.setTabCloseToolTipText("Close");
        this.setTabCloseCallback((_tab, index) -> {
            closeFile(index);
        });
        this.setTabType(TabType.underlined);
        this.setTabInsets(new Insets(0, 8, 0, 8));
    }

    private void selectFile(AssetFile<?> file) {
        manager.setActiveFile(file);
    }

    private void closeFile(int fileIndex) {
        this.removeTabAt(fileIndex);

        manager.closeFile(manager.getFiles().get(fileIndex));
    }
}
