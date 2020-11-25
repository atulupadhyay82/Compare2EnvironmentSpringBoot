package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.math.BigInteger;

@Generated("a")
@SuppressWarnings("unused")
public class Address implements Comparable<Address>{

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

    @SerializedName("geocode")
    private String mGeocode;

    @SerializedName("province")
    private String mProvince;

    @SerializedName("postalRange")
    private PostalRange postalRange;

    public PostalRange getPostalRange() {
        return postalRange;
    }

    public void setPostalRange(PostalRange postalRange) {
        this.postalRange = postalRange;
    }



    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getCounty() {
        return mCounty;
    }

    public void setCounty(String mCounty) {
        this.mCounty = mCounty;
    }


    public String getGeocode() {
        return mGeocode;
    }

    public void setmGeocode(String mGeocode) {
        this.mGeocode = mGeocode;
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

    @Override
    public int compareTo(Address o) {
        return  new BigInteger(this.getAddressKey()).compareTo(new BigInteger(o.getAddressKey()));
    }
}
