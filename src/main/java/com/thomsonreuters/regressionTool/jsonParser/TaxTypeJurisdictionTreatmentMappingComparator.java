package com.thomsonreuters.regressionTool.jsonParser;

import com.thomsonreuters.regressionTool.pojoClasses.JurisdictionTreatmentMapping;

import java.util.Comparator;

public class TaxTypeJurisdictionTreatmentMappingComparator implements Comparator<JurisdictionTreatmentMapping> {

    public int compare(JurisdictionTreatmentMapping j1, JurisdictionTreatmentMapping j2) {
        if (j1.getProductCategoryKey().compareTo(j2.getProductCategoryKey()) == 0) {
            if (j1.getTreatmentGroupKey().compareTo(j2.getTreatmentGroupKey()) == 0) {
                return (j1.getJurisdictionKey().compareTo(j2.getJurisdictionKey()));
            } else
                return j1.getTreatmentGroupKey().compareTo(j2.getTreatmentGroupKey());
        } else
            return j1.getProductCategoryKey().compareTo(j2.getProductCategoryKey());
    }
}
