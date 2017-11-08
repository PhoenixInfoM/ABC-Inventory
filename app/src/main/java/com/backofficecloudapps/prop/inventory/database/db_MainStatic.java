package com.backofficecloudapps.prop.inventory.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Phoenix
 */
public class db_MainStatic {

    public SQLiteDatabase db;
    private Context CONTEXT;

    /**
     * Instantiates a new Db main static.
     *
     * @param context1 the context 1
     */
    public db_MainStatic(Context context1)
    {
         this.CONTEXT = context1;
    }

    /**
     * Open DataBase
     */
    public void Open()
    {
        DataBaseHelper helper = new DataBaseHelper();
        db = helper.getExternalDataBase(CONTEXT);
    }

    /**
     * Close Database
     */
    public void Close(){
        db.close();
    }

    /***********************************
     * INVENTORY @param code the code
     *
     * @return the inventory detailsforzero
     */
    public Cursor getInventoryDetailsforzero(String code)
    {
        Cursor c;
        String q ="select BP02_INV_ITEM_DESCRIPTION from BP02_INVENTORY_ITEM WHERE BP02_INV_ITEM_LEVEL = 0"+" AND "+"BP02_INV_ITEM_CODE"+"="+"'"+code+"'";
        c=db.rawQuery(q, null);
        return c;
    }

    /**
     * Gets inventory detailsbycode.
     *
     * @param code the code
     * @return the inventory detailsbycode
     */
    public Cursor getInventoryDetailsbycode(String code)
    {
        Cursor c;
        String q ="select BP02_INV_ITEM_DESCRIPTION from BP02_INVENTORY_ITEM Where "+"BP02_INV_ITEM_CODE"+"="+"'"+code+"'";
        c=db.rawQuery(q, null);
        return c;
    }

}
