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

public class HashMapForTreatmentComparsionByAuthorityAndProductCategoryKey {
    Root root;
    ExcelWriter excelWriter = new ExcelWriter();
    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    HashMap<String, String> authorityHashMap = new HashMap<String, String>();
    HashMap<String, String> jurisdictionAuthoritiesHashMap = new HashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    HashMap<String, String> jurisdictionHashMap = new HashMap<String, String>();


    public HashMapForTreatmentComparsionByAuthorityAndProductCategoryKey(Root root) {
        this.root = root;
        hashMapGenerator();
    }

    public void hashMapGenerator() {
        try {
            productHashMapGenerator();
            jurisdictionHashMapGenerator();
            authorityHashMapGenerator();
            jurisdictionAuthoritiesHashMapGenerator();
            treatmentHashMapGenerator();
            authorityTreatmentMappingsExcelWriter();
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
        List<Address> addr=root.getAddresses();
        Collections.sort(addr);
        for (Address a : addr){
            if(!jurisdictionHashMap.containsKey(a.getJurisdictionKey()))
                jurisdictionHashMap.put(a.getJurisdictionKey(), a.getState()+"-"+a.getCounty()+"-"+a.getCity()+"-"+ a.getPostalCode() + "-" + a.getGeocode());
        }

    }

    void treatmentHashMapGenerator() {

        for (Treatment t : root.getTreatments()) {
            if (t.getSplitType() == null || t.getSplitType().equalsIgnoreCase("T"))  {
                treatmentHashMapRate.put(t.getTreatmentKey(), t.getRate());
            } else if (t.getSplitType().equalsIgnoreCase("R") || t.getSplitType().equalsIgnoreCase("G")) {
                String tierStr = "Tiers:";
                String tempStr="";
                TreeMap<Long, String> tierData= new TreeMap<>();
                for (TierList tierList : t.getTierList()) {
                    tempStr="^^"+tierList.getOrder() + "_Low=" + tierList.getLowValue() + "_High=" + tierList.getHighValue() + "_rate=" + tierList.getRate() ;
                    tierData.put(tierList.getOrder(),tempStr);
                }

                for(Map.Entry<Long,String> entry : tierData.entrySet()) {
                    tierStr=tierStr+entry.getValue();
                }
                treatmentHashMapSplitType.put(t.getTreatmentKey(), t.getSplitType() + " " + t.getSplitAmountType() + " " + tierStr);
            }
        }

    }

    void authorityTreatmentMappingsExcelWriter() throws IOException, InvalidFormatException {
        String fileName = root.getExtractName();
        MultiValuedMap<String, String> treatmentComaparator = new ArrayListValuedHashMap<String, String>();
        String keys, value;

        for (int i = 0; i < root.getmAuthorityTreatmentMappings().size(); i++) {
            String productCategoryKey = root.getmAuthorityTreatmentMappings().get(i).getProductCategoryKey();
            String treatmentKey = root.getmAuthorityTreatmentMappings().get(i).getTreatmentKey();
            String authorityKey = root.getmAuthorityTreatmentMappings().get(i).getAuthorityKey();
            String jurisdictionKey = jurisdictionAuthoritiesHashMap.get(authorityKey);
            String taxType = root.getmAuthorityTreatmentMappings().get(i).getTaxType();
            List<Long> fromDate = root.getmAuthorityTreatmentMappings().get(i).getEffectiveDate().getFrom();
            List<Long> toDate = root.getmAuthorityTreatmentMappings().get(i).getEffectiveDate().getmTo();

            keys = productCategoryKey + ":" + authorityKey + ":" + taxType;
            value = "DateRange: " + fromDate + "-To-" + toDate+"]";
            if(productCategoryKey.contains("1028059511478213031") && productCategoryKey.contains("3377714364674094217")){
                continue;
            }

            Set<Double> flatRate= new TreeSet<>();
            MultiValuedMap<String, String> tierRates =new ArrayListValuedHashMap<>();
            Double rateValue=null;
            try{
                if (treatmentHashMapRate.containsKey(treatmentKey)) {
                    flatRate.add(treatmentHashMapRate.get(treatmentKey));
                }
                if (treatmentHashMapSplitType.containsKey(treatmentKey)) {
                    String[] str = treatmentHashMapSplitType.get(treatmentKey).split(" ");
                    tierRates.put(str[0], str[0]+"-tierRate:" + str[0] +  "_" + str[2]);
                }

                for(Double rate: flatRate)
                    value = value + "-rate:" + rate;
                List<String> keylist = new ArrayList<>(tierRates.keySet());
                for(String key : keylist) {
                    value = value+tierRates.get(key);
                }
            }catch(Exception ex){
                System.out.println("Rate is either null or corruped for :"+ keys+ " -> "+rateValue);
                value = value + "-rate:" + rateValue;
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


