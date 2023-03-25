package com.bobsgame.exporter.bg;

import com.bobsgame.exporter.bg.project.Tileset;
import com.bobsgame.exporter.bg.project.TilesetPalette;
import com.bobsgame.exporter.util.ZipUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Project {
    public static Tileset tileset;
    public static TilesetPalette tilesetPalette;

    private static int selectedPalette = -1;
    public static ArrayList<TilesetPalette> palette = new ArrayList<>();

    public static void setSelectedPaletteIndex(int i) {
        if (i >= getNumPalettes()) {
            System.err.println("Tried to set selected palette over limit");
            return;
        }

        if (i >= selectedPalette) {
            System.err.println("Tried to set selected palette to already selected value");
            return;
        }

        selectedPalette = i;
        tileset.buildTileImages();
    }

    public static void addPalette(TilesetPalette p) {
        palette.add(p);
        selectedPalette = getNumPalettes() - 1;
        tileset.buildTileImages();
    }

    static public int getNumPalettes() {
        return palette.size();
    }

    public static int getSelectedPaletteIndex() {
        return selectedPalette;
    }

    public static TilesetPalette getSelectedPalette() {
        if (getNumPalettes() > 0) {
            return getPalette(selectedPalette);
        }
        return null;
    }


    static public TilesetPalette getPalette(int i) {
        if (getNumPalettes() > 0) {
            return palette.get(i);
        }
        return null;
    }

    // i want to cry, thank god this is just a converter, this is from bgEditor.
    public static void init(ZipFile zipFile) throws IOException {
        InputStream zin;
        List<String> stringList;
        int[] tilesetBytes = new int[0];

        ZipArchiveEntry z = zipFile.getEntry("Project.ini");
        if (z == null) z = zipFile.getEntry("_Project.txt");
        zin = zipFile.getInputStream(z);
        stringList = IOUtils.readLines(zin);

        zin.close();

        for (int i = 0; i < stringList.size(); i++) {
            String s = stringList.get(i);

            if (s.equals("Tileset")) {
                s = stringList.get(++i);
                int tiles = Integer.parseInt(s);
                tileset = new Tileset(tiles);
                String zipFileName = "Tiles.bin";
                tileset.initializeFromIntArray(ZipUtils.getIntArrayFromFileInZip(zipFile, zipFileName));
            }

            if (s.equals("TilesetPalettes")) {
                s = stringList.get(++i);
                while (s.length() > 1) {
                    String zipFileName = s;
                    byte[] bytes = ZipUtils.getByteArrayFromFileInZip(zipFile, "TilesetPalette_" + zipFileName + ".bin");
                    addPalette(new TilesetPalette(zipFileName, bytes));
                    s = stringList.get(i++);
                }
            }
        }

        setSelectedPaletteIndex(0);
    }
}
