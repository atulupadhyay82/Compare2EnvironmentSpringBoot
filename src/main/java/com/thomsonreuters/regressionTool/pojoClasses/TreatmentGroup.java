package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class TreatmentGroup {

    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("key")
    private String mKey;

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

}
