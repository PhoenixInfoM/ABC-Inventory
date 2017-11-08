package com.backofficecloudapps.prop.inventory.adapter;

/**
 * Created by Phoenix
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.database.DataBaseHelper;
import com.backofficecloudapps.prop.inventory.inventories.inventories_FifthLvl;
import com.backofficecloudapps.prop.inventory.inventories.inventories_FourthLvl;
import com.backofficecloudapps.prop.inventory.inventories.inventories_SecondLvl;
import com.backofficecloudapps.prop.inventory.inventories.inventories_ThirdLvl;
import com.backofficecloudapps.prop.inventory.other.CustomNumberPicker;
import com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Adapter custom db.
 */
public class adapter_CustomDB extends BaseAdapter {

    public boolean checkData;
    public int counter =0;
    private String LVL;
    private Context CONTEXT;
    private List<String> ITEMS= new ArrayList<>();
    public static HashMap<String, String> HASHMAP;
    private List<List<String>> VALUES = new ArrayList<>();
    private List<String> TYPES = new ArrayList<>();
    private List<String> DATATITLE = new ArrayList<>();
    private List<String> DATAVALUE = new ArrayList<>();
    private String finalvalueInValue = "";
    @Nullable
    private List<String> mCheckValues;

    /**
     * Instantiates a new Adapter custom db.
     *
     * @param context   the context
     * @param items     the items
     * @param values    the values
     * @param type      the type
     * @param lvl       the lvl
     * @param dataTitle the data title
     * @param dataValue the data value
     */
    public adapter_CustomDB(Context context, List<String> items, List<List<String>> values, List<String> type, String lvl, List<String> dataTitle, List<String> dataValue, List<String> checkDataExist) {

            HASHMAP= new HashMap<>();

            this.mCheckValues = new ArrayList<>();
            if (checkDataExist != null && !checkDataExist.isEmpty()) {
                this.mCheckValues = checkDataExist;
            }

            this.ITEMS = items;
            this.CONTEXT = context;
            this.VALUES = values;
            this.TYPES = type;
            this.LVL = lvl;
            this.DATATITLE = dataTitle;
            this.DATAVALUE = dataValue;

            checkData = true;
            counter = 0;

    }

    @Override
    public int getCount() {

        if(ITEMS != null && ITEMS.size() != 0){
            return ITEMS.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return ITEMS.get(position);
    }

    @Override
    public long getItemId( int position) {
        return position;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, final ViewGroup parent) {

        final ViewHolder holder;
        String itemlevel= "";


        if(convertView == null){


            holder = new ViewHolder();

            LayoutInflater newInflate = (LayoutInflater) CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = newInflate.inflate(R.layout.custom_new_db_adapter_layout,null);

            holder.editMain=(EditText) convertView.findViewById(R.id.custum_main_edit);
            holder.imgMain=(ImageView) convertView.findViewById(R.id.custum_main_img);
            holder.numMain=(CustomNumberPicker) convertView.findViewById(R.id.custum_main_custom_num_pic);
            holder.spinMain=(Spinner) convertView.findViewById(R.id.custum_main_spinner);
            holder.txtMain=(TextView) convertView.findViewById(R.id.custum_main_desc);


            convertView.setTag(holder);

        }else{
           holder = (ViewHolder) convertView.getTag();
        }




        // hide all data
        holder.editMain.setVisibility(View.GONE);
        holder.imgMain.setVisibility(View.GONE);
        holder.numMain.setVisibility(View.GONE);
        holder.spinMain.setVisibility(View.GONE);
        holder.txtMain.setVisibility(View.GONE);

        // use for hold position number
        holder.ref = position;

       // use for getting type value by which match data into hashmap
        try {
            holder.typeValue = TYPES.get(position);
        } catch (Exception e) {
                holder.typeValue="";
            e.printStackTrace();
        }

        // function use for check level number
        try {
            itemlevel= levelChecker(holder.typeValue);
        } catch (Exception e) {
            itemlevel="";
            e.printStackTrace();
        }


        String KEY = "";
        String VALUE = "";
        String FinalValue = "";

        /////////////////////////////////////////////////// check where user comes from level

        if(LVL.equals("2")) {
            for (Map.Entry<String, String> entry : inventories_SecondLvl.valueHolder.entrySet()) {

                KEY = entry.getKey().trim();//key name
                VALUE = entry.getValue().trim(); // key value

                if (KEY.equals(ITEMS.get(position).toString())) {
                    FinalValue = VALUE;
                    break;
                }
            }
        }
        if(LVL.equals("3")) {
            for (Map.Entry<String, String> entry : inventories_ThirdLvl.valueHolder.entrySet()) {

                KEY = entry.getKey().trim();//key name
                VALUE = entry.getValue().trim(); // key value

                if (KEY.equals(ITEMS.get(position).toString())) {
                    FinalValue = VALUE;
                    break;
                }
            }
        }
        if(LVL.equals("4")) {
            for (Map.Entry<String, String> entry : inventories_FourthLvl.valueHolder.entrySet()) {

                KEY = entry.getKey().trim();//key name
                VALUE = entry.getValue().trim(); // key value

                if (KEY.equals(ITEMS.get(position).toString())) {
                    FinalValue = VALUE;
                    break;
                }
            }
        }
        if(LVL.equals("5")) {
               for (Map.Entry<String, String> entry : inventories_FifthLvl.valueHolder.entrySet()) {

                KEY = entry.getKey().trim();//key name
                VALUE = entry.getValue().trim(); // key value

                if (KEY.equals(ITEMS.get(position).toString())) {
                    FinalValue = VALUE;
                    break;
                }
            }
        }

        if(itemlevel.equals("3"))
        {
            holder.editMain.setVisibility(View.GONE);
            holder.imgMain.setVisibility(View.VISIBLE);
            holder.numMain.setVisibility(View.GONE);
            holder.spinMain.setVisibility(View.GONE);
            holder.txtMain.setVisibility(View.VISIBLE);

        }
        else {

             try{

            if (holder.typeValue.contains("NUMBEROF")) {

                holder.editMain.setVisibility(View.GONE);
                holder.imgMain.setVisibility(View.GONE);
                holder.numMain.setVisibility(View.VISIBLE);
                holder.spinMain.setVisibility(View.GONE);
                holder.txtMain.setVisibility(View.VISIBLE);

            } else if (holder.typeValue.contains("EditText")) {

                holder.editMain.setVisibility(View.VISIBLE);
                holder.imgMain.setVisibility(View.GONE);
                holder.numMain.setVisibility(View.GONE);
                holder.spinMain.setVisibility(View.GONE);
                holder.txtMain.setVisibility(View.VISIBLE);

            } else if (holder.typeValue.contains("SIZEUNIT")) {

                holder.editMain.setVisibility(View.VISIBLE);
                holder.imgMain.setVisibility(View.GONE);
                holder.numMain.setVisibility(View.GONE);
                holder.spinMain.setVisibility(View.VISIBLE);
                holder.txtMain.setVisibility(View.VISIBLE);

            } else if (holder.typeValue.contains("THERMOSTAT")) {

                holder.editMain.setVisibility(View.GONE);
                holder.imgMain.setVisibility(View.VISIBLE);
                holder.numMain.setVisibility(View.GONE);
                holder.spinMain.setVisibility(View.GONE);
                holder.txtMain.setVisibility(View.VISIBLE);
            } else {
                //if value of -3 level is null or empty then set edit box instead of spinner
                for (int i = 0; i < VALUES.get(position).size(); i++) {
                    finalvalueInValue = VALUES.get(position).get(i);
                }

                if (holder.typeValue.equalsIgnoreCase("TOILETTYPE")) {

                    holder.editMain.setVisibility(View.GONE);
                    holder.imgMain.setVisibility(View.GONE);
                    holder.numMain.setVisibility(View.GONE);
                    holder.spinMain.setVisibility(View.VISIBLE);
                    holder.txtMain.setVisibility(View.VISIBLE);

                } else {

                    if (VALUES.get(position).size() == 0 || VALUES.isEmpty() || finalvalueInValue == null || finalvalueInValue.equals("")) {

                        if (holder.typeValue.contains("MANUFACTURER")) {
                            holder.editMain.setVisibility(View.GONE);
                            holder.imgMain.setVisibility(View.GONE);
                            holder.numMain.setVisibility(View.GONE);
                            holder.spinMain.setVisibility(View.VISIBLE);
                            holder.txtMain.setVisibility(View.VISIBLE);
                        }
                        else{
                            holder.editMain.setVisibility(View.VISIBLE);
                            holder.imgMain.setVisibility(View.GONE);
                            holder.numMain.setVisibility(View.GONE);
                            holder.spinMain.setVisibility(View.GONE);
                            holder.txtMain.setVisibility(View.VISIBLE);
                        }


                    } else {

                        holder.editMain.setVisibility(View.GONE);
                        holder.imgMain.setVisibility(View.GONE);
                        holder.numMain.setVisibility(View.GONE);
                        holder.spinMain.setVisibility(View.VISIBLE);
                        holder.txtMain.setVisibility(View.VISIBLE);
                    }

                }
            }

        }catch(Exception e){
                 if(ITEMS.get(position).equals("Other")){
                     holder.editMain.setVisibility(View.VISIBLE);
                     holder.imgMain.setVisibility(View.GONE);
                     holder.numMain.setVisibility(View.GONE);
                     holder.spinMain.setVisibility(View.GONE);
                     holder.txtMain.setVisibility(View.VISIBLE);
                 }
             }
        }


        //////////////////////////////////////////////// VISIBLE LAYOUT according to condition

        /*************************************set value into textview, sppiner, edittext and number picker************************/

        /////////////////////////////////////////// code for text view
        String s = ITEMS.get(position);
        holder.txtMain.setText("   ".concat(s));
        /////////////////////////////////////////// code for text view

        /////////////////////////////////////////// code for edit text

        try {
            if(holder.typeValue.contains("SIZEUNIT")){
                holder.editMain.setText("" + FinalValue.substring(0,FinalValue.indexOf(":")));
            }
            else{
                holder.editMain.setText("" + FinalValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.editMain.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                    if(LVL.equals("2")){

                        try {
                            if(holder.typeValue.contains("SIZEUNIT")){
                                    if(holder.spinMain.getSelectedItem().toString().length()>0 && !holder.spinMain.getSelectedItem().toString().equals("null")) {
                                        inventories_SecondLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                        HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                    }
                                    else{
                                        inventories_SecondLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                        HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                    }

                            }
                            else{
                                inventories_SecondLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                            inventories_SecondLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                            HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                        }


                    }
                    if(LVL.equals("3")){

                        try {
                            if(holder.typeValue.contains("SIZEUNIT")){
                                if(holder.spinMain.getSelectedItem().toString().length()>0 && !holder.spinMain.getSelectedItem().toString().equals("null")) {

                                    inventories_ThirdLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                }
                                else{
                                    inventories_ThirdLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                }

                            }
                            else{

                                inventories_ThirdLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                            inventories_ThirdLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                            HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                        }

                    }
                    if(LVL.equals("4")){

                        try {
                            if(holder.typeValue.contains("SIZEUNIT")){
                                if(holder.spinMain.getSelectedItem().toString().length()>0 && !holder.spinMain.getSelectedItem().toString().equals("null")) {
                                    inventories_FourthLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                }
                                else{
                                    inventories_FourthLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                }

                            }
                            else{
                                inventories_FourthLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            inventories_FourthLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                            HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                        }

                    }
                    if(LVL.equals("5")){

                        try {
                            if(holder.typeValue.contains("SIZEUNIT")){
                                if(holder.spinMain.getSelectedItem().toString().length()>0 && !holder.spinMain.getSelectedItem().toString().equals("null")) {
                                    inventories_FifthLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + holder.spinMain.getSelectedItem().toString());
                                }
                                else{
                                    inventories_FifthLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString() + ":" + "m");
                                }

                            }
                            else{
                                inventories_FifthLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                            inventories_FifthLvl.valueHolder.put(ITEMS.get(holder.ref),holder.editMain.getText().toString());
                            HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());
                        }
                    }

            }
        });

        String curData = "";
        try {
            curData = holder.editMain.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //// get remainig data from edit text
        if(curData.length()>0 && !(curData.equals("null"))){
            HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString());

        }

        /////////////////////////////////////////// code for edit text

        //////////////////////////////////////////// code for number picker
        if ( holder.numMain != null) {
            holder.numMain.setMaxValue(100);
            holder.numMain.setMinValue(0);
            holder.numMain.setValue(0);
            holder.numMain.setChangeListener(new CustomNumberPicker.ChangeListener() {
                @Override
                public void change(int newValue) {

                        if(LVL.equals("2")){
                            inventories_SecondLvl.valueHolder.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                            HASHMAP.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                        }
                        if(LVL.equals("3")){
                            inventories_ThirdLvl.valueHolder.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                            HASHMAP.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                        }
                        if(LVL.equals("4")){
                            inventories_FourthLvl.valueHolder.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                            HASHMAP.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                        }
                        if(LVL.equals("5")){
                            inventories_FifthLvl.valueHolder.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                            HASHMAP.put(ITEMS.get(holder.ref), String.valueOf(newValue));
                        }

                }
            });

        try {
              holder.numMain.setValue(Integer.parseInt(FinalValue));
        } catch (Exception e) {
                    e.printStackTrace();
        }

            // code use for send previous data into database.after one data selected.
            if ( holder.numMain != null) {
                int numberValue = holder.numMain.getValue();
                if(numberValue != 0){
                    HASHMAP.put(ITEMS.get(holder.ref), String.valueOf(numberValue));
                }
            }
        }

        //////////////////////////////////////////// code for number picker

        /////////////////////////////////////////// code for spinner

        if (holder.spinMain != null) {
            holder.spinMain.setAdapter(new ArrayAdapter<>(CONTEXT, R.layout.spinner_t_view, VALUES.get(position)));

            holder.spinMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        if(LVL.equals("2")){
                            if(holder.typeValue.contains("SIZEUNIT")){
                                if(holder.editMain.getText().toString().length()>0){

                                    inventories_SecondLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                }
                            }
                            else{
                                inventories_SecondLvl.valueHolder.put(ITEMS.get(holder.ref),holder.spinMain.getSelectedItem().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.spinMain.getSelectedItem().toString());
                            }
                                                 }

                        if(LVL.equals("3")){

                            if(holder.typeValue.contains("SIZEUNIT")){
                                if(holder.editMain.getText().toString().length()>0){

                                    inventories_ThirdLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                }
                            }
                            else{

                                inventories_ThirdLvl.valueHolder.put(ITEMS.get(holder.ref),holder.spinMain.getSelectedItem().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.spinMain.getSelectedItem().toString());
                            }

                                      }
                        if(LVL.equals("4")){

                            if(holder.typeValue.contains("SIZEUNIT")){
                                if(holder.editMain.getText().toString().length()>0){

                                    inventories_FourthLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                }
                            }
                            else{
                                inventories_FourthLvl.valueHolder.put(ITEMS.get(holder.ref),holder.spinMain.getSelectedItem().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.spinMain.getSelectedItem().toString());
                            }
                                           }
                        if(LVL.equals("5")){

                            if(holder.typeValue.contains("SIZEUNIT")){
                                if(holder.editMain.getText().toString().length()>0){

                                    inventories_FifthLvl.valueHolder.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                    HASHMAP.put(ITEMS.get(holder.ref), holder.editMain.getText().toString()+":"+holder.spinMain.getSelectedItem().toString());
                                }
                            }
                            else{
                                inventories_FifthLvl.valueHolder.put(ITEMS.get(holder.ref),holder.spinMain.getSelectedItem().toString());
                                HASHMAP.put(ITEMS.get(holder.ref), holder.spinMain.getSelectedItem().toString());
                            }
                                           }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            for (int y = 0; y < VALUES.get(holder.ref).size(); y++) {

                if(holder.typeValue.contains("SIZEUNIT")){
                        if (VALUES.get(position).get(y).trim().equals(FinalValue.trim().substring(FinalValue.indexOf(":")+1))) {
                        holder.spinMain.setSelection(y);
                    }
                }
                else{
                    if (VALUES.get(position).get(y).trim().equals(FinalValue.trim())) {

                        if(FinalValue.length()>0){
                            holder.spinMain.setSelection(y);
                        }
                        else{
                            holder.spinMain.setSelection(0);
                        }

                    }

                }


            }


        }

        ////////////////////////////////////////// code for spinner

        /**
         * use: change icon according to hold value
         * Date: 18-March-2017
         */
        try {
            if(mCheckValues.get(position).toString().equalsIgnoreCase("YES")){
                holder.imgMain.setImageResource(R.drawable.icon_down_10);
                convertView.setBackgroundColor(CONTEXT.getResources().getColor(R.color.custom));

            }
            else{
                holder.imgMain.setImageResource(R.drawable.icon_forward_10);
                convertView.setBackgroundColor(CONTEXT.getResources().getColor(R.color.custom_trans));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /////////////////////////////////////////

        /*************************************set value into textview, sppiner, edittext and number picker************************/

        return convertView;
    }
    private String levelChecker(@Nullable String feature) {

        DataBaseHelper helper = new DataBaseHelper();
        SQLiteDatabase database = helper.getExternalDataBase(CONTEXT);

        if(feature == null){
            feature ="";
        }

        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_lvl)).concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_category).concat("='".concat(feature.concat("'"))), null);

        String itemLevel = "";
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                itemLevel = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_lvl));
                cursor.moveToNext();
            }

        cursor.close();

        }
        return itemLevel;
    }

    /**
     * The type View holder.
     */
    public class ViewHolder {

        private EditText editMain;
        private CustomNumberPicker numMain;
        private ImageView imgMain;
        private TextView txtMain;
        private Spinner spinMain;
        int ref;
        String typeValue="";
    }

}
