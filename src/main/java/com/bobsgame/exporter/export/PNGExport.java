package com.bobsgame.exporter.export;

import com.bobsgame.exporter.BobsGameExporter;
import com.bobsgame.exporter.bg.Project;
import com.bobsgame.exporter.bg.project.Tileset;
import org.apache.commons.compress.archivers.zip.ZipFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PNGExport implements Export {
    @Override
    public byte[] export(ZipFile zipFile) throws IOException {
        Project.init(zipFile);

        int numCols = 16; // 16 tiles per row
        //int numRows = (int) Math.ceil((double) Tileset.tileCount / 8.0); // 8 tiles per row
        int outputWidth = Tileset.tileWidth * numCols;
        int outputHeight = 2000; // Tileset.tileWidth * numRows;

        BufferedImage img = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        int x, y;

        for (int tile = 0; tile < Tileset.tileCount; tile++) {
            // calculate the position of the current tile in the output image
            x = (tile % numCols) * Tileset.tileWidth;
            y = (tile / numCols) * Tileset.tileWidth;
            BobsGameExporter.LOGGER.info("Placing tile at ({}, {}) at index {}", x, y, tile);
            g.drawImage(Tileset.tileImage[tile], x, y, null);
        }

        g.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", outputStream);
        return outputStream.toByteArray();
    }
}
