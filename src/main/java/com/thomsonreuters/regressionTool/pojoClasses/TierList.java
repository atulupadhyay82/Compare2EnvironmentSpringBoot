package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("a")
@SuppressWarnings("unused")
public class TierList {

    @SerializedName("highValue")
    private Double mHighValue;
    @SerializedName("lowValue")
    private Double mLowValue;
    @SerializedName("order")
    private Long mOrder;
    @SerializedName("rate")
    private Double mRate;
    @SerializedName("treatmentKey")
    private Long mTreatmentKey;

    public Double getHighValue() {
        return mHighValue;
    }

    public void setHighValue(Double highValue) {
        mHighValue = highValue;
    }

    public Double getLowValue() {
        return mLowValue;
    }

    public void setLowValue(Double lowValue) {
        mLowValue = lowValue;
    }

    public Long getOrder() {
        return mOrder;
    }

    public void setOrder(Long order) {
        mOrder = order;
    }

    public Double getRate() {
        return mRate;
    }

    public void setRate(Double rate) {
        mRate = rate;
    }

    public Long getTreatmentKey() {
        return mTreatmentKey;
    }

    public void setTreatmentKey(Long treatmentKey) {
        mTreatmentKey = treatmentKey;
    }

}
