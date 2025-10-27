package dev.donutquine.editor.layout;

import dev.donutquine.editor.Editor;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class EditorDropTarget implements DropTargetListener {
    private final Editor editor;

    public EditorDropTarget(Editor editor) {
        this.editor = editor;
    }

    private static void onDrag(DropTargetDragEvent e) {
        if ((e.getDropAction() & DnDConstants.ACTION_MOVE) == 0) {
            e.rejectDrag();
            return;
        }

        if (!e.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            e.rejectDrag();
            return;
        }

        List<File> transferData;
        try {
            transferData = getFiles(e.getTransferable());
        } catch (UnsupportedFlavorException | IOException ex) {
            throw new RuntimeException(ex);
        }

        if (transferData.size() == 1) {
            e.acceptDrag(e.getDropAction());
        }
    }

    @Override
    public void dragEnter(DropTargetDragEvent e) {
        onDrag(e);
    }

    @Override
    public void dragOver(DropTargetDragEvent e) {
//        onDrag(e);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent e) {

    }

    @Override
    public void dragExit(DropTargetEvent e) {

    }

    @Override
    public void drop(DropTargetDropEvent e) {
        if (!e.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            e.rejectDrop();
            e.dropComplete(true);
            return;
        }

        e.acceptDrop(e.getDropAction());

        try {
            List<File> transferData = getFiles(e.getTransferable());
            assert transferData.size() == 1;

            // TODO: allow merging several files via DnD as an option
            editor.closeFile();
            this.editor.openFile(transferData.get(0).toPath());
        } catch (UnsupportedFlavorException | IOException ex) {
            throw new RuntimeException(ex);
        }

        e.dropComplete(true);
    }

    private static @NotNull List<File> getFiles(Transferable transferable) throws UnsupportedFlavorException, IOException {
        //noinspection unchecked
        return (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
    }
}
