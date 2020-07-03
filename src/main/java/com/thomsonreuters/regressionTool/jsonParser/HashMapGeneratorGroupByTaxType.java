package com.thomsonreuters.regressionTool.jsonParser;


import com.thomsonreuters.regressionTool.excelOperations.ExcelWriter;
import com.thomsonreuters.regressionTool.pojoClasses.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.*;

public class HashMapGeneratorGroupByTaxType {

    Root root;
    ExcelWriter excelWriter = new ExcelWriter();
    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    HashMap<String, String> jurisdictionHashMap = new HashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    public HashMapGeneratorGroupByTaxType(Root root) {
        this.root = root;
        hashMapGenerator();
    }

    public void hashMapGenerator() {
        try {
            sortJurisdictionTreatmentMapping();
            productHashMapGenerator();
            treatmentHashMapGenerator();
            jurisdictionHashMapGenerator();
            jurisdictionTreatmentMappingsExcelWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sortJurisdictionTreatmentMapping() {
        ArrayList<JurisdictionTreatmentMapping> ll = new ArrayList<JurisdictionTreatmentMapping>();
        for (JurisdictionTreatmentMapping j : root.getJurisdictionTreatmentMappings())
            ll.add(j);
        Collections.sort(ll, new TaxTypeJurisdictionTreatmentMappingComparator());
        root.setJurisdictionTreatmentMappings(ll);
    }

    void productHashMapGenerator() {
        for (Product p : root.getProducts()) {
            //if(!productHashMap.containsKey(p.getProductCategoryKey().toString()))
            productHashMap.put(p.getProductCategoryKey().toString(), p.getProductCategory());
//             else
//             {
//                 String message="ProductCategoryKey occured more than 1 time: "+p.getProductCategory().toString();
//                 System.out.println(message);
//                 System.exit(0);
//             }
        }
    }


    void treatmentHashMapGenerator() {
        for (Treatment t : root.getTreatments()) {
            if (t.getSplitType() == null) {
                treatmentHashMapRate.put(t.getTreatmentKey(), t.getRate());
            } else if (t.getSplitType().equalsIgnoreCase("R") || t.getSplitType().equalsIgnoreCase("G")) {
                String str = null;
                for (TierList tierList : t.getTierList()) {
                    str = str + tierList.getOrder() + "-Low=" + tierList.getLowValue() + "-High=" + tierList.getHighValue() + "-rate=" + tierList.getRate() + "^^";
                }
                treatmentHashMapSplitType.put(t.getTreatmentKey(), t.getSplitType() + " " + t.getSplitAmountType() + " " + str);
            }
        }
    }

    void jurisdictionHashMapGenerator() {
        for (Address a : root.getAddresses())
            jurisdictionHashMap.put(a.getJurisdictionKey(), a.getPostalCode() + "-" + a.getState() + "-" + a.getCity() + "-" + a.getCountry());
    }

    void jurisdictionTreatmentMappingsExcelWriter() throws IOException, InvalidFormatException {
        String fileName = root.getExtractName();
        String sheetName = root.getGroupingRule();
        List<String> columnNames = new LinkedList<String>(Arrays.asList("productCategoryKey", "ProductCategory", "treatmentGroupKey"
                , "Rate", "jurisdictionKey", "jurisdiction", "splitType", "splitAmountType", "splitTieredRate"));
        //excelWriter.rowSize=root.getJurisdictionTreatmentMappings().size();

        excelWriter.createExcelFileAndSheet(fileName, sheetName, columnNames);
        for (int i = 0; i < root.getJurisdictionTreatmentMappings().size(); i++) {
            String productCategoryKey = root.getJurisdictionTreatmentMappings().get(i).getProductCategoryKey();
            String treatmentGroupKey = root.getJurisdictionTreatmentMappings().get(i).getTreatmentGroupKey();
            String jurisdictionKey = root.getJurisdictionTreatmentMappings().get(i).getJurisdictionKey();
            List<Long> fromDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getFrom();
            List<Long> toDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getmTo();
            int rowNo = i + 1;
            excelWriter.createRow(rowNo);
            excelWriter.writeInExcelSheet(0, productCategoryKey);
            excelWriter.writeInExcelSheet(1, productHashMap.get(productCategoryKey));

            excelWriter.writeInExcelSheet(2, treatmentGroupKey);
            if (treatmentHashMapRate.containsKey(treatmentGroupKey)) {
                excelWriter.writeInExcelSheet(3, treatmentHashMapRate.get(treatmentGroupKey));
            }
            excelWriter.writeInExcelSheet(4, jurisdictionKey);
            excelWriter.writeInExcelSheet(5, jurisdictionHashMap.get(jurisdictionKey));

            if (treatmentHashMapSplitType.containsKey(treatmentGroupKey)) {
                String[] str = treatmentHashMapSplitType.get(treatmentGroupKey).split(" ");
                excelWriter.writeInExcelSheet(6, str[0]);
                excelWriter.writeInExcelSheet(7, str[1]);
                excelWriter.writeInExcelSheet(8, str[2]);
            }


//             System.out.println(i);
//             System.out.print(productCategoryKey+" ");
//             System.out.println(productHashMap.get(productCategoryKey));
//             System.out.print(treatmentGroupKey+" ");
//             System.out.println(treatmentHashMap.get(treatmentGroupKey));
//             System.out.print(jurisdictionKey+" ");
//             System.out.println(jurisdictionHashMap.get(jurisdictionKey));
//             System.out.println("========================");

        }
        excelWriter.completeExcelFileAndSheet();
    }
}
