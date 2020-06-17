package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class Location {

    @SerializedName("addressKey")
    private Long mAddressKey;
    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("locationKey")
    private String mLocationKey;
    @SerializedName("name")
    private String mName;

    public Long getAddressKey() {
        return mAddressKey;
    }

    public void setAddressKey(Long addressKey) {
        mAddressKey = addressKey;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getLocationKey() {
        return mLocationKey;
    }

    public void setLocationKey(String locationKey) {
        mLocationKey = locationKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
