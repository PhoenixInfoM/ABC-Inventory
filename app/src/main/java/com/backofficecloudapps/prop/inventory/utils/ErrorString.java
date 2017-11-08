package com.backofficecloudapps.prop.inventory.utils;
/**
 * Created by Phoenix
 */

import android.support.annotation.Nullable;

/**
 * The type Error string.
 */
public final class ErrorString{
  private static String error="";
  @Nullable
  private static ErrorString instance=null;

    /**
     * Get inst error string.
     *
     * @return the error string
     */
    @Nullable
    public static ErrorString getInst(){
	if(instance==null){
	  instance=new ErrorString();
	}
	error="";
	return instance;
  }

  private ErrorString(){}

    /**
     * Clear.
     */
    public static void clear(){
	error = "";
  }

    /**
     * Set.
     *
     * @param value the value
     */
    public static void set(String value){
	error=value;
  }

    /**
     * Get string.
     *
     * @return the string
     */
    public static String get(){
	return error;
  }

  @Override
  public String toString(){
	return error;
  }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public static boolean isEmpty(){
	return error.isEmpty();
  }

    /**
     * Is not empty boolean.
     *
     * @return the boolean
     */
    public static boolean isNotEmpty(){
	return !error.isEmpty();
  }
}
