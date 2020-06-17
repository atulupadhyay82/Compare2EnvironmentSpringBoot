package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class Address {

    @SerializedName("addressKey")
    private String mAddressKey;
    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("city")
    private String mCity;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("jurisdictionKey")
    private String mJurisdictionKey;
    @SerializedName("postalCode")
    private String mPostalCode;
    @SerializedName("state")
    private String mState;
    @SerializedName("county")
    private String mCounty;

    public String getCounty() {
        return mCounty;
    }

    public void setCounty(String mCounty) {
        this.mCounty = mCounty;
    }


    public String getAddressKey() {
        return mAddressKey;
    }

    public void setAddressKey(String addressKey) {
        mAddressKey = addressKey;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getJurisdictionKey() {
        return mJurisdictionKey;
    }

    public void setJurisdictionKey(String jurisdictionKey) {
        mJurisdictionKey = jurisdictionKey;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }
}
