package com.thomsonreuters.extractValidatorResultParser.jsonParser;

public class ValidatorController {

    public static void main(String a[]) {
        try {
            String jsonFile = new String("C:\\Users\\C269865\\IdeaProjects\\trta-idpt_extract-validator\\" + "OutputResults.json");

            new ValidatorJsonReader(jsonFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
