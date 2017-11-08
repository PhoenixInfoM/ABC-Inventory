package com.backofficecloudapps.prop.inventory.activity;

/**
 * Created by Phoenix
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.backofficecloudapps.prop.inventory.Model.Adresses;
import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.database.db_PdfView;
import com.backofficecloudapps.prop.inventory.inapp_purchase.IabHelper;
import com.backofficecloudapps.prop.inventory.inapp_purchase.activity_InApptesting;
import com.backofficecloudapps.prop.inventory.inapp_purchase.inapp_util.IabResult;
import com.backofficecloudapps.prop.inventory.inventories.inventories_ZeroLvl;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.other.asynctask_CreatePdfSendEmail;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_AFTER_PURCHASE_PDF_COUNT;
import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_PDF_FLAG;


/**
 * The type Activity inventories.
 */
public class activity_Inventories extends ActionBarActivity {

    private int selectedPosition;
    private InventoryAdapter adapter;
    private List<String> propertyIDs;
    private List<String> addresses;
    private List<String> inventoryDateCreation;
    private Button viewBut, editBut, emailBut, delBut;
    private List<Adresses> items;
    private PreferencesManager sPrefs;
   ListView inventoriesLView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventories);

        /*******code for custom action bar with event****************/
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
            title.setText("  Inventories");
            ImageView home=(ImageView) v.findViewById(R.id.inventories_bar_logo);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(activity_Inventories.this, activity_MainMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });
        }

        findViewById(R.id.inventories_bar_back_arrow).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view){
                finish();
            }
        });
        /*******code for custom action bar with event****************/

        mContext= this;
        sharedPreference = mContext.getSharedPreferences(PreferName, Context.MODE_PRIVATE);
       // sharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);

        //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? START
        /************Setting Up Google Play Billing in the Application***************/

        mHelper = new IabHelper(activity_Inventories.this, base64EncodedPublicKey);

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

        addresses = new ArrayList<>();
        items = new ArrayList<Adresses>();
        propertyIDs = new ArrayList<>();
        inventoryDateCreation = new ArrayList<>();
        sPrefs = new PreferencesManager(mContext);
        adapter = new InventoryAdapter();

        viewBut = (Button) findViewById(R.id.inventories_view_but);
        editBut = (Button) findViewById(R.id.inventories_edit_but);
        emailBut = (Button) findViewById(R.id.inventories_email_but);
        delBut = (Button) findViewById(R.id.inventories_delete_but);
        inventoriesLView = (ListView) findViewById(R.id.inventories_list_view);

        inventoriesLView.setSelector(R.color.custom);
        inventoriesLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                selectedPosition = position;
                viewBut.setEnabled(true);
                editBut.setEnabled(true);
                emailBut.setEnabled(true);
                delBut.setEnabled(true);

                callFxnForEditDetails();

            }
        });
        inventoriesLView.setAdapter(adapter);

        /*inventoriesLView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callFxnForEditDetails();
            }
        });*/
    }

    private void callFxnForEditDetails() {
        try {
            activity_MainMenu.editValueFlag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        sPrefs.setStringValue(Constantaz.NewInventory, "edit");
        Intent intent=new Intent(activity_Inventories.this, inventories_ZeroLvl.class);

        String mData =addresses.get(selectedPosition);
        String mDate="",mAddress="";
        if(mData.contains(":")){
            mAddress = mData.substring(0,mData.indexOf(":"));
            mDate = mData.substring(mData.indexOf(":")+1);
        }

        String inventory_date = mDate;
        intent.putExtra(Constantaz.selected_prop_date, inventory_date);
        intent.putExtra(Constantaz.selected_prop_name, mAddress);
        intent.putExtra(Constantaz.selected_prop_ID, propertyIDs.get(selectedPosition));

        sPrefs.setStringValue(Constantaz.selected_prop_name, mAddress);

        sPrefs.setStringValue(Constantaz.selected_prop_ID, propertyIDs.get(selectedPosition));
        sPrefs.setStringValue(Constantaz.selected_prop_date, mDate);

        intent.putExtra(Constantaz.is_editable, true);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            adapter = new InventoryAdapter();
            inventoriesLView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * Add new inventory.
     *
     * @param view the view
     */
    public void addNewInventory(View view) {

        try {
            activity_MainMenu.editValueFlag = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        sPrefs.setStringValue(Constantaz.current_creation_date, getCurrentDate());
        Intent intent = new Intent(activity_Inventories.this, activity_WholeInventoryDeatils.class);
        if ((propertyIDs != null) && (propertyIDs.size() > 0)) {
            intent.putExtra(Constantaz.selected_prop_name, addresses.get(selectedPosition));
            intent.putExtra(Constantaz.selected_prop_date, inventoryDateCreation.get(selectedPosition));
            intent.putExtra(Constantaz.selected_prop_ID, propertyIDs.get(selectedPosition));
            intent.putExtra(Constantaz.KEY_ADDRESS, items.get(selectedPosition));
        } else {
            intent.putExtra(Constantaz.selected_prop_name, "");
            intent.putExtra(Constantaz.selected_prop_date, "");
            intent.putExtra(Constantaz.selected_prop_ID, "");
            intent.putExtra(Constantaz.KEY_ADDRESS, "");
        }

        startActivity(intent);
     }

    /**
     *
     * @return
     */
    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * Delete inventory.
     *
     * @param view the view
     */
    public void deleteInventory(View view) {

        if (delBut.isEnabled()) {

            db_DesireOutput dbHelper = new db_DesireOutput(activity_Inventories.this);
            dbHelper.Open();
            dbHelper.deletePropertyTable(propertyIDs.get(selectedPosition), inventoryDateCreation.get(selectedPosition).replaceAll("-", ""));
            dbHelper.close();

            db_PdfView dbView = new db_PdfView(activity_Inventories.this);
            dbView.Open();
            dbView.deletePropertyTable(propertyIDs.get(selectedPosition), inventoryDateCreation.get(selectedPosition).replaceAll("-", ""));
            dbView.close();

            addresses.remove(selectedPosition);
            inventoryDateCreation.remove(selectedPosition);
            viewBut.setEnabled(false);
            emailBut.setEnabled(false);
            delBut.setEnabled(false);
            editBut.setEnabled(false);

            try {
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Send by email.
     *
     * @param view the view
     */
    public void sendByEmail(View view) {


        String mData =addresses.get(selectedPosition);
        String mDate="",mAddress="";
        if(mData.contains(":")){
            mAddress = mData.substring(0,mData.indexOf(":"));
            mDate = mData.substring(mData.indexOf(":")+1);
        }

        String inventoryName = mAddress;
        String inventoryDate = mDate;
        String propertyID = propertyIDs.get(selectedPosition);
        Adresses item=items.get(selectedPosition);


        //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? START

        try {
            mHelper.checkUserHaveInAppOrNot_vaibhav(activity_Inventories.this, ITEM_SKU1,ITEM_SKU2,ITEM_SKU3, RC_REQUEST, "testing");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? FINISH

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

                if (sharedPreference.getInt(PreferKey,0) != 0) {
                    if (sharedPreference.getInt(PreferKey,0) == CHECK_AFTER_PURCHASE_PDF_COUNT) {
                        //Toast.makeText(activity_Inventories.this,"You can generate only 3 pdf",Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(mContext)
                                .setMessage("If you have exceeded the maximum limit . Please purchase more to continue.")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent t = new Intent(activity_Inventories.this, activity_InApptesting.class);
                                        startActivity(t);

                                    }
                                })
                                .show();
                    }else{
                        asynctask_CreatePdfSendEmail mailSender = new asynctask_CreatePdfSendEmail(activity_Inventories.this, propertyID, inventoryName, inventoryDate,item);
                        mailSender.execute();

                    }
                }
                else{
                    asynctask_CreatePdfSendEmail mailSender = new asynctask_CreatePdfSendEmail(activity_Inventories.this, propertyID, inventoryName, inventoryDate,item);
                    mailSender.execute();
                }



    }

    /**
     * Edit inventory.
     *
     * @param view the view
     */
    public void editInventory(View view) {

        callFxnForEditDetails();
    }

    /**
     * View inventory.
     *
     * @param view the view
     */
    public void viewInventory(View view) {

        Intent intent = new Intent(activity_Inventories.this, activity_ViewSaveData.class);
        String mData =addresses.get(selectedPosition);
        String mDate="",mAddress="";
        if(mData.contains(":")){
            mAddress = mData.substring(0,mData.indexOf(":"));
            mDate = mData.substring(mData.indexOf(":")+1);
        }

        String inventory_date = mDate;

        intent.putExtra(Constantaz.selected_prop_name, mAddress);
        intent.putExtra(Constantaz.selected_prop_date, inventory_date);
        intent.putExtra(Constantaz.selected_prop_ID, propertyIDs.get(selectedPosition));
        intent.putExtra(Constantaz.KEY_ADDRESS, items.get(selectedPosition));
        startActivity(intent);
    }


    private class InventoryAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        /**
         * Instantiates a new Inventory adapter.
         */
        public InventoryAdapter() {
            someShitActivated();
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return addresses.size();
        }

        @Nullable
        @Override
        public Object getItem(final int position) {
            return null;
        }

        @Override
        public long getItemId(final int position) {
            return 0;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            View view = inflater.inflate(R.layout.inventory_main_list_item, parent, false);
            TextView addressTV = (TextView) view.findViewById(R.id.inventory_main_list_address);

            String data="";
            String mDate="";
            if(addresses.get(position).contains(":")){
                data= addresses.get(position).substring(0, addresses.get(position).indexOf(":"));
                mDate=addresses.get(position).substring(addresses.get(position).indexOf(":")+1);
            }

            addressTV.setText("   " + data);
            TextView dateTV = (TextView) view.findViewById(R.id.inventory_main_list_date);
            String inventory_value = mDate;

            if ((inventory_value.length()) > 11) {

                inventory_value = inventory_value.substring(0, 11) + "";


            }

            System.out.println("inventory value- "+inventory_value);
            dateTV.setText("   " + inventory_value);
            return view;
        }
    }

    /**
     * Back to main menu.
     *
     * @param view the view
     */
    public void backToMainMenu(View view) {
        activity_Inventories.this.finish();
    }

    /**
     * gets data from TABLE_INVENTORIES
     */
    private void someShitActivated() {

        db_DesireOutput dbHelper = new db_DesireOutput(activity_Inventories.this);
        dbHelper.Open();

        /////////////////////////////1///////////////////
        Cursor c;
        c = dbHelper.fetchPropertyID();
        List<String> propertyID = new ArrayList<>();
        while(c.moveToNext()){
            if (!propertyID.contains(c.getString(0)))
                propertyID.add(c.getString(0));
        }
        c.close();
       /////////////////////////////1///////////////////


        /////////////////////////////2///////////////////
        List<String> inventoryData = new ArrayList<>();
        List<String> inventoryDataTemp = new ArrayList<>();

        for (int i = 0; i < propertyID.size(); i++) {
            String propID = propertyID.get(i);
            Cursor c1;
            c1=dbHelper.fetchDetailsById(propID);
            int count = c1.getCount();
             while(c1.moveToNext()){
                 String tempData = c1.getString(1) + ":" + c1.getString(0) + ":" +c1.getString(2);// Address1 = street
                 String tempDataTemp = c1.getString(1) + ":" + c1.getString(0) + ":" +c1.getString(2)+ ":" +c1.getString(3);// Address1 = street

                 if (!inventoryDataTemp.contains(tempDataTemp)) {
                     inventoryData.add(tempData);
                     inventoryDataTemp.add(tempDataTemp);

                 }
             }
            c1.close();

        }
        /////////////////////////////2///////////////////

        /////////////////////////////3///////////////////

        List<String> addressTempList = new ArrayList<>();

        if(!inventoryData.isEmpty()){

            for(int i = 0; i < propertyID.size(); i++){
                Cursor c2;
                c2=dbHelper.getInventoryDate(propertyID.get(i));
                int count = c2.getCount();

                while(c2.moveToNext()){

                    String newDate ="";
                    if(c2.getString(0).length()==8){
                        String year= c2.getString(0).substring(0,4);
                        String month = c2.getString(0).substring(4,6);
                        String day = c2.getString(0).substring(6);
                        newDate = year+"-"+month+"-"+day;
                    }
                    else{
                        newDate = c2.getString(0);
                    }


                    if(!inventoryDateCreation.contains(newDate)  || !propertyIDs.contains(propertyID.get(i)) ){

                        try {
                            addressTempList.add(inventoryData.get(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        propertyIDs.add(propertyID.get(i));
                        inventoryDateCreation.add(newDate);
                    }

                }

                c2.close();

            }

            /////////////////////////////3///////////////////

            dbHelper.close();
            System.out.println("addressTempList-"+addressTempList.toString()+"inventory lis -"+inventoryDateCreation.toString());
            addressSpliter(addressTempList);
        }
    }

    private void addressSpliter(@NonNull List<String> address) {

        for (int i = 0; i < (address.size()) ; i++) {
            String[] spliter = address.get(i).split(":");
            String splitedAddress = spliter[0] + " " + spliter[1] + ", " + spliter[2]+":"+inventoryDateCreation.get(i).toString();

                if(!addresses.contains(splitedAddress)) {
                items.add(new Adresses(spliter[0], spliter[1], spliter[2]));
                addresses.add(splitedAddress);

                  }
        }
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
