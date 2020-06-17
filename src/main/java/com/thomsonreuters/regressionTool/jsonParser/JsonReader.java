package com.thomsonreuters.regressionTool.jsonParser;

import com.google.gson.Gson;
import com.thomsonreuters.regressionTool.pojoClasses.Root;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class JsonReader {

    JsonReader(File jsonFile) {
        jsonReader(jsonFile);
    }

    public void jsonReader(File jsonFile) {

        JSONParser parser = new JSONParser();
        FileReader fileReader = null;
        JSONObject json = null;

        try {
            fileReader = new FileReader(jsonFile);
            json = (JSONObject) parser.parse(fileReader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = json.toString();
        Gson gson = new Gson();
        Root root = gson.fromJson(str, Root.class);
        if (root.getGroupingRule().equalsIgnoreCase("taxType")) {
            //new HashMapGeneratorGroupByTaxType(root);
            new HashMapForTreatmentComparsionByTaxType(root);
        } else if (root.getGroupingRule().equalsIgnoreCase("authority")) {
            //new HashMapGeneratorGroupByAuthority(root);
            new HashMapForTreatmentComparsionByAuthority(root);
        } else if (root.getGroupingRule().equalsIgnoreCase("authorityType")) {
            //  new HashMapGeneratorGroupByAuthorityType(root);
            new HashMapForTreatmentComparsionByTaxType(root);
        }
    }

}
