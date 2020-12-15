package com.thomsonreuters.regressionTool.jsonParser;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    public static void main(String[] args) {

        List<String> vTestExtracts = Stream.of(
                "VTest%20AL%20Tax%20Holiday",
                "VTest%20Main",
                "VTest%20Main%20Range",
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

        List<String> wishTestExtracts = Stream.of("Wish_UAT_Switzerland_Test",
                "Wish_UAT_Medium_Test",
                "Wish_UAT_Full_Test_DEFAULT_MAPPING",
                "Wish_UAT_Medium_Test_DEFAULT_MAPPING",
                "Wish_UAT_PORTUGAL_TEST",
                "Wish_UAT_Switzerland_Test_2").collect(Collectors.toList());


             List<String> testWayfair = Stream.of("WayfairUAT_09_DC").collect(Collectors.toList());

        List<String> testVTest = Stream.of("VTestVE-AllAddress_622").collect(Collectors.toList());

        List<String> testCE570 = Stream.of(
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

        List<String> maxAmountExtract = Stream.of("Max_Amount_Extract_Authority","Max_Amount_Extract_TaxType", "WayfairUAT_14_IL").collect(Collectors.toList());

        List<String> authorityDisabled = Stream.of("WayfairUAT_44_TX_Authority").collect(Collectors.toList());
        List<String> testWayfairService=  Stream.of("WayfairUAT_57_TN_SERVICES").collect(Collectors.toList());
        List<String> testWayfairRandom=  Stream.of("WayfairUAT_44_TX_Authority_After","WayfairUAT_44_TX_Authority_Before").collect(Collectors.toList());

        List<String> testDC=  Stream.of("WayfairUAT_44_TX").collect(Collectors.toList());
        List<String> testToryBurch=  Stream.of("ToryBurchMainExtract").collect(Collectors.toList());


        MultiValuedMap<String, Collection<String>> multiValuedMap = new ArrayListValuedHashMap<String, Collection<String>>();
//        multiValuedMap.put("01_Wayfair_US", testWayfair);
////        multiValuedMap.put("VTest%20Industries", testVTest);
//         multiValuedMap.put("01_Wayfair_US",wayFairextracts);
         multiValuedMap.put("VTest%20Industries", vTestExtracts);
        multiValuedMap.put("zz%20-%20Acct%20-%20WISH",wishTestExtracts);
        multiValuedMap.put("zz%20-%20Acct%20-%20TORY%20BURCH%20LLC%20UAT", testToryBurch);
//       multiValuedMap.put("01_Wayfair_US", testCE570);
        multiValuedMap.put("zz%20-%20Acct%20-%20TORY%20BURCH%20LLC%20UAT", testToryBurch_825);
        multiValuedMap.put("01_Wayfair_US", testToryBurch_838);
//       multiValuedMap.put("01s_Wayfair_FL_TN_Services",testWayfairService);
//       multiValuedMap.put("01_Wayfair_US", authorityDisabled)
//       multiValuedMap.put("01_Wayfair_US", maxAmountExtract);
//    multiValuedMap.put("01_Wayfair_US", testDC);



        for(Map.Entry<String, Collection<String>> entries:multiValuedMap.entries() ){
            for(String extractName : entries.getValue())
            {
                try {
                    File jsonFile = new File("C:\\dell\\regression\\SAT\\" + extractName + "_new.json");
                   // File jsonFile = new File("C:\\dell\\functional\\809\\" + extractName + ".json");
                    new JsonReader(jsonFile,"CategoryName");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
