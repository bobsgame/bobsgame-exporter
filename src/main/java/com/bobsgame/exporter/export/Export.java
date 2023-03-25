package com.bobsgame.exporter.export;

import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.IOException;

public interface Export {
    byte[] export(ZipFile zipFile) throws IOException;
}
