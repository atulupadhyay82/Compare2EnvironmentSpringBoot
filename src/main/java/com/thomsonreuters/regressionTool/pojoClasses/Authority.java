package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class Authority {

    @SerializedName("authorityKey")
    private String mAuthorityKey;
    @SerializedName("authorityName")
    private String mAuthorityName;
    @SerializedName("authorityType")
    private String mAuthorityType;
    @SerializedName("changeType")
    private String mChangeType;

    public String getAuthorityKey() {
        return mAuthorityKey;
    }

    public void setAuthorityKey(String authorityKey) {
        mAuthorityKey = authorityKey;
    }

    public String getAuthorityName() {
        return mAuthorityName;
    }

    public void setAuthorityName(String authorityName) {
        mAuthorityName = authorityName;
    }

    public String getAuthorityType() {
        return mAuthorityType;
    }

    public void setAuthorityType(String authorityType) {
        mAuthorityType = authorityType;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

}
