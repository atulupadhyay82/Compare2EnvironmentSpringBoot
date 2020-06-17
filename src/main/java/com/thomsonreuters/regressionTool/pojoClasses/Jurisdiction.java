package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class Jurisdiction {

    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("jurisdictionKey")
    private String mJurisdictionKey;

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
