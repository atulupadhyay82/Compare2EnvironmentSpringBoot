package com.thomsonreuters.Services;

import com.thomsonreuters.regressionTool.jsonParser.JsonReader;
import org.apache.commons.io.FileUtils;
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

    private static String uri_STG_QA;
    private static String user_STG_QA;
    private static String password_STG_QA;

    private static String uri_STG_SAT;
    private static String user_STG_SAT;
    private static String password_STG_SAT;

    private static String uri_CE_QA;
    private static String user_CE_QA;
    private static String password_CE_QA;

    private static String uri_CE_SAT;
    private static String user_CE_SAT;
    private static String password_CE_SAT;


    private static String user_CE_Wish;
    private static String password_CE_Wish;


    private static HashMap<String, String> result =new HashMap<>();

    public void loadProperties() throws IOException {
        Properties ceProp = new Properties(); // This class is available in java
        String basePropLocation = System.getProperty("user.dir") + System.getProperty("file.separator") +"src"+System.getProperty("file.separator") +"main"+System.getProperty("file.separator") +"resources" +
                System.getProperty("file.separator")+"config";

        String cePropLocation= basePropLocation+ System.getProperty("file.separator")+"content-extract.properties";
        FileInputStream fileInputStream= new FileInputStream(cePropLocation);
        ceProp.load(fileInputStream);

        uri_CE_QA=ceProp.getProperty("uri_QA");
        user_CE_QA= ceProp.getProperty("userName_QA");
        password_CE_QA=ceProp.getProperty("password_QA");

        uri_CE_SAT=ceProp.getProperty("uri_SAT");
        user_CE_SAT= ceProp.getProperty("userName_SAT");
        password_CE_SAT=ceProp.getProperty("password_SAT");

        user_CE_Wish= ceProp.getProperty("userName_Wish");
        password_CE_Wish=ceProp.getProperty("password_Wish");

        String stgPropLocation =basePropLocation+System.getProperty("file.separator")+"stg.properties";
        fileInputStream= new FileInputStream(stgPropLocation);
        ceProp.load(fileInputStream);

        uri_STG_QA=ceProp.getProperty("uri_QA");
        user_STG_QA= ceProp.getProperty("userName_QA");
        password_STG_QA=ceProp.getProperty("password_QA");

        uri_STG_SAT=ceProp.getProperty("uri_SAT");
        user_STG_SAT= ceProp.getProperty("userName_SAT");
        password_STG_SAT=ceProp.getProperty("password_SAT");

        fileInputStream.close();
    }

    public HashMap<String, String> compareExtractData(String companyName, String extractName) throws URISyntaxException, IOException {
        loadProperties();
        String response;
        Long extractID_QA =stagingService.getExtractFromQAEnv(extractName);
        stagingService.triggerStaging(uri_STG_QA,user_STG_QA,password_STG_QA,extractID_QA,"QA");
        System.out.println(extractName+" is in execution state in QA ENV, wait for its completion");
        Long extractID_SAT =stagingService.getExtractFromSATEnv(extractName);
        stagingService.triggerStaging(uri_STG_SAT,user_STG_SAT,password_STG_SAT,extractID_SAT,"SAT");
        System.out.println(extractName+" is in execution state in SAT ENV, wait for its completion");
        stagingService.waitForItsCompletionInQAEnv(extractID_QA);
        stagingService.waitForItsCompletionInSATEnv(extractID_SAT);

        fetchAndCompareExtractData(companyName,extractName);
        return result;
    }


    public void fetchAndCompareExtractData(String companyName, String extractName){
        try {
            ResponseEntity<String> qaJsonFile= extractFetch .fetchExtractJSON(uri_CE_QA,user_CE_QA,password_CE_QA,user_CE_Wish,password_CE_Wish,companyName,extractName,"QA");
            ResponseEntity<String> satJsonFile= extractFetch.fetchExtractJSON(uri_CE_SAT,user_CE_SAT,password_CE_SAT,user_CE_Wish,password_CE_Wish,companyName,extractName,"SAT");
            compareBothJSON(extractName,qaJsonFile,satJsonFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CompareExtract:: Error occured for extract " + extractName + ":: " + e.getMessage());
        }

    }


    private static void compareBothJSON(String extractName, ResponseEntity<String> qaJsonFile, ResponseEntity<String> satJsonFile) throws Exception {
        File qa_File= JsonReader.getProcessedJson(qaJsonFile.getBody(),"QA");
        File sat_File=JsonReader.getProcessedJson(satJsonFile.getBody(),"SAT");
        if(qa_File ==null && sat_File ==null){
            result.put(extractName, " has some issue in QA and SAT env both");
        }else if(qa_File == null ) {
            result.put(extractName, " has some issue in QA env");
        }else if (sat_File ==null){
            result.put(extractName, " has some issue in SAT env");
        }else{
            boolean matched= FileUtils.contentEquals(qa_File, sat_File);
            if(matched)
                result.put(extractName, "matched");
            else
                result.put(extractName, "notMatched");
        }

    }

    }
