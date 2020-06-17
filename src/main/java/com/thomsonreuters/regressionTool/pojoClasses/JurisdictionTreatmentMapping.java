package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class JurisdictionTreatmentMapping {

    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("effectiveDate")
    private EffectiveDate mEffectiveDate;
    @SerializedName("jurisdictionKey")
    private String mJurisdictionKey;
    @SerializedName("key")
    private String mKey;
    @SerializedName("productCategoryKey")
    private String mProductCategoryKey;
    @SerializedName("taxType")
    private String mTaxType;
    @SerializedName("treatmentGroupKey")
    private String mTreatmentGroupKey;

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public EffectiveDate getEffectiveDate() {
        return mEffectiveDate;
    }

    public void setEffectiveDate(EffectiveDate effectiveDate) {
        mEffectiveDate = effectiveDate;
    }

    public String getJurisdictionKey() {
        return mJurisdictionKey;
    }

    public void setJurisdictionKey(String jurisdictionKey) {
        mJurisdictionKey = jurisdictionKey;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getProductCategoryKey() {
        return mProductCategoryKey;
    }

    public void setProductCategoryKey(String productCategoryKey) {
        mProductCategoryKey = productCategoryKey;
    }

    public String getTaxType() {
        return mTaxType;
    }

    public void setTaxType(String taxType) {
        mTaxType = taxType;
    }

    public String getTreatmentGroupKey() {
        return mTreatmentGroupKey;
    }

    public void setTreatmentGroupKey(String treatmentGroupKey) {
        mTreatmentGroupKey = treatmentGroupKey;
    }

}
