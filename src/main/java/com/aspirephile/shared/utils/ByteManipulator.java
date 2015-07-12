package com.aspirephile.shared.utils;

import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteManipulator {

    public static byte[] getBytesFromFile(String imagePath) throws IOException {
        File file = new File(imagePath);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        //noinspection ResultOfMethodCallIgnored
        buf.read(bytes, 0, bytes.length);
        buf.close();
        return bytes;
    }

    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

}
