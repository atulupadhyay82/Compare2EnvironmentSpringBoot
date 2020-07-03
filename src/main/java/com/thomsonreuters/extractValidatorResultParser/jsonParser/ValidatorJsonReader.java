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
        MultiValuedMap<String,String> failedResults = new ArrayListValuedHashMap<>();

        int count=0;
        for (MainResult result : results) {
              //  if (result.getTestResult().contains("FAIL")) {
                    count++;


//                   failedResults.add(" Product - "+result.getProductCode() + ": Amount - "+result.getGrossAmount()+" : Effective Date - "+ result.getEffectiveDate()+" : MS_Amt - " + result.getModelScenarioTaxAmount() + " : CE_Amt - " + result.getExtractTaxAmount() + " : Jurisdiction- " + result.getJurisdiction()+
//                           " : Postal and Geocode -"+result.getAddress().getPostalCode()+":"+result.getAddress().getGeocode());

                   failedResults.put(" Product - "+result.getProductCode() + ": Amount - "+result.getGrossAmount()+" : Effective Date - "+ result.getEffectiveDate()+ " : Jurisdiction- " + result.getJurisdiction()
                           ," Result -"+result.getTestResult()+" : MS_Amt - " + result.getModelScenarioTaxAmount() + " : CE_Amt - " + result.getExtractTaxAmount());



               // }



        }
        Collection<Map.Entry<String, String>> entries = failedResults.entries();
        List<String> keylist = new ArrayList<String>(failedResults.keySet());

        Collections.sort(keylist);

        for (String key : keylist) {
            Collection<String> values = failedResults.get(key);
            System.out.println(key + " : " + failedResults.get(key));
        }
    }

}



