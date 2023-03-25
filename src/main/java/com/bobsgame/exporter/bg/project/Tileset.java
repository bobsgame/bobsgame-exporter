package com.bobsgame.exporter.bg.project;

import com.bobsgame.exporter.BobsGameExporter;
import com.bobsgame.exporter.bg.Project;
import com.bobsgame.exporter.util.ZipUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tileset {
    public int tilePaletteIndex[][][]; // [index][x][y]
    public static int tileCount = 1;
    public static int tileWidth = 8;
    public static int tileHeight = 8;

    public static BufferedImage[] tileImage;  // [index]

    private Color CLEAR = new Color(0, 0, 0, 0);
    private Color TRANSPARENT_RED = new Color(255, 0, 0, 150); //bob 20060624

    public Tileset() {
        this(5000);
    }

    public Tileset(int size) {
        tileCount = size;
        if (tileCount < 5000) {
            tileCount = 5000;
        }

        tilePaletteIndex = new int[tileCount][tileWidth][tileHeight];
        tileImage = new BufferedImage[tileCount];

        for (int tile = 0; tile < tileCount; tile++) {
            tileImage[tile] = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < tileHeight; x++) {
                for (int y = 0; y < tileHeight; y++) {
                    tilePaletteIndex[tile][x][y] = 0;
                }
            }
        }
    }

    public int[] getAsIntArray() {
        int[] intArray = new int[tileCount*8*8];
        for (int tile = 0; tile < tileCount; tile++) {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    int i = getPixel(tile, x, y);
                    int index = (tile * 8 * 8) + (y * 8) + (x);
                    intArray[index] = i;
                }
            }
        }
        return intArray;
    }

    public byte[] getAsByteArray() {
        return ZipUtils.getByteArrayFromIntArray(getAsIntArray());
    }

    public void initializeFromIntArray(int[] intArray) {
        for (int tile = 0; tile < tileCount; tile++) {
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    int index = (tile * 8 * 8) + (y * 8) + (x);
                    int i = intArray[index];
                    setPixel(tile, x, y, i);
                }
            }
        }
    }

    public void setPixel(int index, int x, int y, int colorIndex) {
        if (index + 1 > tileCount) {
            tileCount = index + 1; // Increase the tileset size if the pixel to be set is outside current size
        }
        tilePaletteIndex[index][x][y] = colorIndex;
        tileImage[index].setRGB(x, y, getColor(index, x, y).getRGB());
    }

    public int getPixel(int index, int x, int y) {
        return tilePaletteIndex[index][x][y];
    }

    public void buildTileImages() {
        BobsGameExporter.LOGGER.info("Building Tile Images!");
        for (int tile = 0; tile < tileCount; tile++) {
            for (int x = 0; x < tileHeight; x++) {
                for (int y = 0; y < tileHeight; y++) {
                    Color color = getColor(tile, x, y);
                    //if (color.getRed() != 0 || color.getBlue() != 0 || color.getGreen() != 0)
                    //TilesetExporter.LOGGER.info("Tile {} at ({}, {}) being set to {}", tile, x, y, color);
                    tileImage[tile].setRGB(x, y, color.getRGB());
                }
            }
        }
    }

    public Color getColor(int index, int x, int y) {
        if (Project.getSelectedPaletteIndex() >= 0) {
            if (tilePaletteIndex[index][x][y] != 0) {
                return Project.getSelectedPalette().getColor(tilePaletteIndex[index][x][y]);
            } else {
                return CLEAR;
            }
        } else {
            return Color.RED;
        }
    }
}
