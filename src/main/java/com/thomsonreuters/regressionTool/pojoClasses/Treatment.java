package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("a")
@SuppressWarnings("unused")
public class Treatment {

    @SerializedName("calculationMethod")
    private String mCalculationMethod;
    @SerializedName("changeType")
    private String mChangeType;
    @SerializedName("splitAmountType")
    private String mSplitAmountType;
    @SerializedName("splitType")
    private String mSplitType;
    @SerializedName("tierList")
    private List<TierList> mTierList;
    @SerializedName("treatmentKey")
    private String mTreatmentKey;
    @SerializedName("rate")
    private Double mRate;
    @SerializedName("fee")
    private Double mFee;

    public Double getFee() {
        return mFee;
    }

    public void setFee(Double Fee) {
        this.mFee = Fee;
    }



    public String getCalculationMethod() {
        return mCalculationMethod;
    }

    public void setCalculationMethod(String calculationMethod) {
        mCalculationMethod = calculationMethod;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getSplitAmountType() {
        return mSplitAmountType;
    }

    public void setSplitAmountType(String splitAmountType) {
        mSplitAmountType = splitAmountType;
    }

    public String getSplitType() {
        return mSplitType;
    }

    public void setSplitType(String splitType) {
        mSplitType = splitType;
    }

    public List<TierList> getTierList() {
        return mTierList;
    }

    public void setTierList(List<TierList> tierList) {
        mTierList = tierList;
    }

    public String getTreatmentKey() {
        return mTreatmentKey;
    }

    public void setTreatmentKey(String treatmentKey) {
        mTreatmentKey = treatmentKey;
    }

    public Double getRate() {
        return mRate;
    }

    public void setRate(Double rate) {
        mRate = rate;
    }


}
