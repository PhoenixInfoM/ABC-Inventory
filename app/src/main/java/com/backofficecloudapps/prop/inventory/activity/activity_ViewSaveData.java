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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.Model.Adresses;
import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.adapter.adapter_ViewData;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.database.db_MainStatic;
import com.backofficecloudapps.prop.inventory.inapp_purchase.IabHelper;
import com.backofficecloudapps.prop.inventory.inapp_purchase.activity_InApptesting;
import com.backofficecloudapps.prop.inventory.inapp_purchase.inapp_util.IabResult;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.other.asynctask_CreatePdfSendEmail;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.itextpdf.text.Font;

import java.util.ArrayList;
import java.util.List;

import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_AFTER_PURCHASE_PDF_COUNT;
import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_PDF_FLAG;


/**
 * The type Activity view save data.
 */
public class activity_ViewSaveData extends ActionBarActivity {

    private ExpandableListView expandableListView;
    private adapter_ViewData adapter;
    private String inventoryName;
    public static String inventoryDate;
    public static String propertyID;
    private List<String> zeroLevelElements;
    private List<String> zeroLevelElementCode;

    private Font bold;
    private Font normal;
    private Font small;
    private Font title;
    @NonNull
    private String titles[] = {"No", "Item", "Description", "Comment", "Picture"};
    private Adresses item;
    private String inventoryDate1;
    public static List<String> thirdData;
    public static List<String> thirdPhotos;
    public static String zerolvlItemvalue = "";
    public static String zerolvlItemvalueCode = "";
    @NonNull
    db_DesireOutput dbClient = new db_DesireOutput(activity_ViewSaveData.this);
    @NonNull
    db_MainStatic dbMain = new db_MainStatic(activity_ViewSaveData.this);

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
        setContentView(R.layout.view_main);

        init();

        mContext= this;
        sharedPreference = mContext.getSharedPreferences(PreferName, Context.MODE_PRIVATE);
       // sharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);

        //////////////////////////////////USE FOR IN APP PURCHAGE??????????????????? START
        /************Setting Up Google Play Billing in the Application***************/

        mHelper = new IabHelper(activity_ViewSaveData.this, base64EncodedPublicKey);

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

        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(@NonNull ExpandableListView parent, View v, int groupPosition, long id) {

                zerolvlItemvalue = zeroLevelElements.get(groupPosition);
                zerolvlItemvalueCode = zeroLevelElementCode.get(groupPosition);

                startActivity(new Intent(activity_ViewSaveData.this,activity_ViewDataByParentClick.class));

                parent.setSelectedGroup(groupPosition);

                return true;
            }
        });
    }

    /**
     * Send by email.
     *
     * @param view the view
     */
    public void sendByEmail(View view) {

        try {
            mHelper.checkUserHaveInAppOrNot_vaibhav(activity_ViewSaveData.this, ITEM_SKU1,ITEM_SKU2,ITEM_SKU3, RC_REQUEST, "testing");
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

            if (sharedPreference.getInt(PreferKey,0) != 0) {
                if (sharedPreference.getInt(PreferKey,0) == CHECK_AFTER_PURCHASE_PDF_COUNT) {

                    new AlertDialog.Builder(mContext)
                            .setMessage("If you have exceeded the maximum limit . Please purchase more to continue.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent t = new Intent(activity_ViewSaveData.this, activity_InApptesting.class);
                                    startActivity(t);

                                }
                            })
                            .show();


                }else{
                    asynctask_CreatePdfSendEmail mailSender = new asynctask_CreatePdfSendEmail(activity_ViewSaveData.this, propertyID, inventoryName, inventoryDate, item);// sent 4 param in ayc class
                    mailSender.execute();

                }
            }
            else{
                asynctask_CreatePdfSendEmail mailSender = new asynctask_CreatePdfSendEmail(activity_ViewSaveData.this, propertyID, inventoryName, inventoryDate, item);// sent 4 param in ayc class
                mailSender.execute();
            }

        //////////////// CONDITION FOR CHECK HAVING ANY SUBSCRIPTION OR NOT //////////////

    }

    /**
     * Back to main menu.
     *
     * @param view the view
     */
    public void backToMainMenu(View view) {
        activity_ViewSaveData.this.finish();
    }

    private void init() {
        bold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
        title = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
        normal = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
        small = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

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

                    Intent intent = new Intent(activity_ViewSaveData.this, activity_MainMenu.class);
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

        final TextView date = (TextView) findViewById(R.id.view_main_current_date);
        final TextView name = (TextView) findViewById(R.id.view_main_prop_name);

        inventoryDate = getIntent().getStringExtra(Constantaz.selected_prop_date);
        propertyID = getIntent().getStringExtra(Constantaz.selected_prop_ID);
        inventoryName = getIntent().getStringExtra(Constantaz.selected_prop_name);
        item = (Adresses) getIntent().getSerializableExtra(Constantaz.KEY_ADDRESS);

        if ((inventoryDate.length()) > 10) {

            inventoryDate1 = inventoryDate.substring(0, 10) + "";
        }
        else{
            inventoryDate1=inventoryDate;
        }

        date.setText(inventoryDate1);
        name.setText(inventoryName);

        expandableListView = (ExpandableListView) findViewById(R.id.view_main_expandable_view);
        expandableListView.setGroupIndicator(null);

        zeroLevelElements = new ArrayList<String>();
        zeroLevelElementCode = new ArrayList<String>();


        dbClient.Open();
        Cursor c;
        c = dbClient.fetchZerolvtDataCode(propertyID, inventoryDate.replaceAll("-",""));

        while(c.moveToNext()){
            if(!zeroLevelElementCode.contains(c.getString(0)))
                zeroLevelElementCode.add(c.getString(0));
        }
        dbClient.close();

        dbMain.Open();
        for(int r =0; r<zeroLevelElementCode.size();r++){
            Cursor c11;
           c11 = dbMain.getInventoryDetailsforzero(zeroLevelElementCode.get(r));

            while(c11.moveToNext()){
                zeroLevelElements.add(c11.getString(0));
            }

        }
        dbMain.Close();

        String zeroLevels = TextUtils.join(", ", zeroLevelElements);
        if (zeroLevelElements != null && !zeroLevelElements.isEmpty()) {
            adapter = new adapter_ViewData(getApplicationContext(), expandableListView, zeroLevelElements);
        } else {
            Toast.makeText(getApplicationContext(), "No saved data", Toast.LENGTH_SHORT).show();
            finish();
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
