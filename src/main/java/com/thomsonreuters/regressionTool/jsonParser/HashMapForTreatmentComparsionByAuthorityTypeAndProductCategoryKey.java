package com.thomsonreuters.regressionTool.jsonParser;


import com.thomsonreuters.regressionTool.excelOperations.ExcelWriter;
import com.thomsonreuters.regressionTool.pojoClasses.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HashMapForTreatmentComparsionByAuthorityTypeAndProductCategoryKey {
    Root root;
    ExcelWriter excelWriter = new ExcelWriter();
    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    HashMap<String, String> jurisdictionAuthoritiesHashMap = new HashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    HashMap<String, String> jurisdictionHashMap = new HashMap<String, String>();
    MultiValuedMap<String, String> treatmentGroupHashMap = new ArrayListValuedHashMap<String, String>();


    public HashMapForTreatmentComparsionByAuthorityTypeAndProductCategoryKey(Root root) {
        this.root = root;
        hashMapGenerator();
    }

    public void hashMapGenerator() {
        try {
            productHashMapGenerator();
            treatmentGroupHashMapGenerator();
            treatmentHashMapGenerator();
            jurisdictionHashMapGenerator();
            jurisdictionTreatmentMappingsParser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void productHashMapGenerator() {
        for (Product p : root.getProducts()) {
            if (!productHashMap.containsKey(p.getProductCategoryKey().toString()))
                productHashMap.put(p.getProductCategoryKey().toString(), p.getProductCategory());
        }
    }



    void jurisdictionHashMapGenerator() {
        List<Address> addr=root.getAddresses();
        Collections.sort(addr);
        for (Address a : addr){
            if(!jurisdictionHashMap.containsKey(a.getJurisdictionKey()))
                jurisdictionHashMap.put(a.getJurisdictionKey(), a.getState()+"-"+a.getCounty()+"-"+a.getCity()+"-"+ a.getPostalCode() + "-" + a.getGeocode());
        }

    }
    void treatmentGroupHashMapGenerator() {
        for (TreatmentGroupTreatment t : root.getTreatmentGroupTreatments()) {
            treatmentGroupHashMap.put(t.getTreatmentGroupKey(), t.getTreatmentKey());
        }


    }
    void treatmentHashMapGenerator() {
        for (Treatment t : root.getTreatments()) {
            if (t.getSplitType() == null || t.getSplitType().equalsIgnoreCase("T"))  {
                treatmentHashMapRate.put(t.getTreatmentKey(), t.getRate());
            } else if (t.getSplitType().equalsIgnoreCase("R") || t.getSplitType().equalsIgnoreCase("G")) {
                String str = "Tiers:";
                for (TierList tierList : t.getTierList()) {
                    str = str + "^^"+tierList.getOrder() + "_Low=" + tierList.getLowValue() + "_High=" + tierList.getHighValue() + "_rate=" + tierList.getRate() ;
                }
                treatmentHashMapSplitType.put(t.getTreatmentKey(), t.getSplitType() + " " + t.getSplitAmountType() + " " + str);
            }
        }

    }


    void jurisdictionTreatmentMappingsParser() throws IOException, InvalidFormatException {
        String fileName = root.getExtractName();
        MultiValuedMap<String, String> treatmentComaparator = new ArrayListValuedHashMap<String, String>();
        String keys, value;

        for (int i = 0; i < root.getJurisdictionTreatmentMappings().size(); i++) {
            String productCategoryKey = root.getJurisdictionTreatmentMappings().get(i).getProductCategoryKey();
            String treatmentGroupKey = root.getJurisdictionTreatmentMappings().get(i).getTreatmentGroupKey();
            String authorityType = root.getJurisdictionTreatmentMappings().get(i).getmAuthorityType();
            String jurisdictionKey = root.getJurisdictionTreatmentMappings().get(i).getJurisdictionKey();
            String taxType = root.getJurisdictionTreatmentMappings().get(i).getTaxType();
            List<Long> fromDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getFrom();
            List<Long> toDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getmTo();
            keys = productCategoryKey + ":" +jurisdictionKey + ":" + authorityType+":"+taxType;
            value = "DateRange: " + fromDate + "-To-" + toDate+"]";
            if(productCategoryKey.contains("1028059511478213031") && productCategoryKey.contains("3377714364674094217")){
                continue;
            }

            Collection<String> values = treatmentGroupHashMap.get(treatmentGroupKey);
            Iterator<String> iterator = values.iterator();
            while (iterator.hasNext()) {
                String treatmentKey = iterator.next();
                if (treatmentHashMapRate.containsKey(treatmentKey)) {
                    value = value + "-rate:" + treatmentHashMapRate.get(treatmentKey);
                }

                if (treatmentHashMapSplitType.containsKey(treatmentKey)) {
                    String[] str = treatmentHashMapSplitType.get(treatmentKey).split(" ");

                    value = value + "-tierRate:" + str[0] +  "_" + str[2];
                    //excelWriter.writeInExcelSheet(9, treatmentHashMapSplitType.get(treatmentKey));
                }


            }
            treatmentComaparator.put(keys, value);
        }

        Collection<Map.Entry<String, String>> entries = treatmentComaparator.entries();
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