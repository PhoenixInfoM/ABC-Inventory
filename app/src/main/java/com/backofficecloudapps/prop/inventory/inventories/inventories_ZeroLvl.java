package com.backofficecloudapps.prop.inventory.inventories;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.Model.Adresses;
import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.inapp_purchase.activity_InApptesting;
import com.backofficecloudapps.prop.inventory.activity.activity_ViewSaveData;
import com.backofficecloudapps.prop.inventory.adapter.adapter_FirstlvlDatabase;
import com.backofficecloudapps.prop.inventory.database.DataBaseHelper;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.database.db_MainStatic;
import com.backofficecloudapps.prop.inventory.database.db_PdfView;
import com.backofficecloudapps.prop.inventory.inapp_purchase.IabHelper;
import com.backofficecloudapps.prop.inventory.inapp_purchase.inapp_util.IabResult;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.other.asynctask_CreatePdfSendEmail;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_AFTER_PURCHASE_PDF_COUNT;
import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_PDF_FLAG;


/**
 * Created by Phoenix
 */
public class inventories_ZeroLvl extends ActionBarActivity {

    public static String inventoryDate;
    private SQLiteDatabase database;
    private DataBaseHelper helper;
    private ListView lView;
    private Button saveBut, viewBut, emailBut, deleteBut;
    private TextView propertyName, propertyDate;
    private List<String> itemCategories;
    private List<String> itemCategoriesCode;
    private List<String> itemDataExist;
    private String propertyID, inventoryCreationDate;
    private String propNmae;
    private PreferencesManager sPrefs;
    @Nullable
    private Adresses item=null;


    @NonNull
    db_DesireOutput dbHelper = new db_DesireOutput(this);
    @NonNull
    db_PdfView dbView = new db_PdfView(this);
    @NonNull
    db_MainStatic dbHelperAll = new db_MainStatic(inventories_ZeroLvl.this);

    private ArrayList<String> zeroLevelElements;
    private ArrayList<String> zeroLevelElementCode;

    //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? START
    private static final String TAG ="INAPP";
    @Nullable
    public static IabHelper mHelper;
    static final String ITEM_SKU1 = "android.test.purchased";
    static final String ITEM_SKU2 = "android.test.purchased";
    static final String ITEM_SKU3 = "android.test.purchased";
    static final int RC_REQUEST = 10001;
    @NonNull
    String base64EncodedPublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuI0Cs8tGkwKwsUk6m5PCXeOqFh/d6NwgcW9Ag8BhsFnj420g9c281DpCNLDVAsnxh7g5xyLEsjGh41RB1PsKAtDjaDLhcjDQgY0dfn9VGSI3OzH5LuBt344vPlajqdqpn0G23UCuamq0gdUwo9eiMVkJ/7EmehprEEM9i8sZgQPvMw4etpTWdMNjb9F2riQygpLR8p+GN0wu2vvXFqDSp6gkIQzdL4Se/NW6/8pDeUO7yb1sgoRyDHfXiW5izKrBGAXG6aYJ0rmS3ZZETgOeNTPEd2TH1iRaKTzRWR+sedA/i+xsNMzYhwpKhdhC5jL6kR7FcyhiimpDzEfy2vxjwIDAQAB";
    //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? FINISH

    ////////////////////////// use for check created pdf counter/////////////////
    SharedPreferences sharedPreference;
    @NonNull
    public static String PreferName = "CountPdf";
    @NonNull
    public static String PreferKey  = "PDF";
    private Context mContext;
    ////////////////////////// use for check created pdf counter/////////////////

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callFxnFor_setContent();

        callFxn_for_set_listData();

    }

    private void callFxnFor_setContent() {
        setContentView(R.layout.inventories_zero_lvl);

        mContext= this;

        /*********code for set custom action bar******/
        ActionBarBuilder.getInstance()
                .setActionBar(this, getSupportActionBar())
                .setCustomIcon(R.drawable.icon_inventory)
                .showDefaultHomeTitle(false)
                .showDefaultHomeIcon(false)
                .showYourHomeIcon(true)
                .inflateLayout(R.layout.inventories_action_bar, null)
                .showCustomLayout(true);
        if (getSupportActionBar() != null) {
            View v = getSupportActionBar().getCustomView();
            TextView title = (TextView) v.findViewById(R.id.inventories_bar_title);
            ImageView home = (ImageView) v.findViewById(R.id.inventories_bar_logo);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(inventories_ZeroLvl.this, activity_MainMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });
            title.setText("  Inventories");
        }
        /*********code for set custom action bar******/

        //@desc: create object of arraylist use for hold database data
        itemCategories = new ArrayList<>();
        itemCategoriesCode= new ArrayList<>();
        itemDataExist= new ArrayList<>();

        //@desc: create object of shared preference
        sPrefs = new PreferencesManager(this);

        //@DESC: use for getting selected property id from SP
        propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");
        //@DESC: use for getting selected property name from SP
        propNmae = sPrefs.getStringValue(Constantaz.selected_prop_name, "No data");

        //@desc: use for getting current date
        inventoryCreationDate = getCurrentDate();

        //@desc:@function: use for set property name and property date
        infoViewInit(sPrefs.getStringValue(Constantaz.selected_prop_name, "No data"),sPrefs.getStringValue(Constantaz.selected_prop_date, "No data"));

        //@desc: id mapping with xml layout
        saveBut = (Button) findViewById(R.id.inventories_zero_lvl_save_but);
        viewBut = (Button) findViewById(R.id.inventories_zero_lvl_view_but);
        emailBut = (Button) findViewById(R.id.inventories_zero_lvl_email_but);
        deleteBut = (Button) findViewById(R.id.inventories_zero_lvl_delete_but);


        sharedPreference = mContext.getSharedPreferences(PreferName, Context.MODE_PRIVATE);
        //sharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);

        //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? START
        /************Setting Up Google Play Billing in the Application***************/

        mHelper = new IabHelper(inventories_ZeroLvl.this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        // mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(@NonNull IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                    complain("In-app Billing setup failed:: " + result);

                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }
        });


        /************Setting Up Google Play Billing in the Application***************/
        //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? FINISH

        //@desc: create object of databse helper class
        helper = new DataBaseHelper();
    }

    @Override
    protected void onRestart() {
        callFxnFor_setContent();
        callFxn_for_set_listData();

        super.onRestart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void callFxn_for_set_listData() {
        //@desc: use for initalize string List , it will hold all "zero label" item category list
        //@desc: use function getZeroLvlItem for getting data from database table
        final List<String> currentInventoryItems = getZeroLvlItems();

        //@desc: use for check all item from database ..if exist or not..if not exist then finish activity
        if (currentInventoryItems == null || currentInventoryItems.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No data in your database", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        /**
         * use: check this item value present into db or not
         * Date: 17-March-2017
         */
        dbHelper.Open();
        for(String itemCode : itemCategoriesCode){

            Cursor c;
            c = dbHelper.fetchZerolvtDataCodeValue(propertyID, inventoryDate.replaceAll("-",""),itemCode);

            if(c.getCount()>0){
                itemDataExist.add("YES");
            }
            else{
                itemDataExist.add("NO");
            }
           c.close();

        }
        dbHelper.close();

        ////////////////////////////////

        /************working on listview with adapter and item click listener***************/
        lView = (ListView) findViewById(R.id.inventories_zero_lvl_list_view);
        lView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lView.setBackgroundColor(getResources().getColor(R.color.custom));
        lView.setDividerHeight(7);
        //@desc: use for set data from database to listview
        lView.setAdapter(new adapter_FirstlvlDatabase(currentInventoryItems, getApplicationContext(),R.layout.zero_lvl_items, R.id.zero_lvl_text,itemDataExist,null, null,R.id.zero_lvl_icon));

        //@desc: onItem click listener at listview item
        lView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

                //@desc: use for getting new inventory data or not
                String s = sPrefs.getStringValue(Constantaz.NewInventory, "");

                if (s.equals("New")) {
                    /*
                    * @desc: putExtra: two parameter: InventoryName, Inventory Categories
                    * @param: Constantaz.selected_inv_name -> hold "inventory name"
                    * @param: ConstantazDataBase.inventory_item_category -> hold "Inventory Categories"
                    * */
                    Intent intent = new Intent(inventories_ZeroLvl.this, inventories_FirstLvl.class);
                    intent.putExtra(Constantaz.selected_inv_name, currentInventoryItems.get(position));
                    intent.putExtra(ConstantazDataBase.inventory_item_category, itemCategories.get(position));
                    intent.putExtra("ZeroLevelCode",""+itemCategoriesCode.get(position));
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(inventories_ZeroLvl.this, inventories_FirstLvl.class);
                    intent.putExtra(Constantaz.selected_inv_name, currentInventoryItems.get(position));
                    intent.putExtra(ConstantazDataBase.inventory_item_category, itemCategories.get(position));
                    intent.putExtra("ZeroLevelCode",""+itemCategoriesCode.get(position));
                    startActivity(intent);
                }

            }
        });
    }

    /************
     * working on listview with adapter and item click listener @param view the view
     */

    public void backToMainMenu(View view) {
        inventories_ZeroLvl.this.finish();
    }

    /**
     * get all elements of zero level fetch from database
     *
     * @return list of items with description
     */
    @NonNull
    private List<String> getZeroLvlItems() {
        SQLiteDatabase database = helper.getExternalDataBase(this);

        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_description)
                .concat(", ".concat(ConstantazDataBase.inventory_item_code))
                .concat(", ".concat(ConstantazDataBase.inventory_item_category))
                .concat(" FROM BP02_INVENTORY_ITEM WHERE ").concat(ConstantazDataBase.inventory_item_lvl)
                .concat("='0'"), null);
        List<String> tempList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String s = cursor.getInt(cursor.getColumnIndex(ConstantazDataBase
                        .inventory_item_category)) + "";

                String itemDesc = cursor.getString(cursor.getColumnIndex(ConstantazDataBase
                        .inventory_item_description));
                String itemCategory = cursor.getString(cursor.getColumnIndex(ConstantazDataBase
                        .inventory_item_category));
                String itemCategoryCode = cursor.getString(cursor.getColumnIndex(ConstantazDataBase
                        .inventory_item_code));

                itemCategories.add(itemCategory);
                tempList.add(itemDesc);
                itemCategoriesCode.add(itemCategoryCode);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return tempList;
    }

    //@function: use for set propertyName and PropertyDate
    private void infoViewInit(String propName, String date) {

        propertyDate = (TextView) findViewById(R.id.inventories_zero_lvl_current_date);
        propertyName = (TextView) findViewById(R.id.inventories_zero_lvl_prop_name);

        propertyName.setText(propName);

        if (sPrefs.getStringValue(Constantaz.NewInventory, "").equals("edit")) {
            inventoryDate = getIntent().getStringExtra(Constantaz.selected_prop_date);

            if ((inventoryDate.length()) > 16) {

                inventoryDate = inventoryDate.substring(0, 16) + "";


            }
            propertyDate.setText("Date: ".concat(inventoryDate));
        } else {

            inventoryDate = inventoryCreationDate;
            propertyDate.setText("Date: ".concat(inventoryCreationDate));
        }

    }

    //@function: use for getting current date
    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * Save current inventory.
     *
     * @param view the view
     */
    public void saveCurrentInventory(View view) {

        inventories_ZeroLvl.this.finish();
    }

    /**
     * View current inventory.
     *
     * @param view the view
     */
    public void viewCurrentInventory(View view) {
        Intent intent = new Intent(inventories_ZeroLvl.this, activity_ViewSaveData.class);
        intent.putExtra(Constantaz.selected_prop_name, propNmae);
        intent.putExtra(Constantaz.selected_prop_date, inventoryDate);
        intent.putExtra(Constantaz.selected_prop_ID, propertyID);
        startActivity(intent);
    }

    /**
     * Send by email current inventory.
     *
     * @param view the view
     */

    public void sendByEmailCurrentInventory(View view) {

        zeroLevelElements = new ArrayList<String>();
        zeroLevelElementCode = new ArrayList<String>();

        boolean checkDataExist = false;
        dbHelper.Open();
        Cursor c;
        c = dbHelper.fetchZerolvtDataCode(propertyID, inventoryDate.replaceAll("-",""));

        while(c.moveToNext()){
            if(!zeroLevelElementCode.contains(c.getString(0)))
                zeroLevelElementCode.add(c.getString(0));
        }
        c.close();
        dbHelper.close();

        dbHelperAll.Open();
        for(int r =0; r<zeroLevelElementCode.size();r++){
            Cursor c11;
            c11 = dbHelperAll.getInventoryDetailsforzero(zeroLevelElementCode.get(r));

            while(c11.moveToNext()){
                zeroLevelElements.add(c11.getString(0));
            }
            c11.close();
        }
        dbHelperAll.Close();

        if (zeroLevelElements != null && !zeroLevelElements.isEmpty()) {
            checkDataExist = true;
        } else {
            checkDataExist = false;
            Toast.makeText(getApplicationContext(), "No saved data", Toast.LENGTH_SHORT).show();

        }

        if(checkDataExist) {

            try {
                mHelper.checkUserHaveInAppOrNot_vaibhav(inventories_ZeroLvl.this, ITEM_SKU1, ITEM_SKU2, ITEM_SKU3, RC_REQUEST, "testing");

            } catch (Exception e) {
                e.printStackTrace();
            }

            //////////////// CONDITION FOR CHECK HAVING ANY SUBSCRIPTION OR NOT //////////////
            /**
             * @ check condotion for purchase sku 1 and exceed global limit according to purchanges
             */
            if(IabHelper.VALUE == 1){
                //ALREADY TAKEN -1
                if (sharedPreference.getInt(PreferKey, 0) != 0) {
                    int sharedData = sharedPreference.getInt(PreferKey, 0);

                    if(CHECK_PDF_FLAG){
                        CHECK_PDF_FLAG= false;
                        CHECK_AFTER_PURCHASE_PDF_COUNT=sharedData+1;
                    }
                }
            }
            else if(IabHelper.VALUE == 2){
                //ALREADY TAKEN -2
                if (sharedPreference.getInt(PreferKey, 0) != 0) {
                    int sharedData = sharedPreference.getInt(PreferKey, 0);

                    if(CHECK_PDF_FLAG){
                        CHECK_PDF_FLAG= false;
                        CHECK_AFTER_PURCHASE_PDF_COUNT=sharedData+5;
                    }
                }

            }
            else if(IabHelper.VALUE == 3){
                //ALREADY TAKEN -3
                if (sharedPreference.getInt(PreferKey, 0) != 0) {
                    int sharedData = sharedPreference.getInt(PreferKey, 0);

                    if(CHECK_PDF_FLAG){
                        CHECK_PDF_FLAG= false;
                        CHECK_AFTER_PURCHASE_PDF_COUNT=sharedData+10;
                    }
                }

            }

                /**
                 *
                 * code for check user create how many time pdf
                 *
                 */

               if (sharedPreference.getInt(PreferKey, 0) != 0) {
                    if (sharedPreference.getInt(PreferKey, 0) == CHECK_AFTER_PURCHASE_PDF_COUNT) {
                       // Toast.makeText(inventories_ZeroLvl.this,"You can generate only 3 pdf",Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(mContext)
                                .setMessage("If you have exceeded the maximum limit . Please purchase more to continue.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent t = new Intent(inventories_ZeroLvl.this, activity_InApptesting.class);
                                        startActivity(t);

                                    }
                                })
                                .show();
                    } else {
                        asynctask_CreatePdfSendEmail mailSender = new asynctask_CreatePdfSendEmail(inventories_ZeroLvl.this, propertyID, propNmae, inventoryDate, item);// sent 4 param in ayc class ### changes: inventoryDate
                        mailSender.execute();

                    }
                } else {
                    asynctask_CreatePdfSendEmail mailSender = new asynctask_CreatePdfSendEmail(inventories_ZeroLvl.this, propertyID, propNmae, inventoryDate, item);// sent 4 param in ayc class ### changes: inventoryDate
                    mailSender.execute();
                }

            }

            //////////////// CONDITION FOR CHECK HAVING ANY SUBSCRIPTION OR NOT //////////////

    }

    /**
     * Delete current inventory.
     *
     * @param view the view
     */
//@onclick: click on Delete button
    //@desc: method call in xml file with onClick parameter
    //@desc: deleted data from invantory table with ID and DATE
    public void deleteCurrentInventory(View view) {

        dbHelper.Open();
        dbHelper.deleteInventoryCocumentTable(propertyID, inventoryCreationDate.replace("-",""));
        dbHelper.close();


        dbView.Open();
        dbView.deletePropertyTable(propertyID, inventoryCreationDate.replace("-", ""));
        dbView.close();

        dbHelper.Open();
        Cursor cur12;
        cur12 = dbHelper.fetchDetailsHouseNo(propertyID);
        String HouseNo ="";
        while(cur12.moveToNext()){
            HouseNo =cur12.getString(cur12.getColumnIndex("BP01_HOUSE_NO"));
        }
        cur12.close();
        dbHelper.close();

        String inventoryName ="",PostName="";

        try {
            inventoryName = propNmae.trim().split(" ")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            PostName = propNmae.trim().split(" ")[4];
        } catch (Exception e) {
            e.printStackTrace();
        }

        String currentInventoryDateandTime=inventoryDate.replace("-","")+"-"+PostName+"-"+inventoryName;

        String fileName = "INV-" + currentInventoryDateandTime + ".pdf";


        String inventoryCreationDate1 = inventoryCreationDate.replaceAll("-","").replaceAll("\\s+", "");//first element

        File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                "/ABCInventory/".concat(fileName));

        File photo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                "/ABCInventory/".concat(inventoryCreationDate1)
                        .concat("/" +(HouseNo+"-"+propertyID).replace(" ", "")));


        if (photo.exists()) {
            String deleteCmd = "rm -r " +""+photo;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (Exception e) {

            }
        }

        if (pdfFile.exists()) {
            String deleteCmd = "rm -r " +""+pdfFile;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (Exception e) {

            }
        }

        inventories_ZeroLvl.this.finish();
    }


    //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? START

    /***
     *
     *
     * Releasing the IabHelper Instance
     *
     * ****/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    /*
    *
    * print complaint
    *
    * */
    public static void complain(String message) {
        Log.e(TAG, "****Error: " + message);
        // alert("Error: " + message);
    }

     /*
    *    Implementing the onActivityResult Method
    * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? FINISH


}
