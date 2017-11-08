package com.backofficecloudapps.prop.inventory.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.database.TableInventories;
import com.backofficecloudapps.prop.inventory.database.db_PdfView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import static com.backofficecloudapps.prop.inventory.database.db_PdfView.MULTIID;

/**
 * Created by Phoenix
 */
public class activity_ViewDataByParentClick extends Activity {

    private ListView listView;
    private Context mContext;
    private TextView txtViewNew;
    List<TableInventories> firstLevelItems;
    List<String> dbDataHolder;
    public final String TAG = activity_ViewDataByParentClick.class.getSimpleName();
    private ImageLoader imageLoader;
    private DisplayImageOptions imageOptions;
    @NonNull
    db_PdfView dbPdfView = new db_PdfView(activity_ViewDataByParentClick.this);

    String photosSplit[];

    private List<TableInventories> dbHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_view_file);

        mContext = activity_ViewDataByParentClick.this;
        listView = (ListView) findViewById(R.id.listNewView);
        txtViewNew = (TextView) findViewById(R.id.textViewNew11);

        firstLevelItems =  new ArrayList<TableInventories>();
        dbDataHolder = new ArrayList<String>();
        dbHolder = new ArrayList<TableInventories>();

        // set value at textView
        txtViewNew.setText("" + activity_ViewSaveData.zerolvlItemvalue);

        dbPdfView.Open();
        Cursor cf;
        cf = dbPdfView.fetchDataforFirstlvl(activity_ViewSaveData.propertyID, activity_ViewSaveData.zerolvlItemvalue, activity_ViewSaveData.inventoryDate.replace("-",""));

        while(cf.moveToNext()){

            /**
             * code for manage data with flag by level
             *l
             */
            String CheckMultiID = cf.getString(cf.getColumnIndex(dbPdfView.MULTI_S_FLAG));
            String Level = cf.getString(cf.getColumnIndex(dbPdfView.LEVEL));
            String FirstLevelName= cf.getString(cf.getColumnIndex(dbPdfView.FIRSTLEVELITEM));
            String SecondLevelName=  cf.getString(cf.getColumnIndex(dbPdfView.SECONDLEVELITEM));
            String MultiFlag_ID=  cf.getString(cf.getColumnIndex(MULTIID));

            if(Level.equalsIgnoreCase("2")){
                if(CheckMultiID.equalsIgnoreCase("Y")){
                    FirstLevelName = FirstLevelName+": "+MultiFlag_ID;
                }
            }
            else if(Level.equalsIgnoreCase("3")){
                if(CheckMultiID.equalsIgnoreCase("Y")){
                    SecondLevelName = SecondLevelName+": "+MultiFlag_ID;
                }

            }

            dbHolder.add(new TableInventories(cf.getString(0),cf.getString(2),cf.getString(3),FirstLevelName,SecondLevelName,cf.getString(6),cf.getString(1)));


        }

        dbPdfView.close();

        // set Adapter of showing data
        FirstLevelAdapter firstAdapter = new FirstLevelAdapter(mContext,dbHolder);
        listView.setAdapter(firstAdapter);

        ImageLoaderInit(mContext);
    }

    private class FirstLevelAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<TableInventories> dataAll;
        private Context _context;

        /**
         * Instantiates a new First level adapter.
         *
         * @param context the context
         * @param mData   the m data
         */
        public FirstLevelAdapter(@NonNull Context context, List<TableInventories> mData) {

             dataAll = new ArrayList<TableInventories>();

             dataAll = mData;
            _context = context;
            photosSplit = new String[3];
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {

         return dataAll.size();

        }

        @Override
        public TableInventories getItem(int position) {
            return dataAll.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Nullable
        @Override
        public final View getView(final int position, @Nullable View convertView, ViewGroup parent) {

            final ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = inflater.inflate(R.layout.custom_view_extends, null);

                vh.txtFirst = (TextView) convertView.findViewById(R.id.custom_view_first_txt);
                vh.txtSecond = (TextView) convertView.findViewById(R.id.custom_view_second_txt);
                vh.txtThird = (TextView) convertView.findViewById(R.id.custom_view_third_txt);

                vh.firstPhoto = (ImageView) convertView.findViewById(R.id.view_main_first_photo);
                vh.secondPhoto = (ImageView) convertView.findViewById(R.id.view_main_second_photo);
                vh.thirdPhoto = (ImageView) convertView.findViewById(R.id.view_main_third_photo);

                convertView.setTag(vh);
            } else {

                vh = (ViewHolder) convertView.getTag();
            }

            vh.thirdPhoto.setImageResource(R.drawable.default1);
            vh.secondPhoto.setImageResource(R.drawable.default1);
            vh.firstPhoto.setImageResource(R.drawable.default1);

            TableInventories mdata = getItem(position);

            String data = "";

            String firstlvlData = dataAll.get(position).getFIRSTLEVELITEM();
            String SecondlvlData = dataAll.get(position).getSECONDLEVELITEM();
            String ThirdLvlData = dataAll.get(position).getTHIRDLEVELDATA();
            String Photos = mdata.getINVENTORYITEMPHOTOS();

            if(ThirdLvlData.length()>0)
            ThirdLvlData = ThirdLvlData.substring(0,ThirdLvlData.length()-1);

            if(Photos.length()>0)
            Photos= Photos.substring(0,Photos.length()-1);

            try{

                ThirdLvlData = ThirdLvlData.replace("|", ",");
                String thirdData[] = ThirdLvlData.split(",");
                String values[]= new String[thirdData.length];

                if(ThirdLvlData.length()>0){
                    if(ThirdLvlData.contains(",")) {
                        for (int y = 0; y < thirdData.length; y++) {
                            values[y] = thirdData[y];
                        }
                    }
                    else{
                        values[0]=ThirdLvlData;
                    }
                }

                if(values.length>1){
                    for(int t = 0; t<values.length;t++){
                        data = data+"• "+values[t]+"\n";
                    }
                }
                else{
                    data= "• "+values[0];
                }

            }
            catch (Exception e){

            }

            vh.txtFirst.setText(firstlvlData);

            if(SecondlvlData.length()>0){
                vh.txtSecond.setVisibility(View.VISIBLE);
                vh.txtSecond.setText("• "+SecondlvlData);
                vh.txtThird.setText(data);

            }
            else{
                vh.txtSecond.setVisibility(View.GONE);
                vh.txtSecond.setText("• "+SecondlvlData);
                vh.txtThird.setText(data);
            }




            try{
                if(Photos.length()>1) {
                    Photos = Photos.replace("|", ",");
                    photosSplit = Photos.split(",");
                }
                else{
                    photosSplit[0]=Photos;
                    photosSplit[1]=Photos;
                    photosSplit[2]=Photos;
                }
            }
            catch (Exception e) {

            }

            try {
                final int size = photosSplit.length;

                if (vh.firstPhoto != null) {
                    vh.firstPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            getPhotos(position);
                            if (size >= 1) {
                                String photo1 = photosSplit[0];
                                if (!photo1.equals("") && photo1 != null) {
                                    Intent intent = new Intent(mContext, activity_EnlargeImage.class);
                                    intent.putExtra("photo1", photo1);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    mContext.startActivity(intent);
                                } else
                                    Toast.makeText(mContext, "New image to show", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(mContext, "New image to show", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                if (vh.secondPhoto != null) {
                    vh.secondPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPhotos(position);
                            if (size >= 2) {
                                String photo1 = photosSplit[1];
                                if (!photo1.equals("") && photo1 != null) {
                                    Intent intent = new Intent(_context, activity_EnlargeImage.class);
                                    intent.putExtra("photo1", photo1);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    _context.startActivity(intent);
                                } else
                                    Toast.makeText(_context, "New image to show", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(_context, "New image to show", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                if (vh.thirdPhoto != null) {
                    vh.thirdPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getPhotos(position);
                            if (size >= 3) {
                                String photo1 = photosSplit[2];
                                if (!photo1.equals("") && photo1 != null) {
                                    Intent intent = new Intent(_context, activity_EnlargeImage.class);
                                    intent.putExtra("photo1", photo1);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    _context.startActivity(intent);
                                }
                                Toast.makeText(_context, "New image to show", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(_context, "New image to show", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
                }


                if (vh.firstPhoto != null) {
                    if (size >= 1) {
                        vh.firstPhoto.setImageResource(0);
                        if(!photosSplit[0].isEmpty()) {
                            vh.firstPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            System.out.println("photo path when setting on imageview " + photosSplit[0] + "pos-" + position);


                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.init(ImageLoaderConfiguration.createDefault(_context));
                            imageLoader.displayImage("file://" + Uri.parse(photosSplit[0]), vh.firstPhoto);

                           //imageLoader.displayImage("file://" + Uri.parse(photosSplit[0]), vh.firstPhoto, imageOptions);


                        }else{
                            vh.firstPhoto.setImageResource(R.drawable.default1);
                        }
                    } else {
                        vh.firstPhoto.setImageResource(R.drawable.default1);
                    }
                }

                if (vh.secondPhoto != null) {
                    if (size >= 2) {
                            vh.secondPhoto.setImageResource(0);
                        if(!photosSplit[1].isEmpty()) {
                            vh.secondPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            System.out.println("photo path when setting on imageview " + photosSplit[1]);

                           // imageLoader.displayImage("file://" + Uri.parse(photosSplit[1]), vh.secondPhoto, imageOptions);

                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.init(ImageLoaderConfiguration.createDefault(_context));
                            imageLoader.displayImage("file://" + Uri.parse(photosSplit[1]), vh.secondPhoto);

                        }else{
                            vh.secondPhoto.setImageResource(R.drawable.default1);
                        }
                    } else {
                            vh.secondPhoto.setImageResource(R.drawable.default1);
                    }
                }
                if (vh.thirdPhoto != null) {
                    if (size >= 3) {
                            vh.thirdPhoto.setImageResource(0);
                        if(!photosSplit[2].isEmpty()) {
                            vh.thirdPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            System.out.println("photo path when setting on imageview " + photosSplit[2]);

                           // imageLoader.displayImage("file://" + Uri.parse(photosSplit[2]), vh.thirdPhoto, imageOptions);

                            ImageLoader imageLoader = ImageLoader.getInstance();
                            imageLoader.init(ImageLoaderConfiguration.createDefault(_context));
                            imageLoader.displayImage("file://" + Uri.parse(photosSplit[2]), vh.thirdPhoto);

                          /*  Picasso.with(mContext).load("file://" +photosSplit[2])
                                    //.transform(new util.CircleTransform())
                                    //.placeholder(R.drawable.default1) // optional
                                    .error(R.drawable.default1)   // optional
                                    .into(vh.thirdPhoto);*/
                        }
                        else{
                            vh.thirdPhoto.setImageResource(R.drawable.default1);
                        }
                    } else {

                        vh.thirdPhoto.setImageResource(R.drawable.default1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return convertView;

        }

        private void getPhotos(int position) {

        TableInventories mdata = getItem(position);
        String Photos = mdata.getINVENTORYITEMPHOTOS();

            if(Photos.length()>0)
                Photos= Photos.substring(0,Photos.length()-1);

            try{
                if(Photos.length()>0) {
                    Photos = Photos.replace("|", ",");
                    photosSplit = Photos.split(",");

                }
            }
            catch (Exception e) {

            }
        }
    }
        private class ViewHolder {

            TextView txtFirst;
            TextView txtSecond;
            TextView txtThird;
            ImageView firstPhoto;
            ImageView secondPhoto;
            ImageView thirdPhoto;
        }

    private void ImageLoaderInit(@NonNull Context context) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .memoryCacheSize(50 * 1024 * 1024)
                .memoryCacheSizePercentage(20)
                .diskCacheSize(300 * 1024 * 1024)
                .diskCacheFileCount(375)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();

        imageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default1)
                .resetViewBeforeLoading(true)
                .delayBeforeLoading(0)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .handler(new Handler())
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
}
