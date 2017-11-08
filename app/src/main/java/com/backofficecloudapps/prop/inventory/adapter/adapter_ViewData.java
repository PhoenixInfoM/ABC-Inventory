package com.backofficecloudapps.prop.inventory.adapter;

/**
 * Created by Phoenix
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.activity.activity_EnlargeImage;
import com.backofficecloudapps.prop.inventory.activity.activity_ViewSaveData;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The type Adapter view data.
 */
public class adapter_ViewData extends BaseExpandableListAdapter {

    @NonNull
    private final List<String> mylist;
    private int lastExpandedGroupPosition;
    @Nullable
    private ExpandableListView expandableListView;
    private LayoutInflater inflater;
    @Nullable
    private List<String> groupItems;
    private HashMap<String, List<String>> childItems;
    public int childCount = 0;
    @Nullable
    private Context context;
    private List<List<String>> firstLevelItems;
    HashMap<String, List<String>> firstLevelItems1;
    private List<String> endData;
    private HashMap<String, String> endHashmap;
    private List<List<String>> finishData;
    private int finishDataPosition;
    private int lengthCounter;
    private int someCounter;
    List< List<String>> PhotoList;
    @NonNull
    private List<String> demoDataList =  new ArrayList<String>();
    int counter=0;

    /**
     * Instantiates a new Adapter view data.
     *
     * @param context    the context
     * @param listView   the list view
     * @param groupItems the group items
     */
    public adapter_ViewData(@Nullable Context context, @Nullable ExpandableListView listView, @Nullable List<String> groupItems) {

        if (listView != null)
            expandableListView = listView;
        if (context != null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (groupItems != null && !groupItems.isEmpty())
            this.groupItems = groupItems;

        this.context = context;

        childItems = new HashMap<>();
        firstLevelItems1 = new HashMap<>();
        firstLevelItems = new ArrayList<>();
        endData = new ArrayList<>();
        endHashmap = new HashMap<>();
        finishData = new ArrayList<>();
        mylist = new ArrayList<>();


        ImageLoaderInit(context);
        lengthCounter = 0;
    }

    @Override
    public int getGroupCount() {
        return groupItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childCount;
    }

    @Nullable
    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Nullable
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;


        Holder vh = null;

        if (view == null) {
            vh = new Holder();
            view = inflater.inflate(R.layout.view_main_group_item, parent, false);
            vh.title = (TextView) view.findViewById(R.id.view_main_group_title);
            vh.arrow = (ImageView) view.findViewById(R.id.view_main_group_arrow);
            view.setTag(vh);
        } else
            vh = (Holder) view.getTag();

        if (isExpanded) {
            view.setPadding(0, 0, 0, 0);
            lastExpandedGroupPosition = groupPosition;
            vh.arrow.setImageResource(R.drawable.icon_down_10);
        } else {
            view.setPadding(0, 0, 0, 0);
            vh.arrow.setImageResource(R.drawable.icon_forward_10);
        }
        String s = groupItems.get(groupPosition);
        vh.title.setText(s);

        return view;
    }
    private class Holder {
        @Nullable
        ImageView arrow = null;
        LinearLayout photoLayout;
        ListView firstLevelItemsLV;
        @Nullable
        TextView title = null;
        ImageView firstPhoto;
        ImageView secondPhoto;
        ImageView thirdPhoto;

    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;

        Holder vh = null;
        if (view == null) {
            vh = new Holder();
            view = inflater.inflate(R.layout.view_main_child_item, parent, false);
            vh.title = (TextView) view.findViewById(R.id.view_main_child_item);


            vh.photoLayout = (LinearLayout) view.findViewById(R.id.view_main_child_bottom_layout);
            vh.firstLevelItemsLV = (ListView) view.findViewById(R.id.view_main_child_first_level_items);
             {
                vh.firstPhoto = (ImageView) view.findViewById(R.id.view_main_first_photo);

                vh.secondPhoto = (ImageView) view.findViewById(R.id.view_main_second_photo);

                vh.thirdPhoto = (ImageView) view.findViewById(R.id.view_main_third_photo);

                vh.thirdPhoto.setImageResource(R.drawable.default1);
                vh.secondPhoto.setImageResource(R.drawable.default1);
                vh.firstPhoto.setImageResource(R.drawable.default1);
            }
            view.setTag(vh);
        } else {
            vh = (Holder) view.getTag();
        }
        if (childItems.get(groupPosition + "") != null && !childItems.get(groupPosition + "").isEmpty()) {
            String s = "• " + childItems.get(groupPosition + "").get(childPosition) + " :";
            vh.title.setText(s);
        }
        try {
            for (int i = 0; i < firstLevelItems.get(childPosition).size(); i++) {
                if (finishData.size() > (lengthCounter)) {
                    if (finishData.get(lengthCounter).size() > someCounter) {
                        someCounter = finishData.get(lengthCounter).size() + 1;
                    }
                }
                    lengthCounter++;
            }
        }
        catch(Exception e){}

        someCounter = 0;

        List<String> dataAdapter=null;
        try{
            String data = activity_ViewSaveData.thirdData.get(childPosition);

            dataAdapter = new ArrayList<String>(Arrays.asList(data));
        }
        catch(Exception e){

        }

        List<String> dataAdapterPhotos=null;
        try{
            String dataphotos = activity_ViewSaveData.thirdPhotos.get(childPosition);

            dataAdapterPhotos = new ArrayList<String>(Arrays.asList(dataphotos));
        }
        catch(Exception e){

        }
        List<String> dataAdapterPhotos1= new ArrayList<String>();
        try{

            for(int w =0 ;w<dataAdapterPhotos.size();w++){
                String data = dataAdapterPhotos.get(w);
                data= data.substring(0,data.length()-1);
                dataAdapterPhotos1.add(data);
            }

        }catch (Exception e){

        }

       try {
            FirstLevelAdapter firstAdapter = new FirstLevelAdapter(context, firstLevelItems.get(0),dataAdapter,dataAdapterPhotos1);
            vh.firstLevelItemsLV.setAdapter(firstAdapter);
        }
        catch (Exception e){
          e.printStackTrace();
        }

        vh.firstLevelItemsLV.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(@NonNull View v, @NonNull MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        // v.getParent().requestDisallowInterceptTouchEvent(false);
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }

                v.onTouchEvent(event);
                return true;
            }
        });


        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(final int groupPosition) {
        if (groupPosition != lastExpandedGroupPosition) {
            expandableListView.collapseGroup(lastExpandedGroupPosition);
        }
        super.onGroupExpanded(groupPosition);
        lastExpandedGroupPosition = groupPosition;
    }

    /**
     * Sets child view.
     *
     * @param groupPosition1    the group position 1
     * @param firstLevelItems1  the first level items 1
     * @param secondLevelItems1 the second level items 1
     * @param thirdLevelInfo1   the third level info 1
     * @param photos1           the photos 1
     * @param demodesc1         the demodesc 1
     */
    public void setChildView(int groupPosition1, @Nullable List<String> firstLevelItems1, @Nullable List<List<String>> secondLevelItems1,
                             @Nullable List<String> thirdLevelInfo1, @Nullable List<List<String>> photos1, @Nullable List<String> demodesc1) {

        if (firstLevelItems1 != null && !firstLevelItems1.isEmpty()) {
            childCount = firstLevelItems1.size();
            this.childItems.put(groupPosition1 + "", firstLevelItems1);
        }
        if (secondLevelItems1 != null && !secondLevelItems1.isEmpty()) {
            this.firstLevelItems.clear();
            this.firstLevelItems.addAll(secondLevelItems1);

        }

        if (demodesc1 != null && !demodesc1.isEmpty()) {
            this.demoDataList.clear();
            this.demoDataList.addAll(demodesc1);
        }


        if (thirdLevelInfo1 != null && !thirdLevelInfo1.isEmpty()) {
            finishData.clear();
            finishData.add(thirdLevelInfo1);
        }

        PhotoList = new ArrayList<>();
        List<String> tempPhotoList = new ArrayList<>();

        if (photos1 != null && !photos1.isEmpty()) {

            PhotoList.clear();
            PhotoList.addAll(photos1);

        }
    }

    private class FirstLevelAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        @Nullable
        private List<String> items;
        private List<String> finishInnerList11;
        private List<String> photosarray;
        private Context _context;
        private SharedPreferences sharedPreferences;
        private String firstLevel;
        private ModelData md;
        private String s1;
        @NonNull
        HashMap<Integer, View> hashMap = new HashMap<Integer, View>();

        /**
         * Instantiates a new First level adapter.
         *
         * @param context    the context
         * @param objects    the objects
         * @param demo       the demo
         * @param dataPhotos the data photos
         */
        public FirstLevelAdapter(@NonNull Context context, @Nullable List<String> objects, List<String> demo, List<String> dataPhotos) {
            if (objects != null) {
                items = objects;
                finishInnerList11= demo;
                photosarray=dataPhotos;
            }

            _context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return finishInnerList11.size();
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Nullable
        @Override
        public View getView(final int position, @Nullable View convertView, ViewGroup parent) {

            final ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = inflater.inflate(R.layout.view_main_child_second_item, null);
                vh.item = (TextView) convertView.findViewById(R.id.child_second_item);
                vh.item.setTag(position);
                vh.elements = (ListView) convertView.findViewById(R.id.child_third_level_items);
                vh.firstPhoto = (ImageView) convertView.findViewById(R.id.view_main_first_photo);
                vh.secondPhoto = (ImageView) convertView.findViewById(R.id.view_main_second_photo);
                vh.thirdPhoto = (ImageView) convertView.findViewById(R.id.view_main_third_photo);


                convertView.setTag(vh);
            } else  {

                vh = (ViewHolder) convertView.getTag();
            }

            vh.thirdPhoto.setVisibility(View.VISIBLE);
            vh.secondPhoto.setVisibility(View.VISIBLE);
            vh.firstPhoto.setVisibility(View.VISIBLE);

            vh.thirdPhoto.setImageResource(R.drawable.default1);
            vh.secondPhoto.setImageResource(R.drawable.default1);
            vh.firstPhoto.setImageResource(R.drawable.default1);

            String s = finishInnerList11.get(position);
            s = s.replace("|", ",");
            String splitData[] = s.split(",");

            String refineSplitData="";
            for(int z=0;z<splitData.length;z++){

                // code for heading ....
                if(z == 0){
                    if(splitData[z].length()>0){
                        if(splitData.length >1){
                            refineSplitData +="     • "+splitData[z]+"\n";
                        }
                        else{
                            refineSplitData +="     "+splitData[z];
                        }

                    }
                }
                // code for other...
                else{
                    if(splitData[z].length()>0){
                        if(splitData.length >1){
                            refineSplitData +="       • "+splitData[z]+"\n";
                        }
                        else{
                            refineSplitData +="       "+splitData[z];
                        }

                    }

                }

            }

            vh.item.setText(""+refineSplitData);


            try {
                final int size =photosarray.size();

                if (vh.firstPhoto != null) {
                    vh.firstPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if ( size>= 1) {
                                String photo1 = photosarray.get(0);
                                if(!photo1.equals("")&&photo1!=null)
                                {
                                    Intent intent = new Intent(context, activity_EnlargeImage.class);
                                    intent.putExtra("photo1", photo1);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);}
                                else
                                    Toast.makeText(context, "New image to show", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "New image to show", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                if (vh.secondPhoto != null) {
                    vh.secondPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (size >= 2) {
                                String photo1 = photosarray.get(1);
                                if(!photo1.equals("")&&photo1!=null)
                                {
                                    Intent intent = new Intent(context, activity_EnlargeImage.class);

                                    intent.putExtra("photo1", photo1);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);}
                                else
                                    Toast.makeText(context, "New image to show", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(context, "New image to show", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                if (vh.thirdPhoto != null) {
                    vh.thirdPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (size >= 3) {
                                String photo1 = photosarray.get(2);
                                if(!photo1.equals("")&&photo1!=null)
                                {
                                    Intent intent = new Intent(context, activity_EnlargeImage.class);

                                    intent.putExtra("photo1", photo1);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);}
                                Toast.makeText(context, "New image to show", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "New image to show", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
                }

                /*************code for set image ************************/
                //check arrary have multiple image or not.
                if(photosarray.get(0).contains("|")){
                    String photo = photosarray.get(0).replace("|", ",");
                    String snapArray[] = photo.split(",");
                    for(int d=0 ;d<snapArray.length;d++){
                        photosarray.add(d,snapArray[d]);
                    }


                }
                /*************code for set image ************************/

                if (vh.firstPhoto != null) {
                    if (photosarray.size() >= 1) {
                        vh.firstPhoto.setImageResource(0);
                        if (!photosarray.isEmpty()) {
                            vh.firstPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            imageLoader.displayImage("file://" + Uri.parse(photosarray.get(0)), vh.firstPhoto, imageOptions);
                        }
                    } else {

                        vh.firstPhoto.setImageResource(R.drawable.default1);
                    }
                }

                if (vh.secondPhoto != null) {
                    if (photosarray.size() >= 2) {
                        if (!photosarray.isEmpty()) {
                            vh.secondPhoto.setImageResource(0);
                            vh.secondPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            imageLoader.displayImage("file://" + Uri.parse(photosarray.get(1)), vh.secondPhoto, imageOptions);
                        }
                    } else {
                              vh.secondPhoto.setImageResource(R.drawable.default1);
                    }
                }
                if (vh.thirdPhoto != null) {
                    if (photosarray.size() >= 3) {
                        if (!photosarray.isEmpty()) {
                            vh.thirdPhoto.setImageResource(0);
                            vh.thirdPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            imageLoader.displayImage("file://" + Uri.parse(photosarray.get(2)), vh.thirdPhoto, imageOptions);
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

        /**
         * The type Model data.
         */
        public class ModelData {

            @NonNull
            public String finalFirtstLevel = "";
            @NonNull
            public String finalFirtstLevel1 = "";
        }
        private class ViewHolder {

            TextView item;
            ListView elements;
            ImageView firstPhoto;
            ImageView secondPhoto;
            ImageView thirdPhoto;
        }
    }

    private class FinishDataAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private List<String> items;

        /**
         * Instantiates a new Finish data adapter.
         *
         * @param objects the objects
         */
        public FinishDataAdapter(List<String> objects) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            items = objects;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.view_main_child_finish_item, parent, false);
            TextView item = (TextView) view.findViewById(R.id.child_finish_item);
            item.setText("• " + items.get(position));
            return view;
        }
    }

    private ImageLoader imageLoader;
    private DisplayImageOptions imageOptions;

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
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }


}

