package com.backofficecloudapps.prop.inventory.activity;

/**
 * Created by Phoenix
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.backofficecloudapps.prop.inventory.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class activity_EnlargeImage extends Activity {
    ImageView image;
    String avatar;
    private ImageLoader imageLoader;
    private DisplayImageOptions imageOptions;
    Context context;
    List<String> stringList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enlarge_imageview);
        context=this;
        image = (ImageView) findViewById(R.id.enlargeimage);
        String photo1 = getIntent().getStringExtra("photo1");
        stringList= new ArrayList<>();
        stringList.add(photo1);
        ImageLoaderInit(context);
        image.setScaleType(ScaleType.CENTER_CROP);
        imageLoader.displayImage("file://" + Uri.parse(this.stringList.get(0)), image, imageOptions);
        avatar = getIntent().getStringExtra("avatar");
        image.setScaleType(ScaleType.FIT_XY);


    }

    /**
     *
     * @param context
     */
    private void ImageLoaderInit(@NonNull Context context){
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .memoryCacheSize(50 * 1024 * 1024)
                .memoryCacheSizePercentage(20)
                .diskCacheSize(300 * 1024 * 1024)
                .diskCacheFileCount(375)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();

        imageOptions=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .resetViewBeforeLoading(false)
                .delayBeforeLoading(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .handler(new Handler())
                .build();
        imageLoader= ImageLoader.getInstance();
        imageLoader.init(config);
    }

}