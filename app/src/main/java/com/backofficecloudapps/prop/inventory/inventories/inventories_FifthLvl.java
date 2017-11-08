package com.backofficecloudapps.prop.inventory.inventories;

/**
 * Created by Phoenix
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

import static com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase.inventory_item_features;
import static com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase.inventory_item_multi_flag;

/**
 * The type Inventories fifth lvl.
 */
public class inventories_FifthLvl extends ActionBarActivity {

    private adapter_CustomDB adapter;
    private ListView listView;
    private List<String> featuresList;
    private List<String> itemCategoriesCode;
    private List<String> itemDataExist;
    private Uri imageUri;
    private int photoID;
    private List<String> currentItemDescriptions;
    public static String[] levelNames;
    private PreferencesManager sPrefs;
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
    private String propertyID;

    private String inventoryCreationDate1;
    private String[] imgPathsplit;
    private String accountId;
    private String zeroLevelItemName;
    private String firstLevelItemName;
    private String secondLevelItemName;
    private String data;
    private String dataInput3;

    String Zero_Item_Code = "";
    String Zero_Item_Item_Features = "";
    String One_Item_Code = "";
    String One_Item_Item_Features = "";
    String Two_Item_Code = "";
    String Two_Item_Item_Features = "";
    String Three_Item_Code;
    String Three_Item_Item_Features = "";
    String Four_Item_Code;
    String Four_Item_Item_Features = "";
    private String Five_Item_Code="";
    String Five_Item_Item_Features = "";

    private String Desc_Item_Code="";
    private String Desc_Item_level="";

    private boolean snapChecker = false;
    public static int lastItem;
    public static List<String> DataTitle ;
    public static List<String> DataValue ;
    public static HashMap<String,String> valueHolder;

    public String inventoryDate="";

    private boolean checkCameraUpdate = true;
    private Button saveBT;
    private Button cameraBT;
    private Button newBT;
    private Button deleteBT;

    private boolean statusMultiPart;
    private String intentValue4="";
    private boolean checkPreviousScreenMultiPart;
    private String multiScreenFlagValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        checkCameraUpdate = true;
        statusMultiPart= false;

        callFxnFor_setContent();

        try{
            checkPreviousScreenMultiPart = getIntent().getBooleanExtra("MultiFlagStatus", false);

            if(checkPreviousScreenMultiPart){
                // previous screen having multi paart function then use use flag as final value
                intentValue4 =multiScreenFlagValue= getIntent().getStringExtra("MultiFlagValue");
                statusMultiPart = checkPreviousScreenMultiPart;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        callFxnForHideVisibleNewbtn();
        callFxn_for_set_listData();


    }



    private void callFxnForHideVisibleNewbtn() {
        /**
         * use for check multiscreen or not
         */
        if(checkDBExistYorN(getIntent().getStringExtra(ConstantazDataBase.inventory_item_category))){
            newBT.setVisibility(View.VISIBLE);
            deleteBT.setVisibility(View.VISIBLE);

        }
        else{
            newBT.setVisibility(View.GONE);
            deleteBT.setVisibility(View.GONE);
        }
        //////////////////////
    }

    private void callFxnFor_setContent() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        photoID = 0;
        imagePath = new String[]{"", "", ""};
        imagePath1 = new String[]{"", "", ""};
        imagePath2 = new String[]{"", "", ""};
        imgPath = new String[3];
        holder = new Integer[3];
        final boolean isThrird = getIntent().getBooleanExtra(Constantaz.isThree, true);
        onTimeInsertData = true;
        insertData =true;
        snapChecker = false;

        setContentView(R.layout.inventories_fifth_lvl);

        saveBT = (Button) findViewById(R.id.saveBT);
        cameraBT = (Button) findViewById(R.id.cameraBT);
        newBT = (Button) findViewById(R.id.newBT);
        deleteBT = (Button) findViewById(R.id.deleteBT);

        txtBallon= (Button) findViewById(R.id.txtBallon);
        txtBallon.setVisibility(View.GONE);

        valueHolder = new LinkedHashMap<>();

        String wholePath = getIntent().getStringExtra(Constantaz.selected_inv_name);
        levelNames = wholePath.split(" > ");

        initialSetUp(this, wholePath);
    }

    @Override
    protected void onRestart() {

   /*     if(checkCameraUpdate){
            callFxnFor_setContent();
            callFxnForHideVisibleNewbtn();
            callFxn_for_set_listData();
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



    private void callFxn_for_set_listData() {

        final String category = getIntent().getStringExtra(ConstantazDataBase.inventory_item_category);

        currentItemDescriptions = new ArrayList<>();
        currentItemDescriptions = getFifthLvlItems(category);

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

        ///////////////////////////// new code for fetch data from table

        dbMain.Open();
        Cursor cursor = dbMain.db.rawQuery("select "+ConstantazDataBase.inventory_item_code+","+ inventory_item_features+","+ConstantazDataBase.inventory_item_category+" from BP02_INVENTORY_ITEM where "+"BP02_INV_ITEM_DESCRIPTION"+"="+"'"+levelNames[0].trim()+"'", null);

        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            for (int i = 0; i < cursor.getCount(); i++) {
                Zero_Item_Code= cursor.getString(0);
                Zero_Item_Item_Features= cursor.getString(1);
            }
        }
        cursor.close();

        Cursor cursor1 = dbMain.db.rawQuery("select "+ConstantazDataBase.inventory_item_code+","+ inventory_item_features+","+ConstantazDataBase.inventory_item_category+" from BP02_INVENTORY_ITEM where "+"BP02_INV_ITEM_DESCRIPTION"+"="+"'"+levelNames[1].trim()+"'"+" and "+"BP02_INV_ITEM_CATEGORY"+"="+"'"+Zero_Item_Item_Features.trim()+"'", null);

        if (cursor1.getCount() > 0) {
            cursor1.moveToNext();
            for (int i = 0; i < cursor1.getCount(); i++) {
                One_Item_Code= cursor1.getString(0);
                One_Item_Item_Features= cursor1.getString(1);
            }
        }
        cursor1.close();

        Cursor cursor2 = dbMain.db.rawQuery("select "+ConstantazDataBase.inventory_item_code+","+ inventory_item_features+","+ConstantazDataBase.inventory_item_category+" from BP02_INVENTORY_ITEM where "+"BP02_INV_ITEM_DESCRIPTION"+"="+"'"+levelNames[2].trim()+"'"+" and "+"BP02_INV_ITEM_CATEGORY"+"="+"'"+One_Item_Item_Features.trim()+"'", null);

        if (cursor2.getCount() > 0) {
            cursor2.moveToNext();
            for (int i = 0; i < cursor2.getCount(); i++) {
                Two_Item_Code= cursor2.getString(0);
                Two_Item_Item_Features= cursor2.getString(1);
            }
        }
        cursor2.close();

        Cursor cursor3 = dbMain.db.rawQuery("select "+ConstantazDataBase.inventory_item_code+","+ inventory_item_features+","+ConstantazDataBase.inventory_item_category+" from BP02_INVENTORY_ITEM where "+"BP02_INV_ITEM_DESCRIPTION"+"="+"'"+levelNames[3].trim()+"'"+" and "+"BP02_INV_ITEM_CATEGORY"+"="+"'"+Two_Item_Item_Features.trim()+"'", null);

        if (cursor3.getCount() > 0) {
            cursor3.moveToNext();
            for (int i = 0; i < cursor3.getCount(); i++) {
                Three_Item_Code= cursor3.getString(0);
                Three_Item_Item_Features= cursor3.getString(1);
            }
        }
        cursor3.close();

        Cursor cursor4 = dbMain.db.rawQuery("select "+ConstantazDataBase.inventory_item_code+","+ inventory_item_features+","+ConstantazDataBase.inventory_item_category+" from BP02_INVENTORY_ITEM where "+"BP02_INV_ITEM_DESCRIPTION"+"="+"'"+levelNames[4].trim()+"'"+" and "+"BP02_INV_ITEM_CATEGORY"+"="+"'"+Three_Item_Item_Features.trim()+"'", null);

        if (cursor4.getCount() > 0) {
            cursor4.moveToNext();
            for (int i = 0; i < cursor4.getCount(); i++) {
                Four_Item_Code= cursor4.getString(0);
                Four_Item_Item_Features= cursor4.getString(1);
            }
        }
        cursor4.close();

        dbMain.Close();


        /////////////////////////////////////////// code for find value of saved data from database and store into listview

        try {
            // check data exist of not in database for set photo count
            if(activity_MainMenu.editValueFlag) {

                String inventoryCreationDate = getCurrentDate();
                inventoryCreationDate1 = inventoryCreationDate.replaceAll("-", "").replaceAll("\\s+", "");

                propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");
                zeroLevelItemName = levelNames[0];
                firstLevelItemName = levelNames[1];
                secondLevelItemName = levelNames[2] + ":" + levelNames[3] + ":" + levelNames[4];

                if (dataExistintempTable3(zeroLevelItemName, firstLevelItemName, secondLevelItemName, inventoryCreationDate1, propertyID,"5")) {
                    String photoCount = getDataExistPhoto(zeroLevelItemName, firstLevelItemName, secondLevelItemName, inventoryCreationDate1, propertyID);

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
            // check data exist of not in database for set photo count
        } catch (Exception e) {
            e.printStackTrace();
        }


        // After find data .. all data merge with hashmap with key and its value.....
        String mValue[];

        mValue = getCodeBycode5th(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, inventoryCreationDate1, propertyID,"5");

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
                c = dbClient.fetchDetailsInventory5(mValue[z], inventoryCreationDate1, propertyID,Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code,"5");
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

        for(int y= 0 ;y<currentItemDescriptions.size();y++){

            valueHolder.put(currentItemDescriptions.get(y).toString().trim(),"");

            for(int r = 0; r<DataTitle.size();r++){
                if(currentItemDescriptions.get(y).toString().trim().equals(DataTitle.get(r))){
                    valueHolder.put(currentItemDescriptions.get(y).toString().trim(),DataValue.get(r).toString());
                }

            }

        }

        /////////////////////////////////////////// code for find value of saved data from database and store into listview

        /**
         * use: check this item value present into db or not
         * Date: 18-March-2017
         */
        String propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");

        dbClient.Open();
        for(String itemCode : itemCategoriesCode){

            Cursor c;
            c = dbClient.fetchFifthlvtDataCodeValue(propertyID, inventoryDate.replaceAll("-",""),""+Zero_Item_Code,One_Item_Code,Two_Item_Code,Three_Item_Code,Four_Item_Code,itemCode);

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

        // After find data .. all data merge with hashmap with key and its value.....

        adapter = new adapter_CustomDB(this, currentItemDescriptions, values, featuresList,"5", DataTitle, DataValue,itemDataExist);
        listView.setAdapter(adapter);
    }

    /**
     * Back to main menu.
     *
     * @param view the view
     */
    public void backToMainMenu(View view){
        inventories_FifthLvl.this.finish();
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
            secondLevelItemName = levelNames[2]+":"+levelNames[3]+":"+levelNames[4];//fifth element... hold third,fourth and fifth element


            data = "";
            String dataInput = "";

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
                    dataInput = dataInput.concat(entry.getValue().trim());
                }

                if(data.length()>0) {

                    /********************old data*********************/

                    /********************new data*********************/
                    String imgPath1 = Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim();
                    imgPath1 = imgPath1.replace("|", ",");
                    imgPathsplit = imgPath1.split(",");

                    dbMain.Open();
                    dbClient.Open();

                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        String data3 = "";
                        dataInput3 = "";

                        data3 = entry.getKey().trim();
                        dataInput3 = entry.getValue().trim();

                        if (entry.getValue().trim().length() > 0) {

                            Cursor cursor5 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_category + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + data3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Four_Item_Item_Features.trim() + "'", null);
                            Five_Item_Code = "";
                            Five_Item_Item_Features = "";
                            if (cursor5.getCount() > 0) {
                                cursor5.moveToNext();
                                for (int i = 0; i < cursor5.getCount(); i++) {
                                    Five_Item_Code = cursor5.getString(0);
                                    Five_Item_Item_Features = cursor5.getString(1);
                                }
                            }
                            cursor5.close();

                            Cursor cursor6 = dbMain.db.rawQuery("select " + ConstantazDataBase.inventory_item_code + "," + inventory_item_features + "," + ConstantazDataBase.inventory_item_lvl + " from BP02_INVENTORY_ITEM where " + "BP02_INV_ITEM_DESCRIPTION" + "=" + "'" + dataInput3.trim() + "'" + " and " + "BP02_INV_ITEM_CATEGORY" + "=" + "'" + Five_Item_Item_Features.trim() + "'", null);
                            Desc_Item_Code="";
                            Desc_Item_level="";

                            if (cursor6.getCount() > 0) {
                                cursor6.moveToNext();
                                for (int i = 0; i < cursor6.getCount(); i++) {
                                    Desc_Item_Code = cursor6.getString(0);
                                    Desc_Item_level = cursor6.getString(2);
                                }
                            }
                            cursor6.close();

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

    //    finish();
        }

    private void call_Fxn_For_negative_3() {
        Boolean isSaveOrNot = false;
        Boolean invDataExist_Desire = false;
        Boolean invDataExist_Temp = false;

        if (dataExistintempTable1(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Five_Item_Code, inventoryCreationDate1, propertyID, "5","5")) {
            invDataExist_Desire = true;
        }

        if (dataExistintempTable3(zeroLevelItemName, firstLevelItemName, secondLevelItemName, inventoryCreationDate1, propertyID, "5")){
            invDataExist_Temp = true;
        }

            // check data exist these both these data or not .. if data exist in db then go to update otherwise goto add
        if (invDataExist_Temp) {

            if (invDataExist_Desire) {

                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.updateDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Desc_Item_Code, "", imgPath[0], imgPath[1], imgPath[2],"5","5");
                updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data);

            } else {
                // do some change
                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Desc_Item_Code, "", imgPath[0], imgPath[1], imgPath[2],"5","5");

                if (invDataExist_Temp){
                    updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data);

                }
                else{
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"5");

                }


            }


        } else {
            // insert data into database
            callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
            dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Desc_Item_Code, "", imgPath[0], imgPath[1], imgPath[2],"5","5");

            insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"5");

        }
    }

    private void call_Fxn_For_not_negative_3() {
        Boolean isSaveOrNot = false;
        Boolean invDataExist_Desire = false;
        Boolean invDataExist_Temp = false;

        if (dataExistintempTable1(Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Five_Item_Code, inventoryCreationDate1, propertyID,"5","5")) {
            invDataExist_Desire = true;
        }

        if (dataExistintempTable3(zeroLevelItemName, firstLevelItemName, secondLevelItemName, inventoryCreationDate1, propertyID, "5")){
            invDataExist_Temp = true;
        }


        // check data exist these both these data or not .. if data exist in db then go to update otherwise goto add
        if (invDataExist_Temp) {
            // data exist then do update

            if (invDataExist_Desire) {

                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.updateDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Five_Item_Code, "", imgPath[0], imgPath[1], imgPath[2],"5","5");
                updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data);

            } else {

                // do some change
                callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
                dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Five_Item_Code, "", imgPath[0], imgPath[1], imgPath[2],"5","5");

                if (invDataExist_Temp){
                    updateDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data);

                }
                else{
                    insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"5");

                }


            }


        } else {
            // insert data into database
            callFxnforInsertDataIntoDocumentTable(inventoryCreationDate1, imgPathsplit);
            dbClient.insertDataIntoInventoryTable(accountId, propertyID, inventoryCreationDate1, "", "", "", dataInput3, Zero_Item_Code, One_Item_Code, Two_Item_Code, Three_Item_Code, Four_Item_Code, Five_Item_Code, "", imgPath[0], imgPath[1], imgPath[2],"5","5");

            insertDataTempInventory(inventoryCreationDate1, propertyID, zeroLevelItemName, firstLevelItemName, secondLevelItemName, data,"5");

        }
    }

    @Override
    protected void onDestroy() {

        Constantaz._5thScreenflag = 1;

        super.onDestroy();
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
            s= "";
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

    /**
     *
     * @param context
     * @param currentSelItem
     */
    private void initialSetUp(@NonNull Context context, String currentSelItem) {
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

                    Intent intent = new Intent(inventories_FifthLvl.this, activity_MainMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });
        }
        final TextView propName = (TextView) findViewById(R.id.inventories_fifth_lvl_prop_name);
        final TextView itemName = (TextView) findViewById(R.id.inventories_fifth_lvl_current_item);
        final TextView currentDate = (TextView) findViewById(R.id.inventories_fifth_lvl_current_date);

        listView = (ListView) findViewById(R.id.inventories_fifth_lvl_list_view);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setBackgroundColor(getResources().getColor(R.color.custom));
        listView.setDividerHeight(7);
        helper = new DataBaseHelper();
        database = helper.getExternalDataBase(getApplicationContext());
        sPrefs = new PreferencesManager(context);
        propName.setText(sPrefs.getStringValue(Constantaz.selected_prop_name, "No data"));
        inventoryDate=""+getCurrentDate();
        currentDate.setText("Date: ".concat(inventoryDate));
        itemName.setText(currentSelItem);
        featuresList = new ArrayList<>();
        itemCategoriesCode=new ArrayList<>();
        itemDataExist= new ArrayList<>();
    }

    /**
     *
     * @param itemCategory
     * @return
     */
    @NonNull
    private List<String> getFifthLvlItems(@NonNull String itemCategory) {
        Cursor cursor = database.rawQuery("SELECT "
                .concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_code))
                .concat(", ".concat(inventory_item_features))
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_lvl)
                .concat("='3'").concat(" AND ")
                .concat(ConstantazDataBase.inventory_item_category)
                .concat("='".concat(itemCategory.concat("'"))), null);
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
                .concat(ConstantazDataBase.inventory_item_lvl)
                .concat("='3'").concat(" AND ")
                .concat(ConstantazDataBase.inventory_item_category)
                .concat("='".concat(itemCategory.concat("'")))
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
     * @param category
     * @return
     */
    @NonNull
    private List<String> wtf(@NonNull String category) {
        Cursor cursor = database.rawQuery("SELECT ".concat(inventory_item_features)
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_category)
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

    /**
     *
     * @param category
     * @return
     */
    @NonNull
    private List<String> getSpinnerItems(@NonNull String category) {
        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_description).concat(" FROM BP02_INVENTORY_ITEM WHERE ").concat(ConstantazDataBase.inventory_item_lvl).concat("='-3'").concat(" AND ").concat(ConstantazDataBase.inventory_item_category).concat("='").concat(category).concat("'"), null);
        List<String> tempList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemDesc = cursor.getString(cursor.getColumnIndex(ConstantazDataBase
                        .inventory_item_description));
                if(itemDesc==null)
                    itemDesc="";
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

                Toast.makeText(inventories_FifthLvl.this,"Maximum of 3 photos per screen allowed", Toast.LENGTH_SHORT).show();
            }
            else{
                String inventoryCreationDate = getCurrentDate();//first element
                String inventoryCreationDate1 = inventoryCreationDate.replaceAll("-","").replaceAll("\\s+", "");//first element

                String propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");//second element
                String zeroLevelItemName = levelNames[0];//third element
                String firstLevelItemName = levelNames[1];//fourth element
                String secondLevelItemName = levelNames[2];//fifth element... hold third,fourth and fifth element
                String thirdLevelItemName =  levelNames[3];
                String fourthLevelItemName =  levelNames[4];

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
                                .concat("/"+ zeroLevelItemName.replace(" ", "") + "-"
                                        + firstLevelItemName.replace(" ", "") + "-"
                                        + secondLevelItemName.replace(" ", "") + "-"
                                        + thirdLevelItemName.replace(" ", "") + "-"
                                        + fourthLevelItemName.replace(" ", "") + "-"
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

        if (adapter != null) {
            saveCurrentData(inventories_FifthLvl.valueHolder);
        }

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

    private void insertDataTempInventory(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName, String secondLevelItemName, String data, String level) {
        if(insertData) {
            insertData =false;
            dbPdfView.Open();
            dbPdfView.insertDataIntoPropertyTable(inventoryCreationDate1,
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
    private void updateDataTempInventory(String inventoryCreationDate1, String propertyID, String zeroLevelItemName, String firstLevelItemName, String secondLevelItemName, String data) {
        dbPdfView.Open();
        dbPdfView.updateDataIntoPropertyTable(inventoryCreationDate1,
                Arrays.toString(imagePath).replace("[", "").replace("]", "").replace(",", "").replace(" ", "").trim(),
                propertyID,
                zeroLevelItemName,
                firstLevelItemName,
                secondLevelItemName,
                data,
                "5"
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
    public boolean dataExistintempTable3(String zerolvlcode, String onelvlcode, String twolvlcode, String date, String propertyId, String level) {


        dbPdfView.Open();
        Cursor c;
        c = dbPdfView.getDataExistOrNotlevel3(propertyId, date, zerolvlcode, onelvlcode, twolvlcode, level,0);

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
     * @param fourlvlcode  the fourlvlcode
     * @param fifthlvlcode the fifthlvlcode
     * @param date         the date
     * @param propertyId   the property id
     * @param level        the level
     * @return the boolean
     */
    public boolean dataExistintempTable1(String zerolvlcode, String onelvlcode, String twolvlcode, String threelvlcode, String fourlvlcode, String fifthlvlcode, String date, String propertyId, String level, String orginalLevel){

        Cursor c;
        c = dbClient.fetchDetailsInventoryTable5rd(zerolvlcode, onelvlcode, twolvlcode, threelvlcode, fourlvlcode, fifthlvlcode, date, propertyId, level,orginalLevel);
        int i=0;
        String valuer[]=new String[c.getCount()];
        while(c.moveToNext()){
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
     * @return the string
     */
    public String getDataExistPhoto(String zerolvlcode, String onelvlcode, String twolvlcode, String date, String propertyId){

        dbPdfView.Open();
        Cursor c;
        c = dbPdfView.getDataExistPhoto(propertyId, date, zerolvlcode,onelvlcode,twolvlcode);
        String photo = "";

        while(c.moveToNext()){
            photo = c.getString(c.getColumnIndex("INVENTORYITEMPHOTOS"));
        }
        c.close();
        dbPdfView.close();
        return  photo;
    }

    /**
     * Get code bycode 5 th string [ ].
     *
     * @param zerolvlcode   the zerolvlcode
     * @param onelvlcode    the onelvlcode
     * @param twolvlcode    the twolvlcode
     * @param threelvlcode  the threelvlcode
     * @param fourthlvlcode the fourthlvlcode
     * @param date          the date
     * @param propertyId    the property id
     * @param level         the level
     * @return the string [ ]
     */
  // for 5th level
  @NonNull
  public String[] getCodeBycode5th(String zerolvlcode, String onelvlcode, String twolvlcode, String threelvlcode, String fourthlvlcode, String date, String propertyId, String level){

      dbClient.Open();
      Cursor c;
      c = dbClient.fetchDetailsInventoryTable4rd2(zerolvlcode, onelvlcode, twolvlcode, threelvlcode, fourthlvlcode, date, propertyId,level);
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

    ///////////////////////////////////// for 5th level

}
