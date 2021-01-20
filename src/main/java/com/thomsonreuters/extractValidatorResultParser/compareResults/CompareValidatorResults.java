package com.thomsonreuters.extractValidatorResultParser.compareResults;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thomsonreuters.extractValidatorResultParser.extractValidatorPOJO.MainResult;
import com.thomsonreuters.extractValidatorResultParser.jsonParser.ValidatorJsonReader;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

public class CompareValidatorResults {

    public static void main(String a[]) {
        try {
            String newJarFile= new String("C:\\Users\\C269865\\IdeaProjects\\trta-idpt_extract-validator\\build\\libs\\" + "ToryBurchMainExtract_Result_QA.json");
            String oldJarFile= new String("C:\\Users\\C269865\\IdeaProjects\\trta-idpt_extract-validator\\build\\libs\\" + "ToryBurchMainExtract_Result_SAT.json");

            MultiValuedMap<String,String> newJarMapResult = createMap(newJarFile);
            MultiValuedMap<String,String> oldJarMapResult= createMap(oldJarFile);


            List<String> newJarKeyList = new ArrayList<String>(newJarMapResult.keySet());

            List<String> oldJarKeyList = new ArrayList<String>(newJarMapResult.keySet());

            Collections.sort(newJarKeyList);
            Collections.sort(oldJarKeyList);
            int newPass=0,oldPass=0;


            for (String key : newJarKeyList) {

                Collection<String> newJarValues = newJarMapResult.get(key);
                Collection<String> oldJarValues = oldJarMapResult.get(key);


                if(newJarValues.toString().contains("FAIL") && (oldJarValues!=null && oldJarValues.toString().contains("PASS"))){
                    System.out.println("New Jar -" + key +" : "+ newJarValues);
                    System.out.println("Old Jar -" + key +" : "+ oldJarValues);
                    oldPass++;
                }

                if(newJarValues.toString().contains("PASS") && (oldJarValues!=null && oldJarValues.toString().contains("FAIL"))){
//                    System.out.println("New Jar -" + key +" : "+ newJarValues);
//                    System.out.println("Old Jar -" + key +" : "+ oldJarValues);
                    newPass++;
                }
            }
            System.out.println("New Pass- " + newPass+" old Pass- "+oldPass);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public static MultiValuedMap<String,String> createMap(String jsonFile) throws IOException {
        // create Gson instance
        Gson gson = new Gson();

        // create a reader
        Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

        // convert JSON array to list of results
        List<MainResult> results = new Gson().fromJson(reader, new TypeToken<List<MainResult>>() {
        }.getType());
        MultiValuedMap<String, String> parseResults = new ArrayListValuedHashMap<>();
        DecimalFormat df2 = new DecimalFormat();

        int count = 0;
        for (MainResult result : results) {
            parseResults.put(" Product - "+result.getProductCode() + ": Amount - "+result.getGrossAmount()+" : Effective Date - "+ result.getEffectiveDate()+ " : State- " + result.getAddress().getState()+ " : County- " + result.getAddress().getCounty()+
                         " : City- " + result.getAddress().getCity()+ " : Postal - "+ result.getAddress().getPostalCode()
                                   +" << "+result.getAddress().getGeocode()+" >>"," Result -"+result.getTestResult()+" : MS_Amt - " +df2.format( Double.parseDouble(result.getModelScenarioTaxAmount())) + " : CE_Amt - " + df2.format(Double.parseDouble(result.getExtractTaxAmount())));

        }

        return parseResults;
    }


}


