package com.backofficecloudapps.prop.inventory.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.other.CustomNumberPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Phoenix
 */

public class adapter_FirstlvlDatabase extends BaseAdapter {

    @Nullable
    private List<String> items;
    private LayoutInflater inflater;
    @Nullable
    private Context context;
    private int layout;
    private int tView;
    private int mImageView;
    @Nullable
    private List<String> mCheckValues;
    @Nullable
    private List<String> types;
    public HashMap<String, String> hashMap;
    @Nullable
    private List<List<String>> values;

    /**
     * Instantiates a new Adapter firstlvl database.
     *  @param zeroLevelItems the zero level items
     * @param context        the context
     * @param resourceLayout the resource layout
     * @param resourceView   the resource view
     * @param checkValues         the mCheckValues
     * @param values
     * @param type           the type
     */
    public adapter_FirstlvlDatabase(@Nullable List<String> zeroLevelItems, @Nullable Context context, int resourceLayout, int resourceView, @Nullable List<String> checkValues, List<List<String>> values, @Nullable List<String> type, int mResourceImgLayout){
        hashMap = new HashMap<>();

        if(zeroLevelItems!=null&&!zeroLevelItems.isEmpty()&&context!=null){
          items       =zeroLevelItems;
          this.context=context;
          inflater    =(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        this.layout   =resourceLayout;
        this.tView    =resourceView;
        this.mImageView=mResourceImgLayout;

      this.mCheckValues = new ArrayList<>();

      if (checkValues != null && !checkValues.isEmpty()) {
          this.mCheckValues = checkValues;
      }

      this.values = new ArrayList<>();
     if (values != null && !values.isEmpty()) {
            this.values = values;
        }

      this.types = new ArrayList<>();
      if (type != null && !type.isEmpty()) {
          this.types = type;
      }
  }
  @Override
  public int getCount()
  {
	return items.size();
  }

  @Nullable
  @Override
  public Object getItem(final int position){

      return null;
  }

  @Override
  public long getItemId(final int position){

      return 0;
  }

  @Override
  public View getView(final int position, View convertView, final ViewGroup parent){

      CustomNumberPicker numberPicker = null;
      TextView description = null;
      ImageView imgIndicatior = null;
      String typvalue = "";

      try {
          typvalue = types.get(position);
      } catch (Exception e) {
          e.printStackTrace();
      }

      if (typvalue.contains("NUMBEROF")) {
          convertView = inflater.inflate(R.layout.third_number_lvl_items, parent, false);
          numberPicker = (CustomNumberPicker) convertView.findViewById(R.id.third_number_lvl_items_counter);
          description = (TextView) convertView.findViewById(R.id.third_number_lvl_items_name);
      }
      else{
          convertView =inflater.inflate(layout, parent, false);
          description=(TextView) convertView.findViewById(tView);
          imgIndicatior=(ImageView) convertView.findViewById(mImageView);


      }

      /**
       * use: change icon according to hold value
       * Date: 18-March-2017
       */
      try {
          if(mCheckValues.get(position).toString().equalsIgnoreCase("YES")){
              imgIndicatior.setImageResource(R.drawable.icon_down_10);
              convertView.setBackgroundColor(context.getResources().getColor(R.color.custom));
          }
          else{
              imgIndicatior.setImageResource(R.drawable.icon_forward_10);
              convertView.setBackgroundColor(context.getResources().getColor(R.color.custom_trans));
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      /////////////////////////////////////////

      /************set value in textView******************/
      String s = items.get(position);
      System.out.println("value to be set-" + s);

      if (description != null && items != null) {


          if (s == null) {
              Toast.makeText(context, "No Navigation data found.", Toast.LENGTH_SHORT).show();
              ((Activity) context).finish();
          } else
              description.setText("   ".concat(s));
      }
      /************set value in textView******************/

      /******************add value into numberpicker to hashmap********/
      if (numberPicker != null) {
          numberPicker.setMaxValue(100);
          numberPicker.setMinValue(0);
          numberPicker.setValue(0);
          numberPicker.setChangeListener(new CustomNumberPicker.ChangeListener() {
              @Override
              public void change(int newValue) {
                  hashMap.put(items.get(position), String.valueOf(newValue));
              }
          });
      }

      /******************add value into numberpicker to hashmap********/

	return convertView;
  }
}
