package com.thomsonreuters.extractValidatorResultParser.jsonParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thomsonreuters.extractValidatorResultParser.extractValidatorPOJO.MainResult;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DecimalFormat;
import java.util.*;



public class ValidatorJsonReader {

    ValidatorJsonReader(String jsonFile) throws IOException {
        jsonReader(jsonFile);
    }

    public void connectToDB(){

    }
    public void jsonReader(String jsonFile) throws IOException {


        // create Gson instance
        Gson gson = new Gson();

        // create a reader
        Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

        // convert JSON array to list of results
        List<MainResult> results = new Gson().fromJson(reader, new TypeToken<List<MainResult>>() {
        }.getType());

       // printFailedResultStateWise(results);
       MultiValuedMap<String,String> failedResults = new ArrayListValuedHashMap<>();

        Set<String> failedJurisdiction = new TreeSet<>();
        DecimalFormat df2 = new DecimalFormat();
       int count=0;
        for (MainResult result : results) {
               if (result.getTestResult().contains("FAIL")) {

//                    failedJurisdiction.add(result.getJurisdiction());

//                   failedResults.add(" Product - "+result.getProductCode() + ": Amount - "+result.getGrossAmount()+" : Effective Date - "+ result.getEffectiveDate()+" : MS_Amt - " + result.getModelScenarioTaxAmount() + " : CE_Amt - " + result.getExtractTaxAmount() + " : Jurisdiction- " + result.getJurisdiction()+
//                           " : Postal and Geocode -"+result.getAddress().getPostalCode()+":"+result.getAddress().getGeocode());

                   failedResults.put(" Product - "+result.getProductCode() + ": Amount - "+result.getGrossAmount()+" : Effective Date - "+ result.getEffectiveDate()+ " : State- " + result.getAddress().getState()+ " : County- " + result.getAddress().getCounty()+
                          " : City- " + result.getAddress().getCity()+ " : Postal - "+ result.getAddress().getPostalCode()
                                   +" << "+result.getAddress().getGeocode()+" >>"," Result -"+result.getTestResult()+" : MS_Amt - " +df2.format( Double.parseDouble(result.getModelScenarioTaxAmount())) + " : CE_Amt - " + df2.format(Double.parseDouble(result.getExtractTaxAmount())));

                    count++;




               }

       }


        Collection<Map.Entry<String, String>> entries = failedResults.entries();
        List<String> keylist = new ArrayList<String>(failedResults.keySet());

        Collections.sort(keylist);


        for (String key : keylist) {
            Collection<String> values = failedResults.get(key);
            for(String value: values)
                System.out.println(key + " : [" + value+"]");
        }
    }

    void printFailedResultStateWise(List<MainResult> results)
    {
        HashMap<String, Integer> failedMap=new HashMap<>();
        for (MainResult result : results){
            if (!result.getTestResult().contains("FAIL")){
                String state=result.getAddress().getState();
                if(!failedMap.containsKey(state)){
                    failedMap.put(state,1);
                }
                else{
                     failedMap.put(state, failedMap.get(state)+1);
                }
            }
        }
        Map<String, Integer> hm1 = sortByValue(failedMap);
        for (Map.Entry<String, Integer> en : hm1.entrySet()) {
            System.out.println("Key = " + en.getKey() +
                    ", Value = " + en.getValue());
        }
    }
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}



