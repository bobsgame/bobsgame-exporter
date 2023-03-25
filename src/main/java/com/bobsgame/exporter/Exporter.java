package com.bobsgame.exporter;

import com.bobsgame.exporter.export.ExportType;
import com.bobsgame.exporter.export.PNGExport;
import com.bobsgame.exporter.export.TiledExport;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Exporter {
    private String filePath;
    private String outputPath;

    public Exporter(String filePath, String outputPath) {
        this.filePath = filePath;
        this.outputPath = outputPath;
    }

    public void export(ExportType exportType) {
        try {
            ZipFile zipFile = new ZipFile(new File(filePath));
            byte[] bytes = new byte[0];
            switch (exportType) {
                case PNG_TILESET:
                    bytes = new PNGExport().export(zipFile);
                    break;
                case TILED_TILESET:
                    bytes = new TiledExport().export(zipFile);
                    break;
            }
            Files.write(new File(outputPath).toPath(), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
