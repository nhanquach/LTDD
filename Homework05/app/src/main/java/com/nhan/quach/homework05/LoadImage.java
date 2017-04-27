package com.nhan.quach.homework05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by nhan on 4/27/17.
 */

public class LoadImage{
    public static Bitmap loadBitmap(String this_url) throws IOException {
        try {
            URL url = new URL(this_url);
            Bitmap mIcon_val = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return mIcon_val;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
