package com.thomsonreuters.extractValidatorResultParser.jsonParser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thomsonreuters.extractValidatorResultParser.extractValidatorPOJO.MainResult;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ValidatorJsonReader {

    ValidatorJsonReader(String jsonFile) throws IOException {
        jsonReader(jsonFile);
    }

    public void jsonReader(String jsonFile) throws IOException {

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
                    failedResults.add(result.getProductCode() + result.getGrossAmount()+" : MS_Amt - " + result.getModelScenarioTaxAmount() + " : CE_Amt - " + result.getExtractTaxAmount() + " : Jurisdiction- " + result.getJurisdiction());
                }

        }

        for(String fail:failedResults)
            System.out.println(fail);
    }

}



