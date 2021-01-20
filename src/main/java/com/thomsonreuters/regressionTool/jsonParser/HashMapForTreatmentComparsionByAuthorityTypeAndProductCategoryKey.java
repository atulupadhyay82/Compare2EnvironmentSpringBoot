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
    MultiValuedMap<String, String> jurisdictionHashMap = new ArrayListValuedHashMap<String, String>();
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
            if(a.getState()!=null && a.getState().equalsIgnoreCase("UNITED STATES"))
                jurisdictionHashMap.put(a.getJurisdictionKey(), a.getState()+"-"+a.getCounty()+"-"+a.getCity()+"-"+ a.getPostalCode() + "-" + a.getGeocode());
            else
                jurisdictionHashMap.put(a.getJurisdictionKey(), a.getCountry()+"-"+a.getmProvince()+"-"+"-"+a.getCity()+"-"+ a.getPostalCode());
        }
    }
    void treatmentGroupHashMapGenerator() {
        for (TreatmentGroupTreatment t : root.getTreatmentGroupTreatments()) {
            treatmentGroupHashMap.put(t.getTreatmentGroupKey(), t.getTreatmentKey());
        }


    }

    void treatmentHashMapGenerator() {

        for (Treatment t : root.getTreatments()) {
            if ((t.getSplitType() == null || t.getSplitType().equalsIgnoreCase("T")) && t.getFee() == null) {
                treatmentHashMapRate.put(t.getTreatmentKey(), t.getRate());
            }else if(t.getFee() != null && t.getSplitType()==null && t.getRate()== null){
                treatmentHashMapRate.put(t.getTreatmentKey(), t.getFee());
              //  System.out.println(t.getFee());
            }
            else if (t.getSplitType().equalsIgnoreCase("R") || t.getSplitType().equalsIgnoreCase("G")) {
                String tierStr = "Tiers:";
                String tempStr="";
                TreeMap<Long, String> tierData= new TreeMap<>();
                for (TierList tierList : t.getTierList()) {
                    tempStr="^^"+tierList.getOrder() + "_Low=" + tierList.getLowValue() + "_High=" + tierList.getHighValue() + "_rate=" + tierList.getRate() ;
                    tierData.put(tierList.getOrder(),tempStr);
                }
                //System.out.println(tierData);
                for(Map.Entry<Long,String> entry : tierData.entrySet()) {
                    tierStr=tierStr+entry.getValue();
                }
                // System.out.println( t.getSplitType() + " " + t.getSplitAmountType() + " " + tierStr);
                treatmentHashMapSplitType.put(t.getTreatmentKey(), t.getSplitType() + " " + t.getSplitAmountType() + " " + tierStr);
            }
        }

    }



    void jurisdictionTreatmentMappingsParser() throws IOException, InvalidFormatException {
        String fileName = root.getExtractName();
        MultiValuedMap<String, String> treatmentComaparator = new ArrayListValuedHashMap<String, String>();
        String keys, value;

        for (int i = 0; i < root.getJurisdictionTreatmentMappings().size(); i++) {
            String productCategoryKey = root.getJurisdictionTreatmentMappings().get(i).getProductCategoryKey();
            if(productCategoryKey.contains("1028059511478213031") ||
                    productCategoryKey.contains("4732796101904615288")||
                    productCategoryKey.contains("367623916056122557") ||
                    productCategoryKey.contains("8630661440905874922") ||
                    productCategoryKey.contains("3377714364674094217"))
                continue;
            String treatmentGroupKey = root.getJurisdictionTreatmentMappings().get(i).getTreatmentGroupKey();
            String authorityType = root.getJurisdictionTreatmentMappings().get(i).getmAuthorityType();
            String jurisdictionKey = root.getJurisdictionTreatmentMappings().get(i).getJurisdictionKey();
            String taxType = root.getJurisdictionTreatmentMappings().get(i).getTaxType();
            List<Long> fromDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getFrom();
            List<Long> toDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getmTo();
            keys = productCategoryKey + ":" +jurisdictionKey + ":" + authorityType+":"+taxType;
            value = "DateRange: " + fromDate + "-To-" + toDate+"]";

            Collection<String> values = treatmentGroupHashMap.get(treatmentGroupKey);
            Iterator<String> iterator = values.iterator();
            Set<Double> flatRate= new TreeSet<>();
            MultiValuedMap<String, String> tierRates =new ArrayListValuedHashMap<>();
            Double rateValue = null;
            while (iterator.hasNext()) {
                try {
                    String treatmentKey = iterator.next();
                    if (treatmentHashMapRate.containsKey(treatmentKey)) {
                        rateValue = treatmentHashMapRate.get(treatmentKey);
                        flatRate.add(rateValue);
                    }

                    if (treatmentHashMapSplitType.containsKey(treatmentKey)) {
                        String[] str = treatmentHashMapSplitType.get(treatmentKey).split(" ");
                        tierRates.put(str[0], str[0] + "-tierRate:" + str[0] + "_" + str[2]);
                    }
                } catch (Exception ex) {
                    System.out.println("Rate is either null or corruped for :" + keys + " -> " + rateValue);
                    value = value + "-rate:" + rateValue;
                }
            }
            if (!flatRate.isEmpty()) {
                for (Double rate : flatRate)
                    value = value + "-rate:" + rate;
            }
            if (!tierRates.isEmpty()) {
                List<String> keylist = new ArrayList<>(tierRates.keySet());
                for (String key : keylist) {
                    value = value + tierRates.get(key);
                }
            }
            for(String address:jurisdictionHashMap.get(jurisdictionKey)){
                keys= productHashMap.get(productCategoryKey) + ":" + address +  ":" + authorityType+":"+ taxType;
                treatmentComaparator.put(keys, value);
            }

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