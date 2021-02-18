package com.thomsonreuters.Services;

import com.thomsonreuters.config.PropertyConfig;
import com.thomsonreuters.regressionTool.jsonParser.JsonReader;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hpsf.CustomProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class RunStaging {

    @Autowired
    private ExtractFetch extractFetch;

    @Autowired
    private StagingService stagingService;

    @Autowired
    private PropertyConfig propertyConfig;


    private static HashMap<String, String> result =new HashMap<>();

//    public void loadProperties() throws IOException {
//        Properties ceProp = new Properties(); // This class is available in java
//        String basePropLocation = System.getProperty("user.dir") + System.getProperty("file.separator") +"src"+System.getProperty("file.separator") +"main"+System.getProperty("file.separator") +"resources" +
//                System.getProperty("file.separator")+"config";
//
//        String cePropLocation= basePropLocation+ System.getProperty("file.separator")+"content-extract.properties";
//        FileInputStream fileInputStream= new FileInputStream(cePropLocation);
//        ceProp.load(fileInputStream);
//
//        uri_CE_QA=ceProp.getProperty("uri_QA");
//        user_CE_QA= ceProp.getProperty("userName_QA");
//        password_CE_QA=ceProp.getProperty("password_QA");
//
//        uri_CE_SAT=ceProp.getProperty("uri_SAT");
//        user_CE_SAT= ceProp.getProperty("userName_SAT");
//        password_CE_SAT=ceProp.getProperty("password_SAT");
//
//        user_CE_Wish= ceProp.getProperty("userName_Wish");
//        password_CE_Wish=ceProp.getProperty("password_Wish");
//
//        String stgPropLocation =basePropLocation+System.getProperty("file.separator")+"stg.properties";
//        fileInputStream= new FileInputStream(stgPropLocation);
//        ceProp.load(fileInputStream);
//
//        uri_STG_QA=ceProp.getProperty("uri_QA");
//        user_STG_QA= ceProp.getProperty("userName_QA");
//        password_STG_QA=ceProp.getProperty("password_QA");
//
//        uri_STG_SAT=ceProp.getProperty("uri_SAT");
//        user_STG_SAT= ceProp.getProperty("userName_SAT");
//        password_STG_SAT=ceProp.getProperty("password_SAT");
//
//        fileInputStream.close();
//    }

    public HashMap<String, String> compareExtractData(String companyName, String extractName) throws URISyntaxException, IOException {
      //  loadProperties();
        String response;
        String env1=propertyConfig.getEnvironment1();
        String env2=propertyConfig.getEnvironment2();
        Long extractID_Env1 =stagingService.getExtractFromEnv1(extractName);
        stagingService.triggerStaging(propertyConfig.getUri_STG_env1(),propertyConfig.getUser_STG_env1(),propertyConfig.getPassword_STG_env1(),extractID_Env1,env1);
        System.out.println(extractName+" is in execution state in "+  env1 +" environment, wait for its completion");
        Long extractID_Env2 =stagingService.getExtractFromEnv2(extractName);
        stagingService.triggerStaging(propertyConfig.getUri_STG_env2(),propertyConfig.getUser_STG_env2(),propertyConfig.getPassword_STG_env2(),extractID_Env2,env2);
        System.out.println(extractName+" is in execution state in "+  env2+" environment, wait for its completion");
        stagingService.waitForItsCompletionInEnv1(extractID_Env1,env1);
        stagingService.waitForItsCompletionInEnv2(extractID_Env2,env2);

        fetchAndCompareExtractData(companyName,extractName);
        return result;
    }


    public void fetchAndCompareExtractData(String companyName, String extractName){
        try {
            ResponseEntity<String> env_1_Json_String= extractFetch.fetchExtractJSON(propertyConfig.getUri_CE_env1(),propertyConfig.getUser_CE_env1(),propertyConfig.getPassword_CE_env1(),propertyConfig.getUser_CE_Wish(),
                    propertyConfig.getPassword_CE_Wish(),companyName,extractName,propertyConfig.getEnvironment1());

            ResponseEntity<String> env_2_Json_String= extractFetch.fetchExtractJSON(propertyConfig.getUri_CE_env2(),propertyConfig.getUser_CE_env2(),propertyConfig.getPassword_CE_env2(),propertyConfig.getUser_CE_Wish(),
                    propertyConfig.getPassword_CE_Wish(),companyName,extractName,propertyConfig.getEnvironment2());

            compareBothJSON(extractName,env_1_Json_String,env_2_Json_String);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CompareExtract:: Error occured for extract " + extractName + ":: " + e.getMessage());
        }

    }


    private void compareBothJSON(String extractName, ResponseEntity<String> qaJsonFile, ResponseEntity<String> satJsonFile) throws Exception {
        String env1=propertyConfig.getEnvironment1();
        String env2= propertyConfig.getEnvironment2();
        File env1_File= JsonReader.getProcessedJson(qaJsonFile.getBody(),env1);
        File env2_File=JsonReader.getProcessedJson(satJsonFile.getBody(),env2);
        if(env1_File ==null && env2_File ==null){
            result.put(extractName, " has some issue in "+ env1+ " and "+ env2+" both");
        }else if(env1_File == null ) {
            result.put(extractName, " has some issue in "+env1);
        }else if (env2_File ==null){
            result.put(extractName, " has some issue in "+env2);
        }else{
            boolean matched= FileUtils.contentEquals(env1_File, env2_File);
            if(matched)
                result.put(extractName, "matched");
            else
                result.put(extractName, "notMatched");
        }

    }

    }
