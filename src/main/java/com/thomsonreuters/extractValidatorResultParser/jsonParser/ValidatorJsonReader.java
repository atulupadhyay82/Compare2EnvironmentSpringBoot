package com.thomsonreuters.extractValidatorResultParser.jsonParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thomsonreuters.extractValidatorResultParser.extractValidatorPOJO.MainResult;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class ValidatorJsonReader {

    ValidatorJsonReader(String jsonFile) throws IOException {
        jsonReader(jsonFile);
    }

    public void connectToDB(){

    }
    public void jsonReader(String jsonFile) throws IOException {

//        Properties prop= new Properties();
//        String propFileName = "application.properties";
//        InputStream inputStream =getClass().getClassLoader().getResourceAsStream(propFileName);
//        if(prop !=null)
//            prop.load(inputStream);
//        else
//            throw new FileAlreadyExistsException("property file is not found in this classPath: "+ propFileName);
        // create Gson instance
        Gson gson = new Gson();

        // create a reader
        Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

        // convert JSON array to list of results
        List<MainResult> results = new Gson().fromJson(reader, new TypeToken<List<MainResult>>() {
        }.getType());
        Set<String> failedResults = new TreeSet<>();

        int count=0;
        for (MainResult result : results) {
                if (result.getTestResult().equals("FAILED")) {
                    count++;
                   boolean success= failedResults.add("Product - "+result.getProductCode() + ": Amount - "+result.getGrossAmount()+" : Effective Date - "+ result.getEffectiveDate()+" : MS_Amt - " + result.getModelScenarioTaxAmount() + " : CE_Amt - " + result.getExtractTaxAmount() + " : Jurisdiction- " + result.getJurisdiction()+
                            " : Postal and Geocode -"+result.getAddress().getPostalCode()+":"+result.getAddress().getGeocode()+" : City - "+result.getAddress().getCity() +" state - "+result.getAddress().getCounty());
                }


        }

        for(String fail:failedResults)
            System.out.println(fail);
    }

}



