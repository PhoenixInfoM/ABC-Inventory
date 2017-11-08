package com.backofficecloudapps.prop.inventory.utils;
/**
 * Created by Phoenix
 */
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The type Intent.
 */
public final class Intent{
  @Nullable
  private static Activity activity=null;
  @Nullable
  private static Intent instance=null;
  @Nullable
  private static Class<?> clas=null;
  @Nullable
  private static android.content.Intent intent=null;

  private Intent(){}

	/**
	 * Get error error string.
	 *
	 * @return the error string
	 */
	public static ErrorString getError(){
	return ErrorString.getInst();
  }

	/**
	 * From intent.
	 *
	 * @param _activity the activity
	 * @return the intent
	 */
	@Nullable
	public static Intent from(@Nullable Activity _activity){
	if(instance==null){
	  instance=new Intent();
	}
	if(_activity==null){
	  ErrorString.set("activity == null");
	  return instance;
	}
	activity=_activity;
	return instance;
  }

	/**
	 * Take intent.
	 *
	 * @param _clas the clas
	 * @return the intent
	 */
	@Nullable
	public static Intent take(@Nullable Class<?> _clas){
	if(instance==null){
	  instance=new Intent();
	}
	if(_clas==null){
	  ErrorString.set("class == null");
	  return instance;
	}
	clas=_clas;
	intent=new android.content.Intent();
	return instance;
  }

	/**
	 * Get string.
	 *
	 * @param name the name
	 * @return the string
	 */
	public String get(@Nullable String name){
	if(name==null||name.isEmpty()){
	  ErrorString.set("func get: name == null or isEmpty");
	  return "";
	}
	if(activity==null){
	  ErrorString.set("func get: activity == null");
	  return "";
	}
	return activity.getIntent().getStringExtra(name);
  }

	/**
	 * Set intent.
	 *
	 * @param name  the name
	 * @param value the value
	 * @return the intent
	 */
	@NonNull
	public Intent set(@Nullable String name, String value){
	if(name==null||name.isEmpty()){
	  ErrorString.set("func get: name == null or isEmpty");
	  return this;
	}
	if(intent==null){
	  ErrorString.set("func get: intent == null");
	  return this;
	}
	intent.putExtra(name, value);
	return this;
  }

	/**
	 * Start from.
	 *
	 * @param _activity the activity
	 */
	public void startFrom(@Nullable Activity _activity){
	if(clas==null||_activity==null){
	  ErrorString.set("func start: class == null or activity == null");
	  return;
	}
	intent.setClass(_activity.getApplicationContext(),clas);
	_activity.startActivity(intent);
  }
}
