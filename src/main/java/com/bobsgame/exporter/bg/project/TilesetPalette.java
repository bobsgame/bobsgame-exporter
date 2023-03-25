package com.bobsgame.exporter.bg.project;

import java.awt.*;

public class TilesetPalette {
    public String name;
    public int data[][]; // [index][r, g, b, bgr, hsb]
    public Color color[]; // [index]
    public boolean used[]; // [index]
    public int hsbi[][];

    public int maxRGBValue = 25;;
    public int numColors = 1024;

    public TilesetPalette(String n) {
        this.name = n;
        this.data = new int[numColors][4];
        this.color = new Color[numColors];
        this.used = new boolean[numColors];
        this.hsbi = new int[numColors][3];

        for (int i = 0; i < numColors; i++) {
            this.color[i] = Color.BLACK;
            this.data[i][0] = 0;
            this.data[i][1] = 0;
            this.data[i][2] = 0;
            this.data[i][3] = 0;
            this.used[i] = false;
            this.hsbi[i][0] = 0;
            this.hsbi[i][1] = 0;
            this.hsbi[i][2] = 0;
        }
    }

    public TilesetPalette(String name, byte[] bytes) {
        this.name = name;
        this.numColors = bytes.length / 3;

        this.data = new int[numColors][4];
        this.color = new Color[numColors];
        this.used = new boolean[numColors];
        this.hsbi = new int[numColors][3];

        for (int i = 0; i < numColors; i++) {
            color[i] = Color.BLACK;
            data[i][0] = 0;
            data[i][1] = 0;
            data[i][2] = 0;
            data[i][3] = 0;
            used[i] = false;
            hsbi[i][0] = 0;
            hsbi[i][1] = 0;
            hsbi[i][2] = 0;
        }

        initFromByteArray(bytes);
    }

    public void initFromByteArray(byte[] bytes) {
        for (int c = 0; c < numColors; c++) {
            int r = bytes[c * 3] & 0xFF;
            int g = bytes[c * 3 + 1] & 0xFF;
            int b = bytes[c * 3 + 2] & 0xFF;
            setColorDataFromRGB(c,r,g,b);
        }
    }

    public void setColorDataFromRGB(int i, int r, int g, int b) {
        int rf = r;
        int gf = g;
        int bf = b;
        int bgr = (((bf / 8) * 1024) + ((gf / 8) * 32) + ((rf / 8)));

        color[i] = new Color(r, g, b);

        data[i][0] = r;
        data[i][1] = g;
        data[i][2] = b;
        data[i][3] = bgr;

        float hsbf[] = new float[3];
        Color.RGBtoHSB(r, g, b, hsbf);
        hsbi[i][0] = Math.round(hsbf[0] * (float)maxRGBValue);
        hsbi[i][1] = Math.round(hsbf[1] * (float)maxRGBValue);
        hsbi[i][2] = Math.round(hsbf[2] * (float)maxRGBValue);

        used[i] = true;
    }

    public Color getColor(int i) {
        return color[i];
    }
}
