package com.backofficecloudapps.prop.inventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * Created by Phoenix
 */
public class db_PdfView extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public static final String DATABASENAME = "tempInventory1";
    public static final String INV_TAB_NAME      = "INV_TEMP_TABLE";
    public static final String INVENTORYDATE        = "INVENTORYDATE";
    public static final String INVENTORYITEMPHOTOS   = "INVENTORYITEMPHOTOS";
    public static final String PROPERTYID            = "PROPERTYID";
    public static final String ZEROLEVELITEM         = "ZEROLEVELITEM";
    public static final String FIRSTLEVELITEM        = "FIRSTLEVELITEM";
    public static final String SECONDLEVELITEM       = "SECONDLEVELITEM";
    public static final String THIRDLEVELDATA        = "THIRDLEVELDATA";
    public static final String ID        = "ID";
    public static final String LEVEL     = "LEVEL";
    public static final String D_TEXT    = " TEXT";
    public static final String D_INTEGER = " INTEGER";
    public static final String MULTIID = "MULTI_ID";
    public static final String MULTI_S_FLAG = "MULTI_S_FLAG";

    private static final String INV_TEMP_QUERY =
            ("create table IF NOT EXISTS"+" "+INV_TAB_NAME+" "
                    +"("+INVENTORYDATE+D_TEXT+","
                    +INVENTORYITEMPHOTOS+D_TEXT+","
                    +PROPERTYID+D_TEXT+","
                    +ZEROLEVELITEM+D_TEXT+","
                     +FIRSTLEVELITEM+D_TEXT+","
                    +SECONDLEVELITEM+D_TEXT+","
                    +THIRDLEVELDATA+D_TEXT+","
                    +ID+" integer primary key autoincrement"+","
                    +LEVEL+D_TEXT+","
                    +MULTIID+D_INTEGER+" default 0"+","
                    +MULTI_S_FLAG+D_TEXT+" default 'N' "
                    +")");

    /**
     * Instantiates a new Db pdf view.
     *
     * @param context the context
     */
    public db_PdfView(Context context) {
        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(INV_TEMP_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Open.
     */
    public void Open()
    {
        db = this.getWritableDatabase();
    }

    /**
     * Insert data into property table long.
     *
     * @param date      the date
     * @param photos    the photos
     * @param propID    the prop id
     * @param zerolvl   the zerolvl
     * @param onelvl    the onelvl
     * @param secondlvl the secondlvl
     * @param threelvl  the threelvl
     * @param level     the level
     * @return the long
     */
    public long insertDataIntoPropertyTable_Multi(String date, String photos, String propID, String zerolvl, String onelvl, String secondlvl, String threelvl, String level, int multiFlag, String multiScreenFlag){
        long value;

        ContentValues cv = new ContentValues();

        cv.put(INVENTORYDATE,date);
        cv.put(INVENTORYITEMPHOTOS,photos);
        cv.put(PROPERTYID,propID);
        cv.put(ZEROLEVELITEM,zerolvl);
        cv.put(FIRSTLEVELITEM,onelvl);
        cv.put(SECONDLEVELITEM,secondlvl);
        cv.put(THIRDLEVELDATA,threelvl);
        cv.put(LEVEL,level);
        cv.put(MULTIID,multiFlag);
        cv.put(MULTI_S_FLAG,multiScreenFlag);

        value = db.insert(INV_TAB_NAME, null, cv);

        return value;
    }
    public long insertDataIntoPropertyTable(String date, String photos, String propID, String zerolvl, String onelvl, String secondlvl, String threelvl, String level){
        long value;

        ContentValues cv = new ContentValues();

        cv.put(INVENTORYDATE,date);
        cv.put(INVENTORYITEMPHOTOS,photos);
        cv.put(PROPERTYID,propID);
        cv.put(ZEROLEVELITEM,zerolvl);
        cv.put(FIRSTLEVELITEM,onelvl);
        cv.put(SECONDLEVELITEM,secondlvl);
        cv.put(THIRDLEVELDATA,threelvl);
        cv.put(LEVEL,level);

        value = db.insert(INV_TAB_NAME, null, cv);

        return value;
    }

    public long insertDataIntoPropertyTable_multi(String date, String photos, String propID, String zerolvl, String onelvl, String secondlvl, String threelvl, String level, int multiFlag, String multiScreenFlag){
        long value;

        ContentValues cv = new ContentValues();

        cv.put(INVENTORYDATE,date);
        cv.put(INVENTORYITEMPHOTOS,photos);
        cv.put(PROPERTYID,propID);
        cv.put(ZEROLEVELITEM,zerolvl);
        cv.put(FIRSTLEVELITEM,onelvl);
        cv.put(SECONDLEVELITEM,secondlvl);
        cv.put(THIRDLEVELDATA,threelvl);
        cv.put(LEVEL,level);
        cv.put(MULTIID,multiFlag);
        cv.put(MULTI_S_FLAG,multiScreenFlag);

        value = db.insert(INV_TAB_NAME, null, cv);

        return value;
    }

    /**
     * Update data into property table long.
     *
     * @param date      the date
     * @param photos    the photos
     * @param propID    the prop id
     * @param zerolvl   the zerolvl
     * @param onelvl    the onelvl
     * @param secondlvl the secondlvl
     * @param threelvl  the threelvl
     * @param level     the level
     * @return the long
     */
    public long updateDataIntoPropertyTable(String date, String photos, String propID, String zerolvl, String onelvl, String secondlvl, String threelvl, String level){
        long value;

        ContentValues cv = new ContentValues();

        cv.put(INVENTORYITEMPHOTOS,photos);
        cv.put(THIRDLEVELDATA,threelvl);

        value = db.update(INV_TAB_NAME, cv,
                PROPERTYID + "=?" + " and " + ZEROLEVELITEM + "=?" + " and " + FIRSTLEVELITEM + "=?" + " and " + SECONDLEVELITEM + "=?" + " and " + INVENTORYDATE + "=?"+ " and " + LEVEL + "=?",
                new String[]{propID, zerolvl, onelvl, secondlvl, date,level});

        return value;
    }

    public void updateDataIntoPropertyTable_multi(String date, String photos, String propID, String zerolvl, String onelvl, String secondlvl, String threelvl, String level, int multiFlag){
        long value;

        Cursor c;
        String q = "update "+INV_TAB_NAME+" set "+INVENTORYITEMPHOTOS+" = "+"'"+photos+"'"+","+THIRDLEVELDATA+" = "+"'"+threelvl+"'"+" where "
                +PROPERTYID+" ="+"'"+propID+"'"
                +" and "+ZEROLEVELITEM+" ="+"'"+zerolvl+"'"
                +" and "+FIRSTLEVELITEM+" ="+"'"+onelvl+"'"
                +" and "+SECONDLEVELITEM+" ="+"'"+secondlvl+"'"

                +" and "+INVENTORYDATE+" ="+"'"+date+"'"
                +" and "+LEVEL+" ="+"'"+level+"'"
                +" and "+MULTIID+" ="+multiFlag
                ;
        c = db.rawQuery(q,null);
        c.moveToFirst();
        c.close();

      /*  ContentValues cv = new ContentValues();

        cv.put(INVENTORYITEMPHOTOS,photos);
        cv.put(THIRDLEVELDATA,threelvl);

        value = db.update(INV_TAB_NAME, cv,
                          PROPERTYID + "=?" + " and "
                        + ZEROLEVELITEM + "=?" + " and "
                        + FIRSTLEVELITEM + "=?"+ " and "
                        + SECONDLEVELITEM + "=?" + " and "
                        + INVENTORYDATE + "=?"+ " and "
                        + LEVEL + "=?"+" and "
                        + MULTIID+ "=?",
                new String[]{
                        propID,
                        zerolvl,
                        onelvl,
                        secondlvl,
                        date,
                        level,
                        String.valueOf(multiFlag)});

        return value;*/
    }

    /**
     * Fetch datafor firstlvl cursor.
     *
     * @param propertyID    the property id
     * @param zeroLevelItem the zero level item
     * @param inventoryDate the inventory date
     * @return the cursor
     */
    public Cursor fetchDataforFirstlvl(String propertyID, String zeroLevelItem, String inventoryDate){
        Cursor c;

        String q ="SELECT * FROM INV_TEMP_TABLE WHERE PROPERTYID LIKE "+"'"+propertyID+"' AND ZEROLEVELITEM LIKE '"+zeroLevelItem+"' AND INVENTORYDATE LIKE "+
                "'"+inventoryDate+"'" ;
        c = db.rawQuery(q, null);

        return c;
    }

    /**
     * Get data exist or notlevel cursor.
     *
     * @param propid the propid
     * @param date   the date
     * @param zero   the zero
     * @param first  the first
     * @param level  the level
     * @return the cursor
     */
    public Cursor getDataExistOrNotlevel2(String propid, String date, String zero, String first, String level, int multi_flag_id){
        Cursor c;

        String sqlQuery ="SELECT * FROM INV_TEMP_TABLE WHERE "
                +"PROPERTYID LIKE "+"'"+propid+"'"+" AND "
                +"INVENTORYDATE LIKE '"+date+"'"+" AND "
                +"ZEROLEVELITEM LIKE '"+zero+"'"+" AND "
                +LEVEL+" LIKE '"+level+"'"+" AND "
                +MULTIID+"="+multi_flag_id+" AND "
                +"FIRSTLEVELITEM LIKE "+"'"+first+"'";


        c = db.rawQuery(sqlQuery,null);
        return c;
    }

    /**
     * Get data exist or not 2 cursor.
     *
     * @param propid the propid
     * @param date   the date
     * @param zero   the zero
     * @param first  the first
     * @param Data   the data
     * @return the cursor
     */
    public Cursor getDataExistOrNot2(String propid, String date, String zero, String first, String Data){
        Cursor c;


        String sqlQuery ="SELECT THIRDLEVELDATA FROM INV_TEMP_TABLE WHERE PROPERTYID LIKE '"+
                propid
                +"' AND INVENTORYDATE LIKE '"+date
                +"' AND ZEROLEVELITEM LIKE '"+zero
                +"' AND THIRDLEVELDATA LIKE '"+Data
                +"' AND FIRSTLEVELITEM LIKE "+"'"+first
                +"'";


        c = db.rawQuery(sqlQuery,null);
        return c;
    }

    /**
     * Get data exist photolevel cursor.
     *
     * @param propid the propid
     * @param date   the date
     * @param zero   the zero
     * @param first  the first
     * @param level  the level
     * @return the cursor
     */
    public Cursor getDataExistPhotolevel2(String propid, String date, String zero, String first, String level, int multiPartID){
        Cursor c;


        String sqlQuery ="SELECT * FROM INV_TEMP_TABLE WHERE PROPERTYID LIKE '"+
                propid
                +"' AND INVENTORYDATE LIKE '"+date
                +"' AND ZEROLEVELITEM LIKE '"+zero
                +"' AND "+LEVEL+" LIKE '"+level
                +"' AND "+MULTIID+" LIKE '"+multiPartID
                +"' AND FIRSTLEVELITEM LIKE "+"'"+first+"'";


        c = db.rawQuery(sqlQuery,null);
        return c;
    }

    /**
     * Get data exist photo cursor.
     *
     * @param propid the propid
     * @param date   the date
     * @param zero   the zero
     * @param first  the first
     * @param second the second
     * @return the cursor
     */
    public Cursor getDataExistPhoto(String propid, String date, String zero, String first, String second){
        Cursor c;


        String sqlQuery ="SELECT * FROM INV_TEMP_TABLE WHERE PROPERTYID LIKE '"+
                propid+"' AND INVENTORYDATE LIKE '"+date+"' AND ZEROLEVELITEM LIKE '"+zero+"' AND FIRSTLEVELITEM LIKE "+
                "'"+first+"' AND SECONDLEVELITEM LIKE '"+second+"'";


        c = db.rawQuery(sqlQuery,null);
        return c;
    }

    /**
     * Get data exist photolevel cursor.
     *
     * @param propid the propid
     * @param date   the date
     * @param zero   the zero
     * @param first  the first
     * @param second the second
     * @param level  the level
     * @return the cursor
     */
    public Cursor getDataExistPhotolevel3(String propid, String date, String zero, String first, String second, String level,int mMultiID){
        Cursor c;


        String sqlQuery ="SELECT * FROM INV_TEMP_TABLE WHERE PROPERTYID LIKE '"+
                propid
                +"' AND INVENTORYDATE LIKE '"+date
                +"' AND ZEROLEVELITEM LIKE '"+zero
                +"' AND FIRSTLEVELITEM LIKE "+"'"+first
                +"' AND "+LEVEL+" LIKE '"+level
                +"' AND "+MULTIID+" LIKE '"+mMultiID
                +"' AND SECONDLEVELITEM LIKE '"+second+"'";


        c = db.rawQuery(sqlQuery,null);
        return c;
    }

    /**
     * Get data exist or notlevel cursor.
     *
     * @param propid the propid
     * @param date   the date
     * @param zero   the zero
     * @param first  the first
     * @param second the second
     * @param level  the level
     * @return the cursor
     */
    public Cursor getDataExistOrNotlevel3(String propid, String date, String zero, String first, String second, String level, int multiFlagID){
        Cursor c;


        String sqlQuery ="SELECT * FROM INV_TEMP_TABLE WHERE PROPERTYID LIKE '"+propid
                +"' AND INVENTORYDATE LIKE '"+date
                +"' AND ZEROLEVELITEM LIKE '"+zero
                +"' AND FIRSTLEVELITEM LIKE "+"'"+first
                +"' AND "+LEVEL+" LIKE '"+level
                +"' AND "+MULTIID+" LIKE '"+multiFlagID
                +"' AND SECONDLEVELITEM LIKE '"+second+"'";


        c = db.rawQuery(sqlQuery,null);
        return c;
    }

    /**
     * Get data exist or not 3 cursor.
     *
     * @param propid the propid
     * @param date   the date
     * @param zero   the zero
     * @param first  the first
     * @param second the second
     * @param Data   the data
     * @return the cursor
     */
    public Cursor getDataExistOrNot3(String propid, String date, String zero, String first, String second, String Data){
        Cursor c;


        String sqlQuery ="SELECT THIRDLEVELDATA FROM INV_TEMP_TABLE WHERE PROPERTYID LIKE '"+
                propid
                +"' AND INVENTORYDATE LIKE '"+date
                +"' AND ZEROLEVELITEM LIKE '"+zero
                +"' AND THIRDLEVELDATA LIKE '"+Data
                +"' AND FIRSTLEVELITEM LIKE "+"'"+first
                +"' AND SECONDLEVELITEM LIKE '"+second+"'";


        c = db.rawQuery(sqlQuery,null);
        return c;
    }

    /**
     * Delete property table.
     *
     * @param propID the prop id
     * @param date   the date
     */
    public void deletePropertyTable(String propID, String date){

        db.delete(INV_TAB_NAME,PROPERTYID +"=?"+" and "+INVENTORYDATE +"=?",new String[]{propID,date});
    }

    public void deleteInventoryData2level_multi(String date, String propertyID, String zerolvlName,String FirstlvlName, int multiFlag){

        db.delete(INV_TAB_NAME,
                PROPERTYID +"=?"+" and "+INVENTORYDATE +"=?"+" and "+ZEROLEVELITEM +"=?"+" and "+FIRSTLEVELITEM +"=?"+" and "+MULTIID +"=?",
                new String[]{propertyID,date,zerolvlName,FirstlvlName, String.valueOf(multiFlag)});
    }

    public void deleteInventoryData2level_multi_3rd(String date, String propertyID, String zerolvlName,String FirstlvlName,String TwolvlName, int multiFlag){

        db.delete(INV_TAB_NAME,
                PROPERTYID +"=?"+" and "+INVENTORYDATE +"=?"+" and "+ZEROLEVELITEM +"=?"+" and "+FIRSTLEVELITEM +"=?"+" and "+SECONDLEVELITEM +" like ?"+" and "+MULTIID +"=?",
                new String[]{String.valueOf(propertyID),String.valueOf(date),String.valueOf(zerolvlName),String.valueOf(FirstlvlName),String.valueOf("%"+TwolvlName+"%"),String.valueOf(multiFlag)});
    }

    public void deleteUpdateInventoryData2level(String date, String propertyID, String zerolvlName,String FirstlvlName, int multiFlag){

        Cursor c;
        String q = "update "+INV_TAB_NAME+" set "+MULTIID+" = "+MULTIID+"-1 where "+MULTIID+" >"+multiFlag
                   +" and "+PROPERTYID+" ="+"'"+propertyID+"'"
                   +" and "+INVENTORYDATE+" ="+"'"+date+"'"
                   +" and "+ZEROLEVELITEM+" ="+"'"+zerolvlName+"'"
                   +" and "+FIRSTLEVELITEM+" ="+"'"+FirstlvlName+"'"
                  ;
        c = db.rawQuery(q,null);
        c.moveToFirst();
        c.close();

    }

    public void deleteUpdateInventoryData2level_3rd(String date, String propertyID, String zerolvlName,String FirstlvlName,String TwolvlName, int multiFlag){

        Cursor c;
        String q = "update "+INV_TAB_NAME+" set "+MULTIID+" = "+MULTIID+"-1 where "+MULTIID+" >"+multiFlag
                +" and "+PROPERTYID+" ="+"'"+propertyID+"'"
                +" and "+INVENTORYDATE+" ="+"'"+date+"'"
                +" and "+ZEROLEVELITEM+" ="+"'"+zerolvlName+"'"
                +" and "+FIRSTLEVELITEM+" ="+"'"+FirstlvlName+"'"
                +" and "+SECONDLEVELITEM+" like "+"'"+"%"+TwolvlName+"%"+"'";
        c = db.rawQuery(q,null);
        c.moveToFirst();
        c.close();


    }


}
