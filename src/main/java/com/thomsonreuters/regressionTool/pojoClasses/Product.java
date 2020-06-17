package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class Product {

    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("effectiveDate")
    private EffectiveDate mEffectiveDate;
    @SerializedName("name")
    private String mName;
    @SerializedName("productCategory")
    private String mProductCategory;
    @SerializedName("productCategoryKey")
    private Long mProductCategoryKey;
    @SerializedName("productKey")
    private String mProductKey;

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public EffectiveDate getEffectiveDate() {
        return mEffectiveDate;
    }

    public void setEffectiveDate(EffectiveDate effectiveDate) {
        mEffectiveDate = effectiveDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProductCategory() {
        return mProductCategory;
    }

    public void setProductCategory(String productCategory) {
        mProductCategory = productCategory;
    }

    public Long getProductCategoryKey() {
        return mProductCategoryKey;
    }

    public void setProductCategoryKey(Long productCategoryKey) {
        mProductCategoryKey = productCategoryKey;
    }

    public String getProductKey() {
        return mProductKey;
    }

    public void setProductKey(String productKey) {
        mProductKey = productKey;
    }

}
