package com.thomsonreuters.regressionTool.jsonParser;

import com.google.gson.Gson;
import com.thomsonreuters.Services.StagingService;
import com.thomsonreuters.regressionTool.pojoClasses.Root;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;

public class JsonReader {
    private static Logger logger = LoggerFactory.getLogger(JsonReader.class);

    public static File getProcessedJson(String jsonFile, String env) throws Exception {

            Gson gson = new Gson();
            File processedExtract =null;
            Root root = gson.fromJson(jsonFile, Root.class);

            if (root.getGroupingRule().equalsIgnoreCase("taxType") || root.getGroupingRule().equalsIgnoreCase("authorityType")) {
                processedExtract= new HashMapForTreatmentComparsionByTaxTypeAndProductCategoryName(root).hashMapGenerator(env);

            } else if (root.getGroupingRule().equalsIgnoreCase("authority")) {
                //new HashMapGeneratorGroupByAuthority(root);
                processedExtract=  new HashMapForTreatmentComparsionByAuthorityAndProductCategoryName(root).hashMapGenerator(env);

            }
//            else if (root.getGroupingRule().equalsIgnoreCase("authorityType")) {
//                new HashMapForTreatmentComparsionByAuthorityTypeAndProductCategoryKey(root);
//            }
//            if(root.getmLocations()!=null){
//                new StoreMapper(root);
//            }
        return processedExtract;

    }


}
