package com.backofficecloudapps.prop.inventory.inventories;

/**
 * Created by Phoenix
 */

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
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import static com.backofficecloudapps.prop.inventory.utils.Constantaz._4thScreenflag;
import static com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase.inventory_item_category;
import static com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase.inventory_item_features;
import static com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase.inventory_item_multi_flag;

/**
 * The type Inventories third lvl.
 */
public class inventories_ThirdLvl extends ActionBarActivity {

    private adapter_CustomDB adapter;
    public static ListView listView;
    private List<String> featuresList;
    private List<String> itemCategoriesCode;
    private List<String> itemDataExist;

    private Uri imageUri;
    private int photoID;
    private List<String> currentItemDescriptions;
    public static String[] levelNames;
    private PreferencesManager sPrefs;
    private HashMap<String, String> map;
    private String[] imagePath;
    private String[] imagePath1;
    private String[] imagePath2;
    private String photoPath;
    private EditText other;
    public String keyOther;
    public String keyComment;
    private EditText comment;
    public String keySpinner;
    private String dateG;
    private Button txtBallon;
    String imgPath[];

    private DataBaseHelper helper;
    @Nullable
    private SQLiteDatabase database;

    @NonNull
    db_MainStatic dbMain = new db_MainStatic(this);
    @NonNull
    db_DesireOutput dbClient = new db_DesireOutput(this);
    @NonNull
    db_PdfView dbPdfView = new db_PdfView(this);
    Integer holder[];
    private boolean onTimeInsertData;
    private boolean insertData;
    public String propertyID="";
    private String[] imgPathsplit;
    private String inventoryCreationDate1;
    private String accountId;
    private String dataInput3;
    private String data;

    String Zero_Item_Code = "";
    String Zero_Item_Item_Features = "";
    String One_Item_Code = "";
    String One_Item_Item_Features = "";
    String Two_Item_Code = "";
   String Two_Item_Item_Features = "";

    private String Three_Item_Code="";
    String Three_Item_Item_Features = "";

    private String Fourth_Item_Code="";
    String Fourth_Item_Item_Features = "";

    private String Desc_Item_Code="";
    private String Desc_Item_level="";

    private String zeroLevelItemName;
    private String firstLevelItemName;
    private String secondLevelItemName;
    private boolean snapChecker = false;
    public static int lastItem;
    public static boolean checkUserComesInv;
    public List<String> DataTitle ;
    public List<String> DataValue ;
    public static HashMap<String,String> valueHolder;

    public String inventoryDate="";
    String wholePath="";

    private boolean checkCameraUpdate = true;
    private Button saveBT;
    private Button cameraBT;
    private Button newBT;
    private Button deleteBT;

    private String intentValue1="";
    private String intentValue2="";
    private String intentValue3="";
    private String intentValue4="";
    private String intentValue5="";
    private String intentValue6="";
    private boolean intentValue7;
    private boolean checkPreviousScreenMultiPart;
    private String multiScreenFlagValue;

    TextView itemName;
    private boolean statusMultiPart = false;
    private RelativeLayout relMultiSArrow;
    private ImageView imgLeftSwip;
    private ImageView imgRightSwip;
    private int mValueMultiPart = 0;
    private Context context;
    private TextView propName;
    private TextView currentDate;
    private String checkPreviousMiltiSceen= "0";
    private String multiscreenlevel="";


    private String mLavel="3";
    private String mScreenMultiPart="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        checkCameraUpdate = true;
        statusMultiPart= false;

        callFxnForStoreINtentData();

        callFxnFor_setContent();

        try{
            checkPreviousScreenMultiPart = getIntent().getBooleanExtra("MultiFlagStatus", false);

            if(checkPreviousScreenMultiPart){
                // previous screen having multi paart function then use use flag as final value
                intentValue4 = multiScreenFlagValue= getIntent().getStringExtra("MultiFlagValue");
                statusMultiPart = checkPreviousScreenMultiPart;
                checkPreviousMiltiSceen = getIntent().getStringExtra("checkPreviousMiltiSceen");
                multiscreenlevel= getIntent().getStringExtra("multiscreenlevel");

                /**
                 * hide new button if previous screen having multipart
                 */
                newBT.setVisibility(View.GONE);
                deleteBT.setVisibility(View.GONE);
                relMultiSArrow.setVisibility(View.GONE);
            }
            else{
                // previous not to multi part screen
                callFxnForHideVisibleNewbtn();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        callFxnForHideVisibleNewbtn();
        initialSetUp(this, wholePath);
        callFxn_for_set_listData(wholePath);
        onCLickLeftRIghtArrow();

    }

    @Override
    protected void onDestroy() {

        _3rdScreenflag= 1;

        super.onDestroy();
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
                    Intent intent=new Intent(inventories_ThirdLvl.this, inventories_ThirdLvl.class);
                    intent.putExtra(ConstantazDataBase.inventory_item_features, intentValue1);
                    intent.putExtra(Constantaz.selected_inv_name,intentValue2);
                    intent.putExtra(inventory_item_category,intentValue5);
                    intent.putExtra("shared1",intentValue3) ;
                    intent.putExtra("checkPreviousMiltiSceen",  ""+intentValue6);
                    intent.putExtra("multiscreencounter",""+(Integer.parseInt(intentValue4)-1));
                    intent.putExtra("MultiFlagStatus", intentValue7);//statusMultiPart
                    intent.putExtra("MultiFlagValue",  ""+intentValue4);
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
                    Intent intent=new Intent(inventories_ThirdLvl.this, inventories_ThirdLvl.class);
                    intent.putExtra(ConstantazDataBase.inventory_item_features, intentValue1);
                    intent.putExtra(Constantaz.selected_inv_name,intentValue2);
                    intent.putExtra(inventory_item_category,intentValue5);
                    intent.putExtra("shared1",intentValue3) ;
                    intent.putExtra("checkPreviousMiltiSceen",  ""+intentValue6);
                    intent.putExtra("multiscreencounter",""+(Integer.parseInt(intentValue4)+1));
                    intent.putExtra("MultiFlagStatus", intentValue7);//statusMultiPart
                    intent.putExtra("MultiFlagValue",  ""+intentValue4);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(inventories_ThirdLvl.this, "Data not found", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    private void callFxnForStoreINtentData() {
        try {
            intentValue1 = getIntent().getStringExtra(ConstantazDataBase.inventory_item_features);
            intentValue2 = getIntent().getStringExtra(Constantaz.selected_inv_name);
            intentValue3 = getIntent().getStringExtra("shared1");
            intentValue4 = getIntent().getStringExtra("multiscreencounter");

            intentValue5 = getIntent().getStringExtra(inventory_item_category);
            intentValue6 = getIntent().getStringExtra("checkPreviousMiltiSceen");
            intentValue7 = getIntent().getBooleanExtra("MultiFlagStatus", false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callFxnForHideVisibleNewbtn() {
        /**
         * use for check multiscreen or not
         */
        statusMultiPart = checkDBExistYorN(getIntent().getStringExtra(inventory_item_category));

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
    }

    private void callFxnFor_setContent() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        photoID = 0;
        imagePath = new String[]{"", "", ""};
        imagePath1 = new String[]{"", "", ""};
        imagePath2 = new String[]{"", "", ""};
        final boolean isThrird = getIntent().getBooleanExtra(Constantaz.isThree, true);
        imgPath = new String[3];
        holder = new Integer[3];
        onTimeInsertData = true;
        insertData =true;

        valueHolder = new LinkedHashMap<>();

        snapChecker = false;
        context = this;

        setContentView(R.layout.inventories_third_lvl);

        saveBT = (Button) findViewById(R.id.saveBT);
        cameraBT = (Button) findViewById(R.id.cameraBT);
        newBT = (Button) findViewById(R.id.newBT);
        deleteBT = (Button) findViewById(R.id.deleteBT);
        relMultiSArrow = (RelativeLayout) findViewById(R.id.rel_multi_screen_arrow);
        imgLeftSwip = (ImageView) findViewById(R.id.leftArrow);
        imgRightSwip =(ImageView) findViewById(R.id.rightArrow);

        txtBallon= (Button) findViewById(R.id.txtBallon);
        txtBallon.setVisibility(View.GONE);

        wholePath = getIntent().getStringExtra(Constantaz.selected_inv_name);
        levelNames = wholePath.split(" > ");

        /**
         * use for splite data and add correct data into array
         */
        for(int g= 0; g<levelNames.length; g++){
            if(levelNames[g].contains(":")){
                levelNames[g]=(levelNames[g].split(":"))[0];
            }
        }

        propName = (TextView) findViewById(R.id.inventories_fifth_lvl_prop_name);
        itemName = (TextView) findViewById(R.id.inventories_fifth_lvl_current_item);
        currentDate = (TextView) findViewById(R.id.inventories_fifth_lvl_current_date);
        listView = (ListView) findViewById(R.id.inventories_third_lvl_list_view);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setBackgroundColor(getResources().getColor(R.color.custom));
        listView.setDividerHeight(7);

        helper = new DataBaseHelper();
        database = helper.getExternalDataBase(getApplicationContext());

        sPrefs = new PreferencesManager(context);

        featuresList = new ArrayList<>();
        itemCategoriesCode=new ArrayList<>();
        itemDataExist= new ArrayList<>();

    }


    @Override
    protected void onRestart() {

        if( _4thScreenflag == 1){


            checkCameraUpdate = true;
            statusMultiPart= false;

            callFxnForStoreINtentData();

            callFxnFor_setContent();

            try{
                checkPreviousScreenMultiPart = getIntent().getBooleanExtra("MultiFlagStatus", false);

                if(checkPreviousScreenMultiPart){
                    // previous screen having multi paart function then use use flag as final value
                    intentValue4 = multiScreenFlagValue= getIntent().getStringExtra("MultiFlagValue");
                    statusMultiPart = checkPreviousScreenMultiPart;
                    checkPreviousMiltiSceen = getIntent().getStringExtra("checkPreviousMiltiSceen");
                    multiscreenlevel= getIntent().getStringExtra("multiscreenlevel");

                    /**
                     * hide new button if previous screen having multipart
                     */
                    newBT.setVisibility(View.GONE);
                    deleteBT.setVisibility(View.GONE);
                    relMultiSArrow.setVisibility(View.GONE);
                }
                else{
                    // previous not to multi part screen
                    callFxnForHideVisibleNewbtn();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            callFxnForHideVisibleNewbtn();
            initialSetUp(this, wholePath);
            callFxn_for_set_listData(wholePath);
            onCLickLeftRIghtArrow();

        }

/*        if(checkCameraUpdate){
            callFxnForStoreINtentData();

            callFxnFor_setContent();

            try{
                checkPreviousScreenMultiPart = getIntent().getBooleanExtra("MultiFlagStatus", false);

                if(checkPreviousScreenMultiPart){
                    // previous screen having multi paart function then use use flag as final value
                    intentValue4 = multiScreenFlagValue= getIntent().getStringExtra("MultiFlagValue");
                    statusMultiPart = checkPreviousScreenMultiPart;
                    checkPreviousMiltiSceen = getIntent().getStringExtra("checkPreviousMiltiSceen");
                    multiscreenlevel= getIntent().getStringExtra("multiscreenlevel");

                    *//**
                     * hide new button if previous screen having multipart
                     *//*
                    newBT.setVisibility(View.GONE);
                    deleteBT.setVisibility(View.GONE);
                    relMultiSArrow.setVisibility(View.GONE);
                }
                else{
                    // previous not to multi part screen
                    callFxnForHideVisibleNewbtn();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            callFxnForHideVisibleNewbtn();
            initialSetUp(this, wholePath);
            callFxn_for_set_listData(wholePath);
            onCLickLeftRIghtArrow();
        }
        else{
            checkCameraUpdate = true;
        }*/

        super.onRestart();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    private void callFxn_for_set_listData(final String wholePath) {


        String level="3";
        if(!checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            level= multiscreenlevel;
        }

        // check data exist of not in database for set photo count
        final String category = getIntent().getStringExtra(inventory_item_category);

        currentItemDescriptions = new ArrayList<>();
        currentItemDescriptions = getThirdLvlItems(category);

        if (currentItemDescriptions == null || currentItemDescriptions.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No Navigation data found.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        final List<String> features = wtf(category);
        final List<List<String>> values = new ArrayList<>();
        for (String name : features) {
                 values.add(getSpinnerItems(name));
        }

        ////////////////////////////////////// new code for setvalue and update value///// start

        dbMain.Open();
        Cursor cursor = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code+ "," + inventory_item_features+ "," + inventory_item_category+ " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'"+ inventories_ThirdLvl.levelNames[0].trim() + "'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            for (int i = 0; i < cursor.getCount(); i++) {
                Zero_Item_Code = cursor.getString(0);
                Zero_Item_Item_Features = cursor.getString(1);
            }
        }
        cursor.close();

        Cursor cursor1 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + inventories_ThirdLvl.levelNames[1].trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Zero_Item_Item_Features.trim() + "'", null);

        if (cursor1.getCount() > 0) {
            cursor1.moveToNext();
            for (int i = 0; i < cursor1.getCount(); i++) {
                One_Item_Code = cursor1.getString(0);
                One_Item_Item_Features = cursor1.getString(1);
            }
        }
        cursor1.close();

        Cursor cursor2 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + inventories_ThirdLvl.levelNames[2].trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + One_Item_Item_Features.trim() + "'", null);

        if (cursor2.getCount() > 0) {
            cursor2.moveToNext();
            for (int i = 0; i < cursor2.getCount(); i++) {
                Two_Item_Code = cursor2.getString(0);
                Two_Item_Item_Features = cursor2.getString(1);
            }
        }
        cursor2.close();
        dbMain.Close();


        //////////////////////////////////////////////////////////////////////////////
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-", "").replaceAll("\\s+", "");
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        dbClient.Open();
        try {
            mValueMultiPart = checkMaxNoFor_Unique_Field_3rd(Zero_Item_Code, One_Item_Code, Two_Item_Code, inventoryCreationDate1, propertyID,level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbClient.close();


        if(statusMultiPart){
            mScreenMultiPart = intentValue4;
        }
        else{
            mScreenMultiPart="0";
        }

        if(!checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            mScreenMultiPart= checkPreviousMiltiSceen;
        }


        // check data exist of not in database for set photo count
        try {
            if(activity_MainMenu.editValueFlag) {
                // comes from inventory



                zeroLevelItemName = levelNames[0];//third element
                firstLevelItemName = levelNames[1];//fourth element
                secondLevelItemName = levelNames[2];//fifth element

                if (dataExistintempTable3(zeroLevelItemName, firstLevelItemName, secondLevelItemName, inventoryCreationDate1, propertyID, level, Integer.parseInt(mScreenMultiPart))) {

                    String photoCount = getDataExistPhoto3(zeroLevelItemName, firstLevelItemName, secondLevelItemName, inventoryCreationDate1, propertyID, level, Integer.parseInt(mScreenMultiPart));

                    if (photoCount.length() > 0) {

                        int counter = 0;
                        if (photoCount.contains("|")) {

                            photoCount = photoCount.replace("|", ",");
                            photoCount = photoCount.substring(0, photoCount.length() - 1);

                            if (photoCount.contains(",")) {
                                String[] photosS = photoCount.split(",");
                                for(int t=0;t<photosS.length;t++){
                                    imagePath2[t]=photosS[t]+ "|";
                                }
                                txtBallon.setVisibility(View.VISIBLE);
                                txtBallon.setText("" + photosS.length);
                            } else {
                                txtBallon.setVisibility(View.VISIBLE);
                                txtBallon.setText("1");
                                imagePath2[0]=photoCount+ "|";
                            }


                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ///////////////////////////////////////
        String mValue[];

        mValue = getCodeBycode(Zero_Item_Code, One_Item_Code, Two_Item_Code, inventoryCreationDate1, propertyID,level,mScreenMultiPart);

        /*******************last rocket.................*/
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

                c = dbClient.fetchDetailsInventory3_multi(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code,""+level,Integer.parseInt(mScreenMultiPart));



            /*    if(statusMultiPart) {

                    c = dbClient.fetchDetailsInventory3_multi(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code,""+level,Integer.parseInt(mScreenMultiPart));


                  *//*  if(checkPreviousMiltiSceen.equalsIgnoreCase("0")) {
                        c = dbClient.fetchDetailsInventory3_multi(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code,level,Integer.parseInt(mScreenMultiPart));

                    }else{
                        c = dbClient.fetchDetailsInventory3_multi(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code,""+level,Integer.parseInt(mScreenMultiPart));

                    }*//*
                }
                else{

                    c = dbClient.fetchDetailsInventory3_multi(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code,""+level,Integer.parseInt(mScreenMultiPart));


                *//*    if(checkPreviousMiltiSceen.equalsIgnoreCase("0")) {
                        c = dbClient.fetchDetailsInventory3(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code,level);

                    }else{
                        c = dbClient.fetchDetailsInventory3_multi(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code,""+level,Integer.parseInt(mScreenMultiPart));

                    }*//*

                }*/

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
        for(int y= 0 ;y<currentItemDescriptions.size();y++){

            valueHolder.put(currentItemDescriptions.get(y).toString().trim(),"");

            for(int r = 0; r<DataTitle.size();r++){
                if(currentItemDescriptions.get(y).toString().trim().equals(DataTitle.get(r))){
                   valueHolder.put(currentItemDescriptions.get(y).toString().trim(),DataValue.get(r).toString());
                }

            }

        }
        ///////////////////////////////////////////// code for add data into hashmap with key and value for manage position flucting and hold runtime data change

        /**
         * use: check this item value present into db or not
         * Date: 18-March-2017
         */
        String propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");

        dbClient.Open();
        for(String itemCode : itemCategoriesCode){

            Cursor c;

            if(statusMultiPart) {
                if(checkPreviousMiltiSceen.equalsIgnoreCase("0")) {
                    c = dbClient.fetchThreelvtDataCodeValue_multi(propertyID, inventoryDate.replaceAll("-",""),""+Zero_Item_Code,One_Item_Code,Two_Item_Code,itemCode,Integer.parseInt(mScreenMultiPart));

                }
                else{
                    c = dbClient.fetchThreelvtDataCodeValue_multi(propertyID, inventoryDate.replaceAll("-",""),""+Zero_Item_Code,One_Item_Code,Two_Item_Code,itemCode,Integer.parseInt(mScreenMultiPart));

                }
            }
            else{
                if(checkPreviousMiltiSceen.equalsIgnoreCase("0")) {
                    c = dbClient.fetchThreelvtDataCodeValue(propertyID, inventoryDate.replaceAll("-",""),""+Zero_Item_Code,One_Item_Code,Two_Item_Code,itemCode);

                }
                else{
                    c = dbClient.fetchThreelvtDataCodeValue_multi(propertyID, inventoryDate.replaceAll("-",""),""+Zero_Item_Code,One_Item_Code,Two_Item_Code,itemCode,Integer.parseInt(mScreenMultiPart));

                }

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

        adapter = new adapter_CustomDB(this, currentItemDescriptions, values, featuresList, "3",DataTitle,DataValue, itemDataExist);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


                Intent intent = new Intent(inventories_ThirdLvl.this, inventories_FourthLvl.class);
                if (levelChecker(featuresList.get(position), tempList)) {

                    intent.putExtra(Constantaz.isThree, true);
                    intent.putExtra(ConstantazDataBase.inventory_item_category, featuresList.get(position));
                    intent.putExtra(ConstantazDataBase.inventory_item_features, featuresList.get(position));
                    intent.putExtra("MultiFlagStatus", statusMultiPart);
                    intent.putExtra("MultiFlagValue",  ""+intentValue4);
                    intent.putExtra("checkPreviousMiltiSceen",  ""+checkPreviousMiltiSceen);
                    intent.putExtra("shared1",intentValue3) ;
                    intent.putExtra("multiscreencounter","1");
                    intent.putExtra("multiscreenlevel","3");
                    intent.putExtra(Constantaz.selected_inv_name, itemName.getText().toString().concat(" > ").concat(currentItemDescriptions.get(position)));
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Back to main menu.
     *
     * @param view the view
     */
    public void backToMainMenu(View view){
        inventories_ThirdLvl.this.finish();
    }

    /**
     * Create one row in TableInventories and fills it with data
     */
    private void saveCurrentData(@Nullable HashMap<String, String> map) {
        onTimeInsertData = true;
        insertData =true;

        accountId=sPrefs.getStringValue(Constantaz.userID, "null");
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-","").replaceAll("\\s+", "");//first element
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        zeroLevelItemName = levelNames[0];//third element
        firstLevelItemName = levelNames[1];//fourth element
        secondLevelItemName = levelNames[2];//fifth element

        data = "";

        if (map != null && !map.isEmpty()) {

            if(snapChecker){
                imagePath=  imagePath1;
            }
            else{
                imagePath=imagePath2;
        }

            /********************old data*********************/
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getValue().trim().length()>0)
                data = data.concat(entry.getKey().trim() + ":" + entry.getValue().trim() + "|");//six element
             }
             /********************old data*********************/

            if(data.length()>0) {

                /********************new data*********************/
                String imgPath1 = Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim();
                imgPath1 = imgPath1.replace("|", ",");
                imgPathsplit = imgPath1.split(",");

                dbMain.Open();
                dbClient.Open();


                try {
                    mValueMultiPart = checkMaxNoFor_Unique_Field_3rd(Zero_Item_Code, One_Item_Code, Two_Item_Code, inventoryCreationDate1, propertyID,"3");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                for (Map.Entry<String, String> entry : map.entrySet()) {

                    String data3 = "";
                    dataInput3 = "";

                    data3 = entry.getKey().trim();//key name
                    dataInput3 = entry.getValue().trim(); // key value



                    if (entry.getValue().trim().length() > 0){

                        Cursor cursor3 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + data3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Two_Item_Item_Features.trim() + "'", null);
                        Three_Item_Code = "";
                        Three_Item_Item_Features = "";
                        if (cursor3.getCount() > 0) {
                            cursor3.moveToNext();
                            Three_Item_Code = cursor3.getString(0);
                            Three_Item_Item_Features =  cursor3.getString(1);
                        }

                        cursor3.close();

                        Cursor cursor4 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_lvl + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + dataInput3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Three_Item_Item_Features.trim() + "'", null);
                        Desc_Item_Code="";
                        Desc_Item_level="";

                        if (cursor4.getCount() > 0) {
                            cursor4.moveToNext();
                            for (int i = 0; i < cursor4.getCount(); i++) {
                                Desc_Item_Code = cursor4.getString(0);
                                Desc_Item_level = cursor4.getString(2);
                            }
                        }
                        cursor4.close();

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
       // finish();

    }

    public int checkMaxNoFor_Unique_Field_3rd(String zerolvlcode, String onelvlcode,String twolvlcode, String date, String propertyId, String level){
        Cursor c;
        c = dbClient.checkMaxNoFor_Unique_Field_3rd(zerolvlcode, onelvlcode,twolvlcode, date, propertyId, level);

        int value= 0;
        String valuer[] = new String[c.getCount()];

        while (c.moveToNext()) {
            value = c.getInt(0);
            return value;
        }

        c.close();
        return value;
    }

    /**
     * Create one row in TableInventories and fills it with data
     */
    private void saveCurrentDataMulti(@Nullable HashMap<String, String> map) {

        onTimeInsertData = true;
        insertData =true;

        accountId=sPrefs.getStringValue(Constantaz.userID, "null");
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-","").replaceAll("\\s+", "");//first element
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        zeroLevelItemName = levelNames[0];//third element
        firstLevelItemName = levelNames[1];//fourth element
        secondLevelItemName = levelNames[2];//fifth element

        data = "";

        if (map != null && !map.isEmpty()) {

            if(snapChecker){
                imagePath=  imagePath1;
            }
            else{
                imagePath=imagePath2;
            }

            /********************old data*********************/
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getValue().trim().length()>0)
                    data = data.concat(entry.getKey().trim() + ":" + entry.getValue().trim() + "|");//six element
            }
            /********************old data*********************/

            if(data.length()>0) {

                /********************new data*********************/
                String imgPath1 = Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim();
                imgPath1 = imgPath1.replace("|", ",");
                imgPathsplit = imgPath1.split(",");

                dbMain.Open();
                dbClient.Open();

                try {
                    mValueMultiPart = checkMaxNoFor_Unique_Field_3rd(Zero_Item_Code, One_Item_Code, Two_Item_Code, inventoryCreationDate1, propertyID,"3");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Map.Entry<String, String> entry : map.entrySet()) {

                    String data3 = "";
                    dataInput3 = "";

                    data3 = entry.getKey().trim();//key name
                    dataInput3 = entry.getValue().trim(); // key value

                    if (entry.getValue().trim().length() > 0){

                        Cursor cursor3 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + data3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Two_Item_Item_Features.trim() + "'", null);
                        Three_Item_Code = "";
                        Three_Item_Item_Features = "";
                        if (cursor3.getCount() > 0) {
                            cursor3.moveToNext();
                            Three_Item_Code = cursor3.getString(0);
                            Three_Item_Item_Features =  cursor3.getString(1);
                        }

                        cursor3.close();

                        Cursor cursor4 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_lvl + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + dataInput3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Three_Item_Item_Features.trim() + "'", null);
                        Desc_Item_Code="";
                        Desc_Item_level="";
                        Fourth_Item_Code = "";
                        Fourth_Item_Item_Features = "";

                        if (cursor4.getCount() > 0) {
                            cursor4.moveToNext();
                            for (int i = 0; i < cursor4.getCount(); i++) {
                                Desc_Item_Code = cursor4.getString(0);
                                Desc_Item_level = cursor4.getString(2);
                                Fourth_Item_Code = cursor4.getString(0);
                                Fourth_Item_Item_Features =  cursor4.getString(1);
                            }
                        }
                        cursor4.close();

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

        Intent intent=new Intent(inventories_ThirdLvl.this, inventories_ThirdLvl.class);
        intent.putExtra(ConstantazDataBase.inventory_item_features, intentValue1);
        intent.putExtra(Constantaz.selected_inv_name,intentValue2);
        intent.putExtra(inventory_item_category,intentValue5);
        intent.putExtra("shared1",intentValue3) ;
        intent.putExtra("checkPreviousMiltiSceen",  ""+intentValue6);
        intent.putExtra("multiscreencounter",""+(Integer.parseInt(intentValue4)+1));
        intent.putExtra("MultiFlagStatus", intentValue7);//statusMultiPart
        intent.putExtra("MultiFlagValue",  ""+intentValue4);
        startActivity(intent);
        finish();

    }

    private void saveCurrentData_M(@Nullable HashMap<String, String> map) {

        onTimeInsertData = true;
        insertData =true;

        accountId=sPrefs.getStringValue(Constantaz.userID, "null");
        String inventoryCreationDate = getCurrentDate();//first element
        inventoryCreationDate1 = inventoryCreationDate.replaceAll("-","").replaceAll("\\s+", "");//first element
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element

        zeroLevelItemName = levelNames[0];//third element
        firstLevelItemName = levelNames[1];//fourth element
        secondLevelItemName = levelNames[2];//fifth element

        data = "";

        if (map != null && !map.isEmpty()) {

            if(snapChecker){
                imagePath=  imagePath1;
            }
            else{
                imagePath=imagePath2;
            }

            /********************old data*********************/
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if(entry.getValue().trim().length()>0)
                    data = data.concat(entry.getKey().trim() + ":" + entry.getValue().trim() + "|");//six element
            }
            /********************old data*********************/

            if(data.length()>0) {

                /********************new data*********************/
                String imgPath1 = Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim();
                imgPath1 = imgPath1.replace("|", ",");
                imgPathsplit = imgPath1.split(",");

                dbMain.Open();
                dbClient.Open();

                try {
                    mValueMultiPart = checkMaxNoFor_Unique_Field_3rd(Zero_Item_Code, One_Item_Code, Two_Item_Code, inventoryCreationDate1, propertyID,"3");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Map.Entry<String, String> entry : map.entrySet()) {

                    String data3 = "";
                    dataInput3 = "";

                    data3 = entry.getKey().trim();//key name
                    dataInput3 = entry.getValue().trim(); // key value

                    if (entry.getValue().trim().length() > 0){

                        Cursor cursor3 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + data3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Two_Item_Item_Features.trim() + "'", null);
                        Three_Item_Code = "";
                        Three_Item_Item_Features = "";
                        if (cursor3.getCount() > 0) {
                            cursor3.moveToNext();
                            Three_Item_Code = cursor3.getString(0);
                            Three_Item_Item_Features =  cursor3.getString(1);
                        }

                        cursor3.close();

                        Cursor cursor4 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_lvl + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + dataInput3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Three_Item_Item_Features.trim() + "'", null);
                        Desc_Item_Code="";
                        Desc_Item_level="";
                        Fourth_Item_Code = "";
                        Fourth_Item_Item_Features = "";

                        if (cursor4.getCount() > 0) {
                            cursor4.moveToNext();
                            for (int i = 0; i < cursor4.getCount(); i++) {
                                Desc_Item_Code = cursor4.getString(0);
                                Desc_Item_level = cursor4.getString(2);
                                Fourth_Item_Code = cursor4.getString(0);
                                Fourth_Item_Item_Features =  cursor4.getString(1);
                            }
                        }
                        cursor4.close();

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

    private void call_Fxn_For_not_negative_3() {
        Boolean invDataExist_Desire = false;
        Boolean invDataExist_Temp = false;

        String level="3";
        if(!checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            level= multiscreenlevel;
        }

        if(statusMultiPart){
            mScreenMultiPart = intentValue4;
        }
        else{
            mScreenMultiPart="0";
        }

        if(!checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            mScreenMultiPart= checkPreviousMiltiSceen;
        }


        /**
         * use for check data exist into "Desire Database"
         */
        String SecondValue=secondLevelItemName;
        if (dataExistintempTable1_multi(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code,Fourth_Item_Code, inventoryCreationDate1, propertyID, level,Integer.parseInt(mScreenMultiPart),"3")) {
            invDataExist_Desire = true;
        }
/*        if(statusMultiPart){
            SecondValue =secondLevelItemName;
            if (dataExistintempTable1_multi(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code,Fourth_Item_Code, inventoryCreationDate1, propertyID, level,Integer.parseInt(mScreenMultiPart))) {
                invDataExist_Desire = true;
            }
        }
        else{
            SecondValue = secondLevelItemName;
            if (dataExistintempTable1(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code,Fourth_Item_Code, inventoryCreationDate1, propertyID, level,Integer.parseInt(mScreenMultiPart))) {
                invDataExist_Desire = true;
            }
        }*/
       ////////////////////////////////

        /**
         * use for check data exist into "Temp database or view database"
         */

        if (dataExistintempTable3(zeroLevelItemName, firstLevelItemName, secondLevelItemName, inventoryCreationDate1, propertyID, level,Integer.parseInt(mScreenMultiPart))) {
            invDataExist_Temp = true;
        }

        //////////////////////////


        // check data exist these both these data or not .. if data exist in db then go to update otherwise goto add
        if (invDataExist_Temp) {

            if(statusMultiPart){
                // if data exist then update database
                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                    dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),""+level);

                 /*   if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                        dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3",Integer.parseInt(mScreenMultiPart));
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),"3");

                    }
                    else{
                        dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),""+level);
                    }*/

                } else {

                    // do some change

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                   //////////////////////////////////////// vaibhav 22-05-2017

                    if (invDataExist_Temp){

                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),""+level);

                  /*      if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3",Integer.parseInt(mScreenMultiPart));
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),"3");

                    }
                    else{
                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),""+level);
                    }*/
                    }
                    else{

                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                        insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

                       /* if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                            dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3",Integer.parseInt(mScreenMultiPart));
                            insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3","Y");

                        }
                        else{
                            dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                            insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

                        }*/
                    }
                   ///////////////////////////////// vaibhav 22-05-2017

                }
            }
            else{
                // if data exist then update database
                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                    dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                    //  updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);


              /*      if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                        dbClient.updateDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3");
                       // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3");
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),"3");

                    }
                    else{
                        dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                      //  updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);

                    }*/


                } else {
                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                    // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);


             /*       if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                        dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3");
                        //updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3");
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),"3");


                    }
                    else{
                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                       // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);

                    }*/


                }
            }

        } else {

            if(statusMultiPart){

                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level, Integer.parseInt(mScreenMultiPart),"3");
                insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");
/*

                if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3",Integer.parseInt(mScreenMultiPart));
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3","Y");

                }
                else{
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level, Integer.parseInt(mScreenMultiPart));
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

                }
*/


            }
            else {
                // insert data into database
                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                    dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],level,"3");
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,level,"N");

                }
                else{
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level, Integer.parseInt(mScreenMultiPart),"3");
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

                }
            }


        }
    }


    private void call_Fxn_For_negative_3() {
        Boolean invDataExist_Desire = false;
        Boolean invDataExist_Temp = false;

        String level="3";
        if(!checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            level= multiscreenlevel;
        }

        if(statusMultiPart){
            mScreenMultiPart = intentValue4;
        }
        else{
            mScreenMultiPart="0";
        }

        if(!checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            mScreenMultiPart= checkPreviousMiltiSceen;
        }

        /**
         * use for check data exist into "Desire Database"
         */

        String SecondValue="";

        SecondValue =secondLevelItemName;
        if (dataExistintempTable1_multi(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code,"", inventoryCreationDate1, propertyID,level,Integer.parseInt(mScreenMultiPart),"3")) {
            invDataExist_Desire = true;
        }
/*        if(statusMultiPart){
            SecondValue =secondLevelItemName;
            if (dataExistintempTable1_multi(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code,"", inventoryCreationDate1, propertyID,level,Integer.parseInt(mScreenMultiPart))) {
                invDataExist_Desire = true;
            }
        }
        else{
            SecondValue = secondLevelItemName;
            if (dataExistintempTable1(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code,"", inventoryCreationDate1, propertyID,level,Integer.parseInt(mScreenMultiPart))) {
                invDataExist_Desire = true;
            }
        }*/
        /////////////////////



        /**
         * use for check data exist into "Temp database or view database"
         */

        if (dataExistintempTable3(zeroLevelItemName, firstLevelItemName, SecondValue, inventoryCreationDate1, propertyID, level,Integer.parseInt(mScreenMultiPart))) {
            invDataExist_Temp = true;
        }

        //////////////////////////

        // check data exist these both these data or not .. if data exist in db then go to update otherwise goto add
        if (invDataExist_Temp) {

            if(statusMultiPart){
                // if data exist then update database
                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                    dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),""+level);


                /*    if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                        dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3",Integer.parseInt(mScreenMultiPart));
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),"3");

                    }
                    else{
                        dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),""+level);

                    }*/



                } else {
                    // do some change

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                    ///////////////////////////////// vaibhav 25-05-2017

                    if (invDataExist_Temp){

                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2], "" + level, Integer.parseInt(mScreenMultiPart),"3");
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data, Integer.parseInt(mScreenMultiPart), "" + level);

                      /*  if (checkPreviousMiltiSceen.equalsIgnoreCase("0")) {
                            dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2], "3", Integer.parseInt(mScreenMultiPart));
                            updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data, Integer.parseInt(mScreenMultiPart), "3");

                        } else {
                            dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2], "" + level, Integer.parseInt(mScreenMultiPart));
                            updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data, Integer.parseInt(mScreenMultiPart), "" + level);

                        }*/
                    }
                    else{

                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                        insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");


                      /*  if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                            dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3",Integer.parseInt(mScreenMultiPart));
                            insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3","Y");

                        }
                        else{
                            dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                            insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

                        }*/
                    }
                      ///////////////////////////////// vaibhav 25-05-2017
                }
            }
            else{

                // if data exist then update database
                if (invDataExist_Desire) {

                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                    dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                    //  updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);

                 /*   if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                        dbClient.updateDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3");
                      //  updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3");
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),"3");

                    }
                    else{
                        dbClient.updateDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                      //  updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);

                    }*/


                } else {
                    callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart),"3");
                    // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                    updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);

                  /*  if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                        dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3");
                       // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3");
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),"3");

                    }
                    else{
                        dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level,Integer.parseInt(mScreenMultiPart));
                       // updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+multiscreenlevel);
                        updateDataTempInventory_multi(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,Integer.parseInt(mScreenMultiPart),level);

                    }*/

                }
            }


        } else {

            if(statusMultiPart){
                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level, Integer.parseInt(mScreenMultiPart),"3");
                insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

          /*      if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],"3",Integer.parseInt(mScreenMultiPart));
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"3","Y");

                }
                else{
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level, Integer.parseInt(mScreenMultiPart));
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

                }*/

            }
            else {
                // insert data into database
                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);

                if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                    dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],level,"3");
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,level,"N");

                }
                else{
                    dbClient.insertDataIntoInventoryTable_multi(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Desc_Item_Code, "", "", imgPath[0], imgPath[1], imgPath[2],""+level, Integer.parseInt(mScreenMultiPart),"3");
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,""+level,"Y");

                }

            }



        }
    }

    /**
     * Gets current date
     *
     * @return string with formatted date
     */
    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        String s= null;
        try {
            s = sPrefs.getStringValue(Constantaz.NewInventory, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = null;
        if(s == null)
            s="";
        if(s.equals("New"))
        {
            df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateG=df.format(c.getTime());}
        else if (s.equals("edit"))
        {
            dateG= inventories_ZeroLvl.inventoryDate;
        }
        else {
            df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateG=df.format(c.getTime());
        }
        return dateG;
    }

    private void initialSetUp(@NonNull Context context, String currentSelItem) {
        try {
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
                ImageView home=(ImageView) v.findViewById(R.id.inventories_bar_logo);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(inventories_ThirdLvl.this, activity_MainMenu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        propName.setText(sPrefs.getStringValue(Constantaz.selected_prop_name, "No data"));
        inventoryDate=""+getCurrentDate();
        currentDate.setText("Date: ".concat(inventoryDate));

        if(statusMultiPart)
            itemName.setText(currentSelItem+": "+intentValue4);
        else
            itemName.setText(currentSelItem);


    }

    @NonNull
    private List<String> getThirdLvlItems(@NonNull String itemCategory) {
        Cursor cursor = database.rawQuery("SELECT "
                .concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_code))
                .concat(", ".concat(inventory_item_features))
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_lvl).concat("='3'").concat(" AND ")
                .concat(inventory_item_category).concat("='".concat(itemCategory.concat("'"))), null);
        List<String> tempList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemDesc = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_description));
                String itemFeature = cursor.getString(cursor.getColumnIndex(inventory_item_features));
                String itemCategoryCode = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_code));

                tempList.add(itemDesc);
                featuresList.add(itemFeature);
                itemCategoriesCode.add(itemCategoryCode);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return tempList;
    }

    private boolean checkDBExistYorN(String itemCategory ) {

        boolean dataExist = false;

        SQLiteDatabase database = helper.getExternalDataBase(getApplicationContext());

        String mSQLQuery = "SELECT "
                .concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_code))
                .concat(", ".concat(inventory_item_features))
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_lvl).concat("='3'").concat(" AND ")
                .concat(inventory_item_category).concat("='".concat(itemCategory.concat("'")))
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

    @NonNull
    private List<String> wtf(@NonNull String category) {

        Cursor cursor = database.rawQuery("SELECT ".concat(inventory_item_features)
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(inventory_item_category)
                .concat("='").concat(category).concat("'"), null);
        List<String> tempList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                String itemCat = cursor.getString(cursor.getColumnIndex(inventory_item_features));
                tempList.add(itemCat);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return tempList;
    }

    @NonNull
    private List<String> getSpinnerItems(@NonNull String category) {
        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_description).concat(" FROM BP02_INVENTORY_ITEM WHERE ").concat(ConstantazDataBase.inventory_item_lvl).concat("='-3'").concat(" AND ").concat(inventory_item_category).concat("='").concat(category).concat("'"), null);
        List<String> tempList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemDesc = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_description));
                if(itemDesc !=null)
                tempList.add(itemDesc);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return tempList;
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

            Toast.makeText(inventories_ThirdLvl.this,"Maximum of 3 photos per screen allowed", Toast.LENGTH_SHORT).show();
        }
        else{

            String inventoryCreationDate = getCurrentDate();//first element
            String inventoryCreationDate1 = inventoryCreationDate.replaceAll("-","").replaceAll("\\s+", "");//first element

            String propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element
            String zeroLevelItemName = levelNames[0];//third element
            String firstLevelItemName = levelNames[1];//fourth element
            String secondLevelItemName = levelNames[2];//fifth element

            dbClient.Open();
            Cursor cur12;
            cur12 = dbClient.fetchDetailsHouseNo(propertyID);
            String HouseNo ="";
            while(cur12.moveToNext()){
                HouseNo =cur12.getString(0);
            }
            cur12.close();
            dbClient.close();

            File photo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                    "/ABCInventory/"
                            .concat(inventoryCreationDate1+"-"+propertyID.replace(" ", ""))
                            .concat("/" + zeroLevelItemName.replace(" ", "")+ "-"
                                    + firstLevelItemName.replace(" ", "")+ "-"
                                    + secondLevelItemName.replace(" ", "")+"-"
                                    + photoID + ".jpg"));
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
            imagePath1[photoID] = selectedImage/*.toString()*/ + "|";
            photoID++;

            /*****use for show ballon******************/
            if(photoID!= 0){
                txtBallon.setVisibility(View.VISIBLE);
                txtBallon.setText(""+photoID);
            }

            else{
                txtBallon.setVisibility(View.GONE);
                txtBallon.setText(""+0);
            }
            /*****use for show ballon******************/
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


        if(statusMultiPart){

            if (adapter != null) {

                saveCurrentData_M(inventories_ThirdLvl.valueHolder);

            }

        }
        else{

            if (adapter != null) {
                saveCurrentData(inventories_ThirdLvl.valueHolder);
            }

        }

    }

    /**
     * use for multi screen save
     * @param view
     */

    public void saveInputedDataMulti(View view) {


        if (adapter != null) {
            new GotoNextActivityWithSave().execute();
           // saveCurrentDataMulti(inventories_ThirdLvl.valueHolder);
        }


    }


    /**
     * use for delete multi screen
     * @param view
     */
    public void deleteMultiScreen(View view){

        new AlertDialog.Builder(inventories_ThirdLvl.this)
                .setMessage("Are you sure to delete entry!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEntry(Zero_Item_Code, One_Item_Code,Two_Item_Code,inventoryCreationDate1, propertyID,"3",Integer.parseInt(intentValue4));
                        deleteTempRecord();
                        deleteUpdateEntry(Zero_Item_Code, One_Item_Code,Two_Item_Code,inventoryCreationDate1, propertyID,"3",Integer.parseInt(intentValue4));
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

    public void deleteEntry(String zerolvlcode, String onelvlcode,String twolvlcode, String date, String propertyId, String level, int multiFlag){
        dbClient.Open();
        try {
            //  Cursor c;
            dbClient.deleteEntry3rd(zerolvlcode, onelvlcode,twolvlcode, date, propertyId, level,multiFlag);
            //c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbClient.close();
    }

    public void deleteTempRecord(){
        dbPdfView.Open();
        try {
            dbPdfView.deleteInventoryData2level_multi_3rd(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName,secondLevelItemName, Integer.parseInt(intentValue4));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dbPdfView.close();
    }

    public void deleteUpdateTempRecord(){
        dbPdfView.Open();
        try {
            dbPdfView.deleteUpdateInventoryData2level_3rd(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName,secondLevelItemName, Integer.parseInt(intentValue4));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dbPdfView.close();
    }


    public void deleteUpdateEntry(String zerolvlcode, String onelvlcode,String twolvlcode, String date, String propertyId, String level, int multiFlag){
        dbClient.Open();
        try {
            //  Cursor c;
            dbClient.deleteUpdateEntry_3rd(zerolvlcode, onelvlcode,twolvlcode, date, propertyId, level,multiFlag);
            //c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbClient.close();
    }

    //@desc: function check next label is available or not
    //select BP02_INV_ITEM_DESCRIPTION,BP02_INV_ITEM_LEVEL  FROM BP02_INVENTORY_ITEM WHERE BP02_INV_ITEM_CATEGORY = 'LIGHTING'
    private boolean levelChecker(@NonNull String feature, @NonNull List<String> descriptions) {

        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_lvl)).concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(inventory_item_category).concat("='".concat(feature.concat("'"))), null);
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

    /**
     * Check data exist boolean.
     *
     * @return the boolean
     */
    public boolean checkDataExist(){

        Cursor c;
        c= dbClient.fetchDetailsDocumentTable();

        while(c.moveToNext()){

            return true;
        }
        c.close();

        return false;
    }

/*
    *
    * fetch data from table
    *
    * @ return integer
    * */
    @Nullable
    public Integer fetchDocId(){

        Cursor c;
        c= dbClient.fetchDetailsDocumentTable();
        Integer data = null;
        while(c.moveToNext()){
            data = c.getInt(0);
        }

        c.close();
        return data;
    }


    private void callFxnforInsertDataIntoDocumentTable(String inventoryCreationDate1, @NonNull String[] imgPathsplit) {
        Integer mData;

        if(onTimeInsertData){

            onTimeInsertData = false;
            for(int h = 0; h<imgPathsplit.length;h++){
                if(checkDataExist()){

                    mData = fetchDocId();
                    mData++;

                    if(holder[0] == null){
                        holder[0] = mData;
                    }
                    else{
                        if(holder[1] == null){
                            holder[1] = mData;
                        }
                        else{
                            holder[2] = mData;
                        }
                    }

                    String imgMimeData = imgPathsplit[h];
                    int lastIndex = imgMimeData.lastIndexOf(".");

                    if(lastIndex != -1){
                        imgMimeData = imgMimeData.substring(lastIndex+1);
                    }
                    else{
                        imgMimeData = "";
                    }

                    // call insert data query
                    if(imgPathsplit[h].length()>0) {

                        dbClient.insertDataIntoDocumentTable(inventoryCreationDate1, "" + mData, imgMimeData, imgPathsplit[h], "PHOTO",propertyID);

                    }
                }
                else{
                    holder[h]=h+1;

                    String imgMimeData = imgPathsplit[h];
                    int lastIndex = imgMimeData.lastIndexOf(".");

                    if(lastIndex != -1){
                        imgMimeData = imgMimeData.substring(lastIndex+1);
                    }
                    else{
                        imgMimeData = "";
                    }


                    // call insert query
                    if(imgPathsplit[h].length()>0) {

                        dbClient.insertDataIntoDocumentTable(inventoryCreationDate1, "" + h + 1, imgMimeData, imgPathsplit[h], "PHOTO",propertyID);

                    }
                }
            }

            if(imgPathsplit.length == 1 && !imgPathsplit[0].equals("")){
                imgPath[0]=""+holder[0];
                imgPath[1]="";
                imgPath[2]="";
            }
            if(imgPathsplit.length == 2){
                imgPath[0]=""+holder[0];
                imgPath[1]=""+holder[1];
                imgPath[2]="";
            }
            if(imgPathsplit.length == 3){
                imgPath[0]=""+holder[0];
                imgPath[1]=""+holder[1];
                imgPath[2]=""+holder[2];
            }

            if(imgPathsplit == null){
                imgPath[0]="";
                imgPath[1]="";
                imgPath[2]="";
            }
            //use for add data into document table...
        }

    }

    /**
     *
     * @param inventoryCreationDate1
     * @param propertyID
     * @param zeroLevelItemName
     * @param firstLevelItemName
     * @param secondLevelItemName
     * @param data
     * @param level
     */

   private void insertDataTempInventory(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName,String secondLevelItemName, String data, String level, String multiScreenFlag) {

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

                if(!checkPreviousMiltiSceen.equalsIgnoreCase("0")){
                    dbMultiFlag= Integer.parseInt(checkPreviousMiltiSceen);
                }
                    dbPdfView.insertDataIntoPropertyTable_Multi(inventoryCreationDate1,
                            Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim(),
                            propertyID,
                            zeroLevelItemName,
                            firstLevelItemName,
                            secondLevelItemName,
                            data,
                            level,
                            dbMultiFlag,
                            multiScreenFlag
                    );
                    dbPdfView.close();

            }
  }


    /**
     *
     * @param inventoryCreationDate1
     * @param propertyID
     * @param zeroLevelItemName
     * @param firstLevelItemName
     * @param secondLevelItemName
     * @param data
     */
    private void updateDataTempInventory(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName, String secondLevelItemName, String data, String level) {
        dbPdfView.Open();
        dbPdfView.updateDataIntoPropertyTable(inventoryCreationDate1,
                Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim(),
                propertyID,
                zeroLevelItemName,
                firstLevelItemName,
                secondLevelItemName,
                data,
                level
        );
        dbPdfView.close();
    }

    private void updateDataTempInventory_multi(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName, String secondLevelItemName, String data, int multiFlag, String level) {
        dbPdfView.Open();
        dbPdfView.updateDataIntoPropertyTable_multi(inventoryCreationDate1,
                Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim(),
                propertyID,
                zeroLevelItemName,
                firstLevelItemName,
                secondLevelItemName,
                data,
                level,
                multiFlag
        );
        dbPdfView.close();
    }


    /**
     * Data existintemp table boolean.
     *
     * @param zerolvlcode the zerolvlcode
     * @param onelvlcode  the onelvlcode
     * @param twolvlcode  the twolvlcode
     * @param date        the date
     * @param propertyId  the property id
     * @param level       the level
     * @return the boolean
     */
    public boolean dataExistintempTable3(String zerolvlcode, String onelvlcode, String twolvlcode, String date, String propertyId, String level, int multiFlagID){

        dbPdfView.Open();
        Cursor c;
        c = dbPdfView.getDataExistOrNotlevel3(propertyId, date, zerolvlcode, onelvlcode, twolvlcode, level,multiFlagID);

        while(c.moveToNext()){
            c.close();
            dbPdfView.close();
            return true;
        }
        dbPdfView.close();
        return false;
    }


    /**
     * Data existintemp table 1 boolean.
     *
     * @param zerolvlcode  the zerolvlcode
     * @param onelvlcode   the onelvlcode
     * @param twolvlcode   the twolvlcode
     * @param threelvlcode the threelvlcode
     * @param date         the date
     * @param propertyId   the property id
     * @param level        the level
     * @return the boolean
     */
    public boolean dataExistintempTable1(String zerolvlcode, String onelvlcode, String twolvlcode, String threelvlcode, String fourthlvlcode, String date, String propertyId, String level, int multFlag){
        //dbClient.Open();
        Cursor c;

        c = dbClient.fetchDetailsInventoryTable3rd2_multi(zerolvlcode, onelvlcode, twolvlcode, threelvlcode,fourthlvlcode,date, propertyId,level, multFlag);

/*
        if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            c = dbClient.fetchDetailsInventoryTable3rd(zerolvlcode, onelvlcode, twolvlcode, threelvlcode,fourthlvlcode,date, propertyId,level);
        }
        else{
            c = dbClient.fetchDetailsInventoryTable3rd2_multi(zerolvlcode, onelvlcode, twolvlcode, threelvlcode,fourthlvlcode,date, propertyId,level, Integer.parseInt(intentValue4));

        }*/


        int i=0;
        String valuer[]=new String[c.getCount()];
        while(c.moveToNext()){
            // dbClient.close();
            c.close();
            return true;
        }

        c.close();
        //dbClient.close();
        return false;
    }

    public boolean dataExistintempTable1_multi(String zerolvlcode, String onelvlcode, String twolvlcode, String threelvlcode, String fourthlvlcode,String date, String propertyId, String level, int multiFlag, String orignalLavel) {
        Cursor c;

       // c = dbClient.fetchDetailsInventoryTable_multi_3rd(zerolvlcode, onelvlcode, twolvlcode,threelvlcode,fourthlvlcode, date, propertyId,level, multiFlag);
        c = dbClient.fetchDetailsDesireDB(zerolvlcode, onelvlcode, twolvlcode,threelvlcode,fourthlvlcode,"",date, propertyId,level, multiFlag,orignalLavel);


        /*
        if(checkPreviousMiltiSceen.equalsIgnoreCase("0")){
            c = dbClient.fetchDetailsInventoryTable_multi_3rd(zerolvlcode, onelvlcode, twolvlcode,threelvlcode,fourthlvlcode, date, propertyId, level,multiFlag);

        }
        else{
            c = dbClient.fetchDetailsInventoryTable_multi_3rd(zerolvlcode, onelvlcode, twolvlcode,threelvlcode,fourthlvlcode, date, propertyId,level, multiFlag);

        }*/
   /*     int
                i = 0;
        String valuer[] = new String[c.getCount()];*/
        while (c.moveToNext()) {
            return true;
        }

        c.close();
        return false;
    }

    /**
     * Get data exist photo string.
     *
     * @param zerolvlcode the zerolvlcode
     * @param onelvlcode  the onelvlcode
     * @param twolvlcode  the twolvlcode
     * @param date        the date
     * @param propertyId  the property id
     * @param level       the level
     * @return the string
     */
    public String getDataExistPhoto3(String zerolvlcode, String onelvlcode, String twolvlcode, String date, String propertyId, String level,int mMultiID){

        dbPdfView.Open();
        Cursor c;
        c = dbPdfView.getDataExistPhotolevel3(propertyId, date, zerolvlcode, onelvlcode, twolvlcode, level,mMultiID);
        String photo = "";

        while(c.moveToNext()){
            photo = c.getString(c.getColumnIndex("INVENTORYITEMPHOTOS"));
        }
        c.close();
        dbPdfView.close();
        return  photo;
    }

    //////////////////////////////////////////////////// new code

    /**
     * Get code bycode string [ ].
     *
     * @param zerolvlcode the zerolvlcode
     * @param onelvlcode  the onelvlcode
     * @param twolvlcode  the twolvlcode
     * @param date        the date
     * @param propertyId  the property id
     * @param level       the level
     * @param mScreenMultiPart
     * @return the string [ ]
     */
// for 3rd level


    @NonNull
    public String[] getCodeBycode(String zerolvlcode, String onelvlcode, String twolvlcode, String date, String propertyId, String level, String mScreenMultiPart){

        dbClient.Open();
        Cursor c;

        c = dbClient.fetchDetailsInventoryTable2_multi(zerolvlcode, onelvlcode, twolvlcode, date, propertyId,  level, Integer.parseInt(mScreenMultiPart));


      /*  if(statusMultiPart){
            if(checkPreviousMiltiSceen.equalsIgnoreCase("0")) {
                c = dbClient.fetchDetailsInventoryTable2_multi(zerolvlcode, onelvlcode, twolvlcode, date, propertyId, level, Integer.parseInt(mScreenMultiPart));
            }
            else{
                c = dbClient.fetchDetailsInventoryTable2_multi(zerolvlcode, onelvlcode, twolvlcode, date, propertyId, level, Integer.parseInt(mScreenMultiPart));
            }
        }
        else{
            if(checkPreviousMiltiSceen.equalsIgnoreCase("0")) {
               // c = dbClient.fetchDetailsInventoryTable2(zerolvlcode, onelvlcode, twolvlcode, date, propertyId,level);
                c = dbClient.fetchDetailsInventoryTable2_multi(zerolvlcode, onelvlcode, twolvlcode, date, propertyId,  level, Integer.parseInt(mScreenMultiPart));

            }
            else{
                c = dbClient.fetchDetailsInventoryTable2_multi(zerolvlcode, onelvlcode, twolvlcode, date, propertyId,  level, Integer.parseInt(mScreenMultiPart));

            }
        }*/

        int i=0;
        String valuer[]=new String[c.getCount()];
        while(c.moveToNext()){
            if(c.getString(0).length()>0)
            valuer[i] = c.getString(0);
            i++;
        }

        c.close();
        dbClient.close();
        return valuer;
    }


    public class GotoNextActivityWithSave extends AsyncTask<Void, Void, Void> {
        ProgressDialog progress1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress1 = new ProgressDialog(inventories_ThirdLvl.this);
            progress1.setMessage("Loading...");
            progress1.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            saveCurrentDataMulti(inventories_ThirdLvl.valueHolder);

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
