package com.backofficecloudapps.prop.inventory.database;

/**
 * Created by Phoenix
 */

/**
 * Table that will store all data related to inventories
 */
public class TableInventories {

    String INVENTORYDATE;
    String PROPERTYID;
     String ZEROLEVELITEM;
    String FIRSTLEVELITEM;
     String SECONDLEVELITEM;
     String THIRDLEVELDATA;
     String INVENTORYITEMPHOTOS;

    /**
     * Instantiates a new Table inventories.
     */
    public TableInventories(){
  }

    /**
     * Instantiates a new Table inventories.
     *
     * @param INVENTORYDATE       the inventorydate
     * @param PROPERTYID          the propertyid
     * @param ZEROLEVELITEM       the zerolevelitem
     * @param FIRSTLEVELITEM      the firstlevelitem
     * @param SECONDLEVELITEM     the secondlevelitem
     * @param THIRDLEVELDATA      the thirdleveldata
     * @param INVENTORYITEMPHOTOS the inventoryitemphotos
     */
    public TableInventories(String INVENTORYDATE, String PROPERTYID, String ZEROLEVELITEM,
                            String FIRSTLEVELITEM, String SECONDLEVELITEM, String THIRDLEVELDATA,
                            String INVENTORYITEMPHOTOS){
	this.INVENTORYDATE = INVENTORYDATE;
	this.PROPERTYID = PROPERTYID;
	this.ZEROLEVELITEM = ZEROLEVELITEM;
	this.FIRSTLEVELITEM = FIRSTLEVELITEM;
	this.SECONDLEVELITEM = SECONDLEVELITEM;
	this.THIRDLEVELDATA = THIRDLEVELDATA;
    this.INVENTORYITEMPHOTOS = INVENTORYITEMPHOTOS;
    }

    /**
     * Get inventorydate string.
     *
     * @return the string
     */
    public String getINVENTORYDATE(){
	return INVENTORYDATE;
  }

    /**
     * Set inventorydate.
     *
     * @param INVENTORYDATE the inventorydate
     */
    public void setINVENTORYDATE(String INVENTORYDATE){
	this.INVENTORYDATE = INVENTORYDATE;
  }

    /**
     * Get propertyid string.
     *
     * @return the string
     */
    public String getPROPERTYID(){
	return PROPERTYID;
  }

    /**
     * Set propertyid.
     *
     * @param PROPERTYID the propertyid
     */
    public void setPROPERTYID(String PROPERTYID){
	this.PROPERTYID = PROPERTYID;
  }

    /**
     * Get zerolevelitem string.
     *
     * @return the string
     */
    public String getZEROLEVELITEM(){
	return ZEROLEVELITEM;
  }

    /**
     * Set zerolevelitem.
     *
     * @param ZEROLEVELITEM the zerolevelitem
     */
    public void setZEROLEVELITEM(String ZEROLEVELITEM){
	this.ZEROLEVELITEM = ZEROLEVELITEM;
  }

    /**
     * Get firstlevelitem string.
     *
     * @return the string
     */
    public String getFIRSTLEVELITEM(){
	return FIRSTLEVELITEM;
  }

    /**
     * Set firstlevelitem.
     *
     * @param FIRSTLEVELITEM the firstlevelitem
     */
    public void setFIRSTLEVELITEM(String FIRSTLEVELITEM){
	this.FIRSTLEVELITEM = FIRSTLEVELITEM;
  }

    /**
     * Get secondlevelitem string.
     *
     * @return the string
     */
    public String getSECONDLEVELITEM(){
	return SECONDLEVELITEM;
  }

    /**
     * Set secondlevelitem.
     *
     * @param SECONDLEVELITEM the secondlevelitem
     */
    public void setSECONDLEVELITEM(String SECONDLEVELITEM){
	this.SECONDLEVELITEM = SECONDLEVELITEM;
  }

    /**
     * Get thirdleveldata string.
     *
     * @return the string
     */
    public String getTHIRDLEVELDATA(){
	return THIRDLEVELDATA;
  }

    /**
     * Set thirdleveldata.
     *
     * @param THIRDLEVELDATA the thirdleveldata
     */
    public void setTHIRDLEVELDATA(String THIRDLEVELDATA){
	this.THIRDLEVELDATA = THIRDLEVELDATA;
  }

    /**
     * Get inventoryitemphotos string.
     *
     * @return the string
     */
    public String getINVENTORYITEMPHOTOS(){
	return INVENTORYITEMPHOTOS;
  }

    /**
     * Set inventoryitemphotos.
     *
     * @param INVENTORYITEMPHOTOS the inventoryitemphotos
     */
    public void setINVENTORYITEMPHOTOS(String INVENTORYITEMPHOTOS){
	this.INVENTORYITEMPHOTOS = INVENTORYITEMPHOTOS;
  }


}
