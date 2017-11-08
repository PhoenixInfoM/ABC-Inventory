package com.backofficecloudapps.prop.inventory.Model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Phoenix
 */
public class Adresses implements Serializable {
    private String houseNo;
    private String address;
    private String postCode;

    /**
     * Instantiates a new Adresses.
     *
     * @param houseNo  the house no
     * @param address  the address
     * @param postCode the post code
     */
    public Adresses(String houseNo, String address, String postCode) {
        this.houseNo = houseNo;
        this.address = address;
        this.postCode = postCode;
    }

    /**
     * Gets house no.
     *
     * @return the house no
     */
    public String getHouseNo() {
        houseNo = houseNo.replace("/", "-");
        if (!TextUtils.isEmpty(houseNo)) {
            houseNo = "-" + houseNo + "";
        } else {
            houseNo = "";
        }
        return houseNo;
    }

    /**
     * Sets house no.
     *
     * @param houseNo the house no
     */
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets post code.
     *
     * @return the post code
     */
    public String getPostCode() {
        if (!TextUtils.isEmpty(postCode)) {
            postCode = "-" + postCode + "";
        } else {
            postCode = "";
        }
        return postCode;
    }

    /**
     * Sets post code.
     *
     * @param postCode the post code
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
