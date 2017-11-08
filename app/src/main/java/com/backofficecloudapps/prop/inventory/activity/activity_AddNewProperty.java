package com.backofficecloudapps.prop.inventory.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.other.CustomNumberPicker;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;
import com.lib.debug.Debug;
import com.lib.debug.Debug.LENGTH;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Phoenix
 */

public class activity_AddNewProperty extends ActionBarActivity {
    RelativeLayout bedroomRL;
    private final static int GET_EMAIL_CODE = 0;
    private final static int PROPERTIES_DATA_SIZE = 9;
    private final static String[] PROPERTY_TYPE = {"Detached", "Semi-Detached", "Terrace", "End-of-terrace", "Flat"};
    private final static String[] NO_OF_BEROOMS = {"1", "2", "3", "4","5", "6"};

    private ExpandableListView propDetailedAddressListView;
    private Button furnishedBut;
    private Button unfurnishedBut;
    private Button details_save_but;
    private String[] tableData; //
    private String[] checkChangeTableData={};
    private PreferencesManager sPref;

    private int positionToEdit;
    private boolean doEdit;
    Context context;
    private int mScreenHeight;
    private CustomNumberPicker numberPicker;
    @NonNull
    private String keySpinner = "SaveProperty";
    private int value=0;
    @NonNull
    private ArrayList<String> OldDataList=new ArrayList<>();
    @NonNull
    private db_DesireOutput dbHelper = new db_DesireOutput(activity_AddNewProperty.this);
    private String propertyID;

    /**
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ÐšÐ»Ð°Ð²Ð° Ð½Ðµ Ð·Ð¶Ð¸Ð¼Ð°Ñ” Ð²ÐµÑÑŒ layout
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.property_details);

        // use for manage database
        sPref = new PreferencesManager(getApplicationContext());

        /****************use for set custom action bar*********************/
        ActionBarBuilder.getInstance()
                .setActionBar(getApplicationContext(), getSupportActionBar())
                .setCustomTitle("   Properties")
                .showDefaultHomeTitle(true)
                .showYourHomeIcon(true)
                .showDefaultHomeIcon(false)
                .setCustomIcon(R.drawable.icon_home)
                .inflateLayout(R.layout.properties_action_bar, null)
                .showCustomLayout(true);
        if (getSupportActionBar() != null) {
            View v = getSupportActionBar().getCustomView();
            TextView title = (TextView) v.findViewById(R.id.properties_bar_title);
            ImageView backToPrevScreen = (ImageView) v.findViewById(R.id.properties_bar_back_arrow);
            backToPrevScreen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    createSaveDialog();
                }
            });
            ImageView home = (ImageView) v.findViewById(R.id.properties_bar_logo);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            title.setText(" Property Details");
        }

        findViewById(R.id.properties_bar_back_arrow).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(final View view){
                //@function use for save data
                createSaveDialogOnHome();
            }
        });
        /****************use for set custom action bar*********************/

        //use for pass context
        context = this;

        //find screen height at run time
        mScreenHeight = getWindowManager().getDefaultDisplay().getHeight();

        try {
            // get data from intent
            tableData = getIntent().getStringArrayExtra(Constantaz.property_details);
            doEdit = getIntent().getBooleanExtra(Constantaz.is_editable, false);
            positionToEdit = getIntent().getIntExtra(Constantaz.property_to_edit, -1);
            propertyID = getIntent().getStringExtra("PID");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //@ use for handled null value
        if (tableData == null || tableData.length < 0) {
            tableData = new String[PROPERTIES_DATA_SIZE];
            for (int i = 0; i < tableData.length - 1; i++) {
                tableData[i] = " ";
            }
            tableData[4] = "United Kingdom";
        }

        // use for hold old data
        try {
            if(value==0)
            {
                for(String g:tableData)

                {

                    OldDataList.add(g);
                }
                checkChangeTableData = new String[PROPERTIES_DATA_SIZE];
                checkChangeTableData = tableData;
                value++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Use for show child data into expandable listview
        propDetailedAddressListView = (ExpandableListView) findViewById(R.id.details_address_expandable_list);
        propDetailedAddressListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {// to make it always in expanded form
            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId) {
                propDetailedAddressListView.expandGroup(itemPosition);
                return true;
            }
        });


        // set Adaper for showing data into expandablelistview with custom adapter
        propDetailedAddressListView.setAdapter(new ExpandableDetailsAdapter());
        propDetailedAddressListView.setGroupIndicator(null);

        //Id mapping with xml database
        furnishedBut     = (Button) findViewById(R.id.details_furnished_but);
        unfurnishedBut   = (Button) findViewById(R.id.details_unfurnished_but);
        bedroomRL        = (RelativeLayout) findViewById(R.id.bedroomRL);
        details_save_but = (Button) findViewById(R.id.details_save_but);

        //get value from array for show data into spinner
        final String[] propTypeV = getApplicationContext().getResources().getStringArray(R.array.base_child_values_prop_type_group);
        final Spinner spinner = (Spinner) findViewById(R.id.inventories_third_main_lvl_spinner);
        spinner.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_t_view, propTypeV));

        // set value from database to spinner
        for (int i = 0; i < propTypeV.length; i++) {

            if (tableData[5].toString().equalsIgnoreCase(propTypeV[i])) {
                spinner.setSelection(i);
                break;
            } else {
                spinner.setSelection(0);
            }
        }

        // clickOnitemListener of Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /********use for save data into database*********/
                tableData[5] = PROPERTY_TYPE[position];
                String s = propTypeV[position];
                savePreferences(keySpinner, propTypeV[position]);
                /********use for save data into database*********/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String savedValue = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(keySpinner, "");
                for (int i = 0; i < propTypeV.length; i++) {
                    if (savedValue.equals(propTypeV[i])) {
                        spinner.setSelection(i);
                        break;
                    } else {
                        spinner.setSelection(0);
                    }
                }

            }
        });

        //use for set new value in number picker
        numberPicker = (CustomNumberPicker) findViewById(R.id.third_number_lvl_items_counter);
        if (numberPicker != null) {
            numberPicker.setMaxValue(6);
            numberPicker.setMinValue(1);
            numberPicker.setChangeListener(new CustomNumberPicker.ChangeListener() {
                @Override
                public void change(int newValue) {
                    if (newValue != 0) {
                        tableData[6] = NO_OF_BEROOMS[newValue - 1];
                        Toast.makeText(context, NO_OF_BEROOMS[newValue - 1] + " is selected", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        /************************use for set edit value in number picker***********************/

        try {
            switch (tableData[6]) {
                case "1": {
                    numberPicker.setValue(1);
                }
                break;
                case "2": {
                    numberPicker.setValue(2);
                }
                break;
                case "3": {
                    numberPicker.setValue(3);
                }
                break;
                case "4": {
                    numberPicker.setValue(4);
                }
                break;
                case "5": {
                    numberPicker.setValue(5);
                }
                break;
                case "6": {
                    numberPicker.setValue(6);
                }
                break;
                default: {
                    numberPicker.setValue(0);
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /************************use for set new value in number picker***********************/

        // add current data into field
        tableData[8] = getCurrentDate();

        /********use for set value into radio button**************/
        if (doEdit) {
            if (tableData[7].equals("true")) {
                furnishedBut.performClick();
                furnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_press, 0);
            } else {
                unfurnishedBut.performClick();
                unfurnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_press, 0);
            }
        } else {
            furnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_unpress, 0);
            unfurnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_unpress, 0);
        }
        /********use for set value into radio button**************/

    }

    /**
     *
     * @param key
     * @param value
     */
    // use for save value into sp
    private void savePreferences(String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Gets current date
     *
     * @return string with formatted date
     */
    private String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     *
     * @param view
     */
    public void saveData(View view) {
        {

            Debug debug = new Debug(getApplicationContext(), LENGTH.toast_short, null);
            String accountId = sPref.getStringValue(Constantaz.userID, "null");
            String propertyId = sPref.getStringValue(Constantaz.propertyID, "0");

            if (!Arrays.asList(tableData).contains(" ")) {
                details_save_but.setVisibility(View.INVISIBLE);

                debug.t("Data saved");
                String hashCodeItems = accountId + propertyId;
                int hash = hashCodeItems.hashCode();
                String propertyCode = String.valueOf(hash);
                dbHelper.Open();
                if (doEdit) {
                     dbHelper.updateDataIntoPropertyTable(accountId, propertyID, propertyCode, tableData);
                } else {

                    dbHelper.insertDataIntoPropertyTable(accountId, propertyId, propertyCode, tableData);

                    int nextPropId = Integer.parseInt(propertyId) + 1;
                    sPref.setStringValue(Constantaz.propertyID, String.valueOf(nextPropId));
                }

                dbHelper.close();

                activity_AddNewProperty.this.finish();
            } else debug.t("Fill all fields");
        }
    }

    /**
     * Saves new or edited property to data base
     */
    private void saveProperty() {

        Debug debug = new Debug(getApplicationContext(), LENGTH.toast_short, null);

        String accountId = sPref.getStringValue(Constantaz.userID, "null");
        String propertyId = sPref.getStringValue(Constantaz.propertyID, "0");

        if (!Arrays.asList(tableData).contains(" ")) {
            details_save_but.setVisibility(View.INVISIBLE);
            debug.t("Data saved");
            String hashCodeItems = accountId + propertyId;
            int hash = hashCodeItems.hashCode();
            String propertyCode = String.valueOf(hash);
            dbHelper.Open();
            if (doEdit) {
                dbHelper.updateDataIntoPropertyTable(accountId, propertyID, propertyCode, tableData);
            } else {
                dbHelper.insertDataIntoPropertyTable(accountId, propertyId, propertyCode, tableData);

                int nextPropId = Integer.parseInt(propertyId) + 1;
                sPref.setStringValue(Constantaz.propertyID, String.valueOf(nextPropId));
            }
            dbHelper.close();
            activity_AddNewProperty.this.finish();
        } else debug.t("Fill all fields");

    }

    /**
     * Saves property On Click Home Button
     */
    private void savePropertyOnclickHome() {

        Debug debug = new Debug(getApplicationContext(), LENGTH.toast_short, null);
        String accountId = sPref.getStringValue(Constantaz.userID, "null");
        String propertyId = sPref.getStringValue(Constantaz.propertyID, "0");

        if (!Arrays.asList(tableData).contains(" ")) {
            details_save_but.setVisibility(View.INVISIBLE);
            debug.t("Data saved");
            String hashCodeItems = accountId + propertyId;
            int hash = hashCodeItems.hashCode();
            String propertyCode = String.valueOf(hash);
            dbHelper.Open();
            if (doEdit) {

                dbHelper.updateDataIntoPropertyTable(accountId, propertyID, propertyCode, tableData);
            } else {

                dbHelper.insertDataIntoPropertyTable(accountId, propertyId, propertyCode, tableData);

                int nextPropId = Integer.parseInt(propertyId) + 1;
                sPref.setStringValue(Constantaz.propertyID, String.valueOf(nextPropId));
            }
            dbHelper.close();

            Intent intent = new Intent(activity_AddNewProperty.this, activity_MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else debug.t("Fill all fields");

    }

    public void setFurnished(@NonNull View view) {
        int i = view.getId();
        if (i == R.id.details_furnished_but) {
            furnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_press, 0);
            unfurnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_unpress, 0);
            tableData[7] = "true";
        }
        if (i == R.id.details_unfurnished_but) {
            unfurnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_press, 0);
            furnishedBut.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_unpress, 0);
            tableData[7] = "false";
        }
    }

    /**
     * Asks user to save his data
     */
    private void createSaveDialog() {

         if(OldDataList.get(0).equals(tableData[0])
                && OldDataList.get(1).equals(tableData[1])
                && OldDataList.get(2).equals(tableData[2])
                && OldDataList.get(3).equals(tableData[3])
                && OldDataList.get(4).equals(tableData[4])
                && OldDataList.get(5).equals(tableData[5])
                && OldDataList.get(6).equals(tableData[6])
                && OldDataList.get(7).equals(tableData[7])
                && OldDataList.get(8).equals(tableData[8])){

                 activity_AddNewProperty.this.finish();
        } else {
            AlertDialog.Builder alertDialogBuilder = new Builder(activity_AddNewProperty.this);
            alertDialogBuilder.setTitle("Attention unsaved data");
            alertDialogBuilder.setMessage("Do you want to save your data?")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull final DialogInterface dialog, final int which) {
                            saveProperty();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(@NonNull final DialogInterface dialog, final int which) {
                    dialog.dismiss();
                    activity_AddNewProperty.this.finish();
                }
            }).setCancelable(true);
            alertDialogBuilder.create();
            alertDialogBuilder.show();

        }
    }

    private void createSaveDialogOnHome() {
        AlertDialog.Builder alertDialogBuilder = new Builder(activity_AddNewProperty.this);
        alertDialogBuilder.setTitle("Attention unsaved data");
        alertDialogBuilder.setMessage("Jump to Main Menu! Do you want to save your data first?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull final DialogInterface dialog, final int which) {
                        savePropertyOnclickHome();
                        dialog.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull final DialogInterface dialog, final int which) {
                dialog.dismiss();
                Intent intent = new Intent(activity_AddNewProperty.this, activity_MainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).setCancelable(true);
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    /**
     * This is use for Expandable listView
     */
    private class ExpandableDetailsAdapter extends BaseExpandableListAdapter {

        private List<String> groupValues;
        private HashMap<String, List<String>> groupChilds;
        private LayoutInflater inflater;

        private int selectedRadionButton;
        private int selectedRadioButton;

        public ExpandableDetailsAdapter() {
            super();

            String[] groupV = getApplicationContext().getResources().getStringArray(R.array.base_groups_values);
            String[] addressChildV = getApplicationContext().getResources().getStringArray(R.array.base_child_values_address_group);

            groupValues = new ArrayList<>();
            groupChilds = new HashMap<>();
            List<String> addressChilds = new ArrayList<>();

            Collections.addAll(groupValues, groupV);
            Collections.addAll(addressChilds, addressChildV);

            groupChilds.put(groupV[0], addressChilds);

            Arrays.fill(groupV, null);
            Arrays.fill(addressChildV, null);

            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            selectedRadioButton = -1;

        }

        @Override
        public int getGroupCount() {
            return groupValues.size();
        }

        @Override
        public int getChildrenCount(final int groupPosition) {
            return groupChilds.get(groupValues.get(groupPosition)).size();
        }

        @Nullable
        @Override
        public Object getGroup(final int groupPosition) {
            return null;
        }

        @Nullable
        @Override
        public Object getChild(final int groupPosition, final int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(final int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(final int groupPosition, final int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, final View convertView, final ViewGroup parent) {

            View view = inflater.inflate(R.layout.prop_details_group_item, parent, false);

            if (isExpanded) {
                view.setPadding(0, 0, 0, 0);
            } else {
                view.setPadding(0, 0, 0, 15);
            }
            propDetailedAddressListView.expandGroup(groupPosition);

            TextView groupName = (TextView) view.findViewById(R.id.prop_det_group_item_but);
            groupName.setText(groupValues.get(groupPosition));

            return view;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild, final View convertView, final ViewGroup parent) {

            View view = inflater.inflate(R.layout.prop_details_child_item, parent, false);

            TextView childName = (TextView) view.findViewById(R.id.prop_det_child_item_tView);
            final EditText eText = (EditText) view.findViewById(R.id.prop_det_child_item_eText);
            final RadioButton rBut = (RadioButton) view.findViewById(R.id.prop_det_child_item_rBut);

            switch (groupPosition) {
                case 0:
                    rBut.setVisibility(View.GONE);
                    if (childPosition == 3) {
                        eText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                    }
                    eText.setText(tableData[childPosition]);
                    break;
		    }

            childName.setText(groupChilds.get(groupValues.get(groupPosition)).get(childPosition));
            childName.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    eText.requestFocus();
                }
            });

            //add text changes for save value into database
            eText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

                }

                @Override
                public void onTextChanged(@NonNull final CharSequence s, final int start, final int before, final int count) {
                    switch (groupPosition) {
                        case 0:
                            tableData[childPosition] = s.toString();
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void afterTextChanged(final Editable s) {

                }
            });

            return view;
        }

        @Override
        public boolean isChildSelectable(final int groupPosition, final int childPosition) {
            return true;
        }

        @Override
        public void onGroupExpanded(final int groupPosition) {
        super.onGroupExpanded(groupPosition);
        }
    }

    @Override
    public void onBackPressed() {
        createSaveDialog();
    }

}
