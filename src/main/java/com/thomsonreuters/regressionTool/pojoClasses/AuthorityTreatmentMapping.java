package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class AuthorityTreatmentMapping {

    @SerializedName("authorityKey")
    private String mAuthorityKey;
    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("effectiveDate")
    private EffectiveDate mEffectiveDate;
    @SerializedName("key")
    private String mKey;
    @SerializedName("productCategoryKey")
    private String mProductCategoryKey;
    @SerializedName("taxType")
    private String mTaxType;
    @SerializedName("treatmentKey")
    private String mTreatmentKey;

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

    public EffectiveDate getEffectiveDate() {
        return mEffectiveDate;
    }

    public void setEffectiveDate(EffectiveDate effectiveDate) {
        mEffectiveDate = effectiveDate;
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

    public String getTreatmentKey() {
        return mTreatmentKey;
    }

    public void setTreatmentKey(String treatmentKey) {
        mTreatmentKey = treatmentKey;
    }

}
