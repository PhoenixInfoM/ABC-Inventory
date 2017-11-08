package com.backofficecloudapps.prop.inventory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.backofficecloudapps.prop.inventory.utils.ErrorString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by Phoenix
 */
public class DataBaseHelper{

  private final static String DB_PATH= "/data/data/" +"com.backofficecloudapps.prop.inventory"+ "/databases/";//Environment.getExternalStorageDirectory().getAbsolutePath();
  private final static String DB_NAME="inventory_items_fixed.sqlite";

	/**
	 * Instantiates a new Data base helper.
	 */
	public DataBaseHelper(){
	ErrorString.clear();

  }

	/**
	 * returns data base
	 *
	 * @param context using to get data base from assets
	 * @return sq lite database
	 */
	@Nullable
	public SQLiteDatabase getExternalDataBase(@NonNull Context context){
	SQLiteDatabase dataBase = null;
	if(openExternalDB(context)){
	  File dbFile = new File(DB_PATH.concat("/").concat(DB_NAME));
	  if(dbFile.exists()){
		dataBase = SQLiteDatabase.openOrCreateDatabase(DB_PATH.concat("/").concat(DB_NAME), null);
	  }else{
		ErrorString.set("db file not exists");
	  }
	}else{
	  ErrorString.set("cant open external db");
	}
	if(dataBase==null){
	  ErrorString.set("database == null");
	}
	return dataBase;
  }


  /**
   * Opens database stored in assets and saves it to SD card
   * @param context
   */
  private boolean openExternalDB(@NonNull Context context){
	InputStream inputStream=null;
	OutputStream outputStream=null;
	try{
	  inputStream=context.getAssets().open(DB_NAME);
	  File fileOnPhone=new File(DB_PATH,
		  "/".concat(DB_NAME));
	 outputStream=new FileOutputStream(fileOnPhone);
	  copyFile(inputStream, outputStream);
	  return true;
	}catch(IOException e){
      e.printStackTrace();
	  return false;
	} finally{
	  if (inputStream != null) {
		try {
		  inputStream.close();
		} catch (IOException e) {

		}
	  }
	  if (outputStream != null) {
		try {
		  outputStream.close();
		} catch (IOException e) {
		 e.printStackTrace();
		}
	  }
	}
  }

  /**
   * copy file from assets to SD card
   * @param is
   * @param os
   * @throws IOException
   */
  private void copyFile(@NonNull InputStream is, @NonNull OutputStream os) throws IOException {
	byte[] buffer=new byte[1024];
	int read;
	while((read=is.read(buffer))!=-1){
	  os.write(buffer, 0, read);
	}
  }


}
