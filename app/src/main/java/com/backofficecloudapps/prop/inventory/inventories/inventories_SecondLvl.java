package com.backofficecloudapps.prop.inventory.inventories;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.adapter.adapter_CustomDB;
import com.backofficecloudapps.prop.inventory.database.DataBaseHelper;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.database.db_MainStatic;
import com.backofficecloudapps.prop.inventory.database.db_PdfView;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.backofficecloudapps.prop.inventory.utils.Constantaz._3rdScreenflag;
import static com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase.inventory_item_features;
import static com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase.inventory_item_multi_flag;

/**
 * Created by Phoenix
 *
 * ** rember: remove multiid concept for "getDataExistOrNotlevel2"
 */
public class inventories_SecondLvl extends ActionBarActivity {


    private ListView secondLvlListView;
    private List<String> categoriesList;
    private List<String> itemCategoriesCode;
    private List<String> itemDataExist;

    @NonNull
    private String keySpinner = "";
    public static HashMap<String, String> map;
    @NonNull
    List<List<String>> lists = new ArrayList<>();
    private List<String> spinnStringItem;
    private int photoID = 0;
    private String photoPath;
    private String[] imagePath;
    private String[] imagePath1;
    private String[] imagePath2;
    private PreferencesManager sPrefs;
    private String[] levelNames;
    private String me;
    private Button saveBT;
    private Button cameraBT;
    private Button txtBallon;
    private String dateG;
    private TextView propName;
    private TextView propDate;
    private TextView currentItem;


    // use in previous code fetching level of data
    private DataBaseHelper helper;
    @Nullable
    private SQLiteDatabase database;
    //////////////////////////////

    @NonNull
    db_MainStatic dbMain = new db_MainStatic(this);
    @NonNull
    db_DesireOutput dbClient = new db_DesireOutput(this);
    @NonNull
    db_PdfView dbPdfView = new db_PdfView(this);
    String imgPath[];
    Integer holder[];
    private boolean onTimeInsertData;
    private boolean insertData;
    private String propertyID = "";
    String zeroLevelItemName = "";
    String firstLevelItemName = "";
    private String inventoryCreationDate1;
    private String[] imgPathsplit;
    private String accountId;
    private String dataInput3;
    private String data;

    String Zero_Item_Code = "";
    String Zero_Item_Item_Features = "";
    String One_Item_Code = "";
    String One_Item_Item_Features = "";

    private String Two_Item_Code="";
    private String Two_Item_Item_Features="";

    private String Three_Item_Code="";
    private String Three_Item_Item_Features="";

    private String Desc_Item_Code="";
    private String Desc_Item_level="";

    public boolean checkDataUpdate;
    private boolean snapChecker = false;
    public int lastItem;
    private adapter_CustomDB adapter;
    public List<String> DataTitle ;
    public List<String> DataValue ;
    public static HashMap<String,String> valueHolder;
    public List<String> currentDescriptions;

    public String inventoryDate="";
    String previousSelItem="";

    String checkDataYorN="";
    private Button newBT;
    private Button deleteBT;

    private boolean checkCameraUpdate = true;

    private boolean statusMultiPart = false;
    private int mValueMultiPart = 0;
    private RelativeLayout relMultiSArrow;
    private ImageView imgLeftSwip;
    private ImageView imgRightSwip;
    private String intentValue1="";
    private String intentValue2="";
    private String intentValue3="";
    private String intentValue4="";
    private String mScreenMultiPart="0";

   // public static String mSecondScreenFlag="1";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkCameraUpdate = true;
        statusMultiPart= false;

        callFxnForStoreINtentData();
        callFxnFor_setContent();
        callFxnForHideVisibleNewbtn();
        initialSetUp(this, previousSelItem);
        callFxn_for_set_listData(previousSelItem);
        onCLickLeftRIghtArrow();

    }

    private void callFxnForStoreINtentData() {
        try {
            intentValue1 = getIntent().getStringExtra(ConstantazDataBase.inventory_item_features);
            intentValue2 = getIntent().getStringExtra(Constantaz.selected_inv_name);
            intentValue3 = getIntent().getStringExtra("shared1");
            intentValue4 = getIntent().getStringExtra("multiscreencounter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onCLickLeftRIghtArrow() {

        if(Integer.parseInt(intentValue4) == 1){
            imgLeftSwip.setImageResource(R.drawable.ic_left_arrow_grey);
        }
        else{
            imgLeftSwip.setImageResource(R.drawable.ic_left_arrow);
        }

        if(!((Integer.parseInt(intentValue4)+1) == mValueMultiPart)){
            imgRightSwip.setImageResource(R.drawable.ic_right_arrow_grey);
        }
        else{
            imgRightSwip.setImageResource(R.drawable.ic_right_arrow);
        }


        imgLeftSwip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // check current title or pckage #.

                if(Integer.parseInt(intentValue4) == 1){
                    onBackPressed();
                }
                else{
                    Intent intent=new Intent(inventories_SecondLvl.this, inventories_SecondLvl.class);
                    intent.putExtra(ConstantazDataBase.inventory_item_features, intentValue1);
                    intent.putExtra(Constantaz.selected_inv_name,intentValue2);
                    intent.putExtra("shared1",intentValue3) ;
                    intent.putExtra("multiscreencounter",""+(Integer.parseInt(intentValue4)-1));
                    startActivity(intent);
                    finish();
                }


            }
        });



        imgRightSwip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // check current title or pacage #

                if((Integer.parseInt(intentValue4)+1)<= mValueMultiPart){
                    Intent intent=new Intent(inventories_SecondLvl.this, inventories_SecondLvl.class);
                    intent.putExtra(ConstantazDataBase.inventory_item_features, intentValue1);
                    intent.putExtra(Constantaz.selected_inv_name,intentValue2);
                    intent.putExtra("shared1",intentValue3) ;
                    intent.putExtra("multiscreencounter",""+(Integer.parseInt(intentValue4)+1));
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(inventories_SecondLvl.this, "Data not found", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void callFxnForHideVisibleNewbtn() {
        /**
         * use for check multiscreen or not
         */
        statusMultiPart = checkDBExistYorN(getIntent().getStringExtra(inventory_item_features));

        if(statusMultiPart){
            newBT.setVisibility(View.VISIBLE);
            deleteBT.setVisibility(View.VISIBLE);
            relMultiSArrow.setVisibility(View.VISIBLE);
        }
        else{
            newBT.setVisibility(View.GONE);
            deleteBT.setVisibility(View.GONE);
            relMultiSArrow.setVisibility(View.GONE);
        }
        //////////////////////
    }

    private void callFxnFor_setContent() {
        setContentView(R.layout.inventories_second_lvl);

        /****mapping with xml layout**********/
        propName = (TextView) findViewById(R.id.inventories_fourth_lvl_prop_name);
        propDate = (TextView) findViewById(R.id.inventories_fourth_lvl_current_date);
        currentItem = (TextView) findViewById(R.id.inventories_fourth_lvl_current_item);
        saveBT = (Button) findViewById(R.id.saveBT);
        cameraBT = (Button) findViewById(R.id.cameraBT);
        newBT = (Button) findViewById(R.id.newBT);
        deleteBT = (Button) findViewById(R.id.deleteBT);
        secondLvlListView = (ListView) findViewById(R.id.inventories_second_lvl_list_view);

        txtBallon = (Button) findViewById(R.id.txtBallon);

        relMultiSArrow = (RelativeLayout) findViewById(R.id.rel_multi_screen_arrow);
        imgLeftSwip = (ImageView) findViewById(R.id.leftArrow);
        imgRightSwip =(ImageView) findViewById(R.id.rightArrow);

        /****mapping with xml layout**********/

        txtBallon.setVisibility(View.GONE);
        snapChecker = false;
        checkDataUpdate = true;

        /****create object**********/

        valueHolder = new LinkedHashMap<>();
        map = new LinkedHashMap<>();
        categoriesList = new ArrayList<>();
        itemCategoriesCode=new ArrayList<>();
        itemDataExist= new ArrayList<>();

        helper = new DataBaseHelper();
        database = helper.getExternalDataBase(getApplicationContext());
        sPrefs = new PreferencesManager(this);
        imgPath = new String[3];
        holder = new Integer[3];
        onTimeInsertData = true;
        insertData = true;
        /****create object**********/

        //@desc: getting previous InventoryName with getExtra
        previousSelItem = getIntent().getStringExtra(Constantaz.selected_inv_name);

        //@desc: hold array "previous Name>Current Name" format and after split with ">"
        levelNames = previousSelItem.split(" > ");

        //@desc: getting previous "Previous Inventory Name + Current Inventory Name" with getExtra
        final String shared12 = getIntent().getStringExtra("shared1");

        //@desc: use for hold selected image with camera.
        //@desc: create object with 3 item
        imagePath = new String[]{"", "", ""};
        imagePath1 = new String[]{"", "", ""};
        imagePath2 = new String[]{"", "", ""};


    }


    @Override
    protected void onRestart() {

        if(_3rdScreenflag == 1){
            _3rdScreenflag= 0;


            checkCameraUpdate = true;
            statusMultiPart= false;

            callFxnForStoreINtentData();
            callFxnFor_setContent();
            callFxnForHideVisibleNewbtn();
            initialSetUp(this, previousSelItem);
            callFxn_for_set_listData(previousSelItem);
            onCLickLeftRIghtArrow();
        }

        super.onRestart();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    private void callFxn_for_set_listData(final String previousSelItem) {

        //@desc: use for initalize string List , it will hold all "two level" item category list
        //@desc: use function getSecondLvlItems for getting data from database table
        final List<String> currentDescriptions = callFxnForUpdateDB();
        if (currentDescriptions == null) return;


        callFxnForUpdateAdapter(currentDescriptions);


        adapter = new adapter_CustomDB(this, currentDescriptions, lists, categoriesList, "2",DataTitle,DataValue,itemDataExist);

        secondLvlListView.setAdapter(adapter);

        /************working on listview with adapter and item click listener***************/
        secondLvlListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        secondLvlListView.setBackgroundColor(getResources().getColor(R.color.custom));
        secondLvlListView.setDividerHeight(7);
        secondLvlListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                ArrayList<String> tempList = new ArrayList<String>();
          /*
            * @desc: putExtra: three parameter: flagforthird lavel, item_category,bothInventoryName(Previous+New)
            * @param: ConstantazDataBase.inventory_item_category -> hold "item category"
            * @param: Constantaz.selected_inv_name -> hold "Inventory Name" with previous "Inventory Name"
            *         formate: previous Name>Current Nameprevious Name>Current Name
            *
         * */

                String checkPreviousMiltiSceen="0";
                if(statusMultiPart){
                    checkPreviousMiltiSceen = intentValue4;
                }

                Intent intent = new Intent(inventories_SecondLvl.this, inventories_ThirdLvl.class);
                if (levelChecker(categoriesList.get(position), tempList)) {
                    intent.putExtra(Constantaz.isThree, true);
                    intent.putExtra(ConstantazDataBase.inventory_item_category, categoriesList.get(position));
                    intent.putExtra(ConstantazDataBase.inventory_item_features, categoriesList.get(position));
                    intent.putExtra("MultiFlagStatus", statusMultiPart);
                    intent.putExtra("MultiFlagValue",  ""+intentValue4);
                    intent.putExtra("checkPreviousMiltiSceen",  ""+checkPreviousMiltiSceen);
                    intent.putExtra("shared1",intentValue3) ;
                    intent.putExtra("multiscreencounter","1");
                    intent.putExtra("multiscreenlevel","2");
                    intent.putExtra(Constantaz.selected_inv_name, currentItem.getText().toString().concat(" > ").concat(currentDescriptions.get(position)));
                    startActivity(intent);
                }
            }
        });

        /************working on listview with adapter and item click listener***************/}

    @Nullable
    private List<String> callFxnForUpdateDB() {
        currentDescriptions = new ArrayList<>();
        final List<String> currentDescriptions = getSecondLvlItems(getIntent().getStringExtra(inventory_item_features));

        //@desc: use for check all item from database ..if exist or not..if not exist then finish activity
        if (currentDescriptions == null || currentDescriptions.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No Navigation data found.", Toast.LENGTH_LONG).show();
            finish();
            return null;
        }

        //use for set different adapter with different functionality and show/hide save and camera button
        for (int i = 0; i < categoriesList.size(); i++) {
            String s = categoriesList.get(i);

            try {
                if (s.equals(null)) {
                    s = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                s = "";
            }

            spinnStringItem = new ArrayList<String>();
            spinnStringItem = getSpinnerItem(s);
            lists.add(spinnStringItem);


            ArrayList<String> tempList = new ArrayList<String>();

            if (!levelChecker(categoriesList.get(i), tempList)) {

               // saveBT.setVisibility(View.VISIBLE);
                cameraBT.setVisibility(View.VISIBLE);

            } else {
                saveBT.setVisibility(View.GONE);
                cameraBT.setVisibility(View.GONE);

            }

        }

        ///////////////////////////////// code to fetch data from database
        dbMain.Open();
        Cursor cursor = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + levelNames[0].trim() + "'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            Zero_Item_Code = cursor.getString(0);
            Zero_Item_Item_Features = cursor.getString(1);

        }
        cursor.close();

        Cursor cursor1 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + levelNames[1].trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Zero_Item_Item_Features.trim() + "'", null);

        if (cursor1.getCount() > 0) {
            cursor1.moveToNext();
            One_Item_Code = cursor1.getString(0);
            One_Item_Item_Features = cursor1.getString(1);
        }
        cursor1.close();

        dbMain.Close();

        ///////////////////////////////// code to fetch data from database
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-", "").replaceAll("\\s+", "");
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        dbClient.Open();
        try {
            mValueMultiPart = checkMaxNoFor_Unique_Field(Zero_Item_Code, One_Item_Code,inventoryCreationDate1, propertyID,"2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbClient.close();


        // check data exist of not in database for set photo count
        try {
            if (activity_MainMenu.editValueFlag) {
                // comes from inventory

                zeroLevelItemName = levelNames[0];//third element
                firstLevelItemName = levelNames[1];//fourth element


                if(statusMultiPart){
                    mScreenMultiPart = intentValue4;
                }
                else{
                    mScreenMultiPart="0";
                }

                if (dataExistintempTable2(zeroLevelItemName, firstLevelItemName, inventoryCreationDate1, propertyID, "2",Integer.parseInt(mScreenMultiPart))) {

                    String photoCount = getDataExistPhoto2(zeroLevelItemName, firstLevelItemName, inventoryCreationDate1, propertyID, "2",Integer.parseInt(mScreenMultiPart));

                    if (photoCount.length() > 0) {

                        int counter = 0;
                        if (photoCount.contains("|")) {

                            photoCount = photoCount.replace("|", ",");
                            photoCount = photoCount.substring(0, photoCount.length() - 1);

                            if (photoCount.contains(",")) {
                                String[] photosS = photoCount.split(",");
                                for (int t = 0; t < photosS.length; t++) {
                                    imagePath2[t] = photosS[t] + "|";
                                }
                                txtBallon.setVisibility(View.VISIBLE);
                                txtBallon.setText("" + photosS.length);
                            } else {
                                txtBallon.setVisibility(View.VISIBLE);
                                txtBallon.setText("1");
                                imagePath2[0] = photoCount + "|";
                            }


                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////
        return currentDescriptions;
    }

    private void callFxnForUpdateAdapter(List<String> currentDescriptions) {
        String mValue[];

        mValue = getCodeBycode(Zero_Item_Code, One_Item_Code,inventoryCreationDate1, propertyID,"2");


        dbMain.Open();
        dbClient.Open();

        DataTitle = new ArrayList<>();
        DataValue = new ArrayList<>();

        for (int z = 0; z < mValue.length; z++) {

            Cursor cur2;
            cur2 = dbMain.getInventoryDetailsbycode(mValue[z]);
            String mmData = "";

            while (cur2.moveToNext()) {
                mmData = cur2.getString(0);

                Cursor c;
                if(statusMultiPart) {
                    c = dbClient.fetchDetailsInventory2_multi(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code,One_Item_Code,"2",Integer.parseInt(intentValue4));

                }
                else{
                    c = dbClient.fetchDetailsInventory2(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code,One_Item_Code,"2");

                }
                while (c.moveToNext()) {
                    DataTitle.add(mmData);
                    DataValue.add(c.getString(0));
                }
                c.close();
            }
            cur2.close();

        }
        dbClient.close();
        dbMain.Close();
        /*******************last rocket.................*/

        //////////////////////////////////////////////////////////////////////////////finish

        ///////////////////////////////////////////// code for add data into hashmap with key and value for manage position flucting and hold runtime data change
        for(int y= 0 ;y<currentDescriptions.size();y++){

            valueHolder.put(currentDescriptions.get(y).toString().trim(),"");

            for(int r = 0; r<DataTitle.size();r++){
                if(currentDescriptions.get(y).toString().trim().equals(DataTitle.get(r))){
                    valueHolder.put(currentDescriptions.get(y).toString().trim(),DataValue.get(r).toString());
                }

            }

        }
        ///////////////////////////////////////////// code for add data into hashmap with key and value for manage position flucting and hold runtime data change


        /**
         * use: check this item value present into db or not
         * and change in arrow direction
         * Date: 18-March-2017
         */
        String propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");

        dbClient.Open();
        for(String itemCode : itemCategoriesCode){

            Cursor c;
            if(statusMultiPart) {
                c = dbClient.fetchTwolvtDataCodeValue_multi(propertyID, inventoryDate.replaceAll("-",""),""+Zero_Item_Code,One_Item_Code,itemCode,Integer.parseInt(intentValue4));

            }
            else{
                c = dbClient.fetchTwolvtDataCodeValue(propertyID, inventoryDate.replaceAll("-",""),""+Zero_Item_Code,One_Item_Code,itemCode);

            }

            if(c.getCount()>0){
                itemDataExist.add("YES");
            }
            else{
                itemDataExist.add("NO");
            }
            c.close();

        }
        dbClient.close();

        ////////////////////////////////
    }

    /**
     * Back to main menu.
     *
     * @param view the view
     */
    public void backToMainMenu(View view) {

        inventories_SecondLvl.this.finish();
    }

    /**
     *
     * @param featureCode
     * @return
     */
    @NonNull
    private List<String> getSecondLvlItems(@NonNull String featureCode) {
        SQLiteDatabase database = helper.getExternalDataBase(getApplicationContext());

        Cursor cursor = database.rawQuery("SELECT * "
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_lvl).concat("='2'").concat(" AND ")
                .concat(ConstantazDataBase.inventory_item_category).concat("='".concat(featureCode.concat("'"))), null);

        List<String> tempList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemDesc = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_description));
                String itemFeature = cursor.getString(cursor.getColumnIndex(inventory_item_features));
                String itemCategoryCode = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_code));

                if (itemDesc != null) {
                    tempList.add(itemDesc);
                } else {
                    tempList.add("");
                }

                if (itemDesc != null) {
                    categoriesList.add(itemFeature);
                } else {
                    categoriesList.add("");
                }

                if(itemDesc != null){
                    itemCategoriesCode.add(itemCategoryCode);
                }
                else{
                    itemCategoriesCode.add("");
                }

                cursor.moveToNext();
            }
        }
        cursor.close();
        return tempList;
    }


    private boolean checkDBExistYorN(String featureCode ) {

        boolean dataExist = false;

        SQLiteDatabase database = helper.getExternalDataBase(getApplicationContext());

        String mSQLQuery = "SELECT * "
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_lvl).concat("='2'").concat(" AND ")
                .concat(ConstantazDataBase.inventory_item_category).concat("='".concat(featureCode.concat("'")))
                .concat(" AND ").concat(inventory_item_multi_flag).concat("='"+"Y'");

        Cursor cursor = database.rawQuery(mSQLQuery, null);

        if(cursor.getCount()>0){
            dataExist = true;
        }
        else{
            dataExist = false;
        }

        cursor.close();

        return dataExist;
    }

    /**
     *
     * @param context
     * @param currentSelItem
     */
    private void initialSetUp(Context context, String currentSelItem) {

        /*****use for show custom action bar**********/
        ActionBarBuilder.getInstance()
                .setActionBar(context, getSupportActionBar())
                .setCustomIcon(R.drawable.icon_inventory)
                .showDefaultHomeTitle(false)
                .showDefaultHomeIcon(false)
                .showYourHomeIcon(true)
                .inflateLayout(R.layout.inventories_action_bar, null)
                .showCustomLayout(true);
        if (getSupportActionBar() != null) {
            View v = getSupportActionBar().getCustomView();
            TextView title = (TextView) v.findViewById(R.id.inventories_bar_title);
            title.setText("  Inventories");
            ImageView home = (ImageView) v.findViewById(R.id.inventories_bar_logo);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(inventories_SecondLvl.this, activity_MainMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });
        }
        /*****use for show custom action bar**********/


        //use for set name and date(current date)
        propName.setText(sPrefs.getStringValue(Constantaz.selected_prop_name, "No data"));
        inventoryDate=""+getCurrentDate();
        propDate.setText("Date: ".concat(inventoryDate));
        if(statusMultiPart)
         currentItem.setText(currentSelItem+": "+intentValue4);
        else
         currentItem.setText(currentSelItem);

    }

    //@desc: use for getting item category description and label
    //select BP02_INV_ITEM_DESCRIPTION,BP02_INV_ITEM_LEVEL  FROM BP02_INVENTORY_ITEM WHERE BP02_INV_ITEM_CATEGORY = 'LIGHTING'
    @NonNull
    private List<String> getSpinnerItem(@NonNull String feature) {
        List<String> descriptions = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_lvl)).concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_category).concat("='".concat(feature.concat("'"))), null);
        String itemLevel = "";
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemDesc = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_description));
                if (itemDesc != null)
                    descriptions.add(itemDesc);
                cursor.moveToNext();
            }
            cursor.close();

        }
        cursor.close();
        return descriptions;
    }

    //@desc: function check next label is available or not
    //select BP02_INV_ITEM_DESCRIPTION,BP02_INV_ITEM_LEVEL  FROM BP02_INVENTORY_ITEM WHERE BP02_INV_ITEM_CATEGORY = 'LIGHTING'
    private boolean levelChecker(@Nullable String feature, @NonNull List<String> descriptions) {

        if (feature == null) {
            feature = "";
        }

        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_lvl)).concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_category).concat("='".concat(feature.concat("'"))), null);

        String itemLevel = "";
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemDesc = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_description));
                itemLevel = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_lvl));
                descriptions.add(itemDesc);
                cursor.moveToNext();
            }
            cursor.close();
            return itemLevel.equals("3");
        }
        cursor.close();
        return false;
    }

    @Override
    protected void onPause() {


       // if(!mSecondScreenFlag.equalsIgnoreCase("1")){
            if(statusMultiPart){
                if (adapter != null) {

                    saveCurrentDataM(inventories_SecondLvl.valueHolder);

                }
            }
            else {
                if (adapter != null) {

                    saveCurrentData(inventories_SecondLvl.valueHolder);
                }
            }
      //  }
     //   else{
     //       mSecondScreenFlag="2";
     //   }



        super.onPause();

    }

    /**
     * use for delete multi screen
     * @param view
     */
    public void deleteMultiScreen(View view){

        new AlertDialog.Builder(inventories_SecondLvl.this)
                .setMessage("Are you sure to delete entry!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEntry(Zero_Item_Code, One_Item_Code,inventoryCreationDate1, propertyID,"2",Integer.parseInt(intentValue4));
                        deleteTempRecord();
                        deleteUpdateEntry(Zero_Item_Code, One_Item_Code,inventoryCreationDate1, propertyID,"2",Integer.parseInt(intentValue4));
                        deleteUpdateTempRecord();

                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();

    }

    /**
     * use for multi screen save
     * @param view
     */

    public void saveInputedDataMulti(View view) {


        if (adapter != null) {

            new GotoNextActivityWithSave().execute();

        }


    }


    private void saveCurrentDataMulti(@Nullable HashMap<String, String> map) {



        onTimeInsertData = true;
        insertData = true;

        accountId = sPrefs.getStringValue(Constantaz.userID, "null");
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-", "").replaceAll("\\s+", "");
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        zeroLevelItemName = levelNames[0];//third element
        firstLevelItemName = levelNames[1];//fourth element

        data = "";
        String dataInput = "";

        if (map != null && !map.isEmpty()) {

            if (snapChecker) {
                imagePath = imagePath1;
            } else {
                imagePath = imagePath2;
            }

            /********************old data*********************/
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().trim().length() > 0)
                    data = data.concat(entry.getKey().trim() + ":" + entry.getValue().trim() + "|");//six element
            }

            /********************old data*********************/

            /********************new data*********************/
            String imgPath1 = Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim();

            imgPath1 = imgPath1.replace("|", ",");

            imgPathsplit = imgPath1.split(",");


            if(data.length()>0){

                dbMain.Open();
                dbClient.Open();

                try {
                    mValueMultiPart = checkMaxNoFor_Unique_Field(Zero_Item_Code, One_Item_Code,inventoryCreationDate1, propertyID,"2");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String data3 = "";
                    dataInput3 = "";

                    data3 = entry.getKey().trim();
                    dataInput3 = entry.getValue().trim();

                    if (entry.getValue().trim().length() > 0){


                        Cursor cursor2 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + data3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + One_Item_Item_Features.trim() + "'", null);
                        Two_Item_Code = "";
                        Two_Item_Item_Features = "";
                        if (cursor2.getCount() > 0) {
                            cursor2.moveToNext();
                            for (int i = 0; i < cursor2.getCount(); i++) {
                                Two_Item_Code = cursor2.getString(0);
                                Two_Item_Item_Features = cursor2.getString(1);
                            }
                        }
                        cursor2.close();


                        Cursor cursor3 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_lvl + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + dataInput3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Two_Item_Item_Features.trim() + "'", null);
                        Desc_Item_Code="";
                        Desc_Item_level="";
                        Three_Item_Code = "";
                        Three_Item_Item_Features = "";
                        if (cursor3.getCount() > 0) {
                            cursor3.moveToNext();
                            for (int i = 0; i < cursor3.getCount(); i++) {
                                Desc_Item_Code = cursor3.getString(0);
                                Desc_Item_level = cursor3.getString(2);

                                Three_Item_Code = cursor3.getString(0);
                                Three_Item_Item_Features = cursor3.getString(1);
                            }
                        }
                        cursor3.close();
                        if(Desc_Item_level.trim().equalsIgnoreCase("-3")){
                            call_Fxn_For_negative_3();
                        }
                        else{
                            call_Fxn_For_not_negative_3();
                        }


                    }

                }

                dbClient.close();
                dbMain.Close();

            }

            /********************new data*********************/

        }

        Intent intent=new Intent(inventories_SecondLvl.this, inventories_SecondLvl.class);
        intent.putExtra(ConstantazDataBase.inventory_item_features, intentValue1);
        intent.putExtra(Constantaz.selected_inv_name,intentValue2);
        intent.putExtra("shared1",intentValue3) ;
        intent.putExtra("multiscreencounter",""+(Integer.parseInt(intentValue4)+1));

        startActivity(intent);
        finish();

    }

    private void saveCurrentDataM(@Nullable HashMap<String, String> map) {



        onTimeInsertData = true;
        insertData = true;

        accountId = sPrefs.getStringValue(Constantaz.userID, "null");
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-", "").replaceAll("\\s+", "");
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        zeroLevelItemName = levelNames[0];//third element
        firstLevelItemName = levelNames[1];//fourth element

        data = "";
        String dataInput = "";

        if (map != null && !map.isEmpty()) {

            if (snapChecker) {
                imagePath = imagePath1;
            } else {
                imagePath = imagePath2;
            }

            /********************old data*********************/
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().trim().length() > 0)
                    data = data.concat(entry.getKey().trim() + ":" + entry.getValue().trim() + "|");//six element
            }

            /********************old data*********************/

            /********************new data*********************/
            String imgPath1 = Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim();

            imgPath1 = imgPath1.replace("|", ",");

            imgPathsplit = imgPath1.split(",");


            if(data.length()>0){

                dbMain.Open();
                dbClient.Open();

                try {
                    mValueMultiPart = checkMaxNoFor_Unique_Field(Zero_Item_Code, One_Item_Code,inventoryCreationDate1, propertyID,"2");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String data3 = "";
                    dataInput3 = "";

                    data3 = entry.getKey().trim();
                    dataInput3 = entry.getValue().trim();

                    if (entry.getValue().trim().length() > 0){


                        Cursor cursor2 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + data3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + One_Item_Item_Features.trim() + "'", null);
                        Two_Item_Code = "";
                        Two_Item_Item_Features = "";
                        if (cursor2.getCount() > 0) {
                            cursor2.moveToNext();
                            for (int i = 0; i < cursor2.getCount(); i++) {
                                Two_Item_Code = cursor2.getString(0);
                                Two_Item_Item_Features = cursor2.getString(1);
                            }
                        }
                        cursor2.close();


                        Cursor cursor3 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_lvl + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + dataInput3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Two_Item_Item_Features.trim() + "'", null);
                        Desc_Item_Code="";
                        Desc_Item_level="";
                        Three_Item_Code = "";
                        Three_Item_Item_Features = "";
                        if (cursor3.getCount() > 0) {
                            cursor3.moveToNext();
                            for (int i = 0; i < cursor3.getCount(); i++) {
                                Desc_Item_Code = cursor3.getString(0);
                                Desc_Item_level = cursor3.getString(2);

                                Three_Item_Code = cursor3.getString(0);
                                Three_Item_Item_Features = cursor3.getString(1);
                            }
                        }
                        cursor3.close();
                        if(Desc_Item_level.trim().equalsIgnoreCase("-3")){
                            call_Fxn_For_negative_3();
                        }
                        else{
                            call_Fxn_For_not_negative_3();
                        }


                    }

                }

                dbClient.close();
                dbMain.Close();

            }

            /********************new data*********************/

        }


    }

    private void saveCurrentData(@Nullable HashMap<String, String> map) {

        onTimeInsertData = true;
        insertData = true;

        accountId = sPrefs.getStringValue(Constantaz.userID, "null");
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-", "").replaceAll("\\s+", "");
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        zeroLevelItemName = levelNames[0];//third element
        firstLevelItemName = levelNames[1];//fourth element

        data = "";
        String dataInput = "";

        if (map != null && !map.isEmpty()) {

            if (snapChecker) {
                imagePath = imagePath1;
            } else {
                imagePath = imagePath2;
            }

            /********************old data*********************/
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().trim().length() > 0)
                    data = data.concat(entry.getKey().trim() + ":" + entry.getValue().trim() + "|");//six element
            }

            /********************old data*********************/

            /********************new data*********************/
            String imgPath1 = Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim();

            imgPath1 = imgPath1.replace("|", ",");

            imgPathsplit = imgPath1.split(",");


            if(data.length()>0){

            dbMain.Open();
            dbClient.Open();

               try {
                   mValueMultiPart = checkMaxNoFor_Unique_Field(Zero_Item_Code, One_Item_Code,inventoryCreationDate1, propertyID,"2");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                for (Map.Entry<String, String> entry : map.entrySet()) {
                String data3 = "";
                dataInput3 = "";

                data3 = entry.getKey().trim();
                dataInput3 = entry.getValue().trim();

                if (entry.getValue().trim().length() > 0){


                        Cursor cursor2 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + data3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + One_Item_Item_Features.trim() + "'", null);
                        Two_Item_Code = "";
                        Two_Item_Item_Features = "";
                        if (cursor2.getCount() > 0) {
                            cursor2.moveToNext();
                            for (int i = 0; i < cursor2.getCount(); i++) {
                                Two_Item_Code = cursor2.getString(0);
                                Two_Item_Item_Features = cursor2.getString(1);
                            }
                        }
                        cursor2.close();

                        Cursor cursor3 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_lvl + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + dataInput3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Two_Item_Item_Features.trim() + "'", null);
                        Desc_Item_Code="";
                        Desc_Item_level="";

                        if (cursor3.getCount() > 0) {
                            cursor3.moveToNext();
                            for (int i = 0; i < cursor3.getCount(); i++) {
                                Desc_Item_Code = cursor3.getString(0);
                                Desc_Item_level = cursor3.getString(2);
                            }
                        }
                        cursor3.close();
                        if(Desc_Item_level.trim().equalsIgnoreCase("-3")){
                            call_Fxn_For_negative_3();
                        }
                        else{
                            call_Fxn_For_not_negative_3();
                        }


            }

            }

                dbClient.close();
                dbMain.Close();

        }

        /********************new data*********************/

        }
     //   finish();           // comment the finish method on the 08-11-2017

    }

    private void call_Fxn_For_not_negative_3() {
        Boolean invDataExist_Desire = false;
        Boolean invDataExist_Temp = false;

        /**
         * use for check data exist into "Desire Database"
         */
        String firstValue="";
        if(statusMultiPart){
            firstValue =firstLevelItemName;
            if (dataExistintempTable1_multi(Zero_Item_Code, One_Item_Code, Two_Item_Code,Three_Item_Code, inventoryCreationDate1, propertyID,"2",Integer.parseInt(intentValue4),"2")) {
                invDataExist_Desire = true;
            }
        }
        else{
            firstValue = firstLevelItemName;
            if (dataExistintempTable1(Zero_Item_Code, One_Item_Code, Two_Item_Code,Three_Item_Code, inventoryCreationDate1, propertyID,"2")) {
                invDataExist_Desire = true;
            }
        }
        //////////////////////////

        /**
         * use for check data exist into "Temp database or view database"
         */

        if(statusMultiPart){
            mScreenMultiPart = intentValue4;
        }
        else{
            mScreenMultiPart="0";
        }

        if (dataExistintempTable2(zeroLevelItemName, firstValue, inventoryCreationDate1, propertyID, "2",Integer.parseInt(mScreenMultiPart))) {
            invDataExist_Temp = true;
        }

        //////////////////////////

        if (invDataExist_Temp) {

            /**
             * check this is for multipart or not
             */

            if(statusMultiPart){
                // find max number of this field

                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, "", "", "", "", imgPath[0], imgPath[1], imgPath[2],"2",Integer.parseInt(intentValue4),"2");
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data,Integer.parseInt(mScreenMultiPart));

                } else {
                    // do some change
                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, "", "", "", "", imgPath[0], imgPath[1], imgPath[2],"2",Integer.parseInt(intentValue4),"2");

                    if (invDataExist_Temp){
                       // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data);
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, Integer.parseInt(mScreenMultiPart));

                    }
                    else{
                        insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data,"2","Y");
                    }

                }

            }
            else{
                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.updateDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, "", "", "", "", imgPath[0], imgPath[1], imgPath[2],"2","2");
                   // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, Integer.parseInt(mScreenMultiPart));

                } else {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, "", "", "", "", imgPath[0], imgPath[1], imgPath[2],"2","2");
                //    updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, Integer.parseInt(mScreenMultiPart));

                }
            }


        } else {
            if(statusMultiPart){

                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, "", "", "", "", imgPath[0], imgPath[1], imgPath[2], "2",Integer.parseInt(intentValue4),"2");
                insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, "2","Y");
            }
            else {

                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, "", "", "", "", imgPath[0], imgPath[1], imgPath[2], "2","2");
                insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, "2","N");
            }


        }

        // check data exist these both these data or not .. if data exist in db then go to update otherwise goto add
    }

    private void call_Fxn_For_negative_3() {
        Boolean invDataExist_Desire = false;
        Boolean invDataExist_Temp = false;

        /**
         * use for check data exist into "Desire Database"
         */
        String firstValue="";
        if(statusMultiPart){
            firstValue =firstLevelItemName;
            if (dataExistintempTable1_multi(Zero_Item_Code, One_Item_Code, Two_Item_Code,Three_Item_Code, inventoryCreationDate1, propertyID,"2",Integer.parseInt(intentValue4),"2")) {
                invDataExist_Desire = true;
            }
        }
        else{
            firstValue = firstLevelItemName;
            if (dataExistintempTable1(Zero_Item_Code, One_Item_Code, Two_Item_Code,Three_Item_Code, inventoryCreationDate1, propertyID,"2")) {
                invDataExist_Desire = true;
            }
        }
       ////////////////////////////

        if(statusMultiPart){
            mScreenMultiPart = intentValue4;
        }
        else{
            mScreenMultiPart="0";
        }

        /**
         * use for check data exist into "Temp database or view database"
         */

        if (dataExistintempTable2(zeroLevelItemName, firstValue, inventoryCreationDate1, propertyID, "2",Integer.parseInt(mScreenMultiPart))) {
            invDataExist_Temp = true;
        }

        //////////////////////////

         if (invDataExist_Temp) {
            // data exist then do update

            if(statusMultiPart){
                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Desc_Item_Code, "", "", "", imgPath[0], imgPath[1], imgPath[2],"2",Integer.parseInt(intentValue4),"2");
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, Integer.parseInt(mScreenMultiPart));

                } else {
                    // do some change
                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Desc_Item_Code, "", "", "", imgPath[0], imgPath[1], imgPath[2],"2",Integer.parseInt(intentValue4),"2");

                    if(invDataExist_Temp){
                       // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data);
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, Integer.parseInt(mScreenMultiPart));

                    }
                    else{
                        insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data,"2","Y");
                    }
                }
            }
            else{
                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.updateDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Desc_Item_Code, "", "", "", imgPath[0], imgPath[1], imgPath[2],"2","2");
                   // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, Integer.parseInt(mScreenMultiPart));


                } else {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                    dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Desc_Item_Code, "", "", "", imgPath[0], imgPath[1], imgPath[2],"2","2");
                  //  updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data, Integer.parseInt(mScreenMultiPart));

                }
            }



        } else {

            if(statusMultiPart){

                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Desc_Item_Code, "", "", "", imgPath[0], imgPath[1], imgPath[2],"2",Integer.parseInt(intentValue4),"2");

                insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data,"2","Y");
            }
            else{
                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Desc_Item_Code, "", "", "", imgPath[0], imgPath[1], imgPath[2],"2","2");

                insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, data,"2","N");
            }



        }

        // check data exist these both these data or not .. if data exist in db then go to update otherwise goto add
    }

    private void insertDataTempInventory(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName, String data, String level, String multiScreenFlag) {

        if (insertData) {
            insertData = false;
            dbPdfView.Open();

            int dbMultiFlag;
            if(statusMultiPart){
                dbMultiFlag=Integer.parseInt(intentValue4);
            }
            else{
                dbMultiFlag=0;
            }

            dbPdfView.insertDataIntoPropertyTable_Multi(inventoryCreationDate1,
                    Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim(),
                    propertyID,
                    zeroLevelItemName,
                    firstLevelItemName,
                    "",
                    data,
                    level,
                    dbMultiFlag,
                    multiScreenFlag

            );
            dbPdfView.close();
        }
    }

    private void updateDataTempInventory(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName, String data) {
        dbPdfView.Open();

        dbPdfView.updateDataIntoPropertyTable(inventoryCreationDate1,
                Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim(),
                propertyID,
                zeroLevelItemName,
                firstLevelItemName,
                "",
                data,
                "2"
        );
        dbPdfView.close();
    }

    private void updateDataTempInventory_multi(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName, String data, int multiFlag) {
        dbPdfView.Open();

        dbPdfView.updateDataIntoPropertyTable_multi(inventoryCreationDate1,
                Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim(),
                propertyID,
                zeroLevelItemName,
                firstLevelItemName,
                "",
                data,
                "2",
                multiFlag
        );
        dbPdfView.close();
    }

    private void callFxnforInsertDataIntoDocumentTable(String inventoryCreationDate1, @NonNull String[] imgPathsplit) {
        Integer mData;

        if (onTimeInsertData) {

            onTimeInsertData = false;
            for (int h = 0; h < imgPathsplit.length; h++) {
                if (checkDataExist()) {

                    mData = fetchDocId();
                    mData++;

                    if (holder[0] == null) {
                        holder[0] = mData;
                    } else {
                        if (holder[1] == null) {
                            holder[1] = mData;
                        } else {
                            holder[2] = mData;
                        }
                    }

                    String imgMimeData = imgPathsplit[h];
                    int lastIndex = imgMimeData.lastIndexOf(".");

                    if (lastIndex != -1) {
                        imgMimeData = imgMimeData.substring(lastIndex + 1);
                    } else {
                        imgMimeData = "";
                    }

                  if (imgPathsplit[h].length() > 0) {

                        dbClient.insertDataIntoDocumentTable(inventoryCreationDate1, "" + mData, imgMimeData, imgPathsplit[h], "PHOTO", propertyID);

                    }

                } else {
                    holder[h] = h + 1;

                    String imgMimeData = imgPathsplit[h];
                    int lastIndex = imgMimeData.lastIndexOf(".");

                    if (lastIndex != -1) {
                        imgMimeData = imgMimeData.substring(lastIndex + 1);
                    } else {
                        imgMimeData = "";
                    }

                    // call insert query
                    if (imgPathsplit[h].length() > 0) {
                          dbClient.insertDataIntoDocumentTable(inventoryCreationDate1, "" + h + 1, imgMimeData, imgPathsplit[h], "PHOTO", propertyID);

                    }
                }
            }

            if (imgPathsplit.length == 1 && !imgPathsplit[0].equals("")) {
                imgPath[0] = "" + holder[0];
                imgPath[1] = "";
                imgPath[2] = "";
            }
            if (imgPathsplit.length == 2) {
                imgPath[0] = "" + holder[0];
                imgPath[1] = "" + holder[1];
                imgPath[2] = "";
            }
            if (imgPathsplit.length == 3) {
                imgPath[0] = "" + holder[0];
                imgPath[1] = "" + holder[1];
                imgPath[2] = "" + holder[2];
            }

            if (imgPathsplit == null) {
                imgPath[0] = "";
                imgPath[1] = "";
                imgPath[2] = "";
            }
            //use for add data into document table...
        }

    }


    /**
     * Take photo.
     *
     * @param view the view
     */
    public void takePhoto(View view) {

        checkCameraUpdate = false;

        snapChecker = true;

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        if (photoID > 2) {
            Toast.makeText(inventories_SecondLvl.this, "Maximum of 3 photos per screen allowed", Toast.LENGTH_SHORT).show();
        } else {

            String inventoryCreationDate = getCurrentDate();//first element
            String inventoryCreationDate1 = inventoryCreationDate.replaceAll("-", "").replaceAll("\\s+", "");

            String propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element
            String zeroLevelItemName = levelNames[0];//third element
            String firstLevelItemName = levelNames[1];//fourth element

            dbClient.Open();
            Cursor cur12;
            cur12 = dbClient.fetchDetailsHouseNo(propertyID);
            String HouseNo = "";
            while (cur12.moveToNext()) {
                HouseNo = cur12.getString(cur12.getColumnIndex("BP01_HOUSE_NO"));
            }
            cur12.close();
            dbClient.close();

            File photo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "/ABCInventory/"
                            .concat(inventoryCreationDate1+"-"+propertyID.replace(" ", ""))
                            .concat("/" + zeroLevelItemName.replace(" ", "")+ "-"
                                    +firstLevelItemName.replace(" ", "")+"-"
                                    +photoID + ".jpg"));

            try {
                if (photo.exists()) {
                    photo.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            photo.getParentFile().mkdirs();

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));

            photoPath = photo.toString();
            startActivityForResult(intent, 0);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            String selectedImage = photoPath;
            imagePath1[photoID] = selectedImage + "|";
            photoID++;

            /*****use for show ballon******************/
            if (photoID != 0) {
                txtBallon.setVisibility(View.VISIBLE);
                txtBallon.setText("" + photoID);
            } else {
                txtBallon.setVisibility(View.GONE);
                txtBallon.setText("" + 0);
            }
            /*****use for show ballon******************/
        }
    }

    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        String s = null;
        try {
            s = sPrefs.getStringValue(Constantaz.NewInventory, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = null;
        if (s == null)
            s = "";
        if (s.equals("New")) {
            df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateG = df.format(c.getTime());
        } else if (s.equals("edit")) {
            dateG = inventories_ZeroLvl.inventoryDate;
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateG = df.format(c.getTime());
        }
        return dateG;
    }

    /**
     * Check data exist boolean.
     *
     * @return the boolean
     */
    public boolean checkDataExist() {
        Cursor c;
        c = dbClient.fetchDetailsDocumentTable();

        while (c.moveToNext()) {
             return true;
        }
         return false;
    }


/*
    *
    * fetch data from table
    *
    * @ return integer
    * */
    @Nullable
    public Integer fetchDocId() {
        Cursor c;
        c = dbClient.fetchDetailsDocumentTable();
        Integer data = null;
        while (c.moveToNext()) {
            data = c.getInt(0);
        }
       return data;
    }


    /**
     * Data existintemp table 1 boolean.
     *
     * @param zerolvlcode the zerolvlcode
     * @param onelvlcode  the onelvlcode
     * @param twolvlcode  the twolvlcode
     * @param date        the date
     * @param propertyId  the property id
     * @param level       the level
     * @return the boolean
     */
    public boolean dataExistintempTable1(String zerolvlcode, String onelvlcode, String twolvlcode,String threelvlcode, String date, String propertyId, String level) {
        Cursor c;
        c = dbClient.fetchDetailsInventoryTable(zerolvlcode, onelvlcode, twolvlcode,threelvlcode, date, propertyId, level);
        int
                i = 0;
        String valuer[] = new String[c.getCount()];
        while (c.moveToNext()) {
             return true;
        }

        c.close();
        return false;
    }

    public boolean dataExistintempTable1_multi(String zerolvlcode, String onelvlcode, String twolvlcode,String threelvlcode, String date, String propertyId, String level, int multiFlag, String orginalLevel) {
        Cursor c;
       // c = dbClient.fetchDetailsInventoryTable_multi(zerolvlcode, onelvlcode, twolvlcode,threelvlcode, date, propertyId, level,multiFlag);
         c = dbClient.fetchDetailsDesireDB(zerolvlcode, onelvlcode, twolvlcode,threelvlcode,"","", date, propertyId, level,multiFlag,orginalLevel);


        int
                i = 0;
        String valuer[] = new String[c.getCount()];
        while (c.moveToNext()) {
            return true;
        }

        c.close();
        return false;
    }

    public int checkMaxNoFor_Unique_Field(String zerolvlcode, String onelvlcode, String date, String propertyId, String level){
        Cursor c;
        c = dbClient.checkMaxNoFor_Unique_Field(zerolvlcode, onelvlcode, date, propertyId, level);

        int value= 0;
        String valuer[] = new String[c.getCount()];

        while (c.moveToNext()) {
            value = c.getInt(0);
            return value;
        }

        c.close();
        return value;
    }

    public void deleteEntry(String zerolvlcode, String onelvlcode, String date, String propertyId, String level, int multiFlag){
        dbClient.Open();
        try {
          //  Cursor c;
           dbClient.deleteEntry(zerolvlcode, onelvlcode, date, propertyId, level,multiFlag);
            //c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbClient.close();
    }

    public void deleteTempRecord(){
        dbPdfView.Open();
        try {
            dbPdfView.deleteInventoryData2level_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, Integer.parseInt(intentValue4));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dbPdfView.close();
    }

    public void deleteUpdateTempRecord(){
        dbPdfView.Open();
        try {
            dbPdfView.deleteUpdateInventoryData2level(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName,Integer.parseInt(intentValue4));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dbPdfView.close();
    }


    public void deleteUpdateEntry(String zerolvlcode, String onelvlcode, String date, String propertyId, String level, int multiFlag){
        dbClient.Open();
        try {
            //  Cursor c;
            dbClient.deleteUpdateEntry(zerolvlcode, onelvlcode, date, propertyId, level,multiFlag);
            //c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbClient.close();
    }

    /**
     * Data existintemp table boolean.
     *
     * @param zerolvlcode the zerolvlcode
     * @param onelvlcode  the onelvlcode
     * @param date        the date
     * @param propertyId  the property id
     * @param level       the level
     * @return the boolean
     */
    public boolean dataExistintempTable2(String zerolvlcode, String onelvlcode, String date, String propertyId, String level, int multiFlagID) {

        dbPdfView.Open();
        Cursor c;
        c = dbPdfView.getDataExistOrNotlevel2(propertyId, date, zerolvlcode, onelvlcode, level,multiFlagID);

        while (c.moveToNext()) {

            c.close();
            dbPdfView.close();
            return true;
        }

        c.close();
        dbPdfView.close();
        return false;
    }



    /**
     * Gets data exist photo.
     *
     * @param zerolvlcode the zerolvlcode
     * @param onelvlcode  the onelvlcode
     * @param date        the date
     * @param propertyId  the property id
     * @param level       the level
     * @return the data exist photo
     */
    public String getDataExistPhoto2(String zerolvlcode, String onelvlcode, String date, String propertyId, String level, int multiPartID) {

        dbPdfView.Open();
        Cursor c;
        c = dbPdfView.getDataExistPhotolevel2(propertyId, date, zerolvlcode, onelvlcode, level,multiPartID);
        String photo = "";

        while (c.moveToNext()) {
            photo = c.getString(c.getColumnIndex("INVENTORYITEMPHOTOS"));
        }
        c.close();
        dbPdfView.close();
        return photo;
    }


    //////////////////////////////////////////////////// new code

    /**
     * Get code bycode string [ ].
     *
     * @param zerolvlcode the zerolvlcode
     * @param onelvlcode  the onelvlcode
     * @param date        the date
     * @param propertyId  the property id
     * @param level       the level
     * @return the string [ ]
     */

    @NonNull
    public String[] getCodeBycode(String zerolvlcode, String onelvlcode, String date, String propertyId, String level) {

        dbClient.Open();
        Cursor c;
        if(statusMultiPart){
            c = dbClient.fetchDetailsInventoryTable2level_multi(zerolvlcode, onelvlcode,date, propertyId,level,Integer.parseInt(intentValue4));
        }
        else{
            c = dbClient.fetchDetailsInventoryTable2level(zerolvlcode, onelvlcode,date, propertyId,level);
        }

        int i=0;
        String valuer[]=new String[c.getCount()];
        while(c.moveToNext()){
            valuer[i] = c.getString(0);
            i++;
        }

        c.close();
        dbClient.close();
        return valuer;
    }

    //////////////////////////////////////////////////////////////new code

    public class GotoNextActivityWithSave extends AsyncTask<Void, Void, Void>{
        ProgressDialog progress1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress1 = new ProgressDialog(inventories_SecondLvl.this);
            progress1.setMessage("Loading...");
            progress1.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            saveCurrentDataMulti(inventories_SecondLvl.valueHolder);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                if(progress1.isShowing())
                    progress1.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
