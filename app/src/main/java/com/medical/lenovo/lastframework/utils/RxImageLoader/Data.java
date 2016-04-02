package com.medical.lenovo.lastframework.utils.RxImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * class store the url and bitmap
 * Created by lenovo on 2016/4/2.
 */
public class Data {
    public Bitmap bitmap;
    public String url;
    private boolean isAvailable;

    public Data(Bitmap bitmap, String url) {
        this.bitmap = bitmap;
        this.url = url;
    }

    public Data(File f, String url) {
        if (f != null && f.exists()) {
            this.url = url;
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Data(FileDescriptor fileDescriptor,String url){
        if (fileDescriptor != null){
            this.url = url;
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        }
    }

    public boolean isAvailable() {
        isAvailable = url != null && bitmap != null;
        return isAvailable;
    }
}
