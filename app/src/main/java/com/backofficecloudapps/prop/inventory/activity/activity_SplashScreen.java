package com.backofficecloudapps.prop.inventory.activity;

/**
 * Created by Phoenix
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.backofficecloudapps.prop.inventory.R;
import com.backofficecloudapps.prop.inventory.mainmenu.activity_MainMenu;


/**
 * Simple activity which shows a Splash Screen
 */
public class activity_SplashScreen extends Activity {

  /**
   * creates fullscreen layout and shows a splashscreen image
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.splash_screen);
	Handler handler = new Handler();
	handler.postDelayed(new Runnable(){
	  /**
	   * starts main menu activity after 2,5 sec
	   */
	  public void run(){
		startActivity(new Intent(activity_SplashScreen.this, activity_MainMenu.class));
		finish();
	  }
	}, 2500);
  }
}
