package com.thomsonreuters.regressionTool.jsonParser;

import com.google.gson.Gson;
import com.thomsonreuters.regressionTool.pojoClasses.Root;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class JsonReader {

    JsonReader(File jsonFile,String runType) throws Exception {
        jsonReader(jsonFile,runType);
    }

    public void jsonReader(File jsonFile,String runType) throws Exception {

        JSONParser parser = new JSONParser();
        FileReader fileReader = null;
        JSONObject json = null;


            fileReader = new FileReader(jsonFile);
            json = (JSONObject) parser.parse(fileReader);

            String str = json.toString();
            Gson gson = new Gson();
            Root root = gson.fromJson(str, Root.class);
            if (root.getGroupingRule().equalsIgnoreCase("taxType")) {
                if (runType.contains("CategoryKey"))
                    new HashMapForTreatmentComparsionByTaxTypeAndProductCategoryKey(root);
                else if (runType.contains("CategoryName"))
                    new HashMapForTreatmentComparsionByTaxTypeAndProductCategoryName(root);
                //new HashMapGeneratorGroupByTaxType(root);

            } else if (root.getGroupingRule().equalsIgnoreCase("authority")) {
                //new HashMapGeneratorGroupByAuthority(root);
                if (runType.contains("CategoryKey"))
                    new HashMapForTreatmentComparsionByAuthorityAndProductCategoryKey(root);
                else if (runType.contains("CategoryName"))
                    new HashMapForTreatmentComparsionByAuthorityAndProductCategoryName(root);

            }
            else if (root.getGroupingRule().equalsIgnoreCase("authorityType")) {
                new HashMapForTreatmentComparsionByAuthorityTypeAndProductCategoryKey(root);
            }

    }

}
