package com.thomsonreuters.extractValidatorResultParser.jsonParser;

public class ValidatorController {

    public static void main(String a[]) {
        try {
            String jsonFile= "C:\\dell\\functional\\toolsresult\\" + "WayfairUAT_Regression_With_Tier_Result_850.json";

            new ValidatorJsonReader(jsonFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
