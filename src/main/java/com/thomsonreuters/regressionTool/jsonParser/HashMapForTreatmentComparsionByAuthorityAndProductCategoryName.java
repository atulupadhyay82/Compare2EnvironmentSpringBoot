package com.thomsonreuters.regressionTool.jsonParser;



import com.thomsonreuters.regressionTool.pojoClasses.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HashMapForTreatmentComparsionByAuthorityAndProductCategoryName {
    Root root;

    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    HashMap<String, String> authorityHashMap = new HashMap<String, String>();
    HashMap<String, String> jurisdictionAuthoritiesHashMap = new HashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    HashMap<String, String> jurisdictionHashMap = new HashMap<String, String>();
    HashMap<String, String> storeMapperMap = new HashMap<String,String>();
    HashMap<String, String> addressMapperMap = new HashMap<String, String>();
    boolean isStoreFlag;


    public HashMapForTreatmentComparsionByAuthorityAndProductCategoryName(Root root) {
        this.root = root;
        if(root.getmLocations()!=null)
            isStoreFlag=true;
        else
            isStoreFlag=false;
    }


    public File hashMapGenerator(String env) {
        File outputFile=null;
        try {
            productHashMapGenerator();
            jurisdictionHashMapGenerator();
            authorityHashMapGenerator();
            jurisdictionAuthoritiesHashMapGenerator();
            treatmentHashMapGenerator();
            if(isStoreFlag){
                addressHashMapGenerator();
                storeHashMapGenerator();
            }
            outputFile=authorityTreatmentMappingsWriter(env);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
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
        String value="";
        for (Address a : addr){
            if(a.getPostalRange()==null)
                jurisdictionHashMap.put(a.getJurisdictionKey(), a.getState()+"-"+a.getCounty()+"-"+a.getCity()+"-"+ a.getPostalCode() + "-" + a.getGeocode());
            else
                jurisdictionHashMap.put(a.getJurisdictionKey(), a.getState()+"-"+a.getCounty()+"-"+a.getCity()+"-"+ a.getPostalRange().getBegin() + "-" + a.getPostalRange().getEnd());
        }
    }

    void addressHashMapGenerator() {
        List<Address> addr=root.getAddresses();
        Collections.sort(addr);
        String value="";
        for (Address a : addr){
            if(a.getPostalRange()==null)
                addressMapperMap.put(a.getAddressKey(), a.getState()+"-"+a.getCounty()+"-"+a.getCity()+"-"+ a.getPostalCode() + "-" + a.getGeocode());
            else
                addressMapperMap.put(a.getAddressKey(), a.getState()+"-"+a.getCounty()+"-"+a.getCity()+"-"+ a.getPostalRange().getBegin() + "-" + a.getPostalRange().getEnd());
        }
    }

    void storeHashMapGenerator() {
        for (Location loc : root.getmLocations()) {
            storeMapperMap.put(loc.getName(), addressMapperMap.get(loc.getAddressKey().toString()));
        }

    }
    void treatmentHashMapGenerator() {

        for (Treatment t : root.getTreatments()) {
            if ((t.getSplitType() == null || t.getSplitType().equalsIgnoreCase("T")) && t.getFee() == null) {
                treatmentHashMapRate.put(t.getTreatmentKey(), t.getRate());
            }else if(t.getFee() != null && t.getSplitType()==null && t.getRate()== null){
                treatmentHashMapRate.put(t.getTreatmentKey(), t.getFee());
               // System.out.println(t.getFee());
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

    File authorityTreatmentMappingsWriter(String env) throws IOException {
        String extractName= root.getExtractName();
        String fileName = extractName+"_" +env;
        File resultFile=null;
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

            keys = productHashMap.get(productCategoryKey) + ":" + authorityHashMap.get(authorityKey) + ":" + taxType;
            value = "DateRange: " + fromDate + "-To-" + toDate+"]";

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

        List<String> treatmentKeylist = new ArrayList<String>(treatmentComaparator.keySet());


        Collections.sort(treatmentKeylist);

        try {
            resultFile = new File(System.getProperty("user.dir") + "/src/main/resources/jsonFiles/" + fileName + ".txt");
            resultFile.createNewFile();
            FileWriter myWriter = new FileWriter(resultFile);
            for (String key : treatmentKeylist) {
                List<String> valueList = new ArrayList<String>(treatmentComaparator.get(key));
                Collections.sort(valueList);
                myWriter.write(key + " : " + valueList);
                myWriter.write(System.getProperty("line.separator"));
            }
            if(isStoreFlag){
                List<String> storeKeylist = new ArrayList<String>(storeMapperMap.keySet());
                Collections.sort(storeKeylist);

                for (String key : storeKeylist) {
                    value= storeMapperMap.get(key);
                    myWriter.write(key + " : " + value);
                    myWriter.write(System.getProperty("line.separator"));
                }

            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return  resultFile;
    }


}


