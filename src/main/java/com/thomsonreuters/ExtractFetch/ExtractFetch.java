package com.thomsonreuters.ExtractFetch;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static void main(String a[]) {
        List<String> match = new ArrayList<>();
        List<String> noMatch = new ArrayList<>();

        //dev environment
       String uri_1 = "https://cre-api-dev.onesourcetax.com/";
        String userName1 = "^elvis-rest-client";
        String password1 = "password";
       String headerName = "CRESDM";

       //tempQA environment
        String uri_2 = "http://cre-sdm2-alb-1249178167.us-east-1.elb.amazonaws.com/";
       String userName2 = "^elvis-rest-client";
       String password2 = "e95XnPgNsDVxpPQP";

       //sat environment
        String uri_3 = "https://cre-api-sat.onesourcetax.com/";
        String userName3 = "^elvis-rest-client";
        String password3 = "e95XnPgNsDVxpPQP";

        //DCIS SAT env
        //dev environment
        String uri_4 = "https://cloud-ms-cre-uat-ws.hostedtax.thomsonreuters.com/";
        String userName4 = "^wayfair.ce.uat";
        String password4 = "w@f@1r19";
        ObjectMapper mapper = new ObjectMapper();

        //sat environment
        String uri_5 = "https://cre-api-qa.onesourcetax.com/";
        String userName5 = "^elvis-rest-client";
        String password5 = "e95XnPgNsDVxpPQP";

        //uat AWS environment
        String uri_6= "https://cre-api-uat.onesourcetax.com/";
        String userName6 = "^wayfair.ce.uat";
        String password6 = "w@f@1r19";



        List<String> companies = Stream.of(
                "01_Wayfair_US",
                "VTest%20Industries",
                "NetSuite%20CA%20Dev"
        ).collect(Collectors.toList());

        List<String> testWayfair = Stream.of("WayfairUAT_03_AZ_622",
                "WayfairUAT_05_CA_622",
                "WayfairUAT_10_FL_622",
                "WayfairUAT_45_UT_622").collect(Collectors.toList());

        List<String> testVTest = Stream.of("VTestVE-AllAddress_622").collect(Collectors.toList());

        List<String> wishTestExtracts = Stream.of("Wish_UAT_Switzerland_Test",
                "Wish_UAT_Medium_Test",
                "Wish_UAT_Full_Test_DEFAULT_MAPPING",
                "Wish_UAT_Medium_Test_DEFAULT_MAPPING",
                "Wish_UAT_PORTUGAL_TEST",
                "Wish_UAT_Switzerland_Test_2").collect(Collectors.toList());

        List<String> vTestExtracts = Stream.of(
                "VTest%20AL%20Tax%20Holiday",
                "VTest%20Main",
                "VTest%20Main%20Range",
               "VTest%20Rule%20Range%20Error",
                "VTest%20Rule%20Range%20Error",
                "VTest%20Tiers",
                "VTestVE-AllAddress",
                "VTestVE-Authority",
                "VTestVE-AuthorityType",
                "VTestVE-INTL",
                "VTestVE-TaxType"

        ).collect(Collectors.toList());

        List<String> wayFairextracts = Stream.of(
                "WayfairUAT_01_AL",
                "WayfairUAT_02_AK",
                "WayfairUAT_03_AZ",
                "WayfairUAT_04_AR",
                "WayfairUAT_05_CA",
                "WayfairUAT_06_CO",
                "WayfairUAT_07_CT",
                "WayfairUAT_08_DE",
                "WayfairUAT_09_DC",
               "WayfairUAT_10_FL",
                "WayfairUAT_11_GA",
                "WayfairUAT_12_HI",
                "WayfairUAT_13_ID",
                "WayfairUAT_14_IL",
                "WayfairUAT_15_IN",
                "WayfairUAT_16_IA",
                "WayfairUAT_17_KS",
                "WayfairUAT_18_KY",
                "WayfairUAT_19_LA",
                "WayfairUAT_20_ME",
                "WayfairUAT_21_MD",
                "WayfairUAT_22_MA",
                "WayfairUAT_23_MI",
                "WayfairUAT_24_MN",
                "WayfairUAT_25_MS",
                "WayfairUAT_26_MO",
                "WayfairUAT_27_MT",
                "WayfairUAT_28_NE",
                "WayfairUAT_29_NV",
                "WayfairUAT_30_NH",
                "WayfairUAT_31_NJ",
                "WayfairUAT_32_NM",
                "WayfairUAT_33_NY",
               "WayfairUAT_34_NC",
                "WayfairUAT_35_ND",
                "WayfairUAT_36_OH",
                "WayfairUAT_37_OK",
                "WayfairUAT_38_OR",
                "WayfairUAT_39_PA",
                "WayfairUAT_40B_RI",
                "WayfairUAT_40_RI",
                "WayfairUAT_41_SC",
                "WayfairUAT_42_SD",
                "WayfairUAT_43_TN",
                "WayfairUAT_44_TX",
                "WayfairUAT_45_UT",
                "WayfairUAT_46_VT",
                "WayfairUAT_47_VA",
                "WayfairUAT_48_WA",
                "WayfairUAT_49_WV",
                "WayfairUAT_50_WI",
                "WayfairUAT_51_WY",
                "WayfairUAT_52_Canada",
                "WayfairUAT_SD_LOCATION_ONLY",
                "WayfairUAT_Texas%20Locations",
                "WayfairUAT_Texas%20State,%20County%20and%20City",
                "WayfairUAT_available2"
        ).collect(Collectors.toList());

        List<String> test570 = Stream.of(
                "AZ_570_T703",
                "AZ_570_T704",
                "AZ_570_T705",
                "AZ_570_T706",
                "AZ_570_T707",
                "AZ_570_T708",
                "AZ_570_T709",
                "AZ_570_T710",
                "AZ_570_T711",
                "AZ_570_T712",
                "AZ_570_T735",
                "AZ_570_T736").collect(Collectors.toList());

        List<String> test427 = Stream.of(
                "WayfairUAT_33_NY").collect(Collectors.toList());

        List<String> testTN = Stream.of("WayfairUAT_43_TN").collect(Collectors.toList());

        List<String> testWayfairGoods=  Stream.of("WayfairUAT_56_TN_GOODS").collect(Collectors.toList());

        List<String> testWayfairService=  Stream.of("WayfairUAT_57_TN_SERVICES").collect(Collectors.toList());



        //This data needs to be written (Object[])
        Map < String, Object[] > empinfo = new TreeMap < String, Object[] >();

        Map<String, Collection<String>> map = new HashMap<>();
//       map.put("01_Wayfair_US", testWayfair);
       map.put("VTest%20Industries", testVTest);
//       map.put("01_Wayfair_US", wayFairextracts);
     //  map.put("VTest%20Industries", vTestExtracts);
//        map.put("zz%20-%20Acct%20-%20WISH",wishTestExtracts);

        //map.put("01_Wayfair_US", test427);
//        map.put("01_Wayfair_US", testTN);
//        map.put("01s_Wayfair_FL_TN_Services",testWayfairService);


        map.forEach((company, extract) -> {
            for (String extractName : extract) {
                try {

                    File outputfile= new File("C:\\dell\\regression\\newQA\\CE_Execution.txt");
                  // ResponseEntity<String> responseEntity1 = getStringResponseEntity(uri_5, company, extractName, userName5, password5, null,outputfile);

                    ResponseEntity<String> responseEntity2 = getStringResponseEntity(uri_5, company, extractName, userName3, password3, headerName,outputfile);
                    runExtract(responseEntity2, extractName, "new");



                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("CompareExtract:: Error occured for extract " + extractName + ":: " + e.getMessage());

                }
            }
        });




    }

    private static void runExtract(ResponseEntity<String> responseEntity, String extractName, String env) throws Exception {
        String path = System.getProperty("user.home") + "/projects/";
        String s = responseEntity.getBody();
        // System.out.println("response::" + s);
        JSONObject jSONObject = new JSONObject(s);
//
//        jSONObject.remove("extractDate");
//        jSONObject.remove("contentVersion");





        Files.write(Paths.get("C:\\dell\\regression\\newQA\\" + extractName + "_" + env + ".json"), jSONObject.toString().getBytes(StandardCharsets.UTF_8));
        String sort_json = "python C:\\Users\\C269865\\projects\\sort_json.py C:\\dell\\regression\\newSAT\\" + extractName + "_" + env + ".json C:\\dell\\regression\\newQA\\sorted\\" + extractName + "_sorted_" + env + ".json";



        Process pr = Runtime.getRuntime().exec(sort_json);
        BufferedReader input = new BufferedReader(new InputStreamReader(
                pr.getInputStream()));

        String line = null;

        while ((line = input.readLine()) != null) {

        }

        int exitVal = pr.waitFor();
       // System.out.println("Exited with error code " + exitVal);
    }

    private static ResponseEntity<String> getStringResponseEntity(String url, String companyName, String extractName, String userName, String password, String headerName, File outputFile) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String unEncoded = userName + ":" + password;
        URI uri = new URI(url + "services/rest/taxtreatments/company/" + companyName + "/extractName/" + extractName + "?loadMethod=FULL");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", "Basic " + Base64.getEncoder().encodeToString(unEncoded.getBytes()));
        if (headerName != null) {
            httpHeaders.add("Name", headerName);
        }
       // System.out.println("Started "+uri);
        Date date1 = new Date();
        Timestamp timestamp1 = new Timestamp(date1.getTime());

        ResponseEntity<String> response= restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<String>(null, httpHeaders),
                String.class
        );

        Date date2 = new Date();
        Timestamp timestamp2= new Timestamp(date2.getTime());
        long timeTaken=timestamp2.getTime() - timestamp1.getTime();
        FileWriter fw=null;
        String timeString="For "+extractName+" Start date: "+ timestamp1+" End date: "+ timestamp2 + " difference: "+timeTaken;
        try {
            fw= new FileWriter(outputFile,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(timeString);
            bw.newLine();
            bw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;

    }
}


