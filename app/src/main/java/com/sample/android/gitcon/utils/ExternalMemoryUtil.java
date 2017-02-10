package com.sample.android.gitcon.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class ExternalMemoryUtil {

    /**
     * @param directoryType The type of files directory to return. May be null for the root of the files directory or one of the following Environment constants for a subdirectory: Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_PODCASTS, Environment.DIRECTORY_RINGTONES, Environment.DIRECTORY_ALARMS, Environment.DIRECTORY_NOTIFICATIONS, Environment.DIRECTORY_PICTURES, or Environment.DIRECTORY_MOVIES
     * @return Directory or null when external storage is not mounted.
     */
    public static File getDirectory(Context context, String directoryType) {
        return context.getExternalFilesDir(directoryType);
    }

    /**
     * @param directoryType The type of files directory to return. May be null for the root of the files directory or one of the following Environment constants for a subdirectory: Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_PODCASTS, Environment.DIRECTORY_RINGTONES, Environment.DIRECTORY_ALARMS, Environment.DIRECTORY_NOTIFICATIONS, Environment.DIRECTORY_PICTURES, or Environment.DIRECTORY_MOVIES
     * @return Directory path or null when external storage is not mounted.
     */
    public static String getDirectoryPath(Context context, String directoryType) {
        File dir = getDirectory(context, directoryType);

        if (dir != null) {
            return dir.getAbsolutePath();
        }

        return null;
    }

    /**
     * @param directoryType The type of files directory to return. May be null for the root of the files directory or one of the following Environment constants for a subdirectory: Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_PODCASTS, Environment.DIRECTORY_RINGTONES, Environment.DIRECTORY_ALARMS, Environment.DIRECTORY_NOTIFICATIONS, Environment.DIRECTORY_PICTURES, or Environment.DIRECTORY_MOVIES
     * @param fileName      Name + extension
     * @return File or null when external storage is not mounted.
     */
    public static File getFile(Context context, String directoryType, String fileName) {
        File dir = getDirectory(context, directoryType);

        if (dir != null) {
            return new File(dir, fileName);
        }

        return null;
    }

    /**
     * @param directoryType The type of files directory to return. May be null for the root of the files directory or one of the following Environment constants for a subdirectory: Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_PODCASTS, Environment.DIRECTORY_RINGTONES, Environment.DIRECTORY_ALARMS, Environment.DIRECTORY_NOTIFICATIONS, Environment.DIRECTORY_PICTURES, or Environment.DIRECTORY_MOVIES
     * @param fileName      Name + extension
     * @return File path or null when external storage is not mounted.
     */
    public static String getFilePath(Context context, String directoryType, String fileName) {
        File file = getFile(context, directoryType, fileName);

        if (file != null) {
            return file.getAbsolutePath();
        }

        return null;
    }

    /**
     * @param directoryType The type of files directory to return. May be null for the root of the files directory or one of the following Environment constants for a subdirectory: Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_PODCASTS, Environment.DIRECTORY_RINGTONES, Environment.DIRECTORY_ALARMS, Environment.DIRECTORY_NOTIFICATIONS, Environment.DIRECTORY_PICTURES, or Environment.DIRECTORY_MOVIES
     * @param fileName      Name + extension
     */
    public static boolean existFile(Context context, String directoryType, String fileName) {
        File file = getFile(context, directoryType, fileName);

        return file != null && file.exists();
    }

    /**
     * @param directoryType The type of files directory to return. May be null for the root of the files directory or one of the following Environment constants for a subdirectory: Environment.DIRECTORY_MUSIC, Environment.DIRECTORY_PODCASTS, Environment.DIRECTORY_RINGTONES, Environment.DIRECTORY_ALARMS, Environment.DIRECTORY_NOTIFICATIONS, Environment.DIRECTORY_PICTURES, or Environment.DIRECTORY_MOVIES
     * @param fileName      Name + extension
     * @return True on success otherwise false
     */
    public static boolean deleteFile(Context context, String directoryType, String fileName) {
        File file = getFile(context, directoryType, fileName);

        return file != null && file.delete();
    }

    /**
     * Checks if external storage is available for read and write
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);

    }
}