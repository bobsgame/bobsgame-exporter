package com.bobsgame.exporter.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ZipUtils {
    public static String decodeBase64String(String s) {
        return s != null && s.length() != 0 ? StringUtils.newStringUtf8(Base64.decodeBase64(s)) : s;
    }

    public static String encodeStringToBase64(String s) {
        return s != null && s.length() != 0 ? Base64.encodeBase64String(StringUtils.getBytesUtf8(s)) : s;
    }

    public static byte[] getByteArrayFromIntArray(int[] intArray) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(intArray.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(intArray);
        return byteBuffer.array();
    }

    public static int[] getIntArrayFromByteArray(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        int[] intArray = new int[bytes.length / 4];
        intBuffer.get(intArray);
        return intArray;
    }

    public static byte[] getByteArrayFromFileInZip(ZipFile zip, String fileName) {
        ZipArchiveEntry z = zip.getEntry(fileName);
        long size = z.getSize();
        byte[] bytes = new byte[(int) size];
        InputStream zin;

        try {
            zin = zip.getInputStream(z);
            IOUtils.readFully(zin, bytes);
            zin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static int[] getIntArrayFromFileInZip(ZipFile zip, String fileName) {
        return getIntArrayFromByteArray(getByteArrayFromFileInZip(zip, fileName));
    }
}
