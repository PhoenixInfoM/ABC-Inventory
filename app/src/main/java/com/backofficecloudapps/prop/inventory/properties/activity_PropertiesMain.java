package com.backofficecloudapps.prop.inventory.properties;

/**
 * Created by Phoenix
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.activity.activity_AddNewProperty;
import com.backofficecloudapps.prop.inventory.adapter.adapter_PropertyExpandableList;
import com.backofficecloudapps.prop.inventory.database.DataBaseHelper;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.database.db_PdfView;
import com.backofficecloudapps.prop.inventory.inventories.inventories_ZeroLvl;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * shows created properties from database
 */
public class activity_PropertiesMain extends ActionBarActivity {

  private final static int PROPERTIES_DATA_SIZE=9;
  private final static int HOUSE_NO=0;
  private final static int STREET=1;
  private final static int POST_CODE=3;
  private final static int UPDATE_DATA_CODE=1;
  private final static int PROPERTY_TYPE=5;
  private List<String[]> propertyDataList;
  private PreferencesManager sPref;
  private DataBaseHelper helper;
  private ArrayAdapter<String> adapter;
  private List<String> properties;
  private int deleteItemPosition;
  private Button editButton, deleteButton, inventoryBut;
  private String[] detailedData;
  private List<String> propertyIds;
  @NonNull
  private db_DesireOutput dbHelper = new db_DesireOutput(activity_PropertiesMain.this);
  @NonNull
  private db_PdfView dbView = new db_PdfView(activity_PropertiesMain.this);

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    private ExpandableListView expandListView;
    adapter_PropertyExpandableList expListAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.properties);
	sPref=new PreferencesManager(this);
	helper=new DataBaseHelper();

	properties = new ArrayList<>();
	propertyIds =new ArrayList<>();
	editButton=(Button) findViewById(R.id.properties_edit_but);
	deleteButton=(Button) findViewById(R.id.properties_del_but);
	inventoryBut=(Button) findViewById(R.id.properties_inventories_but);
	ActionBarBuilder.getInstance()
		.setActionBar(getApplicationContext(), getSupportActionBar())
		.setCustomTitle("   Properties")
		.showDefaultHomeTitle(true)
		.showYourHomeIcon(true)
		.showDefaultHomeIcon(false)
		.setCustomIcon(R.drawable.icon_home)
		.inflateLayout(R.layout.properties_action_bar, null)
		.showCustomLayout(true);

	if(getSupportActionBar()!=null){
	  View v=getSupportActionBar().getCustomView();
	  TextView title=(TextView) v.findViewById(R.id.properties_bar_title);
	  title.setText(" Properties");
		ImageView home=(ImageView) v.findViewById(R.id.properties_bar_logo);
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(activity_PropertiesMain.this, activity_MainMenu.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
	}
	findViewById(R.id.properties_bar_back_arrow).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(final View view) {
            finish();
        }
    });

      getDataFromTable();
      createCollection();

      expandListView = (ExpandableListView) findViewById(R.id.prop_list_view);
      expandListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

      expListAdapter = new adapter_PropertyExpandableList(this,laptopCollection, properties);
      expandListView.setAdapter(expListAdapter);

      expandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

          int previousGroup = -1;

          @Override
          public void onGroupExpand(int groupPosition) {
              // Collapse previous parent if expanded.
              if ((previousGroup != -1) && (groupPosition != previousGroup)) {
                  expandListView.collapseGroup(previousGroup);
              }
              previousGroup = groupPosition;
          }
      });

      expandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {// to make it always in expanded form
          public boolean onGroupClick(@NonNull ExpandableListView parent, @NonNull View v, int groupPosition, long id) {

             try {
                  expListAdapter.notifyDataSetChanged();
              } catch (Exception e) {
                  e.printStackTrace();
              }

              final LinearLayout linLayout = (LinearLayout) v.findViewById(R.id.groupId);

              detailedData = propertyDataList.get(groupPosition);
              deleteButton.setEnabled(true);
              editButton.setEnabled(true);
              inventoryBut.setEnabled(true);
              deleteItemPosition = groupPosition;

              String[] cityData = {detailedData[0], detailedData[1], detailedData[2], detailedData[3], detailedData[4], detailedData[5], detailedData[6], detailedData[7]};

              List<String> wordList = Arrays.asList(cityData);

              if (parent.isGroupExpanded(groupPosition)) {
                  parent.collapseGroup(groupPosition);
              } else {
                  parent.expandGroup(groupPosition);
              }

              expListAdapter.setChildView(groupPosition, wordList, properties);


              return true;
          }
      });

  }


 @Override
 protected void onPause(){
	super.onPause();
  }

  @Override
  protected void onResume(){
	super.onResume();
	deleteButton.setEnabled(false);
	editButton.setEnabled(false);
	inventoryBut.setEnabled(false);

	  getDataFromTable();
      createCollection();
      expListAdapter = new adapter_PropertyExpandableList(this,laptopCollection, properties);
      expandListView.setAdapter(expListAdapter);
  }

  /**
   * getting data from database and stores it to properties
   */
  private void getDataFromTable(){
    groupList = new ArrayList<String>();
	propertyDataList=new ArrayList<String[]>();
	String accountId=sPref.getStringValue(Constantaz.userID, "null");

      try {
          dbHelper.Open();
          Cursor c;
          c = dbHelper.fetchIncomingDetails(accountId);

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
          dbHelper.close();
          properties.clear();

          for(String[] rowDB : propertyDataList) {
              properties.add(rowDB[HOUSE_NO] + " " + rowDB[STREET] + ", " + rowDB[POST_CODE]);

          }

          propertyIds=getPropertyID();

      }
      catch(Exception e){
          e.printStackTrace();
      }

  }

    /**
     * Get property id list.
     *
     * @return the list
     */
    @NonNull
    public List<String> getPropertyID(){

        dbHelper.Open();
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery("SELECT BP01_PROPERTY_ID FROM BP01_PROPERTIES", null);
        List<String> propertyCodes=new ArrayList<>();
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    propertyCodes.add(cursor.getString(0));
                }
            }

            cursor.close();
        dbHelper.close();

        return propertyCodes;
    }

    /**
     * Add new prop.
     *
     * @param view the view
     */
//@function: use for add new property
  public void addNewProp(View view) {

      startActivity(new Intent(activity_PropertiesMain.this, activity_AddNewProperty.class));
  }

    /**
     * Edit prop.
     *
     * @param view the view
     */
    public void editProp(View view) {

	Intent intent=new Intent(activity_PropertiesMain.this, activity_AddNewProperty.class);
      intent.putExtra(Constantaz.property_details, detailedData);
      intent.putExtra(Constantaz.property_to_edit, deleteItemPosition);
	intent.putExtra(Constantaz.is_editable, true);
      intent.putExtra("PID", propertyIds.get(deleteItemPosition));
            startActivityForResult(intent, UPDATE_DATA_CODE);
  }

    /**
     * Go to inventory screen.
     *
     * @param view the view
     */
    public void goToInventoryScreen(View view){
	Intent intent=new Intent(activity_PropertiesMain.this, inventories_ZeroLvl.class);
	sPref.setStringValue(Constantaz.selected_prop_name, ""+properties.get(deleteItemPosition));
	String date = getPropertyDate(propertyIds.get(deleteItemPosition));
	sPref.setStringValue(Constantaz.selected_prop_ID, ""+propertyIds.get(deleteItemPosition));
	sPref.setStringValue(Constantaz.selected_prop_date, ""+date);
	  intent.putExtra(Constantaz.selected_prop_date, date);
	  sPref.setStringValue(Constantaz.NewInventory, "");
      startActivity(intent);
  }

    /**
     * Delete prop.
     *
     * @param view the view
     */
    public void deleteProp(View view){
	createConfirmationDialog();
  }

  private void createConfirmationDialog(){



	AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(activity_PropertiesMain.this);
	alertDialogBuilder.setTitle("Deleting property");
	alertDialogBuilder.setMessage("If you delete property all inventories wil be deleted too. " +
			"Delete selected property?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			  @Override
              public void onClick(@NonNull final DialogInterface dialog, final int which){
                  properties.remove(deleteItemPosition);

                  dbHelper.Open();

                  Cursor cur123;
                  cur123 = dbHelper.fetchCurrentDateById(propertyIds.get(deleteItemPosition));
                  String date ="";
                  while(cur123.moveToNext()){
                      date =cur123.getString(cur123.getColumnIndex("BP01_CURRENT_DATE"));
                  }
                  cur123.close();

                  Cursor cur12;
                  cur12 = dbHelper.fetchDetailsById(propertyIds.get(deleteItemPosition));
                  String HouseNo ="";
                  String PostCode ="";
                  while(cur12.moveToNext()){
                      HouseNo =cur12.getString(cur12.getColumnIndex("BP01_HOUSE_NO"));
                      PostCode = cur12.getString(cur12.getColumnIndex("BP01_POST_CODE"));
                  }
                  cur12.close();


                  dbHelper.deletePropertyfield(sPref.getStringValue(Constantaz.userID, "null"), propertyIds.get(deleteItemPosition));
                  dbHelper.close();


                  dbView.Open();
                  dbView.deletePropertyTable(propertyIds.get(deleteItemPosition), date);
                  dbView.close();


                  String currentInventoryDateandTime=date.replace("-","")+"-"+PostCode.trim()+"-"+HouseNo.trim();

                  String fileName = "INV-" + currentInventoryDateandTime + ".pdf";


                  String inventoryCreationDate1 = date.replaceAll("-","").replaceAll("\\s+", "");//first element

                  File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                          "/ABCInventory/".concat(fileName));

                  File photo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                          "/ABCInventory/".concat(inventoryCreationDate1)
                                  .concat("/" +(HouseNo+"-"+propertyIds.get(deleteItemPosition)).replace(" ", "")));


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


                  expListAdapter.notifyDataSetChanged();
				editButton.setEnabled(false);
				deleteButton.setEnabled(false);
                  inventoryBut.setEnabled(false);
		                  dialog.dismiss();
			  }
			}).setNegativeButton("No", new DialogInterface.OnClickListener(){
	  @Override
      public void onClick(@NonNull final DialogInterface dialog, final int which){
		dialog.dismiss();
	  }
    }).setCancelable(true);
	alertDialogBuilder.create();
	alertDialogBuilder.show();

  }

    /**
     * Gets property date.
     *
     * @param propertyId the property id
     * @return the property date
     */
    public String getPropertyDate(String propertyId) {

        dbHelper.Open();
        Cursor cursor1 = dbHelper.fetchCurrentDateById(propertyId);
        String propertyCodes = "";

        while(cursor1.moveToNext()){
            propertyCodes = cursor1.getString(0);

        }

        cursor1.close();
        dbHelper.close();

        return propertyCodes;

    }

    /////////////////////////////for Expandable List View
    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.addAll(properties);
    }

    private void createCollection() {

        for(String[] rowDB : propertyDataList){

            String[] cityData = { rowDB[0], rowDB[1], rowDB[2],  rowDB[3], rowDB[4], rowDB[5],  rowDB[6], rowDB[7]};


            laptopCollection = new LinkedHashMap<String, List<String>>();

            for (String laptop : properties) {
                if (laptop.equals("Prop1"))
                    loadChild(cityData);
                else{
                    loadChild(cityData);
                }

                laptopCollection.put(laptop, childList);
            }

        }


    }

    private void loadChild(@NonNull String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expandListView.setIndicatorBounds(width - getDipsFromPixel(35), width-getDipsFromPixel(5));
    }

    /**
     * Gets dips from pixel.
     *
     * @param pixels the pixels
     * @return the dips from pixel
     */
// Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    /////////////////////////////for Expandable List View

}
