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
public class db_DesireOutput extends SQLiteOpenHelper {

    //Database Name...
    private static final String DATABASENAME = "testInventoryNew1";

    /********************TABLE NAME********************************/
    private static final String M01_SETTINGS         = "M01_SETTINGS";
    private static final String BP01_PROPERTIES      = "BP01_PROPERTIES";
    private static final String BP02_INVENTORY_ITEM = "BP02_INVENTORY_ITEM ";
    private static final String BP02_INVENTORY = "BP02_INVENTORY";
    private static final String B01_DOCUMENT         = "B01_DOCUMENT";
    /********************TABLE NAME********************************/

    /**********************DEFINE DATATYPE***********************/
    private static final String D_TEXT    = " TEXT";
    private static final String D_INTEGER = " INTEGER";
    /**********************DEFINE DATATYPE***********************/

    /*##########################TABLE CLOUMN NAME###############################*/

    /***************M01_SETTINGS TABLE COLUMN****************/
    private static final String M01_CODE        = "M01_CODE";
    private static final String M01_DESCRIPTION = "M01_DESCRIPTION";
    private static final String M01_REC_TYPE    = "M01_REC_TYPE";
    private static final String M01_VALUE       = "M01_VALUE";
   /***************M01_SETTINGS TABLE COLUMN****************/

    /***************BP01_PROPERTIES TABLE COLUMN****************/
    private static final String B01_ACCOUNT_ID        = "B01_ACCOUNT_ID";
    private static final String BP01_ADDRESS1         = "BP01_ADDRESS1";
    private static final String BP01_ADDRESS2         = "BP01_ADDRESS2";
    private static final String BP01_ADDRESS3         = "BP01_ADDRESS3";
    private static final String BP01_BEDROOMS         = "BP01_BEDROOMS";
    private static final String BP01_CURRENT_DATE     = "BP01_CURRENT_DATE";
    private static final String BP01_FURNISHED        = "BP01_FURNISHED";
    private static final String BP01_HOUSE_NO         = "BP01_HOUSE_NO";
    private static final String BP01_POST_CODE        = "BP01_POST_CODE";
    private static final String BP01_PROPERTY_CODE    = "BP01_PROPERTY_CODE";
    private static final String BP01_PROPERTY_ID      = "BP01_PROPERTY_ID";
    private static final String BP01_PROPERTY_TYPE    = "BP01_PROPERTY_TYPE";
    /***************BP01_PROPERTIES TABLE COLUMN****************/

    /***************BP02_INVENTORY_ITEM TABLE COLUMN****************/
    private static final String BP02_INV_ITEM_CATEGORY      = "BP02_INV_ITEM_CATEGORY";
    private static final String BP02_INV_ITEM_CODE          = "BP02_INV_ITEM_CODE";
    private static final String BP02_INV_ITEM_DESCRIPTION   = "BP02_INV_ITEM_DESCRIPTION";
    private static final String BP02_INV_ITEM_DISPLAY_NO_II = "BP02_INV_ITEM_DISPLAY_NO";
    private static final String BP02_INV_ITEM_FEATURES      = "BP02_INV_ITEM_FEATURES";
    private static final String BP02_INV_ITEM_LEVEL         = "BP02_INV_ITEM_LEVEL";
    private static final String BP02_INV_ITEM_REPORT_NO_II  = "BP02_INV_ITEM_REPORT_NO";
    /***************BP02_INVENTORY_ITEM TABLE COLUMN****************/

    /***************BP02_INVENTORY TABLE COLUMN****************/
    private static final String BP01_INV_ACCOUNT_ID         = "BP01_INV_ACCOUNT_ID";
    private static final String BP02_INV_DATE               = "BP02_INV_DATE";
    private static final String BP02_INV_ITEM_COMMENTS      = "BP02_INV_ITEM_COMMENTS";
    private static final String BP02_INV_ITEM_COUNT         = "BP02_INV_ITEM_COUNT";
    private static final String BP02_INV_ITEM_DISPLAY_NO_I  = "BP02_INV_ITEM_DISPLAY_NO";
    private static final String BP02_INV_ITEM_INPUT         = "BP02_INV_ITEM_INPUT";
    private static final String BP02_INV_ITEM_L0            = "BP02_INV_ITEM_L0";
    private static final String BP02_INV_ITEM_L1            = "BP02_INV_ITEM_L1";
    private static final String BP02_INV_ITEM_L2            = "BP02_INV_ITEM_L2";
    private static final String BP02_INV_ITEM_L3            = "BP02_INV_ITEM_L3";
    private static final String BP02_INV_ITEM_L4            = "BP02_INV_ITEM_L4";
    private static final String BP02_INV_ITEM_L5            = "BP02_INV_ITEM_L5";
    private static final String BP02_INV_ITEM_REPORT_NO_I   = "BP02_INV_ITEM_REPORT_NO";
    private static final String BP02_INV_PHOTO1_DOC_ID      = "BP02_INV_PHOTO1_DOC_ID";
    private static final String BP02_INV_PHOTO2_DOC_ID      = "BP02_INV_PHOTO2_DOC_ID";
    private static final String BP02_INV_PHOTO3_DOC_ID      = "BP02_INV_PHOTO3_DOC_ID";
    private static final String BP02_PROPERTY_ID            = "BP02_PROPERTY_ID";
    private static final String BP02_INV_ITEM_MULTI_FLAG    = "BP02_INV_ITEM_MULTI_FLAG";
    private static final String BP02_INV_ITEM_MULTI_FLAG_S   = "BP02_INV_ITEM_MULTI_FLAG_S";

   /***************BP02_INVENTORY TABLE COLUMN****************/

    /***************B01_DOCUMENT TABLE COLUMN****************/
    private static final String B01_DOC_DATE       = "B01_DOC_DATE";
    private static final String B01_DOC_ID         = "B01_DOC_ID";
    private static final String B01_DOC_MIMETYPE   = "B01_DOC_MIMETYPE";
    private static final String B01_DOC_SOURCE_FN  = "B01_DOC_SOURCE_FN";
    private static final String B01_DOC_TYPE       = "B01_DOC_TYPE";
    private static final String B01_DOC_PROPERTYTYPE       = "B01_DOC_PROPERTYTYPE";

    /***************B01_DOCUMENT TABLE COLUMN****************/

    /*##########################TABLE CLOUMN NAME###############################*/

    /********************TABLE CREATED QUERY************************/
    private static final String SETTING_QUERY =
             ("create table IF NOT EXISTS"+" "+M01_SETTINGS+" "
             +"("+M01_CODE+D_TEXT+","
             +M01_DESCRIPTION+D_TEXT+","
             +M01_REC_TYPE+D_TEXT+","
             +M01_VALUE+D_TEXT+")");
    private static final String PROPERTIES_QUERY =
            ("create table IF NOT EXISTS"+" "+BP01_PROPERTIES+" "
                    +"("+B01_ACCOUNT_ID+D_TEXT+","
                    +BP01_ADDRESS1+D_TEXT+","
                    +BP01_ADDRESS2+D_TEXT+","
                    +BP01_ADDRESS3+D_TEXT+","
                    +BP01_BEDROOMS+D_INTEGER+","
                    +BP01_CURRENT_DATE+D_TEXT+","
                    +BP01_FURNISHED+D_TEXT+","
                    +BP01_HOUSE_NO+D_TEXT+","
                    +BP01_POST_CODE+D_TEXT+","
                    +BP01_PROPERTY_CODE+D_TEXT+","
                    +BP01_PROPERTY_ID+D_TEXT+","
                    +BP01_PROPERTY_TYPE+D_TEXT+")");

    private static final String INVENTORY_ITEM_QUERY =
            ("create table IF NOT EXISTS"+" "+ BP02_INVENTORY_ITEM +" "
                    +"("+BP02_INV_ITEM_CATEGORY+D_TEXT+","
                    +BP02_INV_ITEM_CODE+D_TEXT+","
                    +BP02_INV_ITEM_DESCRIPTION+D_TEXT+","
                    +BP02_INV_ITEM_DISPLAY_NO_II+D_INTEGER+","
                    +BP02_INV_ITEM_FEATURES+D_TEXT+","
                    +BP02_INV_ITEM_LEVEL+D_TEXT+","
                    +BP02_INV_ITEM_REPORT_NO_II+D_INTEGER+")");

    private static final String INVENTORY_QUERY =
            ("create table IF NOT EXISTS"+" "+ BP02_INVENTORY +" "
                    +"("+BP01_INV_ACCOUNT_ID+D_TEXT+","
                    +BP02_INV_DATE+D_TEXT+","
                    +BP02_INV_ITEM_COMMENTS+D_TEXT+","
                    +BP02_INV_ITEM_COUNT+D_TEXT+","
                    +BP02_INV_ITEM_DISPLAY_NO_I+D_TEXT+","
                    +BP02_INV_ITEM_INPUT+D_TEXT+","
                    +BP02_INV_ITEM_L0+D_TEXT+","
                    +BP02_INV_ITEM_L1+D_TEXT+","
                    +BP02_INV_ITEM_L2+D_TEXT+","
                    +BP02_INV_ITEM_L3+D_TEXT+","
                    +BP02_INV_ITEM_L4+D_TEXT+","
                    +BP02_INV_ITEM_L5+D_TEXT+","
                    +BP02_INV_ITEM_REPORT_NO_I+D_TEXT+","
                    +BP02_INV_PHOTO1_DOC_ID +D_TEXT+","
                    +BP02_INV_PHOTO2_DOC_ID +D_TEXT+","
                    +BP02_INV_PHOTO3_DOC_ID +D_TEXT+","
                    +BP02_PROPERTY_ID+D_TEXT+","
                    +BP02_INV_ITEM_LEVEL +D_TEXT+","
                    +BP02_INV_ITEM_MULTI_FLAG+D_INTEGER+" default 0 not null "+","
                    +BP02_INV_ITEM_MULTI_FLAG_S+D_TEXT
                    +")");

    private static final String DOCUMENT_QUERY =
            ("create table IF NOT EXISTS"+" "+B01_DOCUMENT+" "
                    +"("+B01_DOC_DATE+D_TEXT+","
                    +B01_DOC_ID +D_INTEGER+","
                    +B01_DOC_MIMETYPE+D_TEXT+","
                    +B01_DOC_SOURCE_FN+D_TEXT+","
                    +B01_DOC_TYPE+D_TEXT+","
                    +B01_DOC_PROPERTYTYPE+D_TEXT+")");


    // create object of SqliteDatabse
    public SQLiteDatabase db;

    /**
     * Instantiates a new Db desire output.
     *
     * @param context the context
     */
    public db_DesireOutput(Context context) {

        super(context, DATABASENAME, null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SETTING_QUERY);
        sqLiteDatabase.execSQL(PROPERTIES_QUERY);
        sqLiteDatabase.execSQL(INVENTORY_ITEM_QUERY);
        sqLiteDatabase.execSQL(INVENTORY_QUERY);
        sqLiteDatabase.execSQL(DOCUMENT_QUERY);
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

    /***************
     * use for add data into BP01_PROPERTIES table @param accountID the account id
     *
     * @param accountID   the account id
     * @param propID      the prop id
     * @param propCodeID  the prop code id
     * @param detailsData the details data
     * @return the long
     */
    public long insertDataIntoPropertyTable(String accountID, String propID, String propCodeID, String[] detailsData){
        long value;

        int houseNo=0, street=1, city=2, postalCode=3, country=4, propertyType=5,
                noOfBedrooms=6, furnished=7, currentDate=8;

        Integer bedroom = 0;

        try {
            bedroom= Integer.parseInt(detailsData[noOfBedrooms].replaceAll("[\\D]", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ContentValues cv = new ContentValues();

        cv.put(B01_ACCOUNT_ID,accountID);
        cv.put(BP01_ADDRESS1,detailsData[street]);
        cv.put(BP01_ADDRESS2,detailsData[city]);
        cv.put(BP01_ADDRESS3,detailsData[country]);
        cv.put(BP01_BEDROOMS,bedroom);
        cv.put(BP01_CURRENT_DATE,detailsData[currentDate]);
        cv.put(BP01_FURNISHED,detailsData[furnished]);
        cv.put(BP01_HOUSE_NO,detailsData[houseNo]);
        cv.put(BP01_POST_CODE,detailsData[postalCode]);
        cv.put(BP01_PROPERTY_CODE,propCodeID);
        cv.put(BP01_PROPERTY_ID,propID);
        cv.put(BP01_PROPERTY_TYPE,detailsData[propertyType]);

        value = db.insert(BP01_PROPERTIES, null, cv);

        return value;
    }

    /***************use for add data into BP01_PROPERTIES table*************/

    /***************
     * use for add data into BP01_PROPERTIES table @param accountID the account id
     *
     * @param accountID   the account id
     * @param propID      the prop id
     * @param propCodeID  the prop code id
     * @param detailsData the details data
     * @return the long
     */
    public long updateDataIntoPropertyTable(String accountID, String propID, String propCodeID, String[] detailsData){
        long value;

        int houseNo=0, street=1, city=2, postalCode=3, country=4, propertyType=5,
                noOfBedrooms=6, furnished=7, currentDate=8;

        Integer bedroom = 0;

        try {
            bedroom= Integer.parseInt(detailsData[noOfBedrooms].replaceAll("[\\D]", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ContentValues cv = new ContentValues();

        cv.put(BP01_ADDRESS1,detailsData[street]);
        cv.put(BP01_ADDRESS2,detailsData[city]);
        cv.put(BP01_ADDRESS3,detailsData[country]);
        cv.put(BP01_BEDROOMS,bedroom);
        cv.put(BP01_CURRENT_DATE,detailsData[currentDate]);
        cv.put(BP01_FURNISHED,detailsData[furnished]);
        cv.put(BP01_HOUSE_NO,detailsData[houseNo]);
        cv.put(BP01_POST_CODE,detailsData[postalCode]);
        cv.put(BP01_PROPERTY_CODE,propCodeID);
        cv.put(BP01_PROPERTY_TYPE,detailsData[propertyType]);

        value = db.update(BP01_PROPERTIES, cv, B01_ACCOUNT_ID + "=" + "'" + accountID + "'" + " AND " + BP01_PROPERTY_ID + "=" + "'" + propID + "'", null);

        return value;
    }

    /***************use for add data into BP01_PROPERTIES table*************/

    /*************
     * use for get data from BP01_PROPERTIES table @param accountID the account id
     *
     * @param accountID the account id
     * @return the cursor
     */
    public Cursor fetchIncomingDetails(String accountID){
        Cursor c;

        c= db.query(BP01_PROPERTIES, null, B01_ACCOUNT_ID+"="+"'"+accountID+"'", null, null, null, null );

        return c;
    }

    /*************use for get data from BP01_PROPERTIES table*************/

    /*************
     * delete data from BP01_PROPERTIES table @param accountID the account id
     *
     * @param accountID the account id
     * @param propID    the prop id
     */
    public void deletePropertyfield(String accountID, String propID){

        db.delete(BP01_PROPERTIES,B01_ACCOUNT_ID + "=?" + " and "  +BP01_PROPERTY_ID +"=?",new String[]{accountID,propID});
        db.delete(B01_DOCUMENT, B01_DOC_PROPERTYTYPE+"=?", new String[]{propID});
        db.delete(BP02_INVENTORY,BP02_PROPERTY_ID +"=?",new String[]{propID});
    }

    /*************
     * delete data from BP01_PROPERTIES table @param accountID the account id
     *
     * @param accountID  the account id
     * @param propertyId the property id
     * @param date       the date
     * @param comment    the comment
     * @param count      the count
     * @param displayNo  the display no
     * @param dataInput  the data input
     * @param lvl0code   the lvl 0 code
     * @param lvl1code   the lvl 1 code
     * @param lvl2code   the lvl 2 code
     * @param lvl3code   the lvl 3 code
     * @param lvl4code   the lvl 4 code
     * @param lvl5code   the lvl 5 code
     * @param reportNo   the report no
     * @param photo1     the photo 1
     * @param photo2     the photo 2
     * @param photo3     the photo 3
     * @param level      the level
     * @return the long
     */
/*################################### ADD DATA INTO BP02_INVENTORY TABLE ################################*/

    public long insertDataIntoInventoryTable(String accountID,
                                             String propertyId,
                                             String date,
                                             String comment,
                                             String count,
                                             String displayNo,
                                             String dataInput,
                                             String lvl0code,
                                             String lvl1code,
                                             String lvl2code,
                                             String lvl3code,
                                             String lvl4code,
                                             String lvl5code,
                                             String reportNo,
                                             String photo1,
                                             String photo2,
                                             String photo3,
                                             String level,
                                             String originalLevel){
        long value;
        ContentValues cv = new ContentValues();

        cv.put(BP01_INV_ACCOUNT_ID,accountID);
        cv.put(BP02_INV_DATE,date);
        cv.put(BP02_INV_ITEM_COMMENTS,comment);
        cv.put(BP02_INV_ITEM_COUNT,count);
        cv.put(BP02_INV_ITEM_DISPLAY_NO_I,displayNo);
        cv.put(BP02_INV_ITEM_INPUT,dataInput);
        cv.put(BP02_INV_ITEM_L0,lvl0code);
        cv.put(BP02_INV_ITEM_L1,lvl1code);
        cv.put(BP02_INV_ITEM_L2,lvl2code);
        cv.put(BP02_INV_ITEM_L3,lvl3code);
        cv.put(BP02_INV_ITEM_L4,lvl4code);
        cv.put(BP02_INV_ITEM_L5,lvl5code);
        cv.put(BP02_INV_ITEM_REPORT_NO_I,reportNo);
        cv.put(BP02_INV_PHOTO1_DOC_ID,photo1);
        cv.put(BP02_INV_PHOTO2_DOC_ID,photo2);
        cv.put(BP02_INV_PHOTO3_DOC_ID,photo3);
        cv.put(BP02_PROPERTY_ID,propertyId);
        cv.put(BP02_INV_ITEM_LEVEL,level);
        cv.put(BP02_INV_ITEM_MULTI_FLAG_S,originalLevel);

        value = db.insert(BP02_INVENTORY,null,cv);
        return value;
    }


    public long insertDataIntoInventoryTable_multi(String accountID,
                                                   String propertyId,
                                                   String date,
                                                   String comment,
                                                   String count,
                                                   String displayNo,
                                                   String dataInput,
                                                   String lvl0code,
                                                   String lvl1code,
                                                   String lvl2code,
                                                   String lvl3code,
                                                   String lvl4code,
                                                   String lvl5code,
                                                   String reportNo,
                                                   String photo1,
                                                   String photo2,
                                                   String photo3,
                                                   String level,
                                                   int mValue,
                                                   String orignalLevel){
        long value;
        ContentValues cv = new ContentValues();

        cv.put(BP01_INV_ACCOUNT_ID,accountID);
        cv.put(BP02_INV_DATE,date);
        cv.put(BP02_INV_ITEM_COMMENTS,comment);
        cv.put(BP02_INV_ITEM_COUNT,count);
        cv.put(BP02_INV_ITEM_DISPLAY_NO_I,displayNo);
        cv.put(BP02_INV_ITEM_INPUT,dataInput);
        cv.put(BP02_INV_ITEM_L0,lvl0code);
        cv.put(BP02_INV_ITEM_L1,lvl1code);
        cv.put(BP02_INV_ITEM_L2,lvl2code);
        cv.put(BP02_INV_ITEM_L3,lvl3code);
        cv.put(BP02_INV_ITEM_L4,lvl4code);
        cv.put(BP02_INV_ITEM_L5,lvl5code);
        cv.put(BP02_INV_ITEM_REPORT_NO_I,reportNo);
        cv.put(BP02_INV_PHOTO1_DOC_ID,photo1);
        cv.put(BP02_INV_PHOTO2_DOC_ID,photo2);
        cv.put(BP02_INV_PHOTO3_DOC_ID,photo3);
        cv.put(BP02_PROPERTY_ID,propertyId);
        cv.put(BP02_INV_ITEM_LEVEL,level);
        cv.put(BP02_INV_ITEM_MULTI_FLAG,mValue);
        cv.put(BP02_INV_ITEM_MULTI_FLAG_S,orignalLevel);

        value = db.insert(BP02_INVENTORY,null,cv);
        return value;
    }

    /***********
     * update data into table @param accountID the account id
     *
     * @param accountID  the account id
     * @param propertyId the property id
     * @param date       the date
     * @param comment    the comment
     * @param count      the count
     * @param displayNo  the display no
     * @param dataInput  the data input
     * @param lvl0code   the lvl 0 code
     * @param lvl1code   the lvl 1 code
     * @param lvl2code   the lvl 2 code
     * @param lvl3code   the lvl 3 code
     * @param lvl4code   the lvl 4 code
     * @param lvl5code   the lvl 5 code
     * @param reportNo   the report no
     * @param photo1     the photo 1
     * @param photo2     the photo 2
     * @param photo3     the photo 3
     * @param level      the level
     * @return the long
     */
    public long updateDataIntoInventoryTable(String accountID,
                                             String propertyId,
                                             String date,
                                             String comment,
                                             String count,
                                             String displayNo,
                                             String dataInput,
                                             String lvl0code,
                                             String lvl1code,
                                             String lvl2code,
                                             String lvl3code,
                                             String lvl4code,
                                             String lvl5code,
                                             String reportNo,
                                             String photo1,
                                             String photo2,
                                             String photo3,
                                             @NonNull String level,
                                             String origanalLavel){
        long value;

        ContentValues cv = new ContentValues();

        cv.put(BP01_INV_ACCOUNT_ID,accountID);
        cv.put(BP02_INV_DATE,date);
        cv.put(BP02_INV_ITEM_COMMENTS,comment);
        cv.put(BP02_INV_ITEM_COUNT,count);
        cv.put(BP02_INV_ITEM_DISPLAY_NO_I,displayNo);
        cv.put(BP02_INV_ITEM_INPUT,dataInput);
        cv.put(BP02_INV_ITEM_L0,lvl0code);
        cv.put(BP02_INV_ITEM_L1,lvl1code);
        cv.put(BP02_INV_ITEM_L2,lvl2code);
        cv.put(BP02_INV_ITEM_L3,lvl3code);
        cv.put(BP02_INV_ITEM_L4,lvl4code);
        cv.put(BP02_INV_ITEM_L5,lvl5code);
        cv.put(BP02_INV_ITEM_REPORT_NO_I,reportNo);
        cv.put(BP02_INV_PHOTO1_DOC_ID,photo1);
        cv.put(BP02_INV_PHOTO2_DOC_ID,photo2);
        cv.put(BP02_INV_PHOTO3_DOC_ID,photo3);
        cv.put(BP02_PROPERTY_ID,propertyId);

        value = db.update(BP02_INVENTORY, cv,
                getWhereClause(level),
                getWhereArgs(propertyId, date, lvl0code, lvl1code, lvl2code, lvl3code, lvl4code, lvl5code, level,origanalLavel));

        return value;
    }

    public long updateDataIntoInventoryTable_multi(String accountID,
                                             String propertyId,
                                             String date,
                                             String comment,
                                             String count,
                                             String displayNo,
                                             String dataInput,
                                             String lvl0code,
                                             String lvl1code,
                                             String lvl2code,
                                             String lvl3code,
                                             String lvl4code,
                                             String lvl5code,
                                             String reportNo,
                                             String photo1,
                                             String photo2,
                                             String photo3,
                                             @NonNull String level,
                                                   int multiFlag,
                                                   String origanalFlag){
        long value;

        ContentValues cv = new ContentValues();

        cv.put(BP01_INV_ACCOUNT_ID,accountID);
        cv.put(BP02_INV_DATE,date);
        cv.put(BP02_INV_ITEM_COMMENTS,comment);
        cv.put(BP02_INV_ITEM_COUNT,count);
        cv.put(BP02_INV_ITEM_DISPLAY_NO_I,displayNo);
        cv.put(BP02_INV_ITEM_INPUT,dataInput);
        cv.put(BP02_INV_ITEM_L0,lvl0code);
        cv.put(BP02_INV_ITEM_L1,lvl1code);
        cv.put(BP02_INV_ITEM_L2,lvl2code);
        cv.put(BP02_INV_ITEM_L3,lvl3code);
        cv.put(BP02_INV_ITEM_L4,lvl4code);
        cv.put(BP02_INV_ITEM_L5,lvl5code);
        cv.put(BP02_INV_ITEM_REPORT_NO_I,reportNo);
        cv.put(BP02_INV_PHOTO1_DOC_ID,photo1);
        cv.put(BP02_INV_PHOTO2_DOC_ID,photo2);
        cv.put(BP02_INV_PHOTO3_DOC_ID,photo3);
        cv.put(BP02_PROPERTY_ID,propertyId);

        value = db.update(BP02_INVENTORY, cv,getWhereClause_multi(level),getWhereArgs_multi(propertyId, date, lvl0code, lvl1code, lvl2code, lvl3code, lvl4code, lvl5code, level,multiFlag,origanalFlag));

        return value;
    }


    @NonNull
    private String[] getWhereArgs(String propertyId, String date, String lvl0code, String lvl1code, String lvl2code, String lvl3code, String lvl4code, String lvl5code, @NonNull String level, String origalLavel) {

        if(level.equals("2")){
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,level,origalLavel};
        }
        else if(level.equals("3")){
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,lvl3code,level,origalLavel};
        }
        else if(level.equals("4")){
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,lvl3code,lvl4code,level,origalLavel};
        }
        else{
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,lvl3code,lvl4code,lvl5code,level,origalLavel};
        }


    }

    private String[] getWhereArgs_multi(String propertyId, String date, String lvl0code, String lvl1code, String lvl2code, String lvl3code, String lvl4code, String lvl5code, @NonNull String level, int multiFlag, String origalFlag) {

        if(level.equals("2")){
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,level,String.valueOf(multiFlag),origalFlag};
        }
        else if(level.equals("3")){
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,lvl3code,level,String.valueOf(multiFlag),origalFlag};
        }
        else if(level.equals("4")){
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,lvl3code,lvl4code,level,String.valueOf(multiFlag),origalFlag};
        }
        else{
            return new String[]{propertyId, date, lvl0code, lvl1code, lvl2code,lvl3code,lvl4code,lvl5code,level,String.valueOf(multiFlag),origalFlag};
        }


    }

    @NonNull
    private String getWhereClause(@NonNull String level) {

        if(level.equals("2")){
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";

        }
        else if(level.equals("3")){
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_L3 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";
        }
        else if(level.equals("4")){
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_L3 + "=?"
                    + " and " + BP02_INV_ITEM_L4 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";
        }
        else{
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_L3 + "=?"
                    + " and " + BP02_INV_ITEM_L4 + "=?"
                    + " and " + BP02_INV_ITEM_L5 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";
        }

    }

    private String getWhereClause_multi(@NonNull String level) {

        if(level.equals("2")){
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";
        }
        else if(level.equals("3")){
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_L3 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";
        }
        else if(level.equals("4")){
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_L3 + "=?"
                    + " and " + BP02_INV_ITEM_L4 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";
        }
        else{
            return BP02_PROPERTY_ID + "=?"
                    + " and " + BP02_INV_DATE + "=?"
                    + " and " + BP02_INV_ITEM_L0 + "=?"
                    + " and " + BP02_INV_ITEM_L1 + "=?"
                    + " and " + BP02_INV_ITEM_L2 + "=?"
                    + " and " + BP02_INV_ITEM_L3 + "=?"
                    + " and " + BP02_INV_ITEM_L4 + "=?"
                    + " and " + BP02_INV_ITEM_L5 + "=?"
                    + " and " + BP02_INV_ITEM_LEVEL + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG + "=?"
                    + " and " + BP02_INV_ITEM_MULTI_FLAG_S + "=?";
        }

    }

    /***********
     * update data into table @param Code the code
     *
     * @param Code     the code
     * @param Date     the date
     * @param PropId   the prop id
     * @param zeroCode the zero code
     * @param oneCode  the one code
     * @param level    the level
     * @return the cursor
     */


    public Cursor fetchDetailsInventory2(String Code, String Date, String PropId, String zeroCode, String oneCode, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_INPUT+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L2+"="+"'"+Code+"'"
                +" and "+BP02_INV_DATE+"="+"'"+Date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropId+"'"
                +" and "+BP02_INV_ITEM_L0+"="+"'"+zeroCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+oneCode+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }


    public Cursor fetchDetailsInventory2_multi(String Code, String Date, String PropId, String zeroCode, String oneCode, String level, int multiFlag){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_INPUT+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L2+"="+"'"+Code+"'"
                +" and "+BP02_INV_DATE+"="+"'"+Date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropId+"'"
                +" and "+BP02_INV_ITEM_L0+"="+"'"+zeroCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+oneCode+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag

                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
     //   c.close();
        return c;
    }
    /**
     * Fetch details inventory 3 cursor.
     *
     * @param Code     the code
     * @param Date     the date
     * @param PropId   the prop id
     * @param ZeroCode the zero code
     * @param OneCode  the one code
     * @param TwoCode  the two code
     * @param level    the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventory3(String Code, String Date, String PropId, String ZeroCode, String OneCode, String TwoCode, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_INPUT+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L3+"="+"'"+Code+"'"
                +" and "+BP02_INV_DATE+"="+"'"+Date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropId+"'"
                +" and "+BP02_INV_ITEM_L0+"="+"'"+ZeroCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OneCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwoCode+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchDetailsInventory3_multi(String Code, String Date, String PropId, String ZeroCode, String OneCode, String TwoCode, String level, int MultiFlag){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_INPUT+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L3+"="+"'"+Code+"'"
                +" and "+BP02_INV_DATE+"="+"'"+Date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropId+"'"
                +" and "+BP02_INV_ITEM_L0+"="+"'"+ZeroCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OneCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwoCode+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+MultiFlag;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        //c.close();
        return c;
    }




    /**
     * Fetch details inventory 4 cursor.
     *
     * @param Code      the code
     * @param Date      the date
     * @param PropId    the prop id
     * @param ZeroCode  the zero code
     * @param OneCode   the one code
     * @param TwoCode   the two code
     * @param ThreeCode the three code
     * @param level     the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventory4(String Code, String Date, String PropId, String ZeroCode, String OneCode, String TwoCode, String ThreeCode, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_INPUT+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L4+"="+"'"+Code+"'"
                +" and "+BP02_INV_DATE+"="+"'"+Date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropId+"'"
                +" and "+BP02_INV_ITEM_L0+"="+"'"+ZeroCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OneCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwoCode+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreeCode+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchDetailsInventory4_multi(String Code, String Date, String PropId, String ZeroCode, String OneCode, String TwoCode, String ThreeCode, String level,int MultiFlag){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_INPUT+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L4+"="+"'"+Code+"'"
                +" and "+BP02_INV_DATE+"="+"'"+Date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropId+"'"
                +" and "+BP02_INV_ITEM_L0+"="+"'"+ZeroCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OneCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwoCode+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreeCode+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+MultiFlag;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        // c.close();
        return c;
    }

    /**
     * Fetch details inventory 5 cursor.
     *
     * @param Code       the code
     * @param Date       the date
     * @param PropId     the prop id
     * @param ZeroCode   the zero code
     * @param OneCode    the one code
     * @param TwoCode    the two code
     * @param ThreeCode  the three code
     * @param FourthCode the fourth code
     * @param level      the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventory5(String Code, String Date, String PropId, String ZeroCode, String OneCode, String TwoCode, String ThreeCode, String FourthCode, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_INPUT+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L5+"="+"'"+Code+"'"
                +" and "+BP02_INV_DATE+"="+"'"+Date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropId+"'"
                +" and "+BP02_INV_ITEM_L0+"="+"'"+ZeroCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OneCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwoCode+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreeCode+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+FourthCode+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
      //  c.close();
        return c;
    }

    /**
     * Fetch details inventory table cursor.
     *
     * @param ZerolvlCode the zerolvl code
     * @param OnelvlCode  the onelvl code
     * @param TwolvlData  the twolvl data
     * @param date        the date
     * @param PropertyId  the property id
     * @param level       the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable(String ZerolvlCode, String OnelvlCode, String TwolvlData,String ThreelvlData, String date, String PropertyId, String level){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchDetailsInventoryTable_multi(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlCode, String date, String PropertyId, String level, int multFlag){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlCode+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multFlag;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       //c.close();
        return c;
    }
    public Cursor fetchDetailsInventoryTable_multi_3rd(String ZerolvlCode, String OnelvlCode, String TwolvlData,String ThreelvlData,String FourlvlData, String date, String PropertyId, String level, int multFlag){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+FourlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multFlag;

        c = db.rawQuery(q,null);
       // c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor checkMaxNoFor_Unique_Field(String ZerolvlCode, String OnelvlCode, String date, String PropertyId, String level){
        Cursor c;

        String q = "select max("+BP02_INV_ITEM_MULTI_FLAG+") from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"

                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
       // c.moveToFirst();
        //c.close();
        return c;
    }

    public Cursor checkMaxNoFor_Unique_Field_3rd(String ZerolvlCode, String OnelvlCode,String twolvlCode, String date, String PropertyId, String level){
        Cursor c;

        String q = "select max("+BP02_INV_ITEM_MULTI_FLAG+") from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+twolvlCode+"'"

                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        //c.close();
        return c;
    }


    public Cursor checkMaxNoFor_Unique_Field_4th(String ZerolvlCode, String OnelvlCode,String twolvlCode,String threelvlCode, String date, String PropertyId, String level){
        Cursor c;

        String q = "select max("+BP02_INV_ITEM_MULTI_FLAG+") from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+twolvlCode+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+threelvlCode+"'"

                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        //c.close();
        return c;
    }

    public void deleteEntry(String ZerolvlCode, String OnelvlCode, String date, String PropertyId, String level, int multiFlag){

        db.delete(BP02_INVENTORY,
                BP02_INV_ITEM_L0 +" =? AND " +BP02_INV_ITEM_L1 +" =? AND "+BP02_INV_DATE +" =? AND "
                        +BP02_PROPERTY_ID +" =? AND "+BP02_INV_ITEM_LEVEL +" =? AND " +BP02_INV_ITEM_MULTI_FLAG +" =? ",
                new String[] {String.valueOf(ZerolvlCode), String.valueOf(OnelvlCode),String.valueOf(date),
                        String.valueOf(PropertyId),String.valueOf(level),String.valueOf(multiFlag) });

    }

    public void deleteEntry3rd(String ZerolvlCode, String OnelvlCode,String TwolvlCode, String date, String PropertyId, String level, int multiFlag){


        db.delete(BP02_INVENTORY,
                BP02_INV_ITEM_L0 +" =? AND " +BP02_INV_ITEM_L1 +" =? AND " +BP02_INV_ITEM_L2 +" =? AND "+BP02_INV_DATE +" =? AND "
                        +BP02_PROPERTY_ID +" =? AND "+BP02_INV_ITEM_LEVEL +" =? AND " +BP02_INV_ITEM_MULTI_FLAG +" =? ",
                new String[] {String.valueOf(ZerolvlCode), String.valueOf(OnelvlCode),String.valueOf(TwolvlCode),String.valueOf(date),
                        String.valueOf(PropertyId),String.valueOf(level),String.valueOf(multiFlag) });
    }

    public void deleteUpdateEntry(String ZerolvlCode, String OnelvlCode, String date, String PropertyId, String level, int multiFlag){

        Cursor c;
        String q = "update "+BP02_INVENTORY+" set "+BP02_INV_ITEM_MULTI_FLAG+" = "+BP02_INV_ITEM_MULTI_FLAG+"-1 where "+BP02_INV_ITEM_MULTI_FLAG+" >"+multiFlag
                +" and "+BP02_INV_ITEM_L0+" ="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+" ="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_DATE+" ="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+" ="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+" ="+"'"+level+"'";
        c = db.rawQuery(q,null);
        c.moveToFirst();
        c.close();
    }

    public void deleteUpdateEntry_3rd(String ZerolvlCode, String OnelvlCode,String TwolvlCode, String date, String PropertyId, String level, int multiFlag){

        Cursor c;
        String q = "update "+BP02_INVENTORY+" set "+BP02_INV_ITEM_MULTI_FLAG+" = "+BP02_INV_ITEM_MULTI_FLAG+"-1 where "+BP02_INV_ITEM_MULTI_FLAG+" >"+multiFlag
                +" and "+BP02_INV_ITEM_L0+" ="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+" ="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+" ="+"'"+TwolvlCode+"'"
                +" and "+BP02_INV_DATE+" ="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+" ="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+" ="+"'"+level+"'";
        c = db.rawQuery(q,null);
        c.moveToFirst();
        c.close();
    }

    public Cursor checkMaxNoFor_Unique_Field_multi(String ZerolvlCode, String OnelvlCode, String date, String PropertyId, String level, int multiFlag){
        Cursor c;

        String q = "select max("+BP02_INV_ITEM_MULTI_FLAG+") from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"

                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    /**
     * Fetch details inventory table 2 cursor.
     *
     * @param ZerolvlCode the zerolvl code
     * @param OnelvlCode  the onelvl code
     * @param TwolvlData  the twolvl data
     * @param date        the date
     * @param PropertyId  the property id
     * @param level       the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable2(String ZerolvlCode, String OnelvlCode, String TwolvlData, String date, String PropertyId, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L3+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
      //  c.close();
        return c;
    }

    public Cursor fetchDetailsInventoryTable2_multi(String ZerolvlCode, String OnelvlCode, String TwolvlData, String date, String PropertyId, String level, int multiFlag){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L3+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag
                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    /**
     * Fetch details inventory table 2 level cursor.
     *
     * @param ZerolvlCode the zerolvl code
     * @param OnelvlCode  the onelvl code
     * @param date        the date
     * @param PropertyId  the property id
     * @param level       the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable2level(String ZerolvlCode, String OnelvlCode, String date, String PropertyId, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L2+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchDetailsInventoryTable2level_multi(String ZerolvlCode, String OnelvlCode, String date, String PropertyId, String level, int multiFlag){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L2+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag
                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    /**
     * Fetch details inventory table 3 rd cursor.
     *
     * @param ZerolvlCode  the zerolvl code
     * @param OnelvlCode   the onelvl code
     * @param TwolvlData   the twolvl data
     * @param ThreelvlData the threelvl data
     * @param date         the date
     * @param PropertyId   the property id
     * @param level        the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable3rd(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData,String FourthlvlData, String date, String PropertyId, String level){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+FourthlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    /**
     * Fetch details inventory table 3 rd 2 cursor.
     *
     * @param ZerolvlCode  the zerolvl code
     * @param OnelvlCode   the onelvl code
     * @param TwolvlData   the twolvl data
     * @param ThreelvlData the threelvl data
     * @param date         the date
     * @param PropertyId   the property id
     * @param level        the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable3rd2(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData, String date, String PropertyId, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L4+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchDetailsInventoryTable3rd2_multi(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData,String FourthlvlData, String date, String PropertyId, String level, int multiFlag){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L4+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+FourthlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        // c.close();
        return c;
    }    public Cursor fetchDetailsInventoryTable3rd2_multi(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData, String date, String PropertyId, String level, int multiFlag){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L4+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"

                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        // c.close();
        return c;
    }


    /**
     * Fetch details inventory table 4 rd cursor.
     *
     * @param ZerolvlCode  the zerolvl code
     * @param OnelvlCode   the onelvl code
     * @param TwolvlData   the twolvl data
     * @param ThreelvlData the threelvl data
     * @param fourlvlData  the fourlvl data
     * @param date         the date
     * @param PropertyId   the property id
     * @param level        the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable4rd(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData, String fourlvlData, String date, String PropertyId, String level){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+fourlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
      //  c.close();
        return c;
    }

    public Cursor fetchDetailsInventoryTable4rd_multi(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData, String fourlvlData, String date, String PropertyId, String level, int multFlag, String orignalLavel){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+fourlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG_S+"="+"'"+orignalLavel+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multFlag;
                ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        //  c.close();
        return c;
    }

    public Cursor fetchDetailsDesireDB(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData, String fourlvlData,String fifthlvlData, String date, String PropertyId, String level, int multFlag,String originalLevel){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+fourlvlData+"'"
                +" and "+BP02_INV_ITEM_L5+"="+"'"+fourlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG_S+"="+"'"+originalLevel+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG+"="+multFlag;
        ;

        c = db.rawQuery(q,null);
        //c.moveToFirst();
        //  c.close();
        return c;
    }

    /**
     * Fetch details inventory table 4 rd 2 cursor.
     *
     * @param ZerolvlCode  the zerolvl code
     * @param OnelvlCode   the onelvl code
     * @param TwolvlData   the twolvl data
     * @param ThreelvlData the threelvl data
     * @param fourlvlData  the fourlvl data
     * @param date         the date
     * @param PropertyId   the property id
     * @param level        the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable4rd2(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData, String fourlvlData, String date, String PropertyId, String level){
        Cursor c;

        String q = "select "+BP02_INV_ITEM_L5+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+fourlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'";

        c = db.rawQuery(q,null);
       // c.moveToFirst();
      //  c.close();
        return c;
    }


    /**
     * Fetch details inventory table 5 rd cursor.
     *
     * @param ZerolvlCode  the zerolvl code
     * @param OnelvlCode   the onelvl code
     * @param TwolvlData   the twolvl data
     * @param ThreelvlData the threelvl data
     * @param fourlvlData  the fourlvl data
     * @param fifthlvlData the fifthlvl data
     * @param date         the date
     * @param PropertyId   the property id
     * @param level        the level
     * @return the cursor
     */
    public Cursor fetchDetailsInventoryTable5rd(String ZerolvlCode, String OnelvlCode, String TwolvlData, String ThreelvlData, String fourlvlData, String fifthlvlData, String date, String PropertyId, String level, String orginalLevel){
        Cursor c;

        String q = "select * from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_ITEM_L3+"="+"'"+ThreelvlData+"'"
                +" and "+BP02_INV_ITEM_L4+"="+"'"+fourlvlData+"'"
                +" and "+BP02_INV_ITEM_L5+"="+"'"+fifthlvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'"
                +" and "+BP02_INV_ITEM_MULTI_FLAG_S+"="+"'"+orginalLevel+"'"
                +" and "+BP02_INV_ITEM_LEVEL+"="+"'"+level+"'"
                ;

        c = db.rawQuery(q,null);
       // c.moveToFirst();
      //  c.close();
        return c;
    }

    /**
     * Fetch photo id cursor.
     *
     * @param ZerolvlCode the zerolvl code
     * @param OnelvlCode  the onelvl code
     * @param TwolvlData  the twolvl data
     * @param date        the date
     * @param PropertyId  the property id
     * @return the cursor
     */
    public Cursor fetchPhotoId(String ZerolvlCode, String OnelvlCode, String TwolvlData, String date, String PropertyId){
        Cursor c;

        String q = "select "+BP02_INV_PHOTO1_DOC_ID+","+BP02_INV_PHOTO2_DOC_ID+","+BP02_INV_PHOTO3_DOC_ID+" from "+ BP02_INVENTORY +" where "
                +BP02_INV_ITEM_L0+"="+"'"+ZerolvlCode+"'"
                +" and "+BP02_INV_ITEM_L1+"="+"'"+OnelvlCode+"'"
                +" and "+BP02_INV_ITEM_L2+"="+"'"+TwolvlData+"'"
                +" and "+BP02_INV_DATE+"="+"'"+date+"'"
                +" and "+BP02_PROPERTY_ID+"="+"'"+PropertyId+"'";

        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    /**
     * Fetch property id cursor.
     *
     * @return the cursor
     */
    public Cursor fetchPropertyID(){
        Cursor c;

        String q = "select "+BP02_PROPERTY_ID+" from "+ BP02_INVENTORY;
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    /**
     * Fetch zerolvt data code cursor.
     *
     * @param propertyID the property id
     * @param date       the date
     * @return the cursor
     */
    public Cursor fetchZerolvtDataCode(String propertyID, String date){

        Cursor c;
        String q ="SELECT "+BP02_INV_ITEM_L0+" FROM "+ BP02_INVENTORY +" WHERE "+BP02_PROPERTY_ID+" LIKE " +
                "'"+propertyID+"'"+" AND "+BP02_INV_DATE+" LIKE "+"'"+date +"'";
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    /**
     * Date: 17-march-2017
     * @param propertyID
     * @param date
     * @param mZerolvlCode
     * @return
     * use: use for check value form database
     */
    public Cursor fetchZerolvtDataCodeValue(String propertyID, String date, String mZerolvlCode){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'";
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchOnelvtDataCodeValue(String propertyID, String date, String mZerolvlCode, String mOnelvlCode){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'";
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }


    public Cursor fetchTwolvtDataCodeValue(String propertyID, String date, String mZerolvlCode, String mOnelvlCode, String mTwolvlCode){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L2+" LIKE "+"'"+mTwolvlCode +"'";
        c = db.rawQuery(q,null);
        //c.moveToFirst();
      //  c.close();
        return c;
    }

    public Cursor fetchTwolvtDataCodeValue_multi(String propertyID, String date, String mZerolvlCode, String mOnelvlCode, String mTwolvlCode, int multiFlag){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L2+" LIKE "+"'"+mTwolvlCode +"'"+" AND "
                +BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag;
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchThreelvtDataCodeValue(String propertyID, String date, String mZerolvlCode, String mOnelvlCode, String mTwolvlCode, String mThreelvlCode){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L2+" LIKE "+"'"+mTwolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L3+" LIKE "+"'"+mThreelvlCode +"'";
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchThreelvtDataCodeValue_multi(String propertyID, String date, String mZerolvlCode, String mOnelvlCode, String mTwolvlCode, String mThreelvlCode, int multiFlag){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L2+" LIKE "+"'"+mTwolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L3+" LIKE "+"'"+mThreelvlCode +"'"+" AND "
                +BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag;
                ;
        c = db.rawQuery(q,null);
        //c.moveToFirst();
      //  c.close();
        return c;
    }


    public Cursor fetchFourthlvtDataCodeValue(String propertyID, String date, String mZerolvlCode, String mOnelvlCode, String mTwolvlCode, String mThreelvlCode, String mFourthlvlCode){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L2+" LIKE "+"'"+mTwolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L3+" LIKE "+"'"+mThreelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L4+" LIKE "+"'"+mFourthlvlCode +"'";
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }

    public Cursor fetchFourthlvtDataCodeValue_multi(String propertyID, String date, String mZerolvlCode, String mOnelvlCode, String mTwolvlCode, String mThreelvlCode, String mFourthlvlCode, int multiFlag){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L2+" LIKE "+"'"+mTwolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L3+" LIKE "+"'"+mThreelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L4+" LIKE "+"'"+mFourthlvlCode +"'"+" AND "
                +BP02_INV_ITEM_MULTI_FLAG+"="+multiFlag;
        c = db.rawQuery(q,null);
        //c.moveToFirst();
        // c.close();
        return c;
    }


    public Cursor fetchFifthlvtDataCodeValue(String propertyID, String date, String mZerolvlCode, String mOnelvlCode, String mTwolvlCode, String mThreelvlCode, String mFourthlvlCode,String mFifthlvlCode){

        Cursor c;
        String q ="SELECT * FROM "+ BP02_INVENTORY +" WHERE "
                +BP02_PROPERTY_ID+" LIKE " +"'"+propertyID+"'"+" AND "
                +BP02_INV_DATE+" LIKE "+"'"+date +"'"+" AND "
                +BP02_INV_ITEM_L0+" LIKE "+"'"+mZerolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L1+" LIKE "+"'"+mOnelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L2+" LIKE "+"'"+mTwolvlCode +"'"+" AND "
                +BP02_INV_ITEM_L3+" LIKE "+"'"+mThreelvlCode +"'"+" AND "
                +BP02_INV_ITEM_L4+" LIKE "+"'"+mFourthlvlCode +"'"+" AND "
                +BP02_INV_ITEM_L5+" LIKE "+"'"+mFifthlvlCode +"'";
        c = db.rawQuery(q,null);
        //c.moveToFirst();
       // c.close();
        return c;
    }


    /**
     * Fetch details by id cursor.
     *
     * @param propertyID the property id
     * @return the cursor
     */
    public Cursor fetchDetailsById(String propertyID){
        Cursor c;
        String q = "SELECT "+BP01_ADDRESS1+","+BP01_HOUSE_NO+","+BP01_POST_CODE+","+BP01_CURRENT_DATE+","+BP01_FURNISHED+
                " FROM "+BP01_PROPERTIES+" WHERE "+BP01_PROPERTY_ID+" LIKE " + "'" + propertyID + "'";
        c = db.rawQuery(q, null);
        //c.moveToFirst();
        //c.close();
        return c;
    }

    /**
     * Fetch current date by id cursor.
     *
     * @param propertyID the property id
     * @return the cursor
     */
    public Cursor fetchCurrentDateById(String propertyID){
        Cursor c;
        String q = "SELECT "+BP01_CURRENT_DATE+" FROM "+BP01_PROPERTIES+" WHERE "+BP01_PROPERTY_ID+" LIKE " + "'" + propertyID + "'";
        c = db.rawQuery(q, null);
        //c.moveToFirst();
       // c.close();
        return c;
    }


    /**
     * Fetch details house no cursor.
     *
     * @param propertyid the propertyid
     * @return the cursor
     */
    public Cursor fetchDetailsHouseNo(String propertyid){
        Cursor c;

        // use for fetching data of specific columnn like .. select column from tablename
        c= db.query(BP01_PROPERTIES, new String[]{BP01_HOUSE_NO}, BP01_PROPERTY_ID +"= ?", new String[]{propertyid}, null, null, null);

        return c;
    }

    /**
     * Get inventory date cursor.
     *
     * @param propertyID the property id
     * @return the cursor
     */
    public Cursor getInventoryDate(String propertyID){
        Cursor c;
        //SELECT BP02_INV_DATE FROM BP02_INVENTORY WHERE BP02_PROPERTY_IDLIKE'4'
        String q ="SELECT "+BP02_INV_DATE+" FROM "+ BP02_INVENTORY +" WHERE "+BP02_PROPERTY_ID +
                " LIKE" + "'" + propertyID + "'";
        c = db.rawQuery(q, null);
        //c.moveToFirst();
      //  c.close();
        return c;
    }

       /*################################### ADD DATA INTO BP02_INVENTORY TABLE ################################*/

    /*########################### FOR B01_DOCUMENT #################*/

    /*************
     * use for get data from B01_DOCUMENT table @return the cursor
     *
     * @return the cursor
     */
    public Cursor fetchDetailsDocumentTable(){
        Cursor c;

        // use for fetching data of specific columnn like .. select column from tablename
        c= db.query(B01_DOCUMENT, new String[]{B01_DOC_ID}, null, null, null, null, null);

        return c;
    }

    /*************use for get data from B01_DOCUMENT table*************/

    /*************
     * use for insert data from B01_DOCUMENT table @param Docdate the docdate
     *
     * @param Docdate   the docdate
     * @param DocID     the doc id
     * @param DocMIME   the doc mime
     * @param DocSource the doc source
     * @param DocType   the doc type
     * @param PropType  the prop type
     * @return the long
     */
    public long insertDataIntoDocumentTable(String Docdate, String DocID, String DocMIME, String DocSource, String DocType, String PropType){
        long value;

        ContentValues cv = new ContentValues();

        cv.put(B01_DOC_DATE,Docdate); // date
        cv.put(B01_DOC_ID,DocID);   // id of doc
        cv.put(B01_DOC_MIMETYPE,DocMIME); // mime type.. jpg
        cv.put(B01_DOC_SOURCE_FN,DocSource); // img source
        cv.put(B01_DOC_TYPE,DocType); // PHOTO
        cv.put(B01_DOC_PROPERTYTYPE,PropType); // PHOTO

        value = db.insert(B01_DOCUMENT, null, cv);

        return value;
    }

    /*************
     * use for insert data from B01_DOCUMENT table @param propId the prop id
     *
     * @param propId the prop id
     * @param date   the date
     */


    /*######################################DELETE VIEW AND DOVUMENT TABLE DATA*/
    public void deleteInventoryCocumentTable(String propId, String date){

        db.delete(B01_DOCUMENT, B01_DOC_PROPERTYTYPE+"=?"+" and "+B01_DOC_DATE +"=?", new String[]{propId,date});
        db.delete(BP02_INVENTORY,BP02_PROPERTY_ID +"=?"+" and "+BP02_INV_DATE +"=?",new String[]{propId,date});
    }

    /**
     * Delete property table.
     *
     * @param propID the prop id
     * @param date   the date
     */
/*######################################DELETE VIEW AND DOVUMENT TABLE DATA*/
    public void deletePropertyTable(String propID, String date){

        db.delete(BP02_INVENTORY,BP02_PROPERTY_ID +"=?"+" and "+BP02_INV_DATE +"=?",new String[]{propID,date});
    }
      /*######################################DELETE VIEW AND DOVUMENT TABLE DATA*/


}
