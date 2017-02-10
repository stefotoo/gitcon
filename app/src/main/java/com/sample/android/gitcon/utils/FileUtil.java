package com.sample.android.gitcon.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static File pathToFile(String path) {
        return new File(path);
    }

    public static boolean existFile(String path) {
        return pathToFile(path).exists();
    }

    public static boolean deleteFile(String path) {
        return pathToFile(path).delete();
    }

    /**
     * API for generating Thumbnail from particular time frame
     *
     * @param filePath      - video file path
     * @param timeInSeconds - thumbnail to generate at time
     * @return - thumbnail bitmap
     */
    public static Bitmap createThumbnailAtTime(String filePath, int timeInSeconds) {
        MediaMetadataRetriever mMMR = new MediaMetadataRetriever();
        mMMR.setDataSource(filePath);
        //api time unit is microseconds
        return mMMR.getFrameAtTime(timeInSeconds * 1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
    }

    /**
     * @return Bitmap file
     */
    public static File saveBitmap(File outputFile, Bitmap bitmap)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(outputFile);

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } finally {
            fos.flush();
            fos.close();
            bitmap.recycle();
        }

        return outputFile;
    }

    public static long checkFileSize(String filePath) {
        File file = new File(filePath);

        return file.length();
    }

    public static void inputStreamToFile(
            InputStream is,
            File outputFile) {
        OutputStream outputStream = null;

        try {
            // write the inputStream to a FileOutputStream
            outputStream =
                    new FileOutputStream(outputFile);

            int read;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static byte[] fileToBytes(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            try {
                int size = (int) file.length();
                byte[] bytes = new byte[size];

                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();

                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void saveBytesToFile(byte[] bytes, String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);

        fos.write(bytes);
        fos.close();
    }

    public static File createImageFile(File dir) throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
/*
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
*/

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                dir      /* directory */
        );
    }

    public static File createThumbnailImageFile(Context context, String imageFileName) throws IOException {
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                context.getCacheDir()      /* directory */
        );
    }

}