package com.vorono4ka.utilities;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;

public class ByteArrayFlavor implements Transferable {
    private static final int BYTE_FLAVOR = 0;
    private static final int STRING_FLAVOR = 1;

    private static final DataFlavor[] flavors = {
        createConstant(byte[].class, "bytes"),
        createConstant(String.class, "Unicode string"),
    };

    private final byte[] data;

    public ByteArrayFlavor(byte[] data) {
        this.data = data;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return flavors.clone();
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        for (DataFlavor dataFlavor : flavors) {
            if (flavor.equals(dataFlavor)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(flavors[BYTE_FLAVOR]))
            return this.data;
        else if (flavor.equals(flavors[STRING_FLAVOR])) {
            return Arrays.toString(this.data);
        }

        throw new UnsupportedFlavorException(flavor);
    }

    private static DataFlavor createConstant(Class<?> reprClass, String presentableName) {
        try {
            return new DataFlavor(reprClass, presentableName);
        } catch (Exception e) {
            return null;
        }
    }
}
