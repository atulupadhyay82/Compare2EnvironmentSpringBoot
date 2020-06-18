package com.thomsonreuters.extractValidatorResultParser.jsonParser;

public class ValidatorController {

    public static void main(String a[]) {
        try {
            String jsonFile = new String("C:\\Users\\C269865\\IdeaProjects\\trta-idpt_extract-validator\\" + "WayfairUAT_03_AZ_Result_1592408203332.json");

            new ValidatorJsonReader(jsonFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
