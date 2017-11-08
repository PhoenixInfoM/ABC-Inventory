package com.backofficecloudapps.prop.inventory.inventories;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.adapter.adapter_FirstlvlDatabase;
import com.backofficecloudapps.prop.inventory.database.DataBaseHelper;
import com.backofficecloudapps.prop.inventory.database.db_DesireOutput;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.ConstantazDataBase;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Phoenix
 */
public class inventories_FirstLvl extends ActionBarActivity {

     private ListView firstLvlListView;
	 List<String> featuresList;
    private List<String> itemCategoriesCode;
    private List<String> itemDataExist;
    private String previousSelectedItem;
    private TextView propName;
    private TextView itemName;
    private TextView currentDate;
	PreferencesManager sPrefs;
	private DataBaseHelper helper;
	@Nullable
	private SQLiteDatabase database;
    public String inventoryDate="";

	public static String onClickDataFirst_Featurs="";

    @NonNull
    db_DesireOutput dbHelper = new db_DesireOutput(this);
	//////////////////////////////


   @Override
   protected void onCreate(final Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);

	     callFxnFor_setContent();
	     initialSetUp(this);
	     callFxn_for_set_listData();


   }

	private void callFxnFor_setContent() {
		setContentView(R.layout.inventories_first_lvl);

		propName = (TextView) findViewById(R.id.inventories_first_lvl_prop_name);
		itemName = (TextView) findViewById(R.id.inventories_first_lvl_current_item);
		currentDate = (TextView) findViewById(R.id.inventories_first_lvl_current_date);
		firstLvlListView=(ListView) findViewById(R.id.inventories_first_lvl_list_view);


	}


	@Override
	protected void onRestart() {

		callFxnFor_setContent();
		initialSetUp(this);
		callFxn_for_set_listData();

		super.onRestart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}


	private void   callFxn_for_set_listData() {

		sPrefs=new PreferencesManager(this);
		helper=new DataBaseHelper();
		featuresList=new ArrayList<>();
		itemCategoriesCode=new ArrayList<>();
		itemDataExist= new ArrayList<>();

		database = helper.getExternalDataBase(getApplicationContext());
		previousSelectedItem=getIntent().getStringExtra(Constantaz.selected_inv_name);


		propName.setText(sPrefs.getStringValue(Constantaz.selected_prop_name, "No data"));
		if(sPrefs.getStringValue(Constantaz.NewInventory, "").equals("edit"))
		{
			inventoryDate =inventories_ZeroLvl.inventoryDate;
			currentDate.setText("Date: ".concat(inventoryDate));
		}
		else{
			inventoryDate =getCurrentDate();
			currentDate.setText("Date: ".concat(inventoryDate));
		}


		itemName.setText(previousSelectedItem);

		final String category= getIntent().getStringExtra(ConstantazDataBase.inventory_item_category);

		//@desc: use for initalize string List , it will hold all "one level" item category list
		//@desc: use function getFirstLvlItems for getting data from database table
		final List<String> currentDescriptions=getFirstLvlItems(getIntent().getStringExtra(ConstantazDataBase.inventory_item_category));

		//@desc: use for check all item from database ..if exist or not..if not exist then finish activity
		if(currentDescriptions==null||currentDescriptions.isEmpty()){
           Toast.makeText(getApplicationContext(), "No Navigation data found.", Toast.LENGTH_LONG).show();
           finish();
           return;
        }

		/************working on listview with adapter and item click listener***************/
		firstLvlListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		firstLvlListView.setBackgroundColor(getResources().getColor(R.color.custom));
		firstLvlListView.setDividerHeight(7);
		//@desc": set onclick listener at listview
		firstLvlListView.setOnItemClickListener(new OnItemClickListener(){
          @Override
          public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id){
             /*
                * @desc: putExtra: three parameter: InventoryFeature, InventoryName(previousNAme>currentName),bothInventoryName(Previous+New)
                * @param: ConstantazDataBase.inventory_item_features -> hold "inventory feature"
                * @param: Constantaz.selected_inv_name -> hold "Inventory Name" with previous "Inventory Name"
                *         formate: previous Name>Current Nameprevious Name>Current Name
                * @param: shared1 -> hold "Previous Inventory Name + Current Inventory Name"
             * */

			onClickDataFirst_Featurs=featuresList.get(position);

            Intent intent=new Intent(inventories_FirstLvl.this, inventories_SecondLvl.class);
            intent.putExtra(ConstantazDataBase.inventory_item_features, featuresList.get(position));
            intent.putExtra(Constantaz.selected_inv_name, previousSelectedItem.concat(" > ").concat(currentDescriptions.get(position)));
            intent.putExtra("shared1",previousSelectedItem+currentDescriptions.get(position)) ;
			intent.putExtra("multiscreencounter","1");
            startActivity(intent);
          }
        });

		final List<String> features = wtf(category);
		final List<List<String>> values = new ArrayList<>();
		for (String name : features) {
            values.add(getSpinnerItems(name));
        }

		/**
         * use: check this item value present into db or not
         * Date: 18-March-2017
         */
		String propertyID = sPrefs.getStringValue(Constantaz.selected_prop_ID, "ERROR");
		String zerolvlItemCode = getIntent().getStringExtra("ZeroLevelCode");

		dbHelper.Open();
		for(String itemCode : itemCategoriesCode){

            Cursor c;
            c = dbHelper.fetchOnelvtDataCodeValue(propertyID, inventoryDate.replaceAll("-",""),""+zerolvlItemCode,itemCode);

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

		//@desc: use for set data from database to listview
		// firstLvlListView.setAdapter(new CustomDBAdapter(this, currentDescriptions, values, featuresList));
		firstLvlListView.setAdapter(new adapter_FirstlvlDatabase(currentDescriptions, this, R.layout.zero_lvl_items, R.id.zero_lvl_text,itemDataExist,values, featuresList,R.id.zero_lvl_icon));
		/************working on listview with adapter and item click listener***************/}

	/**
	 * Back to main menu.
	 *
	 * @param view the view
	 */
	public void backToMainMenu(View view)
    {
		inventories_FirstLvl.this.finish();
	}

	/**
	 *
	 * @param itemCategory
	 * @return
     */
   @NonNull
   private List<String> getFirstLvlItems(@NonNull String itemCategory){
	   SQLiteDatabase database=helper.getExternalDataBase(getApplicationContext());

	   Cursor cursor=database.rawQuery("SELECT "
               .concat(ConstantazDataBase.inventory_item_description)
               .concat(", ".concat(ConstantazDataBase.inventory_item_code))
			   .concat(", ".concat(ConstantazDataBase.inventory_item_features))
               .concat(" FROM BP02_INVENTORY_ITEM WHERE ")
			   .concat(ConstantazDataBase.inventory_item_lvl).concat("='1'").concat(" AND ")
			   .concat(ConstantazDataBase.inventory_item_category).concat("='".concat(itemCategory.concat("'"))).concat(" order by " +ConstantazDataBase.inventory_item_display_no), null);

	List<String> tempList=new ArrayList<>();
	if(cursor.moveToFirst()){
	  while(!cursor.isAfterLast()){

		String itemDesc=cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_description));
		String itemCat=cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_features));
        String itemCategoryCode = cursor.getString(cursor.getColumnIndex(ConstantazDataBase.inventory_item_code));

          tempList.add(itemDesc);
		  featuresList.add(itemCat);
          itemCategoriesCode.add(itemCategoryCode);

          cursor.moveToNext();
	  }
	}
	cursor.close();
	return tempList;
  }

	/**
	 *
	 * @param context

     */
   //@desc: use for custom action bar and set name and date with sp
  private void initialSetUp(Context context){

	 /**********use for custom action bar************/
	ActionBarBuilder.getInstance()
		.setActionBar(context, getSupportActionBar())
		.setCustomIcon(R.drawable.icon_inventory)
		.showDefaultHomeTitle(false)
		.showDefaultHomeIcon(false)
		.showYourHomeIcon(true)
		.inflateLayout(R.layout.inventories_action_bar, null)
		.showCustomLayout(true);

	if(getSupportActionBar()!=null){
	  View v=getSupportActionBar().getCustomView();
	  TextView title=(TextView) v.findViewById(R.id.inventories_bar_title);
	  title.setText("  Inventories");
		ImageView home=(ImageView) v.findViewById(R.id.inventories_bar_logo);
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(inventories_FirstLvl.this, activity_MainMenu.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
	}
	  /**********use for custom action bar************/


  }

    //@desc: use for getting current date
	private String getCurrentDate(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		return df.format(c.getTime());
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
	 *
	 * @param category
	 * @return
     */
    @NonNull
	private List<String> wtf(@NonNull String category) {
        Cursor cursor = database.rawQuery("SELECT ".concat(ConstantazDataBase.inventory_item_features).concat(" FROM BP02_INVENTORY_ITEM WHERE ")
                .concat(ConstantazDataBase.inventory_item_category)
                .concat("='").concat(category).concat("'"), null);
        List<String> tempList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String itemCat = cursor.getString(cursor.getColumnIndex(ConstantazDataBase
                        .inventory_item_features));
                tempList.add(itemCat);
                 cursor.moveToNext();
            }
        }
        cursor.close();
        return tempList;
    }
}
