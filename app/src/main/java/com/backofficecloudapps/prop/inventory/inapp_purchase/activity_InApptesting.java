package com.backofficecloudapps.prop.inventory.inapp_purchase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.inapp_purchase.inapp_util.IabResult;
import com.backofficecloudapps.prop.inventory.inapp_purchase.inapp_util.Inventory;
import com.backofficecloudapps.prop.inventory.inapp_purchase.inapp_util.Purchase;

import static com.backofficecloudapps.prop.inventory.inapp_purchase.IabHelper.verifyDeveloperPayload;
import static com.backofficecloudapps.prop.inventory.utils.Constantaz.CHECK_PDF_FLAG;


/**
 * Created by Phoenix
 */
public class activity_InApptesting extends ActionBarActivity{

    private ListView lv;
    private static final String TAG ="inAPP";
    public static IabHelper mHelper;
    @NonNull
    String base64EncodedPublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuI0Cs8tGkwKwsUk6m5PCXeOqFh/d6NwgcW9Ag8BhsFnj420g9c281DpCNLDVAsnxh7g5xyLEsjGh41RB1PsKAtDjaDLhcjDQgY0dfn9VGSI3OzH5LuBt344vPlajqdqpn0G23UCuamq0gdUwo9eiMVkJ/7EmehprEEM9i8sZgQPvMw4etpTWdMNjb9F2riQygpLR8p+GN0wu2vvXFqDSp6gkIQzdL4Se/NW6/8pDeUO7yb1sgoRyDHfXiW5izKrBGAXG6aYJ0rmS3ZZETgOeNTPEd2TH1iRaKTzRWR+sedA/i+xsNMzYhwpKhdhC5jL6kR7FcyhiimpDzEfy2vxjwIDAQAB";
    static final String ITEM_SKU = "android.test.purchased";
    static final int RC_REQUEST = 10001;
    public static activity_InApptesting mActivity;
    @NonNull
    private static String mPayload="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.test_inapp);

        // mapping id with layout
        lv = (ListView) findViewById(R.id.listView1);

        mActivity = this;

         // Defined Array values to show in ListView
        String[] values = new String[] { "£1 - Buy 1 Pdf", "£4 - Buy 5 Pdf",
                "£7.5 - Buy 10 pdf", "Cancel" };

        DrawerAdapter adapter = new DrawerAdapter(getApplicationContext(),values);
        lv.setAdapter(adapter);

        // ListView Item Click Listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) lv.getItemAtPosition(position);

                if (position == 0) {

                    //String SKU_INFINITE_GAS="testproduct";
                    String SKU_INFINITE_GAS="android.test.purchased"; // for testing

			      /* TODO: for security, generate your payload here for verification. See the comments on
				         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
				         *        an empty string, but on a production app you should carefully generate this. */
                    mPayload = "1";
                    mHelper.launchPurchaseFlow(activity_InApptesting.this, SKU_INFINITE_GAS, RC_REQUEST,mPurchaseFinishedListener, mPayload);
                }
                if (position == 1) {

                   // String SKU_INFINITE_GAS="testproduc1t";
                    String SKU_INFINITE_GAS="android.test.purchased";

		          /* TODO: for security, generate your payload here for verification. See the comments on
				         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
				         *        an empty string, but on a production app you should carefully generate this. */
                    mPayload = "2";
                    mHelper.launchPurchaseFlow(activity_InApptesting.this, SKU_INFINITE_GAS, RC_REQUEST,mPurchaseFinishedListener, mPayload);
                }
                if (position == 2) {

                    //String SKU_INFINITE_GAS="testproduct2";
                    String SKU_INFINITE_GAS="android.test.purchased";

		        /* TODO: for security, generate your payload here for verification. See the comments on
			         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
			         *        an empty string, but on a production app you should carefully generate this. */
                    mPayload = "3";
                    mHelper.launchPurchaseFlow(activity_InApptesting.this, SKU_INFINITE_GAS, RC_REQUEST,mPurchaseFinishedListener, mPayload);

                }

                lv.setVisibility(View.INVISIBLE);
                finish();

            }

        });

        /************Setting Up Google Play Billing in the Application***************/
        mHelper = new IabHelper(this, base64EncodedPublicKey);

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

    }

     /*
    * Implementing the Purchase Finished Listener
    *
    * */

    @NonNull
    public static IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(@NonNull IabResult result, @NonNull Purchase purchase)
        {
            if (result.isFailure()) {
                /**
                 * @ usages: this code is use for consume previous code if user already taken
                 */
                   if(result.getResponse() == 7){
                       consumeItem();
                   }
                /**********************************************************/

               // Handle error
                complain("Error purchasing:(OnIabPurchaseFinishedListener) " + result);
                return;
            }
            else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
            }

        }
    };

    /*
    * Consuming the Purchased Item
    *
    * */

    /****************************start**************************/
    public static void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    @NonNull
    public static IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(@NonNull IabResult result, @NonNull Inventory inventory) {

            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /**
             * @ important: multiple
             * @ usages
             * change  mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),mConsumeFinishedListener);
             * to below code for consume previous code and use can purchanges more then one product with one gmail account
             */

                /*
                 * Check for items we own. Notice that for each purchase, we check
                 * the developer payload to see if it's correct! See
                 * verifyDeveloperPayload().
                 */

            // // Check for gas delivery -- if we own gas, we should fill up the
            // tank immediately
            Purchase gasPurchase = inventory.getPurchase(ITEM_SKU);
            if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                Log.d(TAG, "We have gas. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),mConsumeFinishedListener);
                return;
            }

             /**********************************************************/
        }
    };

    @NonNull
    public static IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase, @NonNull IabResult result) {

                    if (result.isSuccess()) {
                                ///
                        /**
                         * @ usage ..
                         *
                         * check_pdf_flag .. use for increase countity of pdf creation after purchase new pdf for creation
                         *
                         * and another code use for see inapp purchage dialog for purchages user.
                         */
                        CHECK_PDF_FLAG = true;

                        if(mPayload.equalsIgnoreCase("1")){

                            String SKU_INFINITE_GAS="android.test.purchased";
                            mHelper.launchPurchaseFlow(mActivity, SKU_INFINITE_GAS, RC_REQUEST,mPurchaseFinishedListener, mPayload);
                        }
                        if(mPayload.equalsIgnoreCase("2")){

                            String SKU_INFINITE_GAS="android.test.purchased";
                            mHelper.launchPurchaseFlow(mActivity, SKU_INFINITE_GAS, RC_REQUEST,mPurchaseFinishedListener, mPayload);
                        }
                        if(mPayload.equalsIgnoreCase("3")){

                            String SKU_INFINITE_GAS="android.test.purchased";
                            mHelper.launchPurchaseFlow(mActivity, SKU_INFINITE_GAS, RC_REQUEST,mPurchaseFinishedListener, mPayload);
                        }


                    } else {
                        // handle error
                        complain("Error while consuming:(OnConsumeFinishedListener) " + result);
                    }
                }
    };

    /****************************finish**************************/


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

    /***
     *
     *
     * Releasing the IabHelper Instance
     *
     * ****/

    @Override
    public void onDestroy() {
        super.onDestroy();
        //if (mHelper != null) mHelper.dispose();
        //mHelper = null;

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
    * Adapter By Which Set Value into ListView
    *
    * */
    public class DrawerAdapter extends BaseAdapter {
        private Context mContext;
        String[] mThumbIds;

        // Constructor
        public DrawerAdapter(Context c, String[] mPlanetTitles) {
            mContext = c;
            mThumbIds = mPlanetTitles;

        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return mThumbIds[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // LayoutInflater inflate=getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_subscription_adapter, null);

            TextView textview = (TextView) view.findViewById(R.id.title);

            textview.setText(mThumbIds[position]);
            return view;
        }
    }

    ////////////////////////////////////FINISH//////////////////////////
}
