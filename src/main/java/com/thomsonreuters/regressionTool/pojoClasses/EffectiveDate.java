package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("a")
@SuppressWarnings("unused")
public class EffectiveDate {

    @SerializedName("from")
    private List<Long> mFrom;

    @SerializedName("to")
    private List<Long> mTo;

    public List<Long> getmTo() {
        return mTo;
    }

    public void setmTo(List<Long> mTo) {
        this.mTo = mTo;
    }

    public List<Long> getFrom() {
        return mFrom;
    }

    public void setFrom(List<Long> from) {
        mFrom = from;
    }

}
