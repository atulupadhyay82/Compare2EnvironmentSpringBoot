package com.thomsonreuters.ExtractFetch;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thomsonreuters.regressionTool.jsonParser.JsonReader;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Fetch extract data from DEV, QA, TempQA, UAT environment
 */
public class ExtractFetch {

    private static String uri_QA;
    private static String user_QA;
    private static String password_QA;

    private static String uri_SAT;
    private static String user_SAT;
    private static String password_SAT;


    private static String user_Wish;
    private static String password_Wish;

    private static HashMap<String, String> result =new HashMap<>();


    public static void main(String a[]) throws IOException {

        List<String> acmeExtracts = Stream.of(
                "Dell-CN",
                "Dell-IN",
                "Dell-US",
                "Dell-UK").collect(Collectors.toList());

        Properties prop=new Properties(); // This class is available in java
        String propFilePath= System.getProperty("user.dir")+ "/src/main/resources/env.properties";
        FileInputStream ip= new FileInputStream(propFilePath);
        prop.load(ip);

        uri_QA=prop.getProperty("uri_QA");
        user_QA= prop.getProperty("userName_QA");
        password_QA=prop.getProperty("password_QA");

        uri_SAT=prop.getProperty("uri_SAT");
        user_SAT= prop.getProperty("userName_SAT");
        password_SAT=prop.getProperty("password_SAT");

        user_Wish= prop.getProperty("userName_Wish");
        password_Wish=prop.getProperty("password_Wish");

        MultiValuedMap<String, Collection<String>> multiValuedMap = new ArrayListValuedHashMap<String, Collection<String>>();
        multiValuedMap.put("ACME%20Company",acmeExtracts);
        ResponseEntity<String> responseEntity2;

        for(Map.Entry<String, Collection<String>> entries:multiValuedMap.entries() ){
            for(String extractName : entries.getValue())
            {
                try {
                    ResponseEntity<String> qaJsonFile= fetchExtractJSON(uri_QA,user_QA,password_QA,user_Wish,password_Wish,entries.getKey(),extractName,"QA");
                    ResponseEntity<String> satJsonFile= fetchExtractJSON(uri_SAT,user_SAT,password_SAT,user_Wish,password_Wish,entries.getKey(),extractName,"SAT");
                    compareBothJSON(extractName,qaJsonFile,satJsonFile);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("CompareExtract:: Error occured for extract " + extractName + ":: " + e.getMessage());

                }

            }
       }
        printResult(result);
    }

    private static void printResult(HashMap<String, String> result) {
        Iterator hmIterator = result.entrySet().iterator();

        // Iterate through the hashmap and add some bonus marks for every student
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            System.out.println(mapElement.getKey() + " : " + mapElement.getValue());
        }

    }

    private static void compareBothJSON(String extractName,ResponseEntity<String> qaJsonFile, ResponseEntity<String> satJsonFile) throws Exception {
        File qa_File=JsonReader.getProcessedJson(qaJsonFile.getBody(),"QA");
        File sat_File=JsonReader.getProcessedJson(satJsonFile.getBody(),"SAT");
        if(qa_File == null ) {
            result.put(extractName, " has some issue in QA env");
        }else if (sat_File ==null){
            result.put(extractName, " has some issue in SAT env");
        }else{
            boolean matched=FileUtils.contentEquals(qa_File, sat_File);
            if(matched)
                result.put(extractName, "matched");
            else
                result.put(extractName, "notMatched");
        }

    }


    private static ResponseEntity<String> fetchExtractJSON(String uri, String userName, String password, String userName_WISH, String password_WISH, String companyName, String extractName, String env) throws Exception {
        ResponseEntity<String> responseEntity;
        if(extractName.contains("Wish") ){
            responseEntity= getStringResponseEntity(uri, companyName, extractName, userName_WISH, password_WISH);
        }
        else{
            responseEntity = getStringResponseEntity(uri, companyName, extractName, userName, password);
        }
        return responseEntity;
//        runExtract(responseEntity,"extract",env);
    }



    private static void runExtract(ResponseEntity<String> responseEntity, String extractName, String env) throws Exception {
        String path = System.getProperty("user.home") + "/projects/";
        String s = responseEntity.getBody();
        JSONObject jSONObject = new JSONObject(s);
        Files.write(Paths.get("C:\\dell\\regression\\SAT3\\" + extractName + "_" + env + ".json"), jSONObject.toString().getBytes(StandardCharsets.UTF_8));
    }

    private static ResponseEntity<String> getStringResponseEntity(String url, String companyName, String extractName, String userName, String password) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String unEncoded = userName + ":" + password;
        URI uri = new URI(url + "services/rest/taxtreatments/company/" + companyName + "/extractName/" + extractName + "?loadMethod=FULL");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", "Basic " + Base64.getEncoder().encodeToString(unEncoded.getBytes()));
        System.out.println(uri.toString());
        ResponseEntity<String> response= restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<String>(null, httpHeaders),
                String.class
        );
        return response;

    }
}


