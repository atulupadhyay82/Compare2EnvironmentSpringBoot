package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class TreatmentGroupTreatment {

    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("key")
    private String mKey;
    @SerializedName("treatmentGroupKey")
    private String mTreatmentGroupKey;
    @SerializedName("treatmentKey")
    private String mTreatmentKey;

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

    public String getTreatmentGroupKey() {
        return mTreatmentGroupKey;
    }

    public void setTreatmentGroupKey(String treatmentGroupKey) {
        mTreatmentGroupKey = treatmentGroupKey;
    }

    public String getTreatmentKey() {
        return mTreatmentKey;
    }

    public void setTreatmentKey(String treatmentKey) {
        mTreatmentKey = treatmentKey;
    }

}
