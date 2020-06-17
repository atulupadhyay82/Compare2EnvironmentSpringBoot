package com.thomsonreuters.regressionTool.jsonParser;

import com.thomsonreuters.regressionTool.excelOperations.ExcelWriter;
import com.thomsonreuters.regressionTool.pojoClasses.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HashMapGeneratorGroupByAuthority {
    Root root;
    ExcelWriter excelWriter = new ExcelWriter();
    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    HashMap<String, String> authorityHashMap = new HashMap<String, String>();
    HashMap<String, String> jurisdictionAuthoritiesHashMap = new HashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    HashMap<String, String> jurisdictionHashMap = new HashMap<String, String>();
    public HashMapGeneratorGroupByAuthority(Root root) {
        this.root = root;
        hashMapGenerator();
    }

    public void hashMapGenerator() {
        try {
            productHashMapGenerator();
            treatmentHashMapGenerator();
            authorityHashMapGenerator();
            jurisdictionHashMapGenerator();
            jurisdictionAuthoritiesHashMapGenerator();
            authorityTreatmentMappingsExcelWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void productHashMapGenerator() {
        for (Product p : root.getProducts()) {
            if (!productHashMap.containsKey(p.getProductCategoryKey().toString()))
                productHashMap.put(p.getProductCategoryKey().toString(), p.getProductCategory());
            else {
                String message = "ProductCategoryKey occured more than 1 time: " + p.getProductCategory().toString();
                System.out.println(message);
                System.exit(0);
            }
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

    void authorityHashMapGenerator() {
        for (Authority a : root.getmAuthorities()) {
            authorityHashMap.put(a.getAuthorityKey(), a.getAuthorityName() + "-" + a.getAuthorityType());
        }

    }

    void jurisdictionAuthoritiesHashMapGenerator() {
        for (JurisdictionAuthority ja : root.getmJurisdictionAuthorities()) {
            jurisdictionAuthoritiesHashMap.put(ja.getAuthorityKey(), ja.getJurisdictionKey());
        }
    }

    void jurisdictionHashMapGenerator() {
        for (Address a : root.getAddresses())
            jurisdictionHashMap.put(a.getJurisdictionKey(), a.getPostalCode() + "-" + a.getState() + "-" + a.getCity() + "-" + a.getCounty());
    }

    void authorityTreatmentMappingsExcelWriter() throws IOException, InvalidFormatException {
        String fileName = root.getExtractName();
        String sheetName = root.getGroupingRule();
        List<String> columnNames = new LinkedList<String>(Arrays.asList("productCategoryKey", "ProductCategory", "treatmentKey"
                , "Rate", "authorityKey", "authority", "splitType", "splitAmountType", "splitTieredRate", "jurisdictionKey", "jurisdiction"));
        //excelWriter.rowSize=root.getJurisdictionTreatmentMappings().size();

        excelWriter.createExcelFileAndSheet(fileName, sheetName, columnNames);
        for (int i = 0; i < root.getmAuthorityTreatmentMappings().size(); i++) {
            String productCategoryKey = root.getmAuthorityTreatmentMappings().get(i).getProductCategoryKey();
            String treatmentKey = root.getmAuthorityTreatmentMappings().get(i).getTreatmentKey();
            String authorityKey = root.getmAuthorityTreatmentMappings().get(i).getAuthorityKey();
            String jurisdictionKey = jurisdictionAuthoritiesHashMap.get(authorityKey);
            int rowNo = i + 1;
            excelWriter.createRow(rowNo);
            excelWriter.writeInExcelSheet(0, productCategoryKey);
            excelWriter.writeInExcelSheet(1, productHashMap.get(productCategoryKey));

            excelWriter.writeInExcelSheet(2, treatmentKey);
            if (treatmentHashMapRate.containsKey(treatmentKey)) {
                excelWriter.writeInExcelSheet(3, treatmentHashMapRate.get(treatmentKey));
            }
            excelWriter.writeInExcelSheet(4, authorityKey);
            excelWriter.writeInExcelSheet(5, authorityHashMap.get(authorityKey));

            if (treatmentHashMapSplitType.containsKey(treatmentKey)) {
                String[] str = treatmentHashMapSplitType.get(treatmentKey).split(" ");
                excelWriter.writeInExcelSheet(6, str[0]);
                excelWriter.writeInExcelSheet(7, str[1]);
                excelWriter.writeInExcelSheet(8, str[2]);
                //excelWriter.writeInExcelSheet(9, treatmentHashMapSplitType.get(treatmentKey));
            }

            excelWriter.writeInExcelSheet(9, jurisdictionKey);
            excelWriter.writeInExcelSheet(10, jurisdictionHashMap.get(jurisdictionKey));

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

