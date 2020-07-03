package com.thomsonreuters.regressionTool.jsonParser;


import com.thomsonreuters.regressionTool.pojoClasses.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HashMapForTreatmentComparsionByTaxType {


    Root root;
    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    HashMap<String, String> jurisdictionHashMap = new HashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    MultiValuedMap<String, String> treatmentGroupHashMap = new ArrayListValuedHashMap<String, String>();
    public HashMapForTreatmentComparsionByTaxType(Root root) {
        this.root = root;
        hashMapGenerator();
    }

    public void hashMapGenerator() {
        try {
            productHashMapGenerator();
            treatmentGroupHashMapGenerator();
            treatmentHashMapGenerator();
            jurisdictionHashMapGenerator();
            jurisdictionTreatmentMappingsExcelWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void treatmentGroupHashMapGenerator() {
        for (TreatmentGroupTreatment t : root.getTreatmentGroupTreatments()) {
            treatmentGroupHashMap.put(t.getTreatmentGroupKey(), t.getTreatmentKey());
        }


    }
    void treatmentHashMapGenerator() {
        for (Treatment t : root.getTreatments()) {
            if (t.getSplitType() == null) {
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


    void jurisdictionHashMapGenerator() {
        for (Address a : root.getAddresses())
            jurisdictionHashMap.put(a.getJurisdictionKey(), a.getPostalCode() + "-" + a.getState() + "-" + a.getCity() + "-" + a.getCountry());
    }

    void jurisdictionTreatmentMappingsExcelWriter() throws IOException, InvalidFormatException {
        String fileName = root.getExtractName();

        MultiValuedMap<String, String> treatmentComaparator = new ArrayListValuedHashMap<String, String>();

        // MultiValuedMap<K, String> map = new MultiValuedHashMap<K, String>();

        String keys, value;

        //System.out.println(treatmentHashMapSplitType.values());
        //System.out.println(treatmentHashMapRate.values());
        for (int i = 0; i < root.getJurisdictionTreatmentMappings().size(); i++) {
            String productCategoryKey = root.getJurisdictionTreatmentMappings().get(i).getProductCategoryKey();
            String treatmentGroupKey = root.getJurisdictionTreatmentMappings().get(i).getTreatmentGroupKey();
            String jurisdictionKey = root.getJurisdictionTreatmentMappings().get(i).getJurisdictionKey();
            String taxType = root.getJurisdictionTreatmentMappings().get(i).getTaxType();
            //String key=root.getJurisdictionTreatmentMappings().get(i).getKey();
            List<Long> fromData = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getFrom();
            List<Long> toData = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getmTo();
            keys = productCategoryKey + ":" + jurisdictionKey + ":" + taxType;
            value = "From-" + fromData + "-To-" + toData;

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


