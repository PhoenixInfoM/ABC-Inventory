package com.backofficecloudapps.prop.inventory.setting;
/**
 * Created by Phoenix
 */

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.utils.ActionBarBuilder;
import com.backofficecloudapps.prop.inventory.utils.Constantaz;
import com.backofficecloudapps.prop.inventory.utils.PreferencesManager;
import com.lib.debug.Debug;
import com.lib.debug.Debug.LENGTH;


/**
 * The type Settings activity.
 */
public class SettingsActivity extends ActionBarActivity {

  private EditText nameET, emailET, passET;
  private PreferencesManager sPrefs;
  private Debug debug;

  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.settings);
	debug=new Debug(this, LENGTH.toast_short, "TAG");
	sPrefs=new PreferencesManager(getApplicationContext());
	nameET=(EditText) findViewById(R.id.settings_name_eText);
	emailET=(EditText) findViewById(R.id.settings_email_eText);
	passET=(EditText) findViewById(R.id.settings_password_eText);

	nameET.setText(sPrefs.getStringValue(Constantaz.user_name, ""));
	emailET.setText(sPrefs.getStringValue(Constantaz.user_email, ""));
	passET.setText(sPrefs.getStringValue(Constantaz.user_password, ""));

	ActionBarBuilder.getInstance()
		.setActionBar(this, getSupportActionBar())
		.setCustomIcon(R.drawable.icon_settings)
		.showDefaultHomeIcon(false)
		.showYourHomeIcon(true)
		.showDefaultHomeTitle(true)
		.inflateLayout(R.layout.settings_action_bar, null)
		.showCustomLayout(true)
	    .setCustomTitle("   Settings");

	  if(getSupportActionBar()!=null){
		  View v=getSupportActionBar().getCustomView();

		  ImageView home=(ImageView) v.findViewById(R.id.settings_bar_logo);
		  home.setOnClickListener(new View.OnClickListener() {
			  @Override
			  public void onClick(View v) {
				  backdilog();


			  }
		  });
	  }
  }


	/**
	 * Back to main menu.
	 *
	 * @param view the view
	 */
	public void backToMainMenu(View view){
	  backdilog();
  }

	/**
	 * Backdilog.
	 */
	public void backdilog(){

		final String userName=""+nameET.getText().toString();
		final String userEmail=""+emailET.getText().toString();
		final String userPassword=""+passET.getText().toString();

		String lastEmail=sPrefs.getStringValue(Constantaz.user_email, "null");
		if(lastEmail.equals("null"))
			createDialog(userName, userEmail, userPassword, false);
		else
			createDialog(userName, userEmail, userPassword, true);
	}

  private void createDialog(final String name, final String email, final String pass, boolean reSaveData){
	AlertDialog.Builder alertDialogBuilder=new Builder(SettingsActivity.this);
	if(!reSaveData){
	  alertDialogBuilder.setTitle("Attention unsaved data");
	  alertDialogBuilder.setMessage("Do you want to save your data?")
		  .setPositiveButton("Save", new OnClickListener(){
		@Override
		public void onClick(@NonNull final DialogInterface dialog, final int which){
		  sPrefs.setStringValue(Constantaz.user_name, ""+name);
		  sPrefs.setStringValue(Constantaz.user_email, ""+email);
		  sPrefs.setStringValue(Constantaz.user_password, ""+pass);
		  dialog.dismiss();
		  SettingsActivity.this.finish();
		}
	  }).setNegativeButton("No", new OnClickListener(){
		@Override
		public void onClick(@NonNull final DialogInterface dialog, final int which){
		  dialog.dismiss();
		  SettingsActivity.this.finish();
		}
	  }).setCancelable(true);
	} else{
	  alertDialogBuilder.setTitle("Attention! You want to change your email");
	  alertDialogBuilder.setMessage("Do you want to sync data to this email?")
		  .setPositiveButton("Yes", new OnClickListener(){
			@Override
			public void onClick(@NonNull final DialogInterface dialog, final int which){
				sPrefs.setStringValue(Constantaz.user_name, ""+name);
				sPrefs.setStringValue(Constantaz.user_email, ""+email);
				sPrefs.setStringValue(Constantaz.user_password, ""+pass);
			  dialog.dismiss();
			  SettingsActivity.this.finish();
			}
		  }).setNegativeButton("No", new OnClickListener(){
		@Override
		public void onClick(@NonNull final DialogInterface dialog, final int which){
		  dialog.dismiss();
		  SettingsActivity.this.finish();
		}
	  }).setCancelable(false);
	}
	alertDialogBuilder.create();
	alertDialogBuilder.show();
  }

	/**
	 * Save user data.
	 *
	 * @param view the view
	 */
	public void saveUserData(View view){
	final String userName = nameET.getText().toString();
	final String userEmail = emailET.getText().toString();
	final String userPassword = passET.getText().toString();
	String lastEmail=sPrefs.getStringValue(Constantaz.user_email, "null");
	if(lastEmail.equals("null")){
	  sPrefs.setStringValue(Constantaz.user_name, ""+userName);
	  sPrefs.setStringValue(Constantaz.user_email, ""+userEmail);
	  sPrefs.setStringValue(Constantaz.user_password, ""+userPassword);
	  debug.t("Data saved");
	  SettingsActivity.this.finish();
	} else{
	  createDialog(userName, userEmail, userPassword, true);
	}
  }
}
