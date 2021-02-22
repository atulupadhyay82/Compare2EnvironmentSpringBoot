package com.thomsonreuters;

import com.thomsonreuters.dto.TestCase;
import com.thomsonreuters.dto.TestResult;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application.properties")
public class TestIntegration  extends AbstractTestNGSpringContextTests
 {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


     @DataProvider(name = "wayfair")
     public Object[][] dpMethod(){
         return new Object[][]{
                 {"01_Wayfair_US","WayfairUAT_01_AL"},
                 {"01_Wayfair_US","WayfairUAT_01_AL_Store"},
                 {"01_Wayfair_US","WayfairUAT_02_AK"},
                 {"01_Wayfair_US","WayfairUAT_03_AZ"},
                 {"01_Wayfair_US","WayfairUAT_04_AR"},
                 {"01_Wayfair_US","WayfairUAT_05_CA"},
                 {"01_Wayfair_US","WayfairUAT_06_CO"},
                 {"01_Wayfair_US","WayfairUAT_07_CT"},
                 {"01_Wayfair_US","WayfairUAT_08_DE"},
                 {"01_Wayfair_US","WayfairUAT_09_DC"},
                 {"01_Wayfair_US","WayfairUAT_10_FL"},
                 {"01_Wayfair_US","WayfairUAT_11_GA"},
                 {"01_Wayfair_US","WayfairUAT_12_HI"},
                 {"01_Wayfair_US","WayfairUAT_13_ID"},
                 {"01_Wayfair_US","WayfairUAT_14_IL"},
                 {"01_Wayfair_US","WayfairUAT_15_IN"},
                 {"01_Wayfair_US","WayfairUAT_16_IA"},
                 {"01_Wayfair_US","WayfairUAT_17_KS"},
                 {"01_Wayfair_US","WayfairUAT_18_KY"},
                 {"01_Wayfair_US","WayfairUAT_19_LA"},
                 {"01_Wayfair_US","WayfairUAT_20_ME"},
                 {"01_Wayfair_US","WayfairUAT_21_MD"},
                 {"01_Wayfair_US","WayfairUAT_22_MA"},
                 {"01_Wayfair_US","WayfairUAT_23_MI"},
                 {"01_Wayfair_US","WayfairUAT_24_MN"},
                 {"01_Wayfair_US","WayfairUAT_25_MS"},
                 {"01_Wayfair_US","WayfairUAT_26_MO"},
                 {"01_Wayfair_US","WayfairUAT_27_MT"},
                 {"01_Wayfair_US","WayfairUAT_28_NE"},
                 {"01_Wayfair_US","WayfairUAT_29_NV"},
                 {"01_Wayfair_US","WayfairUAT_30_NH"},
                 {"01_Wayfair_US","WayfairUAT_31_NJ"},
                 {"01_Wayfair_US","WayfairUAT_32_NM"},
                 {"01_Wayfair_US","WayfairUAT_33_NY"},
                 {"01_Wayfair_US","WayfairUAT_34_NC"},
                 {"01_Wayfair_US","WayfairUAT_35_ND"},
                 {"01_Wayfair_US","WayfairUAT_36_OH"},
                 {"01_Wayfair_US","WayfairUAT_37_OK"},
                 {"01_Wayfair_US","WayfairUAT_37_OK_Auth"},
                 {"01_Wayfair_US","WayfairUAT_38_OR"},
                 {"01_Wayfair_US","WayfairUAT_39_PA"},
                 {"01_Wayfair_US","WayfairUAT_40B_RI"},
                 {"01_Wayfair_US","WayfairUAT_40_RI"},
                 {"01_Wayfair_US","WayfairUAT_41_SC"},
                 {"01_Wayfair_US","WayfairUAT_41_SC_Auth"},
                 {"01_Wayfair_US","WayfairUAT_42_SD"},
                 {"01_Wayfair_US","WayfairUAT_43_TN"},
                 {"01_Wayfair_US","WayfairUAT_44_TX"},
                 {"01_Wayfair_US","WayfairUAT_44_TX_Auth"},
                 {"01_Wayfair_US","WayfairUAT_45_UT"},
                 {"01_Wayfair_US","WayfairUAT_46_VT"},
                 {"01_Wayfair_US","WayfairUAT_47_VA"},
                 {"01_Wayfair_US","WayfairUAT_48_WA"},
                 {"01_Wayfair_US","WayfairUAT_49_WV"},
                 {"01_Wayfair_US","WayfairUAT_50_WI"},
                 {"01_Wayfair_US","WayfairUAT_51_WY"},
                 {"01_Wayfair_US","WayfairUAT_52_Canada"},
                 {"01_Wayfair_US","WayfairUAT_SD_LOCATION_ONLY"},
                 {"01_Wayfair_US","WayfairUAT_Texas%20Locations"},
                 {"01_Wayfair_US","WayfairUAT_Texas%20State,%20County%20and%20City"},
                 {"01_Wayfair_US","WayfairUAT_available2"}
         };
     }

    @Test(dataProvider = "wayfair", timeOut = 120000)
    public void testWayfair(String companyName, String extractName) throws Exception{
     TestCase testCase=new TestCase();
     testCase.setCompanyName(companyName);
     testCase.setExtractName(extractName);
        Assert.assertEquals("these are not matching" ,this.restTemplate.postForEntity("http://localhost:" + port+ "/compareExtract",
                testCase, TestResult.class).getBody().getResult(),
                "matched");
    }


}