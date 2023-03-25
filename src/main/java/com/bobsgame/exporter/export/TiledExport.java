package com.bobsgame.exporter.export;

import org.apache.commons.compress.archivers.zip.ZipFile;

public class TiledExport implements Export {
    @Override
    public byte[] export(ZipFile zipFile) {
        return new byte[0];
    }
}
