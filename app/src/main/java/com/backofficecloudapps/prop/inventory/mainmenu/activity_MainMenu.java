package com.backofficecloudapps.prop.inventory.mainmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.activity.activity_Inventories;
import com.backofficecloudapps.prop.inventory.properties.activity_PropertiesMain;
import com.backofficecloudapps.prop.inventory.setting.SettingsActivity;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * Created by Phoenix
 */
public class activity_MainMenu extends ActionBarActivity {

	public static boolean editValueFlag = false;

    /**
   * Configures Actionbar; Initializes ListView and adapter; Shows the items of menu
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main_menu);

      /****************use for set custom action bar*********************/
	  ActionBarBuilder.getInstance()
			  .setActionBar(getApplicationContext(), getSupportActionBar())
			  .setCustomTitle("   Main Menu")
			  .showDefaultHomeTitle(true)
			  .showYourHomeIcon(true)
			  .showDefaultHomeIcon(false)
			  .setCustomIcon(R.drawable.icon_home)
			  .inflateLayout(R.layout.properties_action_bar, null)
			  .showCustomLayout(true);


	  if(getSupportActionBar()!=null){
		  View v=getSupportActionBar().getCustomView();
		  TextView title=(TextView) v.findViewById(R.id.properties_bar_title);
		  title.setText("   Main Menu");
		  ImageView home=(ImageView) v.findViewById(R.id.properties_bar_logo);
		  home.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  // for copy database and store in folder
				  callfxnforcreatefoldersavedatabase();
				 //callfxnforTempDb();

			  }
		  });

	  }
      /****************use for set custom action bar*********************/

      //@function:use for get device id and sim number and save in sp
	  //@useed: database account id for identofy unique data.
	  setUserID(this);

      // use for show list data
	  ListView mainListView = (ListView) findViewById(R.id.main_list_view);
	  mainListView.setAdapter(new MainMenuAdapter());
      mainListView.setOnItemClickListener(new OnItemClickListener(){

      /**
	   * Check position and starts chosen menu item
       * @param parent
       * @param view
       * @param position - a position of the selected element
       * @param id
       */
      @Override
      public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
          switch (position) {
              case 0: {
				  editValueFlag = true;
				  startActivity(new Intent(activity_MainMenu.this, activity_PropertiesMain.class));
			  }
              break;
              case 1: {
				  editValueFlag = true;
				  startActivity(new Intent(activity_MainMenu.this, activity_Inventories.class));
			  }
              break;
              case 2:

                 startActivity(new Intent(activity_MainMenu.this, SettingsActivity.class));
                  break;
              case 3:

                  break;

          }
      }
      });
  }

    /**
     * Creates and shows image and text for every item separately
   */
  private class MainMenuAdapter extends BaseAdapter {

	private String[] listItemsText;
	private int[] listItemsIcons;
	private LayoutInflater inflater;

		/**
		 * Instantiates a new Main menu adapter.
		 */
		public MainMenuAdapter(){
	  listItemsText =getApplicationContext().getResources().getStringArray(R.array.main_list_items);
	  listItemsIcons= new int[]{R.mipmap.icon_home, R.mipmap.icon_inventory, R.mipmap.icon_settings};
	  inflater      =(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * number of elements to show
	 * @return
	 */
	@Override
	public int getCount(){
	  return listItemsText.length;
	}

	@Nullable
	@Override
	public Object getItem(final int position){
	  return null;
	}

	@Override
	public long getItemId(final int position){
	  return 0;
	}

	/**
	 * creates view, which user can see
	 * @param position - current element
	 * @param convertView - a view which user can't see
	 * @param parent
	 * @return
	 */
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent){
	  View view=inflater.inflate(R.layout.main_list_item, parent, false);
		ImageView iView=(ImageView) view.findViewById(R.id.main_list_item_icon);
		iView.setImageResource(listItemsIcons[position]);
		TextView tView=(TextView) view.findViewById(R.id.main_list_item_text);
	    tView.setText(listItemsText[position]);
	  return view;
	}
  }

  /**
   * Sets user ID using device data
   * @param context used for sharedPreferences
   */
  private void setUserID(@NonNull Context context){
	  PreferencesManager sPrefs= null;
	  String deviceId = null;
	  try {
		  sPrefs = new PreferencesManager(context);
		  final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		  final String tmDevice, tmSerial, androidId;
		  tmDevice = "" + tm.getDeviceId();
		  tmSerial = "" + tm.getSimSerialNumber();
		  androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		  UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
		  deviceId = deviceUuid.toString();
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  sPrefs.setStringValue(Constantaz.userID, deviceId);
  }

	private void callfxnforcreatefoldersavedatabase() {

        /****************use for create folder in device*****************/
        File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();

		String path = Environment.getExternalStorageDirectory() + File.separator + "ABCInventory";

		File folder = new File(path);
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		if (success) {
			// Do something on success
		} else {
			// Do something else on failure
		}
       /****************use for create folder in device*****************/

		/****************use for copy data from database*****************/
		if (sd.canWrite()) {

			String currentDBPath = "/data/data/"+ getPackageName()+"/databases/testInventoryNew1";// inv db

			String backupDBPath = "backupname.db";
			File currentDB = new File(currentDBPath);
			File backupDB = new File(path, backupDBPath);
			//to get the run time db file
			if (currentDB.exists()) {
				FileChannel src = null;
				try {
					src = new FileInputStream(currentDB).getChannel();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				FileChannel dst = null;
				try {
					dst = new FileOutputStream(backupDB).getChannel();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				try {
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

            ////////////////////////////////////////////////////
		  }
        /****************use for copy data from database*****************/

		Toast.makeText(getApplicationContext(),"file path:"+path, Toast.LENGTH_SHORT).show();
	}

	private void callfxnforTempDb() {

		/****************use for create folder in device*****************/
		File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();

		String path = Environment.getExternalStorageDirectory() + File.separator + "ABCInventory";

		File folder = new File(path);
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		if (success) {
			// Do something on success
		} else {
			// Do something else on failure
		}
		/****************use for create folder in device*****************/

		/****************use for copy data from database*****************/
		if (sd.canWrite()) {

			String currentDBPath = "/data/data/"+ getPackageName()+"/databases/tempInventory1";// inv db

			String backupDBPath = "backupnameTemp.db";
			File currentDB = new File(currentDBPath);
			File backupDB = new File(path, backupDBPath);
			//to get the run time db file
			if (currentDB.exists()) {
				FileChannel src = null;
				try {
					src = new FileInputStream(currentDB).getChannel();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				FileChannel dst = null;
				try {
					dst = new FileOutputStream(backupDB).getChannel();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				try {
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

			////////////////////////////////////////////////////
		}
		/****************use for copy data from database*****************/

		Toast.makeText(getApplicationContext(),"file path:"+path, Toast.LENGTH_SHORT).show();
	}

}
