package com.otter.otterlibrary;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * OFile is a toolkit of file operation.
 */
public class OFile {

    private static void copy(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[1024];
            int byteCount;

            while ((byteCount = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteCount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** Copy the file. It will override current file. */
    public static boolean copyFile(File from, File to) {
        if (from == null || to == null) {
            return false;
        }
        if (!from.exists() || !from.isFile() || !from.canRead()) {
            return false;
        }

        try {
            FileInputStream in = new FileInputStream(from);
            FileOutputStream out = new FileOutputStream(to);
            copy(in, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        long fromLength = from.length();
        long toLength = to.length();
        return fromLength == toLength;
    }

    /**
     * Copy the directory recursively.
     * If the destination directory did exist, it will be deleted first.
     */
    public static boolean copyDir(File from, File to) {
        if (from == null || to == null) {
            return false;
        }
        if (!from.exists() || !from.canRead()) {
            return false;
        }

        if (from.isDirectory()) {
            if (to.exists()) {
                // Destination directory did exist, delete it.
                if (!deleteDir(to)) {
                    return false;
                }
            }
            if (!to.mkdirs()) {
                return false;
            }

            String[] children = from.list();
            for (String child : children) {
                if (!copyDir(new File(from, child), new File(to, child))) {
                    return false;
                }
            }
        } else {
            return copyFile(from, to);
        }

        // The directory copy is completely.
        return true;
    }

    /** Delete the folder recursively. */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }

        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File child : children) {
                if (!deleteDir(child)) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    /** Copy the file from assets. It will override current file. */
    public static boolean copyFileFromAssets(Context ctx, String fileName, File to) {
        if (fileName == null || to == null) {
            return false;
        }
        try {
            InputStream in = ctx.getAssets().open(fileName);
            FileOutputStream out = new FileOutputStream(to);
            copy(in, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return to.exists();
    }
}
