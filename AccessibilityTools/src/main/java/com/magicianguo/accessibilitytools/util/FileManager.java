package com.magicianguo.accessibilitytools.util;

import com.magicianguo.accessibilitytools.App;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FileManager {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
    public static final String VIEW_AREA_LOG_PATH = App.get().getExternalFilesDir(null).getParent() + "/view-area.txt";

    public static void writeViewAreaLog(String content) {
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                synchronized (FileManager.class) {
                    try {
                        File file = new File(VIEW_AREA_LOG_PATH);
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(file, true);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        bos.write((content + "\n").getBytes());
                        bos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
