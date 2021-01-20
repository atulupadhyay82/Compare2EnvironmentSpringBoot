package com.thomsonreuters.extractValidatorResultParser.jsonParser;

public class ValidatorController {

    public static void main(String a[]) {
        try {
            String jsonFile= "C:\\dell\\regression\\" + "TB_result.json";

            new ValidatorJsonReader(jsonFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
