package com.thomsonreuters.regressionTool.jsonParser;


import com.thomsonreuters.regressionTool.excelOperations.ExcelWriter;
import com.thomsonreuters.regressionTool.pojoClasses.Root;
import com.thomsonreuters.regressionTool.pojoClasses.TierList;
import com.thomsonreuters.regressionTool.pojoClasses.Treatment;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HashMapForTreatmentComparsionByAuthority {
    Root root;
    ExcelWriter excelWriter = new ExcelWriter();
    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    HashMap<String, String> authorityHashMap = new HashMap<String, String>();
    HashMap<String, String> jurisdictionAuthoritiesHashMap = new HashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    HashMap<String, String> jurisdictionHashMap = new HashMap<String, String>();
    //excelWriter.rowSize=root.getJurisdictionTreatmentMappings().size();
    MultiValuedMap<String, String> treatmentComaparator = new ArrayListValuedHashMap<String, String>();
    String keys, value;

    // MultiValuedMap<K, String> map = new MultiValuedHashMap<K, String>();

    public HashMapForTreatmentComparsionByAuthority(Root root) {
        this.root = root;
        hashMapGenerator();
    }

    public void hashMapGenerator() {
        try {
            treatmentHashMapGenerator();
            authorityTreatmentMappingsExcelWriter();
        } catch (Exception e) {
            e.printStackTrace();
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

    void authorityTreatmentMappingsExcelWriter() throws IOException, InvalidFormatException {
        String fileName = root.getExtractName();
        String sheetName = root.getGroupingRule();
        List<String> columnNames = new LinkedList<String>(Arrays.asList("productCategoryKey", "ProductCategory", "treatmentKey"
                , "Rate", "authorityKey", "authority", "splitType", "splitAmountType", "splitTieredRate", "jurisdictionKey", "jurisdiction"));
        //excelWriter.rowSize=root.getJurisdictionTreatmentMappings().size();

        // excelWriter.createExcelFileAndSheet(fileName,sheetName,columnNames);
        for (int i = 0; i < root.getmAuthorityTreatmentMappings().size(); i++) {
            String productCategoryKey = root.getmAuthorityTreatmentMappings().get(i).getProductCategoryKey();
            String treatmentKey = root.getmAuthorityTreatmentMappings().get(i).getTreatmentKey();
            String authorityKey = root.getmAuthorityTreatmentMappings().get(i).getAuthorityKey();
            String jurisdictionKey = jurisdictionAuthoritiesHashMap.get(authorityKey);
            int rowNo = i + 1;
            String taxType = root.getmAuthorityTreatmentMappings().get(i).getTaxType();
            //String key=root.getJurisdictionTreatmentMappings().get(i).getKey();

            List<Long> fromData = root.getmAuthorityTreatmentMappings().get(i).getEffectiveDate().getFrom();
            List<Long> toData = root.getmAuthorityTreatmentMappings().get(i).getEffectiveDate().getmTo();
            keys = productCategoryKey + ":" + authorityKey + ":" + taxType;
            value = "" + fromData + toData;

            if (treatmentHashMapRate.containsKey(treatmentKey)) {
                value = value + treatmentHashMapRate.get(treatmentKey);
            }


            if (treatmentHashMapSplitType.containsKey(treatmentKey)) {
                String[] str = treatmentHashMapSplitType.get(treatmentKey).split(" ");

                value = value + "_" + str[0] + "_" + str[1] + "_" + str[2];
                //excelWriter.writeInExcelSheet(9, treatmentHashMapSplitType.get(treatmentKey));
            }
            treatmentComaparator.put(keys, value);


        }

        Collection<Map.Entry<String, String>> entries = treatmentComaparator.entries();
//        for (Map.Entry<String,String> entry: treatmentComaparator.entries()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
        List<String> keylist = new ArrayList<String>(treatmentComaparator.keySet());


        Collections.sort(keylist);

        try {
            File myObj = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\jsonFiles\\" + fileName + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // System.out.println(key +  " : " + treatmentComaparator.get(key));
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\resources\\jsonFiles\\" + fileName + ".txt");
            for (String key : keylist) {
                Collection<String> values = treatmentComaparator.get(key);
                // System.out.println(key +  " : " + treatmentComaparator.get(key));
                List<String> valueList = new ArrayList<String>(treatmentComaparator.get(key));
                Collections.sort(valueList);
                myWriter.write(key + " : " + valueList);
                myWriter.write(System.getProperty("line.separator"));

            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


}


