package com.backofficecloudapps.prop.inventory.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phoenix
 */
public class Utility {

    /**
     * The constant ROOT.
     */
    public static final File ROOT = Environment.getExternalStorageDirectory();

    /**
     * Gets file path.
     *
     * @param fileName the file name
     * @return the file path
     */
    public static final String getFilePath(@NonNull String fileName) {
        File ret = null;
        try {
            File file = new File(ROOT, "ABCInventory/");
            if (!file.exists()) {
                file.mkdirs();
            }
            ret = new File(file, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.getAbsolutePath();
    }

    /**
     * Get image combined byte byte [ ].
     *
     * @param photos the photos
     * @return the byte [ ]
     */
    public static final byte[] getImageCombinedByte(@NonNull List<String> photos) {
        Bitmap bmp = combineBitmaps(photos);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /**
     * Combine bitmaps bitmap.
     *
     * @param photos the photos
     * @return the bitmap
     */
    public static Bitmap combineBitmaps(@NonNull List<String> photos) {
        Log.i("DREG", "Photos size=" + photos.size());
        List<Bitmap> bms = new ArrayList<Bitmap>();
        for (int i = 0; i < photos.size(); i++) {
            File file = new File(photos.get(i));
            if (file.exists() && file.isFile()) {
                Bitmap bmp = decodeAndResizeFile(file, 150);
                bmp = addWhiteBorder(bmp, 3);
                bms.add(bmp);
            }
        }
        Bitmap result = Bitmap.createBitmap(bms.get(0).getWidth() * 3, bms.get(0).getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
        for (int i = 0; i < bms.size(); i++) {
            canvas.drawBitmap(bms.get(i), bms.get(0).getWidth() * i, 0, paint);
        }

        return result;
    }

    /**
     * Decode and resize file bitmap.
     *
     * @param f             the f
     * @param REQUIRED_SIZE the required size
     * @return the bitmap
     */
    public static Bitmap decodeAndResizeFile(@NonNull File f, int REQUIRED_SIZE) {
        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    /**
     * Add white border bitmap.
     *
     * @param bmp        the bmp
     * @param borderSize the border size
     * @return the bitmap
     */
    public static Bitmap addWhiteBorder(@NonNull Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

}
