package com.backofficecloudapps.prop.inventory.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.inventories.inventories_ZeroLvl;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Phoenix
 */
public class activity_WholeInventoryDeatils extends ActionBarActivity {

    private final static int PROPERTIES_DATA_SIZE = 9;
    private final static int HOUSE_NO = 0;
    private final static int STREET = 1;
    private final static int POST_CODE = 4;
    private final static int DATE = 8;
    private final static int PROPERTY_ID = 1;
    private TextView dateOfPropCreation;
    private TextView selectedPropName;
    private ListView propertiesListView;
    private PreferencesManager sPref;

    private ArrayList<String> properties;
    private ArrayList<String> currentDate;
    private CurrentPropertiesAdapter adapter;
    private Button butNext;
    private int selectedPropertyPos;
    private List<String> propIDs;

    @NonNull
    private db_DesireOutput dbClient = new db_DesireOutput(activity_WholeInventoryDeatils.this);

    private List<String[]> propertyDataList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_inventory);
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

                    Intent intent = new Intent(activity_WholeInventoryDeatils.this, activity_MainMenu.class);
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
        sPref = new PreferencesManager(this);

        properties = new ArrayList<>();
        currentDate = new ArrayList<>();
        getDataFromTable();
        dateOfPropCreation = (TextView) findViewById(R.id.new_inventory_current_date);
        selectedPropName = (TextView) findViewById(R.id.new_inventory_prop_name);
        propertiesListView = (ListView) findViewById(R.id.new_inventory_title_prop_list);
        butNext = (Button) findViewById(R.id.new_inventory_add_next_but);
        propertiesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        propertiesListView.setSelector(R.color.custom);
        propertiesListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                dateOfPropCreation.setText("Date: " + getCurrentDate());
                selectedPropName.setText(properties.get(position));
                sPref.setStringValue(Constantaz.selected_prop_ID, ""+propIDs.get(position));
                butNext.setEnabled(true);
                selectedPropertyPos = position;
            }
        });
        adapter = new CurrentPropertiesAdapter();
        propertiesListView.setAdapter(adapter);
    }

    /**
     * Gets all data about property
     */
    private String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c.getTime());
    }


    private void getDataFromTable() {
        propertyDataList=new ArrayList<>();
        String accountId=sPref.getStringValue(Constantaz.userID, "null");

        try {
            dbClient.Open();
            Cursor c;
            c = dbClient.fetchIncomingDetails(accountId);

            while(c.moveToNext()){
                String[] tempDataArray=new String[PROPERTIES_DATA_SIZE];

                tempDataArray[0]=c.getString(7); // HOUSENO
                tempDataArray[1]=c.getString(1); // STREET
                tempDataArray[2]=c.getString(2); // CITY
                tempDataArray[3]=c.getString(8); // POSTCODE
                tempDataArray[4]=c.getString(3); // COUNTRY
                tempDataArray[5]=c.getString(11);// PROPERTYTYPE
                int value = c.getInt(4);
                tempDataArray[6]=value+"";       // BEDROOMS
                tempDataArray[7]=c.getString(6); // FURNISHED
                tempDataArray[8]=c.getString(5); // CURRENTDATE

                propertyDataList.add(tempDataArray);
            }

            c.close();
            dbClient.close();

            properties.clear();

            for(String[] rowDB : propertyDataList){
                properties.add(rowDB[HOUSE_NO] + " " +rowDB[STREET] + ", " + rowDB[POST_CODE]);
                currentDate.add(rowDB[DATE]);
            }

            propIDs=getPropertyID();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Back to main menu.
     *
     * @param view the view
     */
    public void backToMainMenu(View view) {
        activity_WholeInventoryDeatils.this.finish();
    }

    /**
     * Add new property.
     *
     * @param view the view
     */
    public void addNewProperty(View view) {
        startActivity(new Intent(activity_WholeInventoryDeatils.this, activity_AddNewProperty.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        getDataFromTable();
        adapter.notifyDataSetChanged();
    }

    /**
     * Go to zero lvl.
     *
     * @param view the view
     */
    public void goToZeroLvl(View view) {

        activity_MainMenu.editValueFlag = false;

        sPref.setStringValue(Constantaz.selected_prop_name, properties.get(selectedPropertyPos));
        String date = currentDate.get(selectedPropertyPos);
        System.out.println("date transfer- "+date);
        sPref.setStringValue(Constantaz.selected_prop_date, getCurrentDate());

        try {
            activity_MainMenu.editValueFlag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(activity_WholeInventoryDeatils.this, inventories_ZeroLvl.class);
        intent.putExtra(Constantaz.selected_prop_date, getCurrentDate());
        startActivity(intent);

    }

    private class CurrentPropertiesAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        /**
         * Instantiates a new Current properties adapter.
         */
        public CurrentPropertiesAdapter() {
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return properties.size();
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
            View view = inflater.inflate(R.layout.new_inventory_prop_list_item, parent, false);
            TextView addressTV = (TextView) view.findViewById(R.id.new_inventory_prop_list_name);
            addressTV.setText("   " + properties.get(position));
            TextView dateTV = (TextView) view.findViewById(R.id.new_inventory_prop_list_date);
            dateTV.setVisibility(View.INVISIBLE);
            dateTV.setText("   " + currentDate.get(position));
            return view;
        }
    }

      /**
     * Get property id list.
     *
     * @return the list
     */
    @NonNull
    public List<String> getPropertyID(){

        dbClient.Open();
        Cursor cursor = dbClient.getWritableDatabase().rawQuery("SELECT BP01_PROPERTY_ID FROM BP01_PROPERTIES", null);
        List<String> propertyCodes=new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                propertyCodes.add(cursor.getString(0));
            }
        }

        cursor.close();
        dbClient.close();

        return propertyCodes;
    }
}
