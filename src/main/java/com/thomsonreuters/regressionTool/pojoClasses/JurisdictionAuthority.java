package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class JurisdictionAuthority {

    @SerializedName("authorityKey")
    private String mAuthorityKey;
    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("jurisdictionKey")
    private String mJurisdictionKey;

    public String getAuthorityKey() {
        return mAuthorityKey;
    }

    public void setAuthorityKey(String authorityKey) {
        mAuthorityKey = authorityKey;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getJurisdictionKey() {
        return mJurisdictionKey;
    }

    public void setJurisdictionKey(String jurisdictionKey) {
        mJurisdictionKey = jurisdictionKey;
    }

}
