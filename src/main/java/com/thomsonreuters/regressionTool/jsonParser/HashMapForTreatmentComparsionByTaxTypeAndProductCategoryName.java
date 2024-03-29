package com.thomsonreuters.regressionTool.jsonParser;


import com.thomsonreuters.regressionTool.pojoClasses.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HashMapForTreatmentComparsionByTaxTypeAndProductCategoryName {


    Root root;
    HashMap<String, String> productHashMap = new HashMap();
    HashMap<String, Double> treatmentHashMapRate = new HashMap<String, Double>();
    MultiValuedMap<String, String> jurisdictionHashMap = new ArrayListValuedHashMap<String, String>();
    HashMap<String, String> treatmentHashMapSplitType = new HashMap<String, String>();
    MultiValuedMap<String, String> treatmentGroupHashMap = new ArrayListValuedHashMap<String, String>();
    boolean isStoreFlag;
    HashMap<String, String> storeMapperMap = new HashMap<String,String>();
    HashMap<String, String> addressMapperMap = new HashMap<String, String>();

    public HashMapForTreatmentComparsionByTaxTypeAndProductCategoryName(Root root) {
        this.root = root;
        if(root.getmLocations()!=null)
            isStoreFlag=true;
        else
            isStoreFlag=false;
    }

    public File hashMapGenerator(String env)  {
        File ouptutFile=null;
        try {
            productHashMapGenerator();
            treatmentGroupHashMapGenerator();
            treatmentHashMapGenerator();
            jurisdictionHashMapGenerator();
            if(isStoreFlag){
                addressHashMapGenerator();
                storeHashMapGenerator();
            }
            ouptutFile=jurisdictionTreatmentMappingsWriter(env);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ouptutFile;
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


    void productHashMapGenerator() {
        for (Product p : root.getProducts()) {
            if (!productHashMap.containsKey(p.getProductCategoryKey().toString()))
                productHashMap.put(p.getProductCategoryKey().toString(), p.getProductCategory());
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

     File jurisdictionTreatmentMappingsWriter(String env) throws IOException {
        String extractName= root.getExtractName();
        String fileName = extractName+"_" +env;
        File resultFile=null;

        MultiValuedMap<String, String> treatmentComaparator = new ArrayListValuedHashMap<String, String>();
        String keys , value;
        for (int i = 0; i < root.getJurisdictionTreatmentMappings().size(); i++) {
            keys="";
            String productCategoryKey = root.getJurisdictionTreatmentMappings().get(i).getProductCategoryKey();
            String treatmentGroupKey = root.getJurisdictionTreatmentMappings().get(i).getTreatmentGroupKey();
            String jurisdictionKey = root.getJurisdictionTreatmentMappings().get(i).getJurisdictionKey();
            String taxType = root.getJurisdictionTreatmentMappings().get(i).getTaxType();
            //String key=root.getJurisdictionTreatmentMappings().get(i).getKey();
            List<Long> fromDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getFrom();
            List<Long> toDate = root.getJurisdictionTreatmentMappings().get(i).getEffectiveDate().getmTo();
//            keys = productHashMap.get(productCategoryKey) + ":" + jurisdictionHashMap.get(jurisdictionKey) + ":" + taxType;
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
                    System.out.println("Rate is either null or corruped for :"  + " -> " + rateValue);
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
                keys= productHashMap.get(productCategoryKey) + ":" + address + ":" + taxType;

                treatmentComaparator.put(keys, value);

            }
//            System.out.println(treatmentComaparator.size());

        }

        Collection<Map.Entry<String, String>> entries = treatmentComaparator.entries();
        List<String> treatmentKeylist = new ArrayList<String>(treatmentComaparator.keySet());

        Collections.sort(treatmentKeylist);

        try {
            resultFile = new File(System.getProperty("user.dir") + "/src/main/resources/jsonFiles/" + fileName + ".txt");
            resultFile.createNewFile();
            FileWriter myWriter = new FileWriter(resultFile);
            for (String key : treatmentKeylist) {
                Collection<String> values = treatmentComaparator.get(key);
                // System.out.println(key +  " : " + treatmentComaparator.get(key));
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


