package com.thomsonreuters.extractValidatorResultParser.jsonParser;

public class ValidatorController {

    public static void main(String a[]) {
        try {
            String jsonFile= "C:\\Users\\C269865\\IdeaProjects\\trta-idpt_extract-validator\\" + "VTestVE-TaxType_Result_CAPS_QA.json";

            new ValidatorJsonReader(jsonFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
