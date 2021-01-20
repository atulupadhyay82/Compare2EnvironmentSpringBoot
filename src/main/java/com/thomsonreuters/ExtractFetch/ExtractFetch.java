package com.thomsonreuters.ExtractFetch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
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
        String password1 = "e95XnPgNsDVxpPQP";
       String headerName = "CRESDM";

       //tempQA environment
        String uri_2 = "http://cre-sdm2-alb-1249178167.us-east-1.elb.amazonaws.com/";
       String userName2 = "^elvis-rest-client";
       String password2 = "e95XnPgNsDVxpPQP";

       //sat environment
        String uri_3 = "https://cre-api-sat.onesourcetax.com/";
        String userName3 = "^elvis-rest-client";
        String password3 = "e95XnPgNsDVxpPQP";

        //WISH sat environment
        String userName3_WISH = "^creuser";
        String password3_WISH = "password";

        //DCIS SAT env
        //dev environment
        String uri_4 = "https://cloud-ms-cre-uat-ws.hostedtax.thomsonreuters.com/";
        String userName4 = "^wayfair.ce.uat";
        String password4 = "w@f@1r19";
        ObjectMapper mapper = new ObjectMapper();

        //QA environment
        String uri_5 = "https://cre-api-qa.onesourcetax.com/";
        String userName5 = "^elvis-rest-client";
        String password5 = "password";

        //uat AWS environment
        String uri_6= "https://cre-api-uat.onesourcetax.com/";
        String userName6 = "^wayfair.ce.uat";
        String password6 = "w@f@1r19";

        //localhost environment
        String uri_local= "http://localhost:8101/";

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
                "Wish_UAT_Switzerland_Test_2",
                "ExtractTest").collect(Collectors.toList());

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
                "WayfairUAT_01_AL_Store",
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
                "WayfairUAT_37_OK_Auth",
                "WayfairUAT_38_OR",
                "WayfairUAT_39_PA",
                "WayfairUAT_40B_RI",
                "WayfairUAT_40_RI",
                "WayfairUAT_41_SC",
                "WayfairUAT_41_SC_Auth",
                "WayfairUAT_42_SD",
                "WayfairUAT_43_TN",
                "WayfairUAT_44_TX",
                "WayfairUAT_44_TX_Auth",
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

        List<String> hugoBossExtracts = Stream.of(
                "XStoreExtract").collect(Collectors.toList());

        List<String> quikTripExtracts = Stream.of(
                "TX%20Restaurant",
                "Non%20Restaurant",
                "NonRestaurantTaxType"
        ).collect(Collectors.toList());

        List<String> acmeExtracts = Stream.of(
                "Dell-CN",
                "Dell-IN",
                "Dell-US",
                "Dell-UK").collect(Collectors.toList());

        List<String> kpmgExtracts = Stream.of(
                "KPMG_UAT_RXC_01_MO").collect(Collectors.toList());

        List<String> seveenElevenExtracts = Stream.of(
                "seveneleven-small",
                "SevenElevenCanada").collect(Collectors.toList());

        List<String> sdiUSAExtracts = Stream.of(
                "main").collect(Collectors.toList());

        List<String> wishLogisticsExtracts = Stream.of(
                "ExtractTest").collect(Collectors.toList());

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
                "Wayfair_Test_AZ_570",
                "AZ_570_T736").collect(Collectors.toList());



        List<String> expediaExtracts=  Stream.of("expediaFranceExtract").collect(Collectors.toList());

        List<String> wayfairRandomTest = Stream.of("Max_Amount_Extract_Authority","Max_Amount_Extract_TaxType").collect(Collectors.toList());

        List<String> testWayfairService=  Stream.of("WayfairUAT_57_TN_SERVICES").collect(Collectors.toList());
        List<String> testToryBurch_838=  Stream.of(
                        "TB_AllFlagged_885" ,
                        "TB_FewFlags_885" ,
                        "TB_FewFlags_886" ,
                        "TB_Range_884" ,
                        "TB_Range_885" ,
                        "TB_Range_886" ,
                        "TB_AllFlagged_884" ,
                        "TB_AllFlagged_886" ,
                        "TB_FewFlags_884"
                        ).collect(Collectors.toList());

        List<String> testToryBurch_825=  Stream.of(
                "TB_825_NNN" ,
                "TB_825_NNY" ,
                "TB_825_NYN" ,
                "TB_825_NYY" ,
                "TB_825_YNN" ,
                "TB_825_YNY" ,
                "TB_825_YYN" ,
                "TB_825_YYY"
        ).collect(Collectors.toList());

        List<String> testToryBurch=  Stream.of( "ToryBurchMainExtract"
                                             ).collect(Collectors.toList());



        MultiValuedMap<String, Collection<String>> multiValuedMap = new ArrayListValuedHashMap<String, Collection<String>>();
//        multiValuedMap.put("01_Wayfair_US", testWayfair);
//        multiValuedMap.put("VTest%20Industries", testVTest);

     multiValuedMap.put("zz%20-%20Acct%20-%20TORY%20BURCH%20LLC%20UAT", testToryBurch);
//        multiValuedMap.put("01_Wayfair_US", wayFairextracts);
        multiValuedMap.put("VTest%20Industries", vTestExtracts);
//        multiValuedMap.put("zz%20-%20Acct%20-%20WISH",wishTestExtracts);
//        multiValuedMap.put("Hugo%20Boss%20Retail%20Inc",hugoBossExtracts);
        multiValuedMap.put("ACME%20Company",acmeExtracts);
        multiValuedMap.put("ZZ%20-%20Acct%20-%207-ELEVEN%20INC%20UAT",seveenElevenExtracts);
//        multiValuedMap.put("RxConnect",kpmgExtracts);
//        multiValuedMap.put("zz%20-%20Acct%20-%20SDI%20USA",sdiUSAExtracts);
//        multiValuedMap.put("WISH%20Logistics%20B.V.",wishLogisticsExtracts);
        multiValuedMap.put("QUIKTRIP%20CORPORATION",quikTripExtracts);
//        multiValuedMap.put("Expedia",expediaExtracts);

//        multiValuedMap.put("zz%20-%20Acct%20-%20WISH",wishTestExtracts);
//        multiValuedMap.put("ACME%20Company",acmeExtracts);
//        multiValuedMap.put("QUIKTRIP%20CORPORATION",quikTripExtracts);

//        multiValuedMap.put("01_Wayfair_US", test570);
//      multiValuedMap.put("VTest%20Industrie s", wayfairRandomTest);
////        multiValuedMap.put("01s_Wayfair_FL_TN_Services",testWayfairService);
//        multiValuedMap.put("zz%20-%20Acct%20-%20TORY%20BURCH%20LLC%20UAT", testToryBurch_825);
//        multiValuedMap.put("01_Wayfair_US", testToryBurch_838);
//        multiValuedMap.put("01_Wayfair_US", testTN);
        ResponseEntity<String> responseEntity2;
       for(Map.Entry<String, Collection<String>> entries:multiValuedMap.entries() ){
            for(String extractName : entries.getValue())
            {
                try {

                    File outputfile= new File("C:\\dell\\regression\\SAT2\\CE_Execution.txt");
                    if(extractName.contains("Wish") || extractName.contains("ExtractTest")){
                        responseEntity2 = getStringResponseEntity(uri_3, entries.getKey(), extractName, userName3_WISH, password3_WISH, headerName,outputfile);
                    }
                    else{
                        responseEntity2 = getStringResponseEntity(uri_3, entries.getKey(), extractName, userName2, password3, headerName,outputfile);
                    }

                    runExtract(responseEntity2, extractName, "new");

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("CompareExtract:: Error occured for extract " + extractName + ":: " + e.getMessage());

                }

            }
       }
    }

    private static void runExtract(ResponseEntity<String> responseEntity, String extractName, String env) throws Exception {
        String path = System.getProperty("user.home") + "/projects/";
        String s = responseEntity.getBody();
        // System.out.println("response::" + s);
        JSONObject jSONObject = new JSONObject(s);
//
//        jSONObject.remove("extractDate");
//        jSONObject.remove("contentVersion");

        Files.write(Paths.get("C:\\dell\\regression\\SAT2\\" + extractName + "_" + env + ".json"), jSONObject.toString().getBytes(StandardCharsets.UTF_8));
        String sort_json = "python C:\\Users\\C269865\\projects\\sort_json.py C:\\dell\\funtional\\825\\" + extractName + "_" + env + ".json C:\\dell\\functional\\825\\sorted\\" + extractName + "_sorted_" + env + ".json";



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


